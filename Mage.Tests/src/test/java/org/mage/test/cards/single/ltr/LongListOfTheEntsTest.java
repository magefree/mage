package org.mage.test.cards.single.ltr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class LongListOfTheEntsTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.l.LongListOfTheEnts Long List of the Ents} {G}
     * Enchantment — Saga
     * (As this Saga enters and after your draw step, add a lore counter. Sacrifice after VI.)
     * I, II, III, IV, V, VI — Note a creature type that hasn’t been noted for this Saga. When you next cast a creature spell of that type this turn, that creature enters with an additional +1/+1 counter on it.
     */
    private static final String list = "Long List of the Ents";

    @Test
    public void test_SimplePlay() {
        addCard(Zone.HAND, playerA, list, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, "Memnite", 1); // Construct
        addCard(Zone.HAND, playerA, "Augmenting Automaton", 1); // Construct
        addCard(Zone.HAND, playerA, "Grizzly Bears", 1); // Bear
        addCard(Zone.HAND, playerA, "Bear Cub", 1); // Bear
        addCard(Zone.HAND, playerA, "Woodland Changeling", 1); // Changeling
        addCard(Zone.HAND, playerA, "Llanowar Elves", 1); // Elf Druid
        addCard(Zone.HAND, playerA, "Balduvian Bears", 1); // Bear
        addCard(Zone.HAND, playerA, "Centaur Courser", 1); // Centaur Warrior

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, list);
        setChoice(playerA, "Construct");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite", true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Augmenting Automaton");

        // turn 3
        setChoice(playerA, "Bear");
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears");

        // turn 5
        setChoice(playerA, "Warrior");
        castSpell(5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Bear Cub");

        // turn 7
        setChoice(playerA, "Centaur");
        castSpell(7, PhaseStep.POSTCOMBAT_MAIN, playerA, "Centaur Courser");

        // turn 9
        setChoice(playerA, "Druid");
        castSpell(9, PhaseStep.POSTCOMBAT_MAIN, playerA, "Balduvian Bears", true);
        castSpell(9, PhaseStep.POSTCOMBAT_MAIN, playerA, "Llanowar Elves");

        // turn 11
        setChoice(playerA, "Elf");
        castSpell(11, PhaseStep.POSTCOMBAT_MAIN, playerA, "Woodland Changeling");

        setStrictChooseMode(true);
        setStopAt(12, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Memnite", CounterType.P1P1, 1);
        assertCounterCount(playerA, "Augmenting Automaton", CounterType.P1P1, 0);
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 1);
        assertCounterCount(playerA, "Bear Cub", CounterType.P1P1, 0);
        assertCounterCount(playerA, "Centaur Courser", CounterType.P1P1, 1);
        assertCounterCount(playerA, "Balduvian Bears", CounterType.P1P1, 0);
        assertCounterCount(playerA, "Llanowar Elves", CounterType.P1P1, 1);
        assertCounterCount(playerA, "Woodland Changeling", CounterType.P1P1, 1);
    }
}
