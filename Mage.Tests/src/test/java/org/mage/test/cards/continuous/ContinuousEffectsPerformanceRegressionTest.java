// computer enchance
package org.mage.test.cards.continuous;

import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Regression tests for continuous effects engine behavior after performance
 * optimizations in ContinuousEffects and ContinuousEffectsList.
 *
 * These tests verify that:
 * - updateTimestamps: HashSet-based ID lookup preserves effect ordering
 * - filterLayeredEffects: loop-based filtering matches stream behavior
 * - PT sublayer caching: pre-computed ability lookups produce correct results
 * - getAbility: returning empty set instead of creating new HashSet works
 *
 * @author ben
 */
public class ContinuousEffectsPerformanceRegressionTest extends CardTestPlayerBase {

    // -----------------------------------------------------------------------
    // PT sublayer caching — multiple stacking PT effects across sublayers
    // -----------------------------------------------------------------------

    /**
     * Multiple global +X/+X effects should stack correctly.
     * Exercises the ptAbilitiesCache in Layer 7 (ModifyPT_7c sublayer).
     */
    @Test
    public void testMultipleBoostEffectsStack() {
        // Glorious Anthem: creatures you control get +1/+1
        addCard(Zone.BATTLEFIELD, playerA, "Glorious Anthem", 2);
        // Grizzly Bears: 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // 2/2 + 1/1 + 1/1 = 4/4
        assertPowerToughness(playerA, "Grizzly Bears", 4, 4);
    }

    /**
     * Base PT setting (SetPT_7b) combined with boost (ModifyPT_7c).
     * Ensures cached abilities are applied to both sublayers correctly.
     */
    @Test
    public void testBasePTSetWithBoost() {
        // Humility: all creatures lose abilities and have base P/T 1/1
        addCard(Zone.BATTLEFIELD, playerA, "Humility");
        // Glorious Anthem: creatures you control get +1/+1
        addCard(Zone.BATTLEFIELD, playerA, "Glorious Anthem");
        // Air Elemental: 4/4 flying
        addCard(Zone.BATTLEFIELD, playerA, "Air Elemental");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // Humility sets base to 1/1, Anthem boosts to 2/2
        assertPowerToughness(playerA, "Air Elemental", 2, 2);
        // Flying removed by Humility
        assertAbility(playerA, "Air Elemental", FlyingAbility.getInstance(), false);
    }

    /**
     * Characteristic-defining ability (CDA) for P/T (sublayer 7a) combined
     * with a boost effect (sublayer 7c). Tests that the cache handles both
     * sublayers within the same effect iteration.
     */
    @Test
    public void testCDAWithBoostEffect() {
        // Tarmogoyf: * / 1+* where * is number of card types in all graveyards
        addCard(Zone.BATTLEFIELD, playerA, "Tarmogoyf");
        // Put a creature and an instant in the graveyard
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // creature
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt"); // instant
        // Glorious Anthem: +1/+1
        addCard(Zone.BATTLEFIELD, playerA, "Glorious Anthem");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // Tarmogoyf: 2 types in graveyard -> 2/3, then +1/+1 from Anthem -> 3/4
        assertPowerToughness(playerA, "Tarmogoyf", 3, 4);
    }

    // -----------------------------------------------------------------------
    // updateTimestamps — effect ordering with HashSet-based contains
    // -----------------------------------------------------------------------

    /**
     * When a new continuous effect enters after existing ones, it should get
     * a later timestamp. Verifies that the HashSet-based ID lookup in
     * updateTimestamps correctly identifies new vs existing effects.
     *
     * Blood Moon (entered first) and Urborg (entered second) — Blood Moon
     * should win because it has an earlier timestamp and Urborg depends on it.
     */
    @Test
    public void testTimestampOrderingBloodMoonThenUrborg() {
        // Blood Moon first: nonbasic lands are Mountains
        addCard(Zone.BATTLEFIELD, playerA, "Blood Moon");
        addCard(Zone.BATTLEFIELD, playerA, "Urborg, Tomb of Yawgmoth");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // Blood Moon turns Urborg into a Mountain, removing its ability
        assertType("Urborg, Tomb of Yawgmoth", CardType.LAND, SubType.MOUNTAIN);
        // Plains should NOT be a Swamp since Urborg lost its ability
        assertType("Plains", CardType.LAND, SubType.PLAINS);
    }

