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

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class DredgeTest extends CardTestPlayerBase {
    /**
     *  702.51. Dredge
     *   702.51a Dredge is a static ability that functions only while the card with dredge is in a player's graveyard. "Dredge N" means "As long as you have at least N cards in your library, if you would draw a card, you may instead put N cards from the top of your library into your graveyard and return this card from your graveyard to your hand."
     *   702.51b A player with fewer cards in his or her library than the number required by a dredge ability can't put any of them into his or her graveyard this way.
     */

    
    @Test
    public void testSultaiSoothsayerWithSidisiBroodTyrant() {        
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        // Whenever Sidisi, Brood Tyrant enters the battlefield or attacks, put the top three cards of your library into your graveyard.
        // Whenever one or more creature cards are put into your graveyard from your library, put a 2/2 black Zombie creature token onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Sidisi, Brood Tyrant");
        // When Sultai Soothsayer enters the battlefield, look at the top four cards of your library. 
        // Put one of them into your hand and the rest into your graveyard.
        addCard(Zone.HAND, playerA, "Sultai Soothsayer");
        
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion", 5);
        skipInitShuffling();
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sultai Soothsayer");
        addTarget(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertHandCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 3);
        
        assertPermanentCount(playerA, "Zombie", 1); // May only be one creature
        
    }

    /**
    *    Had a Sidisi, Brood Tyrant in play and dredge a Stinkweed Imp hitting 3 creatures.
    *    and Sidisi triggered 3 times instead of just one.
    */
    @Test
    public void testDredgeWithSidisiBroodTyrant() {        
        // Whenever Sidisi, Brood Tyrant enters the battlefield or attacks, put the top three cards of your library into your graveyard.
        // Whenever one or more creature cards are put into your graveyard from your library, put a 2/2 black Zombie creature token onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerB, "Sidisi, Brood Tyrant");
        // Flying
        // Whenever Stinkweed Imp deals combat damage to a creature, destroy that creature.
        // Dredge 5
        addCard(Zone.GRAVEYARD, playerB, "Stinkweed Imp");
        
        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion", 5);
        skipInitShuffling();
        
        setChoice(playerB, "Yes"); // Use Dredge

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertHandCount(playerB, "Stinkweed Imp", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 5);
        
        assertPermanentCount(playerB, "Zombie", 1); // May only be one creature
        
    }


}