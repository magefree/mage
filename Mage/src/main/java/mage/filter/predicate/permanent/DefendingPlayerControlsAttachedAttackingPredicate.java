package mage.filter.predicate.permanent;

import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Optional;

/**
 * This predicate can only apply when source is attached to an attacking creature.
 * <p>
 * It should be used when the source is attached to an attacking creature during an effect
 * e.g. "Whenever equipped creature attacks, tap target creature defending player controls."
 *
 * @author Susucr, TheElk801, xenohedron
 */
public enum DefendingPlayerControlsAttachedAttackingPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return Optional.ofNullable(input.getSource().getSourcePermanentOrLKI(game))
                .map(Permanent::getAttachedTo)
                .map(uuid -> game.getCombat().getDefendingPlayerId(uuid, game))
                .map(input.getObject()::isControlledBy)
                .orElse(false);
    }

    @Override
    public String toString() {
        return "";
    }
}
