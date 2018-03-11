package homm.demo;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import homm.exceptions.InsufficientAmountOfMoneyException;
import homm.exceptions.InvalidActionException;
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
import homm.unit.Unit;
import homm.validator.Validator;

public class Engine {

	private static Scanner scanner = new Scanner(System.in);

	public static final int PLAYER_ONE_ID = 1;
	public static final int PLAYER_TWO_ID = 2;

	private Player playerOne = new Player(PLAYER_ONE_ID);
	private Player playerTwo = new Player(PLAYER_TWO_ID);

	public void start() {
		readPlayersName(playerOne);
		readPlayersName(playerTwo);

		chooseRace(playerOne);
		chooseRace(playerTwo);

		buyUnits(playerOne, playerTwo.getArmy());
		buyUnits(playerTwo, playerOne.getArmy());

		chooseTypeOfHero(playerOne, playerTwo.getArmy());
		chooseTypeOfHero(playerOne, playerOne.getArmy());

		startBattle(playerOne, playerTwo);
	}

	public static void startBattle(Player playerOne, Player playerTwo) {
		System.out.println("Let the battle start...");
		while (!isEndOfGame(playerOne.getArmy(), playerTwo.getArmy())) {
			enterAndProcessCommand(playerOne, playerTwo.getArmy());
			enterAndProcessCommand(playerTwo, playerOne.getArmy());
		}
	}

	private static boolean isEndOfGame(List<Unit> army, List<Unit> army2) {
		return army2.isEmpty() || army2.isEmpty();
	}

	private static void enterAndProcessCommand(Player player, List<Unit> otherUnits) {
		System.out.println(player.getName() + "should enter his/her command.");
		String[] command = scanner.nextLine().split("\\s+");
		if (command[0].equalsIgnoreCase("move")) {
			executeMove(command, player, otherUnits);
		} else if (command[0].equalsIgnoreCase("attack")) {
			executeAttack(command, player, otherUnits);
		} else if (command[0].equalsIgnoreCase("cast")) {
			executeCast(command, player, otherUnits);
		} else {
			System.out.println("No such commmand.");
		}
	}

	private static void executeMove(String[] command, Player player, List<Unit> otherUnits) {
		while (true) {
			int[] xy = parseCoordinates(command);
			try {
				Validator.validatePlayersMove(xy[0], xy[1], xy[2], xy[3], player, otherUnits);
				player.getUnitFromPos(xy[0], xy[1]).move(xy[2], xy[3]);
				break;
			} catch (InvalidActionException e) {
				System.out.println(e.getLocalizedMessage());
			}
			System.out.println("Enter your command again.");
			command = scanner.nextLine().split("\\s+");
		}
	}

	private static void executeAttack(String[] command, Player player, List<Unit> otherUnits) {
		while (true) {
			int[] xy = parseCoordinates(command);
			try {
				Validator.validatePlayersAttack(xy[0], xy[1], xy[2], xy[3], player, otherUnits);
				player.getUnitFromPos(xy[0], xy[1]).attack(xy[2], xy[3], otherUnits);
				Unit.checkForDeadUnits(otherUnits);
				break;
			} catch (InvalidActionException e) {
				System.out.println(e.getLocalizedMessage());
			}
			System.out.println("Enter your command again.");
			command = scanner.nextLine().split("\\s+");
		}
	}

	private static void executeCast(String[] command, Player player, List<Unit> otherUnits) {
		while (true) {
			int toX = Integer.parseInt(command[3]);
			int toY = Integer.parseInt(command[4]);
			try {
				Validator.validatePlayersCast(toX, toY, command[2], player.getHero(), otherUnits);
				switch (command[1]) {
				case "Fireball":
					((Mage) player.getHero()).fireball(toX, toY, otherUnits);
					break;
				case "Iceball":
					((Mage) player.getHero()).iceball(toX, toY, otherUnits);
					break;
				case "Stun":
					((Warrior) player.getHero()).stun(toX, toY, otherUnits);
					break;
				case "Shield":
					((Warrior) player.getHero()).stun(toX, toY, otherUnits);
					break;
				default:
					System.out.println("No such spell.");
				}
				Unit.checkForDeadUnits(otherUnits);
				break;
			} catch (InvalidActionException e) {
				System.out.println(e.getMessage());
			}
			System.out.println("Enter your command again.");
			command = scanner.nextLine().split("\\s+");
		}
	}

