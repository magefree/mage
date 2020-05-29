/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.abilities.keywords;

import java.io.FileNotFoundException;
import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import mage.game.mulligan.MulliganType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

/**
 *
 * @author LevelX2
 */
public class GoadTest extends CardTestMultiPlayerBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 40);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, playerA, "PlayerA");
        playerB = createPlayer(game, playerB, "PlayerB");
        playerC = createPlayer(game, playerC, "PlayerC");
        playerD = createPlayer(game, playerD, "PlayerD");
        return game;
    }

    /**
     * In a game of commander, my opponent gained control of Marisi, Breaker of
     * Coils (until end of turn) and did combat damage to another player. This
     * caused the creatures damaged by Marisi's controller to be goaded.
     * However, when the goaded creatures went to attack, they could not attack
     * me but could attack the (former) controller of Marisi.
     */
    @Test
    public void goadWithNotOwnedCreatureTest() {
        // Your opponents can't cast spells during combat.
        // Whenever a creature you control deals combat damage to a player, goad each creature that player controls
        // (Until your next turn, that creature attacks each combat if able and attacks a player other than you if able.)
        addCard(Zone.BATTLEFIELD, playerA, "Marisi, Breaker of the Coil", 1); // Creature 5/4

        // Untap target creature an opponent controls and gain control of it until end of turn. 
        // That creature gains haste until end of turn. 
        // When you lose control of the creature, tap it.
        addCard(Zone.HAND, playerD, "Ray of Command"); // Instant {3}{U}
        addCard(Zone.BATTLEFIELD, playerD, "Island", 4);
        
        addCard(Zone.BATTLEFIELD, playerC, "Silvercoat Lion", 3); // Creature 2/2
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Ray of Command", "Marisi, Breaker of the Coil");
        
        attack(2, playerD, "Marisi, Breaker of the Coil", playerC);
        
        attack(3, playerC, "Silvercoat Lion", playerA);
        attack(3, playerC, "Silvercoat Lion", playerB);
        attack(3, playerC, "Silvercoat Lion", playerD);
        
        setStopAt(4, PhaseStep.BEGIN_COMBAT);
        execute();

        
        assertGraveyardCount(playerD, "Ray of Command", 1);
        assertPermanentCount(playerA, "Marisi, Breaker of the Coil", 1);
                
        assertLife(playerC, 35);

        assertLife(playerB, 38);        
        assertLife(playerA, 38);
        assertLife(playerD, 38);                
    }

}
