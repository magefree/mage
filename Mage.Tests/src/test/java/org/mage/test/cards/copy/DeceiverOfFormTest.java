
package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 *
 *
 */
public class DeceiverOfFormTest extends CardTestPlayerBase {

    /**
     * When creatures copy the revealed creature, they do not return to their
     * original state at the end of turn
     *
     */
    @Test
    public void testCopyEndsEndOfTurn() {
        // Vigilance
        addCard(Zone.LIBRARY, playerA, "Affa Protector", 1); // 1/4
        // At the beginning of combat on your turn, reveal the top card of your library.
        // If a creature card is revealed this way, you may have creatures you control other than Deceiver of Form becomes copies of that card until end of turn.
        // You may put that card on the bottom of your library.
        addCard(Zone.BATTLEFIELD, playerA, "Deceiver of Form", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 3);

        attack(1, playerA, "Deceiver of Form");
        attack(1, playerA, "Affa Protector");
        attack(1, playerA, "Affa Protector");
        attack(1, playerA, "Affa Protector");

        skipInitShuffling();

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Deceiver of Form", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 3);
        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2, Filter.ComparisonScope.All);

        assertLife(playerA, 20); // +2 from Robber
        assertLife(playerB, 9);

    }

}
