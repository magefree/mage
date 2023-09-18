
package org.mage.test.cards.continuous;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class DaxosTheReturnedTest extends CardTestPlayerBase {

    /**
     * Daxos the Returned 's spirit tokens are not counted as enchantment tokens
     * and do not count towards experience counters like they should.
     */
    @Test
    public void testCounterAddAndTokenStates() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        // Whenever you cast an enchantment spell, you get an experience counter.
        // {1}{W}{B}: Create a white and black Spirit enchantment creature token onto the battlefield. It has
        // "This creature's power and toughness are each equal to the number of experience counters you have."
        addCard(Zone.BATTLEFIELD, playerA, "Daxos the Returned");

        // Whenever an opponent draws a card, Underworld Dreams deals 1 damage to that player.
        addCard(Zone.HAND, playerA, "Underworld Dreams", 2); // {B}{B}{B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Underworld Dreams", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Underworld Dreams", true);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{1}{W}{B}");
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 18);

        assertPermanentCount(playerA, "Underworld Dreams", 2);
        assertCounterCount(playerA, CounterType.EXPERIENCE, 2);
        assertPowerToughness(playerA, "Spirit Token", 2, 2, Filter.ComparisonScope.All);
        assertType("Spirit Token", CardType.ENCHANTMENT, SubType.SPIRIT);

    }

}
