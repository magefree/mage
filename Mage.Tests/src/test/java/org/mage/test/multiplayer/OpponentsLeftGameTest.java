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
 * Game::getOpponents excludes players who left the game, see #13289
 * <p>
 * 102.3. ...the player's opponents are all players not on their team.
 * <p>
 * 800.4i ...If an effect requires information from the game about actions players have taken, the effect can find
 * actions that were taken by a player who has left the game.
 * <p>
 * 800.4j If a player leaves the game during their turn, that turn continues to its completion without an active player.
 *
 * @author DarioBarrabinoDev
 */
public class OpponentsLeftGameTest extends CardTestMultiPlayerBase {

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
    public void test_OpponentsCount_ExcludeLeftPlayer() {
        // Equipped creature gets +1/+0 for each opponent you have.
        // Whenever equipped creature is dealt damage, it deals that much damage to any target.
        // Equip {4}
        addCard(Zone.BATTLEFIELD, playerA, "Blazing Sunsteel");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}, 3 damage
        setLife(playerB, 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPT("3 opponents", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 2 + 3, 2);

        // playerB dies, so only 2 opponents left
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        runCode("playerB has lost", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertTrue("playerB has lost", playerB.hasLost());
        });
        checkPT("2 opponents", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 2 + 2, 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Grizzly Bears", 2 + 2, 2);
    }

    @Test
    public void test_ActionsThisTurn_IncludeLeftPlayer() {
        // Create X 1/1 black Faerie Rogue creature tokens with flying, where X is the damage dealt to your opponents this turn.
        // If Notorious Throng's prowl cost was paid, take an extra turn after this one.
        addCard(Zone.HAND, playerA, "Notorious Throng"); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}, 3 damage
        setLife(playerB, 3);

        // playerB dies, but the damage dealt to them this turn still counts
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        runCode("playerB has lost", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertTrue("playerB has lost", playerB.hasLost());
        });
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Notorious Throng");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Faerie Rogue Token", 3);
    }

    @Test
    public void test_OpponentsTurn_LeftActivePlayer() {
        // Flash
        // Whenever you cast a spell during an opponent's turn, put a +1/+1 counter on Brineborn Cutthroat.
        addCard(Zone.BATTLEFIELD, playerA, "Brineborn Cutthroat");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}, 3 damage

        // turn 4 is playerB's turn, it continues without an active player
        concede(4, PhaseStep.UPKEEP, playerB);
        runCode("playerB has lost", 4, PhaseStep.DRAW, playerA, (info, player, game) -> {
            Assert.assertTrue("playerB has lost", playerB.hasLost());
        });
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerC);

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertLife(playerC, 20 - 3);
        assertCounterCount(playerA, "Brineborn Cutthroat", CounterType.P1P1, 1);
    }

    @Test
    public void test_Trigger_OpponentLeftByThatDamage() {
        // Flying
        // Whenever Thieving Magpie deals damage to an opponent, you draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Thieving Magpie"); // 1/3
        setLife(playerB, 1);

        // playerB dies from combat damage, but the trigger is checked before state-based actions
        attack(1, playerA, "Thieving Magpie", playerB);
        runCode("playerB has lost", 1, PhaseStep.END_COMBAT, playerA, (info, player, game) -> {
            Assert.assertTrue("playerB has lost", playerB.hasLost());
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 1 + 1); // draw step + trigger
    }

    @Test
    public void test_EndOfTurnEffect_FromLeftPlayer() {
        // Your opponents can't cast spells this turn.
        addCard(Zone.HAND, playerB, "Silence"); // {W}
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}

        // playerB leaves the game, but the effect keeps working until end of turn
        castSpell(1, PhaseStep.UPKEEP, playerB, "Silence");
        concede(1, PhaseStep.DRAW, playerB);
        runCode("playerB has lost", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertTrue("playerB has lost", playerB.hasLost());
        });
        checkPlayableAbility("can't cast on that turn", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", false);
        checkPlayableAbility("can cast on next turn", 2, PhaseStep.UPKEEP, playerA, "Cast Lightning Bolt", true);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();
    }
}
