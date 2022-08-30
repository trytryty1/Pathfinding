package main;

import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class World {
	// The entity to move around
	private Point entity;

	// 2D array to store passable and inpassable nodes
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
		if (x >= 0 && x < worldGridSize.getWidth() && y >= 0 && y < worldGridSize.getHeight()) {
			worldGrid[x][y] = value;
		}
	}

	public boolean getWorldLoc(int x, int y) {
		return x >= 0 && x < worldGridSize.getWidth() && y >= 0 && y < worldGridSize.getHeight() ? worldGrid[x][y]
				: false;
	}

	public Dimension getWorldGridSize() {
		return worldGridSize;
	}

	public void clearWorld() {
		for (int ix = 0; ix < worldGridSize.width; ++ix) {
			for (int iy = 0; iy < worldGridSize.height; ++iy) {
				worldGrid[ix][iy] = false;
			}
		}
	}

	// Lol all variables are one byte so don't go crazy on the size
	public static World loadWorld() {

		try {
			byte[] data = Files.readAllBytes(Paths.get("world"));
			int width = data[0];
			int height = data[1];

			int x = data[2];
			int y = data[3];
			boolean[][] grid = new boolean[width][height];
			for (int i = 4; i < data.length; ++i) {
				grid[(i-4) / width][(i-4) % width] = data[i] != 0;
			}

			return new World(grid, x, y);
		} catch (IOException e) {
			System.err.println("Could not load world");
			e.printStackTrace();
		}
		return null;
	}

	// Lol all variables are one byte so don't go crazy on the size
	public void saveWorld() {
		byte[] data = new byte[4 + this.getWorldGridSize().width * this.getWorldGridSize().height];
		data[0] = (byte) this.getWorldGridSize().width;
		data[1] = (byte) this.getWorldGridSize().height;
		data[2] = (byte) this.entity.x;
		data[3] = (byte) this.entity.y;
		System.out.println(3600 % this.getWorldGridSize().height);

		for (int i = 4; i < data.length; ++i) {
			data[i] = (byte) (worldGrid[(i-4) / this.getWorldGridSize().width][(i-4) % this.getWorldGridSize().height] ? 1 : 0);
		}

		try {
			Files.write(Paths.get("world"), data);
		} catch (IOException e) {
			System.err.println("Could not save world to file");
			e.printStackTrace();
		}
	}
}
