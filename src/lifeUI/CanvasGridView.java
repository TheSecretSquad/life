package lifeUI;

import java.util.stream.IntStream;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class CanvasGridView implements GridModelListener {

	private static final Color bornColor = Color.rgb(155, 227, 78);
	private static final Color aliveColor = Color.valueOf("white");
	private static final Color survivedColor = Color.rgb(227, 217, 78);
	private static final Color killedColor = Color.rgb(227, 128, 78);
	private static final Color deadColor = Color.valueOf("black");
	private static final int gapSizeBetweenCells = 2;
	
	private int dimension;
	private GridViewModel gridViewModel;
	private Canvas cellCanvas;
	
	private static double startPoint(int unitNumber, double unitDimensionValue) {
		return unitNumber * unitDimensionValue;
	}
	
	public CanvasGridView(int dimension, GridViewModel gridViewModel, Canvas cellCanvas) {
		this.dimension = dimension;
		this.gridViewModel = gridViewModel;
		this.cellCanvas = cellCanvas;
	}

	@Override
	public void update() {
		this.beforeDrawCells();
		drawCells();
	}

	private void drawCells() {
		GraphicsContext gc = this.cellCanvas.getGraphicsContext2D();
		IntStream.rangeClosed(0, dimension).forEach((int column) -> {
			IntStream.rangeClosed(0, dimension).forEach((int row) -> {
				this.drawCellFor(column, row, this.gridViewModel.cellState(column, row), gc);
			});
		});
	}
	
	private void beforeDrawCells() {
		clearCells();
	}

	private void clearCells() {
		GraphicsContext gc = this.cellCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, this.cellCanvas.getWidth(), this.cellCanvas.getHeight());
	}
	
	private void drawCellFor(int column, int row, CellDisplayState cellState, GraphicsContext gc) {
		gc.setFill(chooseColor(cellState));
		gc.fillRect(cellStartX(column), cellStartY(row), cellWidth(), cellHeight());
	}

	private Paint chooseColor(CellDisplayState cellState) {
		switch(cellState) {
			case BORN:
				return CanvasGridView.bornColor;
			case ALIVE:
				return CanvasGridView.aliveColor;
			case SURVIVED:
				return CanvasGridView.survivedColor;
			case KILLED:
				return CanvasGridView.killedColor;
			case DEAD:
				return CanvasGridView.deadColor;
			default:
				return CanvasGridView.deadColor;
		}
	}
	
	private double cellStartX(int column) {
		return startPoint(column, cellWidthIncludingGap()) + gapSizeBetweenCells;
	}

	private double cellWidthIncludingGap() {
		return cellWidth() + gapSizeBetweenCells;
	}
	
	private double cellStartY(int row) {
		return startPoint(row, cellHeightIncludingGap()) + gapSizeBetweenCells;
	}

	private double cellHeightIncludingGap() {
		return cellHeight() + gapSizeBetweenCells;
	}

	private double cellWidth() {
		return ((this.cellCanvas.getWidth() - totalGapBetweenAllCells()) / this.dimension);
	}
	
	private double cellHeight() {
		return ((this.cellCanvas.getHeight() - totalGapBetweenAllCells()) / this.dimension);
	}
	
	private int totalGapBetweenAllCells() {
		return (dimension + 1) * gapSizeBetweenCells;
	}
}