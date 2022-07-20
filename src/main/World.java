package main;

import java.awt.Dimension;
import java.awt.Point;

public class World {
	//The entity to move around
	private Point entity;
	
	//2D array to store passable and inpassable nodes
	private boolean worldGrid[][];
	
	private Dimension worldGridSize;
	
	public World(int worldWidth, int worldHeight, int entityX, int entityY) {
		entity = new Point(entityX, entityY);
		worldGridSize = new Dimension(worldWidth, worldHeight);
		worldGrid = new boolean[worldWidth][worldHeight];
	}
	
	public World(boolean[][] world, int entityX, int entityY) {
		worldGridSize = new Dimension(world.length, world[0].length);
		this.worldGrid = world;
		entity = new Point(entityX, entityY);
	}

	public Point getEntity() {
		return entity;
	}

	public void setEntity(Point entity) {
		this.entity = entity;
	}

	public boolean[][] getWorld() {
		return worldGrid;
	}

	public void setWorld(boolean[][] world) {
		this.worldGrid = world;
	}
	
	public void setWorldLoc(int x, int y, boolean value) {
		if(x >= 0 && x < worldGridSize.getWidth() && y >= 0 && y < worldGridSize.getHeight()) {
			worldGrid[x][y] = value;
		}
	}
	
	public boolean getWorldLoc(int x, int y) {
		return x >= 0 && x < worldGridSize.getWidth() && y >= 0 && y < worldGridSize.getHeight() ? worldGrid[x][y] : false;
	}

	public Dimension getWorldGridSize() {
		return worldGridSize;
	}
	
	public void clearWorld() {
		for(int ix = 0; ix < worldGridSize.width; ++ix) {
			for(int iy = 0; iy < worldGridSize.height; ++iy) {
				worldGrid[ix][iy] = false;
			}
		}
	}
}
