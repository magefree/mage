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
 * Counts amount of cards put into graveyards of players during the current
 * turn. Also the UUIDs of cards that went to graveyard from Battlefield this
 * turn.
 *
 * @author LevelX2
 */
public class CardsPutIntoGraveyardWatcher extends Watcher {

    private final Map<UUID, Integer> amountOfCardsThisTurn = new HashMap<>();
    private final Set<MageObjectReference> cardsPutToGraveyardFromBattlefield = new HashSet<>();

    public CardsPutIntoGraveyardWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE
                || ((ZoneChangeEvent) event).getToZone() != Zone.GRAVEYARD) {
            return;
        }
        UUID playerId = event.getPlayerId();
        if (playerId == null || game.getCard(event.getTargetId()) == null) {
            return;
        }
        amountOfCardsThisTurn.compute(playerId, (k, amount) -> amount == null ? 1 : Integer.sum(amount, 1));
        if (((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
            cardsPutToGraveyardFromBattlefield.add(new MageObjectReference(((ZoneChangeEvent) event).getTarget(), game, 1));
        }
    }

    public int getAmountCardsPutToGraveyard(UUID playerId) {
        return amountOfCardsThisTurn.getOrDefault(playerId, 0);
    }

    public Set<Card> getCardsPutToGraveyardFromBattlefield(Game game) {
        return cardsPutToGraveyardFromBattlefield.stream().map(mor -> mor.getCard(game)).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    public boolean checkCardFromBattlefield(Card card, Game game) {
        return cardsPutToGraveyardFromBattlefield.stream().anyMatch(mor -> mor.refersTo(card, game));
    }

    @Override
    public void reset() {
        super.reset();
        amountOfCardsThisTurn.clear();
        cardsPutToGraveyardFromBattlefield.clear();
    }

}
