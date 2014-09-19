package life;

public class Game {

	private Community community;
	private CommunityPublisher communityPublisher;
	private int numberOfEvolutions;
	private int numberOfEvolutionsPassed = 0;
	private EvolutionController evolutionController;
	
	public Game(Community community, CommunityPublisher communityPublisher, int numberOfEvolutions, EvolutionController evolutionController) {
		this.community = community;
		this.communityPublisher = communityPublisher;
		this.numberOfEvolutions = numberOfEvolutions;
		this.evolutionController = evolutionController;
		evolutionController.addEvolveListener(() -> {
			Game.this.nextGeneration();
		});
	}
	
	public void start() {
		this.evolutionController.begin();
	}
	
	private void nextGeneration() {
		if(this.numberOfEvolutionsPassed >= this.numberOfEvolutions) {
			this.evolutionController.stop();
			return;
		}
		
		publishCurrentCommunity();
		updateCommunity();
	}

	private void publishCurrentCommunity() {
		this.community.asPublishable().publishTo(this.communityPublisher);
	}

	private void updateCommunity() {
		this.community = community.tick();
		++this.numberOfEvolutionsPassed;
	}
}