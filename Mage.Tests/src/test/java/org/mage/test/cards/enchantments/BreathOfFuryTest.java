
package org.mage.test.cards.enchantments;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class BreathOfFuryTest extends CardTestPlayerBase {

    @Test
    public void testMoveEnchantment() {

        // Enchant creature you control
        // When enchanted creature deals combat damage to a player, sacrifice it and attach Breath of Fury to a creature you control.
        // If you do, untap all creatures you control and after this phase, there is an additional combat phase.
        addCard(Zone.HAND, playerA, "Breath of Fury", 1); // Enchantment - Aura {2}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Breath of Fury", "Silvercoat Lion");

        attack(3, playerA, "Pillarfield Ox");
        attack(3, playerA, "Silvercoat Lion");

        addTarget(playerA, "Pillarfield Ox");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 16);

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertTappedCount("Pillarfield Ox", false, 1);
        assertPermanentCount(playerA, "Breath of Fury", 1);

    }

}
