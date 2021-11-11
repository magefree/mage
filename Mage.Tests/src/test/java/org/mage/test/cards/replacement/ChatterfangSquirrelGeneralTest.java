package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ChatterfangSquirrelGeneralTest extends CardTestPlayerBase {

    private static final String chatterfang = "Chatterfang, Squirrel General";

    @Test
    public void testChatterfang() {
        addCard(Zone.BATTLEFIELD, playerA, chatterfang);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Raise the Alarm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raise the Alarm");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAllCommandsUsed();
        assertPermanentCount(playerA, "Plains", 2);
        assertPermanentCount(playerA, chatterfang, 1);
        assertPermanentCount(playerA, "Soldier Token", 2);
        assertPermanentCount(playerA, "Squirrel Token", 2);
    }

    @Test
    public void testChatterfangOpponent() {
        addCard(Zone.BATTLEFIELD, playerB, chatterfang);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Raise the Alarm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raise the Alarm");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAllCommandsUsed();
        assertPermanentCount(playerA, "Plains", 2);
        assertPermanentCount(playerB, chatterfang, 1);
        assertPermanentCount(playerA, "Soldier Token", 2);
        assertPermanentCount(playerA, "Squirrel Token", 0);
        assertPermanentCount(playerB, "Soldier Token", 0);
        assertPermanentCount(playerB, "Squirrel Token", 0);
    }

    @Test
    public void testChatterfangPlusAcademyManufactor() {
        addCard(Zone.BATTLEFIELD, playerA, chatterfang);
        addCard(Zone.BATTLEFIELD, playerA, "Academy Manufactor");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Thraben Inspector");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thraben Inspector");
        // Order Academy Manufactor replacement effect first
        setChoice(playerA, "Academy Manufactor");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAllCommandsUsed();
        assertPermanentCount(playerA, "Plains", 1);
        assertPermanentCount(playerA, chatterfang, 1);
        assertPermanentCount(playerA, "Academy Manufactor", 1);
        assertPermanentCount(playerA, "Clue Token", 1);
        assertPermanentCount(playerA, "Food Token", 1);
        assertPermanentCount(playerA, "Treasure Token", 1);
        assertPermanentCount(playerA, "Squirrel Token", 3);
    }
}
