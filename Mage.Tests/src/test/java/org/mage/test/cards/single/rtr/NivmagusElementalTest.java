package org.mage.test.cards.single.rtr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class NivmagusElementalTest extends CardTestPlayerBase {

    private static final String nivmagus = "Nivmagus Elemental";
    private static final String bolt = "Lightning Bolt";
    private static final String grapeshot = "Grapeshot";

    @Test
    public void testNivmagusElemental() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, nivmagus);
        addCard(Zone.HAND, playerA, bolt);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exile");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, nivmagus, CounterType.P1P1, 2);
        assertLife(playerB, 20);
        assertExileCount(playerA, bolt, 1);
    }

    @Test
    public void testNivmagusElementalStorm() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, nivmagus);
        addCard(Zone.HAND, playerA, bolt);
        addCard(Zone.HAND, playerA, grapeshot);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exile");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, grapeshot, playerB);
        setChoice(playerA, false);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exile");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exile");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, nivmagus, CounterType.P1P1, 6);
        assertLife(playerB, 20);
        assertExileCount(playerA, bolt, 1);
        assertExileCount(playerA, grapeshot, 1);
    }
}
