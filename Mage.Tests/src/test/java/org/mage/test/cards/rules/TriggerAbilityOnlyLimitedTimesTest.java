

package org.mage.test.cards.rules;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author markort147
 */

public class TriggerAbilityOnlyLimitedTimesTest extends CardTestPlayerBase {

    /**
     * Enduring Innocence   {1}{W}{W}
     * Enchantment Creature - Sheep Glimmer
     * 2/1
     * Lifelink
     * Whenever one or more other creatures you control with power 2 or less enter, draw a card. This ability triggers only once each turn.
     * When Enduring Innocence dies, if it was a creature, return it to the battlefield under its owner's control. It's an enchantment. (It's not a creature.)
     */
    @Test
    public void testTriggerOnceEachTurn() {
        addCard(Zone.HAND, playerA, "Llanowar Elves", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Enduring Innocence", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Llanowar Elves", true); // Draw a card
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Llanowar Elves", true); // Do not draw a card

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 1);
    }

    /**
     * Momentary Blink {1}{W}
     * Instant
     * Exile target creature you control, then return it to the battlefield under its owner's control.
     * Flashback (You may cast this card from your graveyard for its flashback cost. Then exile it.)
     */
    @Test
    public void testTriggerTwiceSameTurnIfBlinked() {

        addCard(Zone.HAND, playerA, "Llanowar Elves", 2);
        addCard(Zone.HAND, playerA, "Momentary Blink");
        addCard(Zone.BATTLEFIELD, playerA, "Enduring Innocence", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Llanowar Elves", true); // Draw a card

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Momentary Blink", "Enduring Innocence", true); // Blink

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Llanowar Elves", true); // Draw a card again

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 2);
        assertPermanentCount(playerA, "Enduring Innocence", 1);
    }

    /**
     * Acrobatic Cheerleader   {1}{W}
     * Creature - Human Survivor
     * 2/2
     * Survival — At the beginning of your second main phase, if Acrobatic Cheerleader is tapped, put a flying counter on it. This ability triggers only once.
     */
    @Test
    public void testTriggerOnceEachGame() {
        addCard(Zone.BATTLEFIELD, playerA, "Acrobatic Cheerleader", 1);

        attack(3, playerA, "Acrobatic Cheerleader", playerB); // Put a flying counter
        attack(5, playerA, "Acrobatic Cheerleader", playerB); // Do not put another flying counter

        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Acrobatic Cheerleader", CounterType.FLYING, 1);
    }

    /**
     * Momentary Blink {1}{W}
     * Instant
     * Exile target creature you control, then return it to the battlefield under its owner's control.
     * Flashback (You may cast this card from your graveyard for its flashback cost. Then exile it.)
     */
    @Test
    public void testTriggerTwiceSameGameIfBlinked() {
        addCard(Zone.BATTLEFIELD, playerA, "Acrobatic Cheerleader", 1);
        addCard(Zone.HAND, playerA, "Momentary Blink");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        attack(3, playerA, "Acrobatic Cheerleader", playerB); // Put a flying counter
        castSpell(3, PhaseStep.END_TURN, playerA, "Momentary Blink", "Acrobatic Cheerleader"); // Blinks and loses the counter
        attack(5, playerA, "Acrobatic Cheerleader", playerB); // Put a flying counter

        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Acrobatic Cheerleader", CounterType.FLYING, 1);
    }
}