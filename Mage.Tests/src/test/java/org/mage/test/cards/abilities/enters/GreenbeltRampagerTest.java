
package org.mage.test.cards.abilities.enters;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JRHerlehy
 */
public class GreenbeltRampagerTest extends CardTestPlayerBase {

    @Test
    public void testFirstCast() {
        addCard(Zone.HAND, playerA, "Greenbelt Rampager");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Greenbelt Rampager");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Greenbelt Rampager", 1);
        assertPermanentCount(playerA, "Greenbelt Rampager", 0);
        assertCounterCount(playerA, CounterType.ENERGY, 1);
    }

    @Test
    public void testScondCast() {
        addCard(Zone.HAND, playerA, "Greenbelt Rampager");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Greenbelt Rampager", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Greenbelt Rampager");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Greenbelt Rampager", 1);
        assertPermanentCount(playerA, "Greenbelt Rampager", 0);
        assertCounterCount(playerA, CounterType.ENERGY, 2);
    }

    @Test
    public void testThirdCast() {
        addCard(Zone.HAND, playerA, "Greenbelt Rampager");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Greenbelt Rampager", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Greenbelt Rampager", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Greenbelt Rampager");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Greenbelt Rampager", 0);
        assertPermanentCount(playerA, "Greenbelt Rampager", 1);
        assertCounterCount(playerA, CounterType.ENERGY, 0);
    }

    @Test
    public void testCastNotOwned() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        // Deathtouch
        // When Gonti, Lord of Luxury enters the battlefield, look at the top four cards of target opponent's library, exile one of them face down,
        // then put the rest on the bottom of that library in a random order. For as long as that card remains exiled,
        // you may look at it, you may cast it, and you may spend mana as though it were mana of any type to cast it.
        addCard(Zone.HAND, playerA, "Gonti, Lord of Luxury"); // Creature {2}{B}{B}

        // When Greenbelt Rampager enters the battlefield, pay {E}{E}. If you can't, return Greenbelt Rampager to its owner's hand and you get {E}.
        addCard(Zone.LIBRARY, playerB, "Greenbelt Rampager"); // Creature {G} 3/4

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gonti, Lord of Luxury", true);
        // addTarget(playerA, playerB); playerB is autochosen since only option
        setChoice(playerA, "Greenbelt Rampager");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Greenbelt Rampager");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, CounterType.ENERGY, 1);
        assertPermanentCount(playerA, "Greenbelt Rampager", 0);
        assertHandCount(playerA, "Greenbelt Rampager", 0);
        assertHandCount(playerB, "Greenbelt Rampager", 1);
    }
}
