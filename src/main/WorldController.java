package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import pathfinding.EntityPath;
import pathfinding.EntityPathAi;

//This class handles player input and output of the world
public class WorldController extends JPanel implements MouseListener, MouseMotionListener, ActionListener {

	// The size of each cell
	private int cellSize = 15;

	World world;
	Point[] path;
	EntityPathAi ai;

	Point target;

	private static final long serialVersionUID = 1L;

	public WorldController(World world) {

		this.world = world;
		ai = new EntityPathAi(world.getEntity());

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				ai.update();
				repaint();
			}

		}, 100, 300);

		this.setSize(world.getWorld().length * cellSize, world.getWorld()[0].length * cellSize);
		this.setPreferredSize(new Dimension((int) world.getWorldGridSize().getWidth() * cellSize,
				(int) world.getWorldGridSize().getHeight() * cellSize));

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	private boolean drawPath = false, showGrid = false;

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		boolean[][] worldGrid = world.getWorld();

		// Draws world grid
		for (int ix = 0; ix < world.getWorldGridSize().getWidth(); ++ix) {
			for (int iy = 0; iy < world.getWorldGridSize().getHeight(); ++iy) {
				// Sets color ||||| true = white && false = grey
				g.setColor(worldGrid[ix][iy] ? Color.white : Color.gray);
				g.fillRect(ix * cellSize, iy * cellSize, cellSize, cellSize);
			}
		}

		// Draws grid lines
		if (showGrid) {
			g.setColor(Color.black);
			for (int ix = 0; ix < world.getWorldGridSize().getWidth(); ++ix) {
				g.drawLine(ix * cellSize, 0, ix * cellSize, (int) world.getWorldGridSize().getHeight() * cellSize);
			}
			for (int iy = 0; iy < world.getWorldGridSize().getHeight(); ++iy) {
				g.drawLine(0, iy * cellSize, (int) world.getWorldGridSize().getWidth() * cellSize, iy * cellSize);
			}
		}
		if (path != null && drawPath) {
			g.setColor(Color.lightGray);
			Point[] points = path;
			for (int i = 0; i < points.length; ++i) {
				g.fillRect(points[i].x * cellSize, points[i].y * cellSize, cellSize, cellSize);
			}
		}
		if (target != null) {
			g.setColor(Color.red);
			g.fillOval(target.x * cellSize, target.y * cellSize, cellSize, cellSize);
		}
		// Draws entity
		g.setColor(Color.blue);
		g.fillOval(world.getEntity().x * cellSize, world.getEntity().y * cellSize, cellSize, cellSize);
	}

	//////////////////////////////////////////////////////////
	// Handles input

	boolean mouseDown = false;

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.isControlDown()) {
			System.out.println("clicked start pathing");
			Point[] path;
			path = EntityPath.calculatePath(world.getWorld(), world.getWorldGridSize().width,
					world.getWorldGridSize().height, ai.getEntityLoc(),
					new Point(e.getX() / cellSize, e.getY() / cellSize), diagonal);
			this.path = path;
			ai.setPath(path);
			target = new Point(e.getX() / cellSize, e.getY() / cellSize);
			System.out.println("finished pathing");
			repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseDown = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseDown = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		world.setWorldLoc(e.getX() / cellSize, e.getY() / cellSize, !e.isShiftDown());
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	private static boolean diagonal;

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("here");
		if (e.getActionCommand().equals("Clear World")) {
			world.clearWorld();
		} else if (e.getActionCommand().equals("Show Path")) {
			drawPath = !drawPath;
		} else if (e.getActionCommand().equals("Show Grid")) {
			showGrid = !showGrid;
		} else if (e.getActionCommand().equals("Allow Diagonal")) {
			diagonal = !diagonal;
		}
	}

}
