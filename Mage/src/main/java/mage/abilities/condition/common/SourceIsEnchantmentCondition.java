package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

import java.util.Optional;

/**
 * @author TheElk801
 */
public enum SourceIsEnchantmentCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .filter(permanent -> permanent.isEnchantment(game))
                .isPresent();
    }

    @Override
    public String toString() {
        return "this permanent is an enchantment";
    }
}
