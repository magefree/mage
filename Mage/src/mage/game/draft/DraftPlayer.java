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

package mage.game.draft;

import java.util.List;
import java.util.UUID;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.decks.Deck;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DraftPlayer {

	protected UUID id;
	protected Player player;
	protected Deck deck;
	protected List<Card> booster;
	protected boolean picking;

	public DraftPlayer(Player player) {
		id = UUID.randomUUID();
		this.player = player;
		this.deck = new Deck();
	}

	public UUID getId() {
		return id;
	}

	public Player getPlayer() {
		return player;
	}

	public Deck getDeck() {
		return deck;
	}

	public void addPick(Card card) {
		deck.getSideboard().add(card);
		booster.remove(card);
		picking = false;
	}

	public void openBooster(ExpansionSet set) {
		booster = set.createBooster();
	}

	public void setBooster(List<Card> booster) {
		this.booster = booster;
	}

	public List<Card> getBooster() {
		return booster;
	}

	public void setPicking() {
		picking = true;
	}

	public boolean isPicking() {
		return picking;
	}

}
