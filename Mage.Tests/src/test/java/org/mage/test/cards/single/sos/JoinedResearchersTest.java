package org.mage.test.cards.single.sos;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class JoinedResearchersTest extends CardTestPlayerBase {

    @Test
    public void test_BecomesPrepared_OpponentsEndStep() {
        // At the beginning of each end step, if an opponent has more cards in hand than you,
        // this creature becomes prepared.
        addCard(Zone.BATTLEFIELD, playerA, "Joined Researchers");
        addCard(Zone.HAND, playerA, "Plains", 2);
        //
        addCard(Zone.HAND, playerB, "Island", 2);

        // turn 1 - your end step: 2 vs 2 cards, nothing
        checkExileCount("not prepared", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Secret Rendezvous", 0);

        // turn 2 - opponent's end step: 2 vs 2 + 1 cards (draw step), prepared
        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.UPKEEP);
        execute();

        assertExileCount(playerA, "Secret Rendezvous", 1);
    }

    @Test
    public void test_NotPrepared_OpponentHasNotMoreCards() {
        // At the beginning of each end step, if an opponent has more cards in hand than you,
        // this creature becomes prepared.
        addCard(Zone.BATTLEFIELD, playerA, "Joined Researchers");
        addCard(Zone.HAND, playerA, "Plains", 3);
        //
        addCard(Zone.HAND, playerB, "Island", 2);

        // turn 1 - 3 vs 2 cards, turn 2 - 3 vs 2 + 1 cards (draw step)
        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.UPKEEP);
        execute();

        assertExileCount(playerA, "Secret Rendezvous", 0);
    }
}
