
package org.mage.test.cards.single.lrw;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Quercitron
 */
public class NeedleDropTest extends CardTestPlayerBase {

    @Test
    public void testTargetShouldBeDamaged() {
        // Needle Drop deals 1 damage to any target that was dealt damage this turn.
        addCard(Zone.HAND, playerA, "Needle Drop", 4);
        addCard(Zone.HAND, playerA, "Shock", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);

        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant");
        addCard(Zone.BATTLEFIELD, playerB, "Flying Men");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "Hill Giant");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Needle Drop", playerA);
//        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Needle Drop", playerB); // TODO: This should produce an error but doesn't (playerA isn't a valid target)
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Needle Drop", "Hill Giant");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 17);
        assertLife(playerB, 20);
        assertPermanentCount(playerB, "Hill Giant", 0);
    }

    @Test
    public void testTargetIsNotConsideredDamagedNextTurn() {
        // Needle Drop deals 1 damage to any target that was dealt damage this turn.
        addCard(Zone.HAND, playerA, "Needle Drop", 2);
        addCard(Zone.HAND, playerA, "Shock", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "Hill Giant");

        checkPlayableAbility("Can't Needle Drop", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Needle Drop", false);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 18);
        assertPermanentCount(playerB, "Hill Giant", 1);
    }
}
