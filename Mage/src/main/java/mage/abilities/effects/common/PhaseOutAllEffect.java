
package mage.abilities.effects.common;

import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * This class should only be used within the application of another effect
 *
 * @author TheElk801
 */
public class PhaseOutAllEffect extends OneShotEffect {

    private final List<UUID> idList;

    public PhaseOutAllEffect(List<UUID> idList) {
        super(Outcome.Neutral);
        this.idList = idList;
    }

    protected PhaseOutAllEffect(final PhaseOutAllEffect effect) {
        super(effect);
        this.idList = effect.idList;
    }

    @Override
    public PhaseOutAllEffect copy() {
        return new PhaseOutAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // First we phase out everything that isn't attached to anything
        // Anything attached to these permanents will phase out indirectly
        for (UUID permanentId : idList) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null) {
                Permanent attachedTo = game.getPermanent(permanent.getAttachedTo());
                if (attachedTo == null) {
                    permanent.phaseOut(game);
                }
            }
        }
        // Once this is done, we'll have permanents which are attached to something but haven't phased out
        // These will be phased out directly
        for (UUID permanentId : idList) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null && permanent.isPhasedIn()) {
                permanent.phaseOut(game);
            }
        }
        return true;
    }
}
