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
	
	private final Map<Cell, GUICell> allCells;
	private final JFrame theFrame;
	private final Timer timer;
	private Community community;
	private int numberOfGenerationsRequested;
	private int currentTicksPassed;
	
	public GUIGrid(int dimension, Community community) {
		this.community = community;
		this.timer = new Timer(200, (ActionEvent e) ->  {
			GUIGrid.this.nextGeneration();
			GUIGrid.this.updateTimer();
		});
		this.allCells = new HashMap<>();
		this.theFrame = new JFrame();
		setUpFrameDefaults();
		addCellsToFrame(dimension);
	}

	private void addCellsToFrame(int dimension) {
		JPanel allCellsPanel = new JPanel();
		allCellsPanel.setLayout(new GridLayout(dimension, dimension));
		addCellsToPanel(dimension, allCellsPanel);
		this.theFrame.add(allCellsPanel);
	}

	private void addCellsToPanel(int dimension, JPanel allCellsPanel) {
		Cell.rangeDo(new Cell(1, 1), new Cell(dimension, dimension), (Cell c) -> {
			JPanel cellPanel = new JPanel();
			GUICell newCell = new GUICell(cellPanel);
			this.allCells.put(c, newCell);
			allCellsPanel.add(cellPanel);
		});
	}

	private void setUpFrameDefaults() {
		this.theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.theFrame.setSize(600, 600);
	}

	public void start(int numberOfGenerations) {
		this.numberOfGenerationsRequested = numberOfGenerations;
		this.theFrame.setVisible(true);
		this.timer.start();
	}
	
	private void nextGeneration() {
		this.community.asPublishable().publishTo(this);
		this.community = this.community.tick();
		this.updateTimer();
	}

	private void updateTimer() {
		this.currentTicksPassed++;
		if(this.currentTicksPassed >= this.numberOfGenerationsRequested)
			this.timer.stop();
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