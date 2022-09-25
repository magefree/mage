

package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class ChorusOfTheConclaveTest extends CardTestPlayerBase {

    /** 
     * Chorus of the Conclave
     * 4GGWW
     * Legendary Creature -- Dryad
     * 3/8
     * Forestwalk
     * As an additional cost to cast creature spells, you may pay any amount of 
     * mana. If you do, that creature enters the battlefield with that many 
     * additional +1/+1 counters on it.
     *
     */

    @Test
    public void testPlayCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Chorus of the Conclave");
        addCard(Zone.HAND, playerA, "Goblin Roughrider");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Roughrider");
        setChoice(playerA, true);
        setChoice(playerA, "X=1");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Goblin Roughrider", 1);
        assertCounterCount("Goblin Roughrider", CounterType.P1P1, 1);

    }

    /*
        Scenario: I have both Hamza, Guardian of Arashin and Chorus of the Conclave on the board, as well as a bunch of creature's with +1/+1 counters. 
        Hamza doesn't reduce the mana I want to pay extra with Chorus based on the amount of creatures with counters.
        I'm unsure if this is due to Hamza not properly reducing additional costs, or Chorus not properly adding the cost to the creature in a way that Hamza can reduce it.
    */
    @Test
    public void testWithHamza() {
        setStrictChooseMode(true);
        
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, "Mountain");
        
        // As an additional cost to cast creature spells, you may pay any amount of mana.
        // If you do, that creature enters the battlefield with that many additional +1/+1 counters on it.        
        addCard(Zone.BATTLEFIELD, playerA, "Chorus of the Conclave");
        
        addCard(Zone.HAND, playerA, "Goblin Roughrider",1); // Creature {2}{R}
        addCard(Zone.HAND, playerA, "Akki Drillmaster",1); // Creature {2}{R}

        // This spell costs {1} less to cast for each creature you control with a +1/+1 counter on it.
        // Creature spells you cast cost {1} less to cast for each creature you control with a +1/+1 counter on it.        
        addCard(Zone.HAND, playerA, "Hamza, Guardian of Arashin"); // {4}{G}{W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Roughrider", true);
        setChoice(playerA, true);
        setChoice(playerA, "X=1");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hamza, Guardian of Arashin");
        setChoice(playerA, true);
        setChoice(playerA, "X=1");
        
        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Mountain");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Akki Drillmaster");
        setChoice(playerA, true);
        setChoice(playerA, "X=1");
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Goblin Roughrider", 1); // costs {R}{2} + {1} = 4
        assertCounterCount("Goblin Roughrider", CounterType.P1P1, 1);

        assertPermanentCount(playerA, "Hamza, Guardian of Arashin", 1); // costs {G}{W}{3} + {1} = 6
        assertCounterCount("Hamza, Guardian of Arashin", CounterType.P1P1, 1);

        assertPermanentCount(playerA, "Akki Drillmaster", 1);// costs {R} + {1} = 2
        assertCounterCount("Akki Drillmaster", CounterType.P1P1, 1);

    }    

}
