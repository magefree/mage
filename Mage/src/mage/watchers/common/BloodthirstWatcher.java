package mage.watchers.common;

import java.util.UUID;
import mage.Constants.WatcherScope;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.watchers.WatcherImpl;

/**
 * Must be installed to player for proper Bloodthirst work
 * @author Loki
 */
public class BloodthirstWatcher extends WatcherImpl<BloodthirstWatcher> {
    public BloodthirstWatcher(UUID controllerId) {
        super("DamagedOpponents", WatcherScope.PLAYER);
        this.controllerId = controllerId;
    }

    public BloodthirstWatcher(final BloodthirstWatcher watcher) {
        super(watcher);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (condition == true) //no need to check - condition has already occured
            return;
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent)event;
            if (game.getOpponents(this.controllerId).contains(damageEvent.getPlayerId())) {
                condition = true;
            }
        }
    }

    @Override
    public BloodthirstWatcher copy() {
        return new BloodthirstWatcher(this);
    }
}
