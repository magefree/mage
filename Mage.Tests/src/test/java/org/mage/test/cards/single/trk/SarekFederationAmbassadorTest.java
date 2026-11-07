package org.mage.test.cards.single.trk;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class SarekFederationAmbassadorTest extends CardTestPlayerBase {

    // the first player skips the draw step of turn 1, so all hand cards of turn 1 are drawn by Sarek

    private void addFourteenCreatureTypes() {
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // Bear
        addCard(Zone.BATTLEFIELD, playerA, "Craw Wurm"); // Wurm
        addCard(Zone.BATTLEFIELD, playerA, "Savannah Lions"); // Cat
        addCard(Zone.BATTLEFIELD, playerA, "Copper Myr"); // Myr
        addCard(Zone.BATTLEFIELD, playerA, "Memnite"); // Construct
        addCard(Zone.BATTLEFIELD, playerA, "Hill Giant"); // Giant
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter"); // Thopter
        addCard(Zone.BATTLEFIELD, playerA, "Drudge Skeletons"); // Skeleton
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves"); // Elf Druid
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard"); // Human Soldier
    }

    @Test
    public void test_FourTypes_NoDraw() {
        // Federation - At the beginning of combat on your turn, draw a card for every five creature types among
        // non-Borg creatures you control. If you draw three or more cards this way, put two +1/+1 counters on each
        // creature you control.
        addCard(Zone.BATTLEFIELD, playerA, "Sarek, Federation Ambassador"); // Vulcan Advisor
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // Bear
        addCard(Zone.BATTLEFIELD, playerA, "Craw Wurm"); // Wurm
        //
        addCard(Zone.BATTLEFIELD, playerB, "Memnite"); // Construct, opponent's types don't count

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 0);
    }

    @Test
    public void test_FiveTypes_DrawOne_NoCounters() {
        // Federation - At the beginning of combat on your turn, draw a card for every five creature types among
        // non-Borg creatures you control. If you draw three or more cards this way, put two +1/+1 counters on each
        // creature you control.
        addCard(Zone.BATTLEFIELD, playerA, "Sarek, Federation Ambassador"); // Vulcan Advisor
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // Bear
        addCard(Zone.BATTLEFIELD, playerA, "Craw Wurm"); // Wurm
        addCard(Zone.BATTLEFIELD, playerA, "Memnite"); // Construct

        // turn 2 - the opponent's combat, no trigger
        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 1);
        assertCounterCount(playerA, "Sarek, Federation Ambassador", CounterType.P1P1, 0);
    }

    @Test
    public void test_FourteenTypes_DrawTwo_NoCounters() {
        // Federation - At the beginning of combat on your turn, draw a card for every five creature types among
        // non-Borg creatures you control. If you draw three or more cards this way, put two +1/+1 counters on each
        // creature you control.
        addCard(Zone.BATTLEFIELD, playerA, "Sarek, Federation Ambassador"); // Vulcan Advisor
        addFourteenCreatureTypes(); // + 12 types
        addCard(Zone.BATTLEFIELD, playerA, "Runeclaw Bear"); // Bear, same type

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 2);
        assertCounterCount(playerA, "Sarek, Federation Ambassador", CounterType.P1P1, 0);
    }

    @Test
    public void test_FifteenTypes_DrawThree_CountersOnEachCreature() {
        // Federation - At the beginning of combat on your turn, draw a card for every five creature types among
        // non-Borg creatures you control. If you draw three or more cards this way, put two +1/+1 counters on each
        // creature you control.
        addCard(Zone.BATTLEFIELD, playerA, "Sarek, Federation Ambassador"); // Vulcan Advisor, 0/5
        addFourteenCreatureTypes(); // + 12 types
        addCard(Zone.BATTLEFIELD, playerA, "Ambush Viper"); // Snake, 2/1
        //
        addCard(Zone.BATTLEFIELD, playerB, "Kraken Hatchling"); // 0/4

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 3);
        assertPowerToughness(playerA, "Sarek, Federation Ambassador", 0 + 2, 5 + 2);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 2, 2 + 2);
        assertPowerToughness(playerA, "Ambush Viper", 2 + 2, 1 + 2);
        assertPowerToughness(playerB, "Kraken Hatchling", 0, 4);
    }

    @Test
    public void test_BorgAndChangelingTypesNotCounted() {
        // Federation - At the beginning of combat on your turn, draw a card for every five creature types among
        // non-Borg creatures you control. If you draw three or more cards this way, put two +1/+1 counters on each
        // creature you control.
        addCard(Zone.BATTLEFIELD, playerA, "Sarek, Federation Ambassador"); // Vulcan Advisor
        addFourteenCreatureTypes(); // + 12 types
        //
        addCard(Zone.BATTLEFIELD, playerA, "Free Borg Revolutionaries"); // Borg Rebel
        // Changeling (This card is every creature type.)
        addCard(Zone.BATTLEFIELD, playerA, "Changeling Outcast"); // Shapeshifter + all types, so Borg too

        // 14 types - any type of Borg creatures would make it 15
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 2);
        assertCounterCount(playerA, "Sarek, Federation Ambassador", CounterType.P1P1, 0);
    }

    @Test
    public void test_DrawnLessThanThree_NoCounters() {
        // Federation - At the beginning of combat on your turn, draw a card for every five creature types among
        // non-Borg creatures you control. If you draw three or more cards this way, put two +1/+1 counters on each
        // creature you control.
        addCard(Zone.BATTLEFIELD, playerA, "Sarek, Federation Ambassador"); // Vulcan Advisor
        addFourteenCreatureTypes(); // + 12 types
        addCard(Zone.BATTLEFIELD, playerA, "Ambush Viper"); // Snake
        //
        // Each player can't draw more than one card each turn.
        addCard(Zone.BATTLEFIELD, playerB, "Spirit of the Labyrinth");

        // 15 types, but only one card drawn this way
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 1);
        assertCounterCount(playerA, "Sarek, Federation Ambassador", CounterType.P1P1, 0);
    }
}
