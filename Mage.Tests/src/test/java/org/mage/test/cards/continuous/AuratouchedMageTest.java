package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Created by IGOUDT on 23-2-2017.
 */
public class AuratouchedMageTest extends CardTestPlayerBase {

    @Test
    public void testSearch() {
    addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);

    addCard(Zone.HAND, playerA, "Auratouched Mage");

    castSpell(0, PhaseStep.PRECOMBAT_MAIN, playerA, "Auratouched Mage");
    addCard(Zone.LIBRARY, playerA, "White Ward", 1);
    setChoice(playerA, "White ward");
    setStopAt(0, PhaseStep.PRECOMBAT_MAIN);
    execute();
    }

}
