package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.common.OpponentsLostLifeCount;
import mage.game.Game;

/**
 * @author JayDi85
 */
public enum OpponentsLostLifeCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return OpponentsLostLifeCount.instance.calculate(game, source.getControllerId()) > 0;
    }

    @Override
    public String toString() {
        return "an opponent lost life this turn";
    }
}
