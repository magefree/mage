package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.BeholdDragonAbility;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * Checks if the spell was cast with the alternate behold a Dragon cost
 *
 * @author TheElk801
 */
public enum BeheldDragonCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil.checkSourceCostsTagExists(game, source, BeholdDragonAbility.BEHOLD_DRAGON_ACTIVATION_VALUE_KEY);
    }

    @Override
    public String toString() {
        return "Dragon was beheld";
    }
}
