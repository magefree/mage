package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

/**
 * @author TheElk801
 */
public enum NightCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.checkDayNight(false);
    }

    @Override
    public String toString() {
        return "it's night";
    }
}
