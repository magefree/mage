
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 702.64. Aura swap
 *   702.64a Aura swap is an activated ability of some Aura cards. 
 *   "Aura swap [cost]" means "[Cost]: You may exchange this permanent with an Aura card in your hand."
 *   702.64b If either half of the exchange can't be completed, the ability has no effect.
 * 
 * @author LevelX2
 */

public class AuraSwapTest extends CardTestPlayerBase {

    /**
     * Test normal swap
     */
    @Test
    public void testAuraSwap1() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        // Enchanted creature has flying.
        // Aura swap {2}{U}
        addCard(Zone.HAND, playerA, "Arcanum Wings"); // {1}{U}

        // Enchant creature
        // Enchanted creature gets +10/+10 and has trample and annihilator 2. (Whenever it attacks, defending player sacrifices two permanents.)
        addCard(Zone.HAND, playerA, "Eldrazi Conscription"); // {8}
        
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA,  "Arcanum Wings", "Silvercoat Lion");
        
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Aura swap");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Arcanum Wings", 1);
        assertPermanentCount(playerA, "Eldrazi Conscription", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 12, 12);
    }
   /**
     * Test swap canceled because enchantment disenchanted
     */
    @Test
    public void testAuraSwap2() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        // Enchanted creature has flying.
        // Aura swap {2}{U}
        addCard(Zone.HAND, playerA, "Arcanum Wings"); // {1}{U}

        // Enchant creature
        // Enchanted creature gets +10/+10 and has trample and annihilator 2. (Whenever it attacks, defending player sacrifices two permanents.)
        addCard(Zone.HAND, playerA, "Eldrazi Conscription"); // {8}        
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.HAND, playerB, "Disenchant");       
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA,  "Arcanum Wings", "Silvercoat Lion");
        
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Aura swap");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB,  "Disenchant", "Arcanum Wings", "Aura swap");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Disenchant", 1);
        
        assertGraveyardCount(playerA, "Arcanum Wings", 1);
        assertPermanentCount(playerA, "Eldrazi Conscription", 0);
        assertHandCount(playerA, "Eldrazi Conscription", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2);
    }
}
