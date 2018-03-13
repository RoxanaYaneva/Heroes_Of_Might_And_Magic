package homm.monster;

import java.util.List;

import homm.unit.Unit;

public class Crossbowman extends Archer {

	public static final int HP_CROSSBOWMAN = 10;
	public static final int ARMOR_CROSSBOWMAN = 3;
	public static final double PRICE_CROSSBOWMAN = 150;
	public static final int RANGE_CROSSBOWMAN = 2;
	public static final int DAMAGE_CROSSBOWMAN = 14;
	public static final int STAMINA_CROSSBOWMAN = 3;

	public Crossbowman(int health, int armor, int range, int damage, int stamina, int numberOfMonsters, double price) {
		super(health, armor, range, damage, stamina, numberOfMonsters, price);
	}

	@Override
	public void attack(int atX, int atY, List<Unit> enemyArmy) {
		Unit unit = null;
		for (int i = atX - 1; i <= atX + 1; ++i) {
			for (int j = atY - 1; j <= atY + 1; ++j) {
				unit = Unit.getUnitFromPos(i, j, enemyArmy);
				if (unit != null) {
					super.attack(i, j, enemyArmy);
				}
			}
		}
	}
	
}
