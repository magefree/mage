package org.mage.test.cards.single.who;

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
 * 102.1
 * A player is one of the people in the game. The active player is the player whose turn it is. The other players are
 * nonactive players.
 * <p>
 * 701.55d
 * If more than one player is instructed to face a villainous choice, the entire process described in rule 701.55a is
 * performed for each of those players one at a time in APNAP order. This is an exception to rule 608.2e.
 *
 * @author DarioBarrabinoDev
 */
public class TheDalekEmperorTest extends CardTestMultiPlayerBase {

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
    public void test_OpponentLostThisTurn() {
        // At the beginning of combat on your turn, each opponent faces a villainous choice -- That player sacrifices
        // a creature of their choice, or you create a 3/3 black Dalek artifact creature token with menace.
        addCard(Zone.BATTLEFIELD, playerA, "The Dalek Emperor");
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        //
        addCard(Zone.BATTLEFIELD, playerC, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerD, "Silvercoat Lion"); // lion 2/2
        //
        setLife(playerB, 3);

        // playerB loses the game
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        runCode("playerB lost", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertTrue("playerB must lose", playerB.hasLost());
        });

        // beginning of combat: only playerD and playerC face the choice, playerB must not
        setChoice(playerD, false); // playerA creates a Dalek
        setChoice(playerC, true); // playerC sacrifices a creature
        setChoice(playerC, "Grizzly Bears"); // creature to sacrifice

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();

        assertPermanentCount(playerA, "Dalek Token", 1);
        assertGraveyardCount(playerC, "Grizzly Bears", 1);
        assertPermanentCount(playerD, "Silvercoat Lion", 1);
    }
}
