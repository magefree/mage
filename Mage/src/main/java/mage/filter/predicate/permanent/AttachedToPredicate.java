
package mage.filter.predicate.permanent;

import java.util.UUID;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LoneFox
 */
public class AttachedToPredicate implements Predicate<Permanent> {

    private final FilterPermanent filter;

    public AttachedToPredicate(FilterPermanent filter) {
        this.filter = filter;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        UUID attachedTo = input.getAttachedTo();
        Permanent permanent = game.getPermanent(attachedTo);
        return filter.match(permanent, game);
    }

    @Override
    public String toString() {
        return "attached to " + filter.getMessage();
    }

}
