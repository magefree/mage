
package mage.filter.predicate.permanent;

import java.util.List;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author L_J
 */
public class PermanentInListPredicate implements Predicate<Permanent> {

    private final List<Permanent> permanents;

    public PermanentInListPredicate(List<Permanent> permanents) {
        this.permanents = permanents;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        return (permanents.contains(input));
    }
}
