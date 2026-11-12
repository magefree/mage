package org.mage.test.multiplayer;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import mage.game.mulligan.MulliganType;
import mage.target.common.TargetOpponent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * 102.1
 * A player is one of the people in the game. The active player is the player whose turn it is. The other players are
 * nonactive players.
 * <p>
 * 800.4a
 * When a player leaves the game, all objects (see rule 109) owned by that player leave the game and any effects which
 * give that player control of any objects or players end. [...] This is not a state-based action. It happens as soon
 * as the player leaves the game. [...]
 * <p>
 * 800.4b
 * If an object would change to the control of a player who has left the game, it doesn't. [...]
 * <p>
 * 115.10a
 * Just because an object or player is being affected by a spell or ability doesn't make that object or player a target
 * of that spell or ability. [...]
 *
 * @author DarioBarrabinoDev
 */
public class ChoosePlayerLeftGameTest extends CardTestMultiPlayerBase {

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
    public void test_ChooseAnOpponent_OpponentLeftThisTurn() {
        // As Black Vise enters, choose an opponent.
        // At the beginning of the chosen player's upkeep, Black Vise deals X damage to that player, where X is the
        // number of cards in their hand minus 4.
        addCard(Zone.HAND, playerA, "Black Vise"); // {1}
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        setLife(playerB, 3);

        // playerB loses the game
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        runCode("playerB lost", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertTrue(info + " - playerB must leave the game", playerB.hasLeft());
        });

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Black Vise");
        setChoice(playerA, "PlayerC"); // opponent for Black Vise

        // non-target choice of an opponent must not offer the player who left
        runCode("possible opponents", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            Ability ability = getPermanent("Black Vise", player).getAbilities(game).stream()
                    .filter(AsEntersBattlefieldAbility.class::isInstance)
                    .findFirst()
                    .orElse(null);
            Assert.assertNotNull(info + " - must have the choose ability", ability);
            Set<UUID> possibleTargets = new TargetOpponent(true).possibleTargets(player.getId(), ability, game);
            Assert.assertEquals(info + " - only opponents still in the game",
                    new HashSet<>(Arrays.asList(playerC.getId(), playerD.getId())), possibleTargets);
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Black Vise", 1);
    }

    @Test
    public void test_OpponentSeparatesPiles_TwoOpponentsLeftThisTurn() {
        // Reveal the top five cards of your library. An opponent separates those cards into two piles. Put one pile
        // into your hand and the other into your graveyard.
        addCard(Zone.HAND, playerA, "Fact or Fiction"); // {3}{U}
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        //
        setLife(playerB, 3);

        // playerD concedes and playerB loses the game, playerC is the only opponent
        concede(1, PhaseStep.UPKEEP, playerD);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        runCode("playerB and playerD left", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertTrue(info + " - playerB must leave the game", playerB.hasLeft());
            Assert.assertTrue(info + " - playerD must leave the game", playerD.hasLeft());
        });

        // no opponent choice: playerC separates the piles
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fact or Fiction");
        setChoice(playerC, TestPlayer.CHOICE_SKIP); // empty first pile, test player puts the first pile into hand

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 1); // turn 1 draw step (no skip in multiplayer)
        assertGraveyardCount(playerA, 1 + 1 + 5); // bolt, Fact or Fiction and the second pile
    }

    @Test
    public void test_PlayerToYourRight_LeftThisTurn() {
        // Hithlain Rope can't be sacrificed.
        // {2}, {T}: Draw a card. The player to your right gains control of Hithlain Rope.
        addCard(Zone.BATTLEFIELD, playerA, "Hithlain Rope");
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        //
        setLife(playerB, 3);

        // playerB (the player to playerA's right) loses the game
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // draw, then playerC is the player to playerA's right
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}: Draw a card");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Hithlain Rope", 0);
        assertPermanentCount(playerC, "Hithlain Rope", 1);
    }
}
