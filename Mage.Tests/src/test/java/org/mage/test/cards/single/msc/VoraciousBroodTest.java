package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class VoraciousBroodTest extends CardTestPlayerBase {

    @Test
    public void test_CreatureCardsMilled_ThatManyCounters() {
        removeAllCardsFromLibrary(playerA);

        // This creature enters with a +1/+1 counter on it for each creature card in your graveyard.
        // Whenever one or more creature cards are put into your graveyard from anywhere, put that many +1/+1 counters on this creature.
        addCard(Zone.BATTLEFIELD, playerA, "Voracious Brood"); // 1/1
        //
        // Target player mills ten cards.
        addCard(Zone.HAND, playerA, "Glimpse the Unthinkable"); // {U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        //
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 2);
        addCard(Zone.LIBRARY, playerA, "Forest", 1);

        checkPermanentCounters("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Voracious Brood", CounterType.P1P1, 0);

        // mill 2 creatures and 1 land at once
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glimpse the Unthinkable", playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 2);
        assertCounterCount(playerA, "Voracious Brood", CounterType.P1P1, 2);
    }

    @Test
    public void test_OpponentCreatureCardsMilled_NoTrigger() {
        removeAllCardsFromLibrary(playerB);

        // Whenever one or more creature cards are put into your graveyard from anywhere, put that many +1/+1 counters on this creature.
        addCard(Zone.BATTLEFIELD, playerA, "Voracious Brood"); // 1/1
        //
        // Target player mills ten cards.
        addCard(Zone.HAND, playerA, "Glimpse the Unthinkable"); // {U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        //
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glimpse the Unthinkable", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Grizzly Bears", 2);
        assertCounterCount(playerA, "Voracious Brood", CounterType.P1P1, 0);
    }
}
