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
package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author JayDi85
 */
public class MasterThiefTest extends CardTestPlayerBase {

    @Test
    public void testMasterThief_GetControlOnEnterBattlefield() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, "Master Thief", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Accorder's Shield", 1);

        // cast and get control of shield
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Master Thief");
        addTarget(playerB, "Accorder's Shield");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Island", 5);
        assertPermanentCount(playerA, "Master Thief", 1);
        assertPermanentCount(playerA, "Accorder's Shield", 1);

        assertPermanentCount(playerB, 0);
    }

    @Test
    public void testMasterThief_LostControlOnSacrifice() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);
        addCard(Zone.HAND, playerA, "Master Thief", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Accorder's Shield", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Bearer of the Heavens", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Ashnod's Altar", 1);

        // cast and get control of shield
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Master Thief");
        addTarget(playerB, "Accorder's Shield");

        // sacrifice Master Thief -- must lost control
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice a creature");
        addTarget(playerA, "Master Thief");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Island", 10);
        assertPermanentCount(playerA, "Master Thief", 0);
        assertPermanentCount(playerA, "Accorder's Shield", 0);
        assertPermanentCount(playerA, "Ashnod's Altar", 1);
        assertPermanentCount(playerA, "Bearer of the Heavens", 1);
        assertPowerToughness(playerA, "Bearer of the Heavens", 10, 10);

        assertPermanentCount(playerB, "Accorder's Shield", 1);
        assertPermanentCount(playerB, 1);
    }

    @Test
    public void testMasterThief_LostControlOnSacrificeButArtifactAttached() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);
        addCard(Zone.HAND, playerA, "Master Thief", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Accorder's Shield", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Bearer of the Heavens", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Ashnod's Altar", 1);

        // cast and get control of shield
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Master Thief");
        addTarget(playerB, "Accorder's Shield");

        // attach and boost
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Equip {3}");
        addTarget(playerA, "Bearer of the Heavens");

        // sacrifice Master Thief -- must lost control, but attached and boosted
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice a creature");
        addTarget(playerA, "Master Thief");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Island", 10);
        assertPermanentCount(playerA, "Master Thief", 0);
        assertPermanentCount(playerA, "Accorder's Shield", 0);
        assertPermanentCount(playerA, "Ashnod's Altar", 1);
        assertPermanentCount(playerA, "Bearer of the Heavens", 1);
        assertPowerToughness(playerA, "Bearer of the Heavens", 10, 10 + 3);

        assertPermanentCount(playerB, "Accorder's Shield", 1);
        assertPermanentCount(playerB, 1);
    }
}
