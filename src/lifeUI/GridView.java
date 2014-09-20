package lifeUI;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class GridView {

	private static final String borderStyle = "-fx-border-width: 0.5; -fx-border-color: gray;";
	private static final String bornStyle = "-fx-background-color: rgb(155, 227, 78); " + borderStyle;
	private static final String aliveStyle = "-fx-background-color: white; " + borderStyle;
	private static final String survivedStyle = "-fx-background-color: rgb(227, 217, 78); " + borderStyle;
	private static final String killedStyle = "-fx-background-color: rgb(227, 128, 78); " + borderStyle;
	private static final String deadStyle = "-fx-background-color: black; " + borderStyle;
	private final int dimension;
	private final GridPane gridPane;
	private ColumnConstraints columnConstraints;
	private RowConstraints rowConstraints;
	private final Pane[][] panes;
	private GridViewModel gridViewModel;
	
	public GridView(final GridPane gridPane, int dimension, GridViewModel gridViewModel) {
		this.gridPane = gridPane;
		this.dimension = dimension;
		this.panes = new Pane[dimension][dimension];
		this.gridViewModel = gridViewModel;
		this.setupGridPane();
	}
	
	private void setupGridPane() {
		this.initializeRowConstraints();
		this.initializeColumnConstraints();
		
		for(int col = 1; col <= dimension; col++) {
			attachConstraints();
			for(int row = 1; row <= dimension; row++) {
				addCellPane(col, row);
			}
		}
	}

	private void addCellPane(int col, int row) {
		Pane cp = this.createCellPane();
		this.panes[col - 1][row - 1] = cp;
		gridPane.add(cp, col - 1, row - 1);
	}

	private Pane createCellPane() {
		Pane cellPane = new Pane();
		cellPane.setStyle(deadStyle);
		return cellPane;
	}

	private void attachConstraints() {
		gridPane.getColumnConstraints().add(this.columnConstraints);
		gridPane.getRowConstraints().add(this.rowConstraints);
	}

	private void initializeRowConstraints() {
		this.rowConstraints = new RowConstraints();
        this.rowConstraints.setFillHeight(true);
        this.rowConstraints.setVgrow(Priority.ALWAYS);
	}

	private void initializeColumnConstraints() {
		this.columnConstraints = new ColumnConstraints();
        this.columnConstraints.setFillWidth(true);
        this.columnConstraints.setHgrow(Priority.ALWAYS);
	}
	
	public void update() {
		for(int col = 0; col < dimension; col++) {
			for(int row = 0; row < dimension; row++) {
				CellDisplayState cs = this.gridViewModel.cellState(col, row);
				this.applyColorFor(this.panes[col][row], cs);
			}
		}
	}

	private void applyColorFor(Pane pane, CellDisplayState cs) {
		switch(cs) {
		case BORN:
			pane.setStyle(bornStyle);
			break;
		case ALIVE:
			pane.setStyle(aliveStyle);
			break;
		case SURVIVED:
			pane.setStyle(survivedStyle);
			break;
		case KILLED:
			pane.setStyle(killedStyle);
			break;
		case DEAD:
			pane.setStyle(deadStyle);
			break;
		}
	}
}