package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseAI;

/**
 * Tests that the AI correctly recognizes and plays MDFC (Modal Double-Faced Card)
 * land backs when appropriate.
 *
 * @author duxbu
 */
public class MDFCLandPlayAITest extends CardTestPlayerBaseAI {

    /**
     * AI with only Akoum Warrior in hand (no lands, no mana) should play Akoum Teeth as a land.
     * Akoum Warrior is {5}{R} creature // Akoum Teeth is a land (enters tapped, taps for {R}).
     * With no mana available, the AI cannot cast the creature side but should play the land side.
     */
    @Test
    public void test_AIPlaysAkoumTeethAsLand_WhenNoMana() {
        addCard(Zone.HAND, playerA, "Akoum Warrior");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // AI should have played Akoum Teeth (land side) onto the battlefield
        assertPermanentCount(playerA, "Akoum Teeth", 1);
        assertHandCount(playerA, 0);
    }

    /**
     * AI with Akoum Warrior and a castable spell should still play the land side when mana-starved.
     * With only 1 Mountain on battlefield (not enough for {5}{R} Akoum Warrior),
     * the AI should play Akoum Teeth to develop mana.
     */
    @Test
    public void test_AIPlaysLandSide_WhenManaStarved() {
        addCard(Zone.HAND, playerA, "Akoum Warrior");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // AI should have played Akoum Teeth as a land (still can't cast creature at {5}{R})
        assertPermanentCount(playerA, "Akoum Teeth", 1);
    }
}
