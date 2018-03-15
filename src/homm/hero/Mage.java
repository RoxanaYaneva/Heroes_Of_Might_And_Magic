package homm.hero;

import java.util.List;

import homm.unit.Unit;

public class Mage extends Hero {

	public static final int MANA_FIREBALL = 2;
	public static final int MANA_ICEBALL = 3;

	public static final int HP_MAGE = 20;
	public static final int ARMOR_MAGE = 5;
	public static final int RANGE_MAGE = 3;
	public static final int DAMAGE_MAGE = 10;
	public static final int STAMINA_MAGE = 4;

	public Mage(int health, int armor, int range, int damage, int stamina) {
		super(health, armor, range, damage, stamina);
	}

	public void fireball(int atX, int atY, List<Unit> enemyArmy) {
		super.attack(atX, atY, enemyArmy);
		this.mana -= MANA_FIREBALL;
		this.returnMana();
	}

	public void iceball(int atX, int atY, List<Unit> enemyArmy) {
		Unit.getUnitFromPos(atX, atY, enemyArmy).freeze();
		this.mana -= MANA_ICEBALL;
		this.returnMana();
	}

	@Override
	public boolean hasEnoughMana(String spell) {
		boolean hasMagic;
		if (spell.equals("Fireball")) {
			hasMagic = this.mana >= MANA_FIREBALL ? true : false;
		} else { // Ice ball
			hasMagic = this.mana >= MANA_ICEBALL ? true : false;
		}
		return hasMagic;
	}
}
