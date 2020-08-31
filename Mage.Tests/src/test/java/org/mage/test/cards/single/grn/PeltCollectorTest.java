package org.mage.test.cards.single.grn;

import mage.abilities.keyword.TrampleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class PeltCollectorTest extends CardTestPlayerBase {

    @Test
    public void test_Simple() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Whenever another creature you control enters the battlefield or dies, if that creature's power is greater than Pelt Collector's, put a +1/+1 counter on Pelt Collector.
        // As long as Pelt Collector has three or more +1/+1 counters on it, it has trample.        
        addCard(Zone.HAND, playerA, "Pelt Collector", 1); // Creature {G}
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1); // Creature {1}{W}

        addCard(Zone.BATTLEFIELD, playerB, "Pelt Collector", 1);// Creature {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pelt Collector");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertAllCommandsUsed();

        assertPowerToughness(playerB, "Pelt Collector", 1, 1);

        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2);
        assertPowerToughness(playerA, "Pelt Collector", 2, 2);
        assertAbility(playerA, "Pelt Collector", TrampleAbility.getInstance(), false);
        assertAbility(playerB, "Pelt Collector", TrampleAbility.getInstance(), false);

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
        addCard(Zone.HAND, playerA, "Pelt Collector", 1); // Creature {G}
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1); // Creature {1}{W}
        // Other creatures you control get +1/+1.
        // When Trostani Discordant enters the battlefield, create two 1/1 white Soldier creature tokens with lifelink.
        // At the beginning of your end step, each player gains control of all creatures they own.
        addCard(Zone.HAND, playerA, "Trostani Discordant", 1); // Creature {3}{G}{W} /1/4)
        
        addCard(Zone.BATTLEFIELD, playerB, "Pelt Collector", 1);// Creature {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Trostani Discordant");
        
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Pelt Collector");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);

        execute();

        assertAllCommandsUsed();

        assertPowerToughness(playerB, "Pelt Collector", 1, 1);

        assertPowerToughness(playerA, "Soldier", 2, 2, Filter.ComparisonScope.All);
        
        assertPowerToughness(playerA, "Silvercoat Lion", 3, 3);
        assertPowerToughness(playerA, "Pelt Collector", 3, 3);
        assertAbility(playerA, "Pelt Collector", TrampleAbility.getInstance(), false);
        assertAbility(playerB, "Pelt Collector", TrampleAbility.getInstance(), false);

    }    
}
