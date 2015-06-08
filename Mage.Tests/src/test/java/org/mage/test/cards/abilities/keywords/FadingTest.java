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
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class FadingTest extends CardTestPlayerBase {

    /**
     * 702.31. Fading
     * 702.31a Fading is a keyword that represents two abilities. “Fading N” means “This permanent
     * enters the battlefield with N fade counters on it” and “At the beginning of your upkeep, remove
     * a fade counter from this permanent. If you can’t, sacrifice the permanent.”
     */

    /** 
     * 	Blastoderm 
     * 	Creature — Beast 5/5, 2GG (4)
     * 	Shroud (This creature can't be the target of spells or abilities.)
     * 	Fading 3 (This creature enters the battlefield with three fade counters 
     *  on it. At the beginning of your upkeep, remove a fade counter from it. 
     *  If you can't, sacrifice it.)
     *
     */

    @Test
    public void testFading() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Blastoderm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blastoderm");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Blastoderm", 1);
        this.assertCounterCount("Blastoderm", CounterType.FADE, 3);

    }

    @Test
    public void testFades() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Blastoderm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blastoderm");

        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Blastoderm", 1);
        this.assertCounterCount("Blastoderm", CounterType.FADE, 1);

    }

    @Test
    public void testFadesAway() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Blastoderm");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blastoderm");

        setStopAt(9, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Blastoderm", 0);
        assertGraveyardCount(playerA, "Blastoderm", 1);

    }
    
}
