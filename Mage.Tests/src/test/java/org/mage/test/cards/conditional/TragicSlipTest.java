 package org.mage.test.cards.conditional;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */



public class TragicSlipTest extends CardTestPlayerBase {

    @Test
    public void testNoCreatureDied() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp");

        // Tragic Slip - Instant, B - Target creature gets -1/-1 until end of turn.
        // Morbid — That creature gets -13/-13 until end of turn instead if a creature died this turn.
        addCard(Constants.Zone.HAND, playerA, "Tragic Slip");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Pillarfield Ox");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Tragic Slip", "Pillarfield Ox");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Pillarfield Ox", 1, 3);
    }

    @Test
    public void testCreatureDiedAfter() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 2);

        // Tragic Slip - Instant, B - Target creature gets -1/-1 until end of turn.
        // Morbid — That creature gets -13/-13 until end of turn instead if a creature died this turn.
        addCard(Constants.Zone.HAND, playerA, "Tragic Slip");
        // Searing Spear - Instant, 1R - Searing Spear deals 3 damage to target creature or player.
        addCard(Constants.Zone.HAND, playerA, "Searing Spear");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Pillarfield Ox");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Tragic Slip", "Pillarfield Ox");

        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Searing Spear", "Silvercoat Lion");

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Pillarfield Ox", 1, 3);
    }

    @Test
    public void testCreatureDiedBefore() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 2);

        // Tragic Slip - Instant, B - Target creature gets -1/-1 until end of turn.
        // Morbid — That creature gets -13/-13 until end of turn instead if a creature died this turn.
        addCard(Constants.Zone.HAND, playerA, "Tragic Slip");
        // Searing Spear - Instant, 1R - Searing Spear deals 3 damage to target creature or player.
        addCard(Constants.Zone.HAND, playerA, "Searing Spear");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Pillarfield Ox");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Searing Spear", "Silvercoat Lion");

        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Tragic Slip", "Pillarfield Ox");

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 0);
        assertPermanentCount(playerA, "Pillarfield Ox", 0);
    }
}
