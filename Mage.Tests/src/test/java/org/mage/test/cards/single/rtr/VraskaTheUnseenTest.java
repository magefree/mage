package org.mage.test.cards.single.rtr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85, Susucr
 */
public class VraskaTheUnseenTest extends CardTestPlayerBase {

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

    @Test
    public void test_OnlyCombat() {
        addCard(Zone.BATTLEFIELD, playerA, "Vraska the Unseen");
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Cinder Pyromancer", 1); // {T}: Cinder Pyromancer deals 1 damage to target player or planeswalker.

        // 1 - activate
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1:");
        checkPermanentCounters("turn 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Vraska the Unseen", CounterType.LOYALTY, 5 + 1);
        checkPermanentCount("turn 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Balduvian Bears", 1);
        checkPermanentCount("turn 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Cinder Pyromancer", 1);

        // 2 - attack and destroy
        attack(2, playerB, "Balduvian Bears", "Vraska the Unseen");
        checkPermanentCounters("turn 2", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Vraska the Unseen", CounterType.LOYALTY, 5 + 1 - 2);
        checkPermanentCount("turn 2", 2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Balduvian Bears", 0);

        // 3 - ping, no destroy
        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "{T}: {this} deals", "Vraska the Unseen");
        checkPermanentCounters("turn 2", 2, PhaseStep.END_TURN, playerA, "Vraska the Unseen", CounterType.LOYALTY, 5 + 1 - 2 - 1);
        checkPermanentCount("turn 2", 2, PhaseStep.END_TURN, playerB, "Cinder Pyromancer", 1);

        setStopAt(3, PhaseStep.UPKEEP);
        setStrictChooseMode(true);
        execute();
    }
}
