package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

/**
 * Warning: CastFromHandWatcher must be installed to card for proper working.
 *
 * @author Loki
 */
public class CastFromHandCondition implements Condition {
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = game.getPermanent(source.getSourceId());
        if (p != null) {
            Watcher watcher = game.getState().getWatchers().get("CastFromHand", source.getSourceId());
            if (watcher != null && watcher.conditionMet()) {
                return true;
            }
        }
        return false;
    }
}
