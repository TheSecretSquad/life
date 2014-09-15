package life;

import java.util.HashMap;
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
	private PublishableCommunity publishableCommunity = null;

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
		Map<Cell, CellLifeStage> copyOfCurrentCells = new HashMap<>(this.cells);
		this.buildNextGeneration(copyOfCurrentCells);
		return new ConwaysCommunity(this.size, this.deadStage, this.initialLiveStage, copyOfCurrentCells);
	}

	private void buildNextGeneration(final Map<Cell, CellLifeStage> copyOfCurrentCells) {
		Cell.rangeDo(new Cell(1, 1), new Cell(this.size, this.size), (Cell c) -> {
				addNextGenerationOfCellTo(c, copyOfCurrentCells);
			});
	}

	private void addNextGenerationOfCellTo(final Cell cell, final Map<Cell, CellLifeStage> copyOfCurrentCells) {
		CellLifeStage nextStage = nextStageAtCell(cell, copyOfCurrentCells);
		
		if(nextStage.equals(this.deadStage))
			copyOfCurrentCells.remove(cell);
		else
			copyOfCurrentCells.put(cell, nextStage);
	}

	private CellLifeStage nextStageAtCell(final Cell cell, final Map<Cell, CellLifeStage> copyOfCurrentCells) {
		CellLifeStage cls = copyOfCurrentCells.getOrDefault(cell, this.deadStage);
		return this.nextStageAtCellFromStage(cell, cls);
	}

	private CellLifeStage nextStageAtCellFromStage(final Cell cell, final CellLifeStage cellStage) {
		return cellStage.evaluate(cell, this);
	}
	
	@Override
	public boolean isAlive(final Cell cell) {
		return this.cells.containsKey(cell);
	}
	
	@Override
	public PublishableCommunity asPublishable() {
		if(this.publishableCommunity == null)
			return this.publishableCommunity = new ConwaysPublishableCommunity(this.size, this.cells);
		
		return this.publishableCommunity;
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