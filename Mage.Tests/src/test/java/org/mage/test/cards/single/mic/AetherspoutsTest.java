package org.mage.test.cards.single.mic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AetherspoutsTest extends CardTestPlayerBase {

    // https://github.com/magefree/mage/issues/14351
    @Test
    public void testAllToTop() {
        addCard(Zone.HAND, playerB, "Aetherspouts");
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Kalonian Behemoth");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);
        addCard(Zone.HAND, playerA, "Beast Attack");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Beast Attack");
        attack(3, playerA, "Balduvian Bears", playerB);
        attack(3, playerA, "Grizzly Bears", playerB);
        attack(3, playerA, "Beast Token", playerB);
        attack(3, playerA, "Kalonian Behemoth", playerB);
        castSpell(3, PhaseStep.DECLARE_BLOCKERS, playerB, "Aetherspouts");
        setChoice(playerA, true); // yes, send Balduvian Bears to top
        setChoice(playerA, true); // yes, send Grizzly Bears to top
        setChoice(playerA, true); // yes, send Kalonian Behemoth to top
        addTarget(playerA, "Balduvian Bears");
        addTarget(playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Balduvian Bears", 0);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertPermanentCount(playerA, "Kalonian Behemoth", 0);
        assertPermanentCount(playerA, "Beast Token", 0);
        assertLibraryCount(playerA, "Balduvian Bears", 1);
        assertLibraryCount(playerA, "Grizzly Bears", 1);
        assertLibraryCount(playerA, "Kalonian Behemoth", 1);
        assertLibraryCount(playerA, "Beast Token", 0);
    }

    @Test
    public void testSomeToBottom() {
        addCard(Zone.HAND, playerB, "Aetherspouts");
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Kalonian Behemoth");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);
        addCard(Zone.HAND, playerA, "Beast Attack");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Beast Attack");
        attack(3, playerA, "Balduvian Bears", playerB);
        attack(3, playerA, "Grizzly Bears", playerB);
        attack(3, playerA, "Beast Token", playerB);
        attack(3, playerA, "Kalonian Behemoth", playerB);
        castSpell(3, PhaseStep.DECLARE_BLOCKERS, playerB, "Aetherspouts");
        setChoice(playerA, true); // yes, send Balduvian Bears to top
        setChoice(playerA, false); // no, send Grizzly Bears to bottom
        setChoice(playerA, false); // no, send Kalonian Behemoth to bottom
        addTarget(playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Balduvian Bears", 0);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertPermanentCount(playerA, "Kalonian Behemoth", 0);
        assertPermanentCount(playerA, "Beast Token", 0);
        assertLibraryCount(playerA, "Balduvian Bears", 1);
        assertLibraryCount(playerA, "Grizzly Bears", 1);
        assertLibraryCount(playerA, "Kalonian Behemoth", 1);
        assertLibraryCount(playerA, "Beast Token", 0);
    }
}
