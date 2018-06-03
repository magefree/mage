

package org.mage.test.cards.abilities.oneshot.counterspell;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class CantCounterTest extends CardTestPlayerBase {

    /**
     * Test that spell with CantCounterAbility can't be countered
     *
     */
    @Test
    public void testCantCounterLoxodon() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Loxodon Smiter", 1);

        addCard(Zone.HAND, playerB, "Counterspell");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Loxodon Smiter");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", "Loxodon Smiter", "Loxodon Smiter");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Loxodon Smiter", 1);
        assertGraveyardCount(playerB, "Counterspell", 1);

    }

}