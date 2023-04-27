package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class FalconerAdeptTest extends CardTestPlayerBase {

    @Test
    public void createTokenTriger(){
        // Whenever Falconer Adept attacks, create a 1/1 white Bird creature token with flying that's tapped and attacking.
        addCard(Zone.BATTLEFIELD, playerA, "Falconer Adept");

        attack(3, playerA, "Falconer Adept");
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertLife(playerB, 17);
    }
}
