package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class VraskaTheUnseenNextTurnTest extends CardTestPlayerBase {

    @Test
    public void test_SingleOpponentMustAttack() {
        // +1: Until your next turn, whenever a creature deals combat damage to Vraska the Unseen, destroy that creature.
        addCard(Zone.BATTLEFIELD, playerA, "Vraska the Unseen");
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 3); // 2/2

        // 1 - activate
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1:");
        checkPermanentCounters("turn 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Vraska the Unseen", CounterType.LOYALTY, 5 + 1);
        checkPermanentCount("turn 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Balduvian Bears", 3);

        // 2 - attack and destroy
        attack(2, playerB, "Balduvian Bears", "Vraska the Unseen");
        checkPermanentCounters("turn 2", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Vraska the Unseen", CounterType.LOYALTY, 5 + 1 - 2);
        checkPermanentCount("turn 2", 2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Balduvian Bears", 3 - 1);

        // 3 - nothing
        checkPermanentCounters("turn 3", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Vraska the Unseen", CounterType.LOYALTY, 5 + 1 - 2);
        checkPermanentCount("turn 3", 3, PhaseStep.POSTCOMBAT_MAIN, playerB, "Balduvian Bears", 3 - 1);

        // 4 - attack and DO NOT destroy
        checkPermanentCounters("turn 4", 4, PhaseStep.POSTCOMBAT_MAIN, playerA, "Vraska the Unseen", CounterType.LOYALTY, 5 + 1 - 2 * 2);
        attack(4, playerB, "Balduvian Bears", "Vraska the Unseen");
        checkPermanentCount("turn 4", 4, PhaseStep.POSTCOMBAT_MAIN, playerB, "Balduvian Bears", 3 - 1);

        setStopAt(4, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }
}
