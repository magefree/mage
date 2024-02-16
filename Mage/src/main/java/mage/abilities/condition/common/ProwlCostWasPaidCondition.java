package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.ProwlAbility;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * Checks if a the spell was cast with the alternate prowl costs
 *
 * @author LevelX2
 */
public enum ProwlCostWasPaidCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil.checkSourceCostsTagExists(game, source, ProwlAbility.getActivationKey());
    }

    @Override
    public String toString() {
        return "this spell's prowl cost was paid";
    }

}
