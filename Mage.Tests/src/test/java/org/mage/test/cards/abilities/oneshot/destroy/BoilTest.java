
package org.mage.test.cards.abilities.oneshot.destroy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class BoilTest extends CardTestPlayerBase {

    @Test
    public void testDestroy() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Breeding Pool");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        // Destroy all Islands.
        addCard(Zone.HAND, playerA, "Boil"); // {3}{R}

        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Breeding Pool");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Boil");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Island", 0);
        assertPermanentCount(playerA, "Breeding Pool", 0);
        assertPermanentCount(playerA, "Mountain", 2);

        assertPermanentCount(playerB, "Island", 0);
        assertPermanentCount(playerB, "Breeding Pool", 0);
        assertPermanentCount(playerB, "Mountain", 2);
    }
}
