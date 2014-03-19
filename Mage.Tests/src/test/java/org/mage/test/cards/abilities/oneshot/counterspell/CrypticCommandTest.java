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

package org.mage.test.cards.abilities.oneshot.counterspell;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Cryptic Command
 * Instant, 1UUU
 * Choose two — Counter target spell; or return target permanent to its owner's hand; or tap all creatures your opponents control; or draw a card.
 *
 * @author LevelX2
 */
public class CrypticCommandTest extends CardTestPlayerBase {

    /**
     * Test that if command has only one target and that targets is not valid on resolution, Cryptic Command fizzeles
     * The player does not draw a card
     */
    @Test
    public void testCommand() {
        addCard(Zone.HAND, playerA, "Thoughtseize");
        addCard(Zone.HAND, playerA, "Remand");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        
        addCard(Zone.HAND, playerB, "Cryptic Command");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thoughtseize", playerB);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cryptic Command", "Thoughtseize");
        setModeChoice(playerB, "0"); // Counter target spell
        setModeChoice(playerB, "3"); // Draw a card
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Remand", "Thoughtseize", "Cryptic Command");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertHandCount(playerA, 2);
        assertGraveyardCount(playerA, 1);
        assertHandCount(playerB, 0); // Because Cryptic Command has no legal target playerB does not draw a card and has 0 cards in hand
        assertGraveyardCount(playerB, 1);
        
    }
    
}
