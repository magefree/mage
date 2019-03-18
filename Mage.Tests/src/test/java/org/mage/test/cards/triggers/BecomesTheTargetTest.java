
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class BecomesTheTargetTest extends CardTestPlayerBase {

    /**
     * Willbreaker is not working when an ability is targeting the opponet's
     * creature. Only spells.
     *
     */
    @Test
    public void testWillbreakerAbility() {
        // Whenever a creature an opponent controls becomes the target of a spell or ability you control, gain control of that creature for as long as you control Willbreaker.
        addCard(Zone.BATTLEFIELD, playerB, "Willbreaker", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Blinding Souleater", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{W/P}, {T}: Tap target creature", "Silvercoat Lion");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 18);

        assertPermanentCount(playerA, "Silvercoat Lion", 0);
        assertTapped("Silvercoat Lion", true);

        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Willbreaker", 1);
        assertPermanentCount(playerB, "Blinding Souleater", 1);

    }
}
