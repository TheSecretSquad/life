package lifeUI;

import javafx.scene.layout.Pane;


public class GUICell implements CellStateListener {

	private CellViewModel cellViewModel;
	private Pane pane;
	
	public GUICell(CellViewModel cellViewModel, Pane pane) {
		this.cellViewModel = cellViewModel;
		this.pane = pane;
	}
	
	@Override
	public void update() {
		this.showColorFor(this.cellViewModel.cellState());
	}
	
	private void showColorFor(CellViewModelState cellViewModelState) {
		switch(cellViewModelState) {
		case BORN:
			this.showBornColor();
		case ALIVE:
			this.showAliveColor();
		case SURVIVING:
			this.showSurvivingColor();
		case KILLED:
			this.showKilledColor();
		default:
			this.showDeadColor();
		}
	}

	private void showDeadColor() {
		this.pane.setStyle("-fx-background-color: black;");
	}

	private void showKilledColor() {
		// TODO Auto-generated method stub
	}

	private void showSurvivingColor() {
		// TODO Auto-generated method stub
		
	}

	private void showAliveColor() {
		// TODO Auto-generated method stub
		
	}

	private void showBornColor() {
		// TODO Auto-generated method stub
		
	}
}