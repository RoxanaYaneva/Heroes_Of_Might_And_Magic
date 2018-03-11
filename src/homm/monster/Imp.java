package homm.monster;

import java.util.List;

import homm.unit.Unit;

public class Imp extends Monster {

	public static final int HP_IMP = 23;
	public static final int ARMOR_IMP = 6;
	public static final double PRICE_IMP = 140;
	public static final int RANGE_IMP = 3;
	public static final int DAMAGE_IMP = 10;
	public static final int STAMINA_IMP = 4;

	public Imp(int health, int armor, int range, int damage, int stamina, int numberOfMonsters, double price) {
		super(health, armor, range, damage, stamina, numberOfMonsters, price);
	}

	@Override
	public void attack(int x, int y, List<Unit> units) {
	}
}
