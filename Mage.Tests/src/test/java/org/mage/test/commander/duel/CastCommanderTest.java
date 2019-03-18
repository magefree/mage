
package org.mage.test.commander.duel;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

/**
 *
 * @author LevelX2
 */
public class CastCommanderTest extends CardTestCommanderDuelBase {
    @Test
    public void testCastCommander() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ob Nixilis of the Black Oath");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 40);
        assertLife(playerB, 40);

        assertPermanentCount(playerA, "Ob Nixilis of the Black Oath", 1);
    }     
}
