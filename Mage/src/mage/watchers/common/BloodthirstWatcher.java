package mage.watchers.common;

import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.watchers.WatcherImpl;

/**
 * Must be installed to card for proper Bloodthirst work
 * @author Loki
 */
public class BloodthirstWatcher extends WatcherImpl<BloodthirstWatcher> {
    public BloodthirstWatcher() {
        super("DamagedOpponents");
    }

    public BloodthirstWatcher(final BloodthirstWatcher watcher) {
        super(watcher);
    }

    @Override
    public void watch(GameEvent event, Game game) {
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
