package org.mage.test.cards.single.c19;

import java.io.FileNotFoundException;
import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import mage.game.mulligan.MulliganType;
import mage.players.Player;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

/**
 *
 * @author azra1l <algee2005@gmail.com>
 */
public class AeonEngineTest extends CardTestMultiPlayerBase {
    @Override
    protected Game createNewGameAndPlayers() throws GameException {
        Game game = new FreeForAll(MultiplayerAttackOption.LEFT, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 20);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, playerA, "PlayerA");
        playerB = createPlayer(game, playerB, "PlayerB");
        playerC = createPlayer(game, playerC, "PlayerC");
        playerD = createPlayer(game, playerD, "PlayerD");
        return game;
    }
    
    @Test
    public void testEnterTappedNormalTurnOrder() {
        // Aeon Engine - Artefact - {5}
        // Aeon Engine enters the battlefield tapped.
        // {T}, Exile Aeon Engine: Reverse the gameâs turn order. (For example, if play had proceeded clockwise around the table, it now goes counterclockwise.)
        addCard(Zone.HAND, playerA, "Aeon Engine", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerD, "Agonizing Syphon", 1);
        addCard(Zone.BATTLEFIELD, playerD, "Swamp", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aeon Engine");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Agonizing Syphon", playerA);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
        
        //check if aeon engine is tapped
        assertTapped("Aeon Engine", true);
    
        //check if turn was passed to correct player - should be D
        assertActivePlayer(playerD);
        assertLife(playerA, 17);
        assertLife(playerD, 23);
    }

    @Test
    public void testExileCostReversedTurnOrder() throws GameException, FileNotFoundException {
        // Aeon Engine - Artefact - {5}
        // Aeon Engine enters the battlefield tapped.
        // {T}, Exile Aeon Engine: Reverse the gameâs turn order. (For example, if play had proceeded clockwise around the table, it now goes counterclockwise.)
        addCard(Zone.HAND, playerB, "Agonizing Syphon", 3);
        addCard(Zone.HAND, playerA, "Aeon Engine", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerB, "Agonizing Syphon", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 5);
        addCard(Zone.HAND, playerC, "Agonizing Syphon", 3);
        addCard(Zone.BATTLEFIELD, playerC, "Swamp", 5);
        addCard(Zone.HAND, playerD, "Agonizing Syphon", 3);
        addCard(Zone.BATTLEFIELD, playerD, "Swamp", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aeon Engine");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Agonizing Syphon", playerA);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerC, "Agonizing Syphon", playerA);
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Agonizing Syphon", playerA);
        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Exile {this}:");
        castSpell(6, PhaseStep.PRECOMBAT_MAIN, playerB, "Agonizing Syphon", playerA);

        setStrictChooseMode(true);
        setStopAt(6, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
        
        //check if aeon engine has been exiled
        assertExileCount(playerA, "Aeon Engine", 1);
                
        //check if turn was passed to correct player each turn - should be B
        assertActivePlayer(playerB);
        assertLife(playerA, 8);
        assertLife(playerB, 26);
        assertLife(playerC, 23);
        assertLife(playerD, 23);
        assertGraveyardCount(playerB, "Agonizing Syphon", 2);
        assertGraveyardCount(playerC, "Agonizing Syphon", 1);
        assertGraveyardCount(playerD, "Agonizing Syphon", 1);
    }
    
    @Test
    public void testExileCostReversedTurnOrderDouble() throws GameException, FileNotFoundException {
        // Aeon Engine - Artefact - {5}
        // Aeon Engine enters the battlefield tapped.
        // {T}, Exile Aeon Engine: Reverse the gameâs turn order. (For example, if play had proceeded clockwise around the table, it now goes counterclockwise.)
        addCard(Zone.HAND, playerA, "Agonizing Syphon", 3);
        addCard(Zone.HAND, playerA, "Aeon Engine", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerB, "Agonizing Syphon", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 5);
        addCard(Zone.HAND, playerC, "Agonizing Syphon", 3);
        addCard(Zone.BATTLEFIELD, playerC, "Swamp", 5);
        addCard(Zone.HAND, playerD, "Agonizing Syphon", 3);
        addCard(Zone.HAND, playerD, "Aeon Engine", 1);
        addCard(Zone.BATTLEFIELD, playerD, "Swamp", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aeon Engine");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Aeon Engine");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerC, "Agonizing Syphon", playerA);
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Agonizing Syphon", playerA);
        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Exile {this}:");
        castSpell(6, PhaseStep.PRECOMBAT_MAIN, playerB, "Agonizing Syphon", playerA);
        castSpell(7, PhaseStep.PRECOMBAT_MAIN, playerC, "Agonizing Syphon", playerA);
        activateAbility(8, PhaseStep.PRECOMBAT_MAIN, playerD, "{T}, Exile {this}:");
        castSpell(9, PhaseStep.PRECOMBAT_MAIN, playerC, "Agonizing Syphon", playerA);
        
        setStrictChooseMode(true);
        setStopAt(9, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
        
        //check if aeon engine's have been exiled
        assertExileCount(playerA, "Aeon Engine", 1);
        assertExileCount(playerD, "Aeon Engine", 1);
                
        //check if turn was passed to correct player each turn - should be C
        assertActivePlayer(playerC);
        assertLife(playerA, 5);
        assertLife(playerB, 26);
        assertLife(playerC, 29);
        assertLife(playerD, 20);
        assertGraveyardCount(playerB, "Agonizing Syphon", 2);
        assertGraveyardCount(playerC, "Agonizing Syphon", 3);
    }
}