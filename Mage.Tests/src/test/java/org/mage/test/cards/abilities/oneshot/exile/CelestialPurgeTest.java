
package org.mage.test.cards.abilities.oneshot.exile;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class CelestialPurgeTest extends CardTestPlayerBase {

    /**
     * I activated Celestial Purge trying to targeting a Bitterblossom but i
     * couldn't so please fix that bug
     */
    @Test
    public void testExileWorks() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Exile target black or red permanent.
        addCard(Zone.HAND, playerA, "Celestial Purge");

        // At the beginning of your upkeep, you lose 1 life and put a 1/1 black Faerie Rogue creature token with flying onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerB, "Bitterblossom");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Celestial Purge", "Bitterblossom");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);

        assertGraveyardCount(playerA, "Celestial Purge", 1);

        assertExileCount("Bitterblossom", 1);
        assertPermanentCount(playerB, "Faerie Rogue Token", 1);

    }
}
