package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;
import life.Cell;
import life.CellLifeStage;
import life.Cells;
import life.ConwaysDefaultLifeStage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConwaysCellStageTest {

	@Mock
	private Cells cells;
	private Cell evaluatingCell;
	private Cell neighborOne;
	private CellLifeStage conwaysLivingCellLifeStage;
	private CellLifeStage conwaysDeadCellLifeStage;
	private Cell neighborTwo;
	private Cell neighborThree;
	private Cell neighborFour;
		
	@Before
	public void setUp() throws Exception {
		this.conwaysLivingCellLifeStage = ConwaysDefaultLifeStage.ALIVE;
		this.conwaysDeadCellLifeStage = ConwaysDefaultLifeStage.DEAD;
		this.evaluatingCell = new Cell(2, 2);
		this.neighborOne = new Cell(1, 2);
		this.neighborTwo = new Cell(3, 2);
		this.neighborThree = new Cell(2, 3);
		this.neighborFour = new Cell(2, 1);
	}

	private void assertLivingCellDies() {
		CellLifeStage nextLifeStage = this.conwaysLivingCellLifeStage.evaluate(this.evaluatingCell, this.cells);
		assertEquals(ConwaysDefaultLifeStage.DEAD, nextLifeStage);
	}
	
	private void assertLivingCellSurvives() {
		CellLifeStage nextLifeStage = this.conwaysLivingCellLifeStage.evaluate(this.evaluatingCell, this.cells);
		assertEquals(this.conwaysLivingCellLifeStage, nextLifeStage);
	}
	
	private void assertDeadCellRemainsDead() {
		CellLifeStage nextLifeStage = this.conwaysDeadCellLifeStage.evaluate(this.evaluatingCell, this.cells);
		assertEquals(ConwaysDefaultLifeStage.DEAD, nextLifeStage);
	}
	
	private void assertDeadCellIsBorn() {
		CellLifeStage nextLifeStage = this.conwaysDeadCellLifeStage.evaluate(this.evaluatingCell, this.cells);
		assertEquals(ConwaysDefaultLifeStage.ALIVE, nextLifeStage);
	}
	
	@Test
	public void ShouldNotReturnNullLifeStage() {
		CellLifeStage nextLifeStage = this.conwaysLivingCellLifeStage.evaluate(this.evaluatingCell, this.cells);
		assertNotNull(nextLifeStage);
	}
	
	@Test
	public void GivenCellsWithZeroNeighbors_ALiveCellShouldBecomeDead() {
		when(this.cells.isAlive(isA(Cell.class))).thenReturn(false);
		assertLivingCellDies();
	}
	
	@Test
	public void GivenCellsWithOneLiveNeighbor_ALiveCellShouldBecomeDead() {
		when(this.cells.isAlive(eq(this.neighborOne))).thenReturn(true);
		assertLivingCellDies();
	}
	
	@Test
	public void GivenCellsWithTwoLiveNeighbors_ALiveCellShouldSurvive() {
		when(this.cells.isAlive(eq(this.neighborOne))).thenReturn(true);
		when(this.cells.isAlive(eq(this.neighborTwo))).thenReturn(true);
		assertLivingCellSurvives();
	}
	
	@Test
	public void GivenCellsWithThreeLiveNeighbors_ALiveCellShouldSurvive() {
		when(this.cells.isAlive(eq(this.neighborOne))).thenReturn(true);
		when(this.cells.isAlive(eq(this.neighborTwo))).thenReturn(true);
		when(this.cells.isAlive(eq(this.neighborThree))).thenReturn(true);
		assertLivingCellSurvives();
	}

	@Test
	public void GivenCellsWithFourLiveNeighbors_ALiveCellShouldDie() {
		when(this.cells.isAlive(eq(this.neighborOne))).thenReturn(true);
		when(this.cells.isAlive(eq(this.neighborTwo))).thenReturn(true);
		when(this.cells.isAlive(eq(this.neighborThree))).thenReturn(true);
		when(this.cells.isAlive(eq(this.neighborFour))).thenReturn(true);
		assertLivingCellDies();
	}
	
	@Test
	public void GivenCellsWithThreeLiveNeighbors_ADeadCellShouldBecomeAlive() {
		when(this.cells.isAlive(eq(this.neighborOne))).thenReturn(true);
		when(this.cells.isAlive(eq(this.neighborTwo))).thenReturn(true);
		when(this.cells.isAlive(eq(this.neighborThree))).thenReturn(true);
		assertDeadCellIsBorn();
	}
	
	@Test
	public void GivenCellsWithLessThanThreeLiveNeighbors_ADeadCellShouldStayDead() {
		when(this.cells.isAlive(eq(this.neighborOne))).thenReturn(true);
		assertDeadCellRemainsDead();
	}
	
	@Test
	public void GivenCellsWithMoreThanThreeLiveNeighbors_ADeadCellShouldStayDead() {
		when(this.cells.isAlive(eq(this.neighborOne))).thenReturn(true);
		when(this.cells.isAlive(eq(this.neighborTwo))).thenReturn(true);
		when(this.cells.isAlive(eq(this.neighborThree))).thenReturn(true);
		when(this.cells.isAlive(eq(this.neighborFour))).thenReturn(true);
		assertDeadCellRemainsDead();
	}
}