package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AcademyManufactorTest extends CardTestPlayerBase {

    @Test
    public void testAcademyManufactor() {
        addCard(Zone.BATTLEFIELD, playerA, "Academy Manufactor");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Thraben Inspector");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thraben Inspector");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Plains", 1);
        assertPermanentCount(playerA, "Academy Manufactor", 1);
        assertPermanentCount(playerA, "Thraben Inspector", 1);
        assertPermanentCount(playerA, "Clue Token", 1);
        assertPermanentCount(playerA, "Food Token", 1);
        assertPermanentCount(playerA, "Treasure Token", 1);
    }

    @Test
    public void testMultipleReplacementEffect() {
        addCard(Zone.BATTLEFIELD, playerA, "Academy Manufactor", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Anointed Procession");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Thraben Inspector");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thraben Inspector");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Plains", 1);
        assertPermanentCount(playerA, "Academy Manufactor", 2);
        assertPermanentCount(playerA, "Anointed Procession", 1);
        assertPermanentCount(playerA, "Thraben Inspector", 1);
        assertPermanentCount(playerA, "Clue Token", 6);
        assertPermanentCount(playerA, "Food Token", 6);
        assertPermanentCount(playerA, "Treasure Token", 6);
    }

    @Test
    public void testTokenLimit() {
        addCard(Zone.BATTLEFIELD, playerA, "Academy Manufactor", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Thraben Inspector");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thraben Inspector");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Plains", 1);
        assertPermanentCount(playerA, "Academy Manufactor", 6);
        assertPermanentCount(playerA, "Thraben Inspector", 1);

        // 8 permanents above + 500 token limit
        assertPermanentCount(playerA, 508);
    }

    @Test
    public void testGingerbruteToken() {
        addCard(Zone.BATTLEFIELD, playerA, "Academy Manufactor", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 5);
        addCard(Zone.HAND, playerA, "Fractured Identity");

        addCard(Zone.BATTLEFIELD, playerB, "Gingerbrute");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fractured Identity", "Gingerbrute");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Tundra", 5);
        assertPermanentCount(playerA, "Academy Manufactor", 2);
        // Gingerbrute token copy becomes a regular Food
        assertPermanentCount(playerA, "Gingerbrute", 0);
        assertPermanentCount(playerB, "Gingerbrute", 0);
        assertPermanentCount(playerA, "Clue Token", 3);
        assertPermanentCount(playerA, "Food Token", 3);
        assertPermanentCount(playerA, "Treasure Token", 3);
    }
}
