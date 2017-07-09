/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.game.match;

import java.io.Serializable;
import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MatchPlayer implements Serializable {

    private static final long serialVersionUID = 42L;

    private int wins;
    private int winsNeeded;
    private boolean matchWinner;

    private Deck deck;
    private Player player;
    private final String name;

    private boolean quit;
    //private final boolean timerTimeout;
    private boolean doneSideboarding;
    private int priorityTimeLeft;

    public MatchPlayer(Player player, Deck deck, Match match) {
        this.player = player;
        this.deck = deck;
        this.wins = 0;
        this.winsNeeded = match.getWinsNeeded();
        this.doneSideboarding = true;
        this.quit = false;
        //this.timerTimeout = false;
        this.name = player.getName();
        this.matchWinner = false;
    }

    public int getPriorityTimeLeft() {
        return priorityTimeLeft;
    }

    public void setPriorityTimeLeft(int priorityTimeLeft) {
        this.priorityTimeLeft = priorityTimeLeft;
    }

    public int getWins() {
        return wins;
    }

    public int getWinsNeeded() {
        return winsNeeded;
    }

    public void addWin() {
        this.wins++;
    }

    public Deck getDeck() {
        return deck;
    }

    public void submitDeck(Deck deck) {
        this.deck = deck;
        this.doneSideboarding = true;
    }

    public void updateDeck(Deck deck) {
        if (this.deck != null) {
            // preserver deck name, important for Tiny Leaders format
            deck.setName(this.getDeck().getName());
            // preserve the original deck hash code before sideboarding to give no information if cards were swapped
            deck.setDeckHashCode(this.getDeck().getDeckHashCode());
        }
        this.deck = deck;
    }

    public Deck generateDeck() {
        //TODO: improve this
        while (deck.getCards().size() < 40 && !deck.getSideboard().isEmpty()) {
            Card card = deck.getSideboard().iterator().next();
            deck.getCards().add(card);
            deck.getSideboard().remove(card);
        }
        return deck;
    }

    public Player getPlayer() {
        return player;
    }

    public void setSideboarding() {
        this.doneSideboarding = false;
    }

    public boolean isDoneSideboarding() {
        return this.doneSideboarding;
    }

    public boolean hasQuit() {
        return quit;
    }

    public void setQuit(boolean quit) {
        this.doneSideboarding = true;
        this.quit = quit;
    }

    public boolean isMatchWinner() {
        return matchWinner;
    }

    public void setMatchWinner(boolean matchWinner) {
        this.matchWinner = matchWinner;
    }

    public void cleanUpOnMatchEnd() {
        // Free resources that are not needed after match end
        this.deck = null;
        this.player.cleanUpOnMatchEnd();
        // this.player = null;
    }

    public void cleanUp() {
        // Free resources that are not needed after match end
        this.player = null;
    }

    public String getName() {
        return name;
    }

}
