package lifeUI;

import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import life.Cell;
import life.DisplayGrid;

public class GUIGrid extends JFrame implements DisplayGrid{

	private static final long serialVersionUID = -7502210004900623683L;
	
	private Map<Cell, GUICell> allCells;
	
	public GUIGrid(int dimension) {
		this.allCells = new HashMap<>();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(600, 600);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(dimension, dimension));
		
		Cell.rangeDo(new Cell(1, 1), new Cell(dimension, dimension), (Cell c) -> {
			GUICell newCell = new GUICell(false);
			this.allCells.put(c, newCell);
			panel.add(newCell);
		});
		this.add(panel);
	}

	@Override
	public void alive(Cell cell) {
		if(this.allCells.containsKey(cell))
			this.allCells.get(cell).alive();
	}

	@Override
	public void dead(Cell cell) {
		if(this.allCells.containsKey(cell))
			this.allCells.get(cell).dead();
	}
}