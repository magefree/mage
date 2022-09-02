package org.mage.test.cards.single.ncc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 * {@link mage.cards.s.ShieldBroker Shield Broker}
 * {3}{U}{U}
 * Creature — Cephalid Advisor
 * When Shield Broker enters the battlefield, put a shield counter on target noncommander creature you don’t control.
 * You gain control of that creature for as long as it has a shield counter on it.
 * (If it would be dealt damage or destroyed, remove a shield counter from it instead.)
 */
public class ShieldBrokerTest extends CardTestCommander4Players {

    private static final String shieldBroker = "Shield Broker";
    private static final String rograkh = "Rograkh, Son of Rohgahh";
    private static final String lightningBolt = "Lightning Bolt";

    /**
     * Test that it works for non-commander creature.
     */
    @Test
    public void testNonCommander() {
        addCard(Zone.BATTLEFIELD, playerD, rograkh); // {0}

        addCard(Zone.HAND, playerA, shieldBroker); // {3}{U}{U}
        addCard(Zone.HAND, playerA, lightningBolt);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shieldBroker);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertCounterCount(rograkh, CounterType.SHIELD, 1);
        assertPermanentCount(playerA, rograkh, 1);

        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Mountain");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, lightningBolt, rograkh);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertCounterCount(rograkh, CounterType.SHIELD, 0);
        assertPermanentCount(playerA, rograkh, 0);
        assertPermanentCount(playerD, rograkh, 1);
    }

    /**
     * Test that it does not work for commander creature.
     */
    @Test
    public void testCommander() {
        addCard(Zone.COMMAND, playerD, rograkh); // {0}

        addCard(Zone.HAND, playerA, shieldBroker); // {3}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, rograkh);

        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, shieldBroker);

        setStopAt(5, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertCounterCount(rograkh, CounterType.SHIELD, 0);
        assertPermanentCount(playerA, rograkh, 0);
        assertPermanentCount(playerD, rograkh, 1);
    }
}
