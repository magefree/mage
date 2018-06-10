
package org.mage.test.cards.mana;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class HybridManaTest extends CardTestPlayerBase {

    @Test
    public void testCastReaperKingMonoHybrid() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        // Other Scarecrow creatures you control get +1/+1.
        // Whenever another Scarecrow enters the battlefield under your control, destroy target permanent.
        addCard(Zone.HAND, playerA, "Reaper King", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reaper King");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Reaper King", 1);

    }

}
