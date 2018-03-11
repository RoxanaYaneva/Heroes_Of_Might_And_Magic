package homm.monster;

import java.util.List;

import homm.unit.Unit;

public class Peasant extends Monster {

	public static final int HP_PEASANT = 20;
	public static final int ARMOR_PEASANT = 5;
	public static final double PRICE_PEASANT = 100;
	public static final int RANGE_PEASANT = 3;
	public static final int DAMAGE_PEASANT = 10;
	public static final int STAMINA_PEASANT = 4;

	public Peasant(int health, int armor, int range, int damage, int stamina, int numberOfMonsters, double price) {
		super(health, armor, range, damage, stamina, numberOfMonsters, price);
	}

	@Override
	public void attack(int x, int y, List<Unit> units) {
		// TODO Auto-generated method stub
		
	}

}
