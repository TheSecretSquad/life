package lifeUI;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class GUICell extends JPanel{

	private static final long serialVersionUID = -5427277804823810581L;
	
	public GUICell(boolean isAlive) {
		if(isAlive)
			this.alive();
		else
			this.dead();
		this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
	}
	
	public void alive() {
		this.setBackground(Color.WHITE);
	}
	
	public void dead() {
		this.setBackground(Color.BLACK);
	}
}