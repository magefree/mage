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
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

/**
 * 800.4a
 * When a player leaves the game, all objects (see rule 109) owned by that player leave the game and any effects which
 * give that player control of any objects or players end. Then, if that player controlled any objects on the stack not
 * represented by cards, those objects cease to exist. Then, if there are any objects still controlled by that player,
 * those objects are exiled. This is not a state-based action. It happens as soon as the player leaves the game. If the
 * player who left the game had priority at the time they left, priority passes to the next player in turn order who’s
 * still in the game.
 * <p>
 * 800.4b
 * If an object would change to the control of a player who has left the game, it doesn’t. If a token would be created
 * under the control of a player who has left the game, no token is created. If an object would be put onto the
 * battlefield or onto the stack under the control of a player who has left the game, that object remains in its
 * current zone. If a player would be controlled by a player who has left the game, they aren’t.
 *
 * @author DarioBarrabinoDev
 */
public class PlayerLeftGameTokensTest extends CardTestMultiPlayerBase {

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

    private void killPlayerB() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        setLife(playerB, 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        runCode("playerB lost", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertTrue("playerB must lose", playerB.hasLost());
        });
    }

    @Test
    public void test_EachOpponentCreates_OpponentLostThisTurn() {
        // When this creature enters, each opponent creates a 1/1 white Human creature token.
        // Whenever a creature an opponent controls dies, put a +1/+1 counter on this creature.
        addCard(Zone.HAND, playerA, "Slaughter Specialist"); // {1}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        killPlayerB();

        // each opponent creates a token, but playerB already left the game
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Slaughter Specialist");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Human Token", 0);
        assertPermanentCount(playerB, "Human Token", 0);
        assertPermanentCount(playerC, "Human Token", 1);
        assertPermanentCount(playerD, "Human Token", 1);
    }

    @Test
    public void test_EachPlayerCreates_OpponentLostThisTurn() {
        // When this Class enters, each player creates a Food token.
        addCard(Zone.HAND, playerA, "Party Dude"); // {G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        //
        killPlayerB();

        // each player creates a token, but playerB already left the game
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Party Dude");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Food Token", 1);
        assertPermanentCount(playerB, "Food Token", 0);
        assertPermanentCount(playerC, "Food Token", 1);
        assertPermanentCount(playerD, "Food Token", 1);
    }

    @Test
    public void test_LifeOfTheParty_OpponentLostThisTurn() {
        // When this creature enters, if it's not a token, each opponent creates a token that's a copy of it.
        // The tokens are goaded for the rest of the game.
        addCard(Zone.HAND, playerA, "Life of the Party"); // {3}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        //
        killPlayerB();

        // each opponent creates a copy, but playerB already left the game
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Life of the Party");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Life of the Party", 1);
        assertPermanentCount(playerB, "Life of the Party", 0);
        assertPermanentCount(playerC, "Life of the Party", 1);
        assertPermanentCount(playerD, "Life of the Party", 1);
    }

    @Test
    public void test_EiganjoUprising_OpponentLostThisTurn() {
        // Create X 2/2 white Samurai creature tokens with vigilance. They gain menace and haste until end of turn.
        // Each opponent creates X minus one 2/2 white Samurai creature tokens with vigilance.
        addCard(Zone.HAND, playerA, "Eiganjo Uprising"); // {X}{R}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 5);
        //
        killPlayerB();

        // X = 3: playerA creates 3 tokens, each opponent still in the game creates 2 tokens
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Eiganjo Uprising");
        setChoice(playerA, "X=3");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Samurai Token", 3);
        assertPermanentCount(playerB, "Samurai Token", 0);
        assertPermanentCount(playerC, "Samurai Token", 2);
        assertPermanentCount(playerD, "Samurai Token", 2);
    }

    @Test
    public void test_ErestorOfTheCouncil_OpponentLostAfterVoting() {
        // Whenever players finish voting, each opponent who voted for a choice you voted for creates a Treasure token.
        // You scry X, where X is the number of opponents who voted for a choice you didn't vote for. Draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Erestor of the Council");
        //
        // Council's dilemma - When Lieutenants of the Guard enters the battlefield, starting with you, each player
        // votes for strength or numbers. Put a +1/+1 counter on Lieutenants of the Guard for each strength vote and
        // put a 1/1 white Soldier creature token onto the battlefield for each numbers vote.
        addCard(Zone.HAND, playerA, "Lieutenants of the Guard"); // {4}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        //
        // {T}: Prodigal Pyromancer deals 1 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Prodigal Pyromancer");
        setLife(playerB, 1);

        // all players vote for strength
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lieutenants of the Guard");
        setChoice(playerA, "Yes"); // strength
        setChoice(playerD, "Yes"); // strength
        setChoice(playerC, "Yes"); // strength
        setChoice(playerB, "Yes"); // strength

        // playerB loses the game before Erestor's trigger resolves
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals 1 damage", TestPlayer.NO_TARGET, "each opponent who voted");
        addTarget(playerA, playerB); // damage to playerB
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        runCode("playerB lost", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertTrue("playerB must lose", playerB.hasLost());
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, "Lieutenants of the Guard", CounterType.P1P1, 4);
        assertPermanentCount(playerA, "Treasure Token", 0);
        assertPermanentCount(playerB, "Treasure Token", 0);
        assertPermanentCount(playerC, "Treasure Token", 1);
        assertPermanentCount(playerD, "Treasure Token", 1);
    }
}
