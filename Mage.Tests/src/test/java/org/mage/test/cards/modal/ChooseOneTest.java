package org.mage.test.cards.modal;

import mage.abilities.keyword.SwampwalkAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class ChooseOneTest extends CardTestPlayerBase {

    @Test
    public void testFirstMode() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // Choose one
        // - Target player discards a card
        // - Target creature gets +2/-1 until end of turn.
        // - Target creature gains swampwalk until end of turn.
        addCard(Zone.HAND, playerA, "Funeral Charm"); // Instant {B}

        addCard(Zone.HAND, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Funeral Charm", playerB);
        setModeChoice(playerA, "1");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Funeral Charm", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
    }

    @Test
    public void testSecondMode() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // Choose one
        // - Target player discards a card
        // - Target creature gets +2/-1 until end of turn.
        // - Target creature gains swampwalk until end of turn.
        addCard(Zone.HAND, playerA, "Funeral Charm"); // Instant {B}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Funeral Charm", "Silvercoat Lion");
        setModeChoice(playerA, "2");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Funeral Charm", 1);
        assertPowerToughness(playerB, "Silvercoat Lion", 4, 1);
    }

    @Test
    public void testThirdMode() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // Choose one
        // - Target player discards a card
        // - Target creature gets +2/-1 until end of turn.
        // - Target creature gains swampwalk until end of turn.
        addCard(Zone.HAND, playerA, "Funeral Charm"); // Instant {B}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Funeral Charm", "Silvercoat Lion");
        setModeChoice(playerA, "3");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Funeral Charm", 1);
        assertPowerToughness(playerB, "Silvercoat Lion", 2, 2);
        assertAbility(playerB, "Silvercoat Lion", new SwampwalkAbility(), true);
    }
}
