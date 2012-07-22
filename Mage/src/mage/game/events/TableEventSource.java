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
import mage.game.events.TableEvent.EventType;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentPairing;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TableEventSource implements EventSource<TableEvent>, Serializable {

    protected final EventDispatcher<TableEvent> dispatcher = new EventDispatcher<TableEvent>() {};

    @Override
    public void addListener(Listener<TableEvent> listener) {
        dispatcher.addListener(listener);
    }

    public void fireTableEvent(EventType eventType) {
        dispatcher.fireEvent(new TableEvent(eventType));
    }

    public void fireTableEvent(EventType eventType, String message, Game game) {
        dispatcher.fireEvent(new TableEvent(eventType, message, game));
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
}
