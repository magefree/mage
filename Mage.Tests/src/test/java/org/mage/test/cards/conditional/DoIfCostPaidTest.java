package org.mage.test.cards.conditional;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Quercitron
 */
public class DoIfCostPaidTest extends CardTestPlayerBase {

    @Test
    public void testPayIsNotOptional() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        // Shock deals 2 damage to any target.
        addCard(Zone.HAND, playerA, "Shock", 1);

        // When a source an opponent controls deals damage to you, sacrifice Awaken the Sky Tyrant.
        // If you do, put a 5/5 red Dragon creature token with flying onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerB, "Awaken the Sky Tyrant");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", playerB);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Awaken the Sky Tyrant", 0);
        assertPermanentCount(playerB, "Dragon Token", 1);
    }

}
