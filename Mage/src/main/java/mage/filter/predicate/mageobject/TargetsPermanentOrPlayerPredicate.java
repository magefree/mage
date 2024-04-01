package mage.filter.predicate.mageobject;

import mage.abilities.Mode;
import mage.filter.FilterPermanent;
import mage.filter.FilterPlayer;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;

import java.util.UUID;

/**
 * @author Susucr
 */
public class TargetsPermanentOrPlayerPredicate implements ObjectSourcePlayerPredicate<StackObject> {

    private final FilterPermanent targetFilterPermanent;
    private final FilterPlayer targetFilterPlayer;

    public TargetsPermanentOrPlayerPredicate(FilterPermanent targetFilterPermanent, FilterPlayer targetFilterPlayer) {
        this.targetFilterPermanent = targetFilterPermanent;
        this.targetFilterPlayer = targetFilterPlayer;
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
                        // Try for permanent
                        Permanent permanent = game.getPermanent(targetId);
                        if (targetFilterPermanent.match(permanent, input.getPlayerId(), input.getSource(), game)) {
                            return true;
                        }
                        // Try for player
                        Player player = game.getPlayer(targetId);
                        if (targetFilterPlayer.match(player, input.getPlayerId(), input.getSource(), game)) {
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
        return "that targets a " + targetFilterPermanent.getMessage() + " or " + targetFilterPlayer.getMessage();
    }
}
