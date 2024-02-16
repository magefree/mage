package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class MartyrsOfKorlisTest extends CardTestPlayerBase {

    // Martyrs of Korlis 1/6
    // As long as Martyrs of Korlis is untapped, all damage that would be dealt to you by artifacts is dealt to Martyrs of Korlis instead.

    @Test
    public void test_PreventDamageToGideonOnYourTurn() {
        addCard(Zone.BATTLEFIELD, playerB, "Martyrs of Korlis");
        addCard(Zone.BATTLEFIELD, playerA, "Alloy Myr"); // 2/2

        // with redirect
        checkDamage("turn 1 before", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Martyrs of Korlis", 0);
        checkLife("turn 1 before", 1, PhaseStep.PRECOMBAT_MAIN, playerB, 20);
        attack(1, playerA, "Alloy Myr", playerB);
        checkDamage("turn 1 after", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Martyrs of Korlis", 2);
        checkLife("turn 1 after", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, 20);

        attack(2, playerB, "Martyrs of Korlis", playerA);

        // without redirect
        checkDamage("turn 3 before", 3, PhaseStep.PRECOMBAT_MAIN, playerB, "Martyrs of Korlis", 0);
        checkLife("turn 3 before", 3, PhaseStep.PRECOMBAT_MAIN, playerB, 20);
        attack(3, playerA, "Alloy Myr", playerB);
        checkDamage("turn 3 after", 3, PhaseStep.POSTCOMBAT_MAIN, playerB, "Martyrs of Korlis", 0);
        checkLife("turn 3 after", 3, PhaseStep.POSTCOMBAT_MAIN, playerB, 20 - 2);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
    }
}
