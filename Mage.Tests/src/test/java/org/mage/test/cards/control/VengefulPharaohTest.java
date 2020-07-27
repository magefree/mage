
package org.mage.test.cards.control;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class VengefulPharaohTest extends CardTestPlayerBase {

    @Test
    public void controlledByOtherBeforeGraveyardTriggerTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Deathtouch (Any amount of damage this deals to a creature is enough to destroy it.)
        // Whenever combat damage is dealt to you or a planeswalker you control, if Vengeful Pharaoh is in your graveyard, destroy target attacking creature, then put Vengeful Pharaoh on top of your library.
        addCard(Zone.HAND, playerA, "Vengeful Pharaoh", 1); // Creature 5/4 {2}{B}{B}{B}
        // Destroy target permanent.
        addCard(Zone.HAND, playerA, "Vindicate", 1); // Sorcery {1}{W}{B}

        addCard(Zone.HAND, playerB, "Control Magic", 1); // Enchantment
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vengeful Pharaoh");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Control Magic", "Vengeful Pharaoh");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Vindicate", "Vengeful Pharaoh");

        attack(4, playerB, "Silvercoat Lion");

        setStopAt(4, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Vindicate", 1);
        assertGraveyardCount(playerB, "Control Magic", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
        assertGraveyardCount(playerA, "Vengeful Pharaoh", 0);
        assertLibraryCount(playerA, "Vengeful Pharaoh", 1);

        assertLife(playerA, 18);
    }
}
