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
package org.mage.test.commander.FFA3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander3PlayersFFA;

/**
 *
 * @author LevelX2
 */
public class SerraAscendantTest extends CardTestCommander3PlayersFFA {

    /**
     * Serra Ascendant is not working properly. Playing commander free for all,
     * and when life total was less than 30 Serra remained a 6/6.
     */
    @Test
    public void TestChangePTTo11() {

        // Lifelink (Damage dealt by this creature also causes you to gain that much life.)
        // As long as you have 30 or more life, Serra Ascendant gets +5/+5 and has flying.
        addCard(Zone.HAND, playerA, "Serra Ascendant"); // {W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        // Draw cards equal to the power of target creature you control.
        addCard(Zone.HAND, playerA, "Soul's Majesty"); // Sorcery - {4}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);

        addCard(Zone.HAND, playerB, "Lightning Bolt", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        addCard(Zone.HAND, playerC, "Lightning Bolt", 2);
        addCard(Zone.BATTLEFIELD, playerC, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Serra Ascendant");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soul's Majesty", "Serra Ascendant");

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Lightning Bolt", playerA);
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Lightning Bolt", playerA);
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerC, "Lightning Bolt", playerA);
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerC, "Lightning Bolt", playerA);

        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();

        assertGraveyardCount(playerA, "Soul's Majesty", 1);
        assertHandCount(playerA, 7); // 6 from Soul's Majesty + 1 from draw phase
        assertGraveyardCount(playerB, "Lightning Bolt", 2);
        assertGraveyardCount(playerC, "Lightning Bolt", 2);
        assertPermanentCount(playerA, "Serra Ascendant", 1);
        assertPowerToughness(playerA, "Serra Ascendant", 1, 1);
        assertLife(playerA, 28);
        assertLife(playerB, 40);
        assertLife(playerC, 40);
    }

}
