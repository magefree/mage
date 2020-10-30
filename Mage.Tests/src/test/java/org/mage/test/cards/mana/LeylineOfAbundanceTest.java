
package org.mage.test.cards.mana;

import mage.constants.ManaType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author apetresc
 */
public class LeylineOfAbundanceTest extends CardTestPlayerBase {

    /**
     * Creatures an opponent controls shouldn't trigger Leyline's ability to
     * generate additional mana.
     */
    @Test
    public void testOpponentsManaCreatures() {
        addCard(Zone.BATTLEFIELD, playerA, "Upwelling", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Leyline of Abundance");
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves");
        addCard(Zone.BATTLEFIELD, playerB, "Leyline of Abundance");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Add {G}");
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertManaPool(playerB, ManaType.GREEN, 2);
        assertManaPool(playerA, ManaType.GREEN, 0);
    }
}

