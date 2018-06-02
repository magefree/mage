
package org.mage.test.cards.prevention;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class LightOfSanctionTest extends CardTestPlayerBase {

    @Test
    public void testLightOfSanctionEffect() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        // Tremor deals 1 damage to each creature without flying.
        addCard(Zone.HAND, playerA, "Tremor"); // Sorcery {R}
        // Prevent all damage that would be dealt to creatures you control by sources you control.
        addCard(Zone.BATTLEFIELD, playerA, "Light of Sanction");
        addCard(Zone.BATTLEFIELD, playerA, "Metallic Sliver"); // 1/1
        addCard(Zone.BATTLEFIELD, playerA, "Dross Crocodile"); // 5/1

        addCard(Zone.BATTLEFIELD, playerB, "Metallic Sliver"); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Dross Crocodile"); // 5/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tremor");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Tremor", 1);

        assertPermanentCount(playerA, "Metallic Sliver", 1);
        assertPermanentCount(playerA, "Dross Crocodile", 1);
        assertGraveyardCount(playerB, "Metallic Sliver", 1);
        assertGraveyardCount(playerB, "Dross Crocodile", 1);

    }
}
