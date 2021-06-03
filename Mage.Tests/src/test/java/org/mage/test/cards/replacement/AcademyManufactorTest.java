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

        assertAllCommandsUsed();
        assertPermanentCount(playerA, "Plains", 1);
        assertPermanentCount(playerA, "Academy Manufactor", 1);
        assertPermanentCount(playerA, "Thraben Inspector", 1);
        assertPermanentCount(playerA, "Clue", 1);
        assertPermanentCount(playerA, "Food", 1);
        assertPermanentCount(playerA, "Treasure", 1);
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

        assertAllCommandsUsed();
        assertPermanentCount(playerA, "Plains", 1);
        assertPermanentCount(playerA, "Academy Manufactor", 2);
        assertPermanentCount(playerA, "Anointed Procession", 1);
        assertPermanentCount(playerA, "Thraben Inspector", 1);
        assertPermanentCount(playerA, "Clue", 6);
        assertPermanentCount(playerA, "Food", 6);
        assertPermanentCount(playerA, "Treasure", 6);
    }

    @Test
    public void testTokenLimit() {
        addCard(Zone.BATTLEFIELD, playerA, "Academy Manufactor", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Thraben Inspector");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thraben Inspector");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAllCommandsUsed();
        assertPermanentCount(playerA, "Plains", 1);
        assertPermanentCount(playerA, "Academy Manufactor", 6);
        assertPermanentCount(playerA, "Thraben Inspector", 1);

        // 8 permanents above + 500 token limit
        assertPermanentCount(playerA, 508);
    }
}
