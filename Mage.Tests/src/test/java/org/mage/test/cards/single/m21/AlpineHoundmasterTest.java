package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AlpineHoundmasterTest extends CardTestPlayerBase {



    @Test
    public void searchDog() {
        // When Alpine Houndmaster enters the battlefield, you may search your library for a card named
        // Alpine Watchdog and/or a card named Igneous Cur, reveal them, put them into your hand, then shuffle your library.
        addCard(Zone.HAND, playerA, "Alpine Houndmaster", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.LIBRARY, playerA, "Alpine Watchdog");
        addCard(Zone.LIBRARY, playerA, "Igneous Cur");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Alpine Houndmaster");
        setChoice(playerA, true);
        addTarget(playerA, "Alpine Watchdog");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 1);

    }

    @Test
    public void searchCur() {
        // When Alpine Houndmaster enters the battlefield, you may search your library for a card named
        // Alpine Watchdog and/or a card named Igneous Cur, reveal them, put them into your hand, then shuffle your library.
        addCard(Zone.HAND, playerA, "Alpine Houndmaster", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.LIBRARY, playerA, "Alpine Watchdog");
        addCard(Zone.LIBRARY, playerA, "Igneous Cur");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Alpine Houndmaster");
        setChoice(playerA, true);
        addTarget(playerA, "Igneous Cur");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 1);

    }

    @Test
    public void searchBoth() {
        // When Alpine Houndmaster enters the battlefield, you may search your library for a card named
        // Alpine Watchdog and/or a card named Igneous Cur, reveal them, put them into your hand, then shuffle your library.
        addCard(Zone.HAND, playerA, "Alpine Houndmaster", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.LIBRARY, playerA, "Alpine Watchdog");
        addCard(Zone.LIBRARY, playerA, "Igneous Cur");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Alpine Houndmaster");
        setChoice(playerA, true);
        addTarget(playerA, "Igneous Cur^Alpine Watchdog");
        //addTarget(playerA, "");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 2);

    }



    @Test
    public void attack() {
        addCard(Zone.BATTLEFIELD, playerA, "Alpine Houndmaster", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Alpine Watchdog");
        addCard(Zone.BATTLEFIELD, playerA, "Igneous Cur");

        // Whenever Alpine Houndmaster attacks, it gets +X/+0 until end of turn, where X is the number of other attacking creatures.
        attack(3, playerA, "Alpine Houndmaster");
        attack(3, playerA, "Alpine Watchdog");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Alpine Houndmaster", 3, 2);

    }

}
