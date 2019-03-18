package org.mage.test.cards.mana;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class MycosynthLatticeTest extends CardTestPlayerBase {

    @Test
    public void testMycoSynthLatticeNormal() {
        // Converge - Crystalline Crawler enters the battlefield with a +1/+1 counter on it for each color of mana spent to cast it.
        // Remove a +1/+1 counter from Crystalline Crawler: Add one mana of any color.
        // {T}: Put a +1/+1 counter on Crystalline Crawler.
        String crawler = "Crystalline Crawler";

        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, crawler);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, crawler);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);

        execute();

        assertCounterCount(playerA, crawler, CounterType.P1P1, 4);
    }

    @Test
    public void testMycoSynthLatticeWithCrystallineCrawler() {
        // Converge - Crystalline Crawler enters the battlefield with a +1/+1 counter on it for each color of mana spent to cast it.
        // Remove a +1/+1 counter from Crystalline Crawler: Add one mana of any color.
        // {T}: Put a +1/+1 counter on Crystalline Crawler.
        String crawler = "Crystalline Crawler";

        // All permanents are artifacts in addition to their other types.
        // All cards that aren't on the battlefield, spells, and permanents are colorless.
        // Players may spend mana as though it were mana of any color.
        String lattice = "Mycosynth Lattice";

        addCard(Zone.BATTLEFIELD, playerA, lattice);
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, crawler);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, crawler);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);

        execute();

        assertCounterCount(playerA, crawler, CounterType.P1P1, 4);
    }
}
