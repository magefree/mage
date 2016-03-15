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
package org.mage.test.commander;

import mage.cards.Card;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.filter.FilterMana;
import mage.util.CardUtil;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander3PlayersFFA;

/**
 *
 * @author LevelX2
 */
public class CommanderColorIdentityTest extends CardTestCommander3PlayersFFA {

    @Test
    public void TestColors() {
        // Basic Colors
        Assert.assertEquals("{B}", getColorIdentityString("Sengir Vampire"));
        Assert.assertEquals("{G}", getColorIdentityString("Elvish Visionary"));
        Assert.assertEquals("{R}", getColorIdentityString("Demolish"));
        Assert.assertEquals("{U}", getColorIdentityString("Negate"));
        Assert.assertEquals("{W}", getColorIdentityString("Silvercoat Lion"));

        // Multicolor
        Assert.assertEquals("{G}{W}", getColorIdentityString("Veteran Warleader"));
        Assert.assertEquals("{B}{R}", getColorIdentityString("Forerunner of Slaughter"));
        Assert.assertEquals("{R}{U}", getColorIdentityString("Brutal Expulsion"));
        Assert.assertEquals("{B}{G}", getColorIdentityString("Catacomb Sifter"));
        Assert.assertEquals("{B}{U}", getColorIdentityString("Fathom Feeder"));

        Assert.assertEquals("{R}{W}", getColorIdentityString("Angelic Captain"));
        Assert.assertEquals("{G}{U}", getColorIdentityString("Bring to Light"));
        Assert.assertEquals("{B}{W}", getColorIdentityString("Drana's Emissary"));
        Assert.assertEquals("{G}{R}", getColorIdentityString("Grove Rumbler"));
        Assert.assertEquals("{U}{W}", getColorIdentityString("Roil Spout"));

        // Cards with colors in the rule text
        Assert.assertEquals("{B}{R}", getColorIdentityString("Fires of Undeath"));

        // Cards without casting costs
        Assert.assertEquals("{B}", getColorIdentityString("Living End"));
        Assert.assertEquals("{G}", getColorIdentityString("Hypergenesis"));

        // Phyrexian Mana
        Assert.assertEquals("{B}", getColorIdentityString("Dismember"));

        // Hybrid mana
        Assert.assertEquals("{G}{W}", getColorIdentityString("Kitchen Finks"));

        // Lands with colored activation costs
        Assert.assertEquals("{G}", getColorIdentityString("Treetop Village"));

        // Cards with extort don't use extort to determine color identity
        Assert.assertEquals("{W}", getColorIdentityString("Knight of Obligation"));

        // Two face cards
        Assert.assertEquals("{G}{R}", getColorIdentityString("Daybreak Ranger"));
        Assert.assertEquals("{R}{W}", getColorIdentityString("Archangel Avacyn"));
        Assert.assertEquals("{R}{U}", getColorIdentityString("Civilized Scholar"));

    }

    private String getColorIdentityString(String cardName) {
        CardInfo cardInfo = CardRepository.instance.findCard(cardName);
        if (cardInfo == null) {
            throw new IllegalArgumentException("Couldn't find the card " + cardName + " in the DB.");
        }
        Card card = cardInfo.getCard();
        FilterMana filterMana = CardUtil.getColorIdentity(card);
        return filterMana.toString();
    }
}
