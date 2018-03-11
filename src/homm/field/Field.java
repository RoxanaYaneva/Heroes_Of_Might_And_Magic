package homm.field;

public class Field {

	public static final int LENGTH = 50;
	public static final int WIDTH = 60;
	
	public static boolean isFieldValid(int x, int y) {
		return x >= 0 && x < WIDTH && y >= 0 && y < LENGTH;
	}
}
