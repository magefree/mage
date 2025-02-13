package org.mage.test.combat;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mage.test.player.TestPlayer.CHOICE_SKIP;

/**
 * Test restrictions for choosing attackers and blockers.
 *
 * @author noxx, JayDi85
 */
public class AttackBlockRestrictionsTest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void testFlyingVsNonFlying() {
        addCard(Zone.BATTLEFIELD, playerA, "Captain of the Mists");
        addCard(Zone.BATTLEFIELD, playerB, "Mist Raven");

        attack(2, playerB, "Mist Raven");
        block(2, playerA, "Captain of the Mists", "Mist Raven");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 20);

        Permanent mistRaven = getPermanent("Mist Raven", playerB.getId());
        Assert.assertTrue("Should be tapped because of attacking", mistRaven.isTapped());
    }

    /**
     * Tests attacking creature doesn't untap after being blocked by Wall of
     * Frost
     */
    @Test
    public void testWallofFrost() {
        // Whenever Wall of Frost blocks a creature, that creature doesn't untap during its controller's next untap step.
        addCard(Zone.BATTLEFIELD, playerA, "Wall of Frost"); // 0/7
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm");

        attack(2, playerB, "Craw Wurm");
        block(2, playerA, "Wall of Frost", "Craw Wurm");

        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        Permanent crawWurm = getPermanent("Craw Wurm", playerB.getId());
        Assert.assertEquals("Should be tapped because of being blocked by Wall of Frost", true, crawWurm.isTapped());
    }

    /**
     * Tests card that says that it can't block specific cards Hunted Ghoul:
     * Hunted Ghoul can't block Humans.
     */
    @Test
    public void testFilteredBlocking() {
        addCard(Zone.BATTLEFIELD, playerA, "Hunted Ghoul");
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard");

        attack(2, playerB, "Elite Vanguard");
        block(2, playerA, "Hunted Ghoul", "Elite Vanguard");

        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 20);
    }

    /**
     * Tests card that says that it can't block specific cards still can block
     * others Hunted Ghoul: Hunted Ghoul can't block Humans.
     */
    @Test
    public void testFilteredBlocking2() {
        addCard(Zone.BATTLEFIELD, playerA, "Hunted Ghoul");
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm");

        attack(2, playerB, "Craw Wurm");
        block(2, playerA, "Hunted Ghoul", "Craw Wurm");

        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Hunted Ghoul", 0);
    }

    /**
     * Tests "Creatures with flying can't block creatures you control"
     */
    @Test
    public void testBowerPassage() {
        addCard(Zone.BATTLEFIELD, playerB, "Bower Passage");

        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerB, "Arbor Elf");
        addCard(Zone.BATTLEFIELD, playerB, "Assault Griffin");
        addCard(Zone.BATTLEFIELD, playerB, "Sky Ruin Drake");

        addCard(Zone.BATTLEFIELD, playerA, "Angelic Wall");
        addCard(Zone.BATTLEFIELD, playerA, "Air Elemental");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");
        addCard(Zone.BATTLEFIELD, playerA, "Sentinel Spider");

        // attacker vs. blocker:
        // non flying vs. flying
        attack(2, playerB, "Elite Vanguard");
        block(2, playerA, "Angelic Wall", "Elite Vanguard");
        // non flying vs. non flying
        attack(2, playerB, "Arbor Elf");
        block(2, playerA, "Llanowar Elves", "Arbor Elf");
        // flying vs. flying
        attack(2, playerB, "Assault Griffin");
        block(2, playerA, "Air Elemental", "Assault Griffin");
        // flying vs. reach
        attack(2, playerB, "Sky Ruin Drake");
        block(2, playerA, "Sentinel Spider", "Sky Ruin Drake");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 15);
        assertLife(playerB, 20);
    }

    /**
     * Tests restriction effect going away after card is destroyed
     */
    @Test
    public void testBowerPassageDestroyed() {
        addCard(Zone.BATTLEFIELD, playerB, "Bower Passage");

        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerB, "Arbor Elf");
        addCard(Zone.BATTLEFIELD, playerB, "Assault Griffin");

        addCard(Zone.BATTLEFIELD, playerA, "Angelic Wall");
        addCard(Zone.BATTLEFIELD, playerA, "Air Elemental");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Naturalize");

        // attacker vs. blocker:
        // non flying vs. flying
        attack(2, playerB, "Elite Vanguard");
        block(2, playerA, "Angelic Wall", "Elite Vanguard");
        // non flying vs. non flying
        attack(2, playerB, "Arbor Elf");
        block(2, playerA, "Llanowar Elves", "Arbor Elf");
        // flying vs. flying
        attack(2, playerB, "Assault Griffin");
        block(2, playerA, "Air Elemental", "Assault Griffin");

        castSpell(2, PhaseStep.DECLARE_ATTACKERS, playerA, "Naturalize", "Bower Passage");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Bower Passage", 0);

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    /**
     * Tests "Creatures with power less than Champion of Lambholt's power can't
     * block creatures you control."
     */
    @Test
    public void testChampionOfLambholt() {
        //  Champion of Lambholt: Creature — Human Warrior 1/1, 1GG
        //  - Creatures with power less than Champion of Lambholt's power can't block creatures you control.
        //  - Whenever another creature you control enters, put a +1/+1 counter on Champion of Lambholt.
        addCard(Zone.BATTLEFIELD, playerB, "Champion of Lambholt");

        // Elite Vanguard: Creature — Human Soldier 2/1, W
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard");
        // Arbor Elf: Creature — Elf Druid 1/1, G - {T}: Untap target Forest.
        addCard(Zone.BATTLEFIELD, playerB, "Arbor Elf");
        addCard(Zone.BATTLEFIELD, playerB, "Assault Griffin");

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 5);
        //  Baneslayer Angel: Creature — Angel 5/5, 3WW - Flying, first strike, lifelink, protection from Demons and from Dragons
        addCard(Zone.HAND, playerB, "Baneslayer Angel");

        // Angelic Wall: Creature — Wall 0/4, 1W - Defender, flying
        addCard(Zone.BATTLEFIELD, playerA, "Angelic Wall");
        // Air Elemental: Creature — Elemental 4/4, 3UU - Flying
        addCard(Zone.BATTLEFIELD, playerA, "Air Elemental");
        // Llanowar Elves: Creature — Elf Druid 1/1, G - {T}: Add {G}.
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Baneslayer Angel");

        // non flying vs. flying
        attack(2, playerB, "Elite Vanguard");
        block(2, playerA, "Angelic Wall", "Elite Vanguard"); // can't block - Elite Vanguard does 2 damage
        // non flying vs. non flying
        attack(2, playerB, "Arbor Elf");
        block(2, playerA, "Llanowar Elves", "Arbor Elf"); // can't block - Arbor Elf does 1 damage
        // flying vs. flying
        attack(2, playerB, "Assault Griffin");
        block(2, playerA, "Air Elemental", "Assault Griffin"); // can block

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerB, "Champion of Lambholt", 2, 2);

        assertLife(playerA, 17);
        assertLife(playerB, 20);
    }

    /**
     * Tests can't be blocked
     */
    @Test
    public void testCantBeBlocked() {

        addCard(Zone.BATTLEFIELD, playerA, "Blighted Agent");
        addCard(Zone.BATTLEFIELD, playerB, "Blighted Agent");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");
        addCard(Zone.BATTLEFIELD, playerA, "Birds of Paradise");

        attack(2, playerB, "Blighted Agent:0");
        block(2, playerA, "Blighted Agent:0", "Blighted Agent:0");
        block(2, playerA, "Llanowar Elves:0", "Blighted Agent:0");
        block(2, playerA, "Birds of Paradise:0", "Blighted Agent:0");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, CounterType.POISON, 1);
    }

    @Test
    public void testCantBeBlockedTormentedSoul() {
        addCard(Zone.BATTLEFIELD, playerB, "Tormented Soul");

        addCard(Zone.BATTLEFIELD, playerA, "Flinthoof Boar");

        attack(4, playerB, "Tormented Soul");
        block(4, playerA, "Flinthoof Boar", "Tormented Soul");

        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Flinthoof Boar", 1);
        assertPermanentCount(playerB, "Tormented Soul", 1);

        assertLife(playerA, 19);
    }

    /**
     * Reproduced bug in combat blocking related to singleton classes
     */
    @Test
    public void testFlyingVsNonFlying2() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Savannah Lions");
        addCard(Zone.BATTLEFIELD, playerA, "Azure Drake");
        addCard(Zone.BATTLEFIELD, playerA, "Aven Squire");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");
        addCard(Zone.HAND, playerA, "Turn to Frog");

        addCard(Zone.BATTLEFIELD, playerB, "Walking Corpse");
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Turn to Frog", "Aven Squire");

        attack(3, playerA, "Llanowar Elves");
        attack(3, playerA, "Azure Drake");
        attack(3, playerA, "Aven Squire");
        attack(3, playerA, "Savannah Lions");

        block(3, playerB, "Llanowar Elves", "Azure Drake"); // won't be able to block
        block(3, playerB, "Walking Corpse", "Aven Squire"); // able to block because of Turn to Frog

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 15);
        assertLife(playerA, 20);

        assertPermanentCount(playerB, "Aven Squire", 0);
        assertPermanentCount(playerB, "Walking Corpse", 1);
        assertPermanentCount(playerB, "Llanowar Elves", 1);
    }

    /**
     * Reproduces a bug when a creature that must be blocked is not attacking
     */
    @Test
    public void testTurntimberBasilisk() {
        // Landfall - Whenever a land you control enters, you may
        // have target creature block Turntimber Basilisk this turn if able.
        addCard(Zone.BATTLEFIELD, playerA, "Turntimber Basilisk");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Forest");

        addCard(Zone.BATTLEFIELD, playerB, "Storm Crow");

        // Turntimber Basilisk's Landfall ability targets Storm Crow,
        // so Storm Crow must block Turntimber Basilisk if able
        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");
        addTarget(playerA, "Storm Crow");

        // Turntimber Basilisk doesn't attack and Storm Crow can block Grizzly Bears
        attack(3, playerA, "Grizzly Bears");
        block(3, playerB, "Storm Crow", "Grizzly Bears");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Turntimber Basilisk", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerB, "Storm Crow", 0);
    }

    /*
     * Mogg Flunkies cannot attack alone. Cards like Goblin Assault force all goblins to attack each turn.
     * Mogg Flunkies should not be able to attack.
     */
    @Test
    public void testMustAttackButCannotAttackAlone() {
        /* Mogg Flunkies {1}{R} 3/3
         Creature — Goblin
            Mogg Flunkies can't attack or block alone.
        */
        String flunkies = "Mogg Flunkies";

        /* Goblin Assault {2}{R}
         * Enchantment
            At the beginning of your upkeep, create a 1/1 red Goblin creature token with haste.
            Goblin creatures attack each turn if able.
        */
        String gAssault = "Goblin Assault";

        addCard(Zone.BATTLEFIELD, playerA, flunkies);
        addCard(Zone.BATTLEFIELD, playerB, gAssault);

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped(flunkies, false);
        assertLife(playerB, 20);
    }

    /*
    Reported bug: Tromokratis is unable to be blocked.
    */
    @Test
    public void tromokratisBlockedByAll() {
        /*
        Tromokratis {5}{U}{U}
        Legendary Creature — Kraken 8/8
        Tromokratis has hexproof unless it's attacking or blocking.
        Tromokratis can't be blocked unless all creatures defending player controls block it. (If any creature that player controls doesn't block this creature, it can't be blocked.)
        */
        String tromokratis = "Tromokratis";
        String gBears = "Grizzly Bears"; // {1}{G} 2/2
        String memnite = "Memnite"; // {0} 1/1

        addCard(Zone.BATTLEFIELD, playerA, tromokratis);
        addCard(Zone.BATTLEFIELD, playerB, gBears);
        addCard(Zone.BATTLEFIELD, playerB, memnite);

        attack(1, playerA, tromokratis);
        block(1, playerB, gBears, tromokratis);
        block(1, playerB, memnite, tromokratis);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20);
        assertGraveyardCount(playerB, gBears, 1);
        assertGraveyardCount(playerB, memnite, 1);
        assertTapped(tromokratis, true);
    }

    /*
    Reported bug: Tromokratis is unable to be blocked.
    */
    @Test
    public void tromokratisNotBlockedByAll() {
        /*
        Tromokratis {5}{U}{U}
        Legendary Creature — Kraken 8/8
        Tromokratis has hexproof unless it's attacking or blocking.
        Tromokratis can't be blocked unless all creatures defending player controls block it. (If any creature that player controls doesn't block this creature, it can't be blocked.)
        */
        String tromokratis = "Tromokratis";
        String gBears = "Grizzly Bears"; // {1}{G} 2/2
        String memnite = "Memnite"; // {0} 1/1
        String hGiant = "Hill Giant"; // {3}{R} 3/3

        addCard(Zone.BATTLEFIELD, playerA, tromokratis);
        addCard(Zone.BATTLEFIELD, playerB, gBears);
        addCard(Zone.BATTLEFIELD, playerB, memnite);
        addCard(Zone.BATTLEFIELD, playerB, hGiant);

        attack(2, playerB, hGiant); // forces a creature to be tapped so unable to block Tromokratis, which means it cannot be blocked at all
        attack(3, playerA, tromokratis);
        block(3, playerB, gBears, tromokratis);
        block(3, playerB, memnite, tromokratis);

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 12); // Hill Giant could not block it, so no other creature could block Tromokratis either
        assertPermanentCount(playerB, gBears, 1);
        assertPermanentCount(playerB, memnite, 1);
        assertTapped(tromokratis, true);
        assertTapped(hGiant, true);
    }

    @Test
    public void underworldCerberusBlockedByOneTest() {
        /* Underworld Cerberus {3}{B}{3} 6/6
         *  Underworld Cerberus can't be blocked except by three or more creatures.
         *  Cards in graveyards can't be the targets of spells or abilities.
         *  When Underworld Cerberus dies, exile it and each player returns all creature cards from their graveyard to their hand.
         */
        addCard(Zone.BATTLEFIELD, playerA, "Underworld Cerberus");
        addCard(Zone.BATTLEFIELD, playerB, "Memnite"); // 1/1

        attack(3, playerA, "Underworld Cerberus");
        block(3, playerB, "Memnite", "Underworld Cerberus");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);

        try {
            execute();
            fail("Expected exception not thrown");
        } catch (UnsupportedOperationException e) {
            assertEquals("Underworld Cerberus is blocked by 1 creature(s). It has to be blocked by 3 or more.", e.getMessage());
        }
    }

    @Test
    public void underworldCerberusBlockedByTwoTest() {
        /* Underworld Cerberus {3}{B}{3} 6/6
         *  Underworld Cerberus can't be blocked except by three or more creatures.
         *  Cards in graveyards can't be the targets of spells or abilities.
         *  When Underworld Cerberus dies, exile it and each player returns all creature cards from their graveyard to their hand.
         */
        addCard(Zone.BATTLEFIELD, playerA, "Underworld Cerberus");
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 2); // 1/1

        attack(3, playerA, "Underworld Cerberus");
        block(3, playerB, "Memnite:0", "Underworld Cerberus");
        block(3, playerB, "Memnite:1", "Underworld Cerberus");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);

        try {
            execute();
            fail("Expected exception not thrown");
        } catch (UnsupportedOperationException e) {
            assertEquals("Underworld Cerberus is blocked by 2 creature(s). It has to be blocked by 3 or more.", e.getMessage());
        }
    }

    @Test
    public void underworldCerberusBlockedByThreeTest() {

        /* Underworld Cerberus {3}{B}{3} 6/6
         *  Underworld Cerberus can't be blocked except by three or more creatures.
         *  Cards in graveyards can't be the targets of spells or abilities.
         *  When Underworld Cerberus dies, exile it and each player returns all creature cards from their graveyard to their hand.
         */
        addCard(Zone.BATTLEFIELD, playerA, "Underworld Cerberus");
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 3); // 1/1


        // Blocked by 3 creatures - this is acceptable
        attack(3, playerA, "Underworld Cerberus");
        block(3, playerB, "Memnite:0", "Underworld Cerberus");
        block(3, playerB, "Memnite:1", "Underworld Cerberus");
        block(3, playerB, "Memnite:2", "Underworld Cerberus");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);

        execute();

        assertPermanentCount(playerA, "Underworld Cerberus", 1);
        assertPermanentCount(playerB, "Memnite", 0);
        assertGraveyardCount(playerB, "Memnite", 3);

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    @Test
    public void underworldCerberusBlockedByTenTest() {
        /* Underworld Cerberus {3}{B}{3} 6/6
         *  Underworld Cerberus can't be blocked except by three or more creatures.
         *  Cards in graveyards can't be the targets of spells or abilities.
         *  When Underworld Cerberus dies, exile it and each player returns all creature cards from their graveyard to their hand.
         */
        addCard(Zone.BATTLEFIELD, playerA, "Underworld Cerberus");
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 10); // 1/1

        // Blocked by 10 creatures - this is acceptable as it's >3
        attack(3, playerA, "Underworld Cerberus");
        for (int i = 0; i < 10; i++) {
            block(3, playerB, "Memnite:" + i, "Underworld Cerberus");
        }

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);

        execute();

        assertPermanentCount(playerA, "Underworld Cerberus", 0);
        assertPermanentCount(playerB, "Memnite", 4);
        // Actually exiled when it dies
        assertGraveyardCount(playerA, "Underworld Cerberus", 0);
        assertExileCount(playerA, "Underworld Cerberus", 1);
        // Cards are returned to their owner's hand when Underworld Cerberus dies
        assertGraveyardCount(playerB, "Memnite", 0);
        assertHandCount(playerB, "Memnite", 6);

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    @Test
    public void irresistiblePreyMustBeBlockedTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");
        addCard(Zone.BATTLEFIELD, playerA, "Alpha Myr");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Irresistible Prey");

        addCard(Zone.BATTLEFIELD, playerB, "Bronze Sable");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Irresistible Prey", "Llanowar Elves"); // must be blocked

        attack(1, playerA, "Llanowar Elves");
        attack(1, playerA, "Alpha Myr");

        // attempt to block the creature that doesn't have "must be blocked"
        block(1, playerB, "Bronze Sable", "Alpha Myr");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Irresistible Prey", 1);
        assertGraveyardCount(playerA, "Llanowar Elves", 1);
        assertGraveyardCount(playerB, "Bronze Sable", 1);
        assertTapped("Alpha Myr", true);
        assertLife(playerB, 18);
    }

    @Test
    public void test_MustBeBlocked_nothing_human() {
        // Fear of Being Hunted must be blocked if able.
        addCard(Zone.BATTLEFIELD, playerA, "Fear of Being Hunted"); // 4/2

        attack(1, playerA, "Fear of Being Hunted");
        checkAttackers("x1 attacker", 1, playerA, "Fear of Being Hunted");
        checkBlockers("no blocker", 1, playerB, "");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 4);
    }

    @Test
    public void test_MustBeBlocked_nothing_AI() {
        // Fear of Being Hunted must be blocked if able.
        addCard(Zone.BATTLEFIELD, playerA, "Fear of Being Hunted"); // 4/2

        attack(1, playerA, "Fear of Being Hunted");
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkAttackers("x1 attacker", 1, playerA, "Fear of Being Hunted");
        checkBlockers("no blocker", 1, playerB, "");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 4);
    }

    @Test
    public void test_MustBeBlocked_1_blocker_human() {
        // Fear of Being Hunted must be blocked if able.
        addCard(Zone.BATTLEFIELD, playerA, "Fear of Being Hunted"); // 4/2
        //
        addCard(Zone.BATTLEFIELD, playerB, "Alpha Myr", 1); // 2/1

        // auto-choose blocker
        attack(1, playerA, "Fear of Being Hunted");
        checkAttackers("x1 attacker", 1, playerA, "Fear of Being Hunted");
        checkBlockers("forced x1 blocker", 1, playerB, "Alpha Myr");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Fear of Being Hunted", 1);
    }

    @Test
    public void test_MustBeBlocked_1_blocker_AI() {
        // Fear of Being Hunted must be blocked if able.
        addCard(Zone.BATTLEFIELD, playerA, "Fear of Being Hunted"); // 4/2
        //
        addCard(Zone.BATTLEFIELD, playerB, "Alpha Myr", 1); // 2/1

        // auto-choose blocker with AI
        attack(1, playerA, "Fear of Being Hunted");
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkAttackers("x1 attacker", 1, playerA, "Fear of Being Hunted");
        checkBlockers("forced x1 blocker", 1, playerB, "Alpha Myr");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Fear of Being Hunted", 1);
    }

    @Test
    public void test_MustBeBlocked_many_blockers_good_AI() {
        // Fear of Being Hunted must be blocked if able.
        addCard(Zone.BATTLEFIELD, playerA, "Fear of Being Hunted"); // 4/2
        //
        addCard(Zone.BATTLEFIELD, playerB, "Spectral Bears", 10); // 3/3

        // ai must choose any bear
        attack(1, playerA, "Fear of Being Hunted");
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkAttackers("x1 attacker", 1, playerA, "Fear of Being Hunted");
        checkBlockers("x1 optimal blocker", 1, playerB, "Spectral Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Fear of Being Hunted", 1);
    }

    @Test
    public void test_MustBeBlocked_many_blockers_bad_AI() {
        // Fear of Being Hunted must be blocked if able.
        addCard(Zone.BATTLEFIELD, playerA, "Fear of Being Hunted"); // 4/2
        //
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 10); // 1/1

        // ai don't want but must choose any bad memnite
        attack(1, playerA, "Fear of Being Hunted");
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkAttackers("x1 attacker", 1, playerA, "Fear of Being Hunted");
        checkBlockers("x1 optimal blocker", 1, playerB, "Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Fear of Being Hunted", 1);
        assertGraveyardCount(playerB, "Memnite", 1); // x1 blocker die
    }

    @Test
    public void test_MustBeBlocked_many_blockers_optimal_AI() {
        // Fear of Being Hunted must be blocked if able.
        addCard(Zone.BATTLEFIELD, playerA, "Fear of Being Hunted"); // 4/2
        //
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Spectral Bears", 1); // 3/3
        addCard(Zone.BATTLEFIELD, playerB, "Deadbridge Goliath", 1); // 5/5

        // ai must choose optimal creature to kill but survive
        attack(1, playerA, "Fear of Being Hunted");
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkAttackers("x1 attacker", 1, playerA, "Fear of Being Hunted");
        checkBlockers("x1 optimal blocker", 1, playerB, "Deadbridge Goliath");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Fear of Being Hunted", 1);
        assertGraveyardCount(playerB, 0);
    }

    @Test
    public void test_MustBlocking_zero_blockers() {
        // All creatures able to block target creature this turn do so.
        addCard(Zone.HAND, playerA, "Bloodscent"); // {3}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4); // {3}{G}
        //
        // Menace
        // Each creature you control with menace can't be blocked except by three or more creatures.
        addCard(Zone.BATTLEFIELD, playerA, "Sonorous Howlbonder"); // 2/2

        // prepare
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bloodscent", "Sonorous Howlbonder");

        attack(1, playerA, "Sonorous Howlbonder");
        checkAttackers("x1 attacker", 1, playerA, "Sonorous Howlbonder");
        checkBlockers("no blocker", 1, playerB, "");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 2);
        assertPermanentCount(playerA, "Sonorous Howlbonder", 1);
    }

    @Test
    public void test_MustBlocking_full_blockers_human() {
        // All creatures able to block target creature this turn do so.
        addCard(Zone.HAND, playerA, "Bloodscent"); // {3}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4); // {3}{G}
        //
        // Menace
        // Each creature you control with menace can't be blocked except by three or more creatures.
        addCard(Zone.BATTLEFIELD, playerA, "Sonorous Howlbonder"); // 2/2
        //
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 3); // 1/1

        // prepare
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bloodscent", "Sonorous Howlbonder");

        attack(1, playerA, "Sonorous Howlbonder");
        checkAttackers("x1 attacker", 1, playerA, "Sonorous Howlbonder");
        checkBlockers("x3 blockers", 1, playerB, "Memnite", "Memnite", "Memnite");
        setChoice(playerA, CHOICE_SKIP);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Sonorous Howlbonder", 1);
    }

    @Test
    public void test_MustBlocking_full_blockers_AI() {
        // All creatures able to block target creature this turn do so.
        addCard(Zone.HAND, playerA, "Bloodscent"); // {3}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4); // {3}{G}
        //
        // Menace
        // Each creature you control with menace can't be blocked except by three or more creatures.
        addCard(Zone.BATTLEFIELD, playerA, "Sonorous Howlbonder"); // 2/2
        //
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 3); // 1/1

        // prepare
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bloodscent", "Sonorous Howlbonder");

        // ai must choose all blockers anyway
        attack(1, playerA, "Sonorous Howlbonder");
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        setChoiceAmount(playerA, 1, 1, 0); // assign damage to blocking memnites
        checkAttackers("x1 attacker", 1, playerA, "Sonorous Howlbonder");
        checkBlockers("x3 blockers", 1, playerB, "Memnite", "Memnite", "Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Sonorous Howlbonder", 1);
    }

    @Test
    public void test_MustBlocking_many_blockers_human() {
        // possible bug: AI's blockers auto-fix assign too many blockers (e.g. x10 instead x3 by required effect)

        // All creatures able to block target creature this turn do so.
        addCard(Zone.HAND, playerA, "Bloodscent"); // {3}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4); // {3}{G}
        //
        // Menace
        // Each creature you control with menace can't be blocked except by three or more creatures.
        addCard(Zone.BATTLEFIELD, playerA, "Sonorous Howlbonder"); // 2/2
        //
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 5); // 1/1

        // prepare
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bloodscent", "Sonorous Howlbonder");

        attack(1, playerA, "Sonorous Howlbonder");
        checkAttackers("x1 attacker", 1, playerA, "Sonorous Howlbonder");
        checkBlockers("all blockers", 1, playerB, "Memnite", "Memnite", "Memnite", "Memnite", "Memnite");
        setChoice(playerA, CHOICE_SKIP);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Sonorous Howlbonder", 1);
    }

    @Test
    public void test_MustBlocking_many_blockers_AI() {
        // possible bug: AI's blockers auto-fix assign too many blockers (e.g. x10 instead x3 by required effect)

        // All creatures able to block target creature this turn do so.
        addCard(Zone.HAND, playerA, "Bloodscent"); // {3}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4); // {3}{G}
        //
        // Menace
        // Each creature you control with menace can't be blocked except by three or more creatures.
        addCard(Zone.BATTLEFIELD, playerA, "Sonorous Howlbonder"); // 2/2
        //
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 5); // 1/1

        // prepare
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bloodscent", "Sonorous Howlbonder");

        // ai must choose all blockers
        attack(1, playerA, "Sonorous Howlbonder");
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        setChoiceAmount(playerA, 1, 1, 0, 0, 0); // assign damage to blocking memnites
        checkAttackers("x1 attacker", 1, playerA, "Sonorous Howlbonder");
        checkBlockers("all blockers", 1, playerB, "Memnite", "Memnite", "Memnite", "Memnite", "Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Sonorous Howlbonder", 1);
    }

    @Test
    @Ignore
    // TODO: need exception fix - java.lang.UnsupportedOperationException: Sonorous Howlbonder is blocked by 1 creature(s). It has to be blocked by 3 or more.
    //   It's auto-fix in block configuration, so exception must be fixed cause AI works with it
    public void test_MustBlocking_low_blockers() {
        // possible bug: exception on wrong block configuration
        // if effect require x3 blockers, but opponent has only 1 then it must use 1 blocker anyway

        // All creatures able to block target creature this turn do so.
        addCard(Zone.HAND, playerA, "Bloodscent"); // {3}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4); // {3}{G}
        //
        // Menace
        // Each creature you control with menace can't be blocked except by three or more creatures.
        addCard(Zone.BATTLEFIELD, playerA, "Sonorous Howlbonder"); // 2/2
        //
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1); // 1/1

        // prepare
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bloodscent", "Sonorous Howlbonder");

        attack(1, playerA, "Sonorous Howlbonder");
        checkAttackers("x1 attacker", 1, playerA, "Sonorous Howlbonder");
        checkBlockers("one possible blocker", 1, playerB, "Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Sonorous Howlbonder", 1);
    }

    @Test
    public void test_MustBeBlockedWithMenace_0_blockers() {
        // At the beginning of combat on your turn, you may pay {2}{R/G}. If you do, double target creature’s
        // power until end of turn. That creature must be blocked this combat if able.
        addCard(Zone.BATTLEFIELD, playerA, "Neyith of the Dire Hunt"); // 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        //
        // Menace
        addCard(Zone.BATTLEFIELD, playerA, "Alley Strangler", 1); // 2/3

        addTarget(playerA, "Alley Strangler"); // boost target
        setChoice(playerA, true); // boost target
        attack(1, playerA, "Alley Strangler");
        checkAttackers("x1 attacker", 1, playerA, "Alley Strangler");
        checkBlockers("no blocker", 1, playerB, "");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 4);
        assertGraveyardCount(playerA, "Alley Strangler", 0);
    }

    @Test
    @Ignore // TODO: need improve of block configuration auto-fix (block by x2 instead x1)
    //           looks like it's impossible for auto-fix (it's can remove blocker, but not add)
    public void test_MustBeBlockedWithMenace_all_blockers() {
        // At the beginning of combat on your turn, you may pay {2}{R/G}. If you do, double target creature’s
        // power until end of turn. That creature must be blocked this combat if able.
        addCard(Zone.BATTLEFIELD, playerA, "Neyith of the Dire Hunt"); // 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        //
        // Menace
        addCard(Zone.BATTLEFIELD, playerA, "Alley Strangler", 1); // 2/3
        //
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 2); // 1/1

        // If the target creature has menace, two creatures must block it if able.
        // (2020-06-23)

        addTarget(playerA, "Alley Strangler"); // boost target
        setChoice(playerA, true); // boost target
        attack(1, playerA, "Alley Strangler");
        checkAttackers("x1 attacker", 1, playerA, "Alley Strangler");
        checkBlockers("x2 blockers", 1, playerB, "Memnite", "Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Alley Strangler", 0);
    }

    @Test
    @Ignore // TODO: need improve of block configuration auto-fix (block by x2 instead x1)
    //           looks like it's impossible for auto-fix (it's can remove blocker, but not add)
    public void test_MustBeBlockedWithMenace_many_low_blockers_human() {
        // At the beginning of combat on your turn, you may pay {2}{R/G}. If you do, double target creature’s
        // power until end of turn. That creature must be blocked this combat if able.
        addCard(Zone.BATTLEFIELD, playerA, "Neyith of the Dire Hunt"); // 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        //
        // Menace
        addCard(Zone.BATTLEFIELD, playerA, "Alley Strangler", 1); // 2/3
        //
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 10); // 1/1

        // If the target creature has menace, two creatures must block it if able.
        // (2020-06-23)

        addTarget(playerA, "Alley Strangler"); // boost target
        setChoice(playerA, true); // boost target
        attack(1, playerA, "Alley Strangler");
        checkAttackers("x1 attacker", 1, playerA, "Alley Strangler");
        checkBlockers("x2 blockers", 1, playerB, "Memnite", "Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Alley Strangler", 0);
    }

    @Test
    @Ignore // TODO: blockWithGoodTrade2 must support additional restrictions
    public void test_MustBeBlockedWithMenace_many_low_blockers_AI() {
        // At the beginning of combat on your turn, you may pay {2}{R/G}. If you do, double target creature’s
        // power until end of turn. That creature must be blocked this combat if able.
        addCard(Zone.BATTLEFIELD, playerA, "Neyith of the Dire Hunt"); // 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        //
        // Menace
        addCard(Zone.BATTLEFIELD, playerA, "Alley Strangler", 1); // 2/3
        //
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 10); // 1/1

        // If the target creature has menace, two creatures must block it if able.
        // (2020-06-23)

        // AI must be forced to choose min x2 low blockers (it's possible to fail here after AI logic improve someday)

        addTarget(playerA, "Alley Strangler"); // boost target
        setChoice(playerA, true); // boost target
        attack(1, playerA, "Alley Strangler");
        setChoiceAmount(playerA, 1, 1); // assign damage to blocking memnites
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkAttackers("x1 attacker", 1, playerA, "Alley Strangler");
        checkBlockers("x2 blockers", 1, playerB, "Memnite", "Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Alley Strangler", 0);
        assertGraveyardCount(playerB, "Memnite", 2); // x2 blockers must die
    }

    @Test
    public void test_MustBeBlockedWithMenace_low_blockers_auto() {
        // At the beginning of combat on your turn, you may pay {2}{R/G}. If you do, double target creature’s
        // power until end of turn. That creature must be blocked this combat if able.
        addCard(Zone.BATTLEFIELD, playerA, "Neyith of the Dire Hunt"); // 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        //
        // Menace
        addCard(Zone.BATTLEFIELD, playerA, "Alley Strangler", 1); // 2/3
        //
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1); // 1/1

        // If the target creature has menace, two creatures must block it if able.
        // (2020-06-23)
        //
        // If a creature is required to block a creature with menace, another creature must also block that creature
        // if able. If none can, the creature that’s required to block can block another creature or not block at all.
        // (2020-04-17)

        // auto-fix block config inside

        addTarget(playerA, "Alley Strangler"); // boost target
        setChoice(playerA, true); // boost target
        attack(1, playerA, "Alley Strangler");
        checkAttackers("x1 attacker", 1, playerA, "Alley Strangler");
        checkBlockers("no blockers", 1, playerB, "");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 4);
        assertGraveyardCount(playerA, "Alley Strangler", 0);
    }

    @Test
    @Ignore
    // TODO: need exception fix java.lang.UnsupportedOperationException: Alley Strangler is blocked by 1 creature(s). It has to be blocked by 2 or more.
    //   It's ok to have such exception in unit tests from manual setup
    //   If it's impossible to auto-fix, then keep that error and ignore the test
    public void test_MustBeBlockedWithMenace_low_blockers_manual() {
        // At the beginning of combat on your turn, you may pay {2}{R/G}. If you do, double target creature’s
        // power until end of turn. That creature must be blocked this combat if able.
        addCard(Zone.BATTLEFIELD, playerA, "Neyith of the Dire Hunt"); // 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        //
        // Menace
        addCard(Zone.BATTLEFIELD, playerA, "Alley Strangler", 1); // 2/3
        //
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1); // 1/1

        // If the target creature has menace, two creatures must block it if able.
        // (2020-06-23)
        //
        // If a creature is required to block a creature with menace, another creature must also block that creature
        // if able. If none can, the creature that’s required to block can block another creature or not block at all.
        // (2020-04-17)

        // define blocker manual

        addTarget(playerA, "Alley Strangler"); // boost target
        setChoice(playerA, true); // boost target
        attack(1, playerA, "Alley Strangler");
        block(1, playerB, "Memnite", "Alley Strangler");
        checkAttackers("x1 attacker", 1, playerA, "Alley Strangler");
        checkBlockers("no blockers", 1, playerB, "");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 4);
        assertGraveyardCount(playerA, "Alley Strangler", 0);
    }

    @Test
    public void test_MustBeBlockedWithMenace_low_small_blockers_AI() {
        // At the beginning of combat on your turn, you may pay {2}{R/G}. If you do, double target creature’s
        // power until end of turn. That creature must be blocked this combat if able.
        addCard(Zone.BATTLEFIELD, playerA, "Neyith of the Dire Hunt"); // 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        //
        // Menace
        addCard(Zone.BATTLEFIELD, playerA, "Alley Strangler", 1); // 2/3
        //
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1); // 1/1

        // If the target creature has menace, two creatures must block it if able.
        // (2020-06-23)
        //
        // If a creature is required to block a creature with menace, another creature must also block that creature
        // if able. If none can, the creature that’s required to block can block another creature or not block at all.
        // (2020-04-17)

        // auto-fix block config inside
        // AI must ignore such use case

        addTarget(playerA, "Alley Strangler"); // boost target
        setChoice(playerA, true); // boost target
        attack(1, playerA, "Alley Strangler");
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkAttackers("x1 attacker", 1, playerA, "Alley Strangler");
        checkBlockers("no blockers", 1, playerB, "");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 4);
        assertGraveyardCount(playerA, "Alley Strangler", 0);
    }

    @Test
    public void test_MustBeBlockedWithMenace_low_big_blockers_AI() {
        // bug: #13290, AI can try to use bigger creature to block

        // At the beginning of combat on your turn, you may pay {2}{R/G}. If you do, double target creature’s
        // power until end of turn. That creature must be blocked this combat if able.
        addCard(Zone.BATTLEFIELD, playerA, "Neyith of the Dire Hunt"); // 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        //
        // Menace
        addCard(Zone.BATTLEFIELD, playerA, "Alley Strangler", 1); // 2/3
        //
        addCard(Zone.BATTLEFIELD, playerB, "Deadbridge Goliath", 1); // 5/5

        // If the target creature has menace, two creatures must block it if able.
        // (2020-06-23)
        //
        // If a creature is required to block a creature with menace, another creature must also block that creature
        // if able. If none can, the creature that’s required to block can block another creature or not block at all.
        // (2020-04-17)

        // auto-fix block config inside
        // AI must ignore BIG creature to wrongly block

        addTarget(playerA, "Alley Strangler"); // boost target
        setChoice(playerA, true); // boost target
        attack(1, playerA, "Alley Strangler");
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkAttackers("x1 attacker", 1, playerA, "Alley Strangler");
        checkBlockers("no blockers", 1, playerB, "");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 4);
        assertGraveyardCount(playerA, "Alley Strangler", 0);
    }
}