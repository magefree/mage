package org.mage.test.cards.single.msh;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author muz
 */
public class LeaderSuperGeniusTest extends CardTestPlayerBase {

    private static final String leader = "Leader, Super-Genius";
    private static final String lion = "Silvercoat Lion";
    private static final String bolt = "Lightning Bolt";

    @Test
    public void testBeginCombatConniveGetsExtraDrawAndCounter() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Island", 5);

        addCard(Zone.BATTLEFIELD, playerA, leader);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.HAND, playerA, bolt);

        setStrictChooseMode(true);

        // Leader's beginning of combat trigger
        addTarget(playerA, lion);
        setChoice(playerA, bolt); // discard a nonland for connive

        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();

        assertCounterCount(playerA, lion, CounterType.P1P1, 1);
        assertHandCount(playerA, 2); // start 1 + replacement draw 1 + connive draw/discard net 0
        assertGraveyardCount(playerA, bolt, 1);
    }
}
