package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class GideonJuraAndRowanKenrithNextTurnTest extends CardTestPlayerBase {

    @Test
    public void test_SingleOpponentMustAttackGideonJura() {
        // +2: During target opponent's next turn, creatures that player controls attack Gideon Jura if able.
        addCard(Zone.BATTLEFIELD, playerA, "Gideon Jura");
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1); // 2/2

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2:", playerB);

        checkPermanentCounters("turn 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Gideon Jura", CounterType.LOYALTY, 6 + 2);
        checkPermanentCounters("turn 2", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Gideon Jura", CounterType.LOYALTY, 6 + 2 - 2);
        checkPermanentCounters("turn 3", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Gideon Jura", CounterType.LOYALTY, 6 + 2 - 2);
        checkPermanentCounters("turn 4", 4, PhaseStep.POSTCOMBAT_MAIN, playerA, "Gideon Jura", CounterType.LOYALTY, 6 + 2 - 2);

        setStopAt(4, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_SingleOpponentMustAttackRowanKenrith() {
        // +2: During target player's next turn, each creature that player controls attacks if able.
        addCard(Zone.BATTLEFIELD, playerA, "Rowan Kenrith");
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1); // 2/2

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2:", playerB);
        addTarget(playerB, playerA);

        checkLife("turn 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, 20);
        checkLife("turn 2", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, 20 - 2);
        checkLife("turn 3", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, 20 - 2);
        checkLife("turn 4", 4, PhaseStep.POSTCOMBAT_MAIN, playerA, 20 - 2);

        setStopAt(4, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }
}
