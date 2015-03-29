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
public class StormTest extends CardTestPlayerBase {

    /**
     * 702.39. Storm
     * 702.39a Storm is a triggered ability that functions on the stack. “Storm” means “When you cast this
     * spell, put a copy of it onto the stack for each other spell that was cast before it this turn. If the
     * spell has any targets, you may choose new targets for any of the copies.”
     * 702.39b If a spell has multiple instances of storm, each triggers separately.
     * 
     */
    
    /**
     * Grapeshot
     * Sorcery, 1R (2)
     * Grapeshot deals 1 damage to target creature or player.
     * Storm (When you cast this spell, copy it for each spell cast before it 
     * this turn. You may choose new targets for the copies.)
     * 
     */

    @Test
    public void testStorm1x() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Grapeshot");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grapeshot", playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 15);
    }

    @Test
    public void testStorm2x() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, "Grapeshot");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grapeshot", playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 11);
    }

    @Test
    public void testStorm3x() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Grapeshot");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grapeshot", playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 7);
    }
    
    @Test
    public void testStorm4x() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.HAND, playerA, "Grapeshot");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grapeshot", playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 3);
    }
    
    @Test
    public void testNoStorm() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Grapeshot");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grapeshot", playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 19);
    }
    
}
