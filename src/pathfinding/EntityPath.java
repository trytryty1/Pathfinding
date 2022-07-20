package pathfinding;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Stack;

import main.World;

public class EntityPath {
	public Point[] pathPoints;
	
	public EntityPath(Point[] points) {
		pathPoints = points;
	}

	public static EntityPath calculatePath(World world, Point startLoc, Point endLoc) {
		boolean[][] worldGrid = world.getWorld();
		boolean[][] closedNodes = new boolean[world.getWorldGridSize().width][world.getWorldGridSize().height];
		
		ArrayList<PathNode> nodes = new ArrayList<PathNode>();
		nodes.add(createPathNode(startLoc.x, startLoc.y, startLoc, endLoc, null));
		boolean pathComplete = false;
		PathNode lastNode = null;
		while(!pathComplete) {
			System.out.println("new iteration");
			PathNode cheapestNode = nodes.get(0);
			for(int i = 0; i < nodes.size(); ++i) {
				if(!closedNodes[nodes.get(i).getX()][nodes.get(i).getY()]) {
					if(nodes.get(i).getGcost() + nodes.get(i).getHcost() < cheapestNode.getGcost() + cheapestNode.getHcost()) {
						cheapestNode = nodes.get(i);
						System.out.println("found new cheapest node");
					}
				}
			}
			closedNodes[cheapestNode.getX()][cheapestNode.getY()] = true;
			for(int ix = cheapestNode.getX() - 1; ix <= cheapestNode.getX() + 1; ++ix) {
				for(int iy = cheapestNode.getY() - 1; iy <= cheapestNode.getY() + 1; ++iy) {
					if(!(ix != cheapestNode.getX() && iy != cheapestNode.getY())) {
						if(!(ix < 0) && !(ix >= world.getWorldGridSize().getWidth()) && !(iy < 0) && !(iy >= world.getWorldGridSize().getHeight())) {
							if(ix == endLoc.x && iy == endLoc.y) {
								lastNode = createPathNode(ix, iy, startLoc, endLoc, cheapestNode);
								pathComplete = true;
							} else if(!worldGrid[ix][iy] && !closedNodes[ix][iy]) {
								nodes.add(createPathNode(ix, iy, startLoc, endLoc, cheapestNode));
							}
						}
					}
					
				}
			}
			nodes.remove(cheapestNode);
			
		}
		Stack<Point> points = new Stack<Point>();
		while(lastNode.getPreviousNode() != null) {
			
			points.push(new Point(lastNode.getX(), lastNode.getY()));
			lastNode = lastNode.getPreviousNode();
		}
		Point[] pathpoints = new Point[points.size()];
		for(int i = points.size() - 1; i >= 0; --i ) {
			pathpoints[i] = points.get((points.size()-1) - i);
		}
		return new EntityPath(pathpoints);
	}
	
	public static PathNode createPathNode(int x, int y, Point startLoc, Point endLoc, PathNode previousNode) {
		return new PathNode(x, y, startLoc.distance(x, y),
				endLoc.distance(x, y), previousNode);
	}
}
