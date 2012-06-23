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

package mage.client.deckeditor.table;

import mage.Constants.CardType;
import mage.cards.MageCard;
import mage.view.CardView;

/**
 * Helper methods for {@link MageCard}.
 * 
 * @author nantuko
 */
public class CardHelper {
    private CardHelper() {
    }

    public static String getColor(CardView c) {
        if (c.getColor().getColorCount() == 0) return "Colorless";
        else if (c.getColor().getColorCount() > 1) return "Gold";
        else if (c.getColor().isBlack()) return "Black";
        else if (c.getColor().isBlue()) return "Blue";
        else if (c.getColor().isWhite()) return "White";
        else if (c.getColor().isGreen()) return "Green";
        else if (c.getColor().isRed()) return "Red";
        return "";
    }

    public static String getType(CardView c) {
        StringBuilder type = new StringBuilder();
        for (String superType : c.getSuperTypes()) {
            type.append(superType);
            type.append(" ");
        }
        for (CardType cardType : c.getCardTypes()) {
            type.append(cardType.toString());
            type.append(" ");
        }
        if (c.getSubTypes().size() > 0) {
            type.append("- ");
            for (String subType : c.getSubTypes()) {
                type.append(subType);
                type.append(" ");
            }
        }
        return type.toString();
    }

    public static boolean isCreature(CardView c) {
        return c.getCardTypes().contains(CardType.CREATURE);
    }
}
