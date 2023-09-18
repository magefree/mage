package mage.filter.predicate.mageobject;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Mode;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;

/**
 *
 * @author jeffwadsworth
 */
public class TargetsPlayerPredicate implements ObjectSourcePlayerPredicate<MageObject> {

    public TargetsPlayerPredicate() {
    }

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        StackObject object = game.getStack().getStackObject(input.getObject().getId());
        if (object != null) {
            for (UUID modeId : object.getStackAbility().getModes().getSelectedModes()) {
                Mode mode = object.getStackAbility().getModes().get(modeId);
                for (Target target : mode.getTargets()) {
                    for (UUID targetId : target.getTargets()) {
                        Player player = game.getPlayer(targetId);
                        return player != null;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "that targets a player";
    }
}
