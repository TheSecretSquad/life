package lifeUI;

public enum CellDisplayState {
	BORN,
	ALIVE,
	SURVIVED,
	KILLED,
	DEAD;
	
	public CellDisplayState nextLivingState() {
		switch(this) {
			case KILLED:
			case DEAD:
				return BORN;
			case BORN:
				return ALIVE;
			default:
				return SURVIVED;
		}
	}
	
	public CellDisplayState nextDeadState() {
		switch(this) {
		case BORN:
		case ALIVE:
		case SURVIVED:
			return KILLED;
		default:
			return DEAD;
		}
	}
}