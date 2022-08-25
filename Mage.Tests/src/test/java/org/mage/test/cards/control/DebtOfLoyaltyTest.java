
package org.mage.test.cards.control;

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
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
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
