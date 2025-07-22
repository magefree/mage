package mage.filter.predicate.permanent;

import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Optional;

/**
 * Filters out the id of the enchanted object, if the source is an enchantment
 *
 * @author LevelX2
 */
public enum AnotherEnchantedPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return !Optional
                .ofNullable(input)
                .map(ObjectSourcePlayer::getSource)
                .map(source -> source.getSourcePermanentOrLKI(game))
                .map(Permanent::getAttachedTo)
                .filter(input.getObject().getId()::equals)
                .isPresent();
    }

    @Override
    public String toString() {
        return "Another enchanted";
    }
}
