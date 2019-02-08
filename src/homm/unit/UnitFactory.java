package homm.unit;

import homm.hero.Mage;
import homm.hero.Warrior;
import homm.monster.Archer;
import homm.monster.Brute;
import homm.monster.Crossbowman;
import homm.monster.HornedDemon;
import homm.monster.HornedGrunt;
import homm.monster.Imp;
import homm.monster.Peasant;
import homm.monster.Vermin;
import homm.player.Player;

public class UnitFactory {

	public static Unit createHero(String type, Player player) {
		if (type.equals("Mage")) {
			player.hero = new Mage(Mage.HP_MAGE, Mage.ARMOR_MAGE, Mage.RANGE_MAGE, Mage.DAMAGE_MAGE, Mage.STAMINA_MAGE);
			player.hero.setNumberOfUnits(1);
			player.hero.setLevel(Integer.parseInt(player.getInfo().get(1)));
			player.hero.setExperience(Integer.parseInt(player.getInfo().get(2)));
			player.hero.setNextLvlExperience(Integer.parseInt(player.getInfo().get(3)));
			player.hero.setMana(Double.parseDouble(player.getInfo().get(4)));
			player.hero.setCrit(Double.parseDouble(player.getInfo().get(5)));
		} else {
			player.hero = new Warrior(Warrior.HP_WARRIOR, Warrior.ARMOR_WARRIOR, Warrior.RANGE_WARRIOR,
					Warrior.DAMAGE_WARRIOR, Warrior.STAMINA_WARRIOR);
			player.hero.setNumberOfUnits(1);
			player.hero.setLevel(Integer.parseInt(player.getInfo().get(1)));
			player.hero.setExperience(Integer.parseInt(player.getInfo().get(2)));
			player.hero.setNextLvlExperience(Integer.parseInt(player.getInfo().get(3)));
			player.hero.setMana(Double.parseDouble(player.getInfo().get(4)));
			player.hero.setCrit(Double.parseDouble(player.getInfo().get(5)));
		}
		return player.hero;
	}

	public static Unit createMonster(String type, int numberOfUnits) {
		Unit monster = null;
		switch (type) {
		case "Peasant":
			monster = new Peasant(Peasant.HP_PEASANT, Peasant.ARMOR_PEASANT, Peasant.RANGE_PEASANT,
					Peasant.DAMAGE_PEASANT, Peasant.STAMINA_PEASANT, numberOfUnits, Peasant.PRICE_PEASANT);
			break;
		case "Brute":
			monster = new Brute(Brute.HP_BRUTE, Brute.ARMOR_BRUTE, Brute.RANGE_BRUTE, Brute.DAMAGE_BRUTE,
					Brute.STAMINA_BRUTE, numberOfUnits, Brute.PRICE_BRUTE);
			break;
		case "Archer":
			monster = new Archer(Archer.HP_ARCHER, Archer.ARMOR_ARCHER, Archer.RANGE_ARCHER, Archer.DAMAGE_ARCHER,
					Archer.STAMINA_ARCHER, numberOfUnits, Archer.PRICE_ARCHER);
			break;
		case "Crossbowman":
			monster = new Crossbowman(Crossbowman.HP_CROSSBOWMAN, Crossbowman.ARMOR_CROSSBOWMAN,
					Crossbowman.RANGE_CROSSBOWMAN, Crossbowman.DAMAGE_CROSSBOWMAN, Crossbowman.STAMINA_CROSSBOWMAN,
					numberOfUnits, Crossbowman.PRICE_CROSSBOWMAN);
			break;
		case "Imp":
			monster = new Imp(Imp.HP_IMP, Imp.ARMOR_IMP, Imp.RANGE_IMP, Imp.DAMAGE_IMP, Imp.STAMINA_IMP, numberOfUnits,
					Imp.PRICE_IMP);
			break;
		case "Vermin":
			monster = new Vermin(Vermin.HP_VERMIN, Vermin.ARMOR_VERMIN, Vermin.RANGE_VERMIN, Vermin.DAMAGE_VERMIN,
					Vermin.STAMINA_VERMIN, numberOfUnits, Vermin.PRICE_VERMIN);
			break;
		case "Horned demon":
			monster = new HornedDemon(HornedDemon.HP_HORNEDDEMON, HornedDemon.ARMOR_HORNEDDEMON,
					HornedDemon.RANGE_HORNEDDEMON, HornedDemon.DAMAGE_HORNEDDEMON, HornedDemon.STAMINA_HORNEDDEMON,
					numberOfUnits, HornedDemon.PRICE_HORNEDDEMON);
			break;
		case "Horned grunt":
			monster = new HornedGrunt(HornedGrunt.HP_HORNEDGRUNT, HornedGrunt.ARMOR_HORNEDGRUNT,
					HornedGrunt.RANGE_HORNEDGRUNT, HornedGrunt.DAMAGE_HORNEDGRUNT, HornedGrunt.STAMINA_HORNEDGRUNT,
					numberOfUnits, HornedGrunt.PRICE_HORNEDGRUNT);
			break;
		default:
			break;
		}
		return monster;
	}

}
