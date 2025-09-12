package org.mage.test.cards.single.ltr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */

public class BreakingOfTheFellowshipTest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_PossibleTargets() {
        // Target creature an opponent controls deals damage equal to its power to another target creature that player controls.
        addCard(Zone.HAND, playerA, "Breaking of the Fellowship"); // {1}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        addCard(Zone.HAND, playerB, "Grizzly Bears"); // 2/2, {1}{G}
        addCard(Zone.HAND, playerB, "Spectral Bears"); // 3/3, {1}{G}
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2 * 2);

        // turn 1 - no targets, can't play
        checkPlayableAbility("no targets - can't cast", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Breaking of the Fellowship", false);

        // turn 3 - 1 of 2 targets, can't play
        castSpell(3 - 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Grizzly Bears");
        checkPlayableAbility("1 of 2 targets - can't cast", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Breaking of the Fellowship", false);

        // turn 5 - 2 of 2 targets, can play
        castSpell(5 - 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Spectral Bears");
        checkPlayableAbility("2 of 2 targets - can cast", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Breaking of the Fellowship", true);

        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Breaking of the Fellowship", "Grizzly Bears^Spectral Bears");

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertDamageReceived(playerB, "Spectral Bears", 2);
    }

    @Test
    public void test_Normal_AI() {
        // Target creature an opponent controls deals damage equal to its power to another target creature that player controls.
        addCard(Zone.HAND, playerA, "Breaking of the Fellowship"); // {1}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Spectral Bears"); // 3/3

        // AI must choose 3/3 to kill 2/2
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Grizzly Bears", 1);
    }

    @Test
    public void test_Protection_AI() {
        // Target creature an opponent controls deals damage equal to its power to another target creature that player controls.
        addCard(Zone.HAND, playerA, "Breaking of the Fellowship"); // {1}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Spectral Bears"); // 3/3
        addCard(Zone.BATTLEFIELD, playerB, "Lavinia of the Tenth"); // 4/4, Protection from red

        // can't choose 4/4 to kill 3/3 due protection from red
        // AI must choose 3/3 to kill 2/2
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Grizzly Bears", 1);
    }
}