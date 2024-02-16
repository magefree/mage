
package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.s.Silence Silence}
 * {W}
 * Instant
 * Your opponents can’t cast spells this turn.
 *
 * @author LevelX2
 */

public class SilenceTest extends CardTestPlayerBase {

    @Test
    public void testSilence() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.HAND, playerA, "Silence");

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.HAND, playerB, "Silvercoat Lion", 1);
        
        castSpell(2, PhaseStep.UPKEEP, playerA, "Silence");

        checkPlayableAbility("Can't cast spell", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Silvercoat", false);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        
        execute();

        assertGraveyardCount(playerA, "Silence", 1);
    }
}