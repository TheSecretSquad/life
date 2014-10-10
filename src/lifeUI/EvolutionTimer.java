package lifeUI;

import java.util.HashSet;
import java.util.Set;

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
		this.timeline = new Timeline(new KeyFrame(Duration.millis(100), (ae) -> {
        	this.listeners.forEach(EvolutionControllerListener::evolve);
        }));
		this.timeline.setCycleCount(1);
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
		if(this.timeline != null) {
			this.timeline.play();
			this.timeline.jumpTo(Duration.ZERO);
		}
	}
}