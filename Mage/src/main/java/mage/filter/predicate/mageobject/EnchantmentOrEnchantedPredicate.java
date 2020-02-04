package mage.filter.predicate.mageobject;

import java.util.Objects;

import mage.constants.SubType;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public enum EnchantmentOrEnchantedPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        if (!input.isCreature()) {
            return false;
        }
        if (input.isEnchantment()) {
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
