package org.mage.test.cards.single.yeoe;

import mage.constants.PhaseStep;
import mage.constants.Zone;

import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ValMaroonedSurveyorTest extends CardTestPlayerBase {

    @Test
    public void testScryAndInvestigateAndSurveilEachTriggerOnce() {
        addCard(Zone.BATTLEFIELD, playerA, "Val, Marooned Surveyor");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.HAND, playerA, "Opt");
        addCard(Zone.HAND, playerA, "Deduce");
        addCard(Zone.HAND, playerA, "Consider");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Opt");
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deduce");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Consider");
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 26);
        assertLife(playerB, 14);
    }

    @Test
    public void testOpponentActionDoesNotTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, "Val, Marooned Surveyor");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Deduce");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Deduce");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    @Test
    public void testSeekFromExistingCardTriggersOnce() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Val, Marooned Surveyor");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.HAND, playerA, "Hollowhenge Wrangler");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hollowhenge Wrangler");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 22);
        assertLife(playerB, 18);
        assertHandCount(playerA, "Island", 1);
    }

    @Test
    public void testDiscoverTriggersOnce() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Val, Marooned Surveyor");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, "Geological Appraiser");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Geological Appraiser");
        setChoice(playerA, false);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 22);
        assertLife(playerB, 18);
        assertHandCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void testSeekWithoutMatchingCardDoesNotTrigger() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Val, Marooned Surveyor");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.HAND, playerA, "Hollowhenge Wrangler");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hollowhenge Wrangler");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertHandCount(playerA, 0);
        assertLibraryCount(playerA, 1);
    }
}
