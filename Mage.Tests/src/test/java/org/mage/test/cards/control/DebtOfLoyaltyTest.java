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
package org.mage.test.cards.control;

import org.mage.test.cards.prevention.*;
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
public class DebtOfLoyaltyTest extends CardTestPlayerBase {

    @Test
    public void testDebtOfLoyaltyEffect_regen() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Tremor deals 1 damage to each creature without flying.
        addCard(Zone.HAND, playerA, "Tremor"); // Sorcery {R}
        // Regenerate target creature. You gain control of that creature if it regenerates this way.
        addCard(Zone.HAND, playerA, "Debt of Loyalty"); // Instant {1WW}

        addCard(Zone.BATTLEFIELD, playerB, "Metallic Sliver"); // 1/1
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Debt of Loyalty", "Metallic Sliver");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tremor");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Tremor", 1);

        assertPermanentCount(playerB, "Metallic Sliver", 0);
        assertGraveyardCount(playerB, "Metallic Sliver", 0);
        assertPermanentCount(playerA, "Metallic Sliver", 1);
        
        Permanent sliver = getPermanent("Metallic Sliver", playerA.getId());
        Assert.assertNotNull(sliver);

        // regenerate causes to tap
        Assert.assertTrue(sliver.isTapped());
    }

    @Test
    public void testDebtOfLoyaltyEffect_noRegen() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Tremor deals 1 damage to each creature without flying.
        addCard(Zone.HAND, playerA, "Tremor"); // Sorcery {R}
        // Regenerate target creature. You gain control of that creature if it regenerates this way.
        addCard(Zone.HAND, playerA, "Debt of Loyalty"); // Instant {1WW}

        addCard(Zone.BATTLEFIELD, playerB, "Metallic Sliver"); // 1/1
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Debt of Loyalty", "Metallic Sliver");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Metallic Sliver", 1);
        
        Permanent sliver = getPermanent("Metallic Sliver", playerB.getId());
        Assert.assertNotNull(sliver);

        // No regeneration occured.
        Assert.assertFalse(sliver.isTapped());
    }
}
