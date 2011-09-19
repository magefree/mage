package mage.watchers.common;

import mage.Constants;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.WatcherImpl;

public class CastFromHandWatcher extends WatcherImpl<CastFromHandWatcher> {
    public CastFromHandWatcher() {
        super("CastFromHand");
    }

    public CastFromHandWatcher(final CastFromHandWatcher watcher) {
        super(watcher);
    }

    @Override
    public void watch(GameEvent event, Game game) {
         if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getZone() == Constants.Zone.HAND) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (this.getSourceId().equals(spell.getSourceId())) {
               condition = true;
            }
        }
    }

    @Override
    public CastFromHandWatcher copy() {
        return new CastFromHandWatcher(this);
    }
}
