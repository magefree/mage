
package mage.filter.predicate.permanent;

import mage.constants.TargetController;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.game.Controllable;
import mage.game.Game;

import java.util.UUID;

/**
 *
 * @author North
 */
public class ControllerPredicate implements ObjectPlayerPredicate<ObjectPlayer<Controllable>> {

    private final TargetController controller;

    public ControllerPredicate(TargetController controller) {
        this.controller = controller;
    }

    @Override
    public boolean apply(ObjectPlayer<Controllable> input, Game game) {
        Controllable object = input.getObject();
        UUID playerId = input.getPlayerId();

        switch (controller) {
            case YOU:
                if (object.isControlledBy(playerId)) {
                    return true;
                }
                break;
            case TEAM:
                if (!game.getPlayer(playerId).hasOpponent(object.getControllerId(), game)) {
                    return true;
                }
                break;
            case OPPONENT:
                if (!object.isControlledBy(playerId)
                        && game.getPlayer(playerId).hasOpponent(object.getControllerId(), game)) {
                    return true;
                }
                break;
            case NOT_YOU:
                if (!object.isControlledBy(playerId)) {
                    return true;
                }
                break;
            case ACTIVE:
                if (object.isControlledBy(game.getActivePlayerId())) {
                    return true;
                }
                break;
            case ANY:
                return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "TargetController(" + controller.toString() + ')';
    }
}
