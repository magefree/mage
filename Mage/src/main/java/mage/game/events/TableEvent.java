package mage.game.events;

import mage.cards.Cards;
import mage.cards.decks.Deck;
import mage.game.Game;
import mage.game.draft.Draft;
import mage.game.match.MatchOptions;
import mage.game.tournament.MultiplayerRound;
import mage.game.tournament.TournamentPairing;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.EventObject;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TableEvent extends EventObject implements ExternalEvent, Serializable {

    private static final Logger logger = Logger.getLogger(TableEvent.class);

    public enum EventType {
        UPDATE, INFO, STATUS, START_DRAFT, START_MATCH, SIDEBOARD, CONSTRUCT, SUBMIT_DECK, END, END_GAME_INFO, ERROR,
        INIT_TIMER, RESUME_TIMER, PAUSE_TIMER, CHECK_STATE_PLAYERS, START_MULTIPLAYER_MATCH
    }



    private Game game;
    private Draft draft;
    private EventType eventType;
    private String message;
    private Exception ex;
    private Cards cards;
    private UUID playerId;
    private Deck deck;
    private TournamentPairing pair;
    private MultiplayerRound round;
    private MatchOptions options;
    private int timeout;
    private boolean withTime;
    private boolean withTurnInfo;

    public TableEvent(EventType eventType) {
        super(eventType);
        this.eventType = eventType;
    }

    public TableEvent(EventType eventType, String message, Game game) {
        this(eventType, message, true, game);
    }

    public TableEvent(EventType eventType, String message, boolean withTime, Game game) {
        this(eventType, message, withTime, withTime, game); // turn info with time always (exception: start turn message)
    }

    public TableEvent(EventType eventType, String message, boolean withTime, boolean withTurnInfo, Game game) {
        super(game);
        this.game = game;
        this.message = message;
        this.eventType = eventType;
        this.withTime = withTime;
        this.withTurnInfo = withTurnInfo;
    }

    public TableEvent(EventType eventType, String message, Cards cards, Game game) {
        this(eventType, message, game);
        this.cards = cards;
    }

    public TableEvent(EventType eventType, String message, Exception ex, Game game) {
        this(eventType, message, game);
        this.ex = ex;
    }

    public TableEvent(EventType eventType, UUID playerId, String message, Game game) {
        this(eventType, message, game);
        this.playerId = playerId;
    }

    public TableEvent(EventType eventType, UUID playerId, Deck deck, int timeout) {
        super(playerId);
        this.playerId = playerId;
        this.deck = deck;
        this.eventType = eventType;
        this.timeout = timeout;
    }

    public TableEvent(EventType eventType, String message, Draft draft) {
        super(draft);
        this.draft = draft;
        this.message = message;
        this.eventType = eventType;
    }

    public TableEvent(EventType eventType, TournamentPairing pair, MatchOptions options) {
        super(options);
        this.pair = pair;
        this.options = options;
        this.eventType = eventType;
    }

    public TableEvent(EventType eventType, MultiplayerRound round, MatchOptions options) {
        super(options);
        this.round = round;
        this.options = options;
        this.eventType = eventType;
    }

    public Game getGame() {
        return game;
    }

    public Draft getDraft() {
        return draft;
    }

    public EventType getEventType() {
        return eventType;
    }

    public String getMessage() {
        return message;
    }

    public Exception getException() {
        return ex;
    }

    public Cards getCards() {
        return cards;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public Deck getDeck() {
        return deck;
    }

    public TournamentPairing getPair() {
        return pair;
    }

    public MultiplayerRound getMultiplayerRound() {
        return round;
    }

    public MatchOptions getMatchOptions() {
        return options;
    }

    public int getTimeout() {
        return timeout;
    }

    public boolean getWithTime() {
        return withTime;
    }

    public boolean getWithTurnInfo() {
        return withTurnInfo;
    }
}
