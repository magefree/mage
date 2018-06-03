
package org.mage.test.cards.rules;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class CantSearchTest extends CardTestPlayerBase {

    /**
     * Stranglehold doesn´t stop your opponents from searching libraries, at the
     * moment it doesn´t work at all. Opponents were able to search their and my
     * library in multiple gammes.
     */
    @Test
    public void testStranglehold() {
        // Your opponents can't search libraries.
        // If an opponent would begin an extra turn, that player skips that turn instead.
        addCard(Zone.BATTLEFIELD, playerB, "Stranglehold");

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        // Search your library for a basic land card and put that card onto the battlefield tapped. Then shuffle your library.
        addCard(Zone.HAND, playerA, "Rampant Growth", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rampant Growth");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Rampant Growth", 1);
        assertPermanentCount(playerA, 2); // only the two forests

    }

}
