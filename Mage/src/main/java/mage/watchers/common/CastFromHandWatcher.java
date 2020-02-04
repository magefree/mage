package mage.watchers.common;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.turn.Step;
import mage.watchers.Watcher;

public class CastFromHandWatcher extends Watcher {

    private final Set<UUID> spellsCastFromHand = new HashSet<>();
    private Step step;

    public CastFromHandWatcher() {
        super(WatcherScope.GAME);
    }


    @Override
    public void watch(GameEvent event, Game game) {
        /**
         * This does still not handle if a spell is cast from hand and comes to
         * play from other zones during the same step. But at least the state is
         * reset if the game comes to a new step
         */

        if (step != null && !Objects.equals(game.getTurn().getStep(), step)) {
            spellsCastFromHand.clear();
            step = null;
        }
        if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getZone() == Zone.HAND) {
            if (step == null) {
                step = game.getTurn().getStep();
            }
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell != null) {
                spellsCastFromHand.add(spell.getSourceId());
            }
        }
    }

    public boolean spellWasCastFromHand(UUID sourceId) {
        return spellsCastFromHand.contains(sourceId);
    }

    @Override
    public void reset() {
        super.reset();
        spellsCastFromHand.clear();
    }

}
