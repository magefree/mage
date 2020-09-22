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

    private static final String pltclctr = "Pelt Collector";
    private static final String slvrctln = "Silvercoat Lion";
    private static final String trstn = "Trostani Discordant";
    private static final String grzlybrs = "Grizzly Bears";
    private static final String mrdr = "Murder";
    private static final String cntrcrsr = "Centaur Courser";
    private static final String gntgrwth = "Giant Growth";

    @Test
    public void test_Simple() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Whenever another creature you control enters the battlefield or dies, if that creature's power is greater than Pelt Collector's, put a +1/+1 counter on Pelt Collector.
        // As long as Pelt Collector has three or more +1/+1 counters on it, it has trample.        
        addCard(Zone.HAND, playerA, pltclctr, 1); // Creature {G}
        addCard(Zone.HAND, playerA, slvrctln, 1); // Creature {1}{W}

        addCard(Zone.BATTLEFIELD, playerB, pltclctr, 1);// Creature {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, pltclctr);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, slvrctln);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertAllCommandsUsed();

        assertPowerToughness(playerB, pltclctr, 1, 1);

        assertPowerToughness(playerA, slvrctln, 2, 2);
        assertPowerToughness(playerA, pltclctr, 2, 2);
        assertAbility(playerA, pltclctr, TrampleAbility.getInstance(), false);
        assertAbility(playerB, pltclctr, TrampleAbility.getInstance(), false);
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
        addCard(Zone.HAND, playerA, pltclctr, 1); // Creature {G}
        addCard(Zone.HAND, playerA, slvrctln, 1); // Creature {1}{W}
        // Other creatures you control get +1/+1.
        // When Trostani Discordant enters the battlefield, create two 1/1 white Soldier creature tokens with lifelink.
        // At the beginning of your end step, each player gains control of all creatures they own.
        addCard(Zone.HAND, playerA, trstn, 1); // Creature {3}{G}{W} /1/4)

        addCard(Zone.BATTLEFIELD, playerB, pltclctr, 1);// Creature {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, trstn);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, pltclctr);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, slvrctln);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);

        execute();

        assertAllCommandsUsed();

        assertPowerToughness(playerB, pltclctr, 1, 1);

        assertPowerToughness(playerA, "Soldier", 2, 2, Filter.ComparisonScope.All);

        assertPowerToughness(playerA, slvrctln, 3, 3);
        assertPowerToughness(playerA, pltclctr, 3, 3);
        assertAbility(playerA, pltclctr, TrampleAbility.getInstance(), false);
        assertAbility(playerB, pltclctr, TrampleAbility.getInstance(), false);

    }


    @Test
    public void testEntersTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, pltclctr);
        addCard(Zone.HAND, playerA, grzlybrs);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, grzlybrs);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAllCommandsUsed();

        assertPowerToughness(playerA, pltclctr, 2, 2);
        assertCounterCount(pltclctr, CounterType.P1P1, 1);
    }

    @Test
    public void testDiesTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, pltclctr);
        addCard(Zone.HAND, playerA, grzlybrs);
        addCard(Zone.HAND, playerA, mrdr);
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, grzlybrs);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, mrdr, grzlybrs);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAllCommandsUsed();

        assertPowerToughness(playerA, pltclctr, 2, 2);
        assertCounterCount(pltclctr, CounterType.P1P1, 1);
    }

    @Test
    public void testDiesTrigger2() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, pltclctr);
        addCard(Zone.HAND, playerA, cntrcrsr);
        addCard(Zone.HAND, playerA, mrdr);
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cntrcrsr);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, mrdr, cntrcrsr);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAllCommandsUsed();

        assertPowerToughness(playerA, pltclctr, 3, 3);
        assertCounterCount(pltclctr, CounterType.P1P1, 2);
    }

    @Test
    public void testInterveningIf() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, pltclctr);
        addCard(Zone.HAND, playerA, grzlybrs);
        addCard(Zone.HAND, playerA, gntgrwth);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, grzlybrs);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, gntgrwth, pltclctr);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAllCommandsUsed();

        assertPowerToughness(playerA, pltclctr, 4, 4);
        assertCounterCount(pltclctr, CounterType.P1P1, 0);
    }

    @Test
    public void testInterveningIf2() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, pltclctr);
        addCard(Zone.HAND, playerA, grzlybrs);
        addCard(Zone.HAND, playerA, "Scar");
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, grzlybrs);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scar", grzlybrs);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAllCommandsUsed();

        assertPowerToughness(playerA, pltclctr, 1, 1);
        assertCounterCount(pltclctr, CounterType.P1P1, 0);
    }
}
