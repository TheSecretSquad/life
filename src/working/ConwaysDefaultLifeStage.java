package working;

import java.util.Arrays;

public enum ConwaysDefaultLifeStage implements CellLifeStage {
	ALIVE,
	DEAD;

	public static long countLiveNeighbors(Cell cell, Cells cells) {
		return Arrays.asList(neighboringCells(cell)).stream()
				.filter((Cell c) -> cells.isAlive(cell))
				.count();
	}

	private static Cell[] neighboringCells(Cell cell) {
		return new Cell[] {
				cell.up(),
				cell.down(),
				cell.left(),
				cell.right(),
				cell.up().left(),
				cell.up().right(),
				cell.down().left(),
				cell.down().right()
			};
	}
	
	@Override
	public CellLifeStage evaluate(Cell cell, Cells cells) {
		return DEAD;
	}
}