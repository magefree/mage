
package org.mage.test.cards.prevention;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class LymphSliverTest extends CardTestPlayerBase {

    @Test
    public void testMarkOfAsylumEffect() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        // Tremor deals 1 damage to each creature without flying.
        addCard(Zone.HAND, playerA, "Tremor"); // Sorcery {R}
        // All Sliver creatures have absorb 1.
        addCard(Zone.BATTLEFIELD, playerA, "Lymph Sliver"); // 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Metallic Sliver"); // 1/1
        addCard(Zone.BATTLEFIELD, playerA, "Dross Crocodile"); // 5/1

        addCard(Zone.BATTLEFIELD, playerB, "Metallic Sliver"); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Dross Crocodile"); // 5/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tremor");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Tremor", 1);

        assertPermanentCount(playerA, "Metallic Sliver", 1);
        assertGraveyardCount(playerA, "Dross Crocodile", 1);
        assertPermanentCount(playerB, "Metallic Sliver", 1);
        assertGraveyardCount(playerB, "Dross Crocodile", 1);

    }
}
