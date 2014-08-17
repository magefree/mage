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

package org.mage.test.cards.single.ths;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class AshiokNightmareWaeverTest extends CardTestPlayerBase {

    /**
     * Ashiok, Nightmare Weaver {1}{U}{B}  - 3
     *
     * +2: Exile the top three cards of target opponent's library.
     * -X: Put a creature card with converted mana cost X exiled with Ashiok, Nightmare Weaver onto the battlefield under your control. That creature is a Nightmare in addition to its other types.
     * -10: Exile all cards from all opponents' hands and graveyards.);Tests Heliod get a God with devotion to white >>= 5
     */
    @Test
    public void testFirstAbility() {

        addCard(Zone.BATTLEFIELD, playerA, "Ashiok, Nightmare Weaver");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2: Exile the top three cards of target opponent's library.", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertExileCount(playerB, 3);
        assertCounterCount("Ashiok, Nightmare Weaver", CounterType.LOYALTY, 5);
    }

    @Test
    public void testSecondAbility() {

        addCard(Zone.BATTLEFIELD, playerA, "Ashiok, Nightmare Weaver");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        // Untap all creatures and lands you control during each other player's untap step.
        // You may cast creature cards as though they had flash.
        addCard(Zone.LIBRARY, playerB, "Prophet of Kruphix");
        skipInitShuffling();

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2: Exile the top three cards of target opponent's library.", playerB);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "-X: Put a creature card with converted mana cost X exiled with {this} onto the battlefield under your control. That creature is a Nightmare in addition to its other types.");

        setChoice(playerA, "X=5");
        addTarget(playerA, "Prophet of Kruphix");
        
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        
        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 17);

        assertExileCount(playerB, 2);
        assertPermanentCount(playerA, "Prophet of Kruphix", 1);
        assertGraveyardCount(playerA, "Ashiok, Nightmare Weaver", 1); // goes to grave because no more loyality counters

        assertTapped("Mountain", false); // Must be untapped because of Prophet of Kruphix
    }
}