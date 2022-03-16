
package org.mage.test.cards.single.dst;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class EchoingTruthTest extends CardTestPlayerBase {

    /**
     * I played "Echoing Truth" targeting one of my opponent's spirit tokens
     * from Spectral Processions and NONE OF THEM got bounced.
     */
    @Test
    public void testReturnTokens() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, "Spectral Procession");

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Echoing Truth");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spectral Procession");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Echoing Truth", "Spirit Token");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Spectral Procession", 1);
        assertGraveyardCount(playerB, "Echoing Truth", 1);
        assertPermanentCount(playerA, "Spirit Token", 0);

    }

}
