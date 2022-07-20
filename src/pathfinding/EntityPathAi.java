package pathfinding;

import java.awt.Point;

public class EntityPathAi {
	private EntityPath path;
	Point entityLoc;
	int pathindex = 0;
	
	public EntityPathAi(Point entityLoc) {
		this.entityLoc = entityLoc;
	}
	
	public void update() {
		if(path != null && entityLoc != null) {
			if(pathindex < path.pathPoints.length) {
				entityLoc.x = path.pathPoints[pathindex].x;
				entityLoc.y = path.pathPoints[pathindex].y;
				pathindex++;
			}
		}
	}
	
	public void setPath(EntityPath entityPath) {
		pathindex = 0;
		this.path = entityPath;
	}
}
