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
public class ModularTest extends CardTestPlayerBase {

    /**
     * 702.42. Modular 702.42a Modular represents both a static ability and a
     * triggered ability. “Modular N” means “This permanent enters the
     * battlefield with N +1/+1 counters on it” and “When this permanent is put
     * into a graveyard from the battlefield, you may put a +1/+1 counter on
     * target artifact creature for each +1/+1 counter on this permanent.”
     * 702.42b If a creature has multiple instances of modular, each one works
     * separately.
     *
     */
    /**
     * Arcbound Bruiser Artifact Creature — Golem 0/0, 5 (5) Modular 3 (This
     * enters the battlefield with three +1/+1 counters on it. When it dies, you
     * may put its +1/+1 counters on target artifact creature.)
     *
     */
    /**
     * Arcbound Hybrid Artifact Creature — Beast 0/0, 4 (4) Haste Modular 2
     * (This enters the battlefield with two +1/+1 counters on it. When it dies,
     * you may put its +1/+1 counters on target artifact creature.)
     *
     */
    @Test
    public void testModularEnters() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.HAND, playerA, "Arcbound Bruiser");
        addCard(Zone.HAND, playerA, "Arcbound Hybrid");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arcbound Bruiser");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arcbound Hybrid");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Arcbound Bruiser", 1);
        assertPermanentCount(playerA, "Arcbound Hybrid", 1);
        assertPowerToughness(playerA, "Arcbound Bruiser", 3, 3);
        assertPowerToughness(playerA, "Arcbound Hybrid", 2, 2);

    }

    @Test
    public void testModularLeaves() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Arcbound Bruiser");
        addCard(Zone.HAND, playerA, "Arcbound Hybrid");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arcbound Bruiser");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arcbound Hybrid");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Arcbound Bruiser");
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        setChoice(playerA, "Yes");
        execute();

        assertPermanentCount(playerA, "Arcbound Bruiser", 0);
        assertPermanentCount(playerA, "Arcbound Hybrid", 1);
        assertGraveyardCount(playerA, "Arcbound Bruiser", 1);
        assertPowerToughness(playerA, "Arcbound Hybrid", 5, 5);

    }

    /**
     * My Inkmoth Nexus was in creature "form". My Arcbound Ravager died. I
     * could not transfer his counters on my Inkmoth "creature".
     */
    @Test
    public void testInkmothNexus() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Inkmoth Nexus");
        addCard(Zone.HAND, playerA, "Arcbound Ravager");

        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arcbound Ravager");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}: {this} becomes");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Arcbound Ravager");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Arcbound Ravager", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);

        assertCounterCount("Inkmoth Nexus", CounterType.P1P1, 1);

    }

}
