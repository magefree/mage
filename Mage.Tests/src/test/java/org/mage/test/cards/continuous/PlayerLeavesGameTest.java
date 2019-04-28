package org.mage.test.cards.continuous;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.constants.Duration;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBaseWithRangeAll;

/**
 * @author JayDi85
 */
public class PlayerLeavesGameTest extends CardTestMultiPlayerBaseWithRangeAll {

    /*
    800.4a  When a player leaves the game, all objects (see rule 109) owned by that player leave the game and any effects
    which give that player control of any objects or players end. Then, if that player controlled any objects on the stack
    not represented by cards, those objects cease to exist. Then, if there are any objects still controlled by that player,
    those objects are exiled. This is not a state-based action. It happens as soon as the player leaves the game.
    If the player who left the game had priority at the time he or she left, priority passes to the next player in turn
    order who’s still in the game.
     */

    String cardBear2 = "Balduvian Bears"; // 2/2

    @Test
    public void test_PlayerLeaveGame() {
        // Player order: A -> D -> C -> B

        // B must checks A for online status
        checkPlayerInGame("turn 1", 1, PhaseStep.PRECOMBAT_MAIN, playerD, playerA, true);
        checkPlayerInGame("turn 2", 2, PhaseStep.PRECOMBAT_MAIN, playerD, playerA, true);
        checkPlayerInGame("turn 3 before", 3, PhaseStep.PRECOMBAT_MAIN, playerD, playerA, true);

        concede(3, PhaseStep.POSTCOMBAT_MAIN, playerA);

        checkPlayerInGame("turn 3 after", 3, PhaseStep.END_TURN, playerD, playerA, false);
        checkPlayerInGame("turn 4", 4, PhaseStep.PRECOMBAT_MAIN, playerD, playerA, false);

        setStopAt(4, PhaseStep.CLEANUP);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_PlayerLeaveGameWithOwnPermanent() {
        // Player order: A -> D -> C -> B

        addCard(Zone.BATTLEFIELD, playerA, cardBear2, 1);

        // B must checks A for online status
        checkPlayerInGame("turn 1", 1, PhaseStep.PRECOMBAT_MAIN, playerD, playerA, true);
        checkPermanentCount("turn 1", 1, PhaseStep.PRECOMBAT_MAIN, playerD, playerA, cardBear2, 1);
        checkPlayerInGame("turn 2", 2, PhaseStep.PRECOMBAT_MAIN, playerD, playerA, true);
        checkPermanentCount("turn 2", 2, PhaseStep.PRECOMBAT_MAIN, playerD, playerA, cardBear2, 1);
        checkPlayerInGame("turn 3 before", 3, PhaseStep.PRECOMBAT_MAIN, playerD, playerA, true);
        checkPermanentCount("turn 3 before", 3, PhaseStep.PRECOMBAT_MAIN, playerD, playerA, cardBear2, 1);

        concede(3, PhaseStep.POSTCOMBAT_MAIN, playerA);

        checkPlayerInGame("turn 3 after", 3, PhaseStep.END_TURN, playerD, playerA, false);
        checkPermanentCount("turn 3 after", 3, PhaseStep.END_TURN, playerD, playerA, cardBear2, 0);
        checkPlayerInGame("turn 4", 4, PhaseStep.PRECOMBAT_MAIN, playerD, playerA, false);
        checkPermanentCount("turn 4", 4, PhaseStep.PRECOMBAT_MAIN, playerD, playerA, cardBear2, 0);

        setStopAt(4, PhaseStep.CLEANUP);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();
    }

    private void prepareAndRunLeaveGameWithEffectTest(Duration duration) {
        // Player order: A -> D -> C -> B
        addCard(Zone.BATTLEFIELD, playerA, cardBear2, 1);
        addCard(Zone.BATTLEFIELD, playerD, cardBear2, 1);
        addCustomCardWithAbility("effect", playerA, new SimpleStaticAbility(new BoostAllEffect(1, 1, duration)));

        // B must checks A for online status
        checkPlayerInGame(duration.toString() + " - turn 1", 1, PhaseStep.PRECOMBAT_MAIN, playerD, playerA, true);
        checkPermanentCount(duration.name() + " - turn 1", 1, PhaseStep.PRECOMBAT_MAIN, playerD, playerA, cardBear2, 1);
        checkPT(duration.name() + " - turn 1", 1, PhaseStep.PRECOMBAT_MAIN, playerD, cardBear2, 3, 3);
        //
        checkPlayerInGame(duration.name() + " - turn 2", 2, PhaseStep.PRECOMBAT_MAIN, playerD, playerA, true);
        checkPermanentCount(duration.name() + " - turn 2", 2, PhaseStep.PRECOMBAT_MAIN, playerD, playerA, cardBear2, 1);
        checkPT(duration.name() + " - turn 2", 2, PhaseStep.PRECOMBAT_MAIN, playerD, cardBear2, 3, 3);
        //
        checkPlayerInGame(duration.name() + " - turn 3 before", 3, PhaseStep.PRECOMBAT_MAIN, playerD, playerA, true);
        checkPermanentCount(duration.name() + " - turn 3 before", 3, PhaseStep.PRECOMBAT_MAIN, playerD, playerA, cardBear2, 1);
        checkPT(duration.name() + " - turn 3 before", 3, PhaseStep.PRECOMBAT_MAIN, playerD, cardBear2, 3, 3);
        //
        concede(3, PhaseStep.POSTCOMBAT_MAIN, playerA);
        //
        checkPlayerInGame(duration.name() + " - turn 3 after", 3, PhaseStep.END_TURN, playerD, playerA, false);
        checkPermanentCount(duration.name() + " - turn 3 after", 3, PhaseStep.END_TURN, playerD, playerA, cardBear2, 0);
        checkPT(duration.name() + " - turn 3 after", 3, PhaseStep.END_TURN, playerD, cardBear2, 2, 2);
        //
        checkPlayerInGame(duration.name() + " - turn 4", 4, PhaseStep.PRECOMBAT_MAIN, playerD, playerA, false);
        checkPermanentCount(duration.name() + " - turn 4", 4, PhaseStep.PRECOMBAT_MAIN, playerD, playerA, cardBear2, 0);
        checkPT(duration.name() + " - turn 4", 4, PhaseStep.PRECOMBAT_MAIN, playerD, cardBear2, 2, 2);

        setStopAt(4, PhaseStep.CLEANUP);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_PlayerLeaveGameWithOwnPermanentAndCustomEffect() {
        prepareAndRunLeaveGameWithEffectTest(Duration.Custom);
    }

    @Test
    public void test_PlayerLeaveGameWithOwnPermanentAndWhileOnBattlefieldEffect() {
        prepareAndRunLeaveGameWithEffectTest(Duration.WhileOnBattlefield);
    }

    @Test
    public void test_PlayerLeaveGameWithOwnPermanentAndEndOfGameEffect() {
        prepareAndRunLeaveGameWithEffectTest(Duration.EndOfGame);
    }

    @Test
    public void test_PlayerLeaveGameWithOwnPermanentAndUntilSourceLeavesBattlefielEffect() {
        prepareAndRunLeaveGameWithEffectTest(Duration.UntilSourceLeavesBattlefield);
    }

    // TODO: add leave tests for end of step
    // TODO: add leave tests for end of turn
    // TODO: add leave tests for end of your turn
}
