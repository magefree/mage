package org.mage.test.cards.conditional;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class JacesPhantasmTest extends CardTestPlayerBase {

    @Test
    public void testNoBoost() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island");
        addCard(Constants.Zone.HAND, playerA, "Jace's Phantasm");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Jace's Phantasm");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Jace's Phantasm", 1, 1);
    }

    @Test
    public void testWithBoost() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Constants.Zone.HAND, playerA, "Jace's Phantasm");
        addCard(Constants.Zone.HAND, playerA, "Mind Sculpt", 3);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Mind Sculpt", playerB);
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Mind Sculpt", playerB);
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Mind Sculpt", playerA);
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Jace's Phantasm");

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Jace's Phantasm", 5, 5);
    }

    @Test
    public void testWithBoost2() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Constants.Zone.HAND, playerA, "Jace's Phantasm");
        addCard(Constants.Zone.HAND, playerA, "Mind Sculpt", 3);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Jace's Phantasm");
        castSpell(3, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Mind Sculpt", playerB);
        castSpell(3, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Mind Sculpt", playerB);
        castSpell(3, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Mind Sculpt", playerA);

        setStopAt(3, Constants.PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, 14);
        assertPowerToughness(playerA, "Jace's Phantasm", 5, 5);
    }

}
