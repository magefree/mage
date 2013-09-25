/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.prevention;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jeffwadsworth
 */
public class MarkOfAsylumTest extends CardTestPlayerBase{
    
    @Test
    public void testMarkOfAsylumEffect() {
        
        addCard(Zone.BATTLEFIELD, playerA, "Mark of Asylum");
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        
        addCard(Zone.BATTLEFIELD, playerB, "Arbor Elf", 1);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Memnite");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Arbor Elf");
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Memnite", 1);
        assertPermanentCount(playerB, "Arbor Elf", 0);

    }
}
