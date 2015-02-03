package org.mage.test.cards.cost.sacrifice;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class MomentousFallTest extends CardTestPlayerBase {

    /**
     *  Momentous Fall
     *  Instant {2}{G}{G}
     *  As an additional cost to cast Momentous Fall, sacrifice a creature.
     *  You draw cards equal to the sacrificed creature's power, then you
     *  gain life equal to its toughness.
     */
    @Test
    public void testSacrificeCostAndLKI() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Momentous Fall");

        // Geralf's Messenger enters the battlefield tapped.
        // When Geralf's Messenger enters the battlefield, target opponent loses 2 life.
        // Undying (When this creature dies, if it had no +1/+1 counters on it, return it to the battlefield under its owner's control with a +1/+1 counter on it.)
        addCard(Zone.BATTLEFIELD, playerA, "Geralf's Messenger", 1);

        // Creatures you control get +1/+1.
        addCard(Zone.BATTLEFIELD, playerA, "Glorious Anthem", 1);

        // As an additional cost to cast Momentous Fall, sacrifice a creature.
        // You draw cards equal to the sacrificed creature's power, then you gain life equal to its toughness.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Momentous Fall");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Geralf's Messenger", 1);
        assertCounterCount("Geralf's Messenger", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Geralf's Messenger", 5, 4); // +1/+1 counter + Anthem effect
        assertHandCount(playerA, 4); // +4 cards
        assertLife(playerA, 23); // +3 life
    }

    @Test
    public void testSacrificeCostForProGreen() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Momentous Fall");
        addCard(Zone.BATTLEFIELD, playerA, "Mirran Crusader");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Momentous Fall");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Mirran Crusader", 0);
        assertHandCount(playerA, 2); // +2 cards
        assertLife(playerA, 22); // +2 life
    }


}
