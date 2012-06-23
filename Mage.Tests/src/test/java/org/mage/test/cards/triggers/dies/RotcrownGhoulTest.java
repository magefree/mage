package org.mage.test.cards.triggers.dies;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 *
 *   When Rotcrown Ghoul dies, target player puts the top five cards of his or her library into his or her graveyard.
 */
public class RotcrownGhoulTest extends CardTestPlayerBase {

    @Test
    public void testDiesTriggeredAbility() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Rotcrown Ghoul", 1);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Rotcrown Ghoul");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        // Lightning Bolt + 5 cards
        assertGraveyardCount(playerA, 6);
        // Rotcrown Ghoul
        assertGraveyardCount(playerB, 1);
    }

    @Test
    public void testDiesTriggeredAbilityForTwoCopies() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Rotcrown Ghoul", 2);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Rotcrown Ghoul");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        // Lightning Bolt + 5 cards
        assertGraveyardCount(playerA, 6);
        // Rotcrown Ghoul
        assertGraveyardCount(playerB, 1);
    }

}
