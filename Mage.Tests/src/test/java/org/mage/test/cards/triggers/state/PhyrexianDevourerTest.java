
package org.mage.test.cards.triggers.state;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class PhyrexianDevourerTest extends CardTestPlayerBase {

    /**
     * Check that Phyrexian Devourer is sacrifriced as soon as the counters are
     * added
     *
     */
    @Test
    public void testBoostChecked() {
        // When Phyrexian Devourer's power is 7 or greater, sacrifice it.
        // Exile the top card of your library: Put X +1/+1 counters on Phyrexian Devourer, where X is the exiled card's converted mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Phyrexian Devourer");
        addCard(Zone.LIBRARY, playerA, "Phyrexian Devourer");
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        attack(2, playerB, "Silvercoat Lion");
        block(2, playerA, "Phyrexian Devourer", "Silvercoat Lion");

        activateAbility(2, PhaseStep.DECLARE_BLOCKERS, playerA, "Exile the top card of your library");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Phyrexian Devourer", 1);
        assertExileCount("Phyrexian Devourer", 1);

        assertPermanentCount(playerB, "Silvercoat Lion", 1);

    }

}
