
package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.*;

/**
 *
 * @author LevelX2
 */
public class CastFromGraveyardWatcher extends Watcher {

    // holds which spell with witch zone change counter was cast from graveyard
    private final Map<UUID, Set<Integer>> spellsCastFromGraveyard = new HashMap<>();

    public CastFromGraveyardWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        /**
         * This does still not handle if a spell is cast from hand and comes to
         * play from other zones during the same step. But at least the state is
         * reset if the game comes to a new step
         */
        if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getZone() == Zone.GRAVEYARD) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell != null) {
                Set<Integer> zcc = spellsCastFromGraveyard.computeIfAbsent(spell.getSourceId(), k -> new HashSet<>());
                zcc.add(spell.getZoneChangeCounter(game));
            }

        }
    }

    public boolean spellWasCastFromGraveyard(UUID sourceId, int zcc) {
        Set zccSet = spellsCastFromGraveyard.get(sourceId);
        return zccSet != null && zccSet.contains(zcc);

    }

    @Override
    public void reset() {
        super.reset();
        spellsCastFromGraveyard.clear();
    }
}
