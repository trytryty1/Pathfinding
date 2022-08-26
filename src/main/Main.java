package main;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Main {
	World world;
	WorldController worldController;
	
	private static JFrame frame;
	public static final Dimension JFRAME_DEFAULT_SIZE = new Dimension(500,500);
	
	public static void main(String args[]) {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("A* pathfinding");
		frame.setSize(JFRAME_DEFAULT_SIZE);
		frame.setVisible(true);
		
		new Main();
	}
	
	Main() {
		world = new World(60,60,10,10);
		worldController = new WorldController(world);
		

		JMenuBar menuBar = new JMenuBar();
		
		
		JMenu mainMenu = new JMenu("World");
		
		JMenuItem clearWorldOption = new JMenuItem("Clear World");
		clearWorldOption.addActionListener(worldController);
		mainMenu.add(clearWorldOption);
		
		JMenuItem showPathOption = new JMenuItem("Show Path");
		showPathOption.addActionListener(worldController);
		mainMenu.add(showPathOption);
		
		JMenuItem gridLinesOption = new JMenuItem("Show Grid");
		gridLinesOption.addActionListener(worldController);
		mainMenu.add(gridLinesOption);
		
		JMenuItem diagonalOption = new JMenuItem("Allow Diagonal");
		diagonalOption.addActionListener(worldController);
		mainMenu.add(diagonalOption);
		
		
		menuBar.add(mainMenu);
		frame.setJMenuBar(menuBar);
		
		frame.setContentPane(worldController);
		frame.pack();
	}
}
