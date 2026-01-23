package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.SneakAbility;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * Checks if the spell was cast with the sneak cost
 *
 * @author TheElk801
 */
public enum SneakCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil.checkSourceCostsTagExists(game, source, SneakAbility.SNEAK_ACTIVATION_VALUE_KEY);
    }

    @Override
    public String toString() {
        return "sneak cost was paid";
    }
}
