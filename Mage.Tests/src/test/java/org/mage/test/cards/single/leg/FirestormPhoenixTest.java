package org.mage.test.cards.single.leg;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class FirestormPhoenixTest extends CardTestPlayerBase {

    @Test
    public void test_DiesOnYourTurn_CantPlayUntilYourNextTurn() {
        // If this creature would die, return it to its owner's hand instead. Until that player's next turn, that
        // player plays with that card revealed in their hand and can't play it.
        addCard(Zone.BATTLEFIELD, playerA, "Firestorm Phoenix"); // {4}{R}{R}, 3/2
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Firestorm Phoenix");

        checkHandCardCount("returned", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Firestorm Phoenix", 1);
        checkPlayableAbility("this turn", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Firestorm Phoenix", false);
        checkPlayableAbility("your next turn", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Firestorm Phoenix", true);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_DiesOnOpponentsTurn_CantPlayUntilYourNextTurn() {
        // If this creature would die, return it to its owner's hand instead. Until that player's next turn, that
        // player plays with that card revealed in their hand and can't play it.
        addCard(Zone.BATTLEFIELD, playerA, "Firestorm Phoenix"); // {4}{R}{R}, 3/2
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        //
        addCard(Zone.HAND, playerB, "Lightning Bolt"); // {R}
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Firestorm Phoenix");

        checkHandCardCount("returned", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Firestorm Phoenix", 1);
        checkPlayableAbility("your next turn", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Firestorm Phoenix", true);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
    }
}
