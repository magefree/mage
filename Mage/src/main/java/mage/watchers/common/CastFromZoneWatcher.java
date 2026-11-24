package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class CastFromZoneWatcher extends Watcher {

    private final Zone zone;

    // holds which spell with which zone change counter was cast from zone
    private final Map<UUID, Set<Integer>> spellsCastFromZone = new HashMap<>();

    public CastFromZoneWatcher(Zone zone) {
        super(WatcherScope.GAME);
        this.zone = zone;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        /**
         * This does still not handle if a spell is cast from hand and comes to
         * play from other zones during the same step. But at least the state is
         * reset if the game comes to a new step
         */
        if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getZone() == this.zone) {
            final Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell != null) {
                final Set<Integer> zcc = this.spellsCastFromZone.computeIfAbsent(spell.getSourceId(), k -> new HashSet<>());
                zcc.add(spell.getZoneChangeCounter(game));
            }

        }
    }

    public boolean spellWasCastFromZone(UUID sourceId, int zcc) {
        final Set zccSet = this.spellsCastFromZone.get(sourceId);
        return zccSet != null && zccSet.contains(zcc);
    }

    @Override
    public void reset() {
        super.reset();
        this.spellsCastFromZone.clear();
    }
}
