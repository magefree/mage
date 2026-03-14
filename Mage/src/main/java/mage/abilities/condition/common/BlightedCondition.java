package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.BlightAbility;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * Checks if the spell was cast with the alternate blight cost
 *
 * @author TheElk801
 */
public enum BlightedCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil.checkSourceCostsTagExists(game, source, BlightAbility.BLIGHT_ACTIVATION_VALUE_KEY);
    }

    @Override
    public String toString() {
        return "creature was blighted";
    }
}
