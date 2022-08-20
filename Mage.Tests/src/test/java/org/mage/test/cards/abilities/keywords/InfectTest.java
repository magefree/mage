
package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.InfectAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class InfectTest extends CardTestPlayerBase {

    /**
     *
     * 702.89. Infect 702.89a Infect is a static ability. 702.89b Damage dealt
     * to a player by a source with infect doesn't cause that player to lose
     * life. Rather, it causes the player to get that many poison counters. See
     * rule 119.3. 702.89c Damage dealt to a creature by a source with infect
     * isn't marked on that creature. Rather, it causes that many -1/-1 counters
     * to be put on that creature. See rule 119.3. 702.89d If a permanent leaves
     * the battlefield before an effect causes it to deal damage, its last known
     * information is used to determine whether it had infect. 702.89e The
     * infect rules function no matter what zone an object with infect deals
     * damage from. 702.89f Multiple instances of infect on the same object are
     * redundant.
     *
     */
    @Test
    public void testNormalUse() {
        addCard(Zone.BATTLEFIELD, playerB, "Tine Shrike"); // 2/1 Infect

        attack(2, playerB, "Tine Shrike");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, CounterType.POISON, 2);

        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }

    @Test
    public void testLoseInfectUse() {
        // Creatures your opponents control lose infect.
        addCard(Zone.BATTLEFIELD, playerA, "Melira, Sylvok Outcast");

        addCard(Zone.BATTLEFIELD, playerB, "Tine Shrike"); // 2/1 Infect

        attack(2, playerB, "Tine Shrike");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, CounterType.POISON, 0);

        assertLife(playerA, 18);
        assertLife(playerB, 20);

    }

    /**
     * Inkmoth Nexus has no effect it he attacks becaus it has infect but there
     * are no counters added
     * http://www.mtgsalvation.com/forums/magic-fundamentals/magic-rulings/magic-rulings-archives/296553-melira-sylvok-outcast-vs-inkmoth-nexus
     */
    @Test
    public void testInkmothNexusLoseInfect() {
        // Creatures your opponents control lose infect.
        // Creatures you control can't have -1/-1 counters placed on them.
        addCard(Zone.BATTLEFIELD, playerA, "Melira, Sylvok Outcast");
        // Put a -1/-1 counter on target creature. When that creature dies this turn, its controller gets a poison counter.
        addCard(Zone.HAND, playerA, "Virulent Wound"); // Instant {B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Inkmoth Nexus");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Virulent Wound", "Melira, Sylvok Outcast");
        // {1}: Inkmoth Nexus becomes a 1/1 Blinkmoth artifact creature with flying and infect until end of turn. It's still a land.
        // (It deals damage to creatures in the form of -1/-1 counters and to players in the form of poison counters.)
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{1}: {this} becomes");
        attack(2, playerB, "Inkmoth Nexus");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Virulent Wound", 1);
        assertPowerToughness(playerA, "Melira, Sylvok Outcast", 2, 2);
        assertTapped("Plains", true);
        assertTapped("Inkmoth Nexus", true);
        assertCounterCount(playerA, CounterType.POISON, 0);

        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }
    
    @Test
    public void testInkmothPumpedByBecomeImmense1() {
        
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);
        // {1}: Inkmoth Nexus becomes a 1/1 Blinkmoth artifact creature with flying and infect until end of turn. It's still a land.
        // (It deals damage to creatures in the form of -1/-1 counters and to players in the form of poison counters.)
        addCard(Zone.BATTLEFIELD, playerA, "Inkmoth Nexus");
        
        // Become Immense - {5}{G} - Instant
        // Delve (Each card you exile from your graveyard while casting this spell pays for 1.)
        // Target creature gets +6/+6 until end of turn.
        addCard(Zone.HAND, playerA, "Become Immense", 1);

        setStrictChooseMode(true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}: {this} becomes");
        attack(1, playerA, "Inkmoth Nexus");
        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerA, "Become Immense");
        addTarget(playerA, "Inkmoth Nexus");
        
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();
        
        assertTapped("Inkmoth Nexus", true);
        assertGraveyardCount(playerA, "Become Immense", 1);
        assertCounterCount(playerB, CounterType.POISON, 7);
    }
    
        @Test
    public void testInkmothPumpedByBecomeImmense2() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);
        // {1}: Inkmoth Nexus becomes a 1/1 Blinkmoth artifact creature with flying and infect until end of turn. It's still a land.
        // (It deals damage to creatures in the form of -1/-1 counters and to players in the form of poison counters.)
        addCard(Zone.BATTLEFIELD, playerA, "Inkmoth Nexus");
        
        // Become Immense - {5}{G} - Instant
        // Delve (Each card you exile from your graveyard while casting this spell pays for 1.)
        // Target creature gets +6/+6 until end of turn.
        addCard(Zone.HAND, playerA, "Become Immense", 1);        
        addCard(Zone.HAND, playerA, "Mutagenic Growth", 1); // {G} instant +2+2
        addCard(Zone.HAND, playerA, "Might of Old Krosa", 1); // {G} instant +2+2
        
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}: {this} becomes");
        attack(1, playerA, "Inkmoth Nexus");
        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerA, "Mutagenic Growth");
        // Inkmoth Nexus is auto-chosen since it's the only possible target
        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerA, "Might of Old Krosa");
        // Inkmoth Nexus is auto-chosen since it's the only possible target
        // +5 poison
        
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}: {this} becomes");
        attack(3, playerA, "Inkmoth Nexus");
        castSpell(3, PhaseStep.DECLARE_ATTACKERS, playerA, "Become Immense");
        // Inkmoth Nexus is auto-chosen since it's the only possible target
        // +7 poison
        
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();
        
        assertTapped("Inkmoth Nexus", true);
        assertGraveyardCount(playerA, "Become Immense", 1);
        assertGraveyardCount(playerA, "Mutagenic Growth", 1);
        assertGraveyardCount(playerA, "Might of Old Krosa", 1);
        assertCounterCount(playerB, CounterType.POISON, 12);
    }

    /**
     * Phyrexian Obliterator is enchanted with Corrupted Conscience and Enslave
     *
     * on upkeep Phyrexian Obliterator does 1 damage to its owner but this
     * damage was NOT infect damage and it should have been
     */
    @Test
    public void GainedInfectByEnchantment() {
        // Trample
        // Whenever a source deals damage to Phyrexian Obliterator, that source's controller sacrifices that many permanents.
        addCard(Zone.BATTLEFIELD, playerB, "Phyrexian Obliterator");

        // Enchant creature
        // You control enchanted creature.
        // Enchanted creature has infect. (It deals damage to creatures in the form of -1/-1 counters and to players in the form of poison counters.)
        addCard(Zone.HAND, playerA, "Corrupted Conscience"); // Enchantment {3}{U}{U}
        // Enchant creature
        // You control enchanted creature.
        // At the beginning of your upkeep, enchanted creature deals 1 damage to its owner.
        addCard(Zone.HAND, playerA, "Enslave"); // Enchantment {4}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 9);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Corrupted Conscience", "Phyrexian Obliterator");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Enslave", "Phyrexian Obliterator");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Phyrexian Obliterator", 1);
        assertPermanentCount(playerA, "Corrupted Conscience", 1);
        assertPermanentCount(playerA, "Enslave", 1);

        assertAbility(playerA, "Phyrexian Obliterator", InfectAbility.getInstance(), true);

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertCounterCount(playerB, CounterType.POISON, 1);

    }

}
