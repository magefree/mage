
package org.mage.test.cards.triggers.delayed;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ReturnToHandTest extends CardTestPlayerBase {

    /*
        Undiscovered Paradise not being returned to hand on untap step.
        The land provides mana of any color, but it's never returned to hand.
     */
    @Test
    public void testExilesCreatureAtEndStep() {

        // {W}: Honor Guard gets +0/+1 until end of turn.
        addCard(Zone.HAND, playerA, "Honor Guard"); // Creature 1/1

        // {T}: Add one mana of any color. During your next untap step, as you untap your permanents, return Undiscovered Paradise to its owner's hand.
        addCard(Zone.BATTLEFIELD, playerA, "Undiscovered Paradise", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Honor Guard");

        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertPermanentCount(playerA, "Honor Guard", 1);
        assertPermanentCount(playerA, "Undiscovered Paradise", 0);
        assertHandCount(playerA, "Undiscovered Paradise", 1);

    }
}
