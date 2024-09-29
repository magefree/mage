package mage.filter.predicate.mageobject;

import mage.abilities.Mode;
import mage.filter.FilterPlayer;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;

import java.util.UUID;

/**
 * @author jeffwadsworth, Susucr
 */
public class TargetsPlayerPredicate implements ObjectSourcePlayerPredicate<StackObject> {

    private final FilterPlayer targetFilter;

    public TargetsPlayerPredicate(FilterPlayer targetFilter) {
        this.targetFilter = targetFilter;
    }

    @Override
    public boolean apply(ObjectSourcePlayer<StackObject> input, Game game) {
        StackObject object = game.getStack().getStackObject(input.getObject().getId());
        if (object != null) {
            for (UUID modeId : object.getStackAbility().getModes().getSelectedModes()) {
                Mode mode = object.getStackAbility().getModes().get(modeId);
                for (Target target : mode.getTargets()) {
                    if (target.isNotTarget()) {
                        continue;
                    }
                    for (UUID targetId : target.getTargets()) {
                        Player player = game.getPlayer(targetId);
                        if (targetFilter.match(player, input.getPlayerId(), input.getSource(), game)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "that targets a " + targetFilter.getMessage();
    }
}
