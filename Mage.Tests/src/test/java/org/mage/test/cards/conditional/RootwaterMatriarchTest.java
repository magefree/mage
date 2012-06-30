/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.conditional;

import mage.Constants;
import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jeff
 */
public class RootwaterMatriarchTest extends CardTestPlayerBase {
    
    @Test
    public void testTargetFail() {
        
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Rootwater Matriarch");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Memnite");

        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Gain control of target creature for as long as that creature is enchanted.", "Memnite");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertPermanentCount(playerA, "Rootwater Matriarch", 1);
        assertPermanentCount(playerB, "Memnite", 1);
    }
    
    @Test
    public void testTargetSuccess() {
        
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Rootwater Matriarch");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Memnite");
        
        addCard(Constants.Zone.HAND, playerA, "Flight");
        
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Flight", "Memnite");

        activateAbility(1, Constants.PhaseStep.BEGIN_COMBAT, playerA, "{T}: Gain control of target creature for as long as that creature is enchanted.", "Memnite");

        setStopAt(1, Constants.PhaseStep.END_COMBAT);
        execute();
        
        assertPermanentCount(playerA, "Rootwater Matriarch", 1);
        assertPermanentCount(playerA, "Memnite", 1);
        
    }
    
    @Test
    public void testGainControlEnchantedTargetAndRWLeavesPlay() {
        
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Rootwater Matriarch");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Memnite");
        addCard(Constants.Zone.HAND, playerA, "Unsummon");
        addCard(Constants.Zone.HAND, playerA, "Flight");
        
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Flight", "Memnite");

        activateAbility(1, Constants.PhaseStep.BEGIN_COMBAT, playerA, "{T}: Gain control of target creature for as long as that creature is enchanted.", "Memnite");
        
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Unsummon", "Rootwater Matriarch");
        
        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();
        
        assertPermanentCount(playerA, "Rootwater Matriarch", 0);
        assertPermanentCount(playerA, "Memnite", 1);
    }
    
    @Test
    public void testGainControlEnchantedTargetAndAuraIsDisenchanted() {
        
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Rootwater Matriarch");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Memnite");
        addCard(Constants.Zone.HAND, playerA, "Disenchant");
        addCard(Constants.Zone.HAND, playerA, "Flight");
        
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Flight", "Memnite");

        activateAbility(1, Constants.PhaseStep.BEGIN_COMBAT, playerA, "{T}: Gain control of target creature for as long as that creature is enchanted.", "Memnite");
        
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Disenchant", "Flight");
        
        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();
        
        assertPermanentCount(playerA, "Rootwater Matriarch", 1);
        assertPermanentCount(playerB, "Memnite", 1);
    }
}
