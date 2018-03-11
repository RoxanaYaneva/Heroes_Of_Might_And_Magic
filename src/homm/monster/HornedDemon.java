package homm.monster;

import java.util.List;

import homm.unit.Unit;


public class HornedDemon extends Monster {
	
	public static final int HP_HORNEDDEMON = 23;
	public static final int ARMOR_HORNEDDEMON = 8;
	public static final int PRICE_HORNEDDEMON = 140;
	public static final int RANGE_HORNEDDEMON = 3;
	public static final int DAMAGE_HORNEDDEMON = 10;
	public static final int STAMINA_HORNEDDEMON = 4;

	public HornedDemon(int health, int armor, int range, int damage, int stamina, int numberOfMonsters, double price) {
		super(health, armor, range, damage, stamina, numberOfMonsters, price);
	}

	@Override
	public void attack(int x, int y, List<Unit> units) {
		
	}
}
