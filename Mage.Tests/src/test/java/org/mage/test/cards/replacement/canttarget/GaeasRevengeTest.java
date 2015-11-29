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
package org.mage.test.cards.replacement.canttarget;

import mage.abilities.keyword.TrampleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class GaeasRevengeTest extends CardTestPlayerBase {

    /**
     * Test spell
     */
    @Test
    public void testGreenCanTargetWithSpells() {
        addCard(Zone.HAND, playerA, "Titanic Growth");
        // Gaea's Revenge can't be countered.
        // Haste
        // Gaea's Revenge can't be the target of nongreen spells or abilities from nongreen sources.
        addCard(Zone.BATTLEFIELD, playerA, "Gaea's Revenge"); // 8/5    Creature - Elemental

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Titanic Growth", "Gaea's Revenge");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertPowerToughness(playerA, "Gaea's Revenge", 12, 9);
    }

    @Test
    public void testGreenCanTargetWithAnAbilitiy() {
        // Gaea's Revenge can't be countered.
        // Haste
        // Gaea's Revenge can't be the target of nongreen spells or abilities from nongreen sources.
        addCard(Zone.BATTLEFIELD, playerB, "Gaea's Revenge"); // 8/5    Creature - Elemental
        // Whenever a creature you control becomes blocked, it gets +1/+1 until end of turn.
        // {1}{G}: Target creature you control gains trample until end of turn.
        addCard(Zone.BATTLEFIELD, playerB, "Somberwald Alpha"); // 3/2    Creature - Wolf

        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{1}{G}: Target creature", "Gaea's Revenge");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAbility(playerB, "Gaea's Revenge", TrampleAbility.getInstance(), true);
        assertPowerToughness(playerB, "Gaea's Revenge", 8, 5);
    }
}
