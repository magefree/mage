package mage.filter.predicate.permanent;

import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author awjackson
 */
public enum AttachedOrShareCreatureTypePredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent attachment = input.getSource().getSourcePermanentOrLKI(game);
        if (attachment == null) {
            return false;
        }
        UUID attachedId = attachment.getAttachedTo();
        if (attachedId == null) {
            return false;
        }
        Permanent otherPermanent = input.getObject();
        if (attachedId.equals(otherPermanent.getId())) {
            return true;
        }
        Permanent attachedPermanent = game.getPermanentOrLKIBattlefield(attachedId);
        if (attachedPermanent == null) {
            return false;
        }
        return attachedPermanent.shareCreatureTypes(game, otherPermanent);
    }
}
