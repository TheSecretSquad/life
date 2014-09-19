package lifeUI;

import javafx.scene.layout.Pane;

public class GUICell {

	private Pane cellPane;
	private String deadStyle = "-fx-background-color: black; " + Grid.borderStyle;
	private String aliveStyle = "-fx-background-color: white; " + Grid.borderStyle;
	
	public GUICell(Pane cellPane) {
		this.cellPane = cellPane;
	}
	
	public void showAlive() {
		this.cellPane.setStyle(aliveStyle);
	}
	
	public void showDead() {
		this.cellPane.setStyle(deadStyle);
	}
}