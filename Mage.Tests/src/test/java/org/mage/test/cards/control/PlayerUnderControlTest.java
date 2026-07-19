package org.mage.test.cards.control;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.view.GameView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Test Framework do not support under control commands, so check only game related info and data
 *
 * @author JayDi85
 */
public class PlayerUnderControlTest extends CardTestPlayerBase {

    @Test
    public void test_ClientSideDataMustBeHidden() {
        // possible bug: after control ends - player still able to view opponent's hands

        // When you cast Emrakul, you gain control of target opponent during that player's next turn.
        // After that turn, that player takes an extra turn.
        addCard(Zone.HAND, playerA, "Emrakul, the Promised End"); // {13}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 13);
        //
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        // prepare control effect
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Emrakul, the Promised End");
        addTarget(playerA, playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        checkUnderControl("turn 1 - A, B normal", 1, PhaseStep.PRECOMBAT_MAIN, false);
        checkUnderControl("turn 2 - B under A", 2, PhaseStep.PRECOMBAT_MAIN, true);
        checkUnderControl("turn 3 - A, B normal", 3, PhaseStep.PRECOMBAT_MAIN, false);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
    }

    private void checkUnderControl(String info, int turnNum, PhaseStep step, boolean mustHaveControl) {
        runCode(info, turnNum, step, playerA, (info1, player, game) -> {
            GameView viewA = getGameView(playerA);
            GameView viewB = getGameView(playerB);
            if (mustHaveControl) {
                Assertions.assertTrue(playerA.isGameUnderControl(), info);
                Assertions.assertFalse(playerB.isGameUnderControl(), info);

                Assertions.assertTrue(playerA.getPlayersUnderYourControl().contains(playerB.getId()), info);
                Assertions.assertTrue(playerB.getPlayersUnderYourControl().isEmpty(), info);

                Assertions.assertTrue(playerA.getTurnControllers().isEmpty(), info);
                Assertions.assertTrue(playerB.getTurnControllers().contains(playerA.getId()), info);

                Assertions.assertEquals(playerA.getTurnControlledBy(), playerA.getId(), info);
                Assertions.assertEquals(playerB.getTurnControlledBy(), playerA.getId(), info);

                // client side
                Assertions.assertFalse(viewA.getOpponentHands().isEmpty(), info);
                Assertions.assertTrue(viewB.getOpponentHands().isEmpty(), info);
            } else {
                // A,B normal
                Assertions.assertTrue(playerA.isGameUnderControl(), info);
                Assertions.assertTrue(playerB.isGameUnderControl(), info);

                Assertions.assertTrue(playerA.getPlayersUnderYourControl().isEmpty(), info);
                Assertions.assertTrue(playerB.getPlayersUnderYourControl().isEmpty(), info);

                Assertions.assertTrue(playerA.getTurnControllers().isEmpty(), info);
                Assertions.assertTrue(playerB.getTurnControllers().isEmpty(), info);

                Assertions.assertEquals(playerA.getTurnControlledBy(), playerA.getId(), info);
                Assertions.assertEquals(playerB.getTurnControlledBy(), playerB.getId(), info);

                // client side
                Assertions.assertTrue(viewA.getOpponentHands().isEmpty(), info);
                Assertions.assertTrue(viewB.getOpponentHands().isEmpty(), info);
            }
        });
    }
}
