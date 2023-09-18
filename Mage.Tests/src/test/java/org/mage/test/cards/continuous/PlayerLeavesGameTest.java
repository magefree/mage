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
    If the player who left the game had priority at the time they left, priority passes to the next player in turn
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
    }

    private void prepareAndRunLeaveGameWithLongEffectTest(Duration duration) {
        // Player order: A -> D -> C -> B
        addCard(Zone.BATTLEFIELD, playerA, cardBear2, 1);
        addCard(Zone.BATTLEFIELD, playerD, cardBear2, 1);
        addCustomCardWithAbility("effect", playerA, new SimpleStaticAbility(new BoostAllEffect(1, 1, duration)));

        // B must checks A for online status

        // 1
        checkPlayerInGame(duration.toString() + " - turn 1", 1, PhaseStep.PRECOMBAT_MAIN, playerD, playerA, true);
        checkPermanentCount(duration.name() + " - turn 1", 1, PhaseStep.PRECOMBAT_MAIN, playerD, playerA, cardBear2, 1);
        checkPT(duration.name() + " - turn 1", 1, PhaseStep.PRECOMBAT_MAIN, playerD, cardBear2, 3, 3);
        // 2
        checkPlayerInGame(duration.name() + " - turn 2", 2, PhaseStep.PRECOMBAT_MAIN, playerD, playerA, true);
        checkPermanentCount(duration.name() + " - turn 2", 2, PhaseStep.PRECOMBAT_MAIN, playerD, playerA, cardBear2, 1);
        checkPT(duration.name() + " - turn 2", 2, PhaseStep.PRECOMBAT_MAIN, playerD, cardBear2, 3, 3);
        // 3
        checkPlayerInGame(duration.name() + " - turn 3 before", 3, PhaseStep.PRECOMBAT_MAIN, playerD, playerA, true);
        checkPermanentCount(duration.name() + " - turn 3 before", 3, PhaseStep.PRECOMBAT_MAIN, playerD, playerA, cardBear2, 1);
        checkPT(duration.name() + " - turn 3 before", 3, PhaseStep.PRECOMBAT_MAIN, playerD, cardBear2, 3, 3);
        //
        concede(3, PhaseStep.POSTCOMBAT_MAIN, playerA);
        //
        checkPlayerInGame(duration.name() + " - turn 3 after", 3, PhaseStep.END_TURN, playerD, playerA, false);
        checkPermanentCount(duration.name() + " - turn 3 after", 3, PhaseStep.END_TURN, playerD, playerA, cardBear2, 0);
        checkPT(duration.name() + " - turn 3 after", 3, PhaseStep.END_TURN, playerD, cardBear2, 2, 2);
        // 4
        checkPlayerInGame(duration.name() + " - turn 4", 4, PhaseStep.PRECOMBAT_MAIN, playerD, playerA, false);
        checkPermanentCount(duration.name() + " - turn 4", 4, PhaseStep.PRECOMBAT_MAIN, playerD, playerA, cardBear2, 0);
        checkPT(duration.name() + " - turn 4", 4, PhaseStep.PRECOMBAT_MAIN, playerD, cardBear2, 2, 2);

        setStopAt(4, PhaseStep.CLEANUP);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_PlayerLeaveGameWithOwnPermanentAndCustomEffect() {
        // Using Custom duration without implementing specific duration rules makes no sense
        // This conflicts rule 800.4a (only any effects which give that player control of any objects or players end.)
        // See PlayerLeftGameRangeAllTest.TestContinuousEffectStaysAfterCreatingPlayerLeft as example why custom effects may not end, if the source permanent of the effect leaves the game
        
        // prepareAndRunLeaveGameWithLongEffectTest(Duration.Custom);
    }

    @Test
    public void test_PlayerLeaveGameWithOwnPermanentAndWhileOnBattlefieldEffect() {
        prepareAndRunLeaveGameWithLongEffectTest(Duration.WhileOnBattlefield);
    }

    @Test
    public void test_PlayerLeaveGameWithOwnPermanentAndEndOfGameEffect() {
        prepareAndRunLeaveGameWithLongEffectTest(Duration.EndOfGame);
    }

    @Test
    public void test_PlayerLeaveGameWithOwnPermanentAndUntilSourceLeavesBattlefielEffect() {
        prepareAndRunLeaveGameWithLongEffectTest(Duration.UntilSourceLeavesBattlefield);
    }

    @Test
    public void test_EndOfTurnMultiLeave() {
        // Player order: A -> D -> C -> B
        addCustomCardWithAbility("boost", playerA, new SimpleStaticAbility(Zone.ALL, new BoostAllEffect(1, 1, Duration.EndOfTurn)));
        addCard(Zone.BATTLEFIELD, playerA, cardBear2, 1);
        addCard(Zone.BATTLEFIELD, playerB, cardBear2, 1);
        addCard(Zone.BATTLEFIELD, playerC, cardBear2, 1);
        addCard(Zone.BATTLEFIELD, playerD, cardBear2, 1);

        // player A leaves game on turn 1 - postcombat
        // end of turn effect must continue until end of turn
        concede(1, PhaseStep.POSTCOMBAT_MAIN, playerA);
        checkPT("turn 1", 1, PhaseStep.PRECOMBAT_MAIN, playerD, cardBear2, 3, 3);
        checkPT("turn 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerD, cardBear2, 3, 3);
        checkPT("turn 1", 1, PhaseStep.END_TURN, playerD, cardBear2, 3, 3);
        checkPT("turn 2", 2, PhaseStep.PRECOMBAT_MAIN, playerD, cardBear2, 2, 2);

        setStopAt(2, PhaseStep.CLEANUP);
        setStrictChooseMode(true);
        execute();
    }


    private void prepareAndRunUntilYourTurnLeaveTest(Duration duration) {
        // Player order: A -> D -> C -> B
        addCustomCardWithAbility("boost", playerA, new SimpleStaticAbility(Zone.ALL, new BoostAllEffect(1, 1, duration)));
        addCard(Zone.BATTLEFIELD, playerA, cardBear2, 1);
        addCard(Zone.BATTLEFIELD, playerB, cardBear2, 1);
        addCard(Zone.BATTLEFIELD, playerC, cardBear2, 1);
        addCard(Zone.BATTLEFIELD, playerD, cardBear2, 1);

        // 800.4k  When a player leaves the game, any continuous effects with durations that last until
        // that player’s next turn or until a specific point in that turn will last until that turn would have begun.
        // They neither expire immediately nor last indefinitely.

        // player A leaves game on turn 1 - postcombat
        // until your next turn effect must continue until START of your possible next turn
        concede(1, PhaseStep.POSTCOMBAT_MAIN, playerA);
        checkPT(duration.name() + " - turn 1 pre", 1, PhaseStep.PRECOMBAT_MAIN, playerD, cardBear2, 3, 3);
        checkPermanentCount(duration.name() + " - perm A must exists before leave", 1, PhaseStep.PRECOMBAT_MAIN, playerD, playerA, "boost", 1);
        checkPT(duration.name() + " - turn 1 post", 1, PhaseStep.POSTCOMBAT_MAIN, playerD, cardBear2, 3, 3);
        checkPermanentCount(duration.name() + " - perm A must removed after leave", 1, PhaseStep.POSTCOMBAT_MAIN, playerD, playerA, "boost", 0);
        checkPT(duration.name() + " - turn 1 end", 1, PhaseStep.END_TURN, playerD, cardBear2, 3, 3);
        checkPT(duration.name() + " - turn 2", 2, PhaseStep.PRECOMBAT_MAIN, playerD, cardBear2, 3, 3);
        checkPT(duration.name() + " - turn 3", 3, PhaseStep.PRECOMBAT_MAIN, playerD, cardBear2, 3, 3);
        checkPT(duration.name() + " - turn 4", 4, PhaseStep.PRECOMBAT_MAIN, playerD, cardBear2, 3, 3);
        checkPT(duration.name() + " - turn 5 (possible A)", 5, PhaseStep.PRECOMBAT_MAIN, playerD, cardBear2, 2, 2);

        setStopAt(5, PhaseStep.CLEANUP);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_UntilYourNextTurnMultiLeave() {
        prepareAndRunUntilYourTurnLeaveTest(Duration.UntilYourNextTurn);
    }

    @Test
    public void test_UntilEndOfYourNextTurnMultiLeave() {
        prepareAndRunUntilYourTurnLeaveTest(Duration.UntilEndOfYourNextTurn);
    }
}
