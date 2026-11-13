package org.mage.test.cards.single.mid;

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
 * 508.4a
 * If a creature would be put onto the battlefield attacking a certain player, and that player is no longer in the game,
 * the creature is put onto the battlefield but is never considered an attacking creature. [...]
 *
 * @author DarioBarrabinoDev
 */
public class AdelineResplendentCatharTest extends CardTestMultiPlayerBase {

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
    public void test_IgnoreLeavedPlayers() {
        // Whenever you attack, for each opponent, create a 1/1 white Human creature token
        // that's tapped and attacking that player or a planeswalker they control.
        addCard(Zone.BATTLEFIELD, playerA, "Adeline, Resplendent Cathar"); // */4, vigilance
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2, attacker
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

        // tokens for playerD and playerC only
        attack(1, playerA, "Grizzly Bears", playerC);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Human Token", 2);
        assertLife(playerC, 20 - 2 - 1);
        assertLife(playerD, 20 - 1);
    }
}
