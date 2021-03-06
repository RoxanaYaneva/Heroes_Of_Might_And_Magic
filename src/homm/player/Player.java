package homm.player;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import homm.exceptions.InsufficientAmountOfMoneyException;
import homm.hero.Hero;
import homm.monster.Archer;
import homm.monster.Brute;
import homm.monster.Crossbowman;
import homm.monster.HornedDemon;
import homm.monster.HornedGrunt;
import homm.monster.Imp;
import homm.monster.Peasant;
import homm.monster.Vermin;
import homm.unit.Unit;

public class Player {

	private int id;
	private String name;
	private double gold;
	private List<Unit> army;
	private List<String> info;
	private String race;
	public Hero hero;

	public Player(int id) {
		this.id = id;
		this.info = new ArrayList<>();
		this.army = new ArrayList<>();
	}

	public void addUnitToArmy(Unit unit) {
		this.army.add(unit);
	}

	public void loadInformation() throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(name + ".txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				this.info.add(line);
			}
		}
	}

	public void buyMonster(int numberOfUnits, String unit) throws InsufficientAmountOfMoneyException {
		double totalSum = 0;
		switch (unit) {
		case "Peasant":
			totalSum += numberOfUnits * Peasant.PRICE_PEASANT; break;
		case "Brute":
			totalSum += numberOfUnits * Brute.PRICE_BRUTE; break;
		case "Archer":
			totalSum += numberOfUnits * Archer.PRICE_ARCHER; break;
		case "Crossbowman":
			totalSum += numberOfUnits * Crossbowman.PRICE_CROSSBOWMAN; break;
		case "Imp":
			totalSum += numberOfUnits * Imp.PRICE_IMP; break;
		case "Vermin":
			totalSum += numberOfUnits * Vermin.PRICE_VERMIN; break;
		case "Horned demon":
			totalSum += numberOfUnits * HornedDemon.PRICE_HORNEDDEMON; break;
		case "Horned grunt":
			totalSum += numberOfUnits * HornedGrunt.PRICE_HORNEDGRUNT; break;
		default: break;
		}

		if (totalSum <= this.gold) {
			this.gold -= totalSum;
		} else {
			throw new InsufficientAmountOfMoneyException("You don't have enough money.");
		}
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}

	public int getId() {
		return id;
	}

	public List<String> getInfo() {
		return info;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Unit> getArmy() {
		return army;
	}

	public void setArmy(List<Unit> units) {
		this.army = units;
	}

	public double getGold() {
		return gold;
	}

	public void setGold(double gold) {
		this.gold = gold;
	}

	public Hero getHero() {
		return hero;
	}
}
