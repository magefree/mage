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
package org.mage.test.combat;

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
public class RemoveFromCombatTest extends CardTestPlayerBase {

    /**
     * In a test game against the AI, it attacked me with a Stomping Ground
     * animated by an Ambush Commander and boosted it with the Commander's
     * second ability. I killed the Commander. The now non-creature land
     * continued attacking and dealt 3 damage to me.
     */
    @Test
    public void testLeavesCombatIfNoLongerACreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, "Lightning Blast", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Elvish Mystic", 1);

        // Forests you control are 1/1 green Elf creatures that are still lands.
        // {1}{G},Sacrifice an Elf: Target creature gets +3/+3 until end of turn.
        addCard(Zone.BATTLEFIELD, playerB, "Ambush Commander", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Stomping Ground");

        attack(2, playerB, "Stomping Ground");
        activateAbility(2, PhaseStep.DECLARE_ATTACKERS, playerB, "{1}{G},Sacrifice an Elf: Target creature gets +3/+3", "Stomping Ground");
        castSpell(2, PhaseStep.DECLARE_BLOCKERS, playerA, "Lightning Blast", "Ambush Commander");
        setStopAt(2, PhaseStep.COMBAT_DAMAGE);
        execute();

        assertGraveyardCount(playerB, "Elvish Mystic", 1);
        assertGraveyardCount(playerA, "Lightning Blast", 1);
        assertGraveyardCount(playerB, "Ambush Commander", 1);

        assertPowerToughness(playerB, "Stomping Ground", 0, 0);

        Permanent stompingGround = getPermanent("Stomping Ground", playerB);
        Assert.assertEquals("Stomping Ground has to be removed from combat", false, stompingGround.isAttacking());

        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }

}
