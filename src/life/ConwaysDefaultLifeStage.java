package life;

public enum ConwaysDefaultLifeStage implements CellLifeStage {
	ALIVE,
	DEAD;

	private static CellLifeStage nextStageFromStageForNeighborCount(final ConwaysDefaultLifeStage lifeStage, final long liveNeighborCount) {
		switch(lifeStage) {
			case ALIVE:
				return nextStageFromAliveForNeighborCount(liveNeighborCount);
			case DEAD:
				return nextStageFromDeadForNeighborCount(liveNeighborCount);
			default:
				return ALIVE;
		}
	}
	
	private static CellLifeStage nextStageFromAliveForNeighborCount(final long liveNeighborCount) {
		return canLivingSurvive(liveNeighborCount) ? ALIVE : DEAD;
	}
	
	private static CellLifeStage nextStageFromDeadForNeighborCount(final long liveNeighborCount) {
		return canDeadBeBorn(liveNeighborCount) ? ALIVE : DEAD;
	}

	private static boolean canLivingSurvive(final long liveNeighborCount) {
		return liveNeighborCount == 2 || liveNeighborCount == 3;
	}
	
	private static boolean canDeadBeBorn(final long liveNeighborCount) {
		return liveNeighborCount == 3;
	}
	
	private static long countLiveNeighbors(final Cell cell, final Cells cells) {
		return cell.provideNeighbors().stream()
				.filter((Cell c) -> cells.isAlive(c))
				.count();
	}
	
	@Override
	public CellLifeStage evaluate(final Cell cell, final Cells cells) {
		long liveNeighborCount = countLiveNeighbors(cell, cells);
		return nextStageFromStageForNeighborCount(this, liveNeighborCount);
	}
}