package main.java.mage.abilities.condition.common;

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
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            permanent = game.getPermanentEntering(source.getSourceId());
        }
        if (permanent != null) {
            Watcher watcher = game.getState().getWatchers().get("CastFromHand", source.getSourceId());
            if (watcher != null && watcher.conditionMet()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "you cast it from your hand";
    }

}
