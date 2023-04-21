package mage.filter.predicate.permanent;

import mage.filter.Filter;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.util.TargetAddress;

/**
 * @author duncant
 */

public class PermanentCanBeAttachedToPredicate implements ObjectSourcePlayerPredicate<Permanent> {

    protected Permanent aura;

    public PermanentCanBeAttachedToPredicate(Permanent aura) {
        super();
        this.aura = aura;
    }

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        // TODO: Is it possible to add functionality to exclude objects the aura can't enchant (e.g. protection from)?
        Permanent potentialAttachment = input.getObject();
        for (TargetAddress addr : TargetAddress.walk(aura)) {
            Target target = addr.getTarget(aura);
            Filter filter = target.getFilter();
            return filter.match(potentialAttachment, game);
        }
        return false;
    }
}
