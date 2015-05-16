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
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class DelayedTriggerTest extends CardTestPlayerBase {

    /**
     * Activated Sunforger's unequip ability and cast Otherworldly Journey for free, targeting Cathodion. 
     * When Cathodion returned to the battlefield, it did not get a +1/+1 counter. *
     * 
     */
    @Test
    public void testOtherworldlyJourney1() {
        // Exile target creature. At the beginning of the next end step, return that card to the battlefield under its owner's control with a +1/+1 counter on it.
        addCard(Zone.HAND, playerA, "Otherworldly Journey");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Otherworldly Journey", "Silvercoat Lion");

        setStopAt(2, PhaseStep.DRAW);
        execute();

        assertGraveyardCount(playerA, "Otherworldly Journey", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 3, 3);
        
    }
    
    @Test
    public void testOtherworldlyJourney2() {
        // Exile target creature. At the beginning of the next end step, return that card to the battlefield under its owner's control with a +1/+1 counter on it.
        addCard(Zone.HAND, playerA, "Otherworldly Journey");
        addCard(Zone.HAND, playerA, "Cloudshift");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Otherworldly Journey", "Silvercoat Lion");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", "Silvercoat Lion");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Otherworldly Journey", 1);
        assertGraveyardCount(playerA, "Cloudshift", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2); // one one use of +1/+1
        
    }
}
