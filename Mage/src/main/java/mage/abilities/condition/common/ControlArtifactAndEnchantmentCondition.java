package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.filter.StaticFilters;
import mage.game.Game;

/**
 * @author TheElk801
 */
public enum ControlArtifactAndEnchantmentCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getBattlefield().contains(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT, source, game, 1)
                && game.getBattlefield().contains(StaticFilters.FILTER_CONTROLLED_PERMANENT_ENCHANTMENT, source, game, 1);
    }

    @Override
    public String toString() {
        return "you control an artifact and an enchantment";
    }
}
