package working;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Community implements Cells {

	private static Map<Cell, CellLifeStage> createInitialCells(final Set<Cell> initialLiveCells, CellLifeStage initialLifeStage) {
		return initialLiveCells.stream().collect(Collectors.toMap(c -> c, c -> initialLifeStage));
	}

	private final CellLifeStage initialLiveStage;
	private final CellLifeStage deadStage;
	private final Map<Cell, CellLifeStage> cells;
	private final int communitySize;

	public Community(final int communitySize, final CellLifeStage deadStage,
			final CellLifeStage initialLiveStage,
			final Set<Cell> initialLiveCells) {
		this(communitySize, deadStage, initialLiveStage, Community
				.createInitialCells(initialLiveCells, initialLiveStage));
	}

	private Community(final int communitySize, final CellLifeStage deadStage,
			final CellLifeStage initialLiveStage,
			final Map<Cell, CellLifeStage> nextGeneration) {
		this.communitySize = communitySize;
		this.deadStage = deadStage;
		this.initialLiveStage = initialLiveStage;
		this.cells = nextGeneration;
	}

	public Community tick() {
		Map<Cell, CellLifeStage> copyOfCurrentCells = new ConcurrentHashMap<>(this.cells);
		this.buildNextGeneration(copyOfCurrentCells);
		return new Community(this.communitySize, this.deadStage, this.initialLiveStage, copyOfCurrentCells);
	}

	private void buildNextGeneration(Map<Cell, CellLifeStage> copyOfCurrentCells) {
		for (int x = 1; x <= this.communitySize; x++) {
			for (int y = 1; y <= this.communitySize; y++) {
				addNextGenerationOfCellTo(new Cell(x, y), copyOfCurrentCells);
			}
		}
	}

	private void addNextGenerationOfCellTo(Cell cell, Map<Cell, CellLifeStage> copyOfCurrentCells) {
		CellLifeStage cls = copyOfCurrentCells.getOrDefault(cell, this.deadStage);
		cls = this.nextStageAtCellFromStage(cell, cls);
		if(cls.equals(this.deadStage))
			copyOfCurrentCells.remove(cell);
		else
			copyOfCurrentCells.put(cell, cls);
//		this.addIfDeadCell(cell, copyOfCurrentCells);
//		this.addIfLiveCell(cell, copyOfCurrentCells);
	}

//	private void addIfDeadCell(Cell cell, Map<Cell, CellLifeStage> copyOfCurrentCells) {
//		copyOfCurrentCells.computeIfAbsent(cell, (Cell c) -> {
//				return createMapValueFor(nextStageAtCellFromStage(c, this.deadStage));
//			});
//	}
//
//	private void addIfLiveCell(Cell cell, Map<Cell, CellLifeStage> copyOfCurrentCells) {
//		copyOfCurrentCells.computeIfPresent(cell, (Cell c, CellLifeStage cls) -> {
//			return createMapValueFor(this.nextStageAtCellFromStage(cell, cls));
//		});
//	}

	private CellLifeStage nextStageAtCellFromStage(Cell cell, CellLifeStage cellStage) {
		return cellStage.evaluate(cell, this);
	}

//	private CellLifeStage createMapValueFor(CellLifeStage nextStage) {
//		return nextStage.equals(this.deadStage) ? null : nextStage;
//	}
	
	@Override
	public boolean isAlive(Cell cell) {
		return this.cells.containsKey(cell);
	}
}