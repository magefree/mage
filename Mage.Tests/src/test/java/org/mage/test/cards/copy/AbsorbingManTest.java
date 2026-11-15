package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.constants.CardType;
import mage.constants.SubType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AbsorbingManTest extends CardTestPlayerBase {

    @Test
    public void testAbsorbingManCopiesEnchantment() {
        // Setup: Add Absorbing Man and an Enchantment to the battlefield
        addCard(Zone.BATTLEFIELD, playerA, "Absorbing Man");
        addCard(Zone.BATTLEFIELD, playerA, "Impostor Syndrome");

        // Fast-forward to the beginning of the first main phase to trigger the ability
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);

        // Target the enchantment with the trigger
        addTarget(playerA, "Impostor Syndrome");

        execute();

        // Assertions: Check if the copy applier worked correctly!
        assertPermanentCount(playerA, "Absorbing Man", 1);

        // Check Types and Subtypes
        assertType("Absorbing Man", CardType.CREATURE, true);
        assertType("Absorbing Man", CardType.ENCHANTMENT, true);
        assertSubtype("Absorbing Man", SubType.HUMAN);
        assertSubtype("Absorbing Man", SubType.VILLAIN);

        // Check Power and Toughness
        assertPowerToughness(playerA, "Absorbing Man", 4, 4);
    }

    @Test
    public void test_BlinkInResponse_NoCopy() {
        addCard(Zone.BATTLEFIELD, playerA, "Absorbing Man");
        addCard(Zone.BATTLEFIELD, playerA, "Impostor Syndrome");
        //
        addCard(Zone.HAND, playerA, "Cloudshift"); // {W}, exile target creature you control, then return it
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        // 400.7 the returned Absorbing Man is a new object, the old trigger can't make it a copy
        addTarget(playerA, "Impostor Syndrome"); // enchantment to copy
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", "Absorbing Man", "becomes a copy");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Cloudshift", 1);
        assertType("Absorbing Man", CardType.ENCHANTMENT, false);
    }
}
