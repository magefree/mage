package mage.watchers.common;

import mage.MageObjectReference;
import mage.cards.Card;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.watchers.Watcher;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Keeps track of the UUIDs of the cards that left graveyard this turn.
 */
public class CardsLeftGraveyardWatcher extends Watcher {

    // UUID of cards that left each players graveyards
    private final Map<UUID, Set<UUID>> cardsLeftGraveyardThisTurn = new HashMap<>();

    public CardsLeftGraveyardWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE
                || ((ZoneChangeEvent) event).getFromZone() != Zone.GRAVEYARD) {
            return;
        }

        UUID playerId = event.getPlayerId();
        if (playerId == null || game.getCard(event.getTargetId()) == null) {
            return;
        }

        cardsLeftGraveyardThisTurn.compute(playerId, (k, set) -> {
            if (set == null) {
                set = new HashSet<>();
            }
            set.add(event.getTargetId());
            return set;
        });
    }

    /**
     * The cards that left a specific player's graveyard this turn.
     *
     * @param playerId The player's UUID.
     * @param game The game to check for.
     * @return A set containing the card objects.
     */
    public Set<Card> getCardsThatLeftGraveyard(UUID playerId, Game game) {
        if (cardsLeftGraveyardThisTurn.get(playerId) == null) {
            return new HashSet<>();
        }
        return cardsLeftGraveyardThisTurn.get(playerId).stream().map(game::getCard).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    @Override
    public void reset() {
        super.reset();
        cardsLeftGraveyardThisTurn.clear();
    }
}
