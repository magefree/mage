package org.mage.test.cards.single.ice;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class OathOfLimDulTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.o.OathOfLimDul Oath of Lim-Dûl} {3}{B}
     * Enchantment
     * Whenever you lose life, for each 1 life you lost, sacrifice a permanent other than Oath of Lim-Dûl unless you discard a card. (Damage dealt to you causes you to lose life.)
     * {B}{B}: Draw a card.
     */
    private static final String oath = "Oath of Lim-Dul";

    @Test
    public void test_3Sacrifice() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, oath, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerA);
        setChoice(playerA, false); // No to discard on first instance.
        setChoice(playerA, "Mountain"); // sacrifice Mountain
        setChoice(playerA, false); // No to discard on second instance.
        setChoice(playerA, "Mountain"); // sacrifice Mountain
        setChoice(playerA, false); // No to discard on third instance.
        setChoice(playerA, "Mountain"); // sacrifice Mountain

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 - 3);
        assertGraveyardCount(playerA, "Mountain", 3);
    }

    @Test
    public void test_3Discard() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, oath, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerA);
        setChoice(playerA, true); // Yes to discard on first instance.
        setChoice(playerA, "Swamp"); // sacrifice Swamp
        setChoice(playerA, true); // Yes to discard on second instance.
        setChoice(playerA, "Swamp"); // sacrifice Swamp
        setChoice(playerA, true); // Yes to discard on third instance.
        setChoice(playerA, "Swamp"); // sacrifice Swamp

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 - 3);
        assertGraveyardCount(playerA, "Swamp", 3);
    }

    @Test
    public void test_1Sacrifice1Discard_NoOther() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, oath, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Swamp", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerA);
        setChoice(playerA, true); // Yes to discard on first instance.
        setChoice(playerA, "Swamp"); // discard Swamp
        // No more possibility to Discard
        setChoice(playerA, "Mountain"); // sacrifice Mountain
        // No more things to Sacrifice

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 - 3);
        assertGraveyardCount(playerA, "Mountain", 1);
        assertGraveyardCount(playerA, "Swamp", 1);
    }

    @Test
    public void test_AllSacrificeNoDiscard_KeepCardInHand() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, oath, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Swamp", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerA);
        setChoice(playerA, false); // No to discard on first instance.
        setChoice(playerA, "Mountain"); // sacrifice Mountain
        setChoice(playerA, false); // No to discard on second instance.
        setChoice(playerA, false); // No to discard on third instance.

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 - 3);
        assertPermanentCount(playerA, oath, 1);
        assertGraveyardCount(playerA, "Mountain", 1);
        assertHandCount(playerA, "Swamp", 1);
    }
}
