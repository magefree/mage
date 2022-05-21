package mage.watchers.common;

import mage.cards.Card;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class CreaturePutIntoGraveyardWatcher extends Watcher {

    private final Set<UUID> players = new HashSet<>();

    public CreaturePutIntoGraveyardWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE
                || !Zone.GRAVEYARD.match(((ZoneChangeEvent) event).getToZone())) {
            return;
        }
        Card card = game.getCard(event.getTargetId());
        if (card != null && card.isCreature(game)) {
            players.add(card.getOwnerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        players.clear();
    }

    public static boolean checkPlayer(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(CreaturePutIntoGraveyardWatcher.class)
                .players
                .contains(playerId);
    }
}
