package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import working.Cell;

public class CellTest {

	@Test
	public void ShouldBeEqualWithSameCoordinates() {
		assertTrue(new Cell(1, 1).equals(new Cell(1, 1)));
	}
	
	@Test
	public void ShouldBeUnEqualWithDifferentCoordinates() {
		assertFalse(new Cell(1, 1).equals(new Cell(2, 2)));
	}
	
	@Test
	public void ShouldBeUnEqualWithDifferentXCoordinate() {
		assertFalse(new Cell(1, 1).equals(new Cell(2, 1)));
	}
	
	@Test
	public void ShouldBeUnEqualWithDifferentYCoordinate() {
		assertFalse(new Cell(1, 1).equals(new Cell(1, 2)));
	}
	
	@Test
	public void ShouldReturnCellX_YPlus1WhenUp() {
		assertEquals(new Cell(1, 2), new Cell(1, 1).up());
	}
	
	@Test
	public void ShouldReturnCellX_YMinus1WhenDown() {
		assertEquals(new Cell(1, 0), new Cell(1, 1).down());
	}
	
	@Test
	public void ShouldReturnCellXMinus1YWhenLeft() {
		assertEquals(new Cell(0, 1), new Cell(1, 1).left());
	}
	
	@Test
	public void ShouldReturnCellXPlus1YWhenRight() {
		assertEquals(new Cell(2, 1), new Cell(1, 1).right());
	}
}