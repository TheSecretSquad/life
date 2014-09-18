package test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import lifeUI.CellStateListener;
import lifeUI.CellViewModel;
import lifeUI.CellViewModelState;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CellViewModelTest {

	@Mock
	private CellStateListener cellStateListener;
	private CellViewModel cellViewModel;
	
	@Before
	public void setUp() throws Exception {
		this.cellViewModel = new CellViewModel(CellViewModelState.ALIVE);
		this.cellViewModel.makeListener(this.cellStateListener);
	}

	private void assertFollowsDeadStates(CellViewModel cvm) {
		cvm.dead();
		assertEquals(CellViewModelState.KILLED, cvm.cellState());
		cvm.dead();
		assertEquals(CellViewModelState.DEAD, cvm.cellState());
		cvm.dead();
		assertEquals(CellViewModelState.DEAD, cvm.cellState());
	}

	private CellViewModel createCellViewModelWithCellState(CellViewModelState cellViewModelState) {
		CellViewModel cvm = new CellViewModel(cellViewModelState);
		cvm.makeListener(this.cellStateListener);
		return cvm;
	}
	
	@Test
	public void ShouldNotifyListenersWhenAlive() {
		this.cellViewModel.alive();
		verify(this.cellStateListener).update();
	}
	
	@Test
	public void ShouldNotifyListenersWhenDead() {
		this.cellViewModel.dead();
		verify(this.cellStateListener).update();
	}
	
	@Test
	public void ShouldTransitionLivingStatesWhenAlive() {
		CellViewModel cvm = createCellViewModelWithCellState(CellViewModelState.DEAD);
		cvm.alive();
		assertEquals(CellViewModelState.BORN, cvm.cellState());
		cvm.alive();
		assertEquals(CellViewModelState.ALIVE, cvm.cellState());
		cvm.alive();
		assertEquals(CellViewModelState.SURVIVING, cvm.cellState());
		cvm.alive();
		assertEquals(CellViewModelState.SURVIVING, cvm.cellState());
	}
	
	@Test
	public void ShouldTransitionDeadStatesWhenDead() {
		CellViewModel cvm = createCellViewModelWithCellState(CellViewModelState.ALIVE);
		assertFollowsDeadStates(cvm);
		cvm = createCellViewModelWithCellState(CellViewModelState.BORN);
		assertFollowsDeadStates(cvm);
	}
}