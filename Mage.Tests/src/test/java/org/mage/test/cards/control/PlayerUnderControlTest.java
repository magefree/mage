package org.mage.test.cards.control;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.view.GameView;
import org.junit.Assert;
import org.junit.Test;
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
                Assert.assertTrue(info, playerA.isGameUnderControl());
                Assert.assertFalse(info, playerB.isGameUnderControl());

                Assert.assertTrue(info, playerA.getPlayersUnderYourControl().contains(playerB.getId()));
                Assert.assertTrue(info, playerB.getPlayersUnderYourControl().isEmpty());

                Assert.assertTrue(info, playerA.getTurnControllers().isEmpty());
                Assert.assertTrue(info, playerB.getTurnControllers().contains(playerA.getId()));

                Assert.assertEquals(info, playerA.getTurnControlledBy(), playerA.getId());
                Assert.assertEquals(info, playerB.getTurnControlledBy(), playerA.getId());

                // client side
                Assert.assertFalse(info, viewA.getOpponentHands().isEmpty());
                Assert.assertTrue(info, viewB.getOpponentHands().isEmpty());
            } else {
                // A,B normal
                Assert.assertTrue(info, playerA.isGameUnderControl());
                Assert.assertTrue(info, playerB.isGameUnderControl());

                Assert.assertTrue(info, playerA.getPlayersUnderYourControl().isEmpty());
                Assert.assertTrue(info, playerB.getPlayersUnderYourControl().isEmpty());

                Assert.assertTrue(info, playerA.getTurnControllers().isEmpty());
                Assert.assertTrue(info, playerB.getTurnControllers().isEmpty());

                Assert.assertEquals(info, playerA.getTurnControlledBy(), playerA.getId());
                Assert.assertEquals(info, playerB.getTurnControlledBy(), playerB.getId());

                // client side
                Assert.assertTrue(info, viewA.getOpponentHands().isEmpty());
                Assert.assertTrue(info, viewB.getOpponentHands().isEmpty());
            }
        });
    }
}
