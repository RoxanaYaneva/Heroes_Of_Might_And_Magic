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

		Unit unitFrom = player.getUnitFromPos(fromX, fromY);
		if (unitFrom == null) {
			throw new InvalidActionException(
					"There is no unit from " + player.getName() + "'s army at " + fromX + ", " + fromY);
		}

		if (unitFrom.isFrozen()) {
			unitFrom.unfreeze();
			throw new InvalidActionException("Unit is frozen and cannot be moved.");
		}

		if (unitFrom.isStunned()) {
			unitFrom.yield();
			throw new InvalidActionException("Unit is stunned and cannot be moved.");
		}

		Unit unitTo = Unit.getUnitFromPos(toX, toY, player.getArmy(), enemyArmy);
		if (unitTo != null) {
			throw new InvalidActionException("There is already unit on this field");
		}

		if ((Math.abs(fromX - toX) < unitFrom.getStamina()) || (Math.abs(fromY - toY) < unitFrom.getStamina())) {
			throw new InvalidActionException("There is not enough stamina for this move.");
		}
	}

	public static void validatePlayersAttack(int fromX, int fromY, int toX, int toY, Player player,
			List<Unit> enemyArmy) throws InvalidActionException {
		// int fromX = this.position.getX();
		// int fromY = this.position.getY();
		//
		// if ((atX < 1 || atX > Field.WIDTH) || (atY < 1 || atY >
		// Field.LENGTH)) {
		// throw new InvalidActionException("There is no such field");
		// }
		//
		// if ((Math.abs(fromX - atX) < this.range) || (Math.abs(fromY - atY) <
		// this.range)) {
		// throw new InvalidActionException("There is not enough stamina.");
		// }
		//
		// if (this.isShielded) {
		// System.out.println("This unit is shielded.");
		// return;
		// }
		//
		// Unit enemyUnit = getUnitFromPos(atX, atY, enemyArmy);
		// if (enemyUnit == null) {
		// throw new InvalidActionException("There is no unit at this
		// position.");
		// } else {
		//
		// }

	}

	public static void validatePlayersCast(int atX, int atY, String spell, Hero hero, List<Unit> units)
			throws InvalidActionException {

		if (!Field.isFieldValid(atX, atY)) {
			throw new InvalidActionException("There is no such field.");
		}

		if (hero instanceof Mage && (!spell.equals("Fireball") || !spell.equals("Iceball"))) {
			throw new InvalidActionException("Mage cannot make this magic.");
		}

		if (hero instanceof Warrior && (!spell.equals("Stun") || !spell.equals("Shield"))) {
			throw new InvalidActionException("Warrior cannot make this magic.");
		}

		if (!hero.hasEnoughMana(spell)) {
			throw new InvalidActionException("Hero does not have enough mana.");
		}

		Unit unit = Unit.getUnitFromPos(atX, atY, units);
		if (unit == null) {
			throw new InvalidActionException("There is no unit at this field");
		}

		if (unit.isShielded()) {
			throw new InvalidActionException("Unit is shileded.");
		}
	}
}