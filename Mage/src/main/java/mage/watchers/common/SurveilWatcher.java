package mage.watchers.common;

import mage.MageObjectReference;
import mage.cards.Card;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.SurveilledEvent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Who surveilled this turn and which cards they put into a graveyard while surveilling
 *
 * @author JayDi85
 */
public class SurveilWatcher extends Watcher {

    private final Set<UUID> playerIds = new HashSet<>();
    private final Map<UUID, Set<MageObjectReference>> cardsPutIntoGraveyard = new HashMap<>();

    public SurveilWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SURVEILED) {
            return;
        }
        playerIds.add(event.getPlayerId());
        if (event instanceof SurveilledEvent) {
            for (Card card : ((SurveilledEvent) event).getSurveilledToGraveyard().getCards(game)) {
                cardsPutIntoGraveyard
                        .computeIfAbsent(event.getPlayerId(), k -> new HashSet<>())
                        .add(new MageObjectReference(card.getMainCard(), game));
            }
        }
    }

    public boolean hasSurveilled(UUID playerId) {
        return playerIds.contains(playerId);
    }

    /**
     * The card was put into a graveyard while that player surveilled this turn and is still the same object
     */
    public boolean isPutIntoGraveyardBySurveil(UUID playerId, Card card, Game game) {
        Set<MageObjectReference> cards = cardsPutIntoGraveyard.get(playerId);
        return cards != null && cards.contains(new MageObjectReference(card.getMainCard(), game));
    }

    @Override
    public void reset() {
        super.reset();
        playerIds.clear();
        cardsPutIntoGraveyard.clear();
    }
}
