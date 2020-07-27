
package org.mage.test.cards.single.zen;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class InfernoTrapTest extends CardTestPlayerBase {

    @Test
    public void testTwoDamageStepsCountOnlyAsOneCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        // If you've been dealt damage by two or more creatures this turn, you may pay {R} rather than pay Inferno Trap's mana cost.
        // Inferno Trap deals 4 damage to target creature.
        addCard(Zone.HAND, playerA, "Inferno Trap"); // Instant {3}{R}

        // Flying, double strike
        addCard(Zone.BATTLEFIELD, playerB, "Skyhunter Skirmisher"); // 1/1

        attack(2, playerB, "Skyhunter Skirmisher");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Inferno Trap", "Skyhunter Skirmisher");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Inferno Trap", 0);
        assertGraveyardCount(playerB, "Skyhunter Skirmisher", 0);
    }

    @Test
    public void testPlayByAlternateCost() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        // If you've been dealt damage by two or more creatures this turn, you may pay {R} rather than pay Inferno Trap's mana cost.
        // Inferno Trap deals 4 damage to target creature.
        addCard(Zone.HAND, playerA, "Inferno Trap"); // Instant {3}{R}

        // Flying, double strike
        addCard(Zone.BATTLEFIELD, playerB, "Skyhunter Skirmisher"); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // 2/2

        attack(2, playerB, "Skyhunter Skirmisher");
        attack(2, playerB, "Silvercoat Lion");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Inferno Trap", "Skyhunter Skirmisher");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 16);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Inferno Trap", 1);
        assertGraveyardCount(playerB, "Skyhunter Skirmisher", 1);
    }

}
