package mage.filter.predicate.permanent;

import mage.constants.SubType;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Objects;

/**
 * @author TheElk801
 */
public enum EnchantmentOrEnchantedPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        if (!input.isCreature(game)) {
            return false;
        }
        if (input.isEnchantment(game)) {
            return true;
        }
        return input
                .getAttachments()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .anyMatch(permanent -> permanent.hasSubtype(SubType.AURA, game));
    }

    @Override
    public String toString() {
        return "enchanted creature or enchantment creature";
    }
}
