package mage.filter.predicate.permanent;

import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class ProtectorIdPredicate implements Predicate<Permanent> {

    private final UUID protectorId;

    public ProtectorIdPredicate(UUID protectorId) {
        this.protectorId = protectorId;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.isProtectedBy(protectorId);
    }
}
