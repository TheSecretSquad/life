package life;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ConwaysCommunity implements Community, Cells {

	private static Map<Cell, CellLifeStage> createInitialCells(final Set<Cell> initialLiveCells, final CellLifeStage initialLifeStage) {
		return initialLiveCells.stream().collect(Collectors.toMap((Cell c) -> c, (Cell c) -> initialLifeStage));
	}

	private final CellLifeStage initialLiveStage;
	private final CellLifeStage deadStage;
	private final Map<Cell, CellLifeStage> cells;
	private final int size;

	public ConwaysCommunity(final int size, Set<Cell> initialCells) {
		this(size, ConwaysDefaultLifeStage.DEAD, ConwaysDefaultLifeStage.ALIVE, initialCells);
	}
	
	public ConwaysCommunity(
			final int size,
			final CellLifeStage deadStage,
			final CellLifeStage initialLiveStage,
			final Set<Cell> initialLiveCells) {
		this(size, deadStage, initialLiveStage, ConwaysCommunity.createInitialCells(initialLiveCells, initialLiveStage));
	}
	
	private ConwaysCommunity(
			final int size,
			final CellLifeStage deadStage,
			final CellLifeStage initialLiveStage,
			final Map<Cell, CellLifeStage> nextGeneration) {
		this.size = size;
		this.deadStage = deadStage;
		this.initialLiveStage = initialLiveStage;
		this.cells = nextGeneration;
	}

	public ConwaysCommunity tick() {
		Map<Cell, CellLifeStage> nextGeneration = new HashMap<>();
		buildNextGenerationTo(nextGeneration);
		return new ConwaysCommunity(this.size, this.deadStage, this.initialLiveStage, nextGeneration);
	}

	private void buildNextGenerationTo(Map<Cell, CellLifeStage> nextGeneration) {
		Cell.rangeDo(new Cell(1, 1), new Cell(this.size, this.size), (Cell c) -> {
			CellLifeStage cellNextLifeStage = nextLifeStageForCell(c);
			if(isLiving(cellNextLifeStage))
				nextGeneration.put(c, cellNextLifeStage);
		});
	}

	private boolean isLiving(CellLifeStage cellNextLifeStage) {
		return !cellNextLifeStage.equals(this.deadStage);
	}

	private CellLifeStage nextLifeStageForCell(Cell cell) {
		return this.cells.getOrDefault(cell, this.deadStage).evaluate(cell, this);
	}
	
	@Override
	public boolean isAlive(final Cell cell) {
		return this.cells.containsKey(cell);
	}
	

	@Override
	public ConwaysPublishableCommunity asPublishable() {
		return new ConwaysPublishableCommunity(new HashSet<Cell>(this.cells.keySet()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cells == null) ? 0 : cells.hashCode());
		result = prime * result + size;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConwaysCommunity other = (ConwaysCommunity) obj;
		if (cells == null) {
			if (other.cells != null)
				return false;
		} else if (!cells.equals(other.cells))
			return false;
		if (size != other.size)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder grid = new StringBuilder();

		Cell maxCell = new Cell(this.size, this.size);
		Cell.rangeDo(new Cell(1, 1), maxCell, (Cell c) -> {
				if(this.isAlive(c))
					grid.append("[O]");
				else
					grid.append("[ ]");
				if(c.equalsX(maxCell))
					grid.append("\n");
			});
		
		return grid.toString();
	}
}