package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class AvengeTest extends CardTestPlayerBase {

    // This spell costs {2} less to cast if a player attacked you during their last turn.
    // Destroy all creatures. You gain 1 life for each creature destroyed this way.
    private static final String avenge = "Avenge";

    private static final String lion = "Silvercoat Lion";
    private static final String bear = "Grizzly Bears";

    @Test
    public void test_NoAttack_NoCostReduction() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, avenge);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerB, bear);

        checkPlayableAbility("no attack, full cost", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + avenge, false);

        setStopAt(3, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_AttackedLastTurn_CostReducedAndGainLife() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, avenge);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerB, bear);

        attack(2, playerB, bear, playerA);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, avenge);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, lion, 1);
        assertGraveyardCount(playerB, bear, 1);
        assertLife(playerA, 20 - 2 + 2);
        assertTappedCount("Plains", true, 4);
    }

    @Test
    public void test_AttackedTwoTurnsAgo_NoCostReduction() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, avenge);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerB, bear);

        // attack on turn 2, but on turn 4 (playerB's last turn before turn 5) there is no attack
        attack(2, playerB, bear, playerA);

        checkPlayableAbility("attack is too old", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + avenge, false);

        setStopAt(5, PhaseStep.END_TURN);
        execute();
    }
}
