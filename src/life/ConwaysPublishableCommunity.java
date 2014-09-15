package life;

import java.util.Map;

public class ConwaysPublishableCommunity implements PublishableCommunity {
	
	private final int size;
	private final Map<Cell, CellLifeStage> cells;
	
	public ConwaysPublishableCommunity(final int size, final Map<Cell, CellLifeStage> cells) {
		this.size = size;
		this.cells = cells;
	}
	
	@Override
	public void publishTo(DisplayGrid displayGrid) {
		Cell.rangeDo(new Cell(1, 1), new Cell(size, size), (Cell c) -> {
				if(this.cells.containsKey(c))
					displayGrid.alive(c);
				else
					displayGrid.dead(c);
		});
	}
}