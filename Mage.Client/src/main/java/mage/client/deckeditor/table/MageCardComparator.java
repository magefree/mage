/*  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
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

package mage.client.deckeditor.table;

import java.util.Comparator;

import mage.cards.MageCard;
import mage.view.CardView;

/**
 * {@link MageCard} comparator. Used to sort cards in Deck Editor Table View pane.
 * 
 * @author nantuko
 */
public class MageCardComparator implements Comparator<CardView> {
	private final int column;
	private boolean ascending;

	public MageCardComparator(int column, boolean ascending) {
		this.column = column;
		this.ascending = ascending;
	}

	public int compare(CardView a, CardView b) {
		Comparable aCom = null;
		Comparable bCom = null;

		if (column == 0)// #skip
		{
			aCom = Integer.valueOf(1);
			bCom = Integer.valueOf(1);
		} else if (column == 1)// Name
		{
			aCom = a.getName();
			bCom = b.getName();
			if (aCom.equals(bCom) && a.getExpansionSetCode().equals(b.getExpansionSetCode())) {
				aCom = a.getCardNumber();
				bCom = b.getCardNumber();
			}
		} else if (column == 2)// Cost
		{
			aCom = a.getConvertedManaCost();
			bCom = b.getConvertedManaCost();
		} else if (column == 3)// Color
		{
			aCom = CardHelper.getColor(a);
			bCom = CardHelper.getColor(b);
		} else if (column == 4)// Type
		{
			aCom = CardHelper.getType(a);
			bCom = CardHelper.getType(b);
		} else if (column == 5)// Stats, attack and defense
		{
			aCom = new Float(-1);
			bCom = new Float(-1);

			if (CardHelper.isCreature(a))
				aCom = new Float(a.getPower() + "." + a.getToughness());
			if (CardHelper.isCreature(b))
				bCom = new Float(b.getPower() + "." + b.getToughness());
		} else if (column == 6)// Rarity
		{
			aCom = a.getRarity().toString();
			bCom = b.getRarity().toString();
		} else if (column == 7)// Set name
		{
			aCom = a.getExpansionSetCode();
			bCom = b.getExpansionSetCode();
		}

		if (ascending)
			return aCom.compareTo(bCom);
		else
			return bCom.compareTo(aCom);
	}// compare()
}
