package org.mage.test.cards.mana;

import mage.abilities.mana.ManaOptions;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;
import static org.mage.test.utils.ManaOptionsTestUtils.assertManaOptions;

/**
 *
 * @author LevelX2
 */
public class TappedForManaRelatedTest extends CardTestPlayerBase {

    /**
     * This is a new rule that slightly changes how we resolve abilities that
     * trigger whenever a permanent is tapped for mana or for mana of a
     * specified type. Now, you look at what was actually produced after the
     * activated mana ability resolves. So, tapping a Gaea's Cradle while you no
     * control no creatures won't cause a Wild Growth attached to it to trigger.
     */
    @Test
    public void TestCradleWithWildGrowthNoCreatures() {
        // {T}: Add {G} for each creature you control.
        addCard(Zone.BATTLEFIELD, playerA, "Gaea's Cradle", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        // Enchant land
        // Whenever enchanted land is tapped for mana, its controller adds {G}.
        addCard(Zone.HAND, playerA, "Wild Growth", 1); // Enchantment {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wild Growth", "Gaea's Cradle");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Wild Growth", 1);

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{G}{G}", manaOptions);

    }

    @Test
    public void TestCradleWithWildGrowthTwoCreatures() {
        // {T}: Add {G} for each creature you control.
        addCard(Zone.BATTLEFIELD, playerA, "Gaea's Cradle", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 2);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        // Enchant land
        // Whenever enchanted land is tapped for mana, its controller adds {G}.
        addCard(Zone.HAND, playerA, "Wild Growth", 1); // Enchantment {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wild Growth", "Gaea's Cradle");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Wild Growth", 1);

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{G}{G}{G}{G}", manaOptions);

    }

    @Test
    public void TestWildGrowth() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 2);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        // Enchant land
        // Whenever enchanted land is tapped for mana, its controller adds {G}.
        addCard(Zone.HAND, playerA, "Wild Growth", 1); // Enchantment {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wild Growth", "Forest");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Wild Growth", 1);

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{G}{G}", manaOptions);

    }

    @Test
    public void TestCalciformPools() {
        // {T}: Add {C}.
        // {1}, {T}: Put a storage counter on Calciform Pools.
        // {1}, Remove X storage counters from Calciform Pools: Add X mana in any combination of {W} and/or {U}.
        addCard(Zone.BATTLEFIELD, playerA, "Calciform Pools", 1);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{C}", manaOptions);

    }

    @Test
    public void TestCalciformPools2Counter() {
        // {T}: Add {C}.
        // {1}, {T}: Put a storage counter on Calciform Pools.
        // {1}, Remove X storage counters from Calciform Pools: Add X mana in any combination of {W} and/or {U}.
        addCard(Zone.BATTLEFIELD, playerA, "Calciform Pools", 1);
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Calciform Pools", CounterType.STORAGE, 2);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount("Calciform Pools", CounterType.STORAGE, 2);

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 4, manaOptions.size());
        assertManaOptions("{W}{W}", manaOptions);
        assertManaOptions("{W}{U}", manaOptions);
        assertManaOptions("{U}{U}", manaOptions);
        assertManaOptions("{C}", manaOptions);
    }

    @Test
    public void TestCalciformPools2CounterAndTrigger() {
        setStrictChooseMode(true);
        // {T}: Add {C}.
        // {1}, {T}: Put a storage counter on Calciform Pools.
        // {1}, Remove X storage counters from Calciform Pools: Add X mana in any combination of {W} and/or {U}.
        addCard(Zone.BATTLEFIELD, playerA, "Calciform Pools", 1);
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Calciform Pools", CounterType.STORAGE, 2);

        // As Caged Sun enters the battlefield, choose a color.
        // Creatures you control of the chosen color get +1/+1.
        // Whenever a land's ability adds one or more mana of the chosen color, add one additional mana of that color.
        addCard(Zone.BATTLEFIELD, playerA, "Caged Sun", 1);
        setChoice(playerA, "White");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();

        assertCounterCount("Calciform Pools", CounterType.STORAGE, 2);

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 4, manaOptions.size());
        assertManaOptions("{W}{W}{W}", manaOptions);
        assertManaOptions("{W}{W}{U}", manaOptions);
        assertManaOptions("{U}{U}", manaOptions);
        assertManaOptions("{C}", manaOptions);
    }

    @Test
    public void TestCastleSengir() {
        setStrictChooseMode(true);
        // {T}: Add Colorless.
        // {1}, {T}: Add Black.
        // {2}, {T}: Add Blue or Red.
        addCard(Zone.BATTLEFIELD, playerA, "Castle Sengir", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 2, manaOptions.size());
        assertManaOptions("{C}{R}", manaOptions);
        assertManaOptions("{B}", manaOptions);
    }

    @Test
    public void TestCastleSengir2() {
        setStrictChooseMode(true);
        // {T}: Add {C}.
        // {1}, {T}: Add {B}.
        // {2}, {T}: Add {U} or {R}.
        addCard(Zone.BATTLEFIELD, playerA, "Castle Sengir", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 4, manaOptions.size());
        assertManaOptions("{C}{W}{W}", manaOptions);
        assertManaOptions("{W}{B}", manaOptions);
        assertManaOptions("{U}", manaOptions);
        assertManaOptions("{R}", manaOptions);
    }

    @Test
    public void TestCrystallineCrawler() {
        setStrictChooseMode(true);
        // Converge - Crystalline Crawler enters the battlefield with a +1/+1 counter on it for each color of mana spent to cast it.
        // Remove a +1/+1 counter from Crystalline Crawler: Add one mana of any color.
        // {T}: Put a +1/+1 counter on Crystalline Crawler.
        addCard(Zone.BATTLEFIELD, playerA, "Crystalline Crawler", 1);
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crystalline Crawler", CounterType.P1P1, 2);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{Any}{Any}", manaOptions);
    }

    @Test
    @Ignore  // Because this is no mana ability, this mana will not be calculated during available mana calculation
    public void TestDeathriteShaman() {
        setStrictChooseMode(true);
        // {T}: Exile target land card from a graveyard. Add one mana of any color.
        // {B}, {T}: Exile target instant or sorcery card from a graveyard. Each opponent loses 2 life.
        // {G}, {T}: Exile target creature card from a graveyard. You gain 2 life.
        addCard(Zone.BATTLEFIELD, playerA, "Deathrite Shaman", 1);

        addCard(Zone.GRAVEYARD, playerA, "Mountain", 3);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{Any}", manaOptions);
    }

    @Test
    public void TestEyeOfRamos() {
        setStrictChooseMode(true);
        // {T}: Add {U}.
        // Sacrifice Eye of Ramos: Add {U}.
        addCard(Zone.BATTLEFIELD, playerA, "Eye of Ramos", 2);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{U}{U}{U}{U}", manaOptions);
    }

    @Test
    public void TestFarrelitePriest() {
        setStrictChooseMode(true);
        // {1}: Add {W}. If this ability has been activated four or more times this turn, sacrifice Farrelite Priest at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Farrelite Priest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 5, manaOptions.size());
        assertManaOptions("{W}{W}{W}{W}", manaOptions);
        assertManaOptions("{W}{W}{W}{B}", manaOptions);
        assertManaOptions("{W}{W}{B}{B}", manaOptions);
        assertManaOptions("{W}{B}{B}{B}", manaOptions);
        assertManaOptions("{B}{B}{B}{B}", manaOptions);
    }

    @Test
    public void TestMorselhoarder() {
        setStrictChooseMode(true);
        // Morselhoarder enters the battlefield with two -1/-1 counters on it.
        // Remove a -1/-1 counter from Morselhoarder: Add one mana of any color.
        addCard(Zone.BATTLEFIELD, playerA, "Morselhoarder", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{B}{B}{Any}{Any}{Any}{Any}", manaOptions);
    }

    @Test
    public void TestChromaticOrrery() {
        setStrictChooseMode(true);
        // You may spend mana as though it were mana of any color.
        // {T}: Add {C}{C}{C}{C}{C}.
        // {5}, {T}: Draw a card for each color among permanents you control.
        addCard(Zone.BATTLEFIELD, playerA, "Chromatic Orrery", 1);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{C}{C}{C}{C}{C}", manaOptions);
    }
}
