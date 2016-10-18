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
package org.mage.test.cards.control;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class GontiLordOfLuxuryEffectTest extends CardTestPlayerBase {

    /**
     * Returning to your hand a creature you own but is controlled by an
     * opponent doesn't let you replay it. Happened after I Aether Tradewinded
     * my Rashmi that an opponent cast with Gonti, Lord of Luxury (the exile
     * part could have something to do with this?). Then on my turn I couldn't
     * replay it.
     */
    @Test
    public void testCanBeCastAgain() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 8);
        // Deathtouch
        // When Gonti, Lord of Luxury enters the battlefield, look at the top four cards of target opponent's library, exile one of them face down,
        // then put the rest on the bottom of that library in a random order. For as long as that card remains exiled,
        // you may look at it, you may cast it, and you may spend mana as though it were mana of any type to cast it.
        addCard(Zone.HAND, playerA, "Gonti, Lord of Luxury", 1); // Creature {2}{B}{B}

        addCard(Zone.LIBRARY, playerB, "Rashmi, Eternities Crafter"); // Creature {2}{G}{U}
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);
        // Return target permanent you control and target permanent you don't control to their owners' hands.
        addCard(Zone.HAND, playerB, "Aether Tradewinds", 1); // Intant {2}{U}
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gonti, Lord of Luxury");
        addTarget(playerA, playerB);
        setChoice(playerA, "Rashmi, Eternities Crafter");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Rashmi, Eternities Crafter");

        castSpell(1, PhaseStep.END_TURN, playerB, "Aether Tradewinds", "Silvercoat Lion^Rashmi, Eternities Crafter");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Rashmi, Eternities Crafter");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Aether Tradewinds", 1);
        assertHandCount(playerB, "Silvercoat Lion", 1);
        assertHandCount(playerB, "Rashmi, Eternities Crafter", 0);
        assertPermanentCount(playerB, "Rashmi, Eternities Crafter", 1);

    }

}
