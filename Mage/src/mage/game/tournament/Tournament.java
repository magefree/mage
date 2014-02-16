/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
 * 
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 * 
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 * 
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 * 
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

package mage.game.tournament;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import mage.cards.ExpansionSet;
import mage.cards.decks.Deck;
import mage.game.events.Listener;
import mage.game.events.PlayerQueryEvent;
import mage.game.events.TableEvent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public interface Tournament {

    UUID getId();
    void addPlayer(Player player, String playerType);
    void removePlayer(UUID playerId);
    TournamentPlayer getPlayer(UUID playerId);
    Collection<TournamentPlayer> getPlayers();
    Collection<Round> getRounds();
    List<ExpansionSet> getSets();

    void updateResults();
    void setBoosterInfo(String setInfo);
    /**
     * Gives back a String that shows the included sets (e.g. "3xRTR" or "1xDGM 1xGTC 1xRTR") or cube name
     * @return String
     */
    String getBoosterInfo();
    void submitDeck(UUID playerId, Deck deck);
    void updateDeck(UUID playerId, Deck deck);
    void autoSubmit(UUID playerId, Deck deck);
    boolean allJoined();
    boolean isDoneConstructing();
    void quit(UUID playerId);
    void leave(UUID playerId);
    void nextStep();

    void addTableEventListener(Listener<TableEvent> listener);
    void addPlayerQueryEventListener(Listener<PlayerQueryEvent> listener);
    void fireConstructEvent(UUID playerId);

    TournamentOptions getOptions();
    
    // tournament times
    Date getStartTime();
    Date getEndTime();
    // tournament type
    TournamentType getTournamentType(); 
    void setTournamentType(TournamentType tournamentType);

    int getNumberRounds();
}
