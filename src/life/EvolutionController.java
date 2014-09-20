package life;

public interface EvolutionController {

	void addEvolveListener(final EvolutionControllerListener timerListener);

	void begin();

	void stop();
}