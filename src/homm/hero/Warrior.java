package homm.hero;

import java.util.List;

import homm.unit.Unit;

public class Warrior extends Hero {

	public static final int MANA_STUN = 3;
	public static final int MANA_SHIELD = 7;

	public static final int HP_WARRIOR = 13;
	public static final int ARMOR_WARRIOR = 4;
	public static final int RANGE_WARRIOR = 1;
	public static final int DAMAGE_WARRIOR = 10;
	public static final int STAMINA_WARRIOR = 4;

	public Warrior(int health, int armor, int range, int damage, int stamina) {
		super(health, armor, range, damage, stamina);
	}

	public void stun(int atX, int atY, List<Unit> units) {
		Unit.getUnitFromPos(atX, atY, units).stun();
		this.mana -= MANA_STUN;
		this.returnMana();
	}

	public void shield(int atX, int atY, List<Unit> units) {
		Unit.getUnitFromPos(atX, atY, units).shield();
		this.mana -= MANA_SHIELD;
		this.returnMana();
	}
	
	@Override
	public boolean hasEnoughMana(String spell) {
		boolean hasMagic;
		if (spell.equals("Stun")) {
			hasMagic = mana >= MANA_STUN ? true : false;
		} else { // Shield
			hasMagic = mana >= MANA_SHIELD ? true : false;
		}
		return hasMagic;
	}
}
