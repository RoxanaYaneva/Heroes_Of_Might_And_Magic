package homm.monster;

import java.util.List;
import java.util.Random;

import homm.unit.Unit;

public class HornedGrunt extends HornedDemon {

	public static final int HP_HORNEDGRUNT = 23;
	public static final int ARMOR_HORNEDGRUNT = 7;
	public static final double PRICE_HORNEDGRUNT = 140;
	public static final int RANGE_HORNEDGRUNT = 3;
	public static final int DAMAGE_HORNEDGRUNT = 10;
	public static final int STAMINA_HORNEDGRUNT = 4;
	
	public HornedGrunt(int health, int armor, int range, int damage, int stamina, int numberOfMonsters, double price) {
		super(health, armor, range, damage, stamina, numberOfMonsters, price);
	}

	@Override
	public void attack(int atX, int atY, List<Unit> enemyArmy) {
		super.attack(atX, atY, enemyArmy);
		Random random = new Random();
		boolean secondAttack = random.nextBoolean();
		if(secondAttack) {
			super.attack(atX, atY, enemyArmy);
		}
	}
}
