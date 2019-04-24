
package mage.watchers.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public class PlayerCastCreatureWatcher extends Watcher {

    final Set<UUID> playerIds = new HashSet<>();

    public PlayerCastCreatureWatcher() {
        super(PlayerCastCreatureWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public PlayerCastCreatureWatcher(final PlayerCastCreatureWatcher watcher) {
        super(watcher);
        this.playerIds.addAll(watcher.playerIds);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell.isCreature()) {
                playerIds.add(spell.getControllerId());
            }
        }
    }

    @Override
    public PlayerCastCreatureWatcher copy() {
        return new PlayerCastCreatureWatcher(this);
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
