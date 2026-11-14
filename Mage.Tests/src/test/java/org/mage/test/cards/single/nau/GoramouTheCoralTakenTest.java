package org.mage.test.cards.single.nau;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Goramou the Coral-Taken
 * Whenever you discard one or more land cards, you may exile one of them from your graveyard.
 * If you do, put that card onto the battlefield tapped at the beginning of the next end step.
 */
public class GoramouTheCoralTakenTest extends CardTestPlayerBase {

    private static final String GORAMOU = "Goramou the Coral-Taken";

    @Test
    public void testDiscardTwoLandsOnlyOneReturns() {
        addCard(Zone.BATTLEFIELD, playerA, GORAMOU);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Cathartic Reunion");
        addCard(Zone.HAND, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Island");
        addCard(Zone.GRAVEYARD, playerA, "Plains");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cathartic Reunion");
        setChoice(playerA, "Forest^Island");
        setChoice(playerA, true);
        setChoice(playerA, "Island");
        checkExileCount("land waits in exile", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Island", 1);
        checkPermanentCount("not returned early", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Island", 0);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertPermanentCount(playerA, "Island", 1);
        assertTapped("Island", true);
        assertGraveyardCount(playerA, "Forest", 1);
        assertGraveyardCount(playerA, "Plains", 1);
    }

    @Test
    public void testDeclineExile() {
        addCard(Zone.BATTLEFIELD, playerA, GORAMOU);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Cathartic Reunion");
        addCard(Zone.HAND, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Shock");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cathartic Reunion");
        setChoice(playerA, "Forest^Shock");
        setChoice(playerA, false);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertGraveyardCount(playerA, "Forest", 1);
        assertPermanentCount(playerA, "Forest", 0);
    }

    @Test
    public void testNonlandAndOpponentDiscardDoNotTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, GORAMOU);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Cathartic Reunion");
        addCard(Zone.HAND, playerA, "Shock");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Zone.HAND, playerB, "Cathartic Reunion");
        addCard(Zone.HAND, playerB, "Forest");
        addCard(Zone.HAND, playerB, "Island");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cathartic Reunion");
        setChoice(playerA, "Shock^Lightning Bolt");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cathartic Reunion");
        setChoice(playerB, "Forest^Island");
        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.CLEANUP);
        execute();

        assertGraveyardCount(playerB, "Forest", 1);
        assertGraveyardCount(playerB, "Island", 1);
        assertPermanentCount(playerA, "Forest", 0);
        assertPermanentCount(playerB, "Island", 0);
    }

    @Test
    public void testDelayedReturnAfterGoramouLeaves() {
        addCard(Zone.BATTLEFIELD, playerA, GORAMOU);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Cathartic Reunion");
        addCard(Zone.HAND, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Shock");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        addCard(Zone.HAND, playerB, "Doom Blade");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cathartic Reunion");
        setChoice(playerA, "Forest^Shock");
        setChoice(playerA, true);
        setChoice(playerA, "Forest");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Doom Blade", GORAMOU);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertGraveyardCount(playerA, GORAMOU, 1);
        assertPermanentCount(playerA, "Forest", 1);
        assertTapped("Forest", true);
    }

    @Test
    public void testLandRemovedFromGraveyardBeforeTriggerResolves() {
        addCard(Zone.BATTLEFIELD, playerA, GORAMOU);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Cathartic Reunion");
        addCard(Zone.HAND, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Shock");
        addCard(Zone.GRAVEYARD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp");
        addCard(Zone.HAND, playerB, "Cremate");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cathartic Reunion");
        setChoice(playerA, "Forest^Shock");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cremate", "Forest", "Whenever you discard one or more land cards");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertExileCount(playerA, "Forest", 1);
        assertGraveyardCount(playerA, "Island", 1);
        assertPermanentCount(playerA, "Forest", 0);
    }

    @Test
    public void testCardThatLeavesAndReturnsToExileIsNotReturned() {
        addCard(Zone.BATTLEFIELD, playerA, GORAMOU);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Cathartic Reunion");
        addCard(Zone.HAND, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Shock");
        addCard(Zone.BATTLEFIELD, playerB, "Plains");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp");
        addCard(Zone.HAND, playerB, "Pull from Eternity");
        addCard(Zone.HAND, playerB, "Cremate");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cathartic Reunion");
        setChoice(playerA, "Forest^Shock");
        setChoice(playerA, true);
        setChoice(playerA, "Forest");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Pull from Eternity", "Forest");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Cremate", "Forest");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertExileCount(playerA, "Forest", 1);
        assertPermanentCount(playerA, "Forest", 0);
    }

    @Test
    public void testDiscardDuringEndStepReturnsNextTurn() {
        addCard(Zone.BATTLEFIELD, playerA, GORAMOU);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Thrill of Possibility");
        addCard(Zone.HAND, playerA, "Forest");

        castSpell(1, PhaseStep.END_TURN, playerA, "Thrill of Possibility");
        setChoice(playerA, "Forest");
        setChoice(playerA, true);
        setChoice(playerA, "Forest");
        checkExileCount("still exiled after first end step", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest", 1);
        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.CLEANUP);
        execute();

        assertPermanentCount(playerA, "Forest", 1);
        assertTapped("Forest", true);
    }

    @Test
    public void testSeparateDiscardsTriggerSeparately() {
        addCard(Zone.BATTLEFIELD, playerA, GORAMOU);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, "Thrill of Possibility", 2);
        addCard(Zone.HAND, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Island");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thrill of Possibility");
        setChoice(playerA, "Forest");
        setChoice(playerA, true);
        setChoice(playerA, "Forest");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thrill of Possibility");
        setChoice(playerA, "Island");
        setChoice(playerA, true);
        setChoice(playerA, "Island");
        setChoice(playerA, "At the beginning of the next end step");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertPermanentCount(playerA, "Forest", 1);
        assertPermanentCount(playerA, "Island", 1);
        assertTapped("Forest", true);
        assertTapped("Island", true);
    }

    @Test
    public void testDiscardReplacedByExile() {
        addCard(Zone.BATTLEFIELD, playerA, GORAMOU);
        addCard(Zone.BATTLEFIELD, playerA, "Rest in Peace");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Cathartic Reunion");
        addCard(Zone.HAND, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Shock");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cathartic Reunion");
        setChoice(playerA, "Forest^Shock");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertExileCount(playerA, "Forest", 1);
        assertPermanentCount(playerA, "Forest", 0);
    }
}
