package org.mage.test.cards.prevention;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class PreventDamageRemoveCountersTest extends CardTestPlayerBase {

    @Test
    public void test_OathswornKnight_CounterRemoval() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Flame Slash", 1);
        addCard(Zone.HAND, playerA, "Shock", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Oathsworn Knight", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Polukranos, Unchained", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flame Slash", "Oathsworn Knight");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "Polukranos, Unchained");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Oathsworn Knight", 1);
        assertPermanentCount(playerA, "Polukranos, Unchained", 1);
        assertPowerToughness(playerA, "Oathsworn Knight", 3, 3); // 1 counter removed
        assertPowerToughness(playerA, "Polukranos, Unchained", 4, 4); // 2 counters removed

    }

    @Test
    public void test_MagmaPummeler() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);
        addCard(Zone.HAND, playerA, "Magma Pummeler", 1);
        addCard(Zone.HAND, playerA, "Shock", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Magma Pummeler");
        setChoice(playerA, "X=5");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "Magma Pummeler");
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Magma Pummeler", 1);
        assertPowerToughness(playerA, "Magma Pummeler", 3, 3); // 2 counters removed
        assertLife(playerB, 18); // 2 damage dealt
    }

    @Test
    public void test_MagmaPummeler_DoubleBlocked() {
        // The part of this one that is weird is that there should be only a single trigger, that sums
        // all the counter removed by multiple prevention effects occuring at the same time.
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);
        addCard(Zone.HAND, playerA, "Magma Pummeler", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Magma Pummeler");
        setChoice(playerA, "X=5");

        attack(3, playerA, "Magma Pummeler", playerB);
        block(3, playerB, "Memnite", "Magma Pummeler");
        block(3, playerB, "Goblin Piker", "Magma Pummeler");
        setChoice(playerA, "X=5"); // damage for Pummeler, does not really matter for this test.
        addTarget(playerA, playerB); // For the one trigger

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Magma Pummeler", 1);
        assertPowerToughness(playerA, "Magma Pummeler", 2, 2); // 3 counters removed
        assertLife(playerB, 20 - 3); // 3 damage dealt by the 1 trigger.
    }

    @Test
    public void test_MagmaPummeler_KilledByMore() {

        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7 + 5);
        addCard(Zone.HAND, playerA, "Magma Pummeler", 1);
        addCard(Zone.HAND, playerA, "Shivan Meteor", 1); // 13 damage to target creature

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Magma Pummeler");
        setChoice(playerA, "X=5");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shivan Meteor", "Magma Pummeler");
        addTarget(playerA, playerB); // For the reflective trigger

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Magma Pummeler", 1);
        assertGraveyardCount(playerA, "Shivan Meteor", 1);
        assertLife(playerB, 20 - 5); // 5 counters removed in total.
    }

    @Test
    public void test_MagmaPummeler_DoubleBlocked_And_Die() {
        // The part of this one that is weird is that there should be only a single trigger, that sums
        // all the counter removed by multiple prevention effects occuring at the same time.
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);
        addCard(Zone.HAND, playerA, "Magma Pummeler", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Centaur Courser", 1); // 3/3
        addCard(Zone.BATTLEFIELD, playerB, "Air Elemental", 1); // 4/4
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Magma Pummeler");
        setChoice(playerA, "X=5");

        attack(3, playerA, "Magma Pummeler", playerB);
        block(3, playerB, "Centaur Courser", "Magma Pummeler");
        block(3, playerB, "Air Elemental", "Magma Pummeler");
        setChoice(playerA, "X=5"); // damage for Pummeler, does not really matter for this test.
        addTarget(playerA, playerB); // For the one trigger

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Magma Pummeler", 1);
        assertLife(playerB, 20 - 5); // 5 counters prevented, Pummeler's trigger dealt 5.
    }


    @Test
    public void test_UndergrowthChampion_DoubleBlocked() {
        setStrictChooseMode(true);

        // Undergrowth Champion {1}{G}{G}
        // Creature — Elemental
        // If damage would be dealt to Undergrowth Champion while it has a +1/+1 counter on it, prevent that damage and remove a +1/+1 counter from Undergrowth Champion.
        // Landfall — Whenever a land enters the battlefield under your control, put a +1/+1 counter on Undergrowth Champion.
        // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Undergrowth Champion");
        addCard(Zone.HAND, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plains");
        // Champion now has a +1/+1 counter

        attack(1, playerA, "Undergrowth Champion", playerB);
        block(1, playerB, "Grizzly Bears", "Undergrowth Champion");
        block(1, playerB, "Elite Vanguard", "Undergrowth Champion");
        setChoice(playerA, "X=2"); // damage attribution

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerB, 2);
        assertDamageReceived(playerA, "Undergrowth Champion", 0); // All the damage should be prevented.
        assertPowerToughness(playerA, "Undergrowth Champion", 2, 2);
    }

    @Test
    public void test_ProteanHydra_Boosted() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Glorious Anthem", 1);
        addCard(Zone.HAND, playerA, "Protean Hydra", 1);
        addCard(Zone.HAND, playerA, "Hornet Sting", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Protean Hydra");
        setChoice(playerA, "X=0");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hornet Sting", "Protean Hydra");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Protean Hydra", 1);
        assertPowerToughness(playerA, "Protean Hydra", 1, 1);

    }

}
