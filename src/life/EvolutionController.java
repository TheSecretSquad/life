package life;

public interface EvolutionController {

	void addEvolveListener(EvolutionControllerListener timerListener);

	void begin();

	void stop();
}