	private static int[] parseCoordinates(String[] command) {
		int[] coord = new int[4];
		coord[0] = Integer.parseInt(command[1]);
		coord[1] = Integer.parseInt(command[2]);
		coord[2] = Integer.parseInt(command[4]);
		coord[3] = Integer.parseInt(command[5]);
		return coord;
	}

	private static void readPlayersName(Player player) {
		System.out.print("Player should enter his/her name: ");
		String name = scanner.nextLine();
		player.setName(name);
		try {
			player.loadInformation();
			player.setGold(Double.parseDouble(player.getInfo()[0]));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void chooseRace(Player player) {
		System.out.print(player.getName() + " should enter the race he/she wants to play with: ");
		String race = scanner.nextLine();
		while (!race.equals("Inferno") || !race.equals("Heaven")) {
			System.out.println("Invalid race. Please enter Heaven or Inferno.");
			race = scanner.nextLine();
		}
		player.setRace(race);
	}

	private static void buyUnits(Player player, List<Unit> otherUnits) {
		System.out.println(player.getName() + " should buy units. Enter quit when you are ready.");
		String command;
		while (!(command = scanner.nextLine()).equals("quit")) {
			String[] attributes = command.split("\\s+");
			int number = Integer.parseInt(attributes[1]);
			String unit = attributes[2];
			if (isOfRace(player.getRace(), unit)) {
				try {
					player.buyMonster(number, unit);
					Unit monster = getMonster(number, unit);
					positionUnitOnField(monster, player.getArmy(), otherUnits);
					player.addUnitToArmy(monster);
				} catch (NumberFormatException | InsufficientAmountOfMoneyException e) {
					System.out.println(e.getMessage());
				}
			} else {
				System.out.println("No such unit.");
			}
		}
	}

	public static Unit getMonster(int numberOfUnits, String unit) {
		Unit monster = null;
		switch (unit) {
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
		case "Horned grunt	":
			monster = new HornedGrunt(HornedGrunt.HP_HORNEDGRUNT, HornedGrunt.ARMOR_HORNEDGRUNT,
					HornedGrunt.RANGE_HORNEDGRUNT, HornedGrunt.DAMAGE_HORNEDGRUNT, HornedGrunt.STAMINA_HORNEDGRUNT,
					numberOfUnits, HornedGrunt.PRICE_HORNEDGRUNT);
			break;
		default:
			break;
		}
		return monster;
	}

	private static boolean isOfRace(String race, String monster) {
		String monsterRace = "";
		switch (monster) {
		case "Peasant":
		case "Brute":
		case "Archer":
		case "Crossbowman":
			monsterRace = "Heaven";
			break;
		case "Imp":
		case "Vermin":
		case "Horned grunt":
		case "Horned demon":
			monsterRace = "Inferno";
			break;
		default:
			break;
		}
		return race.equals(monsterRace);
	}

	private static void chooseTypeOfHero(Player player, List<Unit> otherUnits) {
		System.out.print(player.getName() + " should choose what type of hero he/she wants (Mage or Warrior): ");
		String typeOfHero = scanner.nextLine();
		while (!typeOfHero.equals("Mage") || !typeOfHero.equals("Warrior")) {
			System.out.print("Invalid type. Please enter Mage or Warrior.");
			typeOfHero = scanner.nextLine();
		}
		player.createHero(typeOfHero);
		positionUnitOnField(player.getHero(), player.getArmy(), otherUnits);
		player.addUnitToArmy(player.getHero());
	}

	private static boolean isThereUnitAtPos(int x, int y, List<Unit> armyOne, List<Unit> armyTwo) {
		Unit unit = Unit.getUnitFromPos(x, y, armyOne, armyTwo);
		return unit == null ? false : true;
	}

	private static void positionUnitOnField(Unit unit, List<Unit> armyOne, List<Unit> armyTwo) {
		System.out.println("Set the position x and y of the hero: ");
		int x, y;
		x = scanner.nextInt();
		y = scanner.nextInt();
		while (isThereUnitAtPos(x, y, armyOne, armyTwo)) {
			System.out.println("There is a unit at this position already. Please set new x and y: ");
			x = scanner.nextInt();
			y = scanner.nextInt();
		}
		unit.setPosition(x, y);
	}
}
