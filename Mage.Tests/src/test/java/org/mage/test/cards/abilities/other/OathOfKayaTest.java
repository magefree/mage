package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class OathOfKayaTest extends CardTestPlayerBase {

    @Test
    public void test_AttackingPlayer() {
        // Whenever an opponent attacks a planeswalker you control with one or more creatures,
        // Oath of Kaya deals 2 damage to that player and you gain 2 life.
        addCard(Zone.BATTLEFIELD, playerB, "Oath of Kaya", 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Liliana, Dreadhorde General", 1);

        attack(1, playerA, "Grizzly Bears", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerB, "Liliana, Dreadhorde General", CounterType.LOYALTY, 6);
        assertLife(playerA, 20);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_AttackingPlaneswalker() {
        // Whenever an opponent attacks a planeswalker you control with one or more creatures,
        // Oath of Kaya deals 2 damage to that player and you gain 2 life.
        addCard(Zone.BATTLEFIELD, playerB, "Oath of Kaya", 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Liliana, Dreadhorde General", 1);

        attack(1, playerA, "Grizzly Bears", "Liliana, Dreadhorde General");
        attack(1, playerA, "Grizzly Bears", "Liliana, Dreadhorde General");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerB, "Liliana, Dreadhorde General", CounterType.LOYALTY, 6 - 2 * 2);
        assertLife(playerA, 20 - 2);
        assertLife(playerB, 20 + 2);
    }

    @Test
    public void test_AttackingTwoPlaneswalkers() {
        // Whenever an opponent attacks a planeswalker you control with one or more creatures,
        // Oath of Kaya deals 2 damage to that player and you gain 2 life.
        addCard(Zone.BATTLEFIELD, playerB, "Oath of Kaya", 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Liliana, Dreadhorde General", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Vivien, Champion of the Wilds", 1);

        attack(1, playerA, "Grizzly Bears", "Liliana, Dreadhorde General");
        attack(1, playerA, "Grizzly Bears", "Vivien, Champion of the Wilds");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerB, "Liliana, Dreadhorde General", CounterType.LOYALTY, 6 - 2);
        assertCounterCount(playerB, "Vivien, Champion of the Wilds", CounterType.LOYALTY, 4 - 2);
        assertLife(playerA, 20 - 2);
        assertLife(playerB, 20 + 2);
    }
}
