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

package mage.deck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidatorImpl;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Constructed extends DeckValidatorImpl {

	public Constructed() {
		super("Constructed");
	}

	@Override
	public boolean validate(Deck deck) {
		//20091005 - 100.2a
		if (deck.getCards().size() < 60)
			return false;
		//20091005 - 100.4a
		if (deck.getSideboard().size() != 0 && deck.getSideboard().size() != 15)
			return false;

		List<String> basicLandNames = new ArrayList<String>(Arrays.asList("Forest", "Island", "Mountain", "Swamp", "Plains"));
		Map<String, Integer> counts = new HashMap<String, Integer>();
		countCards(counts, deck.getCards());
		countCards(counts, deck.getSideboard());
		for (Entry<String, Integer> entry: counts.entrySet()) {
			if (entry.getValue() > 4) {
				if (!basicLandNames.contains(entry.getKey())) {
					return false;
				}
			}
		}

		return true;
	}

}
