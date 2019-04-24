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
 * {@link MageCard} comparator. Used to sort cards in Deck Editor Table View
 * pane.
 *
 * @author nantuko
 */
public class MageCardComparator implements Comparator<CardView> {

    private final int column;
    private final boolean ascending;

    public MageCardComparator(int column, boolean ascending) {
        this.column = column;
        this.ascending = ascending;
    }

    @Override
    public int compare(CardView a, CardView b) {
        Comparable aCom = null;
        Comparable bCom = null;

        switch (column) {
            // #skip
            case 0:
                aCom = 1;
                bCom = 1;
                break;
            // Name
            case 1:
                aCom = a.getName();
                bCom = b.getName();
                if (aCom.equals(bCom) && a.getExpansionSetCode().equals(b.getExpansionSetCode())) {
                    aCom = a.getCardNumber();
                    bCom = b.getCardNumber();
                }
                break;
            // Cost
            case 2:
                aCom = a.getConvertedManaCost();
                bCom = b.getConvertedManaCost();
                break;
            // Color
            case 3:
                aCom = a.getColorText();
                bCom = b.getColorText();
                break;
            // Type
            case 4:
                aCom = a.getTypeText();
                bCom = b.getTypeText();
                break;
            // Stats, attack and defense
            case 5:
                aCom = (float) -1;
                bCom = (float) -1;
                if (a.isCreature()) {
                    aCom = new Float(a.getPower() + '.' + (a.getToughness().startsWith("-") ? "0" : a.getToughness()));
                }
                if (b.isCreature()) {
                    bCom = new Float(b.getPower() + '.' + (b.getToughness().startsWith("-") ? "0" : b.getToughness()));
                }
                break;
            // Rarity
            case 6:
                aCom = a.getRarity().getSorting();
                bCom = b.getRarity().getSorting();
                break;
            // Set name
            case 7:
                aCom = a.getExpansionSetCode();
                bCom = b.getExpansionSetCode();
                break;
            case 8:
                aCom = Integer.parseInt(a.getCardNumber().replaceAll("[\\D]", ""));
                bCom = Integer.parseInt(b.getCardNumber().replaceAll("[\\D]", ""));
                break;
            default:
                break;
        }

        if (ascending) {
            return aCom.compareTo(bCom);
        } else {
            return bCom.compareTo(aCom);
        }
    }// compare()
}
