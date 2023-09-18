package org.mage.test.cards.single.stx;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class EmergentSequenceTest extends CardTestPlayerBase {

    @Test
    public void test_PlayFirst() {
        removeAllCardsFromLibrary(playerA);

        // Search your library for a basic land card, put it onto the battlefield tapped, then shuffle.
        // That land becomes a 0/0 green and blue Fractal creature that's still a land. Put a +1/+1 counter on
        // it for each land you had enter the battlefield under your control this turn.
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2); // land goes first to ignore cheated etb
        addCard(Zone.HAND, playerA, "Emergent Sequence"); // {1}{G}
        //
        addCard(Zone.LIBRARY, playerA, "Swamp", 1);
        addCard(Zone.LIBRARY, playerA, "Mountain", 1);

        // cast and etb land with 1x counter (counts from land)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Emergent Sequence");
        addTarget(playerA, "Swamp");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertType("Swamp", CardType.CREATURE, true);
        assertCounterCount(playerA, "Swamp", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Swamp", 1, 1);
    }

    @Test
    public void test_PlayAfterLand() {
        removeAllCardsFromLibrary(playerA);

        // Search your library for a basic land card, put it onto the battlefield tapped, then shuffle.
        // That land becomes a 0/0 green and blue Fractal creature that's still a land. Put a +1/+1 counter on
        // it for each land you had enter the battlefield under your control this turn.
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2); // land goes first to ignore cheated etb
        addCard(Zone.HAND, playerA, "Emergent Sequence"); // {1}{G}
        //
        addCard(Zone.HAND, playerA, "Island", 1);
        addCard(Zone.LIBRARY, playerA, "Swamp", 1);
        addCard(Zone.LIBRARY, playerA, "Mountain", 1);

        // play land first
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Island");

        // cast and etb land with 2x counter (counts from etb land + land before)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Emergent Sequence");
        addTarget(playerA, "Swamp");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Island", 1);
        assertType("Swamp", CardType.CREATURE, true);
        assertCounterCount(playerA, "Swamp", CounterType.P1P1, 2);
        assertPowerToughness(playerA, "Swamp", 2, 2);
    }
}
