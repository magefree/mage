package org.mage.test.cards.replacement;

import mage.constants.ManaType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ManaReflectionTest extends CardTestPlayerBase {

    @Test
    public void generatesCorrectManaFromMarwyn() {
        addCard(Zone.BATTLEFIELD, playerA, "Mana Reflection");
        addCard(Zone.BATTLEFIELD, playerA, "Marwyn, the Nurturer");
        addCard(Zone.BATTLEFIELD, playerA, "Upwelling"); // Prevent mana from emptying before we can check it

        addCounters(1, PhaseStep.UPKEEP, playerA, "Marwyn, the Nurturer", CounterType.P1P1, 2);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Marwyn, the Nurturer", 3, 3);
        assertManaPool(playerA, ManaType.GREEN, 6);
    }

    @Test
    public void generatesCorrectManaFromGemstoneCaverns() {
        addCard(Zone.BATTLEFIELD, playerA, "Mana Reflection");
        addCard(Zone.BATTLEFIELD, playerA, "Gemstone Caverns");
        addCard(Zone.BATTLEFIELD, playerA, "Upwelling"); // Prevent mana from emptying before we can check it

        addCounters(1, PhaseStep.UPKEEP, playerA, "Gemstone Caverns", CounterType.LUCK, 1);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add");
        setChoice(playerA, "Green");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertManaPool(playerA, ManaType.GREEN, 2);
    }

    @Test
    public void generatesCorrectManaFromLlanowarElves() {
        addCard(Zone.BATTLEFIELD, playerA, "Mana Reflection");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");
        addCard(Zone.BATTLEFIELD, playerA, "Upwelling"); // Prevent mana from emptying before we can check it

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertManaPool(playerA, ManaType.GREEN, 2);
    }
}
