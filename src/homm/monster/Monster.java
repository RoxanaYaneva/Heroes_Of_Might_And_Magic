package homm.monster;

import java.util.List;

import homm.unit.Unit;

public abstract class Monster extends Unit {

	protected double price;

	public Monster(int health, int armor, int range, int damage, int stamina, int numberOfMonsters, double price) {
		super(health, armor, range, damage, stamina);
		this.numberOfUnits = numberOfMonsters;
		this.price = price;
	}

	@Override
	public void attack(int atX, int atY, List<Unit> enemyArmy) {
		int totalDamage = this.numberOfUnits * this.damage;
		Unit unit = Unit.getUnitFromPos(atX, atY, enemyArmy);
		int totalArmor = unit.getNumberOfUnits() * unit.getArmor();
		int points = totalDamage - totalArmor;
		if (points < 0) {
			this.updateGroup(points);
		} else if (points > 0) {
			unit.updateGroup(points);
		}
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
