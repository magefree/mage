package org.mage.test.cards.single.cmr;

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
 * 702.141a
 * Encore is an activated ability that functions while the card with encore is in a graveyard. “Encore [cost]” means
 * “[Cost], Exile this card from your graveyard: For each opponent, create a token that’s a copy of this card that
 * attacks that opponent this turn if able. The tokens gain haste. Sacrifice them at the beginning of the next end step.
 * Activate only as a sorcery.”
 * <p>
 * Encore ruling (2020-11-10): Opponents who have left the game aren't counted when determining how many tokens to create.
 *
 * @author DarioBarrabinoDev
 */
public class AraumiOfTheDeadTideTest extends CardTestMultiPlayerBase {

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
    public void test_AllOpponentsAlive() {
        // {T}, Exile cards from your graveyard equal to the number of opponents you have: Target creature card in your
        // graveyard gains encore until end of turn. The encore cost is equal to its mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Araumi of the Dead Tide");
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // bear 2/2, {1}{G}
        addCard(Zone.GRAVEYARD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Exile cards");
        addTarget(playerA, "Grizzly Bears"); // gains encore
        setChoice(playerA, "Island^Island^Island"); // cost for 3 opponents
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // encore: one token for each opponent
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Encore");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, "Island", 3);
        assertExileCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 3);
    }

    @Test
    public void test_Cost_OpponentLostThisTurn() {
        // {T}, Exile cards from your graveyard equal to the number of opponents you have: Target creature card in your
        // graveyard gains encore until end of turn. The encore cost is equal to its mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Araumi of the Dead Tide");
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // bear 2/2, {1}{G}
        addCard(Zone.GRAVEYARD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        //
        setLife(playerB, 3);

        // playerB loses the game
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        runCode("playerB lost", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertTrue("playerB must lose", playerB.hasLost());
        });

        // only 2 opponents left
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Exile cards");
        addTarget(playerA, "Grizzly Bears"); // gains encore
        setChoice(playerA, "Island^Island"); // cost for 2 opponents
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPlayableAbility("must have encore", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Encore", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, "Island", 2);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void test_Encore_OpponentLostThisTurn() {
        // {T}, Exile cards from your graveyard equal to the number of opponents you have: Target creature card in your
        // graveyard gains encore until end of turn. The encore cost is equal to its mana cost.
        //
        // Encore {cost} ({cost}, Exile this card from your graveyard: For each opponent, create a token copy that
        // attacks that opponent this turn if able. They gain haste. Sacrifice them at the beginning of the next end
        // step. Activate only as a sorcery.)
        addCard(Zone.BATTLEFIELD, playerA, "Araumi of the Dead Tide");
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // bear 2/2, {1}{G}
        addCard(Zone.GRAVEYARD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        //
        setLife(playerB, 3);

        // all 3 opponents alive
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Exile cards");
        addTarget(playerA, "Grizzly Bears"); // gains encore
        setChoice(playerA, "Island^Island^Island"); // cost for 3 opponents
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // playerB loses the game
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        runCode("playerB lost", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertTrue("playerB must lose", playerB.hasLost());
        });

        // encore: tokens for playerD and playerC only, each one must attack its own opponent
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Encore");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertExileCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 2);
        assertLife(playerC, 20 - 2);
        assertLife(playerD, 20 - 2);
    }
}
