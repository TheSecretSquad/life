package test;

import java.util.HashSet;
import java.util.Set;

import life.Cell;
import life.Community;
import life.ConwaysCommunity;
import lifeUI.GUIGrid;

public class MainTest {

	public static void main(String[] args) {

		int dimension = 54;
		Set<Cell> initialLiveCells = new HashSet<>();
		Cell.rangeDo(new Cell(1, 1), new Cell(dimension, dimension), (Cell c) -> {
			if(Math.round((1.62 * 3.14 * Math.random())) % 2 == 0)
				initialLiveCells.add(c);
		});

		Community c = new ConwaysCommunity(dimension, initialLiveCells);
		GUIGrid grid = new GUIGrid(dimension, c);
		
		grid.start(10000);
	}
}