package org.mage.test.cards.single.ydft;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class MotivatedMuralistTest extends CardTestPlayerBase {

    @Test
    public void testConjureOnEntry() {
        addCard(Zone.HAND, playerA, "Motivated Muralist");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Motivated Muralist");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Motivated Muralist", 1);
        assertHandCount(playerA, "Inspiring Statuary", 1);
        assertHandCount(playerB, "Inspiring Statuary", 0);
    }

    @Test
    public void testImprovise() {
        addCard(Zone.HAND, playerA, "Motivated Muralist");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter");

        disableManaAutoPayment(playerA);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Motivated Muralist");
        setChoice(playerA, "Red");
        setChoice(playerA, "Improvise");
        addTarget(playerA, "Ornithopter");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Motivated Muralist", 1);
        assertTapped("Ornithopter", true);
        assertHandCount(playerA, "Inspiring Statuary", 1);
    }
}
