package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.hint.Hint;
import mage.filter.StaticFilters;
import mage.game.Game;

/**
 * @author TheElk801
 */
public enum ControlArtifactAndEnchantmentHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        boolean artifact = game.getBattlefield().contains(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT, ability, game, 1
        );
        boolean enchantment = game.getBattlefield().contains(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ENCHANTMENT, ability, game, 1
        );
        if (artifact) {
            return "You control and artifact" + (enchantment ? " and an enchantment" : "");
        } else if (enchantment) {
            return "You control an enchantment";
        }
        return null;
    }

    @Override
    public ControlArtifactAndEnchantmentHint copy() {
        return this;
    }
}
