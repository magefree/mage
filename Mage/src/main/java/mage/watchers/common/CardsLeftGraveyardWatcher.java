package mage.watchers.common;

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

    // Player id -> card ids
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
        Card card = game.getCard(event.getTargetId());
        if (card == null) {
            return;
        }
        UUID playerId = card.getOwnerId();
        if (playerId == null) {
            return;
        }
        cardsLeftGraveyardThisTurn.computeIfAbsent(playerId, k -> new HashSet<>())
                        .add(event.getTargetId());
    }

    /**
     * The cards that left a specific player's graveyard this turn.
     */
    public Set<Card> getCardsThatLeftGraveyard(UUID playerId, Game game) {
        return cardsLeftGraveyardThisTurn.getOrDefault(playerId, Collections.emptySet())
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Override
    public void reset() {
        super.reset();
        cardsLeftGraveyardThisTurn.clear();
    }
}
