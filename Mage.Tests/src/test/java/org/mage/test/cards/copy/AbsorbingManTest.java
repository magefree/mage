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
}
