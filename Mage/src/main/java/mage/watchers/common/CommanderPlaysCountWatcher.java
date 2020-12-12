package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.watchers.Watcher;

import java.util.*;

/**
 * Calcs commanders play count only from command zone (spell or land)
 * Cards like Remand can put command to hand and cast it without commander tax increase
 *
 * @author JayDi85
 */
public class CommanderPlaysCountWatcher extends Watcher {

    private final Map<UUID, Integer> playsCount = new HashMap<>();
    private final Map<UUID, Integer> playerCount = new HashMap<>();

    public CommanderPlaysCountWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != EventType.LAND_PLAYED
                && event.getType() != EventType.SPELL_CAST) {
            return;
        }
        final UUID objectId;
        if (event.getType() == EventType.LAND_PLAYED) {
            objectId = event.getTargetId();
        } else if (event.getType() == EventType.SPELL_CAST) {
            objectId = event.getSourceId();
        } else {
            objectId = null;
        }
        boolean isCommanderObject = game
                .getPlayerList()
                .stream()
                .map(game::getPlayer)
                .map(game::getCommandersIds)
                .flatMap(Collection::stream)
                .anyMatch(id -> Objects.equals(id, objectId));
        if (!isCommanderObject || event.getZone() != Zone.COMMAND) {
            return;
        }
        playsCount.putIfAbsent(event.getSourceId(), 0);
        playsCount.computeIfPresent(event.getSourceId(), (u, i) -> i + 1);
        playerCount.putIfAbsent(event.getPlayerId(), 0);
        playerCount.compute(event.getPlayerId(), (u, i) -> i + 1);
    }

    public int getPlaysCount(UUID commanderId) {
        return this.playsCount.getOrDefault(commanderId, 0);
    }

    public int getPlayerCount(UUID playerId) {
        return this.playerCount.getOrDefault(playerId, 0);
    }
}
