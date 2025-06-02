package mage.filter.predicate.permanent;

import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author North
 */
public enum ControllerIsActivePlayerPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return game.isActivePlayer(input.getControllerId());
    }

    @Override
    public String toString() {
        return "controlled by the active player";
    }
}
