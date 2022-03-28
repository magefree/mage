package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;

/**
 * @author JayDi85
 */
public enum GateYouControlCount implements DynamicValue {

    instance;
    private static final FilterPermanent filter = new FilterControlledPermanent("Gate you control");

    static {
        filter.add(SubType.GATE.getPredicate());
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield().count(filter, sourceAbility.getControllerId(), sourceAbility, game);
    }

    @Override
    public GateYouControlCount copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "1"; // uses "for each" effects, so must be 1, not X
    }

    @Override
    public String getMessage() {
        return "Gate you control";
    }
}
