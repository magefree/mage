
package org.mage.test.cards.replacement.entersBattlefield;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class UnbreathingHordeTest extends CardTestPlayerBase {

    /**
     * That the +1/+1 counters are added to Living Lore before state based
     * actions take place
     */
    @Test
    public void testCountersAdded() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        // Unbreathing Horde enters the battlefield with a +1/+1 counter on it for each other Zombie you control and each Zombie card in your graveyard.
        // If Unbreathing Horde would be dealt damage, prevent that damage and remove a +1/+1 counter from it.
        addCard(Zone.HAND, playerA, "Unbreathing Horde"); //{2}{B} - 0/0

        addCard(Zone.BATTLEFIELD, playerA, "Dross Crocodile", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Dross Crocodile", 2);
        addCard(Zone.GRAVEYARD, playerA, "Dross Crocodile", 2);
        addCard(Zone.GRAVEYARD, playerB, "Dross Crocodile", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unbreathing Horde");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Unbreathing Horde", 1);
        assertPowerToughness(playerA, "Unbreathing Horde", 4, 4);
    }

}
