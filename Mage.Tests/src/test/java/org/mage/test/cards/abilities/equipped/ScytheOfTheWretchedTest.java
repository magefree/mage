/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package org.mage.test.cards.abilities.equipped;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class ScytheOfTheWretchedTest extends CardTestPlayerBase {

    /**
     * Test that the creature taht died returns to battlefield under your control 
     * if the previous equiped creature does not die
     */
    @Test
    public void testEquipAlive() {
        addCard(Zone.BATTLEFIELD, playerA, "Soulmender", 1);        

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");        
        // Equipped creature gets +2/+2.
        // Whenever a creature dealt damage by equipped creature this turn dies, return that card to the battlefield under your control. Attach Scythe of the Wretched to that creature.
        // Equip {4}  
        addCard(Zone.BATTLEFIELD, playerB, "Scythe of the Wretched");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Equip {4}", "Silvercoat Lion");

        attack(2, playerB, "Silvercoat Lion");
        block(2, playerA, "Soulmender", "Silvercoat Lion");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        
        assertPermanentCount(playerA, "Soulmender", 0);
        assertPermanentCount(playerB, "Soulmender", 1);
        assertPowerToughness(playerB, "Soulmender", 3, 3);
        
        assertPowerToughness(playerB, "Silvercoat Lion", 2, 2);

        Permanent silvercoatLion = getPermanent("Silvercoat Lion", playerB.getId());
        Assert.assertTrue("Silvercoat Lion may not have any attachments", silvercoatLion.getAttachments().isEmpty());
    }
   /**
     * Test that the creature that died returns to battlefield under your control 
     * if the previous equiped creature does die after equipment is removed
     */
    @Test
    public void testEquipDied() {
        addCard(Zone.BATTLEFIELD, playerA, "Oreskos Swiftclaw", 1); // 3/1   

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");        
        // Equipped creature gets +2/+2.
        // Whenever a creature dealt damage by equipped creature this turn dies, return that card to the battlefield under your control. Attach Scythe of the Wretched to that creature.
        // Equip {4}  
        addCard(Zone.BATTLEFIELD, playerB, "Scythe of the Wretched");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Equip {4}", "Silvercoat Lion");

        attack(2, playerB, "Silvercoat Lion");
        block(2, playerA, "Oreskos Swiftclaw", "Silvercoat Lion");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        
        assertPermanentCount(playerA, "Oreskos Swiftclaw", 0);
        assertPermanentCount(playerB, "Oreskos Swiftclaw", 1);
        assertPowerToughness(playerB, "Oreskos Swiftclaw", 5, 3);
        
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
    }
    
   /**
     * Test that the creature that died returns to battlefield under your control 
     * if the previous equiped creature does die already in combat
     */
    @Test
    public void testEquipDiedInCombat() {
        addCard(Zone.BATTLEFIELD, playerA, "Serra Angel", 1); // 4/4

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");        
        // Equipped creature gets +2/+2.
        // Whenever a creature dealt damage by equipped creature this turn dies, return that card to the battlefield under your control. Attach Scythe of the Wretched to that creature.
        // Equip {4}  
        addCard(Zone.BATTLEFIELD, playerB, "Scythe of the Wretched");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Equip {4}", "Silvercoat Lion");

        attack(2, playerB, "Silvercoat Lion");
        block(2, playerA, "Serra Angel", "Silvercoat Lion");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        
        assertPermanentCount(playerA, "Serra Angel", 0);
        assertPermanentCount(playerB, "Serra Angel", 1);
        assertPowerToughness(playerB, "Serra Angel", 6, 6);
        
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
    }    
    
    
 /**
     * Test that the creature that died returns to battlefield under your control 
     * if the previous equiped creature does die already in combat and the equipment was destroyed meanwhile
     */
    @Test
    public void testEquipDiedInCombat2() {
        addCard(Zone.BATTLEFIELD, playerA, "Serra Angel", 1); // 4/4
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Disenchant", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");        
        // Equipped creature gets +2/+2.
        // Whenever a creature dealt damage by equipped creature this turn dies, return that card to the battlefield under your control. Attach Scythe of the Wretched to that creature.
        // Equip {4}  
        addCard(Zone.BATTLEFIELD, playerB, "Scythe of the Wretched");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Equip {4}", "Silvercoat Lion");

        attack(2, playerB, "Silvercoat Lion");
        block(2, playerA, "Serra Angel", "Silvercoat Lion");
        
        castSpell(2, PhaseStep.COMBAT_DAMAGE, playerA, "Disenchant", "Scythe of the Wretched", "Whenever a creature dealt damage");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        
        assertGraveyardCount(playerA, "Disenchant", 1);
        assertGraveyardCount(playerB, "Scythe of the Wretched", 1);
        
        assertPermanentCount(playerA, "Serra Angel", 0);
        assertPermanentCount(playerB, "Serra Angel", 1);
        assertPowerToughness(playerB, "Serra Angel", 4, 4);
        
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
    }        
}