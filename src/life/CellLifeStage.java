package life;

public interface CellLifeStage {
	
	CellLifeStage evaluate(final Cell cell, final Cells cells);
}