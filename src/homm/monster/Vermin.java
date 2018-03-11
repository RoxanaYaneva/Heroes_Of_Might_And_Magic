package homm.monster;

import java.util.List;

import homm.unit.Unit;

public class Vermin extends Imp {

	public static final int HP_VERMIN = 23;
	public static final int ARMOR_VERMIN = 7;
	public static final int PRICE_VERMIN = 140;
	public static final int RANGE_VERMIN = 3;
	public static final int DAMAGE_VERMIN = 10;
	public static final int STAMINA_VERMIN = 4;

	public Vermin(int health, int armor, int range, int damage, int stamina, int numberOfMonsters, double price) {
		super(health, armor, range, damage, stamina, numberOfMonsters, price);
	}

	@Override
	public void attack(int x, int y, List<Unit> units) {

	}
}
