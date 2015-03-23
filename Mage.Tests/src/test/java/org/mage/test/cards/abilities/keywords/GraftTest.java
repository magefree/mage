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
public class GraftTest extends CardTestPlayerBase {

    /**
     * 702.57. Graft
     * 702.57a Graft represents both a static ability and a triggered ability. “Graft N” means “This
     * permanent enters the battlefield with N +1/+1 counters on it” and “Whenever another creature
     * enters the battlefield, if this permanent has a +1/+1 counter on it, you may move a +1/+1
     * counter from this permanent onto that creature.”
     * 702.57b If a creature has multiple instances of graft, each one works separately.
     * 
     */

    /** 
     * 	Sporeback Troll
     * 	Creature — Troll Mutant 0/0, 3G (4)
     * 	Graft 2 (This creature enters the battlefield with two +1/+1 counters on it. 
     *  Whenever another creature enters the battlefield, you may move a +1/+1 
     *  counter from this creature onto it.)
     * 	{1}{G}: Regenerate target creature with a +1/+1 counter on it.
     *
     */

    /**
     *  Cytoplast Root-Kin
     *  Creature — Elemental Mutant 0/0, 2GG (4)
     *  Graft 4 (This creature enters the battlefield with four +1/+1 counters on 
     *  it. Whenever another creature enters the battlefield, you may move a +1/+1 
     *  counter from this creature onto it.)
     *  When Cytoplast Root-Kin enters the battlefield, put a +1/+1 counter on 
     *  each other creature you control with a +1/+1 counter on it.
     *  {2}: Move a +1/+1 counter from target creature you control onto Cytoplast Root-Kin.
     * 
     */

    @Test
    public void testGraft() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Sporeback Troll");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sporeback Troll");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Sporeback Troll", 1);
        assertPowerToughness(playerA, "Sporeback Troll", 2, 2);
        assertCounterCount("Sporeback Troll", CounterType.P1P1, 2);

    }

    @Test
    public void testMoveCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);
        addCard(Zone.HAND, playerA, "Cytoplast Root-Kin");
        addCard(Zone.HAND, playerA, "Sporeback Troll");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cytoplast Root-Kin");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sporeback Troll");
        setChoice(playerA, "Yes");
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Sporeback Troll", 1);
        assertPermanentCount(playerA, "Cytoplast Root-Kin", 1);
        assertPowerToughness(playerA, "Sporeback Troll", 3, 3);
        assertPowerToughness(playerA, "Cytoplast Root-Kin", 3, 3);
        assertCounterCount("Sporeback Troll", CounterType.P1P1, 3);
        assertCounterCount("Cytoplast Root-Kin", CounterType.P1P1, 3);
    }
    
    @Test
    public void testDontMoveCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);
        addCard(Zone.HAND, playerA, "Cytoplast Root-Kin");
        addCard(Zone.HAND, playerA, "Sporeback Troll");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cytoplast Root-Kin");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sporeback Troll");
        setChoice(playerA, "No");
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Sporeback Troll", 1);
        assertPermanentCount(playerA, "Cytoplast Root-Kin", 1);
        assertPowerToughness(playerA, "Sporeback Troll", 2, 2);
        assertPowerToughness(playerA, "Cytoplast Root-Kin", 4, 4);
        assertCounterCount("Sporeback Troll", CounterType.P1P1, 2);
        assertCounterCount("Cytoplast Root-Kin", CounterType.P1P1, 4);
    }   
    
}
