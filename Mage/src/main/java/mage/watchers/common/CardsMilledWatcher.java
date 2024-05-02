package mage.watchers.common;

import mage.MageObjectReference;
import mage.cards.Card;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.MilledCardEvent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * Track MOR of mainCard milled this turn.
 *
 * @author Susucr
 */
public class CardsMilledWatcher extends Watcher {

    // set of Cards (the main card's mor) milled this turn.
    private final Set<MageObjectReference> milledThisTurn = new HashSet<>();

    public CardsMilledWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.MILLED_CARD) {
            return;
        }
        Card card = ((MilledCardEvent) event).getCard(game);
        if (card == null) {
            return;
        }
        Card mainCard = card.getMainCard();
        if (game.getState().getZone(mainCard.getId()) != Zone.GRAVEYARD) {
            // Ensure that the current zone is indeed the graveyard
            return;
        }
        milledThisTurn.add(new MageObjectReference(mainCard, game));
    }

    @Override
    public void reset() {
        super.reset();
        milledThisTurn.clear();
    }

    public static boolean wasMilledThisTurn(MageObjectReference morMainCard, Game game) {
        CardsMilledWatcher watcher = game.getState().getWatcher(CardsMilledWatcher.class);
        return watcher != null && watcher
                .milledThisTurn
                .contains(morMainCard);
    }
}
