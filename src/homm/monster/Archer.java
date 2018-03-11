package homm.monster;

import java.util.List;

import homm.unit.Unit;

public class Archer extends Monster {

	public static final int HP_ARCHER = 23;
	public static final int ARMOR_ARCHER = 8;
	public static final double PRICE_ARCHER = 140;
	public static final int RANGE_ARCHER = 3;
	public static final int DAMAGE_ARCHER = 10;
	public static final int STAMINA_ARCHER = 4;

	public Archer(int health, int armor, int range, int damage, int stamina, int numberOfMonsters, double price) {
		super(health, armor, range, damage, stamina, numberOfMonsters, price);
	}

	@Override
	public void attack(int x, int y, List<Unit> units) {
	}
}
