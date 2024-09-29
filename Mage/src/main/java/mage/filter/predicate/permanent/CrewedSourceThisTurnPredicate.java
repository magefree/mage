package mage.filter.predicate.permanent;

import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.CrewedVehicleWatcher;

/**
 * @author TheElk801
 */
public enum CrewedSourceThisTurnPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return CrewedVehicleWatcher.checkIfCrewedThisTurn(
                input.getObject(), input.getSource().getSourcePermanentOrLKI(game), game
        );
    }

    @Override
    public String toString() {
        return "crewed {this} this turn";
    }
}
