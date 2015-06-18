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
package org.mage.test.cards.abilities.oneshot.counter;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class MovingCounterTest extends CardTestPlayerBase {

    /**
     * I'm having an issue when using Bioshift to move only a portion of
     * counters to another creature. When I attempt to do this, it moves all of
     * the counters (and in some cases with my Simic deck) kills the creature.
     */
    @Test
    public void testCantBeCounteredNormal() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        // Move any number of +1/+1 counters from target creature onto another target creature with the same controller.
        addCard(Zone.HAND, playerA, "Bioshift", 1);
        
        // Protean Hydra enters the battlefield with X +1/+1 counters on it.        
        // If damage would be dealt to Protean Hydra, prevent that damage and remove that many +1/+1 counters from it.
        // Whenever a +1/+1 counter is removed from Protean Hydra, put two +1/+1 counters on it at the beginning of the next end step.        
        addCard(Zone.HAND, playerA, "Protean Hydra", 1);


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Protean Hydra");
        setChoice(playerA, "X=4");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Bioshift", "Protean Hydra^Silvercoat Lion");
        setChoice(playerA, "X=2");

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertGraveyardCount(playerA, "Bioshift", 1);
        
        assertPermanentCount(playerA, "Silvercoat Lion", 1);        
        assertPowerToughness(playerA, "Silvercoat Lion", 4, 4); // added 2 counters

        assertPermanentCount(playerA, "Protean Hydra", 1);        
        assertPowerToughness(playerA, "Protean Hydra", 6, 6); // started with 4, removed 2, added 4 at end = 6
        

    }

}