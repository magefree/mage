/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.triggers.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jeffwadsworth
 */
public class SeraphAndSengirVampireTest extends CardTestPlayerBase {

    @Test
    public void testBothDieButTriggersStillFire() {
        
        // https://github.com/magefree/mage/issues/8293
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Seraph", 1); // 4/4 flying : take control of creature that dies after taking damage
        addCard(Zone.BATTLEFIELD, playerB, "Sengir Vampire", 1);  // 4/4 flying : gains +1/+1 for any creature that takes damage and dies

        attack(3, playerA, "Seraph");
        block(3, playerB, "Sengir Vampire", "Seraph");

        setStopAt(4, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();
        
        assertGraveyardCount(playerA, "Seraph", 1);  // Seraph dies
        assertGraveyardCount(playerB, "Sengir Vampire", 0);
        assertPermanentCount(playerA, "Sengir Vampire", 1);  // playerA now controls the Sengir Vampire
    }
}
