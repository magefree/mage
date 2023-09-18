
package org.mage.test.cards.single.wwk;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author magenoxx
 */
public class MindbreakTrapTest extends CardTestPlayerBase {

    /*
      Mindbreak Trap {2}{U}{U}
      Instant
      If an opponent cast three or more spells this turn, you may pay {0} rather than pay Mindbreak Trap's mana cost.
      Exile any number of target spells.
     */
    private final String mindBreakTrap = "Mindbreak Trap";
    private final String shock = "Shock"; // card for counters {R}
    private final String grapeShot = "Grapeshot"; // storm card 1{R}

    /**
     * Play 2 Shock and then Grapeshot (with Storm) to trigger twice
     * Afterwards use Mindbreak Tap to exile all Storm spells from stack
     */
    @Test
    public void mindBreakTrap_Exile_All_Spells() {
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 4);

        addCard(Zone.HAND, playerA, mindBreakTrap);
        addCard(Zone.HAND, playerB, shock, 2);
        addCard(Zone.HAND, playerB, grapeShot);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, shock, playerA);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, shock, playerA);

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, grapeShot, playerA);

        waitStackResolved(2, PhaseStep.POSTCOMBAT_MAIN, 1); // Let the storm ability resolve to put the copies on the stack
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, mindBreakTrap, "Grapeshot^Grapeshot^Grapeshot");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, shock, 2);
        assertExileCount(playerB, grapeShot, 1); // exiled by Mindbreak Trap
        assertGraveyardCount(playerA, mindBreakTrap, 1);
        assertLife(playerA, 16); // 2x2 from two Shock  = 4 and 3 (Storm twice) from Grapeshot get exiled
    }
}