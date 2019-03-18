
package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ImpelledGiantTest extends CardTestPlayerBase {

    @Test
    public void testGainsPower() {
        addCard(Zone.BATTLEFIELD, playerA, "Impelled Giant");
        addCard(Zone.BATTLEFIELD, playerA, "Hurloon Minotaur");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tap an untapped red creature you control other than Impelled Giant");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped("Hurloon Minotaur", true);
        assertPowerToughness(playerA, "Impelled Giant", 5, 3);
    }
}
