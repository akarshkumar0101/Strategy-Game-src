package game.unit.listofunits;

import game.Game;
import game.Player;
import game.board.Coordinate;
import game.board.Direction;
import game.unit.Unit;
import game.unit.UnitStat;
import game.unit.property.ability.AbilityProperty;

public class Archer extends Unit {

    public Archer(Game game, Player playerOwner, Direction directionFacing, Coordinate coor) {
	super(game, playerOwner, directionFacing, coor);
    }

    @Override
    public AbilityProperty getDefaultAbilityProperty() {
	UnitStat defaultStat = getDefaultStat();
	AbilityProperty abilityProp = new BowmenAbilityProperty(this, defaultStat.defaultPower,
		defaultStat.defaultAttackRange, defaultStat.defaultWaitTime);
	return abilityProp;
    }
}
