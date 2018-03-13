package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author LevelX2
 */
public class TwoHeadedSliverTest extends CardTestPlayerBase {

    @Test
    public void testCantBeBlockedByOneEffectAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        // All Sliver creatures have "This creature can't be blocked except by two or more creatures."
        addCard(Zone.HAND, playerA, "Two-Headed Sliver");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Plains");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Two-Headed Sliver");

        attack(3, playerA, "Two-Headed Sliver");
        // Block has to fail, because Two-Headed Sliver can't be blocked except by two or more creatures
        block(3, playerB, "Silvercoat Lion", "Two-Headed Sliver");

        setStopAt(3, PhaseStep.END_TURN);

        try {
            execute();
            fail("Expected exception not thrown");
        } catch (UnsupportedOperationException e) {
            assertEquals("Two-Headed Sliver is blocked by 1 creature(s). It has to be blocked by 2 or more.", e.getMessage());
        }

    }

    @Test
    public void testCanBeBlockedByTwoEffectAbility() {

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        // Two-Headed Sliver {1}{R} 1/1
        // All Sliver creatures have "This creature can't be blocked except by two or more creatures."
        addCard(Zone.HAND, playerA, "Two-Headed Sliver");

        // Silvercoat Lion {1}{W} 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        // Coral Barrier {2}{U} 1/3
        addCard(Zone.BATTLEFIELD, playerB, "Coral Barrier");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Two-Headed Sliver");

        attack(3, playerA, "Two-Headed Sliver");
        // Two blocks will succeed
        block(3, playerB, "Silvercoat Lion", "Two-Headed Sliver");
        block(3, playerB, "Coral Barrier", "Two-Headed Sliver");

        setStopAt(3, PhaseStep.END_TURN);

        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        // Two-Headed Sliver died from the block
        assertPermanentCount(playerA, "Two-Headed Sliver", 0);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Coral Barrier", 1);
        assertGraveyardCount(playerA, "Two-Headed Sliver", 1);
    }
}