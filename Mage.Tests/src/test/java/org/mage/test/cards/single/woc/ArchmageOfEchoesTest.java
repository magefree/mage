package org.mage.test.cards.single.woc;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import mage.constants.PhaseStep;
import mage.constants.Zone;

public class ArchmageOfEchoesTest extends CardTestPlayerBase {

    private static String ARCHMAGE = "Archmage of Echoes";
    private static String FAERIE = "Faerie Miscreant";

    // Whenever you cast a Faerie or Wizard permanent spell, copy it.
    @Test
    public void testCopy() {
        addCard(Zone.BATTLEFIELD, playerA, ARCHMAGE);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.HAND, playerA, FAERIE);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, FAERIE);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, FAERIE, 2);
    }
    
}
