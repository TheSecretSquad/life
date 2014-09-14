package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import working.Cell;
import working.CellLifeStage;
import working.Cells;
import working.ConwaysDefaultLifeStage;
import working.ConwaysLivingCellLifeStage;

@RunWith(MockitoJUnitRunner.class)
public class ConwaysLiveCellStageTest {

	@Mock
	private Cells cells;
	private Cell anyCell;
	private CellLifeStage conwaysLivingCellLifeStage;
	private CellLifeStage conwaysDeadCellLifeStage;
		
	@Before
	public void setUp() throws Exception {
		this.conwaysLivingCellLifeStage = new ConwaysLivingCellLifeStage();
		this.conwaysDeadCellLifeStage = ConwaysDefaultLifeStage.DEAD;
		this.anyCell = new Cell(1, 1);
	}

	private void assertLivingCellDies() {
		CellLifeStage nextLifeStage = this.conwaysLivingCellLifeStage.evaluate(this.anyCell, this.cells);
		assertEquals(ConwaysDefaultLifeStage.DEAD, nextLifeStage);
	}
	
	private void assertLivingCellSurvives() {
		CellLifeStage nextLifeStage = this.conwaysLivingCellLifeStage.evaluate(this.anyCell, this.cells);
		assertEquals(this.conwaysLivingCellLifeStage, nextLifeStage);
	}
	
	private void assertDeadCellRemainsDead() {
		CellLifeStage nextLifeStage = this.conwaysDeadCellLifeStage.evaluate(this.anyCell, this.cells);
		assertEquals(ConwaysDefaultLifeStage.DEAD, nextLifeStage);
	}

//	private void setupWhenLiveNeighborCountOfMaxNeighbors(int numberOfLiveNeighbors) {
//		int maxNumberOfNeighbors = 8;
//		if(numberOfLiveNeighbors < 0 || numberOfLiveNeighbors > maxNumberOfNeighbors || maxNumberOfNeighbors < numberOfLiveNeighbors)
//			throw new IllegalArgumentException("Number of live neighbors requested: " + numberOfLiveNeighbors + ", Max number of neighbors: " + maxNumberOfNeighbors);
//		
//		boolean thenFirstNeighbor = false;
//		Boolean[] thenRestOfNeighbors = new Boolean[maxNumberOfNeighbors];
//		Arrays.fill(thenRestOfNeighbors, false);
//		
//		if(numberOfLiveNeighbors > 0) {
//			thenFirstNeighbor = true;
//		}
//		
//		if(numberOfLiveNeighbors > 1) {
//			Arrays.fill(thenRestOfNeighbors, true);
//			Arrays.fill(thenRestOfNeighbors, numberOfLiveNeighbors - 1, maxNumberOfNeighbors - 1, false);
//		}
//	
//		when(this.cells.isAlive(isA(Cell.class))).thenReturn(thenFirstNeighbor, thenRestOfNeighbors);
//	}
	
	@Test
	public void ShouldNotReturnNullLifeStage() {
		CellLifeStage nextLifeStage = this.conwaysLivingCellLifeStage.evaluate(this.anyCell, this.cells);
		assertNotNull(nextLifeStage);
	}
	
	@Test
	public void GivenCellsWithZeroNeighbors_ALiveCellShouldBecomeDead() {
		when(this.cells.isAlive(isA(Cell.class))).thenReturn(false);
		assertLivingCellDies();
	}
	
	@Test
	public void GivenCellsWithOneLiveNeighbor_ALiveCellShouldBecomeDead() {
		when(this.cells.isAlive(isA(Cell.class))).thenReturn(true, false, false, false, false, false, false, false);
		assertLivingCellDies();
	}
	
	@Test
	public void GivenCellsWithTwoLiveNeighbors_ALiveCellShouldSurvive() {
		when(this.cells.isAlive(isA(Cell.class))).thenReturn(true, true, false, false, false, false, false, false);
		assertLivingCellSurvives();
	}
	
	@Test
	public void GivenCellsWithThreeLiveNeighbors_ALiveCellShouldSurvive() {
		when(this.cells.isAlive(isA(Cell.class))).thenReturn(true, true, true, false, false, false, false, false);
		assertLivingCellSurvives();
	}

	@Test
	public void GivenCellsWithFourLiveNeighbors_ALiveCellShouldDie() {
		when(this.cells.isAlive(isA(Cell.class))).thenReturn(true, true, true, true, false, false, false, false);
		assertLivingCellDies();
	}
	
	@Test
	public void GivenCellsWithZeroLiveNeighbors_ADeadCellShouldStayDead() {
		when(this.cells.isAlive(isA(Cell.class))).thenReturn(false, false, false, false, false, false, false, false);
		assertDeadCellRemainsDead();
	}
}