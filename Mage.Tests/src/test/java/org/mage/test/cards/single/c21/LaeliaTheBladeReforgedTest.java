package org.mage.test.cards.single.c21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.l.LaeliaTheBladeReforged Laelia, the Blade Reforged}
 * {2}{R}
 * Legendary Creature - Spirit Warrior
 * Haste
 * Whenever Laelia, the Blade Reforged attacks, exile the top card of your library. You may play that card this turn.
 * Whenever one or more cards are put into exile from your library and/or your graveyard, put a +1/+1 counter on Laelia.
 *
 * @author DominionSpy
 */
public class LaeliaTheBladeReforgedTest extends CardTestPlayerBase {

    private static final String laelia = "Laelia, the Blade Reforged";
    private static final String cranialExtraction = "Cranial Extraction";
    private static final String llanowarElves = "Llanowar Elves";

    @Test
    public void controllerExilesOwnCards() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, laelia);
        addCard(Zone.HAND, playerA, cranialExtraction);
        addCard(Zone.HAND, playerA, llanowarElves);
        addCard(Zone.GRAVEYARD, playerA, llanowarElves);
        addCard(Zone.LIBRARY, playerA, llanowarElves);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cranialExtraction, playerA);
        // Choose a nonland card name (Llanowar Elves)
        setChoice(playerA, llanowarElves);
        // Graveyard
        setChoice(playerA, llanowarElves);
        // Hand
        setChoice(playerA, llanowarElves);
        // Library
        setChoice(playerA, llanowarElves);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, laelia, CounterType.P1P1, 1);
    }

    @Test
    public void opponentExilesControllersCards() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.HAND, playerA, cranialExtraction);

        addCard(Zone.BATTLEFIELD, playerB, laelia);
        addCard(Zone.HAND, playerB, llanowarElves);
        addCard(Zone.GRAVEYARD, playerB, llanowarElves);
        addCard(Zone.LIBRARY, playerB, llanowarElves);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cranialExtraction, playerB);
        // Choose a nonland card name (Llanowar Elves)
        setChoice(playerA, llanowarElves);
        // Graveyard
        setChoice(playerA, llanowarElves);
        // Hand
        setChoice(playerA, llanowarElves);
        // Library
        setChoice(playerA, llanowarElves);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerB, laelia, CounterType.P1P1, 1);
    }

    @Test
    public void controllerExilesOpponentsCards() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, laelia);
        addCard(Zone.HAND, playerA, cranialExtraction);

        addCard(Zone.HAND, playerB, llanowarElves);
        addCard(Zone.GRAVEYARD, playerB, llanowarElves);
        addCard(Zone.LIBRARY, playerB, llanowarElves);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cranialExtraction, playerB);
        // Choose a nonland card name (Llanowar Elves)
        setChoice(playerA, llanowarElves);
        // Graveyard
        setChoice(playerA, llanowarElves);
        // Hand
        setChoice(playerA, llanowarElves);
        // Library
        setChoice(playerA, llanowarElves);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, laelia, CounterType.P1P1, 0);
    }

    @Test
    public void opponentExilesOwnCards() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.HAND, playerA, cranialExtraction);
        addCard(Zone.HAND, playerA, llanowarElves);
        addCard(Zone.GRAVEYARD, playerA, llanowarElves);
        addCard(Zone.LIBRARY, playerA, llanowarElves);

        addCard(Zone.BATTLEFIELD, playerB, laelia);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cranialExtraction, playerA);
        // Choose a nonland card name (Llanowar Elves)
        setChoice(playerA, llanowarElves);
        // Graveyard
        setChoice(playerA, llanowarElves);
        // Hand
        setChoice(playerA, llanowarElves);
        // Library
        setChoice(playerA, llanowarElves);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerB, laelia, CounterType.P1P1, 0);
    }
}
