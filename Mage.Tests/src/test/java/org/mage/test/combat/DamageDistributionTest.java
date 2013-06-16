package org.mage.test.combat;

import junit.framework.Assert;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author ayrat
 */
public class DamageDistributionTest extends CardTestPlayerBase {

    @Test
    public void testDoubleStrike() {
        addCard(Zone.BATTLEFIELD, playerA, "Warren Instigator");
        addCard(Zone.BATTLEFIELD, playerB, "Merfolk Looter");
        setLife(playerB, 4);

        attack(1, playerA, "Warren Instigator");
        block(1, playerB, "Merfolk Looter", "Warren Instigator");
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        Permanent instigator = getPermanent("Warren Instigator", playerA.getId());
        Assert.assertNotNull(instigator);
        Assert.assertTrue("Computer didn't attacked with Warren Instigator", instigator.isTapped());

        // should block and die
        assertPermanentCount(playerB, "Merfolk Looter", 0);

        // creature is blocked
        // blocker dies and second strike does nothing
        assertLife(playerB, 4);
    }

    @Test
    public void testDoubleStrikeUnblocked() {
        addCard(Zone.BATTLEFIELD, playerA, "Warren Instigator");
        setLife(playerB, 4);

        attack(1, playerA, "Warren Instigator");
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 2);
    }

    @Test
    public void testNotAttackingVersusDoubleStrike() {
        addCard(Zone.BATTLEFIELD, playerA, "Merfolk Looter");
        addCard(Zone.BATTLEFIELD, playerB, "Warren Instigator");
        setLife(playerB, 4);

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        // should block and die
        assertPermanentCount(playerA, "Merfolk Looter", 1);
        assertPermanentCount(playerB, "Warren Instigator", 1);

        // creature is blocked
        // blocker dies and second strike does nothing
        assertLife(playerB, 4);
    }

}
