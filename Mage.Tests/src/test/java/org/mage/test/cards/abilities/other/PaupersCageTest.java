package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class PaupersCageTest extends CardTestPlayerBase {

    // Paupers' Cage
    // At the beginning of each opponent's upkeep, if that player has two or fewer cards in hand,
    // Paupers' Cage deals 2 damage to that player.

    @Test
    public void test_TooManyCards() {
        addCard(Zone.BATTLEFIELD, playerA, "Paupers' Cage", 1);
        //
        addCard(Zone.HAND, playerA, "Island", 5);
        addCard(Zone.HAND, playerB, "Island", 5);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    @Test
    public void test_YouHaveFewCards() {
        addCard(Zone.BATTLEFIELD, playerA, "Paupers' Cage", 1);
        //
        //addCard(Zone.HAND, playerA, "Island", 5);
        addCard(Zone.HAND, playerB, "Island", 5);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    @Test
    public void test_OpponentHaveFewCards() {
        addCard(Zone.BATTLEFIELD, playerA, "Paupers' Cage", 1);
        //
        addCard(Zone.HAND, playerA, "Island", 5);
        //addCard(Zone.HAND, playerB, "Island", 5);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_OpponentHaveFewCardsMultipleTurns() {
        addCard(Zone.BATTLEFIELD, playerA, "Paupers' Cage", 1);
        //
        addCard(Zone.HAND, playerA, "Island", 5);
        //addCard(Zone.HAND, playerB, "Island", 5);

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 2 * 2);
    }
}
