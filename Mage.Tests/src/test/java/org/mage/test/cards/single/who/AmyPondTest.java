package org.mage.test.cards.single.who;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Steven Knipe
 */
public class AmyPondTest extends CardTestPlayerBase {

    private static final String amy = "Amy Pond"; // 2/2
    private static final String kraken = "Deep-Sea Kraken"; // Suspend 9-{2}{U}

    @Test
    public void testRemovesTimeCountersEqualToTheDamage() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, amy);
        addCard(Zone.HAND, playerA, kraken);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Suspend");
        attack(1, playerA, amy, playerB);
        setChoice(playerA, kraken); // a non-target choice, not a target

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 2);
        assertExileCount(kraken, 1);
        assertCounterOnExiledCardCount(kraken, CounterType.TIME, 9 - 2);
    }
}
