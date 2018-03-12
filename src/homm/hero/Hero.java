package homm.hero;

import java.util.List;

import homm.unit.Unit;

public abstract class Hero extends Unit {

	public static final double MANA_RETURN_PERCANTAGE = 0.01;

	protected double mana;
	protected double crit;
	protected int experience;
	protected int nextLvlExperience;
	protected int level;

	public Hero(int health, int armor, int range, int damage, int stamina) {
		super(health, armor, range, damage, stamina);
	}
	
	public abstract boolean hasEnoughMana(String spell);
	
	@Override
	public void attack(int atX, int atY, List<Unit> enemyArmy) {

	}

	public double getMana() {
		return mana;
	}

	public void setMana(double mana) {
		this.mana = mana;
	}

	public double getCrit() {
		return crit;
	}

	public void setCrit(double crit) {
		this.crit = crit;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public void addExperience(int experience) {
		this.experience += experience;
	}

	public int getNextLvlExperience() {
		return nextLvlExperience;
	}

	public void setNextLvlExperience(int nextLvlExperience) {
		this.nextLvlExperience = nextLvlExperience;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
