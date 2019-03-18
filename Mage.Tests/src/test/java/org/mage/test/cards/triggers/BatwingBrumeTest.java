

package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class BatwingBrumeTest extends CardTestPlayerBase {
    /**
     * Batwing Brume
     * Instant {B}{1}
     * Prevent all combat damage that would be dealt this turn if {W} was spent to
     * cast Batwing Brume. Each player loses 1 life for each attacking creature he
     * or she controls if {B} was spent to cast Batwing Brume.
     *
     */

    @Test
    public void testWhiteAndBlackManaWasPaidCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.HAND, playerA, "Batwing Brume");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        attack(2, playerB, "Silvercoat Lion");
        castSpell(2, PhaseStep.DECLARE_BLOCKERS, playerA, "Batwing Brume");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);

    }

    @Test
    public void testOnlyBlackManaWasPaidCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.HAND, playerA, "Batwing Brume");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        attack(2, playerB, "Silvercoat Lion");
        castSpell(2, PhaseStep.DECLARE_BLOCKERS, playerA, "Batwing Brume");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 19);
    }

}
