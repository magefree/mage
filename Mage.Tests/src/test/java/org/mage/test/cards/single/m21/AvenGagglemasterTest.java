package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AvenGagglemasterTest extends CardTestPlayerBase {

    @Test
    public void gainLife(){
        addCard(Zone.HAND, playerA, "Aven Gagglemaster");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.BATTLEFIELD, playerB, "Birds of Paradise");

        // When Aven Gagglemaster enters the battlefield, you gain 2 life for each creature you control with flying.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aven Gagglemaster");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 22);
    }
}
