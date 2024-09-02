package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.ValueHint;
import mage.constants.ValuePhrasing;
import mage.filter.StaticFilters;
import mage.game.Game;

/**
 * @author JayDi85
 */
public enum CreaturesYouControlCount implements DynamicValue {

    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield().count(StaticFilters.FILTER_CONTROLLED_CREATURES, sourceAbility.getControllerId(), sourceAbility, game);
    }

    @Override
    public CreaturesYouControlCount copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "creatures you control";
    }

    @Override
    public String getMessage(ValuePhrasing phrasing) {
        switch (phrasing) {
            case FOR_EACH:
                return "creature you control";
            case X_HIDDEN:
                return "";
            default:
                return "the number of creatures you control";
        }
    }

    @Override
    public ValueHint getValueHint() {
        return new ValueHint("Creatures you control", this);
    }
}
