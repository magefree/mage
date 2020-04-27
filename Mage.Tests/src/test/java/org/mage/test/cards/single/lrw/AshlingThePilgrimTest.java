package org.mage.test.cards.single.lrw;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */

public class AshlingThePilgrimTest extends CardTestPlayerBase {

    private static final String ashling = "Ashling the Pilgrim";
    private static final String ashAbility = "{1}{R}: ";
    private static final String cshift = "Cloudshift";

    @Test
    public void testAshling() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.BATTLEFIELD, playerA, ashling);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, ashAbility);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, ashAbility);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, ashAbility);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAllCommandsUsed();
        assertPermanentCount(playerA, ashling, 0);
        assertLife(playerA, 17);
    }

    @Test
    public void testAshlingMultipleTurns() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);
        addCard(Zone.BATTLEFIELD, playerA, ashling);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, ashAbility);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, ashAbility);
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, ashAbility);
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, ashAbility);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertAllCommandsUsed();
        assertPermanentCount(playerA, ashling, 1);
        assertCounterCount(ashling, CounterType.P1P1, 4);
        assertLife(playerA, 20);
    }

    @Test
    public void testAshlingMultipleTurns2() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 14);
        addCard(Zone.BATTLEFIELD, playerA, ashling);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, ashAbility);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, ashAbility);
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, ashAbility);
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, ashAbility);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, ashAbility);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, ashAbility);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, ashAbility);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertAllCommandsUsed();
        assertPermanentCount(playerA, ashling, 0);
        assertLife(playerA, 13);
    }

    @Test
    public void testBlinkedAshling() {
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 7);
        addCard(Zone.BATTLEFIELD, playerA, ashling);
        addCard(Zone.HAND, playerA, cshift);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, ashAbility);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cshift, ashling);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, ashAbility);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, ashAbility);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAllCommandsUsed();
        assertPermanentCount(playerA, ashling, 1);
        assertCounterCount(ashling, CounterType.P1P1, 2);
        assertLife(playerA, 20);
    }

    @Test
    public void testBlinkedAshling2() {
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 9);
        addCard(Zone.BATTLEFIELD, playerA, ashling);
        addCard(Zone.HAND, playerA, cshift);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, ashAbility);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cshift, ashling);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, ashAbility);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, ashAbility);
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, ashAbility);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAllCommandsUsed();
        assertPermanentCount(playerA, ashling, 0);
        assertLife(playerA, 17);
    }
}
