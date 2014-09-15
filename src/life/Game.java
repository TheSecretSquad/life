package life;

import java.awt.event.ActionEvent;

import javax.swing.Timer;

public class Game {

	private Community community;
	private final DisplayGrid grid;
	private final Timer timer;
	private int numberOfGenerationsRequested;
	private int currentTicksPassed;
	
	public Game(Community community, DisplayGrid grid) {
		this.community = community;
		this.grid = grid;
		this.timer = new Timer(100, (ActionEvent e) -> this.nextGeneration());
		this.currentTicksPassed = 0;
	}

	public void start(int numberOfGenerations) {
		this.numberOfGenerationsRequested = numberOfGenerations;
		this.timer.start();
	}

	private void updateTimer() {
		this.currentTicksPassed++;
		if(this.currentTicksPassed >= this.numberOfGenerationsRequested)
			this.timer.stop();
	}
	
	private void nextGeneration() {
		this.community.asPublishable().publishTo(grid);
		this.community = this.community.tick();
		this.updateTimer();
	}
}