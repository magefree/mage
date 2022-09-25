
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author tamaroth
 */
public class TuvasaTheSunlitTest extends CardTestPlayerBase {

    /**
     * Playing more than one enchantment spell in a single turn does not draw more than 1 additional card.
     */
    @Test
    public void testWithStriveSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        // Whenever you play your first enchantment spell of the turn, draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Tuvasa the Sunlit", 1);

        // Two enchantments to play
        addCard(Zone.HAND, playerA, "Burgeoning", 1);
        addCard(Zone.HAND, playerA, "Ajani's Welcome", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Burgeoning", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ajani's Welcome");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 1);
    }

}
