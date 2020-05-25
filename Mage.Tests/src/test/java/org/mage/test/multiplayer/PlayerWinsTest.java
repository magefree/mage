/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.multiplayer;

import java.io.FileNotFoundException;
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
 * @author LevelX2
 */
public class PlayerWinsTest extends CardTestMultiPlayerBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ONE, MulliganType.GAME_DEFAULT.getMulligan(0), 40);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, playerA, "PlayerA");
        playerB = createPlayer(game, playerB, "PlayerB");
        playerC = createPlayer(game, playerC, "PlayerC");
        playerD = createPlayer(game, playerD, "PlayerD");
        return game;
    }

    /**
     * Tests multiplayer effects Player order: A -> D -> C -> B
     */
    @Test
    public void ApproachOfTheSecondSunTest() {

        // If this spell was cast from your hand and you've cast another spell named Approach of the Second Sun this game,
        // you win the game. Otherwise, put Approach of the Second Sun into its owner's library seventh from the top and you gain 7 life. 
        addCard(Zone.HAND, playerA, "Approach of the Second Sun", 2); // Sorcery {6}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun");
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun");

        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 47);
        assertLife(playerC, 40);
        Assert.assertTrue("Player D has lost the game", !playerD.isInGame());
        Assert.assertTrue("Player B has lost the game", !playerB.isInGame());
        Assert.assertTrue("Player C is in the game", playerC.isInGame());
        Assert.assertTrue("Player A is in the game", playerA.isInGame());

    }

}
