package test;

import static org.junit.Assert.assertEquals;
import lifeUI.CellDisplayState;

import org.junit.Before;
import org.junit.Test;

public class CellDisplayStateTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void ShouldBeBornFromKilledWhenNextLivingState() {
		assertEquals(CellDisplayState.BORN, CellDisplayState.KILLED.nextLivingState());
	}
	
	@Test
	public void ShouldBeBornFromDeadWhenNextLivingState() {
		assertEquals(CellDisplayState.BORN, CellDisplayState.DEAD.nextLivingState());
	}
	
	@Test
	public void ShouldBeAliveFromBornWhenNextLivingState() {
		assertEquals(CellDisplayState.ALIVE, CellDisplayState.BORN.nextLivingState());
	}
	
	@Test
	public void ShouldBeSurvivedFromAliveWhenNextLivingState() {
		assertEquals(CellDisplayState.SURVIVED, CellDisplayState.ALIVE.nextLivingState());
	}
	
	@Test
	public void ShouldBeSurvivedFromSurvivedWhenNextLivingState() {
		assertEquals(CellDisplayState.SURVIVED, CellDisplayState.SURVIVED.nextLivingState());
	}
	
	@Test
	public void ShouldBeKilledFromBornWhenNextDeadState() {
		assertEquals(CellDisplayState.KILLED, CellDisplayState.BORN.nextDeadState());
	}
	
	@Test
	public void ShouldBeKilledFromAliveWhenNextDeadState() {
		assertEquals(CellDisplayState.KILLED, CellDisplayState.ALIVE.nextDeadState());
	}
	
	@Test
	public void ShouldBeKilledFromSurvivedWhenNextDeadState() {
		assertEquals(CellDisplayState.KILLED, CellDisplayState.SURVIVED.nextDeadState());
	}
	
	@Test
	public void ShouldBeDeadFromKilledWhenNextDeadState() {
		assertEquals(CellDisplayState.DEAD, CellDisplayState.KILLED.nextDeadState());
	}
	
	@Test
	public void ShouldBeDeadFromDeadWhenNextDeadState() {
		assertEquals(CellDisplayState.DEAD, CellDisplayState.DEAD.nextDeadState());
	}
}