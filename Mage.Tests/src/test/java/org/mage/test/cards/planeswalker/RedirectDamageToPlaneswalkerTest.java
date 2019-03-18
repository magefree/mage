
package org.mage.test.cards.planeswalker;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class RedirectDamageToPlaneswalkerTest extends CardTestPlayerBase {

    @Ignore
    @Test
    public void testDirectDamage() {
        // +2: Look at the top card of target player's library. You may put that card on the bottom of that player's library.
        // 0: Draw three cards, then put two cards from your hand on top of your library in any order.
        // −1: Return target creature to its owner's hand.
        addCard(Zone.BATTLEFIELD, playerA, "Jace, the Mind Sculptor"); // starts with 3 Loyality counters

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2:", playerB);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        setChoice(playerB, "Yes");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Jace, the Mind Sculptor", 1);
        assertCounterCount("Jace, the Mind Sculptor", CounterType.LOYALTY, 2);  // 3 + 2 - 3 = 2

        assertGraveyardCount(playerB, "Lightning Bolt", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }

}
