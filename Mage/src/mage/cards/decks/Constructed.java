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

package mage.cards.decks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import mage.cards.Card;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Constructed extends DeckValidatorImpl {

	protected List<String> banned = new ArrayList<String>();
	protected List<String> restricted = new ArrayList<String>();
	protected List<String> setCodes = new ArrayList<String>();

	public Constructed() {
		super("Constructed");
	}

	protected Constructed(String name) {
		super(name);
	}

	@Override
	public boolean validate(Deck deck) {
		boolean valid = true;
		//20091005 - 100.2a
		if (deck.getCards().size() < 60) {
			invalid.put("Deck", "Must contain at least 60 cards: has only " + deck.getCards().size() + " cards");
			valid = false;
		}
		//20091005 - 100.4a
		if (!deck.getSideboard().isEmpty() && deck.getSideboard().size() != 15) {
			invalid.put("Sideboard", "Must have 0 or 15 cards: has " + deck.getSideboard().size() + " cards");
			valid = false;
		}

		List<String> basicLandNames = new ArrayList<String>(Arrays.asList("Forest", "Island", "Mountain", "Swamp", "Plains"));
		Map<String, Integer> counts = new HashMap<String, Integer>();
		countCards(counts, deck.getCards());
		countCards(counts, deck.getSideboard());
		for (Entry<String, Integer> entry: counts.entrySet()) {
			if (entry.getValue() > 4) {
				if (!basicLandNames.contains(entry.getKey()) && !entry.getKey().equals("Relentless Rats")) {
					invalid.put(entry.getKey(), "Too many: " + entry.getValue());
					valid = false;
				}
			}
		}
		for (String bannedCard: banned) {
			if (counts.containsKey(bannedCard)) {
				invalid.put(bannedCard, "Banned");
				valid = false;
			}
		}

		for (String restrictedCard: restricted) {
			if (counts.containsKey(restrictedCard)) {
				int count = counts.get(restrictedCard);
				if (count > 1) {
					invalid.put(restrictedCard, "Restricted: " + count);
					valid = false;
				}
			}
		}
		if (!setCodes.isEmpty()) {
			for (Card card: deck.getCards()) {
				if (!setCodes.contains(card.getExpansionSetCode())) {
					if (!invalid.containsKey(card.getName())) {
						invalid.put(card.getName(), "Invalid set: " + card.getExpansionSetCode());
						valid = false;
					}
				}
			}
			for (Card card: deck.getSideboard()) {
				if (!setCodes.contains(card.getExpansionSetCode())) {
					if (!invalid.containsKey(card.getName())) {
						invalid.put(card.getName(), "Invalid set: " + card.getExpansionSetCode());
						valid = false;
					}
				}
			}
		}

		return valid;
	}

}
