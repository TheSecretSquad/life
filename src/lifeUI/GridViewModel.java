package lifeUI;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import life.Cell;
import life.CommunityPublisher;

public class GridViewModel implements CommunityPublisher {
	
	private final Map<Cell, CellDisplayState> lastCells;
	private final Set<GridView> gridViews;
	private final int dimension;
	
	public GridViewModel(final int size) {
		this.dimension = size;
		this.lastCells = new HashMap<>();
		this.gridViews = new HashSet<>();
	}

	@Override
	public void publishLiving(final Set<Cell> livingCells) {
		this.evaluateNextDisplayStates(livingCells);
		this.updateViews();
	}

	private void evaluateNextDisplayStates(final Set<Cell> livingCells) {
		Cell.rangeDo(new Cell(1, 1), new Cell(dimension, dimension), (Cell c) -> {
			CellDisplayState currentDisplayState = this.lastCells.getOrDefault(c, CellDisplayState.DEAD);
			
			if(livingCells.contains(c))
				this.lastCells.put(c, currentDisplayState.nextLivingState());
			else
				this.lastCells.put(c, currentDisplayState.nextDeadState());
		});
	}

	public CellDisplayState cellState(final int x, final int y) {
		return this.lastCells.getOrDefault(new Cell(x + 1, y + 1), CellDisplayState.DEAD);
	}
	
	public void addView(final GridView gridView) {
		this.gridViews.add(gridView);
	}
	
	private void updateViews() {
		this.gridViews.forEach((GridView gv) -> {
			gv.update();
		});
	}
}