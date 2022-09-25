package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class CompleatedTest extends CardTestPlayerBase {

    private static final String tamiyo = "Tamiyo, Compleated Sage";

    @Test
    public void testLifePaid() {
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 4);
        addCard(Zone.HAND, playerA, tamiyo);

        setChoice(playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, tamiyo);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, tamiyo, CounterType.LOYALTY, 3);
    }

    @Test
    public void testManaPaid() {
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 5);
        addCard(Zone.HAND, playerA, tamiyo);

        setChoice(playerA, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, tamiyo);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, tamiyo, CounterType.LOYALTY, 5);
    }
}