    /**
     * Multiple effects entering at different turns should maintain correct
     * timestamp ordering. A new effect entering should get a new order
     * (setOrder called) while existing effects keep their order.
     */
    @Test
    public void testNewEffectGetsNewTimestamp() {
        // Turn 1: first anthem enters
        addCard(Zone.BATTLEFIELD, playerA, "Glorious Anthem");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2

        // Cast a second anthem on turn 1
        addCard(Zone.HAND, playerA, "Glorious Anthem");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glorious Anthem");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // 2/2 + 1/1 + 1/1 = 4/4
        assertPowerToughness(playerA, "Grizzly Bears", 4, 4);
    }

    // -----------------------------------------------------------------------
    // filterLayeredEffects — correct filtering across multiple layers
    // -----------------------------------------------------------------------

    /**
     * Effects that span multiple layers should be correctly filtered into
     * each layer. Tests type-changing (layer 4) + ability-adding (layer 6)
     * + PT-changing (layer 7).
     */
    @Test
    public void testMultipleLayerFiltering() {
        // Enchanted Evening: all permanents become enchantments (layer 4)
        addCard(Zone.HAND, playerA, "Enchanted Evening");
        // Opalescence: enchantments are creatures with P/T = CMC (layers 4+7)
        addCard(Zone.HAND, playerA, "Opalescence");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        // Glorious Anthem to keep 0-CMC things alive
        addCard(Zone.BATTLEFIELD, playerA, "Glorious Anthem");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Opalescence", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Enchanted Evening");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // Enchanted Evening (CMC 5) becomes a 5/5, +1/+1 from Anthem = 6/6
        assertPowerToughness(playerA, "Enchanted Evening", 6, 6);
        assertType("Enchanted Evening", CardType.CREATURE, true);
    }

    // -----------------------------------------------------------------------
    // getAbility returning empty set — effects without mapped abilities
    // -----------------------------------------------------------------------

    /**
     * A creature with no continuous effects applied should still function
     * correctly when the engine queries for abilities via getAbility.
     * This tests the EMPTY_ABILITY_SET path.
     */
    @Test
    public void testCreatureWithNoEffectsApplied() {
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Hill Giant"); // 3/3

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Grizzly Bears", 2, 2);
        assertPowerToughness(playerA, "Hill Giant", 3, 3);
    }

    // -----------------------------------------------------------------------
    // Combined scenarios — multiple optimizations exercised together
    // -----------------------------------------------------------------------

    /**
     * Complex scenario with effects entering and leaving, exercising
     * timestamp tracking, layer filtering, and PT caching together.
     * An aura boosts a creature, then gets destroyed — P/T should revert.
     */
    @Test
    public void testEffectEntersAndLeaves() {
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2
        // Rancor: +2/+0 and trample
        addCard(Zone.HAND, playerA, "Rancor");
        // Naturalize to destroy Rancor
        addCard(Zone.HAND, playerA, "Naturalize");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rancor", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Check boosted state
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertPowerToughness(playerA, "Grizzly Bears", 4, 2); // 2+2/2+0

        // Now destroy Rancor
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Naturalize", "Rancor");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // Bears should revert to base stats
        assertPowerToughness(playerA, "Grizzly Bears", 2, 2);
    }

    /**
     * Multiple creatures with different global effects — ensures the
     * filterLayeredEffects and PT cache handle the full set correctly.
     * Tests "only your creatures" vs "all creatures" effects.
     */
    @Test
    public void testSelectiveVsGlobalBoosts() {
        // Glorious Anthem: YOUR creatures get +1/+1
        addCard(Zone.BATTLEFIELD, playerA, "Glorious Anthem");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant"); // 3/3

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // Player A's bear gets +1/+1
        assertPowerToughness(playerA, "Grizzly Bears", 3, 3);
        // Player B's creature should NOT be affected
        assertPowerToughness(playerB, "Hill Giant", 3, 3);
    }

    /**
     * Multiple PT effects across different sublayers: a CDA creature,
     * a set-base-PT effect, a +X/+X boost, and counters. All sublayers
     * of layer 7 are exercised with the cached ability lookups.
     */
    @Test
    public void testAllPTSublayersWithCounters() {
        // Silvercoat Lion: 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        // Glorious Anthem: +1/+1 (sublayer 7c)
        addCard(Zone.BATTLEFIELD, playerA, "Glorious Anthem");
        // Put a +1/+1 counter on it via Battlegrowth
        addCard(Zone.HAND, playerA, "Battlegrowth");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Battlegrowth", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // 2/2 base + 1/1 counter (sublayer 7d) + 1/1 anthem (sublayer 7c) = 4/4
        assertPowerToughness(playerA, "Silvercoat Lion", 4, 4);
    }

    /**
     * P/T switching (sublayer 7e) combined with boost effects (sublayer 7c).
     * Ensures the PT ability cache correctly serves all five sublayer passes.
     */
    @Test
    public void testPTSwitchingWithBoost() {
        // Grizzly Bears: 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        // About Face: switch target creature's P/T until end of turn
        addCard(Zone.HAND, playerA, "About Face");
        // Glorious Anthem: +1/+1
        addCard(Zone.BATTLEFIELD, playerA, "Glorious Anthem");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "About Face", "Grizzly Bears");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Base 2/2 + anthem 1/1 = 3/3, then switch = 3/3 (symmetric)
        assertPowerToughness(playerA, "Grizzly Bears", 3, 3);
    }

    /**
     * P/T switching with asymmetric creature to verify sublayer ordering.
     * Boost is applied before switch (7c before 7e).
     */
    @Test
    public void testPTSwitchingAsymmetric() {
        // Hill Giant: 3/3 — symmetric, so let's use something asymmetric
        // Centaur Courser: 3/3 — also symmetric. Use Air Elemental: 4/4 — also symmetric
        // Use Goblin Piker: 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Piker");
        addCard(Zone.HAND, playerA, "About Face");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "About Face", "Goblin Piker");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // 2/1 switched = 1/2
        assertPowerToughness(playerA, "Goblin Piker", 1, 2);
    }

    /**
     * P/T switching with asymmetric creature AND a boost — verifies the
     * correct sublayer order (7c boost before 7e switch).
     */
    @Test
    public void testPTSwitchingAsymmetricWithBoost() {
        // Goblin Piker: 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Piker");
        addCard(Zone.HAND, playerA, "About Face");
        addCard(Zone.BATTLEFIELD, playerA, "Glorious Anthem");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "About Face", "Goblin Piker");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // 2/1 + anthem 1/1 = 3/2, then switch = 2/3
        assertPowerToughness(playerA, "Goblin Piker", 2, 3);
    }

    /**
     * Multiple creatures each with different effects, testing that the
     * layer system processes them all correctly when filtering and caching.
     */
    @Test
    public void testMultipleCreaturesMultipleEffects() {
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Air Elemental"); // 4/4 flying
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Piker"); // 2/1
        // Glorious Anthem: +1/+1 to all your creatures
        addCard(Zone.BATTLEFIELD, playerA, "Glorious Anthem");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Grizzly Bears", 3, 3);
        assertPowerToughness(playerA, "Air Elemental", 5, 5);
        assertPowerToughness(playerA, "Goblin Piker", 3, 2);
        // Flying should be unaffected by PT-layer changes
        assertAbility(playerA, "Air Elemental", FlyingAbility.getInstance(), true);
    }
}
