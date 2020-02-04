
package mage.filter.predicate.permanent;

import java.util.UUID;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class PermanentIdPredicate implements Predicate<Permanent> {

    private final UUID permanentId;

    public PermanentIdPredicate(UUID permanentId) {
        this.permanentId = permanentId;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getId().equals(permanentId);
    }

    @Override
    public String toString() {
        return "PermanentId(" + permanentId + ')';
    }
}
