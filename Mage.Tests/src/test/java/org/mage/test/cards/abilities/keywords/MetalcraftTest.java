
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class MetalcraftTest extends CardTestPlayerBase {

    /**
     * Rusted Relic or Blinkmoth nexus is bugged	
     * Either Relic does not see Blinkmoth as an artifact or it does not turn 
     * into one when it should.
     * 
     */
    @Test
    public void testMetalcraftFromBlinkmoth() {
        addCard(Zone.BATTLEFIELD, playerA, "Darksteel Citadel",1); 

        // <i>Metalcraft</i> &mdash; {this} is a 5/5 Golem artifact creature as long as you control three or more artifacts
        addCard(Zone.BATTLEFIELD, playerA, "Rusted Relic", 1); 

        // {T}: Add {C}to your mana pool.
        // {1}: Blinkmoth Nexus becomes a 1/1 Blinkmoth artifact creature with flying until end of turn. It's still a land.
        // {1}, {T}: Target Blinkmoth creature gets +1/+1 until end of turn.        
        addCard(Zone.BATTLEFIELD, playerA, "Blinkmoth Nexus", 1); 
        
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}:");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Blinkmoth Nexus", 1, 1);
        assertPowerToughness(playerA, "Rusted Relic", 5, 5);
        
    }             
     
}
