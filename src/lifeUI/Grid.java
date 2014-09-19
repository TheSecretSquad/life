package lifeUI;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import life.Cell;
import life.CommunityPublisher;

public class Grid implements CommunityPublisher {

	protected static String borderStyle = "-fx-border-width: 0.5; -fx-border-color: gray;";
	private final int dimension;
	private final GridPane gridPane;
	private ColumnConstraints columnConstraints;
	private RowConstraints rowConstraints;
	private final Map<Cell, GUICell> cells;
	
	public Grid(final GridPane gridPane, int dimension) {
		this.gridPane = gridPane;
		this.dimension = dimension;
		this.cells = new HashMap<>();
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
		this.cells.put(new Cell(col, row), new GUICell(cp));
		gridPane.add(cp, col - 1, row - 1);
	}

	private Pane createCellPane() {
		Pane cellPane = new Pane();
		cellPane.setStyle("-fx-background-color: black;" + borderStyle);
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

	@Override
	public void publishLiving(Set<Cell> livingCells) {
		Grid.this.cells.forEach((Cell c, GUICell gc) -> {
			if(livingCells.contains(c))
				gc.showAlive();
			else
				gc.showDead();
		});
	}
}