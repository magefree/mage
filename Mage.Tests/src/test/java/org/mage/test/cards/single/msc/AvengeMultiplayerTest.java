package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBaseWithRangeAll;

/**
 * @author DarioBarrabinoDev
 */
public class AvengeMultiplayerTest extends CardTestMultiPlayerBaseWithRangeAll {

    // This spell costs {2} less to cast if a player attacked you during their last turn.
    // Destroy all creatures. You gain 1 life for each creature destroyed this way.
    private static final String avenge = "Avenge";

    private static final String lion = "Silvercoat Lion";
    private static final String bear = "Grizzly Bears";

    // 800.4i If an effect requires information from the game about actions players have taken during their "last turn,"
    // it can find the actions that were taken by a player who has left the game only until that player's next turn
    // after leaving the game would have begun.

    @Test
    public void test_AttackerLeftGame_CostStillReducedBeforeTheirNextTurn() {
        // Player order: A -> D -> C -> B, each player can attack only the next one, so B attacks A
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, avenge);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerB, bear);

        attack(4, playerB, bear, playerA);
        concede(4, PhaseStep.POSTCOMBAT_MAIN, playerB);

        checkPlayerInGame("B left", 5, PhaseStep.PRECOMBAT_MAIN, playerA, playerB, false);
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, avenge);

        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, lion, 1);
        assertLife(playerA, 20 - 2 + 1);
        assertTappedCount("Plains", true, 4);
    }

    @Test
    public void test_AttackerLeftGame_NoCostReductionAfterTheirNextTurnWouldHaveBegun() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, avenge);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerB, bear);

        attack(4, playerB, bear, playerA);
        concede(4, PhaseStep.POSTCOMBAT_MAIN, playerB);

        checkPlayableAbility("before B's next turn", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + avenge, true);
        // turns 6 and 7 are D and C, then B's turn is skipped
        checkPlayableAbility("after B's next turn", 8, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + avenge, false);

        setStopAt(8, PhaseStep.END_TURN);
        execute();
    }
}
