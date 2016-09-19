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
package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class PanharmoniconTest extends CardTestPlayerBase {

    /**
     * Check that Panharmonicon adds EtB triggers correctly.
     *
     */
    @Test
    public void testAddsTrigger() {
        // If an artifact or creature entering the battlefield causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        addCard(Zone.BATTLEFIELD, playerA, "Panharmonicon");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Whenever another creature enters the battlefield, you gain 1 life.
        addCard(Zone.HAND, playerA, "Soul Warden");
        // When Devout Monk enters the battlefield, you gain 1 life.
        addCard(Zone.HAND, playerA, "Devout Monk");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soul Warden");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Devout Monk"); // Life: 20 + 2*1 + 2*1 = 24

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 24);
    }

    /**
     * Check that Panharmonicon doesn't add to opponents' triggers.
     *
     */
    @Test
    public void testDoesntAddOpponentsTriggers() {
        // If an artifact or creature entering the battlefield causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        addCard(Zone.BATTLEFIELD, playerA, "Panharmonicon");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        // Whenever another creature enters the battlefield, you gain 1 life.
        addCard(Zone.HAND, playerB, "Soul Warden");
        // When Devout Monk enters the battlefield, you gain 1 life.
        addCard(Zone.HAND, playerB, "Devout Monk");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Soul Warden");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Devout Monk"); // Life: 20 + 1 + 1 = 22

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 22);
    }

    /**
     * Check that Panharmonicon doesn't add to lands triggers.
     *
     */
    @Test
    public void testDoesntAddLandsTriggers() {
        // If an artifact or creature entering the battlefield causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        addCard(Zone.BATTLEFIELD, playerA, "Panharmonicon");
        // When Radiant Fountain enters the battlefield, you gain 2 life.
        addCard(Zone.HAND, playerA, "Radiant Fountain");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Radiant Fountain"); // Life: 20 + 2 = 22

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 22);
    }
}
