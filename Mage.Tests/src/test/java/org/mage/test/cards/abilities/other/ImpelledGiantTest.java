package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ImpelledGiantTest extends CardTestPlayerBase {

    @Test
    public void testGainsPower() {
        setStrictChooseMode(true);
        // Trample
        // Tap an untapped red creature you control other than Impelled Giant: Impelled Giant gets +X/+0 until end of turn, where X is the power of the creature tapped this way.
        addCard(Zone.BATTLEFIELD, playerA, "Impelled Giant"); // Creature 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Hurloon Minotaur"); // Creature 2/3

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tap an untapped red creature you control other than Impelled Giant");
        setChoice(playerA, "Hurloon Minotaur");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped("Hurloon Minotaur", true);
        assertPowerToughness(playerA, "Impelled Giant", 5, 3);
    }
}
