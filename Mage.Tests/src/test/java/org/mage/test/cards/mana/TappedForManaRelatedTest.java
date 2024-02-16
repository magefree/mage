package org.mage.test.cards.mana;

import mage.abilities.mana.ManaOptions;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.Permanent;
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

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

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

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 4, manaOptions.size());
        assertManaOptions("{C}{W}{W}", manaOptions);
        assertManaOptions("{W}{B}", manaOptions);
        assertManaOptions("{U}", manaOptions);
        assertManaOptions("{R}", manaOptions);
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

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{U}{U}{U}{U}", manaOptions);
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

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{C}{C}{C}{C}{C}", manaOptions);
    }

    @Test
    public void TestViridianJoiner() {
        setStrictChooseMode(true);
        
        // {T}: Add an amount of {G} equal to Viridian Joiner's power.
        addCard(Zone.HAND, playerA, "Viridian Joiner", 1); // Creature {2}{G}  1/2
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 1);
        addCard(Zone.HAND, playerB, "Giant Growth", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA,  "Viridian Joiner");
        
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerB,  "Giant Growth", "Viridian Joiner");
        
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Giant Growth", 1);
        assertPowerToughness(playerA, "Viridian Joiner", 4, 5);
        
        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{G}{G}{G}{G}{G}{G}{G}", manaOptions);
    }    
    
    @Test
    public void TestPriestOfYawgmoth() {
        setStrictChooseMode(true);
        
        // {T}, Sacrifice an artifact: Add an amount of {B} equal to the sacrificed artifact's converted mana cost.     
        addCard(Zone.BATTLEFIELD, playerA, "Priest of Yawgmoth", 1); // Creature {1}{B}  1/2
        
        addCard(Zone.BATTLEFIELD, playerA, "Abandoned Sarcophagus", 1); // {3}
        addCard(Zone.BATTLEFIELD, playerA, "Accorder's Shield", 1);  // {0}
        addCard(Zone.BATTLEFIELD, playerA, "Adarkar Sentinel", 1); // {5}

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{B}{B}{B}{B}{B}", manaOptions);
    }    

    @Test
    public void TestParadiseMantle() {
        setStrictChooseMode(true);
        
        // Equipped creature has "{T}: Add one mana of any color."
        // Equip {1}
        addCard(Zone.BATTLEFIELD, playerA, "Paradise Mantle", 1); // Creature {1}{B}  1/2

        // Flying;
        // {2}, {untap}: Add one mana of any color.        
        addCard(Zone.BATTLEFIELD, playerA, "Pili-Pala", 1); // Artifact Creature (1/1)
        
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);  

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip");
        addTarget(playerA, "Pili-Pala"); // Select a creature you control
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        Permanent pp = getPermanent("Pili-Pala");
        Assert.assertTrue("Pili-Pala has 1 attachment", pp.getAttachments().size() == 1);
        
        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{Any}", manaOptions);
    }    
    
}
