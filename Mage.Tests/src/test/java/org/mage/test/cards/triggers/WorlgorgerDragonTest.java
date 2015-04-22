/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class WorlgorgerDragonTest extends CardTestPlayerBase {

    /**
     * Tests that exiled permanents return to battlefield
     */
    @Test
    public void testDisabledEffectOnChangeZone() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.HAND, playerA, "Worldgorger Dragon");
        
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Gerrard's Battle Cry", 1);
        
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        // Destroy target nonartifact, nonblack creature. It can't be regenerated.
        addCard(Zone.HAND, playerB, "Terror", 1);
        

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Worldgorger Dragon");
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Terror", "Worldgorger Dragon");
        
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Worldgorger Dragon", 1);
        assertGraveyardCount(playerB, "Terror", 1);
        
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Gerrard's Battle Cry", 1);
        
    }

}