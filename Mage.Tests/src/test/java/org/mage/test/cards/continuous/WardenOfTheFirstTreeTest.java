
package org.mage.test.cards.continuous;

import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class WardenOfTheFirstTreeTest extends CardTestPlayerBase {

    @Test
    public void testFirstAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        // {1}{W/B}: Warden of the First Tree becomes a Human Warrior with base power and toughness 3/3.
        // {2}{W/B}{W/B}: If Warden of the First Tree is a Warrior, it becomes a Human Spirit Warrior with trample and lifelink.
        // {3}{W/B}{W/B}{W/B}: If Warden of the First Tree is a Spirit, put five +1/+1 counters on it.
        addCard(Zone.HAND, playerA, "Warden of the First Tree", 2); // {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Warden of the First Tree", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W/B}:");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Warden of the First Tree", 3, 3, Filter.ComparisonScope.Any);
        assertType("Warden of the First Tree", CardType.CREATURE, SubType.HUMAN);
        assertType("Warden of the First Tree", CardType.CREATURE, SubType.WARRIOR);
        assertAbility(playerA, "Warden of the First Tree", TrampleAbility.getInstance(), false);
        assertAbility(playerA, "Warden of the First Tree", LifelinkAbility.getInstance(), false);
    }

    @Test
    public void testSecondAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        // {1}{W/B}: Warden of the First Tree becomes a Human Warrior with base power and toughness 3/3.
        // {2}{W/B}{W/B}: If Warden of the First Tree is a Warrior, it becomes a Human Spirit Warrior with trample and lifelink.
        // {3}{W/B}{W/B}{W/B}: If Warden of the First Tree is a Spirit, put five +1/+1 counters on it.
        addCard(Zone.HAND, playerA, "Warden of the First Tree", 2); // {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Warden of the First Tree", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W/B}:");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}{W/B}{W/B}:");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Warden of the First Tree", 3, 3, Filter.ComparisonScope.Any);
        assertType("Warden of the First Tree", CardType.CREATURE, SubType.HUMAN);
        assertType("Warden of the First Tree", CardType.CREATURE, SubType.SPIRIT);
        assertType("Warden of the First Tree", CardType.CREATURE, SubType.WARRIOR);
        assertAbility(playerA, "Warden of the First Tree", TrampleAbility.getInstance(), true);
        assertAbility(playerA, "Warden of the First Tree", LifelinkAbility.getInstance(), true);
    }

    @Test
    public void testThirdAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        // {1}{W/B}: Warden of the First Tree becomes a Human Warrior with base power and toughness 3/3.
        // {2}{W/B}{W/B}: If Warden of the First Tree is a Warrior, it becomes a Human Spirit Warrior with trample and lifelink.
        // {3}{W/B}{W/B}{W/B}: If Warden of the First Tree is a Spirit, put five +1/+1 counters on it.
        addCard(Zone.HAND, playerA, "Warden of the First Tree", 2); // {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Warden of the First Tree", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W/B}:");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}{W/B}{W/B}:");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}{W/B}{W/B}{W/B}:");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Warden of the First Tree", 8, 8, Filter.ComparisonScope.Any);
        assertType("Warden of the First Tree", CardType.CREATURE, SubType.HUMAN);
        assertType("Warden of the First Tree", CardType.CREATURE, SubType.SPIRIT);
        assertType("Warden of the First Tree", CardType.CREATURE, SubType.WARRIOR);
        assertAbility(playerA, "Warden of the First Tree", TrampleAbility.getInstance(), true);
        assertAbility(playerA, "Warden of the First Tree", LifelinkAbility.getInstance(), true);
    }

    /**
     * When a Warden of the First Tree enters the battlefield, if it is not the
     * first warden played during the game, it enters with a random
     * power/toughness instead of 1/1. I have had it enter with both 2/2 and
     * 4/4, neither of which are actual values the card can hold.
     */
    @Test
    public void testTwoWarden() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        // {1}{W/B}: Warden of the First Tree becomes a Human Warrior with base power and toughness 3/3.
        // {2}{W/B}{W/B}: If Warden of the First Tree is a Warrior, it becomes a Human Spirit Warrior with trample and lifelink.
        // {3}{W/B}{W/B}{W/B}: If Warden of the First Tree is a Spirit, put five +1/+1 counters on it.
        addCard(Zone.HAND, playerA, "Warden of the First Tree", 2); // {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Warden of the First Tree", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W/B}:");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}{W/B}{W/B}:");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Warden of the First Tree");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Warden of the First Tree", 1, 1, Filter.ComparisonScope.Any);
        assertPowerToughness(playerA, "Warden of the First Tree", 3, 3, Filter.ComparisonScope.Any);
    }

}
