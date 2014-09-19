package test;

import java.util.HashSet;
import java.util.Set;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import life.Cell;
import life.ConwaysCommunity;
import life.Game;
import lifeUI.EvolutionTimer;
import lifeUI.Grid;

public class Main extends Application {
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane gridPane = new GridPane();
		Grid grid = new Grid(gridPane, 140);
		Game g = new Game(new ConwaysCommunity(140, randomTest(140)), grid, 10000, new EvolutionTimer());
		Scene scene = new Scene(gridPane, 600, 600);
		primaryStage.setScene(scene);
		primaryStage.show();
		g.start();
	}
	
	private static Set<Cell> blockTest(int dimension) {
		Set<Cell> cells = new HashSet<>();
		Cell.rangeDo(new Cell(1, 1), new Cell(dimension / 2, dimension / 2), (Cell c) -> {
			cells.add(c);
		});

		Cell.rangeDo(new Cell(dimension / 2, dimension / 2), new Cell(dimension, dimension), (Cell c) -> {
			cells.add(c);
		});
		return cells;
	}
	
	private static Set<Cell> randomTest(int dimension) {
		Set<Cell> cells = new HashSet<>();
		Cell.rangeDo(new Cell(1, 1), new Cell(dimension, dimension), (Cell c) -> {
			if(Math.round((1.62 * 3.14 * Math.random())) % 2 == 0)
				cells.add(c);
		});
		return cells;
	}
	private static Set<Cell> gliderGunTest() {
		Set<Cell> cells = new HashSet<>();
		cells.add(new Cell(5, 5));
		cells.add(new Cell(5, 6));
		cells.add(new Cell(6, 5));
		cells.add(new Cell(6, 6));
		cells.add(new Cell(15, 5));
		cells.add(new Cell(15, 6));
		cells.add(new Cell(15, 7));
		cells.add(new Cell(16, 4));
		cells.add(new Cell(17, 3));
		cells.add(new Cell(18, 3));
		cells.add(new Cell(16, 8));
		cells.add(new Cell(17, 9));
		cells.add(new Cell(18, 9));
		cells.add(new Cell(19, 6));
		cells.add(new Cell(20, 4));
		cells.add(new Cell(20, 8));
		cells.add(new Cell(21, 5));
		cells.add(new Cell(21, 6));
		cells.add(new Cell(21, 7));
		cells.add(new Cell(22, 6));
		cells.add(new Cell(25, 5));
		cells.add(new Cell(25, 4));
		cells.add(new Cell(25, 3));
		cells.add(new Cell(26, 5));
		cells.add(new Cell(26, 4));
		cells.add(new Cell(26, 3));
		cells.add(new Cell(27, 6));
		cells.add(new Cell(27, 2));
		cells.add(new Cell(29, 6));
		cells.add(new Cell(29, 2));
		cells.add(new Cell(29, 7));
		cells.add(new Cell(29, 1));
		cells.add(new Cell(39, 3));
		cells.add(new Cell(39, 4));
		cells.add(new Cell(40, 3));
		cells.add(new Cell(40, 4));
		return cells;
	}
}