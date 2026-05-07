package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Tracks the first spell with {X} in its mana cost that each player casts each turn.
 *
 * @author muz
 */
public class FirstXSpellCastThisTurnWatcher extends Watcher {

    private final Map<UUID, UUID> firstXSpellPerPlayer = new HashMap<>();

    public FirstXSpellCastThisTurnWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && spell.getSpellAbility().getManaCostsToPay().containsX()) {
            firstXSpellPerPlayer.putIfAbsent(event.getPlayerId(), spell.getId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        firstXSpellPerPlayer.clear();
    }

    /**
     * Returns the ID of the first X spell cast by the given player this turn, or null if none yet.
     */
    public UUID getFirstXSpellId(UUID playerId) {
        return firstXSpellPerPlayer.get(playerId);
    }
}
