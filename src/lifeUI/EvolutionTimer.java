package lifeUI;

import java.util.HashSet;
import java.util.Set;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import life.EvolutionController;
import life.EvolutionControllerListener;

public class EvolutionTimer implements EvolutionController, UpdateCompleteListener {

	private final Set<EvolutionControllerListener> listeners = new HashSet<>();
	private Timeline timeline;

	@Override
	public void addEvolveListener(EvolutionControllerListener timerListener) {
		this.listeners.add(timerListener);		
	}

	@Override
	public void begin() {
		this.updateComplete();
	}

	@Override
	public void stop() {
		if(this.timeline == null)
			return;
		
		this.timeline.stop();
		this.timeline = null;
	}

	@Override
	public void updateComplete() {
		this.timeline = new Timeline(new KeyFrame(Duration.millis(10), (ae) -> {
        	this.listeners.forEach((ecl) -> {
        		ecl.evolve();
        	});
        }));
		this.timeline.setCycleCount(1);
		this.timeline.play();
    	System.out.println("update");
	}
}