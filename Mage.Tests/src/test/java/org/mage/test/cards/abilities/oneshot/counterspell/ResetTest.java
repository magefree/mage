
package org.mage.test.cards.abilities.oneshot.counterspell;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ResetTest extends CardTestPlayerBase {

    /*
     * I was playing a game against Show and Tell with the deck Solidarity (or
     * Reset High Tide) and xmage would not allow me to play Reset costing me
     * the match.
     *
     * I suspect it may be a timing issue because of Resets odd wording
     * preventing it from being played on the opponents upkeep.
     *
     * It was working in the previous build so this issue surprised me.
     */

    /**
     * Should be allowed to cast Reset on opponent's turn after their upkeep.
     */
    @Test
    public void testResetShouldWork() {
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2, true);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        // Cast Reset only during an opponent's turn after their upkeep step.
        // Untap all lands you control.
        addCard(Zone.HAND, playerB, "Reset");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Reset");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTappedCount("Island", false,4);
        assertGraveyardCount(playerB, "Reset", 1);
    }

    /**
     * Should not be allowed to cast reset during your own turn.
     */
    @Test
    public void testResetShouldNotWork() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2, true);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        // Cast Reset only during an opponent's turn after their upkeep step.
        // Untap all lands you control.
        addCard(Zone.HAND, playerA, "Reset");

        checkPlayableAbility("during", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Reset", false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }
}
