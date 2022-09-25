package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class FrostBreathTest extends CardTestPlayerBase {

    @Test
    public void tapTwoCreatures(){
        // Tap up to two target creatures. Those creatures don't untap during their controller's next untap step.
        addCard(Zone.HAND, playerA, "Frost Breath");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Serra Angel");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Frost Breath");
        addTarget(playerA, "Grizzly Bears^Serra Angel");
        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertTapped("Grizzly Bears", true);
        assertTapped("Serra Angel", true);
    }
}
