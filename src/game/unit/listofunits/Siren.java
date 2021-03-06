package game.unit.listofunits;

import game.Game;
import game.Player;
import game.board.Coordinate;
import game.board.Direction;
import game.board.Square;
import game.unit.Unit;
import game.unit.UnitStat;
import game.unit.property.Property;
import game.unit.property.ability.Ability;
import game.unit.property.ability.AbilityRange;
import game.unit.property.ability.ActiveTargetAbility;

public class Siren extends Unit {

    public Siren(Game game, Player playerOwner, Direction directionFacing, Coordinate coor) {
	super(game, playerOwner, directionFacing, coor);
    }

    @Override
    public Ability getDefaultAbility() {
	UnitStat defaultStat = getDefaultStat();
	Ability ability = new SirenAbility(this, defaultStat.defaultAttackRange, defaultStat.defaultWaitTime);
	return ability;
    }
}

class SirenAbility extends ActiveTargetAbility implements AbilityRange {

    private final Property<Integer> abilityRangeProperty;

    public SirenAbility(Unit unitOwner, int initialRange, int maxWaitTime) {
	super(unitOwner, maxWaitTime);
	abilityRangeProperty = new Property<>(unitOwner, initialRange);
    }

    @Override
    public Property<Integer> getAbilityRangeProperty() {
	return abilityRangeProperty;
    }

    @Override
    public boolean canUseAbilityOn(Square target) {
	if (target.isEmpty() || Unit.areAllies(getUnitOwner(), target.getUnitOnTop())) {
	    return false;
	}
	return true;
    }

    @Override
    protected void performAbility(Object... specs) {
	Square target = (Square) specs[0];
	if (!canUseAbilityOn(target)) {
	    return;
	}
	Unit enemyUnit = target.getUnitOnTop();
	// temporary
	enemyUnit.getOwnerProp().setValue(getUnitOwner().getOwnerProp().getValue());
    }

}
