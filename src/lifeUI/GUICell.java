package lifeUI;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GUICell {

	private static final Color aliveColor = Color.WHITE;
	private static final Color survivedColor = new Color(227, 217, 78);
	private static final Color deadColor = Color.BLACK;
	private static final Color bornColor = new Color(227, 128, 78);
	private JPanel panel;
	
	public GUICell(boolean isAlive, JPanel panel) {
		this.panel = panel;
		if(isAlive)
			this.alive();
		else
			this.dead();
		this.panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
	}
	
	public void alive() {
		if(this.panel.getBackground().equals(aliveColor))
			this.panel.setBackground(survivedColor);
		else {
			this.panel.setBackground(new Color(155, 227, 78));
			Timer t = new Timer(100, e -> 
				this.panel.setBackground(aliveColor));
			t.setRepeats(false);
			t.start();
		}
	}
	
	public void dead() {
		if(this.panel.getBackground().equals(aliveColor)) {
			this.panel.setBackground(bornColor);
			Timer t = new Timer(100, e -> 
				this.panel.setBackground(deadColor));
			t.setRepeats(false);
			t.start();
		}
		else
			this.panel.setBackground(deadColor);
	}
}