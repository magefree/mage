package org.mage.test.cards.single.war;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author escplan9
 */
public class KioraTest extends CardTestPlayerBase {
    
    /*
    {2} {U/G}
    7 loyalty
    Whenever a creature with power 4 or greater enters the battlefield, draw a card.
    [-1]: Untap target permanent.
    */
    public final String kiora = "Kiora, Behemoth Beckoner";

    
    @Test
    public void kioraUntapLand() {

        addCard(Zone.BATTLEFIELD, playerA, kiora);
        addCard(Zone.BATTLEFIELD, playerA, "Bronze Sable"); // {2} 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Giant Growth");
        
        // cast a spell to tap the only land
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", "Bronze Sable");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        
        // untap that only land
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-1: Untap target permanent", "Forest");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertGraveyardCount(playerA, "Giant Growth", 1);
        assertHandCount(playerA, 0);
        assertPowerToughness(playerA, "Bronze Sable", 5, 4); // giant growthed 2/1 + 3/3 = 5/4
        assertCounterCount(playerA, kiora, CounterType.LOYALTY, 6);
        assertTapped("Forest", false); // Kiora's untap
    }
}