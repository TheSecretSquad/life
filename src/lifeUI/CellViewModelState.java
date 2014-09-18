package lifeUI;

public enum CellViewModelState {
	BORN,
	ALIVE,
	SURVIVING,
	KILLED,
	DEAD;
	
	public CellViewModelState nextLivingState() {
		switch(this) {
		case DEAD:
		case KILLED:
			return BORN;
		case BORN:
			return ALIVE;
		default:
			return SURVIVING;
		}
	}
	
	public CellViewModelState nextDeadState() {
		switch(this) {
		case BORN:
		case ALIVE:
		case SURVIVING:
			return KILLED;
		default:
			return DEAD;
		}
	}
}