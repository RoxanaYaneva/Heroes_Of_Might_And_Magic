package homm.demo;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import homm.exceptions.InsufficientAmountOfMoneyException;
import homm.exceptions.InvalidActionException;
import homm.hero.Hero;
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

	private static void readPlayersName(Player player) {
		System.out.print("Player should enter his/her name: ");
		String name = scanner.nextLine();
		while (true) {
			try {
				player.setName(name);
				player.loadInformation();
				player.setGold(Double.parseDouble(player.getInfo().get(0)));
				break;
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			System.out.println("Please enter name for the player again:");
			name = scanner.next();
		}
	}

	private static void chooseRace(Player player) {
		System.out.println(player.getName() + " should enter the race he/she wants to play with:");
		String race = scanner.nextLine();
		while (!race.equals("Inferno") && !race.equals("Heaven")) {
			System.out.println("Invalid race. Please enter Heaven or Inferno.");
			race = scanner.nextLine();
		}
		player.setRace(race);
	}

	private static void buyUnits(Player player, List<Unit> enemyArmy) {
		System.out.println(player.getName() + " should buy units. Enter quit when you are ready.");
		String command;
		while (!(command = scanner.nextLine()).equals("quit")) {
			// works if there are enough arguments
			String[] attributes = command.split("\\s+");
			int number = Integer.parseInt(attributes[1]);
			String unit = attributes[2];
			if (unit.equals("Horned")) {
				unit += " " + attributes[3];
			}
			if (isOfRace(player.getRace(), unit)) {
				try {
					player.buyMonster(number, unit);
					System.out.println("You've succesfully bought " + number + " " + unit + "(s).");
					Unit monster = getMonster(number, unit);
					positionUnitOnField(monster, player.getArmy(), enemyArmy);
					player.addUnitToArmy(monster);
				} catch (InsufficientAmountOfMoneyException e) {
					System.out.println(e.getMessage());
				}
			} else {
				System.out.println("No such unit of race " + player.getRace());
			}
		}
	}

	private static void chooseTypeOfHero(Player player, List<Unit> enemyArmy) {
		System.out.print(player.getName() + " should choose what type of hero he/she wants (Mage or Warrior): ");
		String typeOfHero = scanner.nextLine();
		while (!typeOfHero.equals("Mage") && !typeOfHero.equals("Warrior")) {
			System.out.print("Invalid type. Please enter Mage or Warrior.");
			typeOfHero = scanner.nextLine();
		}
		Hero hero = player.createHero(typeOfHero);
		positionUnitOnField(hero, player.getArmy(), enemyArmy);
		player.addUnitToArmy(hero);
	}

	private static void positionUnitOnField(Unit unit, List<Unit> armyOne, List<Unit> armyTwo) {
		int x, y;
		System.out.println("Set the position x of the unit:");
		x = scanner.nextInt();
		System.out.println("Set the position y of the unit:");
		y = scanner.nextInt();
		while (isThereUnitAtPos(x, y, armyOne, armyTwo)) {
			System.out.println("There is a unit at this position already. Please set new x and y: ");
			x = scanner.nextInt();
			y = scanner.nextInt();
		}
		unit.setPosition(x, y);
	}

	private static boolean isThereUnitAtPos(int x, int y, List<Unit> armyOne, List<Unit> armyTwo) {
		Unit unit = Unit.getUnitFromPos(x, y, armyOne, armyTwo);
		return unit == null ? false : true;
	}

	public static void startBattle(Player playerOne, Player playerTwo) {
		System.out.println("Let the battle start...");
		while (!isEndOfGame(playerOne.getArmy(), playerTwo.getArmy())) {
			enterAndProcessCommand(playerOne, playerTwo.getArmy());
			enterAndProcessCommand(playerTwo, playerOne.getArmy());
		}
		if (playerOne.getArmy().isEmpty()) {
			System.out.println("The winner is " + playerTwo.getName());
		} else {
			System.out.println("The winner is " + playerOne.getName());
		}
	}

	private static boolean isEndOfGame(List<Unit> armyOne, List<Unit> armyTwo) {
		return armyOne.isEmpty() || armyTwo.isEmpty();
	}

	private static void enterAndProcessCommand(Player player, List<Unit> enemyArmy) {
		System.out.println(player.getName() + "should enter his/her command.");
		String[] command = scanner.nextLine().split("\\s+");
		if (command[0].equalsIgnoreCase("move")) {
			executeMove(command, player, enemyArmy);
		} else if (command[0].equalsIgnoreCase("attack")) {
			executeAttack(command, player, enemyArmy);
		} else if (command[0].equalsIgnoreCase("cast")) {
			executeCast(command, player, enemyArmy);
		} else {
			System.out.println("No such commmand.");
		}
	}

	private static void executeMove(String[] command, Player player, List<Unit> enemyArmy) {
		while (true) {
			int[] xy = parseCoordinates(command);
			try {
				Validator.validatePlayersMove(xy[0], xy[1], xy[2], xy[3], player, enemyArmy);
				Unit.getUnitFromPos(xy[0], xy[1], player.getArmy()).move(xy[2], xy[3]);
				System.out.println("You've moved from " + xy[0] + "," + xy[1] + " to " + xy[2] + "," + xy[3]);
				break;
			} catch (InvalidActionException e) {
				System.out.println(e.getMessage());
			}
			System.out.println("Enter your command again.");
			command = scanner.nextLine().split("\\s+");
		}
	}

	private static void executeAttack(String[] command, Player player, List<Unit> enemyArmy) {
		while (true) {
			int[] xy = parseCoordinates(command);
			try {
				Validator.validatePlayersAttack(xy[0], xy[1], xy[2], xy[3], player, enemyArmy);
				Unit.getUnitFromPos(xy[0], xy[1], player.getArmy()).attack(xy[2], xy[3], enemyArmy);
				System.out.println(xy[0] + "," + xy[1] + " attacked unit at " + xy[2] + "," + xy[3]);
				Unit.checkForDeadUnits(enemyArmy);
				break;
			} catch (InvalidActionException e) {
				System.out.println(e.getMessage());
			}
			System.out.println("Enter your command again.");
			command = scanner.nextLine().split("\\s+");
		}
	}

	private static void executeCast(String[] command, Player player, List<Unit> enemyArmy) {
		while (true) {
			int toX = Integer.parseInt(command[3]);
			int toY = Integer.parseInt(command[4]);
			try {
				Validator.validatePlayersCast(toX, toY, command[2], player.getHero(), enemyArmy);
				switch (command[1]) {
				case "Fireball":
					((Mage) player.getHero()).fireball(toX, toY, enemyArmy); break;
				case "Iceball":
					((Mage) player.getHero()).iceball(toX, toY, enemyArmy); break;
				case "Stun":
					((Warrior) player.getHero()).stun(toX, toY, enemyArmy); break;
				case "Shield":
					((Warrior) player.getHero()).stun(toX, toY, player.getArmy()); break;
				default: break;
				}
				Unit.checkForDeadUnits(enemyArmy);
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

	public static Unit getMonster(int numberOfUnits, String unit) {
		Unit monster = null;
		switch (unit) {
		case "Peasant":
			monster = new Peasant(Peasant.HP_PEASANT, Peasant.ARMOR_PEASANT, Peasant.RANGE_PEASANT,
					Peasant.DAMAGE_PEASANT, Peasant.STAMINA_PEASANT, numberOfUnits, Peasant.PRICE_PEASANT); break;
		case "Brute":
			monster = new Brute(Brute.HP_BRUTE, Brute.ARMOR_BRUTE, Brute.RANGE_BRUTE, Brute.DAMAGE_BRUTE,
					Brute.STAMINA_BRUTE, numberOfUnits, Brute.PRICE_BRUTE); break;
		case "Archer":
			monster = new Archer(Archer.HP_ARCHER, Archer.ARMOR_ARCHER, Archer.RANGE_ARCHER, Archer.DAMAGE_ARCHER,
					Archer.STAMINA_ARCHER, numberOfUnits, Archer.PRICE_ARCHER); break;
		case "Crossbowman":
			monster = new Crossbowman(Crossbowman.HP_CROSSBOWMAN, Crossbowman.ARMOR_CROSSBOWMAN,
					Crossbowman.RANGE_CROSSBOWMAN, Crossbowman.DAMAGE_CROSSBOWMAN, Crossbowman.STAMINA_CROSSBOWMAN,
					numberOfUnits, Crossbowman.PRICE_CROSSBOWMAN); break;
		case "Imp":
			monster = new Imp(Imp.HP_IMP, Imp.ARMOR_IMP, Imp.RANGE_IMP, Imp.DAMAGE_IMP, Imp.STAMINA_IMP, numberOfUnits,
					Imp.PRICE_IMP); break;
		case "Vermin":
			monster = new Vermin(Vermin.HP_VERMIN, Vermin.ARMOR_VERMIN, Vermin.RANGE_VERMIN, Vermin.DAMAGE_VERMIN,
					Vermin.STAMINA_VERMIN, numberOfUnits, Vermin.PRICE_VERMIN); break;
		case "Horned demon":
			monster = new HornedDemon(HornedDemon.HP_HORNEDDEMON, HornedDemon.ARMOR_HORNEDDEMON,
					HornedDemon.RANGE_HORNEDDEMON, HornedDemon.DAMAGE_HORNEDDEMON, HornedDemon.STAMINA_HORNEDDEMON,
					numberOfUnits, HornedDemon.PRICE_HORNEDDEMON); break;
		case "Horned grunt":
			monster = new HornedGrunt(HornedGrunt.HP_HORNEDGRUNT, HornedGrunt.ARMOR_HORNEDGRUNT,
					HornedGrunt.RANGE_HORNEDGRUNT, HornedGrunt.DAMAGE_HORNEDGRUNT, HornedGrunt.STAMINA_HORNEDGRUNT,
					numberOfUnits, HornedGrunt.PRICE_HORNEDGRUNT); break;
		default: break;
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
			monsterRace = "Heaven"; break;
		case "Imp":
		case "Vermin":
		case "Horned grunt":
		case "Horned demon":
			monsterRace = "Inferno"; break;
		default: break;
		}
		return race.equals(monsterRace);
	}
}
