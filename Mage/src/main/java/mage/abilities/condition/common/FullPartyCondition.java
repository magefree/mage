package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.game.Game;

/**
 * @author TheElk801
 */

public enum FullPartyCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return PartyCount.instance.calculate(game, source, null) >= 4;
    }
}
