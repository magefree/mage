package org.mage.test.cards.single.hob;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class TheEaglesAreComingTest extends CardTestPlayerBase {

    @Test
    public void testKicked() {
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "The Eagles Are Coming!");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Eagles Are Coming!");
        setChoice(playerA, true);
        addTarget(playerA, "Balduvian Bears^Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Bird Soldier Token", 2);
        assertHandCount(playerA, "Balduvian Bears", 1);
        assertHandCount(playerA, "Grizzly Bears", 1);
    }
}
