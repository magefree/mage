
package org.mage.test.testapi;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class TestAPITest extends CardTestPlayerBase {

    /**
     * Tests that it is possible to cast two instants in a row.
     * Shock should be able to remove Last Breath's target before it resolves
     */
    @Test
    public void testCardTestPlayerAPIImpl1() {
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        addCard(Zone.HAND, playerA, "Last Breath");
        addCard(Zone.HAND, playerA, "Shock");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Last Breath", "Grizzly Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "Grizzly Bears");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertLife(playerA, 20); // no life gain, Last Breath should have been fizzled
    }

    /**
     * Tests that it is possible to wait until certain spell resolves with StackClause.WHILE_NOT_ON_STACK.
     * Shock won't be even cast here as no Last Breath should resolve.
     */
    @Test
    public void testCardTestPlayerAPIImpl2() {
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        addCard(Zone.HAND, playerA, "Last Breath");
        addCard(Zone.HAND, playerA, "Shock");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Last Breath", "Grizzly Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "Grizzly Bears", "Last Breath",
                StackClause.WHILE_NOT_ON_STACK);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertLife(playerA, 24); // gain 4 life from Last Breath
    }
}
