
package mage.filter.predicate.permanent;

import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author North
 */
public class ControllerIdPredicate implements Predicate<Permanent> {

    private final UUID controllerId;

    public ControllerIdPredicate(UUID controllerId) {
        this.controllerId = controllerId;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        if(controllerId == null){
            return false;
        }
        if(input.getControllerId() == null){
            return false;
        }
        return controllerId.equals(input.getControllerId());
    }

    @Override
    public String toString() {
        return "ControllerId(" + controllerId + ')';
    }
}
