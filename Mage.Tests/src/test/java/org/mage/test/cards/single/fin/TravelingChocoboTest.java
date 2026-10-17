package org.mage.test.cards.single.fin;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class TravelingChocoboTest extends CardTestPlayerBase {

    @Test
    public void test_OneOrMoreBirdsEnter_TriggersAdditionalTime() {
        // If a land or Bird you control entering the battlefield causes a triggered ability of a permanent you control
        // to trigger, that ability triggers an additional time.
        addCard(Zone.BATTLEFIELD, playerA, "Traveling Chocobo");
        //
        // Whenever one or more tokens enter the battlefield under your control, each opponent loses 1 life and you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Kambal, Profiteering Mayor");
        //
        // Create two 1/1 white Bird creature tokens with flying.
        addCard(Zone.HAND, playerA, "Battle Screech"); // {2}{W}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        // two birds enter at once - one trigger + one additional trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Battle Screech");
        setChoice(playerA, "Whenever one or more tokens you control enter"); // two triggers order

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Bird Token", 2);
        assertLife(playerA, 20 + 2);
        assertLife(playerB, 20 - 2);
    }
}
