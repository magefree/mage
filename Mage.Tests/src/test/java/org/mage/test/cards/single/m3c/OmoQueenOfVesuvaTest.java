package org.mage.test.cards.single.m3c;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class OmoQueenOfVesuvaTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.o.OmoQueenOfVesuva Omo, Queen of Vesuva} {2}{G/U}
     * Legendary Creature — Shapeshifter Noble
     * Whenever Omo, Queen of Vesuva enters the battlefield or attacks, put an everything counter on each of up to one target land and up to one target creature.
     * Each land with an everything counter on it is every land type in addition to its other types.
     * Each nonland creature with an everything counter on it is every creature type.
     * 1/5
     */
    private static final String omo = "Omo, Queen of Vesuva";

    @Test
    public void test_TronLand() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Urza's Tower");
        addCard(Zone.HAND, playerA, omo);
        addCard(Zone.HAND, playerA, "Abzan Banner");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, omo, true);
        addTarget(playerA, "Forest");
        addTarget(playerA, TestPlayer.TARGET_SKIP);

        // Urza's Tower makes {3} mana
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Abzan Banner");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Abzan Banner", 1);
    }

    @Test
    public void test_IsForest() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Coiling Woodworm", 1); // Coiling Woodworm’s power is equal to the number of Forests on the battlefield.
        addCard(Zone.HAND, playerA, omo);
        addCard(Zone.HAND, playerA, "Llanowar Elves");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, omo);
        addTarget(playerA, "Island");
        addTarget(playerA, TestPlayer.TARGET_SKIP);

        setChoice(playerA, "Green"); // mana color to cast Llanowar Elves
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Llanowar Elves");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Coiling Woodworm", 1, 1);
        assertPermanentCount(playerA, "Llanowar Elves", 1);
    }

    @Test
    public void test_EveryCreatureSubType() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Coiling Woodworm", 1); // Coiling Woodworm’s power is equal to the number of Forests on the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Imperious Perfect", 1); // Other Elves you control get +1/+1.
        addCard(Zone.HAND, playerA, omo);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, omo, true);
        addTarget(playerA, "Island");
        addTarget(playerA, "Coiling Woodworm");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, "Coiling Woodworm", CounterType.EVERYTHING, 1);
        assertPowerToughness(playerA, "Coiling Woodworm", 2, 2); // 1 Forest, and is an Elf
    }
}
