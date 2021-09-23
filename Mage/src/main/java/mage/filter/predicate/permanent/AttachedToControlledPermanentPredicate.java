
package mage.filter.predicate.permanent;

import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author North & L_J
 */
public class AttachedToControlledPermanentPredicate implements ObjectSourcePlayerPredicate<Permanent> {

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent attachement = input.getObject();
        if (attachement != null) {
            Permanent permanent = game.getPermanent(attachement.getAttachedTo());
            if (permanent != null && permanent.isControlledBy(input.getPlayerId())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "Attached to permanents you control";
    }
}
