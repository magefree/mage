package org.mage.test.cards.single.woc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ShadowPuppeteersTest extends CardTestPlayerBase {

    // https://github.com/magefree/mage/issues/14326
    @Test
    public void testSublayer() {
        addCard(Zone.BATTLEFIELD, playerA, "Glen Elendra Liege");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Zone.HAND, playerA, "Shadow Puppeteers");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shadow Puppeteers");
        attack(3, playerA, "Faerie Rogue Token");
        setChoice(playerA, true);

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerB, 15);
    }

}
