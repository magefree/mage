
package org.mage.test.cards.rules;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class DontUntapTest extends CardTestPlayerBase {

    /**
     * Test that the attackers blocked by creatures boosted with
     * Triton Tactics do not untap in their controllers next untap step
     */
    @Test
    public void testTritonTactics() {
        // Up to two target creatures each get +0/+3 until end of turn. Untap those creatures.
        // At this turn's next end of combat, tap each creature that was blocked by one of those
        // creatures this turn and it doesn't untap during its controller's next untap step.        
        addCard(Zone.HAND, playerA, "Triton Tactics");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        // {T}: You gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Soulmender", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        
        activateAbility(4, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: You gain 1 life");
        
        attack(4, playerB, "Silvercoat Lion");        
        castSpell(4, PhaseStep.DECLARE_ATTACKERS, playerA, "Triton Tactics", "Soulmender");
        block(4, playerA, "Soulmender", "Silvercoat Lion");
        
        setStopAt(6, PhaseStep.DRAW);
        execute();

        assertGraveyardCount(playerA, "Triton Tactics", 1);

        assertPowerToughness(playerA, "Soulmender", 1, 1);
        
        assertPowerToughness(playerB, "Silvercoat Lion", 2, 2);
        assertTapped("Silvercoat Lion", true); // Should be stilled tapped in turn 6 because it was blocked in turn 4 with Triton Tactics
        
        assertLife(playerA, 21);
        assertLife(playerB, 20);

    }
    
    /**
     * I used Ajani Vengeant's +1 on a Sublime Archangel and it untap on it's controller's upkeep.
     */
    @Test
    public void TestAjaniVengeantFirst() {

        addCard(Zone.BATTLEFIELD, playerA, "Sublime Archangel", 1); // 4/3

        // +1: Target permanent doesn't untap during its controller's next untap step.
        addCard(Zone.BATTLEFIELD, playerB, "Ajani Vengeant", 1);
        
        attack(1, playerA, "Sublime Archangel");
                
        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "+1: Target permanent doesn't","Sublime Archangel");
                
        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 15); // 4 + 1 from Exalted
        
        assertTapped("Sublime Archangel", true);

    }
        
}