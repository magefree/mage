package org.mage.test.cards.abilities.oneshot;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Effects for all permanents of a kind (all creatures, all creatures of a type, all attacking creatures, the lands
 * of each player, all creatures a player controls) must find the permanents of every player
 *
 * @author JayDi85
 */
public class AllPermanentsOfKindTest extends CardTestPlayerBase {

    @Test
    public void test_EachPlayerKeepsOneCreature_PromiseOfLoyalty() {
        // Each player puts a vow counter on a creature they control and sacrifices the rest. Each of those creatures
        // can't attack you or planeswalkers you control for as long as it has a vow counter on it.
        addCard(Zone.HAND, playerA, "Promise of Loyalty"); // {4}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Centaur Courser"); // 3/3
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Promise of Loyalty");
        setChoice(playerB, "Centaur Courser"); // keep it with a vow counter

        checkMayAttackDefender("can't attack you", 2, playerB, "Centaur Courser", playerA, false);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerB, "Centaur Courser", CounterType.VOW, 1);
        assertPermanentCount(playerB, "Grizzly Bears", 0);
    }

    @Test
    public void test_DestroyAllOtherCreatures_WhiteSunsTwilight() {
        // You gain X life. Create X 1/1 colorless Phyrexian Mite artifact creature tokens with toxic 1 and "This
        // token can't block." If X is 5 or more, destroy all other creatures.
        addCard(Zone.HAND, playerA, "White Sun's Twilight"); // {X}{W}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5 + 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Centaur Courser"); // 3/3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "White Sun's Twilight");
        setChoice(playerA, "X=5");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Phyrexian Mite Token", 5);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertPermanentCount(playerB, "Centaur Courser", 0);
        assertLife(playerA, 20 + 5);
    }

    @Test
    public void test_DestroyAllOfTypeTargetPlayerControls_TsabosDecree() {
        // Choose a creature type. Target player reveals their hand and discards all creature cards of that type.
        // Then destroy all creatures of that type that player controls. They can't be regenerated.
        addCard(Zone.HAND, playerA, "Tsabo's Decree"); // {5}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Centaur Courser"); // 3/3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tsabo's Decree", playerB);
        setChoice(playerA, "Bear");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerB, "Grizzly Bears", 0);
        assertPermanentCount(playerB, "Centaur Courser", 1);
    }

    @Test
    public void test_AttackingCreaturesBecomeBlocked_FogPatch() {
        // Cast this spell only during the declare blockers step.
        // Attacking creatures become blocked.
        addCard(Zone.HAND, playerB, "Fog Patch"); // {1}{G}
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        attack(1, playerA, "Grizzly Bears");
        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerB, "Fog Patch");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
    }

    @Test
    public void test_DestroyAllOfChosenType_Extinction() {
        // Destroy all creatures of the creature type of your choice.
        addCard(Zone.HAND, playerA, "Extinction"); // {4}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Centaur Courser"); // 3/3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Extinction");
        setChoice(playerA, "Bear");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertPermanentCount(playerB, "Grizzly Bears", 0);
        assertPermanentCount(playerB, "Centaur Courser", 1);
    }

    @Test
    public void test_DamageByNonbasicLandsOfEachPlayer_SunspineLynx() {
        // When this creature enters, it deals damage to each player equal to the number of nonbasic lands that
        // player controls.
        addCard(Zone.HAND, playerA, "Sunspine Lynx"); // {2}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Evolving Wilds", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Evolving Wilds", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sunspine Lynx");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 1);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_DestroyAllCreaturesTargetOpponentControls_AngrathMinotaurPirate() {
        // −11: Destroy all creatures target opponent controls. Angrath deals damage to that player equal to their
        // total power.
        addCard(Zone.BATTLEFIELD, playerA, "Angrath, Minotaur Pirate"); // planeswalker, loyalty 5
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Centaur Courser"); // 3/3

        addCounters(1, PhaseStep.UPKEEP, playerA, "Angrath, Minotaur Pirate", CounterType.LOYALTY, 11 - 5);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-11:", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerB, "Grizzly Bears", 0);
        assertPermanentCount(playerB, "Centaur Courser", 0);
        assertLife(playerB, 20 - 2 - 3);
    }

    @Test
    public void test_UntapAllOfChosenType_RiptideChronologist() {
        // {U}, Sacrifice this creature: Untap all creatures of the creature type of your choice.
        addCard(Zone.BATTLEFIELD, playerA, "Riptide Chronologist");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1, true); // bear 2/2, tapped
        addCard(Zone.BATTLEFIELD, playerB, "Centaur Courser", 1, true); // 3/3, tapped

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{U}, Sacrifice");
        setChoice(playerA, "Bear");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped("Grizzly Bears", false);
        assertTapped("Centaur Courser", true);
    }
}
