package org.mage.test.cards.single.lea;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import mage.game.mulligan.MulliganType;
import org.junit.Assert;
import org.junit.Test;
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
 * Player
 * One of the people in the game. See rule 102, “Players.”
 *
 * @author DarioBarrabinoDev
 */
public class BalanceMultiplayerTest extends CardTestMultiPlayerBase {

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
    public void test_Balance_PlayerLostThisTurn() {
        // Each player chooses a number of lands they control equal to the number of lands controlled by the player
        // who controls the fewest, then sacrifices the rest. Players discard cards and sacrifice creatures the same way.
        addCard(Zone.HAND, playerA, "Balance"); // {1}{W}
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}
        addCard(Zone.HAND, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2); // bear 2/2
        //
        setLife(playerB, 3);
        //
        addCard(Zone.HAND, playerC, "Island");
        addCard(Zone.BATTLEFIELD, playerC, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerC, "Grizzly Bears"); // bear 2/2
        //
        addCard(Zone.HAND, playerD, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerD, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerD, "Grizzly Bears", 2); // bear 2/2

        // playerB loses the game
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        runCode("playerB lost", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertTrue("playerB must lose", playerB.hasLost());
        });

        // fewest: 2 lands (playerC), 1 creature (playerC), 1 card in hand (playerC)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balance");
        setChoice(playerA, "Plains^Plains"); // lands to keep
        setChoice(playerD, "Forest^Forest"); // lands to keep
        setChoice(playerC, "Swamp^Swamp"); // lands to keep
        setChoice(playerA, "Grizzly Bears"); // creature to keep
        setChoice(playerD, "Grizzly Bears"); // creature to keep
        setChoice(playerC, "Grizzly Bears"); // creature to keep
        setChoice(playerA, "Island"); // card to keep
        setChoice(playerD, "Island"); // card to keep
        setChoice(playerC, "Island"); // card to keep

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Plains", 2);
        assertPermanentCount(playerA, "Mountain", 0);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertHandCount(playerA, "Island", 1);
        //
        assertPermanentCount(playerC, "Swamp", 2);
        assertPermanentCount(playerC, "Grizzly Bears", 1);
        assertHandCount(playerC, "Island", 1);
        //
        assertPermanentCount(playerD, "Forest", 2);
        assertPermanentCount(playerD, "Grizzly Bears", 1);
        assertHandCount(playerD, "Island", 1);
    }

    @Test
    public void test_MagusOfTheBalance_PlayerLostThisTurn() {
        // {4}{W}, {T}, Sacrifice this creature: Each player chooses a number of lands they control equal to the number
        // of lands controlled by the player who controls the fewest, then sacrifices the rest. Players discard cards
        // and sacrifice creatures the same way.
        addCard(Zone.BATTLEFIELD, playerA, "Magus of the Balance");
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2); // bear 2/2
        //
        setLife(playerB, 3);
        //
        addCard(Zone.BATTLEFIELD, playerC, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerC, "Grizzly Bears"); // bear 2/2
        //
        addCard(Zone.BATTLEFIELD, playerD, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerD, "Grizzly Bears", 2); // bear 2/2

        // playerB loses the game
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        runCode("playerB lost", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertTrue("playerB must lose", playerB.hasLost());
        });

        // fewest: 2 lands (playerC), 1 creature (playerC), no cards in hand
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{W}, {T}, Sacrifice");
        setChoice(playerA, "Plains^Plains"); // lands to keep
        setChoice(playerD, "Forest^Forest"); // lands to keep
        setChoice(playerC, "Swamp^Swamp"); // lands to keep
        setChoice(playerA, "Grizzly Bears"); // creature to keep
        setChoice(playerD, "Grizzly Bears"); // creature to keep
        setChoice(playerC, "Grizzly Bears"); // creature to keep

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Magus of the Balance", 1);
        assertPermanentCount(playerA, "Plains", 2);
        assertPermanentCount(playerA, "Mountain", 0);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        //
        assertPermanentCount(playerC, "Swamp", 2);
        assertPermanentCount(playerC, "Grizzly Bears", 1);
        //
        assertPermanentCount(playerD, "Forest", 2);
        assertPermanentCount(playerD, "Grizzly Bears", 1);
    }
}
