
package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.UUID;

/**
 * Must be installed to player for proper Bloodthirst work
 *
 * @author Loki
 */
public class BloodthirstWatcher extends Watcher {
    public BloodthirstWatcher(UUID controllerId) {
        super(BloodthirstWatcher.class.getSimpleName(), WatcherScope.PLAYER);
        this.controllerId = controllerId;
    }

    public BloodthirstWatcher(final BloodthirstWatcher watcher) {
        super(watcher);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (condition) { //no need to check - condition has already occured
            return;
        }
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
            if (game.getPlayer(this.getControllerId()).hasOpponent(damageEvent.getPlayerId(), game)) {
                condition = true;
            }
        }
    }

    @Override
    public BloodthirstWatcher copy() {
        return new BloodthirstWatcher(this);
    }
}
