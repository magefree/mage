package org.mage.test.cards.cost.modification;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Playable and casting tests for variable mana costs ({X}, {X}{X}) with minimum X constraints
 * and generic cost reduction effects.
 *
 * @author JayDi85
 */
public class VariableManaCostPlayableTest extends CardTestPlayerBase {

    @Test
    public void test_MinXOneSpell_WithoutCostReduction_MindGrind() {
        // Each opponent reveals cards from the top of their library until they reveal X land cards,
        // then puts all cards revealed this way into their graveyard. X can't be 0.
        addCard(Zone.HAND, playerA, "Mind Grind"); // {X}{U}{B}, minX = 1
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        checkPlayableAbility("not enough mana for X=1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Mind Grind", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_MinXOneSpell_WithCostReduction_MindGrind() {
        skipInitShuffling();

        // Each opponent reveals cards from the top of their library until they reveal X land cards,
        // then puts all cards revealed this way into their graveyard. X can't be 0.
        addCard(Zone.HAND, playerA, "Mind Grind"); // {X}{U}{B}, minX = 1
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        //
        // Instant and sorcery spells you cast cost {1} less to cast.
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Electromancer");
        addCard(Zone.LIBRARY, playerB, "Forest"); // revealed and milled for X=1

        checkPlayableAbility("reduced X=1 is playable", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Mind Grind", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mind Grind");
        setChoice(playerA, "X=1"); // {1}{U}{B} - {1} = {U}{B}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Mind Grind", 1);
        assertGraveyardCount(playerB, "Forest", 1);
        assertTappedCount("Island", true, 1);
        assertTappedCount("Swamp", true, 1);
    }

    @Test
    public void test_MinXOneSpell_WithMultipleCostReductions_MindGrind() {
        skipInitShuffling();

        // Each opponent reveals cards from the top of their library until they reveal X land cards,
        // then puts all cards revealed this way into their graveyard. X can't be 0.
        addCard(Zone.HAND, playerA, "Mind Grind"); // {X}{U}{B}, minX = 1
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        //
        // Instant and sorcery spells you cast cost {1} less to cast.
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Electromancer", 2); // total -{2}
        addCard(Zone.LIBRARY, playerB, "Forest", 2);

        checkPlayableAbility("reduced X=1/X=2 is playable", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Mind Grind", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mind Grind");
        setChoice(playerA, "X=2"); // {2}{U}{B} - {2} = {U}{B}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Mind Grind", 1);
        assertGraveyardCount(playerB, "Forest", 2);
        assertTappedCount("Island", true, 1);
        assertTappedCount("Swamp", true, 1);
    }

    @Test
    public void test_MinXOneSpell_WithCostReduction_StillNeedsColoredMana_MindGrind() {
        // Each opponent reveals cards from the top of their library until they reveal X land cards,
        // then puts all cards revealed this way into their graveyard. X can't be 0.
        addCard(Zone.HAND, playerA, "Mind Grind"); // {X}{U}{B}, minX = 1
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        //
        // Instant and sorcery spells you cast cost {1} less to cast.
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Electromancer");

        checkPlayableAbility("missing black mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Mind Grind", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_MinXOneSpell_WithCostReduction_HigherXStillPaysRemaining_MindGrind() {
        skipInitShuffling();

        // Each opponent reveals cards from the top of their library until they reveal X land cards,
        // then puts all cards revealed this way into their graveyard. X can't be 0.
        addCard(Zone.HAND, playerA, "Mind Grind"); // {X}{U}{B}, minX = 1
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1); // pays remaining {1} of X=2
        //
        // Instant and sorcery spells you cast cost {1} less to cast.
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Electromancer");
        addCard(Zone.LIBRARY, playerB, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mind Grind");
        setChoice(playerA, "X=2"); // {2}{U}{B} - {1} = {1}{U}{B} (must not double-reduce)

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Mind Grind", 1);
        assertGraveyardCount(playerB, "Forest", 2);
        assertTappedCount("Island", true, 1);
        assertTappedCount("Swamp", true, 1);
        assertTappedCount("Mountain", true, 1);
    }

    @Test
    public void test_MinXOneSpell_WithIncreaseAndReduction_MindGrind() {
        // Each opponent reveals cards from the top of their library until they reveal X land cards,
        // then puts all cards revealed this way into their graveyard. X can't be 0.
        addCard(Zone.HAND, playerA, "Mind Grind"); // {X}{U}{B}, minX = 1
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        //
        // Instant and sorcery spells you cast cost {1} less to cast.
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Electromancer");
        // Noncreature spells cost {1} more to cast.
        addCard(Zone.BATTLEFIELD, playerB, "Thalia, Guardian of Thraben");

        // +{1} and -{1} cancel out, so X=1 still needs {1}{U}{B}
        checkPlayableAbility("net zero reduction with Thalia", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Mind Grind", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_MinXOneActivatedAbility_NotPlayableForZeroWithMinOneReducer_HelmOfObedienceWithZirda() {
        // {X}, {T}: Target opponent mills a card, then repeats this process until a creature card
        // or X cards have been put into their graveyard this way, whichever comes first. X can't be 0.
        addCard(Zone.BATTLEFIELD, playerA, "Helm of Obedience"); // {X}, minX = 1
        //
        // Abilities you activate that aren't mana abilities cost {2} less to activate.
        // This effect can't reduce the mana in that cost to less than one mana.
        addCard(Zone.BATTLEFIELD, playerA, "Zirda, the Dawnwaker");

        // 0 available mana -> Zirda cannot reduce {X} below {1}
        checkPlayableAbility("cannot reduce below 1 mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}, {T}:", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_MinXOneActivatedAbility_WithoutEnoughMana_LairOfTheHydra() {
        // {T}: Add {G}.
        // {X}{G}: Until end of turn, Lair of the Hydra becomes an X/X green Hydra creature. It's still a land. X can't be 0.
        addCard(Zone.BATTLEFIELD, playerA, "Lair of the Hydra"); // only 1 mana total

        checkPlayableAbility("not enough mana for X=1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}{G}: Until end of turn", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_MinXOneActivatedAbility_WithEnoughMana_LairOfTheHydra() {
        // {T}: Add {G}.
        // {X}{G}: Until end of turn, Lair of the Hydra becomes an X/X green Hydra creature. It's still a land. X can't be 0.
        addCard(Zone.BATTLEFIELD, playerA, "Lair of the Hydra");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1); // {1}{G} total for X=1

        checkPlayableAbility("enough mana for X=1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}{G}: Until end of turn", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_DoubleXSpell_WithCostReduction_ChaliceOfTheVoid() {
        // Chalice of the Void enters with X charge counters on it.
        addCard(Zone.HAND, playerA, "Chalice of the Void"); // {X}{X}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1); // pays {2} - {1} = {1} for X=1
        //
        // Artifact spells you cast cost {1} less to cast.
        addCard(Zone.BATTLEFIELD, playerA, "Foundry Inspector");

        checkPlayableAbility("Chalice playable", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Chalice of the Void", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chalice of the Void");
        setChoice(playerA, "X=1"); // {X}{X} = {2}, minus {1} = {1}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Chalice of the Void", 1);
        assertCounterCount(playerA, "Chalice of the Void", CounterType.CHARGE, 1);
        assertTappedCount("Island", true, 1);
    }
}
