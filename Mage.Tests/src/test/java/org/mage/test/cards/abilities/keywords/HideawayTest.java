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

package org.mage.test.cards.abilities.keywords;

import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class HideawayTest extends CardTestPlayerBase {

    /**
     * 702.74. Hideaway
     * 702.74a Hideaway represents a static ability and a triggered ability. “Hideaway” means “This
     * permanent enters the battlefield tapped” and “When this permanent enters the battlefield, look at
     * the top four cards of your library. Exile one of them face down and put the rest on the bottom of
     * your library in any order. The exiled card gains ‘Any player who has controlled the permanent
     * that exiled this card may look at this card in the exile zone.’”
     * 
     */

    /** 
     * 	Shelldock Isle
     * 	Land
     * 	Hideaway (This land enters the battlefield tapped. When it does, look at 
     *  the top four cards of your library, exile one face down, then put the 
     *  rest on the bottom of your library.)
     * 	{T}: Add {U} to your mana pool.
     * 	{U}, {T}: You may play the exiled card without paying its mana cost if a 
     *  library has twenty or fewer cards in it.
     *
     */

    @Test
    public void testHideaway() {
        addCard(Zone.HAND, playerA, "Shelldock Isle");

        this.playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shelldock Isle");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Shelldock Isle", 1);
        assertExileCount(playerA, 1);
        for (Card card :currentGame.getExile().getAllCards(currentGame)){
            Assert.assertTrue("Exiled card is not face down", card.isFaceDown(currentGame));
        }
        

    }

}
