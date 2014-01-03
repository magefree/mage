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

import mage.abilities.keyword.HexproofAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class HexproofTest extends CardTestPlayerBase {

    /**
     * Tests one target gets hexproof
     */
    @Test
    public void testOneTargetOneGainingHexproof() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Elder of Laurels");
        addCard(Zone.HAND, playerA, "Ranger's Guile");

        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.HAND, playerB, "Into the Void");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Into the Void", "Elder of Laurels");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Ranger's Guile", "Elder of Laurels");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        // because of hexproof the Elder should be onto the battlefield
        assertPermanentCount(playerA, "Elder of Laurels", 1);
        assertPowerToughness(playerA, "Elder of Laurels", 3, 4);
        assertAbility(playerA, "Elder of Laurels", HexproofAbility.getInstance(), true);
    }
    /**
     * Tests one target gets hexproof
     */
    @Test
    public void testTwoTargetsOneGainingHexproof() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Elder of Laurels");
        addCard(Zone.BATTLEFIELD, playerA, "Arbor Elf");
        addCard(Zone.HAND, playerA, "Ranger's Guile");

        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.HAND, playerB, "Into the Void");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Into the Void", "Elder of Laurels^Arbor Elf");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Ranger's Guile", "Elder of Laurels");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        // because of hexproof the Elder should be onto the battlefield
        assertPermanentCount(playerA, "Elder of Laurels", 1);
        assertPowerToughness(playerA, "Elder of Laurels", 3, 4);
        assertAbility(playerA, "Elder of Laurels", HexproofAbility.getInstance(), true);
        assertPermanentCount(playerA, "Arbor Elf", 0);
    }
}
