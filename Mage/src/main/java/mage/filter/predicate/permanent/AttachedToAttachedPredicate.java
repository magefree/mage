package mage.filter.predicate.permanent;

import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Optional;

/**
 * @author TheElk801
 */
public enum AttachedToAttachedPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return Optional
                .ofNullable(input.getSource().getSourcePermanentOrLKI(game))
                .map(Permanent::getAttachedTo)
                .filter(input.getObject()::isAttachedTo)
                .isPresent();
    }

    @Override
    public String toString() {
        return "attached to attached permanent";
    }

}
