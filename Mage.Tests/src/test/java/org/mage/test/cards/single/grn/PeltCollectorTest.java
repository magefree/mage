package org.mage.test.cards.single.grn;

import mage.abilities.keyword.TrampleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2, TheElk801
 */
public class PeltCollectorTest extends CardTestPlayerBase {

    private static final String collector = "Pelt Collector";
    private static final String lion = "Silvercoat Lion";
    private static final String trostani = "Trostani Discordant";
    private static final String bear = "Grizzly Bears";
    private static final String murder = "Murder";
    private static final String courser = "Centaur Courser";
    private static final String growth = "Giant Growth";
    private static final String karstoderm = "Karstoderm";

    @Test
    public void test_Simple() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Whenever another creature you control enters the battlefield or dies, if that creature's power is greater than Pelt Collector's, put a +1/+1 counter on Pelt Collector.
        // As long as Pelt Collector has three or more +1/+1 counters on it, it has trample.        
        addCard(Zone.HAND, playerA, collector, 1); // Creature {G}
        addCard(Zone.HAND, playerA, lion, 1); // Creature {1}{W}

        addCard(Zone.BATTLEFIELD, playerB, collector, 1);// Creature {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, collector);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lion);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPowerToughness(playerB, collector, 1, 1);

        assertPowerToughness(playerA, lion, 2, 2);
        assertPowerToughness(playerA, collector, 2, 2);
        assertAbility(playerA, collector, TrampleAbility.getInstance(), false);
        assertAbility(playerB, collector, TrampleAbility.getInstance(), false);
    }

    /**
     * To determine if Pelt Collector’s first ability triggers when a creature
     * enters the battlefield, use the creature’s power after applying any
     * static abilities (such as that of Trostani Discordant) that modify its
     * power.
     */
    @Test
    public void test_TrostaniDiscordant() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        // Whenever another creature you control enters the battlefield or dies, if that creature's power is greater than Pelt Collector's, put a +1/+1 counter on Pelt Collector.
        // As long as Pelt Collector has three or more +1/+1 counters on it, it has trample.        
        addCard(Zone.HAND, playerA, collector, 1); // Creature {G}
        addCard(Zone.HAND, playerA, lion, 1); // Creature {1}{W}
        // Other creatures you control get +1/+1.
        // When Trostani Discordant enters the battlefield, create two 1/1 white Soldier creature tokens with lifelink.
        // At the beginning of your end step, each player gains control of all creatures they own.
        addCard(Zone.HAND, playerA, trostani, 1); // Creature {3}{G}{W} /1/4)

        addCard(Zone.BATTLEFIELD, playerB, collector, 1);// Creature {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, trostani);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, collector);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, lion);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPowerToughness(playerB, collector, 1, 1);

        assertPowerToughness(playerA, "Soldier Token", 2, 2, Filter.ComparisonScope.All);

        assertPowerToughness(playerA, lion, 3, 3);
        assertPowerToughness(playerA, collector, 3, 3);
        assertAbility(playerA, collector, TrampleAbility.getInstance(), false);
        assertAbility(playerB, collector, TrampleAbility.getInstance(), false);

    }


    @Test
    public void testEntersTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, collector);
        addCard(Zone.HAND, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bear);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, collector, 2, 2);
        assertCounterCount(collector, CounterType.P1P1, 1);
    }

    @Test
    public void testEntersTrigger2() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, collector);
        addCard(Zone.HAND, playerA, karstoderm);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, karstoderm);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, collector, 2, 2);
        assertCounterCount(collector, CounterType.P1P1, 1);
    }

    @Test
    public void testDiesTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, collector);
        addCard(Zone.HAND, playerA, bear);
        addCard(Zone.HAND, playerA, murder);
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bear);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, murder, bear);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, collector, 2, 2);
        assertCounterCount(collector, CounterType.P1P1, 1);
    }

    @Test
    public void testDiesTrigger2() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, collector);
        addCard(Zone.HAND, playerA, courser);
        addCard(Zone.HAND, playerA, murder);
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, courser);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, murder, courser);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, collector, 3, 3);
        assertCounterCount(collector, CounterType.P1P1, 2);
    }

    @Test
    public void testDiesTrigger3() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, collector);
        addCard(Zone.HAND, playerA, karstoderm);
        addCard(Zone.HAND, playerA, murder);
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 7);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, karstoderm);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, murder, karstoderm);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, collector, 3, 3);
        assertCounterCount(collector, CounterType.P1P1, 2);
    }

    @Test
    public void testInterveningIf() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, collector);
        addCard(Zone.HAND, playerA, bear);
        addCard(Zone.HAND, playerA, growth);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bear);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, growth, collector);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, collector, 4, 4);
        assertCounterCount(collector, CounterType.P1P1, 0);
    }

    @Test
    public void testInterveningIf2() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, collector);
        addCard(Zone.HAND, playerA, bear);
        addCard(Zone.HAND, playerA, "Scar");
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bear);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scar", bear);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, collector, 1, 1);
        assertCounterCount(collector, CounterType.P1P1, 0);
    }
}
