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
public class FlashbackTest extends CardTestPlayerBase {

    /**
     * Fracturing Gust is bugged. In a match against Affinity, it worked
     * properly when cast from hand. When I cast it from graveyard c/o
     * Snapcaster Mage flashback, it destroyed my opponent's Darksteel Citadels,
     * which it did not do when cast from my hand.
     */
    @Test
    public void testSnapcasterMageWithFracturingGust() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.HAND, playerA, "Snapcaster Mage", 1);
        
        // Destroy all artifacts and enchantments. You gain 2 life for each permanent destroyed this way.
        addCard(Zone.GRAVEYARD, playerA, "Fracturing Gust");

        addCard(Zone.BATTLEFIELD, playerA, "Berserkers' Onslaught", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Darksteel Citadel", 1);
        

        // When Snapcaster Mage enters the battlefield, target instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Snapcaster Mage");
        setChoice(playerA, "Fracturing Gust");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback {2}{G/W}{G/W}{G/W}"); // now snapcaster mage is died so -13/-13
        

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Snapcaster Mage", 1);
        assertGraveyardCount(playerA, "Berserkers' Onslaught", 1);
        
        assertPermanentCount(playerB, "Darksteel Citadel", 1);
                
        assertExileCount("Fracturing Gust", 1);
    }

}
