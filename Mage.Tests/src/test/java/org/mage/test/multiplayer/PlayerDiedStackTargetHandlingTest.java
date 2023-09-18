package org.mage.test.multiplayer;

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

import java.io.FileNotFoundException;

/**
 * @author LevelX2
 */
public class PlayerDiedStackTargetHandlingTest extends CardTestMultiPlayerBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        // Start Life = 2
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ONE, MulliganType.GAME_DEFAULT.getMulligan(0), 3);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        playerD = createPlayer(game, "PlayerD");
        return game;
    }

    /**
     * If a spell or ability on the stack is targeting a player (or an object
     * controlled by that player), and that player leaves the game, then the
     * target becomes illegal and the spell or ability should be countered upon
     * resolution (assuming that player or object is the only target of the
     * spell). However, Xmage regularly continues resolving spells and abilities
     * targeting players after that player concedes, which is a problem in
     * multiplayer.
     */
    @Test
    public void TestDeadPlayerIsNoLongerValidTarget() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        // Lightning Helix deals 3 damage to any target and you gain 3 life.
        addCard(Zone.HAND, playerA, "Lightning Helix", 2); // Instant {R}{W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Helix", playerD);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Helix", playerD);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Lightning Helix", 2);
        Assert.assertTrue("Active player has to be player C", currentGame.getActivePlayerId().equals(playerC.getId()));

        assertLife(playerA, 6);

    }

    /**
     * Player A casts Tendrils of Agony with a storm count of 10 targeting
     * Player B, who is at 10 life. After the first five copies of Tendrils
     * resolve, player A will have gained 10 life and Player B will have lost 10
     * life, so player B should die as a state-based action before the remaining
     * six copies resolve. In similar circumstances, Xmage has continued
     * resolving the additional six copies, putting player B at -12 life and
     * letting player A gain an additional 12 life inappropriately
     */
    @Test
    public void TestDeadPlayerIsNoLongerValidTarget2() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 2);
        // Target player loses 2 life and you gain 2 life.
        // Storm (When you cast this spell, copy it for each spell cast before it this turn. You may choose new targets for the copies.)
        addCard(Zone.HAND, playerA, "Tendrils of Agony", 1); // Sorcery {2}{B}{B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tendrils of Agony", playerD);
        addTarget(playerA, playerD);
        addTarget(playerA, playerD);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 2);
        assertGraveyardCount(playerA, "Tendrils of Agony", 1);
        Assert.assertTrue("Active player has to be player C", currentGame.getActivePlayerId().equals(playerC.getId()));

        assertLife(playerA, 7);
    }
}
