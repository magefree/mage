package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class CopyEnchantmentTest extends CardTestPlayerBase {

    @Test
    public void copyNonAuraEnchantment() {
        addCard(Zone.HAND, playerA, "Copy Enchantment", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        // Creature - Dragon   2/2
        // Flying
        // {R}: Furnace Whelp gets +1/+0 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Furnace Whelp", 3);

        // Dragon creatures you control get +3/+3.
        addCard(Zone.BATTLEFIELD, playerB, "Crucible of Fire", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Copy Enchantment");
        setChoice(playerA, "Crucible of Fire");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Copy Enchantment", 0);
        assertPermanentCount(playerA, "Crucible of Fire", 1);
        assertPowerToughness(playerA, "Furnace Whelp", 5, 5);
    }

    @Test
    public void copyAuraEnchantment() {
        addCard(Zone.HAND, playerA, "Inferno Fist", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // 3/1 Flying
        addCard(Zone.BATTLEFIELD, playerA, "Geist of the Moors", 1);

        addCard(Zone.HAND, playerB, "Copy Enchantment", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Inferno Fist", "Geist of the Moors");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Copy Enchantment");
        setChoice(playerB, true); // copy
        setChoice(playerB, "Inferno Fist"); // copied
        setChoice(playerB, "Silvercoat Lion"); // target for copy

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertHandCount(playerA, "Inferno Fist", 0);
        assertGraveyardCount(playerB, "Copy Enchantment", 0);
        assertPermanentCount(playerA, "Inferno Fist", 1);
        assertPermanentCount(playerB, "Inferno Fist", 1);
        assertPowerToughness(playerA, "Geist of the Moors", 5, 1);
        assertPowerToughness(playerB, "Silvercoat Lion", 4, 2);
    }
}
