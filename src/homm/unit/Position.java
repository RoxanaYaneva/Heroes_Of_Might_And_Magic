package homm.unit;

public class Position {

	private int x;
	private int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public boolean equals(Object pos) {
		Position position = (Position) pos;
		if ((this.x == position.x) && (this.y == position.y)) {
			return true;
		} else {
			return false;
		}
	}
}