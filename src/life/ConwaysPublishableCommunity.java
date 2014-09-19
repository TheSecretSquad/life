package life;

import java.util.Set;

public class ConwaysPublishableCommunity implements PublishableCommunity {

	private final Set<Cell> livingCells;
	
	public ConwaysPublishableCommunity(Set<Cell> livingCells) {
		this.livingCells = livingCells;
	}
	
	@Override
	public void publishTo(CommunityPublisher communityPublisher) {
		communityPublisher.publishLiving(this.livingCells);
	}
}