package test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import life.Community;
import life.DisplayGrid;
import life.Game;
import life.PublishableCommunity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GameTest {

	@Mock
	private Community community;
	@Mock
	private Community resultOfTickCommunity;
	@Mock
	private DisplayGrid grid;
	@Mock
	private PublishableCommunity publishableCommunity;
	private Game game;

	@Before
	public void setUp() throws Exception {
		this.game = new Game(this.community, this.grid);
		when(this.community.asPublishable()).thenReturn(this.publishableCommunity);
		when(this.resultOfTickCommunity.asPublishable()).thenReturn(this.publishableCommunity);
	}

	@Test
	public void ShouldTickCommunityWhenStarted() {
		this.game.start(1);
		verify(this.community, times(1)).tick();
	}
	
	@Test
	public void ShouldUseNextGenerationForSubequentTicks() {
		when(this.community.tick()).thenReturn(this.resultOfTickCommunity);
		this.game.start(2);
		verify(this.community, times(1)).tick();
		verify(this.resultOfTickCommunity, times(1)).tick();
	}
	
	@Test
	public void ShouldReportLivingCellsToTheGrid() {
		this.game.start(1);
		verify(this.publishableCommunity).publishTo(this.grid);
	}
}