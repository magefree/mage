package org.mage.test.cards.watchers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class TunnelIgnusTest extends CardTestPlayerBase {
    /*
     * Tunnel Ignus
     * Creature — Elemental 2/1, 1R (2)
     * Whenever a land enters the battlefield under an opponent's control, if 
     * that player had another land enter the battlefield under their
     * control this turn, Tunnel Ignus deals 3 damage to that player.
     *
    */
    
    // test extra lands damage controller
    @Test
    public void testExtraLandsDamage() {
        addCard(Zone.BATTLEFIELD, playerB, "Tunnel Ignus");
        addCard(Zone.HAND, playerA, "Scalding Tarn");
        
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scalding Tarn");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}, ");
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();
            
        assertLife(playerA, 16);
        assertGraveyardCount(playerA, "Scalding Tarn", 1);
    }
    
}
