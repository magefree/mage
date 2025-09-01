package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;

/**
 * @author TheElk801
 */
public enum YouControlTwoOrMoreGatesCondition implements Condition {
    instance;
    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.GATE);

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getBattlefield().count(filter, source.getControllerId(), source, game) >= 2;
    }

    @Override
    public String toString() {
        return "you control two or more Gates";
    }
}
