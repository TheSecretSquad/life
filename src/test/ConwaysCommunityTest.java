package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import life.Cell;
import life.CellLifeStage;
import life.Cells;
import life.ConwaysCommunity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConwaysCommunityTest {

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
	private ConwaysCommunity community;
	
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
		this.community = new ConwaysCommunity(communitySize, this.deadStage, this.liveStage, this.initialCells);
	}
	
	private ConwaysCommunity createCommunityWithDefaultSizeAndInitialCells(Set<Cell> initialCells) {
		return this.createCommunityWithSizeAndInitialCells(this.communitySize, initialCells);
	}
	
	private ConwaysCommunity createCommunityWithSizeAndInitialCells(int communitySize, Set<Cell> initialCells) {
		return new ConwaysCommunity(communitySize, this.deadStage, this.liveStage, initialCells);
	}
	
	private ConwaysCommunity copyOfInitialCommunityRemovingCell(Cell cell) {
		return this.createCommunityWithDefaultSizeAndInitialCells(copyInitialCellsRemovingCell(cell));
	}
	
	private ConwaysCommunity copyOfInitialCommunityAddingCell(Cell cell) {
		return this.createCommunityWithDefaultSizeAndInitialCells(copyInitialCellsAddingCell(cell));
	}
	
	private Set<Cell> copyInitialCellsRemovingCell(Cell cell) {
		Set<Cell> nextCells = new HashSet<>(this.initialCells);
		nextCells.remove(cell);
		return nextCells;
	}
	
	private Set<Cell> copyInitialCellsAddingCell(Cell cell) {
		Set<Cell> nextCells = new HashSet<>(this.initialCells);
		nextCells.add(cell);
		return nextCells;
	}
	
	@Test
	public void ShouldHaveLiveCellsEqualToInitialCells() {
		assertTrue(this.community.isAlive(new Cell(1, 1)));
		assertTrue(this.community.isAlive(new Cell(1, 2)));
	}
	
	@Test
	public void ShouldCopyInitialCells() {
		ConwaysCommunity communityOne = createCommunityWithDefaultSizeAndInitialCells(this.initialCells);
		this.initialCells.remove(this.initiallyLiveCellOne);
		ConwaysCommunity communityTwo = createCommunityWithDefaultSizeAndInitialCells(this.initialCells);
		assertNotEquals(communityOne, communityTwo);
	}
	
	@Test
	public void ShouldBeEqualToAnotherCommunityWithSameSizeAndCellStages() {
		ConwaysCommunity communityOne = this.createCommunityWithDefaultSizeAndInitialCells(this.initialCells);
		Set<Cell> sameCells = new HashSet<>(this.initialCells);
		ConwaysCommunity communityTwo = this.createCommunityWithDefaultSizeAndInitialCells(sameCells);
		assertEquals(communityOne, communityTwo);
	}
	
	@Test
	public void ShouldNotBeEqualToAnotherCommunityWithDifferentSize() {
		ConwaysCommunity communityOne = this.createCommunityWithDefaultSizeAndInitialCells(this.initialCells);
		ConwaysCommunity communityTwo = this.createCommunityWithSizeAndInitialCells(this.communitySize - 1, this.initialCells);
		assertNotEquals(communityOne, communityTwo);
	}
	
	@Test
	public void ShouldNotBeEqualToAnotherCommunityWithDifferentCellStages() {
		Set<Cell> differentInitialCells = new HashSet<>(Arrays.asList(new Cell[] {initiallyLiveCellOne}));
		ConwaysCommunity communityOne = this.createCommunityWithDefaultSizeAndInitialCells(this.initialCells);
		ConwaysCommunity communityTwo = this.createCommunityWithDefaultSizeAndInitialCells(differentInitialCells);
		assertNotEquals(communityOne, communityTwo);
	}
	
	@Test
	public void ShouldCreateANewCommunityWhenTicked() {
		ConwaysCommunity nextCommunity = community.tick();
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
		ConwaysCommunity nextCommunity = community.tick();
		assertEquals(nextCommunity, copyOfInitialCommunityRemovingCell(this.initiallyLiveCellOne));
	}

	@Test
	public void GivenALiveCellThatSurvives_ShouldRemainAliveInNextCommunity() {
		when(this.liveStage.evaluate(eq(this.initiallyLiveCellOne), isA(Cells.class))).thenReturn(this.liveStage);
		ConwaysCommunity nextCommunity = community.tick();
		assertEquals(nextCommunity, this.createCommunityWithDefaultSizeAndInitialCells(this.initialCells));
	}
	
	@Test
	public void GivenADeadCellThatStaysDead_ShouldRemainDeadInNextCommunity() {
		when(this.deadStage.evaluate(eq(this.initiallyDeadCellOne), isA(Cells.class))).thenReturn(this.deadStage);
		ConwaysCommunity nextCommunity = community.tick();
		assertEquals(nextCommunity, this.createCommunityWithDefaultSizeAndInitialCells(this.initialCells));
	}
	
	@Test
	public void GivenADeadCellThatIsBorn_ShouldBeALiveInNextCommunity() {
		when(this.deadStage.evaluate(eq(this.initiallyDeadCellOne), isA(Cells.class))).thenReturn(this.liveStage);
		ConwaysCommunity nextCommunity = community.tick();
		assertEquals(nextCommunity, copyOfInitialCommunityAddingCell(this.initiallyDeadCellOne));
	}
	
	@Test
	public void ShouldReturnNewPublishableCommunityForEachNewCommunity() {
		assertSame(community.asPublishable(), community.asPublishable());
		assertNotSame(community.asPublishable(), community.tick().asPublishable());
	}
	
	@Test
	public void ShouldNotBeAliveForCellsOutsideOfTheCommunity() {
		assertFalse(this.community.isAlive(new Cell(5, 5)));
	}
}