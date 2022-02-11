
package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ScorchingLavaTest extends CardTestPlayerBase {

    /**
     * Tests that spells don't work for opponents but still work for controller
     */
    @Test
    public void testTargetGetExiled() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // Kicker {R}
        // Scorching Lava deals 2 damage to any target. If Scorching Lava was kicked, that creature can't be regenerated this turn and if it would die this turn, exile it instead.
        addCard(Zone.HAND, playerA, "Scorching Lava"); // Instant {1}{R}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scorching Lava", "Silvercoat Lion");
        setChoice(playerA, true); // with Kicker

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Scorching Lava", 1);
        assertExileCount(playerB, "Silvercoat Lion", 1);
    }

}
