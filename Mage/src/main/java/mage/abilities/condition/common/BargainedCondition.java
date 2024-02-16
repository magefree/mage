package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.BargainAbility;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * Checks if the spell was cast with the alternate Bargain cost
 *
 * @author Susucr
 */
public enum BargainedCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil.checkSourceCostsTagExists(game, source, BargainAbility.BARGAIN_ACTIVATION_VALUE_KEY);
    }

    @Override
    public String toString() {
        return "{this} was Bargained";
    }

}
