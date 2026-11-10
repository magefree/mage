package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * A phased-out permanent is treated as though it doesn't exist, so effects must use its last known information,
 * as for a permanent that is no longer on the battlefield (see 702.2e, 702.15c, 702.90d, 701.44c, 701.50b).
 *
 * @author DarioBarrabinoDev
 */
public class PhasingLastKnownInformationTest extends CardTestPlayerBase {

    // Target artifact, creature, or land phases out.
    private static final String ripple = "Reality Ripple";
    // {T}: Prodigal Pyromancer deals 1 damage to any target.
    private static final String pyromancer = "Prodigal Pyromancer";
    private static final String ping = "{T}: {this} deals 1 damage";

    @Test
    public void test_Damage_PhasedOutSource_DeathtouchAndLifelinkFromEquipment() {
        // Equipped creature has deathtouch and lifelink. Equip {2}
        addCard(Zone.BATTLEFIELD, playerA, "Basilisk Collar");
        addCard(Zone.BATTLEFIELD, playerA, pyromancer);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, ripple);
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant"); // 3/3

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {2}", pyromancer);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        // the Collar phases out indirectly with the creature, before the damage is dealt
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, ping, "Hill Giant");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ripple, pyromancer);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Hill Giant", 1);
        assertLife(playerA, 20 + 1);
    }

    @Test
    public void test_Damage_PhasedOutSource_InfectFromEquipmentToCreature() {
        // Equipped creature gets +2/+2 and has infect. Equip {2}
        addCard(Zone.BATTLEFIELD, playerA, "Grafted Exoskeleton");
        addCard(Zone.BATTLEFIELD, playerA, pyromancer);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, ripple);
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant"); // 3/3

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {2}", pyromancer);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, ping, "Hill Giant");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ripple, pyromancer);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerB, "Hill Giant", CounterType.M1M1, 1);
        assertPowerToughness(playerB, "Hill Giant", 3 - 1, 3 - 1);
    }

    @Test
    public void test_Damage_PhasedOutSource_InfectFromEquipment() {
        // Equipped creature gets +2/+2 and has infect. Equip {2}
        addCard(Zone.BATTLEFIELD, playerA, "Grafted Exoskeleton");
        addCard(Zone.BATTLEFIELD, playerA, pyromancer);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, ripple);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {2}", pyromancer);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, ping, playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ripple, pyromancer);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        assertCounterCount(playerB, CounterType.POISON, 1);
    }

    @Test
    public void test_Explore_PhasedOutBeforeExploring() {
        // When Merfolk Branchwalker enters, it explores.
        addCard(Zone.HAND, playerA, "Merfolk Branchwalker"); // {1}{G}, 2/1
        // Whenever a creature you control explores, put a +1/+1 counter on Wildgrowth Walker and you gain 3 life.
        addCard(Zone.BATTLEFIELD, playerA, "Wildgrowth Walker"); // 1/3
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, ripple);
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears"); // nonland on top

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Merfolk Branchwalker");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1); // resolve the creature, keep the explore trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ripple, "Merfolk Branchwalker");
        setChoice(playerA, true); // put Grizzly Bears into the graveyard

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN); // Branchwalker phases in on turn 3
        execute();

        // it still explored (Wildgrowth Walker sees it), but the counter can't be put on a phased-out permanent
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertCounterCount(playerA, "Wildgrowth Walker", CounterType.P1P1, 1);
        assertLife(playerA, 20 + 3);
        assertCounterCount(playerA, "Merfolk Branchwalker", CounterType.P1P1, 0);
        assertPowerToughness(playerA, "Merfolk Branchwalker", 2, 1);
    }

    @Test
    public void test_Connive_PhasedOutBeforeConniving() {
        // When Psychic Pickpocket enters, it connives.
        // When it connives this way, return up to one target nonland permanent to its owner's hand.
        addCard(Zone.HAND, playerA, "Psychic Pickpocket"); // {4}{U}, 3/2
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Zone.HAND, playerA, ripple);
        addCard(Zone.HAND, playerA, "Grizzly Bears"); // nonland to discard
        addCard(Zone.BATTLEFIELD, playerB, "Sol Ring");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Psychic Pickpocket");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1); // resolve the creature, keep the connive trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ripple, "Psychic Pickpocket");
        setChoice(playerA, "Grizzly Bears"); // to discard
        addTarget(playerA, "Sol Ring");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN); // Pickpocket phases in on turn 3
        execute();

        // it still connived (the reflexive trigger works), but the counter can't be put on a phased-out permanent
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertHandCount(playerB, "Sol Ring", 1);
        assertCounterCount(playerA, "Psychic Pickpocket", CounterType.P1P1, 0);
        assertPowerToughness(playerA, "Psychic Pickpocket", 3, 2);
    }

    @Test
    public void test_Power_PhasedOutSource_CardOwnLookup() {
        // {1}{R}{R}: Spikeshot Elder deals damage equal to its power to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Spikeshot Elder"); // 1/1
        // Equipped creature gets +2/+0. Equip {1}
        addCard(Zone.BATTLEFIELD, playerA, "Bonesplitter");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, ripple);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {1}", "Spikeshot Elder");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        // the equipment phases out indirectly with the creature, so only the LKI still has power 3
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{R}{R}: {this} deals damage", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ripple, "Spikeshot Elder");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 3);
    }

    @Test
    public void test_Power_PhasedOutSource_SourcePermanentOrLKI() {
        // {B}, {T}: Tap target creature. Abyssal Hunter deals damage equal to Abyssal Hunter's power to that creature.
        addCard(Zone.BATTLEFIELD, playerA, "Abyssal Hunter"); // 1/1
        // Equipped creature gets +2/+0. Equip {1}
        addCard(Zone.BATTLEFIELD, playerA, "Bonesplitter");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, ripple);
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant"); // 3/3

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {1}", "Abyssal Hunter");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{B}, {T}: Tap target creature", "Hill Giant");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ripple, "Abyssal Hunter");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Hill Giant", 1);
    }

    @Test
    public void test_Power_PhasedOutTarget_FixedTarget() {
        // Whenever another creature enters, you may put X +1/+1 counters on this creature, where X is that creature's power.
        addCard(Zone.BATTLEFIELD, playerA, "Hamletback Goliath"); // 6/6
        // Creatures you control get +1/+1.
        addCard(Zone.BATTLEFIELD, playerA, "Glorious Anthem");
        addCard(Zone.HAND, playerA, "Grizzly Bears"); // {1}{G}, 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, ripple);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1); // resolve the creature, keep the Goliath trigger
        // the Anthem no longer applies to the phased-out Bears, so only the LKI still has power 3
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ripple, "Grizzly Bears");
        setChoice(playerA, true); // put the counters

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Hamletback Goliath", CounterType.P1P1, 3);
        assertPowerToughness(playerA, "Hamletback Goliath", 6 + 1 + 3, 6 + 1 + 3);
    }
}
