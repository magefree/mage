package mage.filter.predicate.permanent;

import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author Susucr
 */
public enum AttachedToSourcePredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return input.getObject().isAttachedTo(input.getSourceId());
    }

    @Override
    public String toString() {
        return "attached to {this}";
    }

}
