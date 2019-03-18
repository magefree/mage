
package org.mage.test.cards.cost.modification;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class DefenseGridTest extends CardTestPlayerBase {

    /**
     * Defense Grid vs Mindbreak Trap Not sure how this is coded, but Mindbreak
     * Trap should still cost 3 more (0+3=3).
     *
     */
    @Test
    public void testCostIncrease() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 3);

        // Each spell costs {3} more to cast except during its controller's turn.
        addCard(Zone.BATTLEFIELD, playerA, "Defense Grid");

        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        // If an opponent cast three or more spells this turn, you may pay {0} rather than pay Mindbreak Trap's mana cost.
        // Exile any number of target spells.
        addCard(Zone.HAND, playerB, "Mindbreak Trap"); // {2}{U}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Mindbreak Trap", "Lightning Bolt^Lightning Bolt^Lightning Bolt");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount("Lightning Bolt", 3);
        assertGraveyardCount(playerB, "Mindbreak Trap", 1);

        assertTappedCount("Island", true, 3);

        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }

}
