package org.mage.test.cards.watchers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class FellShepherdTest extends CardTestPlayerBase {
    /*
     * Fell Shepherd
     * Creature — Avatar 8/6, 5BB (7)
     * Whenever Fell Shepherd deals combat damage to a player, you may return to 
     * your hand all creature cards that were put into your graveyard from the battlefield this turn.
     * {B}, Sacrifice another creature: Target creature gets -2/-2 until end of turn.
     *
    */
    
    // test that creatures are returned to hand
    @Test
    public void testCreaturesReturn() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Craw Wurm");
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin");
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Roughrider");
        addCard(Zone.BATTLEFIELD, playerA, "Fell Shepherd");
        
        playerA.addChoice("Craw Wurm");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{B}, Sacrifice another creature: Target creature gets -2/-2 until end of turn.", "Raging Goblin");
        attack(3, playerA, "Fell Shepherd");
        
        setStopAt(3, PhaseStep.END_TURN);
        execute();
            
        assertLife(playerB, 12);
        assertPermanentCount(playerA, "Goblin Roughrider", 1);
        assertPermanentCount(playerA, "Raging Goblin", 0);
        assertPermanentCount(playerA, "Craw Wurm", 0);
        assertHandCount(playerA, "Craw Wurm", 1);
        assertHandCount(playerA, "Raging Goblin", 1);
    }
    
}
