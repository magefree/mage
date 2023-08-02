package mage.filter.predicate.permanent;

import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * This predicate only works when source is an attacking creature.
 * Use it sparingly, mostly in triggers e.g. 'when [...] attacks, target creature defending player [...]'
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
