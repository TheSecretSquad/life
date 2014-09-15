package lifeUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import life.Cell;
import life.Community;
import life.CommunityPublisher;

public class GUIGrid implements CommunityPublisher{
	
	private Map<Cell, GUICell> allCells;
	private Community community;
	private JFrame frame;
	private final Timer timer;
	private int numberOfGenerationsRequested;
	private int currentTicksPassed;
	
	public GUIGrid(int dimension, Community community) {
		this.community = community;
		this.timer = new Timer(100, (ActionEvent e) ->  {
			GUIGrid.this.nextGeneration();
			GUIGrid.this.updateTimer();
		});
		this.allCells = new HashMap<>();
		this.frame = new JFrame();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(600, 600);
		JPanel allCellsPanel = new JPanel();
		allCellsPanel.setLayout(new GridLayout(dimension, dimension));
		
		Cell.rangeDo(new Cell(1, 1), new Cell(dimension, dimension), (Cell c) -> {
			JPanel cellPanel = new JPanel();
			GUICell newCell = new GUICell(false, cellPanel);
			this.allCells.put(c, newCell);
			allCellsPanel.add(cellPanel);
		});
		this.frame.add(allCellsPanel);
	}

	public void start(int numberOfGenerations) {
		this.numberOfGenerationsRequested = numberOfGenerations;
		this.frame.setVisible(true);
		this.timer.start();
	}
	
	private void updateTimer() {
		this.currentTicksPassed++;
		if(this.currentTicksPassed >= this.numberOfGenerationsRequested)
			this.timer.stop();
	}
	
	private void nextGeneration() {
		this.community.asPublishable().publishTo(this);
		this.community = this.community.tick();
		this.updateTimer();
	}

	@Override
	public void alive(Cell cell) {
		if(this.allCells.containsKey(cell))
			this.allCells.get(cell).alive();
	}

	@Override
	public void dead(Cell cell) {
		if(this.allCells.containsKey(cell))
			this.allCells.get(cell).dead();
	}
}