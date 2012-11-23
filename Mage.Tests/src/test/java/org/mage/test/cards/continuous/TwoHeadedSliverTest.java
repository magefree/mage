package org.mage.test.cards.continuous;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class TwoHeadedSliverTest extends CardTestPlayerBase {

    @Test
    public void testCantBeBlockedByOneEffectAbility() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 2);
        // All Sliver creatures have "This creature can't be blocked except by two or more creatures."
        addCard(Constants.Zone.HAND, playerA, "Two-Headed Sliver");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Plains");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Two-Headed Sliver");

        attack(3, playerA, "Two-Headed Sliver");
        // Block has to fail, because Two-Headed Sliver can't be blocked except by two or more creatures
        block(3, playerB, "Silvercoat Lion", "Two-Headed Sliver");

        setStopAt(3, Constants.PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Two-Headed Sliver", 1);
        assertLife(playerB, 19);
    }
}
