package homm.monster;

import homm.unit.Unit;

public abstract class Monster extends Unit {

	protected int numberOfMonsters;
	protected double price;

	public Monster(int health, int armor, int range, int damage, int stamina, int numberOfMonsters, double price) {
		super(health, armor, range, damage, stamina);
		this.numberOfMonsters = numberOfMonsters;
		this.price = price;
	}

	public int getNumberOfMonsters() {
		return numberOfMonsters;
	}

	public void setNumberOfMonsters(int numberOfMonsters) {
		this.numberOfMonsters = numberOfMonsters;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
