package org.mage.test.multiplayer;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import mage.game.mulligan.MulliganType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

/**
 * Conditions and values that check opponents or players must ignore players who left the game this turn
 * (one time checks like intervening if, cast restrictions, cost reductions and one shot effects), see #13289
 * <p>
 * 102.3
 * In a multiplayer game between teams, a player’s teammates are the other players on their team, and the player’s
 * opponents are all players not on their team.
 * <p>
 * 800.4a
 * When a player leaves the game, all objects (see rule 109) owned by that player leave the game and any effects which
 * give that player control of any objects or players end. Then, if that player controlled any objects on the stack not
 * represented by cards, those objects cease to exist. Then, if there are any objects still controlled by that player,
 * those objects are exiled. This is not a state-based action. It happens as soon as the player leaves the game. If the
 * player who left the game had priority at the time they left, priority passes to the next player in turn order who’s
 * still in the game.
 * <p>
 * 603.4
 * A triggered ability may read “When/Whenever/At [trigger event], if [condition], [effect].” When the trigger event
 * occurs, the ability checks whether the stated condition is true. The ability triggers only if it is; otherwise it
 * does nothing. If the ability triggers, it checks the stated condition again as it resolves.
 *
 * @author DarioBarrabinoDev
 */
public class PlayerLeftGameConditionsTest extends CardTestMultiPlayerBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException {
        // Player order: A -> D -> C -> B
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 20, 7);
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        playerD = createPlayer(game, "PlayerD");
        return game;
    }

    @Test
    public void test_OpponentHasNoCardsInHand_AvatarOfWill() {
        // If an opponent has no cards in hand, this spell costs {6} less to cast.
        addCard(Zone.HAND, playerA, "Avatar of Will"); // {6}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        //
        addCard(Zone.HAND, playerB, "Grizzly Bears"); // leaves the game with the player
        addCard(Zone.HAND, playerC, "Grizzly Bears");
        addCard(Zone.HAND, playerD, "Grizzly Bears");
        setLife(playerB, 3);

        // all opponents have cards in hand
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Avatar of Will", false);

        // playerB loses the game, its hand is empty now
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        runCode("playerB lost", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertTrue("playerB must lose", playerB.hasLost());
        });

        // playerB is not an opponent anymore, so no cost reduction
        checkPlayableAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Avatar of Will", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_OpponentHasMoreLife_LinvalaThePreserver() {
        // When Linvala enters, if an opponent has more life than you, you gain 5 life.
        // When Linvala enters, if an opponent controls more creatures than you, create a 3/3 white Angel creature
        // token with flying.
        addCard(Zone.HAND, playerA, "Linvala, the Preserver"); // {4}{W}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        setLife(playerA, 10);
        //
        setLife(playerC, 5);
        setLife(playerD, 5);

        // playerB leaves the game with more life than playerA
        concede(1, PhaseStep.PRECOMBAT_MAIN, playerB);
        runCode("playerB lost", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertTrue("playerB must lose", playerB.hasLost());
        });

        // no opponent in the game has more life, so no life gain
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Linvala, the Preserver");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Linvala, the Preserver", 1);
        assertLife(playerA, 10);
    }

    @Test
    public void test_PlayerWithTheMostLife_Dethrone() {
        // Dethrone (Whenever this creature attacks the player with the most life or tied for most life, put a +1/+1
        // counter on it.)
        addCard(Zone.BATTLEFIELD, playerA, "Marchesa's Emissary"); // 2/2
        setLife(playerA, 5);
        //
        setLife(playerC, 10);
        setLife(playerD, 15);

        // playerB leaves the game with the most life
        concede(1, PhaseStep.PRECOMBAT_MAIN, playerB);
        runCode("playerB lost", 1, PhaseStep.BEGIN_COMBAT, playerA, (info, player, game) -> {
            Assert.assertTrue("playerB must lose", playerB.hasLost());
        });

        // playerD has the most life among players in the game
        attack(1, playerA, "Marchesa's Emissary", playerD);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Marchesa's Emissary", CounterType.P1P1, 1);
        assertLife(playerD, 15 - 3);
    }

    @Test
    public void test_ControlsFewerThanEachOpponent_EvenTheOdds() {
        // Cast this spell only if you control fewer creatures than each opponent.
        // Create three 1/1 white Soldier creature tokens.
        addCard(Zone.HAND, playerA, "Even the Odds"); // {2}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        //
        addCard(Zone.BATTLEFIELD, playerC, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerD, "Grizzly Bears");
        setLife(playerB, 3);

        // playerB controls no creatures
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Even the Odds", false);

        // playerB loses the game
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        runCode("playerB lost", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertTrue("playerB must lose", playerB.hasLost());
        });

        // only playerC and playerD are opponents now
        checkPlayableAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Even the Odds", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Even the Odds");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Soldier Token", 3);
    }

    @Test
    public void test_RepayInKind() {
        // Each player's life total becomes the lowest life total among all players.
        addCard(Zone.HAND, playerA, "Repay in Kind"); // {5}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        //
        setLife(playerB, 3);
        setLife(playerC, 10);
        setLife(playerD, 15);

        // playerB loses the game with 0 life
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        runCode("playerB lost", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertTrue("playerB must lose", playerB.hasLost());
        });

        // lowest life among players in the game is 10
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Repay in Kind");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 10);
        assertLife(playerC, 10);
        assertLife(playerD, 10);
    }

    @Test
    public void test_ScourgeOfTheSkyclaves() {
        // Kicker {4}{B}
        // When you cast this spell, if it was kicked, each player loses half their life, rounded up.
        // Scourge of the Skyclaves's power and toughness are each equal to 20 minus the highest life total among players.
        addCard(Zone.HAND, playerA, "Scourge of the Skyclaves"); // {1}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2 + 5); // can pay kicker
        setLife(playerA, 15);
        //
        setLife(playerC, 10);
        setLife(playerD, 12);

        // playerB leaves the game with 20 life
        concede(1, PhaseStep.PRECOMBAT_MAIN, playerB);
        runCode("playerB lost", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertTrue("playerB must lose", playerB.hasLost());
        });

        // highest life among players in the game is 15
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Scourge of the Skyclaves");
        setChoice(playerA, false); // no kicker

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Scourge of the Skyclaves", 20 - 15, 20 - 15);
    }

    @Test
    public void test_BreenaTheDemagogue() {
        // Whenever a player attacks one of your opponents, if that opponent has more life than another of your
        // opponents, that attacking player draws a card and you put two +1/+1 counters on a creature you control.
        addCard(Zone.BATTLEFIELD, playerA, "Breena, the Demagogue"); // 1/3
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        //
        setLife(playerB, 3);

        // playerB loses the game with 0 life
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        runCode("playerB lost", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertTrue("playerB must lose", playerB.hasLost());
        });

        // playerC and playerD have the same life, so no trigger
        attack(1, playerA, "Grizzly Bears", playerC);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 0);
        assertCounterCount(playerA, "Breena, the Demagogue", CounterType.P1P1, 0);
        assertHandCount(playerA, 1); // turn 1 draw step only (no skip in multiplayer)
        assertLife(playerC, 20 - 2);
    }
}
