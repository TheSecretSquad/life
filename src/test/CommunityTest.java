package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import working.Cell;
import working.CellLifeStage;
import working.Cells;
import working.Community;

@RunWith(MockitoJUnitRunner.class)
public class CommunityTest {

	@Mock
	private CellLifeStage liveStage;
	@Mock
	private CellLifeStage deadStage;

	private int communitySize;
	
	private Cell initiallyDeadCellOne;
	private Cell initiallyDeadCellTwo;
	private Cell initiallyLiveCellOne;
	private Cell initiallyLiveCellTwo;
	private Set<Cell> initialCells;
	private Community community;
	
	@Before
	public void setUp() throws Exception {
		this.communitySize = 4;
		this.initiallyDeadCellOne = new Cell(2, 1);
		this.initiallyDeadCellTwo = new Cell(2, 2);
		this.initiallyLiveCellOne = new Cell(1, 1);
		this.initiallyLiveCellTwo = new Cell(1, 2);
		this.initialCells= new HashSet<>(Arrays.asList(new Cell[] {initiallyLiveCellOne, initiallyLiveCellTwo}));
		when(this.liveStage.evaluate(isA(Cell.class), isA(Cells.class))).thenReturn(this.liveStage);
		when(this.deadStage.evaluate(isA(Cell.class), isA(Cells.class))).thenReturn(this.deadStage);
		this.community = new Community(communitySize, this.deadStage, this.liveStage, this.initialCells);
	}
	
	@Test
	public void ShouldHaveLiveCellsEqualToInitialCells() {
		assertTrue(this.community.isAlive(new Cell(1, 1)));
		assertTrue(this.community.isAlive(new Cell(1, 2)));
	}
	
	@Test
	public void ShouldCreateANewCommunityWhenTicked() {
		Community nextCommunity = community.tick();
		assertNotNull(nextCommunity);
	}
	
	@Test
	public void ShouldEvaluateNextLifeStageOfLiveCellsWhenTicked() {
		this.community.tick();
		verify(this.liveStage, times(1)).evaluate(eq(this.initiallyLiveCellOne), isA(Cells.class));
		verify(this.liveStage, times(1)).evaluate(eq(this.initiallyLiveCellTwo), isA(Cells.class));
	}
	
	@Test
	public void ShouldEvaluateNextLifeStageOfDeadCellsWhenTicked() {
		community.tick();
		verify(this.deadStage, times(1)).evaluate(eq(initiallyDeadCellOne), isA(Cells.class));
		verify(this.deadStage, times(1)).evaluate(eq(initiallyDeadCellTwo), isA(Cells.class));
	}
	
	@Test
	public void GivenALiveCellThatDies_ShouldBeDeadInNextCommunityWhenTicked() {
		when(this.liveStage.evaluate(eq(this.initiallyLiveCellOne), isA(Cells.class))).thenReturn(this.deadStage);
		Community nextCommunity = community.tick();
		assertFalse(nextCommunity.isAlive(this.initiallyLiveCellOne));
	}
	
	@Test
	public void GivenALiveCellThatSurvives_ShouldRemainAliveInNextCommunity() {
		when(this.liveStage.evaluate(eq(this.initiallyLiveCellOne), isA(Cells.class))).thenReturn(this.liveStage);
		Community nextCommunity = community.tick();
		assertTrue(nextCommunity.isAlive(this.initiallyLiveCellOne));
	}
	
	@Test
	public void GivenADeadCellThatStaysDead_ShouldRemainDeadInNextCommunity() {
		when(this.deadStage.evaluate(eq(this.initiallyDeadCellOne), isA(Cells.class))).thenReturn(this.deadStage);
		Community nextCommunity = community.tick();
		assertFalse(nextCommunity.isAlive(this.initiallyDeadCellOne));
	}
	
	@Test
	public void GivenADeadCellThatIsBorn_ShouldBeALiveInNextCommunity() {
		when(this.deadStage.evaluate(eq(this.initiallyDeadCellOne), isA(Cells.class))).thenReturn(this.liveStage);
		Community nextCommunity = community.tick();
		assertTrue(nextCommunity.isAlive(this.initiallyDeadCellOne));
	}
}