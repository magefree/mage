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

import java.util.Set;
import mage.cards.decks.Deck;
import mage.constants.TournamentPlayerState;
import mage.game.result.ResultProtos.TourneyPlayerProto;
import mage.game.result.ResultProtos.TourneyQuitStatus;
import mage.players.Player;
import mage.util.TournamentUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TournamentPlayer {

    protected int points;
    protected String playerType;
    protected TournamentPlayerState state;
    protected String stateInfo;
    protected String disconnectInfo;
    protected Player player;
    protected Deck deck;
    protected String results;
    protected boolean eliminated = false;
    protected boolean quit = false;
    protected boolean doneConstructing;
    protected boolean joined = false;
    protected TourneyQuitStatus quitStatus = TourneyQuitStatus.NO_TOURNEY_QUIT;
    protected TournamentPlayer replacedTournamentPlayer;

    public TournamentPlayer(Player player, String playerType) {
        this.player = player;
        this.playerType = playerType;
        this.state = TournamentPlayerState.JOINED;
        this.stateInfo = "";
        this.disconnectInfo = "";
        this.results = "";
    }

    public Player getPlayer() {
        return player;
    }

    public String getPlayerType() {
        return playerType;
    }

    public Deck getDeck() {
        return deck;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isEliminated() {
        return eliminated;
    }

    public void setEliminated() {
        this.setDisconnectInfo("");
        this.setState(TournamentPlayerState.ELIMINATED);
        this.eliminated = true;
    }

    public boolean isJoined() {
        return joined;
    }

    public void setJoined() {
        this.joined = true;
    }

    public void setConstructing() {
        this.setState(TournamentPlayerState.CONSTRUCTING);
        this.doneConstructing = false;
    }

    public void submitDeck(Deck deck) {
        this.deck = deck; // Check if player manipulated available cards???
        this.doneConstructing = true;
        this.setState(TournamentPlayerState.WAITING);
    }

    public void updateDeck(Deck deck) {
        this.deck = deck;
    }

    public Deck generateDeck() {
        // user passed to submit deck in time
        // all all cards to deck
        deck.getCards().addAll(deck.getSideboard());
        deck.getSideboard().clear();
        // add lands to deck
        int landsPerType = 7;
        if (deck.getCards().size() >= 90) {
            landsPerType = 14;
        }
        Set<String> landSets = TournamentUtil.getLandSetCodeForDeckSets(deck.getExpansionSetCodes());
        deck.getCards().addAll(TournamentUtil.getLands("Mountain", landsPerType, landSets));
        deck.getCards().addAll(TournamentUtil.getLands("Plains", landsPerType, landSets));
        deck.getCards().addAll(TournamentUtil.getLands("Swamp", landsPerType, landSets));
        deck.getCards().addAll(TournamentUtil.getLands("Forest", landsPerType, landSets));
        deck.getCards().addAll(TournamentUtil.getLands("Island", landsPerType, landSets));
        
        this.doneConstructing = true;
        this.setState(TournamentPlayerState.WAITING);
        
        return deck;
    }


    
    public boolean isDoneConstructing() {
        return this.doneConstructing;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public String getResults() {
        return this.results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public TournamentPlayerState getState() {
        return state;
    }

    public void setState(TournamentPlayerState state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public String getDisconnectInfo() {
        return disconnectInfo;
    }

    public void setDisconnectInfo(String disconnectInfo) {
        this.disconnectInfo = disconnectInfo;
    }

    public boolean hasQuit() {
        return quit;
    }

    public void setQuit(String info, TourneyQuitStatus status) {
        setEliminated();
        this.setState(TournamentPlayerState.CANCELED);
        this.setStateInfo(info);
        this.quit = true;
        this.doneConstructing = true;
        this.quitStatus = status;
    }

    /**
     * Free resources no longer needed if tournament has ended
     * 
     */
    public void CleanUpOnTournamentEnd() {
        this.deck = null;
    }

    public void setStateAtTournamentEnd() {
        if (this.getState().equals(TournamentPlayerState.DRAFTING)
                || this.getState().equals(TournamentPlayerState.CONSTRUCTING)
                || this.getState().equals(TournamentPlayerState.DUELING)
                || this.getState().equals(TournamentPlayerState.SIDEBOARDING)
                || this.getState().equals(TournamentPlayerState.WAITING)) {
            this.setState(TournamentPlayerState.FINISHED);
        }
    }
    
    public boolean isInTournament() {
        return !this.getState().equals(TournamentPlayerState.CANCELED)
                && !this.getState().equals(TournamentPlayerState.ELIMINATED)
                && !this.getState().equals(TournamentPlayerState.FINISHED);
    }

    public TournamentPlayer getReplacedTournamentPlayer() {
        return this.replacedTournamentPlayer;
    }

    public void setReplacedTournamentPlayer(TournamentPlayer player) {
        this.replacedTournamentPlayer = player;
    }

    public TourneyPlayerProto toProto() {
        return TourneyPlayerProto.newBuilder()
                .setName(this.player.getName())
                .setPlayerType(this.playerType)
                .setQuit(this.quitStatus)
                .build();
    }
}

