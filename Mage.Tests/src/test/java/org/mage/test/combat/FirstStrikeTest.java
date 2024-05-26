package org.mage.test.combat;

import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class FirstStrikeTest extends CardTestPlayerBase {

    /*
     702.7b. If at least one attacking or blocking creature has first strike or double strike (see rule 702.4)
     as the combat damage step begins, the only creatures that assign combat damage in that step
     are those with first strike or double strike. After that step, instead of proceeding to the end of combat step,
     the phase gets a second combat damage step. The only creatures that assign combat damage in that step
     are the remaining attackers and blockers that had neither first strike nor double strike
     as the first combat damage step began, as well as the remaining attackers and blockers
     that currently have double strike. After that step, the phase proceeds to the end of combat step.

     702.7c. Giving first strike to a creature without it after combat damage has already been dealt
     in the first combat damage step won't preclude that creature from assigning combat damage
     in the second combat damage step. Removing first strike from a creature after it has already
     dealt combat damage in the first combat damage step won't allow it to also assign combat damage
     in the second combat damage step (unless the creature has double strike).

     702.4c. Removing double strike from a creature during the first combat damage step will stop it from
     assigning combat damage in the second combat damage step.

     702.4d. Giving double strike to a creature with first strike after it has already dealt combat damage
     in the first combat damage step will allow the creature to assign combat damage in the second combat damage step.
     */

    private static final String knight = "White Knight"; // 2/2 first strike, protection from black
    private static final String bears = "Grizzly Bears"; // 2/2
    private static final String piker = "Goblin Piker"; // 2/1
    private static final String ghoul = "Warpath Ghoul"; // 3/2
    private static final String centaur = "Centaur Courser"; // 3/3
    private static final String sentry = "Dragon's Eye Sentry"; // 1/3, defender, first strike
    private static final String bodyguard = "Anaba Bodyguard"; // 2/3 first strike
    private static final String blademaster = "Markov Blademaster"; // 1/1 double strike, +1/+1 counter when deals damage
    private static final String wolverine = "Spelleater Wolverine"; // 3/2, double strike as long as 3+ instants/sorceries in your graveyard
    private static final String fury = "Kindled Fury"; // R: target gets +1/+0 and first strike
    private static final String runemark = "Mardu Runemark"; // 2R Aura
    // Enchanted creature gets +2/+2.
    // Enchanted creature has first strike as long as you control a white or black permanent.
    private static final String urchin = "Bile Urchin"; // 1/1
    // Sacrifice Bile Urchin: Target player loses 1 life
    private static final String cleave = "Double Cleave"; // 1{R/W}: target gains double strike

    @Test
    public void firstStrikeAttacker() {
        addCard(Zone.BATTLEFIELD, playerA, knight, 1);
        addCard(Zone.BATTLEFIELD, playerB, bears, 1);

        attack(1, playerA, knight, playerB);
        block(1, playerB, bears, knight);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, bears, 1);
        assertGraveyardCount(playerA, knight, 0);
    }

    @Test
    public void firstStrikeBlocker() {
        addCard(Zone.BATTLEFIELD, playerB, knight, 1);
        addCard(Zone.BATTLEFIELD, playerA, bears, 1);

        attack(1, playerA, bears, playerB);
        block(1, playerB, knight, bears);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, bears, 1);
        assertGraveyardCount(playerB, knight, 0);
    }

    @Test
    public void firstStrikeBoth() {
        addCard(Zone.BATTLEFIELD, playerA, bodyguard);
        addCard(Zone.BATTLEFIELD, playerB, sentry);

        attack(1, playerA, bodyguard, playerB);
        block(1, playerB, sentry, bodyguard);

        checkDamage("after first strike", 1, PhaseStep.FIRST_COMBAT_DAMAGE, playerA, bodyguard, 1);
        checkDamage("after first strike", 1, PhaseStep.FIRST_COMBAT_DAMAGE, playerB, sentry, 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, bodyguard, 1);
        assertPermanentCount(playerB, sentry, 1);
    }

    @Test
    public void doubleStrikePump() {
        addCard(Zone.BATTLEFIELD, playerA, blademaster); // 1/1
        // Double strike; Whenever Markov Blademaster deals combat damage to a player, put a +1/+1 counter on it.

        attack(1, playerA, blademaster, playerB);

        checkLife("after first strike damage", 1, PhaseStep.FIRST_COMBAT_DAMAGE, playerB, 19);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 17);
        assertPowerToughness(playerA, blademaster, 3, 3);
    }

    @Test
    public void firstStrikeGainedAttacker() {
        addCard(Zone.BATTLEFIELD, playerA, piker, 1);
        addCard(Zone.BATTLEFIELD, playerB, bears, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, fury);

        attack(1, playerA, piker, playerB);
        block(1, playerB, bears, piker);

        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerA, fury, piker);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, bears, 1);
        assertGraveyardCount(playerA, piker, 0);
        assertAbility(playerA, piker, FirstStrikeAbility.getInstance(), true);
    }

    @Test
    public void firstStrikeGainedBlocker() {
        addCard(Zone.BATTLEFIELD, playerB, piker, 1);
        addCard(Zone.BATTLEFIELD, playerA, bears, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, fury);

        attack(1, playerA, bears, playerB);
        block(1, playerB, piker, bears);

        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerB, fury, piker);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, bears, 1);
        assertGraveyardCount(playerB, piker, 0);
        assertAbility(playerB, piker, FirstStrikeAbility.getInstance(), true);
    }

    @Test
    public void firstStrikeGainedBothDie() {
        addCard(Zone.BATTLEFIELD, playerA, knight, 1);
        addCard(Zone.BATTLEFIELD, playerB, bears, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, fury);

        attack(1, playerA, knight, playerB);
        block(1, playerB, bears, knight);

        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerB, fury, bears);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, bears, 1);
        assertGraveyardCount(playerA, knight, 1);
    }

    @Test
    public void firstStrikeGainedMidCombat() {
        addCard(Zone.BATTLEFIELD, playerA, knight, 1);
        addCard(Zone.BATTLEFIELD, playerA, ghoul, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, fury);

        attack(1, playerA, knight, playerB);
        attack(1, playerA, ghoul, playerB);

        checkLife("after first strike", 1, PhaseStep.FIRST_COMBAT_DAMAGE, playerB, 18);
        castSpell(1, PhaseStep.FIRST_COMBAT_DAMAGE, playerA, fury, ghoul);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 14);
    }

    @Test
    public void firstStrikeLostMidCombat() {
        addCard(Zone.BATTLEFIELD, playerA, centaur, 1);
        addCard(Zone.BATTLEFIELD, playerA, urchin, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, runemark);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, runemark, centaur);

        checkAbility("first strike gained", 1, PhaseStep.BEGIN_COMBAT, playerA, centaur, FirstStrikeAbility.class, true);

        attack(1, playerA, centaur, playerB);

        checkLife("after first strike", 1, PhaseStep.FIRST_COMBAT_DAMAGE, playerB, 15);
        activateAbility(1, PhaseStep.FIRST_COMBAT_DAMAGE, playerA, "Sacrifice ");
        addTarget(playerA, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 19);
        assertLife(playerB, 15);
        assertAbility(playerA, centaur, FirstStrikeAbility.getInstance(), false);
    }

    @Test
    public void doubleStrikeGainedMidCombat() {
        addCard(Zone.BATTLEFIELD, playerA, knight, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, cleave);

        attack(1, playerA, knight, playerB);

        checkLife("after first strike", 1, PhaseStep.FIRST_COMBAT_DAMAGE, playerB, 18);
        castSpell(1, PhaseStep.FIRST_COMBAT_DAMAGE, playerA, cleave, knight);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 16);
        assertAbility(playerA, knight, DoubleStrikeAbility.getInstance(), true);
    }

    @Test
    public void doubleStrikeLostMidCombat() {
        addCard(Zone.BATTLEFIELD, playerA, wolverine, 1);
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt");
        addCard(Zone.GRAVEYARD, playerA, "Divination");
        addCard(Zone.GRAVEYARD, playerA, "Prey Upon");
        addCard(Zone.BATTLEFIELD, playerA, "Heap Doll"); // sac to exile target from graveyard

        checkAbility("has double strike", 1, PhaseStep.BEGIN_COMBAT, playerA, wolverine, DoubleStrikeAbility.class, true);

        attack(1, playerA, wolverine, playerB);

        checkLife("after first strike", 1, PhaseStep.FIRST_COMBAT_DAMAGE, playerB, 17);
        activateAbility(1, PhaseStep.FIRST_COMBAT_DAMAGE, playerA, "Sacrifice ");
        addTarget(playerA, "Divination");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 17);
        assertAbility(playerA, wolverine, DoubleStrikeAbility.getInstance(), false);
    }

    @Test
    public void firstStrikeLostDoubleStrikeGained() {
        addCard(Zone.BATTLEFIELD, playerA, centaur, 1);
        addCard(Zone.BATTLEFIELD, playerA, urchin, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, runemark);
        addCard(Zone.HAND, playerA, cleave);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, runemark, centaur);

        checkAbility("first strike gained", 1, PhaseStep.BEGIN_COMBAT, playerA, centaur, FirstStrikeAbility.class, true);
        checkAbility("no double strike", 1, PhaseStep.BEGIN_COMBAT, playerA, centaur, DoubleStrikeAbility.class, false);

        attack(1, playerA, centaur, playerB);

        checkLife("after first strike", 1, PhaseStep.FIRST_COMBAT_DAMAGE, playerB, 15);
        activateAbility(1, PhaseStep.FIRST_COMBAT_DAMAGE, playerA, "Sacrifice ");
        addTarget(playerA, playerA);
        waitStackResolved(1, PhaseStep.FIRST_COMBAT_DAMAGE);
        castSpell(1, PhaseStep.FIRST_COMBAT_DAMAGE, playerA, cleave, centaur);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 19);
        assertLife(playerB, 10);
        assertAbility(playerA, centaur, FirstStrikeAbility.getInstance(), false);
        assertAbility(playerA, centaur, DoubleStrikeAbility.getInstance(), true);
    }

    @Test
    public void damageDealtInBetween() {
        // To check that the first strike damage watcher doesn't count noncombat damage

        String fall = "Fall of the Hammer"; // 1R Instant
        // Target creature you control deals damage equal to its power to another target creature.
        String hatchling = "Kraken Hatchling"; // 0/4

        addCard(Zone.BATTLEFIELD, playerA, knight, 1);
        addCard(Zone.BATTLEFIELD, playerA, ghoul, 1);
        addCard(Zone.BATTLEFIELD, playerB, hatchling);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, fall);

        attack(1, playerA, knight, playerB);
        attack(1, playerA, ghoul, playerB);

        checkLife("after first strike", 1, PhaseStep.FIRST_COMBAT_DAMAGE, playerB, 18);
        castSpell(1, PhaseStep.FIRST_COMBAT_DAMAGE, playerA, fall);
        addTarget(playerA, ghoul);
        addTarget(playerA, hatchling);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 15);
        assertDamageReceived(playerB, hatchling, 3);
    }

    @Test
    public void firstStrikeDamagePrevented() {
        String prevention = "Dazzling Reflection"; // 1W Instant
        // You gain life equal to target creature’s power. The next time that creature would deal damage this turn, prevent that damage.

        addCard(Zone.BATTLEFIELD, playerA, knight, 1);
        addCard(Zone.BATTLEFIELD, playerA, ghoul, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, prevention);

        attack(1, playerA, knight, playerB);
        attack(1, playerA, ghoul, playerB);

        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerA, prevention, knight);

        checkLife("life gained", 1, PhaseStep.FIRST_COMBAT_DAMAGE, playerA, 22);
        checkLife("damage prevented", 1, PhaseStep.FIRST_COMBAT_DAMAGE, playerB, 20);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 17);
    }

    @Test
    public void firstStrikeZeroDamage() {
        String rograkh = "Rograkh, Son of Rohgahh"; // 0/1 first strike
        String battlegrowth = "Battlegrowth"; // G: put a +1/+1 counter on target creature

        addCard(Zone.BATTLEFIELD, playerA, rograkh, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, battlegrowth);

        attack(1, playerA, rograkh, playerB);

        castSpell(1, PhaseStep.FIRST_COMBAT_DAMAGE, playerA, battlegrowth, rograkh);
        // no combat damage should be dealt here

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20);
        assertPowerToughness(playerA, rograkh, 1, 2);
    }

    @Test
    public void ninjutsuTwice() {
        /* If a creature in combat has first strike or double strike,
         * you can activate the ninjutsu ability during the first-strike combat damage step.
         * The Ninja will deal combat damage during the regular combat damage step, even if it has first strike.
         */

        String moonblade = "Moonblade Shinobi"; // 3/2, Ninjutsu 2U
        // Whenever Moonblade Shinobi deals combat damage to a player, create a 1/1 blue Illusion creature token with flying.
        String ambusher = "Mukotai Ambusher"; // 3/2 Lifelink; Ninjutsu 1B

        addCard(Zone.BATTLEFIELD, playerA, moonblade, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 5);
        addCard(Zone.HAND, playerA, ambusher);
        addCard(Zone.BATTLEFIELD, playerA, "Celebrity Fencer");
        //  Whenever another creature enters the battlefield under your control, put a +1/+1 counter on Celebrity Fencer.
        addCard(Zone.BATTLEFIELD, playerA, "Knighthood"); // Creatures you control have first strike

        attack(1, playerA, moonblade, playerB);

        checkLife("first strike damage dealt", 1, PhaseStep.FIRST_COMBAT_DAMAGE, playerB, 17);
        activateAbility(1, PhaseStep.FIRST_COMBAT_DAMAGE, playerA, "Ninjutsu {1}{B}");
        setChoice(playerA, moonblade);
        waitStackResolved(1, PhaseStep.FIRST_COMBAT_DAMAGE);
        activateAbility(1, PhaseStep.FIRST_COMBAT_DAMAGE, playerA, "Ninjutsu {2}{U}");
        setChoice(playerA, ambusher);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // the moonblade deals 3 damage each step because it is a different object after zone change
        assertLife(playerA, 20);
        assertLife(playerB, 14);
        assertPermanentCount(playerA, "Illusion Token", 2);
        assertCounterCount(playerA, "Celebrity Fencer", CounterType.P1P1, 4); // two tokens and both ninjutsu
    }

}
