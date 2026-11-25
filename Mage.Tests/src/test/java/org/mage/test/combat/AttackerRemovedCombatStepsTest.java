package org.mage.test.combat;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * CR 508.8: "If no creatures are declared as attackers or put onto the
 * battlefield attacking, skip the declare blockers and combat damage steps."
 * <p>
 * A creature that was declared as an attacker and then left combat does not
 * un-declare the attack, so both steps still happen with priority.
 */
public class AttackerRemovedCombatStepsTest extends CardTestPlayerBase {

    @Test
    public void testOnlyAttackerSacrificedStillGetsBlockersAndDamageSteps() {
        // Whenever Daemogoth Titan attacks or blocks, sacrifice a creature.
        addCard(Zone.BATTLEFIELD, playerA, "Daemogoth Titan"); // 11/10

        attack(1, playerA, "Daemogoth Titan", playerB);
        // Its own attack trigger resolves before blockers; it is the only creature.
        setChoice(playerA, "Daemogoth Titan");

        // The steps must still run even though no attacker is left. A skipped step
        // leaves these checks queued and the test fails on the unused actions.
        checkGraveyardCount("titan sacrificed by its trigger", 1, PhaseStep.DECLARE_BLOCKERS, playerA, "Daemogoth Titan", 1);
        checkLife("declare blockers step still has priority", 1, PhaseStep.DECLARE_BLOCKERS, playerB, 20);
        checkLife("combat damage step still has priority", 1, PhaseStep.COMBAT_DAMAGE, playerB, 20);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Daemogoth Titan", 1);
        assertLife(playerB, 20);
    }
}
