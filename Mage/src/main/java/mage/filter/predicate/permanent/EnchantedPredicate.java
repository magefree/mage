package mage.filter.predicate.permanent;

import mage.constants.SubType;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Objects;

/**
 * @author LevelX2
 */
public enum EnchantedPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getAttachments()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .anyMatch(permanent -> permanent.hasSubtype(SubType.AURA, game));
    }

    @Override
    public String toString() {
        return "Enchanted";
    }
}
