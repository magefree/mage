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
package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class EldraziMimicTest extends CardTestPlayerBase {

    /**
     * Eldrazi Mimic also did not copy the last known P/T of a Drowner of Hope
     * that was killed with the trigger on the stack.
     *
     */
    @Test
    public void testCopyIfPermanentIsGone() {
        // Devoid (This card has no color.)
        // Flying
        // Whenever you cast a colorless spell, target opponent exiles the top card of his or her library.
        addCard(Zone.HAND, playerA, "Thought Harvester", 1); // {3}{U} 2/4
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        // Whenever another colorless creature enters the battlefield under your control, you may have the base power and toughness of Eldrazi Mimic
        // become that creature's power and toughness until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Eldrazi Mimic", 1); // 2/1

        // Destroy target nonartifact, nonblack creature. It can't be regenerated.
        addCard(Zone.HAND, playerB, "Terror", 1); // {1}{B}
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thought Harvester");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Terror", "Thought Harvester");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Terror", 1);
        assertGraveyardCount(playerA, "Thought Harvester", 1);

        assertPermanentCount(playerA, "Eldrazi Mimic", 1);
        assertPowerToughness(playerA, "Eldrazi Mimic", 2, 4);

    }

    /**
     * Eldrazi Mimic ability to change it's health and power on another
     * creatures entering the battlefield doesn't work after update.
     */
    @Test
    public void testNormalCopy() {
        addCard(Zone.HAND, playerA, "Composite Golem", 1); // 4/4
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        // Whenever another colorless creature enters the battlefield under your control, you may have the base power and toughness of Eldrazi Mimic
        // become that creature's power and toughness until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Eldrazi Mimic", 1); // 2/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Composite Golem");
        setChoice(playerA, "Yes");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Composite Golem", 4, 4);
        assertPowerToughness(playerA, "Eldrazi Mimic", 4, 4);

    }
}
