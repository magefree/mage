
package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public class PlayerCastCreatureWatcher extends Watcher {

    private final Set<UUID> playerIds = new HashSet<>();

    public PlayerCastCreatureWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell.isCreature(game)) {
                playerIds.add(spell.getControllerId());
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerIds.clear();
    }

    public boolean playerDidCastCreatureThisTurn(UUID playerId) {
        return playerIds.contains(playerId);
    }
}
