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

import mage.abilities.Ability;
import mage.abilities.keyword.BloodthirstAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class BloodthirstTest extends CardTestPlayerBase {

    /**
     * Tests boosting on being blocked
     */
    @Test
    public void testNormal() {
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerB, "Isao, Enlightened Bushi");

        attack(2, playerB, "Isao, Enlightened Bushi");
        block(2, playerA, "Elite Vanguard", "Isao, Enlightened Bushi");

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertPowerToughness(playerB, "Isao, Enlightened Bushi", 4, 3);
        assertPermanentCount(playerA, "Elite Vanguard", 0);
    }

    /**
     * Tests boosting on block
     */
    @Test
    public void testBloodlord() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        // Bloodthirst 3 (If an opponent was dealt damage this turn, this creature enters the battlefield with 3 +1/+1 counters)
        // Whenever you cast a Vampire creature spell, it gains bloodthirst 3
        addCard(Zone.HAND, playerA, "Bloodlord of Vaasgoth"); // {3}{B}{B}
        addCard(Zone.HAND, playerA, "Barony Vampire"); // 3/2  {2}{B}
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bloodlord of Vaasgoth");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Barony Vampire");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Barony Vampire", 1);
        Permanent baron = getPermanent("Barony Vampire", playerA);
        boolean bloodthirstFound = false;
        for (Ability ability : baron.getAbilities()) {
            if (ability instanceof BloodthirstAbility) {
                bloodthirstFound = true;
                break;
            }
        }
        Assert.assertTrue("Baron Vampire is missing the bloodthirst ability", bloodthirstFound);
        assertPermanentCount(playerA, "Bloodlord of Vaasgoth", 1);
        assertPowerToughness(playerA, "Bloodlord of Vaasgoth", 6, 6);
        assertPermanentCount(playerA, "Barony Vampire", 1);
        assertPowerToughness(playerA, "Barony Vampire", 6, 5);
    }

}
