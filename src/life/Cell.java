package life;

import java.util.function.Consumer;
import java.util.stream.IntStream;

public final class Cell {
	
	public static void rangeDo(Cell start, Cell finish, Consumer<Cell> doThis) {
		IntStream.rangeClosed(start.y, finish.y).forEachOrdered(yValue -> {
			IntStream.rangeClosed(start.x, finish.x).forEachOrdered(xValue -> {
				doThis.accept(new Cell(xValue, yValue));
			});
		});
	}
	
	private final int x;
	private final int y;

	public Cell(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	public Cell up() {
		return new Cell(this.x, this.y + 1);
	}
	
	public Cell down() {
		return new Cell(this.x, this.y - 1);
	}

	public Cell left() {
		return new Cell(this.x - 1, this.y);
	}

	public Cell right() {
		return new Cell(this.x + 1, this.y);
	}
	
	public boolean equalsX(Cell cell) {
		 return this.x == cell.x;
	}
	
	public boolean equalsY(Cell cell) {
		 return this.y == cell.y;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cell [x=" + x + ", y=" + y + "]";
	}
}