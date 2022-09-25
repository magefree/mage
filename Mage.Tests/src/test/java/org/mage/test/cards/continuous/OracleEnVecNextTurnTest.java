package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class OracleEnVecNextTurnTest extends CardTestPlayerBase {

    @Test
    public void test_SingleOpponentMustAttack() {
        // {T}: Target opponent chooses any number of creatures they control. During that player’s next turn, the chosen
        // creatures attack if able, and other creatures can’t attack. At the beginning of that turn’s end step,
        // destroy each of the chosen creatures that didn’t attack this turn. Activate this ability only during your turn.
        addCard(Zone.BATTLEFIELD, playerA, "Oracle en-Vec");
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Angelic Wall", 1); // wall, can't attack
        addCard(Zone.BATTLEFIELD, playerB, "Ashcoat Bear", 1); // 2/2

        // 1 - activate
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target opponent", playerB);
        setChoice(playerB, "Balduvian Bears^Angelic Wall");
        //
        checkLife("turn 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, 20);
        checkPermanentCount("turn 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Balduvian Bears", 1);
        checkPermanentCount("turn 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Angelic Wall", 1);
        checkPermanentCount("turn 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Ashcoat Bear", 1);

        // 2 - attack and destroy at the end
        checkLife("turn 2", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, 20 - 2);
        checkPermanentCount("turn 2a", 2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Balduvian Bears", 1);
        checkPermanentCount("turn 2a", 2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Angelic Wall", 1);
        checkPermanentCount("turn 2a", 2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Ashcoat Bear", 1);

        // 3 - nothing
        // after destroy at the end
        checkLife("turn 3", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, 20 - 2);
        checkPermanentCount("turn 3", 3, PhaseStep.POSTCOMBAT_MAIN, playerB, "Balduvian Bears", 1);
        checkPermanentCount("turn 3", 3, PhaseStep.POSTCOMBAT_MAIN, playerB, "Angelic Wall", 0);
        checkPermanentCount("turn 3", 3, PhaseStep.POSTCOMBAT_MAIN, playerB, "Ashcoat Bear", 1);

        // 4 - nothing
        checkLife("turn 4", 4, PhaseStep.POSTCOMBAT_MAIN, playerA, 20 - 2);
        checkPermanentCount("turn 4", 4, PhaseStep.POSTCOMBAT_MAIN, playerB, "Balduvian Bears", 1);
        checkPermanentCount("turn 4", 4, PhaseStep.POSTCOMBAT_MAIN, playerB, "Angelic Wall", 0);
        checkPermanentCount("turn 4", 4, PhaseStep.POSTCOMBAT_MAIN, playerB, "Ashcoat Bear", 1);

        setStopAt(4, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }
}
