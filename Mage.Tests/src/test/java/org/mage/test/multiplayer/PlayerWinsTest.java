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
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        playerD = createPlayer(game, "PlayerD");
        return game;
    }

    /**
     * Tests multiplayer effects Player order: A -> D -> C -> B
     */
    
    /**
     * Test that players out of range do not lose the game if effect from Approach of the Seconnd Sun takes effect.     
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
        Assert.assertTrue("Player D still alive but should have lost the game", !playerD.isInGame());
        Assert.assertTrue("Player B still alive but should have lost the game", !playerB.isInGame());
        Assert.assertTrue("Player C is in the game", playerC.isInGame());
        Assert.assertTrue("Player A is in the game", playerA.isInGame());

    }
    
    /**
     * Test that players out of range do not lose the game if effect from Laboratory Maniac takes effect.     
     */
    @Test
    public void LaboratoryManiacWinsTest() {       
        // If you would draw a card while your library has no cards in it, you win the game instead.        
        addCard(Zone.HAND, playerA, "Laboratory Maniac", 1); // Creature {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Laboratory Maniac");        
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Mountain");
        
        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Laboratory Maniac", 1);
        assertHandCount(playerA, "Mountain", 1);
        assertLibraryCount(playerA, 0);
        Assert.assertTrue("Player D still alive but should have lost the game", !playerD.isInGame());
        Assert.assertTrue("Player B still alive but should have lost the game", !playerB.isInGame());
        Assert.assertTrue("Player C should be in the game but has lost", playerC.isInGame());
        Assert.assertTrue("Player A should be in the game but has lost", playerA.isInGame());
    }
    
    /**
     * Test that player can't win while Platinium Angel ist in range.     
     */
    @Test
    public void LaboratoryManiacAndPlatinumAngelInRangeTest() {       
        // If you would draw a card while your library has no cards in it, you win the game instead.        
        addCard(Zone.HAND, playerA, "Laboratory Maniac", 1); // Creature {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        removeAllCardsFromLibrary(playerA);
        
        // You can't lose the game and your opponents can't win the game.
        addCard(Zone.HAND, playerD, "Platinum Angel", 1); // Creature {2}{U}
        addCard(Zone.BATTLEFIELD, playerD, "Island", 7);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Laboratory Maniac");        
        addCard(Zone.LIBRARY, playerA, "Mountain");
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Platinum Angel");        

        setStopAt(9, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Laboratory Maniac", 1);
        assertHandCount(playerA, "Mountain", 1);
        assertLibraryCount(playerA, 0);
        
        assertPermanentCount(playerD, "Platinum Angel", 1);
        
        Assert.assertTrue("Player D should be in the game but has lost", playerD.isInGame());
        Assert.assertTrue("Player B should be in the game but has lost", playerB.isInGame());
        Assert.assertTrue("Player C should be in the game but has lost", playerC.isInGame());
        Assert.assertTrue("Player A should be in the game but has lost", playerA.isInGame());
    }
    
        /**
     * Test that player can't win while Platinium Angel ist in range.     
     */
    @Test
    public void LaboratoryManiacAndPlatinumAngelFirstOutOfRangeTest() {       
        // If you would draw a card while your library has no cards in it, you win the game instead.        
        addCard(Zone.HAND, playerA, "Laboratory Maniac", 1); // Creature {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        removeAllCardsFromLibrary(playerA);
        
        // You can't lose the game and your opponents can't win the game.
        addCard(Zone.HAND, playerC, "Platinum Angel", 1); // Creature {2}{U}
        addCard(Zone.BATTLEFIELD, playerC, "Island", 7);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Laboratory Maniac");        
        addCard(Zone.LIBRARY, playerA, "Mountain");
        
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerC, "Platinum Angel");        

        setStopAt(9, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Laboratory Maniac", 1);
        assertHandCount(playerA, "Mountain", 1);
        assertLibraryCount(playerA, 0);
        
        assertPermanentCount(playerC, "Platinum Angel", 1);
 
        Assert.assertTrue("Player D still alive but should have lost the gamet", !playerD.isInGame());
        Assert.assertTrue("Player B still alive but should have lost the game", !playerB.isInGame());
        Assert.assertTrue("Player C should be in the game but has lost", playerC.isInGame());
        Assert.assertTrue("Player A should be in the game but has lost", playerA.isInGame());
    }
}
