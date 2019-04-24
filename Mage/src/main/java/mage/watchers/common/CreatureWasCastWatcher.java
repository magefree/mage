
package mage.watchers.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.cards.Card;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public class CreatureWasCastWatcher extends Watcher {

    private final Set<UUID> creaturesCasted = new HashSet<>();

    public CreatureWasCastWatcher() {
        super(CreatureWasCastWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public CreatureWasCastWatcher(final CreatureWasCastWatcher watcher) {
        super(watcher);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell != null) {
                Card card = game.getCard(spell.getSourceId());
                if (card != null && card.isCreature()) {
                    creaturesCasted.add(card.getId());
                }
            }
        }
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && card.isCreature()) {
                creaturesCasted.remove(card.getId());
            }
        }
    }

    public boolean wasCreatureCastThisTurn(UUID creatureSourceId) {
        return creaturesCasted.contains(creatureSourceId);
    }

    @Override
    public void reset() {
        super.reset();
        creaturesCasted.clear();
    }

    @Override
    public CreatureWasCastWatcher copy() {
        return new CreatureWasCastWatcher(this);
    }
}
