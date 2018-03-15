package homm.unit;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public abstract class Unit {

	protected int health;
	protected int armor;
	protected int damage;
	protected int range;
	protected int stamina;
	protected Position position;
	protected int numberOfUnits;
	
	protected boolean isFrozen = false;
	protected boolean isStunned = false;
	protected boolean isShielded = false;
	protected boolean isDead = false;

	public Unit(int health, int armor, int damage, int range, int stamina) {
		super();
		this.health = health;
		this.armor = armor;
		this.damage = damage;
		this.range = range;
		this.stamina = stamina;
	}

	public abstract void attack(int x, int y, List<Unit> units);

	public void move(int toX, int toY) {
		this.setPosition(toX, toY);
	}

	public static Unit getUnitFromPos(int x, int y, List<Unit> units) {
		Unit unit = units.stream()
				.filter(u -> u.getPosition().getX() == x)
				.filter(u -> u.getPosition().getY() == y)
				.findAny()
				.orElse(null);

		return unit;
	}

	public static Unit getUnitFromPos(int x, int y, List<Unit> armyOne, List<Unit> armyTwo) {
		Unit unit = Stream.concat(armyOne.stream(), armyTwo.stream())
				.filter(u -> u.getPosition().getX() == x)
				.filter(u -> u.getPosition().getY() == y)
				.findAny()
				.orElse(null);

		return unit;
	}
	
	public static void checkForDeadUnits(List<Unit> units) {
		Iterator<Unit> iter = units.iterator();
		while (iter.hasNext()) {
			if (iter.next().isDead()) {
				iter.remove();
			}
		}
	}
	
	public void updateGroup(int points) {
		int newHP = this.health * this.numberOfUnits - points;
		this.numberOfUnits = newHP / this.health;
		if (numberOfUnits <= 0) {
			this.die();
		}
	}

	public void die() {
		isDead = true;
	}

	public boolean isDead() {
		return isDead;
	}

	public Position getPosition() {
		return position;
	}

	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	public void setPosition(int x, int y) {
		this.position = new Position(x, y);
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	public void freeze() {
		isFrozen = true;
	}

	public void unfreeze() {
		isFrozen = false;
	}

	public void stun() {
		isStunned = true;
	}

	public void yield() {
		isStunned = false;
	}

	public void shield() {
		isShielded = true;
	}

	public boolean isFrozen() {
		return isFrozen;
	}

	public boolean isStunned() {
		return isStunned;
	}

	public boolean isShielded() {
		return isShielded;
	}
	
	public int getNumberOfUnits() {
		return numberOfUnits;
	}

	public void setNumberOfUnits(int number) {
		this.numberOfUnits = number;
	}
}
