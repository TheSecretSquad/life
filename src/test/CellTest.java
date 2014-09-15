package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import life.Cell;

import org.junit.Assert;
import org.junit.Test;

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
	
	@Test
	public void RangeDoShouldForCell1_1ToCell3_3ShouldReturnAllCellsBetweenStartAndFinishInclusive() {
		Cell[] expected = new Cell[] {
				new Cell(1, 1),
				new Cell(1, 2),
				new Cell(1, 3),
				new Cell(2, 1),
				new Cell(2, 2),
				new Cell(2, 3),
				new Cell(3, 1),
				new Cell(3, 2),
				new Cell(3, 3)
				};
		
		Collection<Cell> results = new ArrayList<>();
		Cell.rangeDo(new Cell(1, 1), new Cell(3, 3), (Cell c) -> results.add(c));
		Assert.assertArrayEquals(expected, results.toArray(new Cell[expected.length]));
	}
	
	@Test
	public void RangeDoShouldForCell1_1ToCell3_3ShouldNotContainCellsOutsideTheRange() {
		Collection<Cell> results = new ArrayList<>();
		Cell.rangeDo(new Cell(1, 1), new Cell(3, 3), (Cell c) -> results.add(c));
		assertFalse(results.contains(new Cell(0, 0)));
		assertFalse(results.contains(new Cell(1, 0)));
		assertFalse(results.contains(new Cell(0, 1)));
		assertFalse(results.contains(new Cell(4, 4)));
		assertFalse(results.contains(new Cell(3, 4)));
		assertFalse(results.contains(new Cell(4, 3)));
	}
}