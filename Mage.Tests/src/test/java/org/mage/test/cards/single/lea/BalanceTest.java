package org.mage.test.cards.single.lea;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BalanceTest extends CardTestPlayerBase {

    @Test
    public void testBalance() {

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Runeclaw Bear", 3);
        addCard(Zone.HAND, playerA, "Plains", 1);
        addCard(Zone.HAND, playerA, "Balance");

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Runeclaw Bear", 2);
        addCard(Zone.HAND, playerB, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balance");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Plains", 2);
        assertPermanentCount(playerA, "Runeclaw Bear", 2);
        assertHandCount(playerA, "Plains", 1);
        assertHandCount(playerA, "Balance", 0);
        assertGraveyardCount(playerA, "Runeclaw Bear", 1);
        assertGraveyardCount(playerA, "Balance", 1);

        assertPermanentCount(playerB, "Swamp", 2);
        assertPermanentCount(playerB, "Runeclaw Bear", 2);
        assertHandCount(playerB, "Swamp", 1);
        assertGraveyardCount(playerB, "Swamp", 2); //1 from hand, 1 from battlefield

    }

    @Test
    public void test_LandsAndCardsAreSeparateEvents() {
        removeAllCardsFromHand(playerB);

        // Each player chooses a number of lands they control equal to the number of lands controlled by the player
        // who controls the fewest, then sacrifices the rest. Players discard cards and sacrifice creatures the same way.
        addCard(Zone.HAND, playerA, "Balance"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.HAND, playerA, "Forest", 2);
        //
        // Whenever one or more land cards are put into your graveyard from anywhere, you gain 2 life.
        addCard(Zone.BATTLEFIELD, playerA, "Titania, Voice of Gaea");
        //
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);

        // sacrifice lands and discard cards are two different events, so two triggers
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balance");
        setChoice(playerA, "Forest"); // land to keep
        setChoice(playerB, "Swamp"); // land to keep
        setChoice(playerA, "Titania, Voice of Gaea"); // creature to keep
        setChoice(playerB, "Grizzly Bears"); // creature to keep
        setChoice(playerA, "Whenever one or more land cards"); // triggers order

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Forest", 1);
        assertGraveyardCount(playerA, "Plains", 2);
        assertGraveyardCount(playerA, "Forest", 2);
        assertLife(playerA, 20 + 2 + 2);
    }
}
