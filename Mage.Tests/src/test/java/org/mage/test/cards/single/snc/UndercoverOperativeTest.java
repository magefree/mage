package org.mage.test.cards.single.snc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class UndercoverOperativeTest extends CardTestPlayerBase {

    @Test
    public void testWithAstralDragon() {
        addCard(Zone.BATTLEFIELD, playerA, "Princess Yue");
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears");
        addCard(Zone.HAND, playerA, "Undercover Operative");
        addCard(Zone.HAND, playerA, "Astral Dragon");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 12);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Undercover Operative");
        setChoice(playerA, true);
        setChoice(playerA, "Princess Yue");
        setChoice(playerA, "Princess Yue[no copy]");
        setChoice(playerA, true);
        setChoice(playerA, "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Astral Dragon");
        addTarget(playerA, "Moon");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Balduvian Bears", 3);
    }

}
