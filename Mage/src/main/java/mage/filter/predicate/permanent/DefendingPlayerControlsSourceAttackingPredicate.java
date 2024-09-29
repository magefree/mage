package mage.filter.predicate.permanent;

import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * This predicate can only apply when source is an attacking creature.
 * <p>
 * It should be used when the source is an attacking creature during an effect
 * e.g. 'when {this} attacks, target creature defending player controls can't block this turn.'
 *
 * @author Susucr
 */
public enum DefendingPlayerControlsSourceAttackingPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return input.getObject().isControlledBy(game.getCombat().getDefendingPlayerId(input.getSourceId(), game));
    }

    @Override
    public String toString() {
        return "";
    }
}
