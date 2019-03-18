
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class MephidrossVampireTest extends CardTestPlayerBase {

    /**
     * Checks if only one triggered ability is handleded in the pool
     *
     */
    @Test
    public void testMultiTriggers() {
        // Creature - Vampire 3/4
        // Flying
        // Each creature you control is a Vampire in addition to its other creature types and has
        // "Whenever this creature deals damage to a creature, put a +1/+1 counter on this creature."
        addCard(Zone.BATTLEFIELD, playerB, "Mephidross Vampire");
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter");

        attack(2, playerB, "Mephidross Vampire");
        block(2, playerA, "Ornithopter", "Mephidross Vampire");
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerB, "Mephidross Vampire", 1);
        assertPowerToughness(playerB,  "Mephidross Vampire", 4, 5);

        Assert.assertEquals("There should only be one triggered ability in the list of triggers of the State", 1,  currentGame.getState().getTriggers().size());
        
    }
}
