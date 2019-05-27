package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Calcs commanders play count (spell or land)
 *
 * @author JayDi85
 */
public class CommanderPlaysCountWatcher extends Watcher {

    private final Map<UUID, Integer> playsCount = new HashMap<>();

    public CommanderPlaysCountWatcher() {
        super(WatcherScope.GAME);
    }

    public CommanderPlaysCountWatcher(final CommanderPlaysCountWatcher watcher) {
        super(watcher);
        this.playsCount.putAll(watcher.playsCount);
    }

    @Override
    public CommanderPlaysCountWatcher copy() {
        return new CommanderPlaysCountWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != EventType.LAND_PLAYED && event.getType() != EventType.SPELL_CAST) {
            return;
        }

        UUID possibleCommanderId = event.getSourceId();
        boolean isCommanderObject = false;
        for (Player player : game.getPlayers().values()) {
            if (game.getCommandersIds(player).contains(possibleCommanderId)) {
                isCommanderObject = true;
                break;
            }
        }

        if (isCommanderObject) {
            int count = playsCount.getOrDefault(possibleCommanderId, 0);
            count++;
            playsCount.put(possibleCommanderId, count);
        }
    }

    public int getPlaysCount(UUID commanderId) {
        return this.playsCount.getOrDefault(commanderId, 0);
    }
}
