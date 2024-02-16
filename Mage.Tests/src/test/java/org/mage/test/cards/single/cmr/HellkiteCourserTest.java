package org.mage.test.cards.single.cmr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

/**
 * @author JayDi85
 */

public class HellkiteCourserTest extends CardTestCommanderDuelBase {

    @Test
    public void test_ETB() {
        // https://github.com/magefree/mage/issues/7198

        // When Hellkite Courser enters the battlefield, you may put a commander you own from the command
        // zone onto the battlefield. It gains haste. Return it to the command zone at the
        // beginning of the next end step.
        addCard(Zone.HAND, playerA, "Hellkite Courser", 1); // {4}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        //
        addCard(Zone.COMMAND, playerA, "Balduvian Bears", 1); // {1}{G}

        checkCommandCardCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);

        // cast card and move commander to battle
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hellkite Courser");
        setChoice(playerA, true); // put commander to battlefield
        setChoice(playerA, "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after etb", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);

        // try to attack (test haste)
        attack(1, playerA, "Balduvian Bears", playerB);

        // keep until end
        checkPermanentCount("keep after battle", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Balduvian Bears", 1);

        // return to command zone
        checkPermanentCount("return on end", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 0);
        checkCommandCardCount("return on end", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
    }
}
