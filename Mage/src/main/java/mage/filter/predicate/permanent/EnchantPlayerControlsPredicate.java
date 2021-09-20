package mage.filter.predicate.permanent;

import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public enum EnchantPlayerControlsPredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<Permanent>> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent permanent = game.getPermanent(input.getSourceId());
        return permanent != null && input.getObject().isControlledBy(permanent.getAttachedTo());
    }
}
