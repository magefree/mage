package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 * @author JayDi85
 */
public class CommandersCastTest extends CardTestCommander4Players {

    // Player order: A -> D -> C -> B

    @Test
    public void test_CastToBattlefieldOneTime() {
        addCard(Zone.COMMAND, playerA, "Balduvian Bears", 1); // {1}{G}, 2/2, commander
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        showCommand("commanders", 1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertCommandZoneCount(playerA, "Balduvian Bears", 0);
        assertPermanentCount(playerA, "Balduvian Bears", 1);
    }

    @Test
    public void test_CastToBattlefieldTwoTimes() {
        // Player order: A -> D -> C -> B
        addCard(Zone.COMMAND, playerA, "Balduvian Bears", 1); // {1}{G}, 2/2, commander
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6); // 2 + 4
        //
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        // cast 1
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after cast 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);

        // destroy commander
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Balduvian Bears");
        setChoice(playerA, "Yes"); // put to command zone again
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after destroy", 1, PhaseStep.PRECOMBAT_MAIN, playerB, playerA, "Balduvian Bears", 0);

        // cast 2
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Balduvian Bears");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        checkPermanentCount("after cast 2", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Balduvian Bears", 1);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertCommandZoneCount(playerA, "Balduvian Bears", 0);
        assertPermanentCount(playerA, "Balduvian Bears", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
    }
}
