package pathfinding;

import java.awt.Point;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

/**
 * @author wing_
 *
 */
public class EntityPath {

	public static final int MOVE_COST = 10;
	public static final int DIAGONAL_MOVE_COST = 14;

	// TODO: you can totally combine these methods by changing the node list to
	// allow diagonal
	// TODO: its slower going to the right idk
	// TODO: it creates stupid paths sometimes
	public static Point[] calculatePath(boolean[][] blockedTiles, int xBound, int yBound, Point startLoc,
			Point endLoc, boolean diagonal) {
		// Make sure the target and starting point are in bounds
		if (startLoc.x > xBound - 1 || startLoc.x < 0 || endLoc.y > yBound - 1 || endLoc.y < 0) {
			System.err.println("Could not calcuate path because the targets are out of bounds");
			return null;
		}

		// Make sure the target and starting point are not the same
		if (startLoc.x == endLoc.x && startLoc.y == endLoc.y) {
			System.err.println("Path target is the same as the start location");
			return new Point[] { (Point) startLoc.clone() };
		}

		System.out.println("starting path from: " + startLoc.x + "," + startLoc.y);
		System.out.println("starting path to: " + endLoc.x + "," + endLoc.y);

		boolean testedNodes[][] = new boolean[xBound][yBound];
		int gCost[][] = new int[xBound][yBound];
		int[][] parent = new int[xBound][yBound];
		boolean finished = false;

		// Nodes that are touching testedNodes and are calculated
		boolean openNodes[][] = new boolean[xBound][yBound];
		// Distance from starting node
		int[][] cost = new int[xBound][yBound];
		// Keep track of the cheapest nodes
		int cheapestNodeValue = 1000000;

		Stack<Point> cheapestNodes = new Stack<>();

		Point test = (Point) startLoc.clone();
		
		int[] nodeXlist;
		int[] nodeYlist;

		if (diagonal) {
			nodeXlist = new int[] { test.x + 1, test.x - 1, test.x, test.x };
			nodeYlist = new int[] { test.y, test.y, test.y + 1, test.y - 1 };
		} else {
			nodeXlist = new int[] { test.x + 1, test.x - 1, test.x, test.x, test.x + 1, test.x - 1,
					test.x + 1, test.x - 1 };
			nodeYlist = new int[] { test.y, test.y, test.y + 1, test.y - 1, test.y + 1, test.y + 1,
					test.y - 1, test.y - 1 };
		}
		
		// Prime the list with open nodes
		for (int i = 0; i < nodeXlist.length; ++i) {
			int nodeX = nodeXlist[i];
			int nodeY = nodeYlist[i];
			
			openNodes[nodeX][nodeY] = !blockedTiles[nodeX][nodeY];
			gCost[nodeX][nodeY] = i > 3 ? DIAGONAL_MOVE_COST: MOVE_COST;
			parent[nodeX][nodeY] = i;
		}

		while (!finished) {

			// Test if we have any more nodes at the current cheapest value
			//if (cheapestNodes.isEmpty()) {
			if(true) {
				cheapestNodeValue = 1000000;
				// Check for the cheapest node value
				for (int x = 0; x < xBound; x++) {
					for (int y = 0; y < yBound; y++) {
						if (openNodes[x][y] && !testedNodes[x][y]) {
							if (cost[x][y] < cheapestNodeValue)
								cheapestNodeValue = cost[x][y];
						}
					}
				}

				// Populate the cheapest node list
				for (int x = 0; x < xBound; x++) {
					for (int y = 0; y < yBound; y++) {
						if (openNodes[x][y] && !testedNodes[x][y] && !blockedTiles[x][y]) {
							// If the cost is ever lower then we have a problem
							if (cost[x][y] <= cheapestNodeValue) {

								cheapestNodes.push(new Point(x, y));
							}
						}
					}
				}
			}

			// Grab the cheapest node
			test = cheapestNodes.pop();
			
			
			if (diagonal) {
				nodeXlist = new int[] { test.x + 1, test.x - 1, test.x, test.x };
				nodeYlist = new int[] { test.y, test.y, test.y + 1, test.y - 1 };
			} else {
				nodeXlist = new int[] { test.x + 1, test.x - 1, test.x, test.x, test.x + 1, test.x - 1,
						test.x + 1, test.x - 1 };
				nodeYlist = new int[] { test.y, test.y, test.y + 1, test.y - 1, test.y + 1, test.y + 1,
						test.y - 1, test.y - 1 };
			}

			// Test if we have finished
			if (test.x == endLoc.x && test.y == endLoc.y) {
				finished = true;
				Stack<Point> stack = new Stack<>();
				stack.push(test);

				// Rebuild the path from the end to the start
				while (true) {

					// Grab the inverse of the normal node list so the direction can be traced backwards
					if (diagonal) {
						nodeXlist = new int[] { test.x - 1, test.x + 1, test.x, test.x };
						nodeYlist = new int[] { test.y, test.y, test.y - 1, test.y + 1 };
					} else {
						nodeXlist = new int[] { test.x - 1, test.x + 1, test.x, test.x, test.x - 1, test.x + 1,
								test.x - 1, test.x + 1 };
						nodeYlist = new int[] { test.y, test.y, test.y - 1, test.y + 1, test.y - 1, test.y - 1,
								test.y + 1, test.y + 1 };
					}
					
					int parentNode = parent[test.x][test.y];
					int lowestX = nodeXlist[parentNode];
					int lowestY = nodeYlist[parentNode];
					

					if (lowestX == startLoc.x && lowestY == startLoc.y) {
						Collections.reverse(stack);
						return stack.toArray(new Point[0]);
					}
					
					test = new Point(lowestX, lowestY);
					stack.push(test);

				}
			}

			testedNodes[test.x][test.y] = true;
			
			// Check for surrounding nodes
			for (int i = 0; i < nodeXlist.length; ++i) {
				int nodeX = nodeXlist[i];
				int nodeY = nodeYlist[i];
				if (nodeX > 0 && nodeY > 0 && nodeX < xBound && nodeY < yBound) {
					// TODO: we might want to recalculate the tested nodes
					if (!testedNodes[nodeX][nodeY] && !blockedTiles[nodeX][nodeY]) {
						// Calculate diagonal
						int newGCost;
						if(!diagonal) {
							newGCost = ((i > 3) ? DIAGONAL_MOVE_COST:MOVE_COST) + gCost[test.x][test.y];
						} else {
							newGCost = MOVE_COST + gCost[test.x][test.y];
						}
						System.out.println(gCost[test.x][test.y]);
						int newCost = (int) (distance(nodeX, nodeY, endLoc.x, endLoc.y) * 10) + newGCost;
						if (!openNodes[nodeX][nodeY] || newCost < cost[nodeX][nodeY]) {
							gCost[nodeX][nodeY] = newGCost;
							cost[nodeX][nodeY] = newCost;
							parent[nodeX][nodeY] = i;
							if (cost[nodeX][nodeY] == cheapestNodeValue) {
								cheapestNodes.push(new Point(nodeX, nodeY));
							} else if (cost[nodeX][nodeY] < cheapestNodeValue) {
								cheapestNodes.clear();
								cheapestNodes.push(new Point(nodeX, nodeY));
								cheapestNodeValue = cost[nodeX][nodeY];
							}
							openNodes[nodeX][nodeY] = true;
						}
					}
				}
			}
		}

		return null;
	}

	public static float distance(int x, int y, int x2, int y2) {
		return (float) Math.hypot(x - x2, y - y2);
	}
}
