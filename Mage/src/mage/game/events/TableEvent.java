/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.game.events;

import mage.cards.Cards;
import mage.cards.decks.Deck;
import mage.game.Game;
import mage.game.draft.Draft;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentPairing;

import java.io.Serializable;
import java.util.EventObject;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TableEvent extends EventObject implements ExternalEvent, Serializable {

    public enum EventType {
        UPDATE, INFO, STATUS, REVEAL, LOOK, START_DRAFT, START_MATCH, SIDEBOARD, CONSTRUCT, SUBMIT_DECK, END, ERROR
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
    private MatchOptions options;
    private int timeout;
    private boolean withTime;

    public TableEvent(EventType eventType) {
        super(eventType);
        this.eventType = eventType;
    }

    public TableEvent(EventType eventType, String message, Game game) {
        this(eventType, message, true, game);
    }

    public TableEvent(EventType eventType, String message, boolean withTime, Game game) {
        super(game);
        this.game = game;
        this.message = message;
        this.eventType = eventType;
        this.withTime = withTime;
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

    public MatchOptions getMatchOptions() {
        return options;
    }

    public int getTimeout() {
        return timeout;
    }

    public boolean getWithTime() {
        return withTime;
    }
}
