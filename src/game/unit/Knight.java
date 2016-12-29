package game.unit;

import java.util.ArrayList;
import java.util.List;

import game.Game;
import game.Player;
import game.board.Board;
import game.board.Coordinate;
import game.board.Direction;
import game.board.Square;
import game.interaction.Damage;
import game.interaction.DamageType;
import game.unit.ability.AbilityProperty;
import game.unit.ability.ActiveTargetAbilityProperty;

public class Knight extends Unit {

    public static final int DEFAULT_HEALTH = 50;
    public static final int DEFAULT_ARMOR = 8;
    public static final double DEFAULT_SIDE_BLOCK = 0.35;
    public static final double DEFAULT_FRONT_BLOCK = 0.8;
    public static final int DEFAULT_MOVE_RANGE = 3;
    public static final int DEFAULT_ATTACK_RANGE = 1;
    public static final int DEFAULT_POWER = 25;
    public static final int MAX_WAIT_TIME = 1;

    public Knight(Game game, Player playerOwner, Direction directionFacing, Coordinate coor) {
	super(game, playerOwner, directionFacing, coor);
    }

    @Override
    public int getDefaultHealth() {
	return DEFAULT_HEALTH;
    }

    @Override
    public int getDefaultArmor() {
	return DEFAULT_ARMOR;
    }

    @Override
    public double getDefaultSideBlock() {
	return DEFAULT_SIDE_BLOCK;
    }

    @Override
    public double getDefaultFrontBlock() {
	return DEFAULT_FRONT_BLOCK;
    }

    @Override
    public int getDefaultMoveRange() {
	return DEFAULT_MOVE_RANGE;
    }

    @Override
    public int getDefaultAttackRange() {
	return DEFAULT_ATTACK_RANGE;
    }

    @Override
    public int getDefaultPower() {
	return DEFAULT_POWER;
    }

    @Override
    public AbilityProperty getDefaultAbilityProperty() {
	AbilityProperty abilityProp = new KnightAbilityProperty(this, DEFAULT_POWER, DEFAULT_ATTACK_RANGE);
	return abilityProp;
    }

    @Override
    public int getMaxWaitTime() {
	return MAX_WAIT_TIME;
    }
}

class KnightAbilityProperty extends ActiveTargetAbilityProperty {

    public KnightAbilityProperty(Unit unitOwner, int initialPower, int initialAttackRange) {
	super(unitOwner, initialPower, initialAttackRange);
    }

    @Override
    public boolean canUseAbilityOn(Square target) {
	if (getUnitOwner().getStunnedProp().getCurrentPropertyValue() || target.getUnitOnTop() == null
		|| Board.walkDist(getUnitOwner().getPosProp().getCurrentPropertyValue(),
			target.getCoor()) > getAbilityRangeProperty().getCurrentPropertyValue()
		|| Unit.areAllies(getUnitOwner(), target.getUnitOnTop())) {
	    return false;
	} else {
	    return true;
	}
    }

    @Override
    public List<Square> getAOESqaures(Square target) {
	List<Square> list = new ArrayList<>(1);
	list.add(target);
	return list;
    }

    @Override
    public void performAbility(Square target) {
	if (!canUseAbilityOn(target)) {
	    return;
	}
	target.getUnitOnTop().getHealthProp().takeDamage(
		new Damage(getCurrentPropertyValue(), DamageType.PHYSICAL, getUnitOwner(), target.getUnitOnTop()));
    }

}
