package org.mage.test.cards.cost.modification;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Spells with a conditional cost reduction must not be playable while the condition is false and only the reduced
 * cost can be paid (the condition is known before the spell is put on the stack)
 *
 * @author JayDi85
 */
public class CostReductionConditionPlayableTest extends CardTestPlayerBase {

    @Test
    public void test_AnotherSpellThisTurn_FocusTheMind() {
        // This spell costs {2} less to cast if you've cast another spell this turn.
        addCard(Zone.HAND, playerA, "Focus the Mind"); // {4}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5 - 2);
        //
        addCard(Zone.HAND, playerA, "Ornithopter"); // {0}

        checkPlayableAbility("no other spell", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Focus the Mind", false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ornithopter");
        checkPlayableAbility("after other spell", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Focus the Mind", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_AnotherSpellThisTurn_GigastormTitan() {
        // This spell costs {3} less to cast if you've cast another spell this turn.
        addCard(Zone.HAND, playerA, "Gigastorm Titan"); // {4}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5 - 3);

        checkPlayableAbility("no other spell", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gigastorm Titan", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_AnotherSpellThisTurn_RallyTheMonastery() {
        // This spell costs {2} less to cast if you've cast another spell this turn.
        addCard(Zone.HAND, playerA, "Rally the Monastery"); // {3}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4 - 2);

        checkPlayableAbility("no other spell", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Rally the Monastery", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_AnotherInstantOrSorceryThisTurn_MageDuel() {
        // This spell costs {2} less to cast if you've cast another instant or sorcery spell this turn.
        addCard(Zone.HAND, playerA, "Mage Duel"); // {2}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3 - 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        checkPlayableAbility("no other spell", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Mage Duel", false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        checkPlayableAbility("after instant", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Mage Duel", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_ControlSpiritOrEnchantment_GeistlightSnare() {
        // This spell costs {1} less to cast if you control a Spirit. It also costs {1} less to cast if you control an
        // enchantment.
        addCard(Zone.HAND, playerA, "Geistlight Snare"); // {2}{U}, counter target spell
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3 - 1);
        //
        addCard(Zone.HAND, playerB, "Grizzly Bears"); // {1}{G}
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Grizzly Bears");
        checkPlayableAbility("no spirit and enchantment", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Geistlight Snare", false);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_OpponentsControlCreatures_LashwhipPredator() {
        // This spell costs {2} less to cast if your opponents control three or more creatures.
        addCard(Zone.HAND, playerA, "Lashwhip Predator"); // {4}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6 - 2);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 2); // bear 2/2

        checkPlayableAbility("two creatures", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lashwhip Predator", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_GainedLifeThisTurn_MortalitySpear() {
        // This spell costs {2} less to cast if you gained life this turn.
        addCard(Zone.HAND, playerA, "Mortality Spear"); // {2}{B}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2

        checkPlayableAbility("no life gain", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Mortality Spear", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_InstantsAndSorceriesInGraveyard_OctaviaLivingThesis() {
        // This spell costs {8} less to cast if you have eight or more instant and/or sorcery cards in your graveyard.
        addCard(Zone.HAND, playerA, "Octavia, Living Thesis"); // {8}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10 - 8);
        //
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt", 8 - 1);

        checkPlayableAbility("seven cards", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Octavia, Living Thesis", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_CreatureWithCounter_PrehistoricTurtlesaurus() {
        // This spell costs {1} less to cast if you control a creature with a +1/+1 counter on it.
        addCard(Zone.HAND, playerA, "Prehistoric Turtlesaurus"); // {4}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5 - 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2 without counters

        checkPlayableAbility("no counters", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Prehistoric Turtlesaurus", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_CreatureAttackingYou_SwatAway() {
        // This spell costs {2} less to cast if a creature is attacking you.
        addCard(Zone.HAND, playerA, "Swat Away"); // {2}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4 - 2);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2

        checkPlayableAbility("no attack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Swat Away", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_LifeFiveOrLess_TheEnd() {
        // This spell costs {2} less to cast if your life total is 5 or less.
        addCard(Zone.HAND, playerA, "The End"); // {2}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4 - 2);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2

        checkPlayableAbility("life 20", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast The End", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_ControlVillain_VenomsHunger() {
        // This spell costs {2} less to cast if you control a Villain.
        addCard(Zone.HAND, playerA, "Venom's Hunger"); // {4}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5 - 2);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2

        checkPlayableAbility("no villain", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Venom's Hunger", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_OpponentControlsNoBasicLands_HagraMauling() {
        // This spell costs {1} less to cast if an opponent controls no basic lands.
        addCard(Zone.HAND, playerA, "Hagra Mauling"); // {2}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4 - 1);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Plains"); // basic land

        checkPlayableAbility("opponent with basic land", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Hagra Mauling", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_ControlWolfOrWerewolf_WolfkinOutcast() {
        // This spell costs {2} less to cast if you control a Wolf or Werewolf.
        addCard(Zone.HAND, playerA, "Wolfkin Outcast"); // {5}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6 - 2);

        checkPlayableAbility("no wolf", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Wolfkin Outcast", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
