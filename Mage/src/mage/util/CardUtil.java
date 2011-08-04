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

package mage.util;

import mage.Constants;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.Card;
import mage.game.permanent.token.Token;
import mage.util.functions.CopyFunction;
import mage.util.functions.CopyTokenFunction;
import mage.util.functions.Function;

/**
 * @author nantuko
 */
public class CardUtil {

	/**
	 * Checks whether two cards share card types.
	 *
	 * @param card1
	 * @param card2
	 * @return
	 */
	public static boolean shareTypes(Card card1, Card card2) {

		if (card1 == null || card2 == null)
			throw new IllegalArgumentException("Params can't be null");

		for (Constants.CardType type : card1.getCardType()) {
			if (card2.getCardType().contains(type)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Adjusts spell or ability cost to be paid.
	 *
	 * @param spellAbility
	 * @param reduceCount
	 */
	public static void adjustCost(SpellAbility spellAbility, int reduceCount) {
		ManaCosts<ManaCost> previousCost = spellAbility.getManaCostsToPay();
		ManaCosts<ManaCost> adjustedCost = new ManaCostsImpl<ManaCost>();
		boolean reduced = false;
		for (ManaCost manaCost : previousCost) {
			Mana mana = manaCost.getOptions().get(0);
			int colorless = mana.getColorless();
			if (!reduced && mana != null && colorless > 0) {
				if ((colorless - reduceCount) > 0) {
					int newColorless = colorless - reduceCount;
					adjustedCost.add(new GenericManaCost(newColorless));
				}
				reduced = true;
			} else {
				adjustedCost.add(manaCost);
			}
		}
		spellAbility.getManaCostsToPay().clear();
		spellAbility.getManaCostsToPay().addAll(adjustedCost);
	}

	/**
	 * Returns function that copies params\abilities from one card to another.
	 *
	 * @param target
	 */
	public static CopyFunction copyTo(Card target) {
		return new CopyFunction(target);
	}

	/**
	 * Returns function that copies params\abilities from one card to {@link Token}.
	 *
	 * @param target
	 */
	public static CopyTokenFunction copyTo(Token target) {
		return new CopyTokenFunction(target);
	}

}
