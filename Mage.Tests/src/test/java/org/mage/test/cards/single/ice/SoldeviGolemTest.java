package org.mage.test.cards.single.ice;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author matoro
 */
public class SoldeviGolemTest extends CardTestPlayerBase {

    /*
     * This creature doesn't untap during your untap step.
     * At the beginning of your upkeep, you may untap target tapped creature an opponent controls. If you do, untap this creature.
     */

    @Test
    public void testCantTargetSelf() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Soldevi Golem", 1, true);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertTapped("Soldevi Golem", true);
    }
}
