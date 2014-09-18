package lifeUI;

public class CellViewModel {
	
	private CellViewModelState cellState;
	private CellStateListener cellStateListener;
	
	public CellViewModel(CellViewModelState initialCellState) {
		this.cellState = initialCellState;
		this.cellStateListener = cellStateListener;
	}
	
	public CellViewModelState cellState(){
		return this.cellState;
	}
	
	public void alive() {
		this.cellState = this.cellState.nextLivingState();
		this.cellStateListener.update();
	}
	
	public void dead() {
		this.cellState = this.cellState.nextDeadState();
		this.cellStateListener.update();
	}
	
	public void makeListener(CellStateListener cellStateListener) {
		this.cellStateListener = cellStateListener;
	}
}