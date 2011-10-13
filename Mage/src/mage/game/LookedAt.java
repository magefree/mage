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

package mage.game;

import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.util.Copyable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author nantuko
 */
public class LookedAt extends HashMap<String, Cards> implements Serializable, Copyable<LookedAt> {

	public LookedAt() {	}

	public LookedAt(final LookedAt lookedAt) {
        for (Map.Entry<String, Cards> entry: lookedAt.entrySet()) {
            this.put(entry.getKey(), entry.getValue().copy());
		}
	}

	public void add(String name, Card card) {
		this.get(name).add(card);
	}

	public void add(String name, Cards cards) {
		if (!this.containsKey(name))
			createLookedAt(name);
		this.put(name, cards.copy());
	}

	public Cards createLookedAt(String name) {
		if (!this.containsKey(name)) {
			this.put(name, new CardsImpl());
		}
		return this.get(name);
	}

	public void reset() {
		this.clear();
	}

	public Card getCard(UUID cardId, Game game) {
		for (Cards cards: this.values()) {
			if (cards.contains(cardId))
				return game.getCard(cardId);
		}
		return null;
	}

	@Override
	public LookedAt copy() {
		return new LookedAt(this);
	}
}
