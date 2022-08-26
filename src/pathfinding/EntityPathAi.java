package pathfinding;

import java.awt.Point;

public class EntityPathAi {
	private Point[] path;
	Point entityLoc;
	int pathindex = 0;
	
	public EntityPathAi(Point entityLoc) {
		this.entityLoc = entityLoc;
	}
	
	public void update() {
		if(path != null && entityLoc != null) {
			if(pathindex < path.length) {
				entityLoc.x = path[pathindex].x;
				entityLoc.y = path[pathindex].y;
				pathindex++;
			}
		}
	}
	
	public void setPath(Point[] entityPath) {
		pathindex = 0;
		this.path = entityPath;
	}

	public Point getEntityLoc() {
		return entityLoc;
	}

	public void setEntityLoc(Point entityLoc) {
		this.entityLoc = entityLoc;
	}
}
