package mage.filter.predicate.permanent;

import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Optional;

/**
 * @author Susucr
 */
public enum AttachedToSourcePredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return Optional.of(input.getObject())
                .map(Permanent::getAttachedTo)
                .filter(p -> p.equals(input.getSourceId()))
                .isPresent();
    }

    @Override
    public String toString() {
        return "attached to {this}";
    }

}
