package lifeUI;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

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
		if(this.getBackground().equals(Color.WHITE))
			this.setBackground(new Color(227, 217, 78));
		else {
			this.setBackground(new Color(155, 227, 78));
			Timer t = new Timer(100, e -> 
				this.setBackground(Color.WHITE));
			t.setRepeats(false);
			t.start();
		}
	}
	
	public void dead() {
		if(this.getBackground().equals(Color.WHITE)) {
			this.setBackground(new Color(227, 128, 78));
			Timer t = new Timer(100, e -> 
				this.setBackground(Color.BLACK));
			t.setRepeats(false);
			t.start();
		}
	}
}