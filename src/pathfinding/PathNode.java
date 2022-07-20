package pathfinding;

public class PathNode {
	private int x, y;
	private double gcost, hcost;
	private PathNode previousNode;
	public PathNode(int x, int y, double gcost, double hcost, PathNode previousNode) {
		super();
		this.x = x;
		this.y = y;
		this.gcost = gcost;
		this.hcost = hcost;
		this.previousNode = previousNode;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public double getGcost() {
		return gcost;
	}
	public void setGcost(int gcost) {
		this.gcost = gcost;
	}
	public double getHcost() {
		return hcost;
	}
	public void setHcost(int hcost) {
		this.hcost = hcost;
	}
	public PathNode getPreviousNode() {
		return previousNode;
	}
	public void setPreviousNode(PathNode previousNode) {
		this.previousNode = previousNode;
	}
}
