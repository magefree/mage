package org.mage.test.cards.restriction;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class CantAttackTest extends CardTestPlayerBase {

    /**
     * Tests "If all other elves get the Forestwalk ability and can't be blocked
     * from creatures whose controller has a forest in game"
     */
    @Test
    public void testAttack() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Myr Enforcer"); // 4/4

        // Except for creatures named Akron Legionnaire and artifact creatures, creatures you control can't attack.
        addCard(Zone.BATTLEFIELD, playerB, "Akron Legionnaire"); // 8/4
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Myr Enforcer"); // 4/4

        attack(2, playerB, "Akron Legionnaire");
        attack(2, playerB, "Silvercoat Lion");
        attack(2, playerB, "Myr Enforcer");

        attack(3, playerA, "Silvercoat Lion");
        attack(3, playerA, "Myr Enforcer");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);

        try {
            execute();
            assertAllCommandsUsed();

            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Player PlayerB must have 0 actions but found 1")) {
                Assert.fail("Should have thrown error about not being able to attack, but got:\n" + e.getMessage());
            }
        }

        assertLife(playerA, 8); // 8 + 4
        assertLife(playerB, 14); // 4 + 2
    }

    @Test
    public void testAttackHarborSerpent() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2
        // Islandwalk (This creature is unblockable as long as defending player controls an Island.)
        // Harbor Serpent can't attack unless there are five or more Islands on the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Harbor Serpent"); // 5/5
        addCard(Zone.HAND, playerA, "Island");

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Harbor Serpent"); // 5/5

        attack(2, playerB, "Harbor Serpent");  // Can't attack since there are only 4 Islands
        attack(2, playerB, "Silvercoat Lion");

        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Island");
        attack(3, playerA, "Harbor Serpent"); // Should be able to attack
        attack(3, playerA, "Silvercoat Lion");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);

        try {
            execute();
            assertAllCommandsUsed();

            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Player PlayerB must have 0 actions but found 1")) {
                Assert.fail("Should have thrown error about not being able to attack, but got:\n" + e.getMessage());
            }
        }

        assertLife(playerB, 13);
        assertLife(playerA, 18);
    }

    @Test
    public void testBlazingArchon() {
        // Flying
        // Creatures can't attack you.
        addCard(Zone.BATTLEFIELD, playerA, "Blazing Archon");

        addCard(Zone.BATTLEFIELD, playerA, "Ajani Goldmane"); // Planeswalker 4 loyality counter

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox"); // 2/4

        attack(2, playerB, "Pillarfield Ox", "Ajani Goldmane");
        attack(2, playerB, "Silvercoat Lion", playerA);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);

        try {
            execute();
            assertAllCommandsUsed();

            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Player PlayerB must have 0 actions but found 1")) {
                Assert.fail("Should have thrown error about not being able to attack, but got:\n" + e.getMessage());
            }
        }

        assertLife(playerA, 20);

        assertTapped("Silvercoat Lion", false);
        assertTapped("Pillarfield Ox", true);
        assertCounterCount("Ajani Goldmane", CounterType.LOYALTY, 2);
    }

    @Test
    public void testCowedByWisdom() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        // Enchant creature
        // Enchanted creature can't attack or block unless its controller pays {1} for each card in your hand.
        addCard(Zone.HAND, playerA, "Cowed by Wisdom"); // Planeswalker 4 loyality counter

        // Bushido 2 (When this blocks or becomes blocked, it gets +2/+2 until end of turn.)
        // Battle-Mad Ronin attacks each turn if able.
        addCard(Zone.BATTLEFIELD, playerB, "Battle-Mad Ronin");
        addCard(Zone.HAND, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cowed by Wisdom", "Battle-Mad Ronin");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);

        assertTapped("Battle-Mad Ronin", false);
    }

    /**
     * Orzhov Advokist's ability does not work.
     * Your opponents get the counters but they can still attack you.
     */
    @Test
    public void testOrzhovAdvokist() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        // At the beginning of your upkeep, each player may put two +1/+1 counters on a creature they control.
        // If a player does, creatures that player controls can't attack you or a planeswalker you control until your next turn.
        addCard(Zone.HAND, playerA, "Orzhov Advokist"); // Creature {2}{W} 1/4

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Orzhov Advokist");
        setChoice(playerA, true);
        setChoice(playerB, true);
        attack(2, playerB, "Silvercoat Lion"); // Can't attack since they put a +1/+1 counter
        attack(4, playerB, "Silvercoat Lion");
        setStopAt(4, PhaseStep.POSTCOMBAT_MAIN);

        try {
            execute();
            assertAllCommandsUsed();

            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Player PlayerB must have 0 actions but found 1")) {
                Assert.fail("Should have thrown error about not being able to attack, but got:\n" + e.getMessage());
            }
        }

        assertPermanentCount(playerA, "Orzhov Advokist", 1);
        assertPowerToughness(playerA, "Orzhov Advokist", 3, 6);
        assertLife(playerA, 18);
        assertTapped("Silvercoat Lion", false);
        assertPowerToughness(playerB, "Silvercoat Lion", 4, 4);
    }

    /**
     * Reported bug: Medomai was able to attack on an extra turn when cheated into play.
     */
    @Test
    public void testMedomaiShouldNotAttackOnExtraTurns() {

        /*
        Medomai the Ageless {4}{W}{U}
        Legendary Creature — Sphinx 4/4
        Flying
        Whenever Medomai the Ageless deals combat damage to a player, take an extra turn after this one.
        Medomai the Ageless can't attack during extra turns.
         */
        String medomai = "Medomai the Ageless";

        /*
         Cauldron Dance {4}{B}{R} Instant
         Cast Cauldron Dance only during combat.
         Return target creature card from your graveyard to the battlefield.
         That creature gains haste.
         Return it to your hand at the beginning of the next end step.

         You may put a creature card from your hand onto the battlefield.
         That creature gains haste.
         Its controller sacrifices it at the beginning of the next end step.
         */
        String cDance = "Cauldron Dance";
        String dBlade = "Doom Blade"; // {1}{B} instant destroy target creature
        addCard(Zone.BATTLEFIELD, playerA, medomai);
        addCard(Zone.HAND, playerA, dBlade);
        addCard(Zone.HAND, playerA, cDance);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        // attack with Medomai, connect, and destroy him after combat
        attack(1, playerA, medomai);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, dBlade, medomai);

        // next turn granted, return Medomai to field with Cauldron and try to attack again
        castSpell(2, PhaseStep.BEGIN_COMBAT, playerA, cDance);
        addTarget(playerA, medomai);
        attack(2, playerA, medomai);

        // Medomai should not have been allowed to attack, but returned to hand at beginning of next end step still
        setStopAt(2, PhaseStep.END_TURN);

        try {
            execute();
            assertAllCommandsUsed();

            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Player PlayerA must have 0 actions but found 1")) {
                Assert.fail("Should have thrown error about not being able to attack, but got:\n" + e.getMessage());
            }
        }

        assertLife(playerB, 16); // one hit from medomai
        assertGraveyardCount(playerA, dBlade, 1);
        assertGraveyardCount(playerA, cDance, 1);
        assertGraveyardCount(playerA, medomai, 0);
        assertHandCount(playerA, medomai, 1);
    }

    @Test
    public void basicMedomaiTestForExtraTurn() {
        /*
        Medomai the Ageless {4}{W}{U}
        Legendary Creature — Sphinx 4/4
        Flying
        Whenever Medomai the Ageless deals combat damage to a player, take an extra turn after this one.
        Medomai the Ageless can't attack during extra turns.
         */
        String medomai = "Medomai the Ageless";

        /*
         Exquisite Firecraft {1}{R}{R}
            Sorcery
            Exquisite Firecraft deals 4 damage to any target.
         */
        String eFirecraft = "Exquisite Firecraft";

        addCard(Zone.BATTLEFIELD, playerA, medomai);
        addCard(Zone.HAND, playerA, eFirecraft);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        // attack with medomai, get extra turn, confirm cannot attack again with medomai and can cast sorcery
        attack(1, playerA, medomai);
        attack(2, playerA, medomai); // should not be allowed to
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, eFirecraft, playerB);

        setStopAt(2, PhaseStep.END_TURN);

        try {
            execute();
            assertAllCommandsUsed();

            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Player PlayerA must have 0 actions but found 1")) {
                Assert.fail("Should have thrown error about not being able to attack, but got:\n" + e.getMessage());
            }
        }

        assertLife(playerB, 12); // 1 hit from medomai and firecraft = 8 damage
        assertGraveyardCount(playerA, eFirecraft, 1);
        assertPermanentCount(playerA, medomai, 1);
    }

    @Test
    public void sphereOfSafetyPaidCostAllowsAttack() {
        /*
        Sphere of Safety {4}{W}
         Enchantment
        Creatures can't attack you or a planeswalker you control unless their controller pays {X} for each of those creatures, where X is the number of enchantments you control.
         */
        String sphere = "Sphere of Safety";
        String memnite = "Memnite";

        addCard(Zone.BATTLEFIELD, playerA, memnite); // {0} 1/1
        addCard(Zone.BATTLEFIELD, playerB, sphere);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");

        attack(1, playerA, memnite);
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, sphere, 1);
        assertLife(playerB, 19); // took the hit from memnite
        assertTapped("Forest", true); // forest had to be tapped
    }

    @Test
    public void sphereOfSafetyCostNotPaid_NoAttackAllowed() {
        /*
        Sphere of Safety {4}{W}
         Enchantment
        Creatures can't attack you or a planeswalker you control unless their controller pays {X} for each of those creatures, where X is the number of enchantments you control.
         */
        String sphere = "Sphere of Safety";
        String memnite = "Memnite";

        addCard(Zone.BATTLEFIELD, playerA, memnite); // {0} 1/1
        addCard(Zone.BATTLEFIELD, playerB, sphere);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");

        attack(1, playerA, memnite);
        setChoice(playerA, false);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, sphere, 1);
        assertLife(playerB, 20); // no damage went through, did not elect to pay
        assertTapped("Forest", false); // forest not tapped
    }

    @Test
    public void collectiveResistanceCostPaid_AttackAllowed() {
        /*
        Collective Restraint {3}{U}
        Enchantment
        Domain — Creatures can't attack you unless their controller pays {X} for each creature they control that's attacking you, where X is the number of basic land types among lands you control.
         */
        String cRestraint = "Collective Restraint";
        String memnite = "Memnite";

        addCard(Zone.BATTLEFIELD, playerA, memnite); // {0} 1/1
        addCard(Zone.BATTLEFIELD, playerB, cRestraint);
        addCard(Zone.BATTLEFIELD, playerB, "Island"); // 1 basic land type = pay 1 to attack
        addCard(Zone.BATTLEFIELD, playerA, "Forest");

        attack(1, playerA, memnite);
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, cRestraint, 1);
        assertLife(playerB, 19); // took the hit from memnite
        assertTapped("Forest", true); // forest had to be tapped
    }

    @Test
    public void collectiveResistanceCostNotPaid_NoAttackAllowed() {
        /*
        Collective Restraint {3}{U}
        Enchantment
        Domain — Creatures can't attack you unless their controller pays {X} for each creature they control that's attacking you, where X is the number of basic land types among lands you control.
         */
        String cRestraint = "Collective Restraint";
        String memnite = "Memnite";

        addCard(Zone.BATTLEFIELD, playerA, memnite); // {0} 1/1
        addCard(Zone.BATTLEFIELD, playerB, cRestraint);
        addCard(Zone.BATTLEFIELD, playerB, "Island"); // 1 basic land type = pay 1 to attack
        addCard(Zone.BATTLEFIELD, playerA, "Forest");

        attack(1, playerA, memnite);
        setChoice(playerA, false);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, cRestraint, 1);
        assertLife(playerB, 20); // no damage went through, did not elect to pay
        assertTapped("Forest", false); // forest not tapped
    }

    @Test
    public void ghostlyPrison_PaidCost_AllowsAttack() {
        /*
        Ghostly Prison {2}{W}
        Enchantment
        Creatures can't attack you unless their controller pays {2} for each creature they control that's attacking you.
         */
        String gPrison = "Ghostly Prison";
        String memnite = "Memnite";

        addCard(Zone.BATTLEFIELD, playerA, memnite); // {0} 1/1
        addCard(Zone.BATTLEFIELD, playerB, gPrison);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        attack(1, playerA, memnite);
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, gPrison, 1);
        assertLife(playerB, 19); // took the hit from memnite
        assertTappedCount("Forest", true, 2);  // forests had to be tapped
    }

    @Test
    public void ghostlyPrison_CostNotPaid_NoAttackAllowed() {
        /*
        Ghostly Prison {2}{W}
        Enchantment
        Creatures can't attack you unless their controller pays {2} for each creature they control that's attacking you.
         */
        String gPrison = "Ghostly Prison";
        String memnite = "Memnite";

        addCard(Zone.BATTLEFIELD, playerA, memnite); // {0} 1/1
        addCard(Zone.BATTLEFIELD, playerB, gPrison);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        attack(1, playerA, memnite);
        setChoice(playerA, false);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, gPrison, 1);
        assertLife(playerB, 20); // no damage went through, did not elect to pay
        assertTapped("Forest", false); // no forests tapped
    }

    @Test
    public void OpportunisticDragon() {
        // Flying
        // When Opportunistic Dragon enters the battlefield, choose target Human or artifact an opponent controls. For as long as Opportunistic Dragon remains on the battlefield, gain control of that permanent, it loses all abilities, and it can't attack or block.
        addCard(Zone.HAND, playerA, "Opportunistic Dragon"); // Creature {2}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Desperate Castaways"); // Creature - Human Pirate 2/3

        // Other Pirates you control get +1/+1.
        // At the beginning of your end step, gain control of target nonland permanent controlled by a player who was dealt combat damage by three or more Pirates this turn.
        addCard(Zone.BATTLEFIELD, playerB, "Admiral Beckett Brass"); // Creature {1}{B}{B}{R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Opportunistic Dragon");
        addTarget(playerA, "Admiral Beckett Brass");

        attack(3, playerA, "Admiral Beckett Brass"); // Can't attack

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);

        try {
            execute();
            assertAllCommandsUsed();

            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Player PlayerA must have 0 actions but found 1")) {
                Assert.fail("Should have thrown error about not being able to attack, but got:\n" + e.getMessage());
            }
        }

        assertPermanentCount(playerA, "Opportunistic Dragon", 1);
        assertPermanentCount(playerA, "Admiral Beckett Brass", 1);
        assertPowerToughness(playerA, "Desperate Castaways", 2, 3);

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    /* Opportunistic Dragon - can't block/can't attack effect did not end when opportunistic dragon was exiled */
    @Test
    public void OpportunisticDragonEndEffects() {
        // Flying
        // When Opportunistic Dragon enters the battlefield, choose target Human or artifact an opponent controls. For as long as Opportunistic Dragon remains on the battlefield, gain control of that permanent, it loses all abilities, and it can't attack or block.
        addCard(Zone.HAND, playerA, "Opportunistic Dragon"); // Creature {2}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Desperate Castaways"); // Creature - Human Pirate 2/3

        // Other Pirates you control get +1/+1.
        // At the beginning of your end step, gain control of target nonland permanent controlled by a player who was dealt combat damage by three or more Pirates this turn.
        addCard(Zone.BATTLEFIELD, playerB, "Admiral Beckett Brass"); // Creature {1}{B}{B}{R} 3/3
        addCard(Zone.BATTLEFIELD, playerB, "Desperate Castaways"); // Creature - Human Pirate 2/3
        // Destroy target nonartifact, nonblack creature. It can't be regenerated.
        addCard(Zone.HAND, playerB, "Terror"); // Instant {1}{B}
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Opportunistic Dragon");
        addTarget(playerA, "Admiral Beckett Brass");

        attack(3, playerA, "Admiral Beckett Brass"); // Can't attack
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerB, "Terror", "Opportunistic Dragon");

        attack(4, playerB, "Admiral Beckett Brass"); // Can attack again

        setStopAt(4, PhaseStep.POSTCOMBAT_MAIN);

        try {
            execute();
            assertAllCommandsUsed();

            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Player PlayerA must have 0 actions but found 1")) {
                Assert.fail("Should have thrown error about not being able to attack, but got:\n" + e.getMessage());
            }
        }

        assertGraveyardCount(playerB, "Terror", 1);
        assertGraveyardCount(playerA, "Opportunistic Dragon", 1);
        assertPermanentCount(playerB, "Admiral Beckett Brass", 1);
        assertPowerToughness(playerB, "Desperate Castaways", 3, 4);

        assertLife(playerA, 17);
        assertLife(playerB, 20);
    }

}
