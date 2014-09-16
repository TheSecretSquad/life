package lifeUI;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GUICell {

	private static final Color bornColor = new Color(155, 227, 78);
	private static final Color aliveColor = Color.WHITE;
	private static final Color survivingColor = new Color(227, 217, 78);
	private static final Color deadColor = Color.BLACK;
	private static final Color dyingColor = new Color(227, 128, 78);
	private JPanel panel;
	
	public GUICell(JPanel panel) {
		this.panel = panel;
		this.panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		this.displayColor(deadColor);
	}
	
	public void alive() {
		if(this.isDisplayingColor(survivingColor))
			return;
		
		if(this.isDisplayingColor(aliveColor) || this.isDisplayingColor(bornColor)) {
			this.displayColor(survivingColor);
			return;
		}
		
		this.displayColor(bornColor);
		this.displayAfterMilliSecTimeOut(aliveColor, 100);

	}

	public void dead() {
		if(this.isDisplayingColor(deadColor))
			return;
		
		this.displayColor(dyingColor);
		this.displayAfterMilliSecTimeOut(deadColor, 100);
	}

	private boolean isDisplayingColor(Color color) {
		return this.panel.getBackground().equals(color);
	}
	
	private void displayColor(Color color) {
		this.panel.setBackground(color);
	}
	
	private void displayAfterMilliSecTimeOut(Color color, int milliseconds) {
		Timer t = new Timer(milliseconds, e -> this.displayColor(color));
		t.setRepeats(false);
		t.start();
	}
}