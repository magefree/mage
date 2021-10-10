package mage.watchers.common;

import mage.constants.CommanderCardType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.*;

/**
 * Default game watcher, no need to add it with abilities
 * <p>
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

        // must control main cards (split/mdf cards support)
        final UUID objectId;
        if (event.getType() == EventType.LAND_PLAYED) {
            objectId = CardUtil.getMainCardId(game, event.getTargetId());
        } else if (event.getType() == EventType.SPELL_CAST) {
            objectId = CardUtil.getMainCardId(game, event.getSourceId());
        } else {
            objectId = null;
        }

        // must calc all commanders and signature spell cause uses in commander tax
        boolean isCommanderObject = game
                .getPlayerList()
                .stream()
                .map(game::getPlayer)
                .map(player -> game.getCommandersIds(player, CommanderCardType.ANY, false))
                .flatMap(Collection::stream)
                .anyMatch(id -> Objects.equals(id, objectId));
        if (!isCommanderObject || event.getZone() != Zone.COMMAND) {
            return;
        }
        playsCount.putIfAbsent(objectId, 0);
        playsCount.computeIfPresent(objectId, (u, i) -> i + 1);
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
