package homm.validator;

import java.util.List;

import homm.exceptions.InvalidActionException;
import homm.field.Field;
import homm.hero.Hero;
import homm.hero.Mage;
import homm.hero.Warrior;
import homm.player.Player;
import homm.unit.Unit;

public class Validator {

	public static void validatePlayersMove(int fromX, int fromY, int toX, int toY, Player player, List<Unit> enemyArmy)
			throws InvalidActionException {

		if (!Field.isFieldValid(fromX, fromY) || !Field.isFieldValid(toX, toY)) {
			throw new InvalidActionException("There are no such fields.");
		}

		Unit unitFrom = Unit.getUnitFromPos(fromX, fromY, player.getArmy());
		if (unitFrom == null) {
			throw new InvalidActionException(
					"There's no unit from " + player.getName() + "'s army at " + fromX + "," + fromY);
		}

		if (unitFrom.isFrozen()) {
			unitFrom.unfreeze();
			throw new InvalidActionException("The unit is frozen and cannot be moved.");
		}

		if (unitFrom.isStunned()) {
			unitFrom.yield();
			throw new InvalidActionException("The unit is stunned and cannot be moved.");
		}

		Unit unitTo = Unit.getUnitFromPos(toX, toY, player.getArmy(), enemyArmy);
		if (unitTo != null) {
			throw new InvalidActionException("There is unit on this field already.");
		}

		if (calcDist(fromX, toX, fromY, toY) > unitFrom.getStamina()) {
			throw new InvalidActionException("There is not enough stamina for this move.");
		}
	}

	public static void validatePlayersAttack(int fromX, int fromY, int toX, int toY, Player player,
			List<Unit> enemyArmy) throws InvalidActionException {

		if (!Field.isFieldValid(fromX, fromY) || !Field.isFieldValid(toX, toY)) {
			throw new InvalidActionException("There are no such fields.");
		}

		Unit unitFrom = Unit.getUnitFromPos(fromX, fromY, player.getArmy());
		if (unitFrom == null) {
			throw new InvalidActionException(
					"There's no unit from " + player.getName() + "'s army at " + fromX + "," + fromY);
		}

		if (unitFrom.isStunned()) {
			unitFrom.yield();
			throw new InvalidActionException("The unit is stunned and cannot attack.");
		}

		Unit unitTo = Unit.getUnitFromPos(toX, toY, enemyArmy);
		if (unitTo == null) {
			throw new InvalidActionException("There's no unit from the enemy's army at " + toX + "," + toY);
		}

		if (unitTo.isShielded()) {
			throw new InvalidActionException("The enemy unit is shielded and cannot be attacked.");
		}

		if (calcDist(fromX, toX, fromY, toY) > unitFrom.getRange()) {
			throw new InvalidActionException("The range of the unit is not enough for this attack.");
		}
	}

	public static void validatePlayersCast(int atX, int atY, String spell, Hero hero, List<Unit> units)
			throws InvalidActionException {

		if (!Field.isFieldValid(atX, atY)) {
			throw new InvalidActionException("There is no such field.");
		}

		int fromX = hero.getPosition().getX();
		int fromY = hero.getPosition().getY();
		if (calcDist(fromX, atX, fromY, atY) > hero.getRange()) {
			throw new InvalidActionException(hero.getClass().getSimpleName() + " cannot make this magic.");
		}

		if (hero instanceof Mage && !spell.equals("Fireball") && !spell.equals("Iceball")) {
			throw new InvalidActionException("Mage cannot make this magic.");
		}

		if (hero instanceof Warrior && !spell.equals("Stun") && !spell.equals("Shield")) {
			throw new InvalidActionException("Warrior cannot make this magic.");
		}

		if (!hero.hasEnoughMana(spell)) {
			throw new InvalidActionException("Hero does not have enough mana.");
		}

		Unit unit = Unit.getUnitFromPos(atX, atY, units);
		if (unit == null) {
			throw new InvalidActionException("There is no unit at " + atX + "," + atY);
		}

		if (unit.isShielded()) {
			throw new InvalidActionException("The unit is shielded.");
		}
	}

	private static int calcDist(int x1, int y1, int x2, int y2) {
		int distance = (int) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
		return distance;
	}
}