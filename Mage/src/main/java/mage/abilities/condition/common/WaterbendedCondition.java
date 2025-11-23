package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.WaterbendAbility;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * Checks if the spell was cast with the alternate waterbend cost
 *
 * @author TheElk801
 */
public enum WaterbendedCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil.checkSourceCostsTagExists(game, source, WaterbendAbility.WATERBEND_ACTIVATION_VALUE_KEY);
    }

    @Override
    public String toString() {
        return "the additional cost was paid";
    }
}
