
package mage.filter.predicate.permanent;

import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;


/**
 *
 * @author North
 */
public class ControllerIsActivePlayerPredicate implements Predicate<Permanent> {
    @Override
    public boolean apply(Permanent input, Game game) {
        if(input.getControllerId() == null){
            return false;
        }
        return game.isActivePlayer(input.getControllerId());
    }

    @Override
    public String toString() {
        return "controlled by the active player";
    }
}
