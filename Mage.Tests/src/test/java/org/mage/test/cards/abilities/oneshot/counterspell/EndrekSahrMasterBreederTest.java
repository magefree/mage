
package org.mage.test.cards.abilities.oneshot.counterspell;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class EndrekSahrMasterBreederTest extends CardTestPlayerBase {

    /**
     * Test that tokens are created also if the spell that triggers Endreks ability is countered
     *
     */
    @Test
    public void testTokenAlsoIfCountered() {
        // Whenever you cast a creature spell, put X 1/1 black Thrull creature tokens onto the battlefield, where X is that spell's converted mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Endrek Sahr, Master Breeder", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Counterspell", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", "Silvercoat Lion", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerB, "Counterspell", 1);
        
        assertPermanentCount(playerA, "Thrull Token", 2);

    }

}