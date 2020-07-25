package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AlpineHoundmasterTest extends CardTestPlayerBase {

    // When Alpine Houndmaster enters the battlefield, you may search your library for a card named Alpine Watchdog and/or a card named Igneous Cur, reveal them, put them into your hand, then shuffle your library.


    @Test
    public void searchDog() {
        addCard(Zone.HAND, playerA, "Alpine Houndmaster", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.LIBRARY, playerA, "Alpine Watchdog");
        addCard(Zone.LIBRARY, playerA, "Igneous Cur");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Alpine Houndmaster");
        setChoice(playerA, "Yes");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();
        assertHandCount(playerA, 2);

    }

    // Whenever Alpine Houndmaster attacks, it gets +X/+0 until end of turn, where X is the number of other attacking creatures.


    @Test
    public void attack() {
        addCard(Zone.BATTLEFIELD, playerA, "Alpine Houndmaster", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Alpine Watchdog");
        addCard(Zone.BATTLEFIELD, playerA, "Igneous Cur");

        attack(3, playerA, "Alpine Houndmaster");
        attack(3, playerA, "Alpine Watchdog");
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();
        assertPowerToughness(playerA, "Alpine Houndmaster", 3, 2);

    }

}
