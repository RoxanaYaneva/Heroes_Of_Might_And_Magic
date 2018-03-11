package homm.monster;

import java.util.List;

import homm.unit.Unit;

public class Brute extends Peasant {

	public static final int HP_BRUTE = 15;
	public static final int ARMOR_BRUTE = 6;
	public static final double PRICE_BRUTE = 120;
	public static final int RANGE_BRUTE = 3;
	public static final int DAMAGE_BRUTE = 10;
	public static final int STAMINA_BRUTE = 4;

	public Brute(int health, int armor, int range, int damage, int stamina, int numberOfMonsters, double price) {
		super(health, armor, range, damage, stamina, numberOfMonsters, price);
	}

	@Override
	public void attack(int x, int y, List<Unit> units) {

	}
}
