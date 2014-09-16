package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import life.Cell;

import org.junit.Before;
import org.junit.Test;

public class CellTest {

	private Cell cell1_1;
	private Set<Cell> neighborsOf1_1;
	
	@Before
	public void setUp() throws Exception {
		this.cell1_1 = new Cell(1, 1);
		this.neighborsOf1_1 = new HashSet<Cell>();
		this.neighborsOf1_1.addAll(Arrays.asList(new Cell[] {
				new Cell(1, 2),	new Cell(1, 0),	new Cell(0, 1),	new Cell(2, 1),
				new Cell(0, 2),	new Cell(2, 2),	new Cell(0, 0), new Cell(2, 0)
		}));
	}
	
	private void assertNeighborsCorrect(Set<Cell> expected, Set<Cell> actual) {
		assertTrue(expected.containsAll(actual));
	}
	
	@Test
	public void ShouldBeEqualWithSameCoordinates() {
		assertTrue(this.cell1_1.equals(new Cell(1, 1)));
	}
	
	@Test
	public void ShouldBeUnEqualWithDifferentCoordinates() {
		assertFalse(this.cell1_1.equals(new Cell(2, 2)));
	}
	
	@Test
	public void ShouldBeUnEqualWithDifferentXCoordinate() {
		assertFalse(this.cell1_1.equals(new Cell(2, 1)));
	}
	
	@Test
	public void ShouldBeUnEqualWithDifferentYCoordinate() {
		assertFalse(this.cell1_1.equals(new Cell(1, 2)));
	}
	
	@Test
	public void ShouldProvideItsNeighbors() {
		this.assertNeighborsCorrect(this.cell1_1.provideNeighbors(), this.neighborsOf1_1);
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
		assertTrue(results.containsAll(Arrays.asList(expected)));
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