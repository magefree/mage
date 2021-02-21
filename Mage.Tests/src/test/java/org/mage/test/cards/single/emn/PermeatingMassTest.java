package org.mage.test.cards.single.emn;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9
 */
public class PermeatingMassTest extends CardTestPlayerBase {

    @Test
    public void testWhenDiesInCombatMakesCopyStill() {

        addCard(Zone.BATTLEFIELD, playerA, "Hill Giant"); // 3/3

        // Whenever Permeating Mass deals combat damage to a creature, that creature becomes a copy of Permeating Mass.
        addCard(Zone.BATTLEFIELD, playerB, "Permeating Mass"); // 1/3

        attack(1, playerA, "Hill Giant");
        block(1, playerB, "Permeating Mass", "Hill Giant");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20);
        assertGraveyardCount(playerB, "Permeating Mass", 1);
        assertPermanentCount(playerA, "Permeating Mass", 1);
        assertPowerToughness(playerA, "Permeating Mass", 1, 3);
    }
    
    /*
     * NOTE: As of 04/19/2017 this test is failing due to a bug in code. See issue #3167
    */
    @Test
    public void damagedCreatureWithVaryingPTbecomesCopyOfPermeatingMass() {
        /*
        Permeating Mass {G} 
        Creature — Spirit - 1/3
        Whenever Permeating Mass deals combat damage to a creature, that creature becomes a copy of Permeating Mass.
        */
        String pMass = "Permeating Mass";

        /*
         Dungrove Elder {2}{G}
        Creature — Treefolk
        Hexproof  * / *
        Dungrove Elder's power and toughness are each equal to the number of Forests you control.
        */
        String dElder = "Dungrove Elder";

        addCard(Zone.BATTLEFIELD, playerA, pMass);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerB, dElder); // 4/4 with the 4 forests

        attack(2, playerB, dElder);
        block(2, playerA, pMass, dElder);
        
        setStopAt(2, PhaseStep.END_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertGraveyardCount(playerA, pMass, 1);
        assertPermanentCount(playerB, pMass, 1); // dungrove elder becomes copy of permeating mass
        assertPowerToughness(playerB, "Permeating Mass", 1, 3); // and should have P/T 1/3
    }
}
