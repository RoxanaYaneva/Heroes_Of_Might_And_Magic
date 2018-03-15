package homm.monster;

import java.util.List;

import homm.unit.Unit;

public class Brute extends Peasant {

	private static final double DAMAGE_PERCENTAGE = 0.25;
	
	public static final int HP_BRUTE = 15;
	public static final int ARMOR_BRUTE = 6;
	public static final double PRICE_BRUTE = 120;
	public static final int RANGE_BRUTE = 1;
	public static final int DAMAGE_BRUTE = 10;
	public static final int STAMINA_BRUTE = 4;

	public Brute(int health, int armor, int range, int damage, int stamina, int numberOfMonsters, double price) {
		super(health, armor, range, damage, stamina, numberOfMonsters, price);
	}

	@Override
	public void attack(int atX, int atY, List<Unit> enemyArmy) {
		super.attack(atX, atY, enemyArmy);
		Unit unit = null;
		int totalArmor = 0, points = 0;
		double totalDamage = this.numberOfUnits * this.damage * DAMAGE_PERCENTAGE;
		for (int i = atX - 1; i <= atX + 1; ++i) {
			for (int j = atY - 1; j <= atY + 1; ++j) {
				if (i != atX && j != atY) {
					unit = Unit.getUnitFromPos(i, j, enemyArmy);
					if (unit != null) {
						totalArmor = unit.getNumberOfUnits() * unit.getArmor();
						points = (int) (totalDamage - totalArmor);
						if (points > 0) {
							unit.updateGroup(points);
						}
					}
				}
			}
		}
	}
	
}
