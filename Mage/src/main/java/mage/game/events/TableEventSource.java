package mage.game.events;

import mage.cards.Cards;
import mage.cards.decks.Deck;
import mage.game.Game;
import mage.game.draft.Draft;
import mage.game.events.TableEvent.EventType;
import mage.game.match.MatchOptions;
import mage.game.tournament.MultiplayerRound;
import mage.game.tournament.TournamentPairing;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TableEventSource implements EventSource<TableEvent>, Serializable {

    protected final EventDispatcher<TableEvent> dispatcher = new EventDispatcher<TableEvent>() {
    };

    @Override
    public void addListener(Listener<TableEvent> listener) {
        dispatcher.addListener(listener);
    }

    @Override
    public void removeListener(Listener<TableEvent> listener) {
        dispatcher.removeListener(listener);
    }

    @Override
    public void removeAllListeners() {
        dispatcher.removeAllListener();
    }


    public void fireTableEvent(EventType eventType) {
        dispatcher.fireEvent(new TableEvent(eventType));
    }

    public void fireTableEvent(EventType eventType, String message, Game game) {
        dispatcher.fireEvent(new TableEvent(eventType, message, game));
    }

    public void fireTableEvent(EventType eventType, String message, boolean withTime, boolean withTurnInfo, Game game) {
        dispatcher.fireEvent(new TableEvent(eventType, message, withTime, withTurnInfo, game));
    }

    public void fireTableEvent(EventType eventType, UUID playerId, String message, Game game) {
        dispatcher.fireEvent(new TableEvent(eventType, playerId, message, game));
    }

    public void fireTableEvent(EventType eventType, String message, Exception ex, Game game) {
        dispatcher.fireEvent(new TableEvent(eventType, message, ex, game));
    }

    public void fireTableEvent(EventType eventType, String message, Draft draft) {
        dispatcher.fireEvent(new TableEvent(eventType, message, draft));
    }

    public void fireTableEvent(EventType eventType, String message, Cards cards, Game game) {
        dispatcher.fireEvent(new TableEvent(eventType, message, cards, game));
    }

    public void fireTableEvent(EventType eventType, UUID playerId, Deck deck, int timeout) {
        dispatcher.fireEvent(new TableEvent(eventType, playerId, deck, timeout));
    }

    public void fireTableEvent(EventType eventType, TournamentPairing pair, MatchOptions options) {
        dispatcher.fireEvent(new TableEvent(eventType, pair, options));
    }

    public void fireTableEvent(EventType eventType, MultiplayerRound round, MatchOptions options) {
        dispatcher.fireEvent(new TableEvent(eventType, round, options));
    }
}
