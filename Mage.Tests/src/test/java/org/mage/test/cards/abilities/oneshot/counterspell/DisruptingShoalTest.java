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
package org.mage.test.cards.abilities.oneshot.counterspell;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class DisruptingShoalTest extends CardTestPlayerBase {

    /**
     * Test that Disrupting Shoal can be played with alternate casting costs
     * And the X Value is equal to the CMC of the exiled blue card
     * 
     */
    @Test
    public void testWithBlueCardsInHand() {
        addCard(Zone.HAND, playerA, "Pillarfield Ox");
        addCard(Zone.HAND, playerA, "Spell Snare");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        
        addCard(Zone.HAND, playerB, "Disrupting Shoal");
        addCard(Zone.HAND, playerB, "Mistfire Adept", 2); // blue cards with 4 CMC to pay Disrupting Shoal
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pillarfield Ox");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Disrupting Shoal", "Pillarfield Ox", "Pillarfield Ox");
        playerB.addChoice("Yes"); // use alternate costs = Mistfire Adept = CMC = 4
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spell Snare", "Disrupting Shoal", "Disrupting Shoal");
        
        
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertLife(playerA, 20); 
        assertLife(playerB, 20); 

        assertGraveyardCount(playerB,"Disrupting Shoal", 1);
        assertExileCount(playerB, 1);     // Mistfire Adept
        assertHandCount(playerB, "Mistfire Adept", 1);     // One Left
        
        assertHandCount(playerA, "Spell Snare", 1);     // Can't be cast -> no valid target
        
        assertGraveyardCount(playerA, "Pillarfield Ox", 1);

        
        
    }
}