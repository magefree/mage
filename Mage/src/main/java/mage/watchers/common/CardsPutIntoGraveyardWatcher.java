
package mage.watchers.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.watchers.Watcher;

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
        if (event.getType() == GameEvent.EventType.UNTAP_STEP_PRE) {
            reset();
        }
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD) {
            UUID playerId = event.getPlayerId();
            if (playerId != null && game.getCard(event.getTargetId()) != null) {
                amountOfCardsThisTurn.putIfAbsent(playerId, 0);
                amountOfCardsThisTurn.compute(playerId, (k, amount) -> amount += 1);

                if (((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
                    cardsPutToGraveyardFromBattlefield.add(new MageObjectReference(event.getTargetId(), game));
                }
            }
        }
    }

    public int getAmountCardsPutToGraveyard(UUID playerId) {
        return amountOfCardsThisTurn.getOrDefault(playerId, 0);
    }

    public Set<MageObjectReference> getCardsPutToGraveyardFromBattlefield() {
        return cardsPutToGraveyardFromBattlefield;
    }

    @Override
    public void reset() {
        super.reset();
        amountOfCardsThisTurn.clear();
        cardsPutToGraveyardFromBattlefield.clear();
    }

}
