package org.mage.test.cards.single.eve;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class DreamFractureTest extends CardTestPlayerBase {

    private static final String dreamFracture = "Dream Fracture";
    private static final String shock = "Shock";
    private static final String lorescale = "Lorescale Coatl"; // When you draw a card, +1/+1 counter

    @Test
    public void testSpellOpponent() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerA, dreamFracture);
        addCard(Zone.HAND, playerB, shock);
        addCard(Zone.BATTLEFIELD, playerA, lorescale);
        addCard(Zone.BATTLEFIELD, playerB, lorescale);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, shock, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, dreamFracture, shock, shock);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, dreamFracture, 1);
        assertGraveyardCount(playerB, shock, 1);
        assertCounterCount(playerA, lorescale, CounterType.P1P1, 1);
        assertCounterCount(playerB, lorescale, CounterType.P1P1, 1);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    @Test
    public void testSpellOwn() {
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 4);
        addCard(Zone.HAND, playerA, dreamFracture);
        addCard(Zone.HAND, playerA, shock);
        addCard(Zone.BATTLEFIELD, playerA, lorescale);
        addCard(Zone.BATTLEFIELD, playerB, lorescale);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shock, playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, dreamFracture, shock, shock);

        setChoice(playerA, "Whenever you draw"); // order triggers

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, dreamFracture, 1);
        assertGraveyardCount(playerA, shock, 1);
        assertCounterCount(playerA, lorescale, CounterType.P1P1, 2);
        assertCounterCount(playerB, lorescale, CounterType.P1P1, 0);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

}
