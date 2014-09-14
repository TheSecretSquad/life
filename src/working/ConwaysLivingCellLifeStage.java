package working;

import static working.ConwaysDefaultLifeStage.countLiveNeighbors;

public class ConwaysLivingCellLifeStage implements CellLifeStage {

	@Override
	public CellLifeStage evaluate(Cell cell, Cells cells) {
		long liveNeighborCount = countLiveNeighbors(cell, cells);
		return nextStageForNeighborCount(liveNeighborCount);
	}

	private CellLifeStage nextStageForNeighborCount(long liveNeighborCount) {
		return canSurvive(liveNeighborCount) ? this	: ConwaysDefaultLifeStage.DEAD;
	}

	private boolean canSurvive(long liveNeighborCount) {
		return liveNeighborCount == 2 || liveNeighborCount == 3;
	}
}