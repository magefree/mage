package org.mage.test.cards.conditional;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class JacesPhantasmTest extends CardTestPlayerBase {

    @Test
    public void testNoBoost() {
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.HAND, playerA, "Jace's Phantasm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Jace's Phantasm");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Jace's Phantasm", 1, 1);
    }

    @Test
    public void testWithBoost() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Zone.HAND, playerA, "Jace's Phantasm");
        addCard(Zone.HAND, playerA, "Mind Sculpt", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mind Sculpt", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mind Sculpt", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mind Sculpt", true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Jace's Phantasm");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, 21);
        assertPowerToughness(playerA, "Jace's Phantasm", 5, 5);
    }

    @Test
    public void testWithBoost2() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Zone.HAND, playerA, "Jace's Phantasm");
        addCard(Zone.HAND, playerA, "Mind Sculpt", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Jace's Phantasm");

        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Mind Sculpt");
        waitStackResolved(3, PhaseStep.POSTCOMBAT_MAIN);
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Mind Sculpt");
        waitStackResolved(3, PhaseStep.POSTCOMBAT_MAIN);
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Mind Sculpt", true);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

//        assertGraveyardCount(playerB, 21);
//        assertPowerToughness(playerA, "Jace's Phantasm", 5, 5);
    }

}
