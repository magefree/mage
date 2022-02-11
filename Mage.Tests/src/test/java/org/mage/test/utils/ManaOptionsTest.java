package org.mage.test.utils;

import mage.abilities.mana.ManaOptions;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static org.mage.test.utils.ManaOptionsTestUtils.assertDuplicatedManaOptions;
import static org.mage.test.utils.ManaOptionsTestUtils.assertManaOptions;

/**
 * This test checks if the calculated possible mana options are correct related
 * to the given mana sources available.
 *
 * @author LevelX2, JayDi85
 */
public class ManaOptionsTest extends CardTestPlayerBase {

    @Test
    public void testSimpleMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);

        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{G}{G}{G}", manaOptions);

    }

    // Tinder Farm enters the battlefield tapped.
    // {T}: Add {G}.
    // {T}, Sacrifice Tinder Farm: Add {R}{W}.
    @Test
    public void testTinderFarm() {
        addCard(Zone.BATTLEFIELD, playerA, "Tinder Farm", 3);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);

        Assert.assertEquals("mana variations don't fit", 4, manaOptions.size());
        assertManaOptions("{G}{G}{G}", manaOptions);
        assertManaOptions("{R}{G}{G}{W}", manaOptions);
        assertManaOptions("{R}{R}{G}{W}{W}", manaOptions);
        assertManaOptions("{R}{R}{R}{W}{W}{W}", manaOptions);

    }

    // Adarkar Wastes
    // {T}: Add {C}.
    // {T}: Add {W} or {U}. Adarkar Wastes deals 1 damage to you.
    @Test
    public void testAdarkarWastes() {
        addCard(Zone.BATTLEFIELD, playerA, "Adarkar Wastes", 3);

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);

        Assert.assertEquals("mana variations don't fit", 10, manaOptions.size());
        assertManaOptions("{C}{C}{C}", manaOptions);
        assertManaOptions("{C}{C}{W}", manaOptions);
        assertManaOptions("{C}{C}{U}", manaOptions);
        assertManaOptions("{C}{W}{W}", manaOptions);
        assertManaOptions("{C}{W}{U}", manaOptions);
        assertManaOptions("{C}{U}{U}", manaOptions);
        assertManaOptions("{W}{W}{W}", manaOptions);
        assertManaOptions("{W}{W}{U}", manaOptions);
        assertManaOptions("{W}{U}{U}", manaOptions);
        assertManaOptions("{U}{U}{U}", manaOptions);
    }

    // Chromatic Sphere
    // {1}, {T}, Sacrifice Chromatic Sphere: Add one mana of any color. Draw a card.
    @Test
    public void testChromaticSphere() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Chromatic Sphere", 2);

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);

        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{Any}{Any}", manaOptions);
    }

    // Orochi Leafcaller
    // {G}: Add one mana of any color.
    @Test
    public void testOrochiLeafcaller() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Orochi Leafcaller", 1);

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);

        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{W}{W}{Any}{Any}", manaOptions);
    }

    // Crystal Quarry
    // {T}: {1} Add .
    // {5}, {T}: Add {W}{U}{B}{R}{G}.
    @Test
    public void testCrystalQuarry() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Crystal Quarry", 1);

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);

        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{C}{G}{G}{W}{W}", manaOptions);
    }

    // Crystal Quarry
    // {T}: {1} Add .
    // {5}, {T}: Add {W}{U}{B}{R}{G}.
    @Test
    public void testCrystalQuarry2() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Crystal Quarry", 1);

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);

        Assert.assertEquals("mana variations don't fit", 2, manaOptions.size());
        assertManaOptions("{C}{G}{G}{G}{W}{W}", manaOptions);
        assertManaOptions("{W}{U}{B}{R}{G}", manaOptions);
    }

    // Nykthos, Shrine to Nyx
    // {T}: Add {C}.
    // {2}, {T}: Choose a color. Add an amount of mana of that color equal to your devotion to that color. (Your devotion to a color is the number of mana symbols of that color in the mana costs of permanents you control.)
    @Test
    public void testNykthos1() {
        addCard(Zone.BATTLEFIELD, playerA, "Sedge Scorpion", 4); // Creature {G} (1/1)
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 4); // Creature {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Nykthos, Shrine to Nyx", 1); // Land

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);

        Assert.assertEquals("mana variations don't fit", 3, manaOptions.size());
        assertManaOptions("{C}{G}{G}{G}", manaOptions);
        assertManaOptions("{G}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{G}{W}{W}{W}{W}", manaOptions);
    }

    @Test
    public void testNykthos2() {
        addCard(Zone.BATTLEFIELD, playerA, "Sedge Scorpion", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Akroan Crusader", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3); // {G}
        addCard(Zone.BATTLEFIELD, playerA, "Nykthos, Shrine to Nyx", 1); // {C}

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);

        Assert.assertEquals("mana variations don't fit", 3, manaOptions.size());
        assertManaOptions("{C}{G}{G}{G}", manaOptions);
        assertManaOptions("{G}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{R}{R}{R}{G}", manaOptions);
    }

    @Test
    public void testNykthos3() {
        addCard(Zone.BATTLEFIELD, playerA, "Sylvan Caryatid", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Nykthos, Shrine to Nyx", 1);

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);

        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{C}{G}{Any}", manaOptions);
    }

    // Nykthos, Shrine to Nyx
    @Test
    public void testNykthos4a() {
        addCard(Zone.BATTLEFIELD, playerA, "Sedge Scorpion", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        // {T}: Add {C}.
        // {2}, {T}: Choose a color. Add an amount of mana of that color equal to your devotion to that color. (Your devotion to a color is the number of mana symbols of that color in the mana costs of permanents you control.)
        addCard(Zone.BATTLEFIELD, playerA, "Nykthos, Shrine to Nyx", 1);

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);

        Assert.assertEquals("mana variations don't fit", 2, manaOptions.size());
        assertManaOptions("{C}{G}{G}{G}", manaOptions);
        assertManaOptions("{G}{G}{G}{G}{G}", manaOptions);

    }

    // Nykthos, Shrine to Nyx
    // {T}: Add {C}.
    // {2}, {T}: Choose a color. Add an amount of mana of that color equal to your devotion to that color. (Your devotion to a color is the number of mana symbols of that color in the mana costs of permanents you control.)
    @Test
    public void testNykthos4b() {
        // If a land is tapped for two or more mana, it produces {C} instead of any other type and amount.
        // Each spell a player casts costs {1} more to cast for each other spell that player has cast this turn.
        addCard(Zone.BATTLEFIELD, playerA, "Damping Sphere", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Sedge Scorpion", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Nykthos, Shrine to Nyx", 1);

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);

        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{C}{G}{G}{G}", manaOptions);

    }

    @Test
    public void testNykthos5() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Sedge Scorpion", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Nykthos, Shrine to Nyx", 1);

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);

        Assert.assertEquals("mana variations don't fit", 3, manaOptions.size());
        assertManaOptions("{G}{W}{W}", manaOptions);
        assertManaOptions("{C}{G}{G}{G}", manaOptions);
        assertManaOptions("{G}{G}{G}{G}{G}", manaOptions);
    }

    @Test
    public void testNykthos6() {
        addCard(Zone.BATTLEFIELD, playerA, "Sedge Scorpion", 4); // Creature {G} (1/1)
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 4); // Creature {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Nykthos, Shrine to Nyx", 1); // Land

        addCard(Zone.BATTLEFIELD, playerA, "Radha, Heart of Keld");
        addCard(Zone.BATTLEFIELD, playerA, "Precognition Field");
        addCard(Zone.BATTLEFIELD, playerA, "Mystic Forge");
        addCard(Zone.BATTLEFIELD, playerA, "Experimental Frenzy");
        addCard(Zone.BATTLEFIELD, playerA, "Elsha of the Infinite");
        addCard(Zone.BATTLEFIELD, playerA, "Bolas's Citadel");
        addCard(Zone.BATTLEFIELD, playerA, "Verge Rangers");
        addCard(Zone.BATTLEFIELD, playerA, "Vivien, Monsters' Advocate");
        addCard(Zone.BATTLEFIELD, playerA, "Vizier of the Menagerie");

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);

        Assert.assertEquals("mana variations don't fit", 6, manaOptions.size());
        assertManaOptions("{C}{G}{G}{G}", manaOptions);
        assertManaOptions("{G}{G}{G}{G}{G}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{G}{W}{W}{W}{W}{W}{W}", manaOptions);
        assertManaOptions("{R}{R}{R}{G}", manaOptions);
        assertManaOptions("{B}{B}{B}{G}", manaOptions);
        assertManaOptions("{G}{U}{U}", manaOptions);
    }

    @Test
    public void testDuplicatedDontHave1() {
        addCard(Zone.BATTLEFIELD, playerA, "City of Brass", 2); // Any
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);
    }

    @Test
    public void testDuplicatedDontHave3() {
        addCard(Zone.BATTLEFIELD, playerA, "Grove of the Burnwillows", 2); // R or G
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);
    }

    @Test
    public void testDuplicatedHave() {
        // getManaAvailable return any combination of mana variants evailable to player
        // if mana ability cost another mana then if replaced in mana cost
        // example:
        // 1x forest
        // 1x Chromatic Star ({1}, {T}, Sacrifice Chromatic Star: Add one mana of any color.)
        // give {G}{Any}, but after pay it transform to {Any} (1 green will be pay)
        // That's why there are can be duplicated records in getManaAvailable

        // {1}, {T}, Sacrifice Chromatic Star: Add one mana of any color.
        // When Chromatic Star is put into a graveyard from the battlefield, draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Chromatic Star", 1);
        // {1}, {T}, Sacrifice Chromatic Sphere: Add one mana of any color. Draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Chromatic Sphere", 1);
        // {T}: Add {C}. If you control an Urza's Mine and an Urza's Power-Plant, add {C}{C}{C} instead.
        addCard(Zone.BATTLEFIELD, playerA, "Urza's Tower", 1);
        // {T}: Add {C}.
        // {T}: Add {R} or {G}. Each opponent gains 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Grove of the Burnwillows", 1);

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 3, manaOptions.size());
        assertDuplicatedManaOptions(manaOptions);
        assertManaOptions("{C}{C}", manaOptions);
        assertManaOptions("{Any}{Any}", manaOptions);
        assertManaOptions("{C}{Any}", manaOptions);
    }

    @Test
    public void testFetidHeath() {
        // {T}: Add {C}.
        // {W/B}, {T}: Add {W}{W}, {W}{B}, or {B}{B}.        
        addCard(Zone.BATTLEFIELD, playerA, "Fetid Heath", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);

        Assert.assertEquals("mana variations don't fit", 4, manaOptions.size());
        assertManaOptions("{C}{W}", manaOptions);
        assertManaOptions("{W}{W}", manaOptions);
        assertManaOptions("{W}{B}", manaOptions);
        assertManaOptions("{B}{B}", manaOptions);
    }

    /**
     * Don't use mana sources that only reduce available mana
     */
    @Test
    public void testCabalCoffers1() {
        addCard(Zone.BATTLEFIELD, playerA, "Cabal Coffers", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);

        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{W}{B}", manaOptions);
    }

    @Test
    public void testCabalCoffers2() {
        addCard(Zone.BATTLEFIELD, playerA, "Cabal Coffers", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);

        Assert.assertEquals("mana variations don't fit", 2, manaOptions.size());
        assertManaOptions("{W}{B}{B}", manaOptions);
        assertManaOptions("{B}{B}{B}", manaOptions);
    }

    @Test
    public void testMageRingNetwork() {
        // {T}: Add {C}.
        // {T}, {1} : Put a storage counter on Mage-Ring Network.
        // {T}, Remove X storage counters from Mage-Ring Network: Add {X}.
        addCard(Zone.BATTLEFIELD, playerA, "Mage-Ring Network", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);

        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{C}{W}{B}", manaOptions);
    }

    @Test
    public void testMageRingNetwork2() {
        // {T}: Add {C}.
        // {T}, {1} : Put a storage counter on Mage-Ring Network.
        // {T}, Remove any number of storage counters from Mage-Ring Network: Add {C} for each storage counter removed this way.
        addCard(Zone.BATTLEFIELD, playerA, "Mage-Ring Network", 1);
        addCounters(1, PhaseStep.UPKEEP, playerA, "Mage-Ring Network", CounterType.STORAGE, 4);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        setStopAt(1, PhaseStep.DRAW);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);

        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{C}{C}{C}{C}{W}{B}", manaOptions);
    }

    @Test
    public void testCryptGhast() {
        //Extort (Whenever you cast a spell, you may pay {WB}. If you do, each opponent loses 1 life and you gain that much life.)
        // Whenever you tap a Swamp for mana, add {B} (in addition to the mana the land produces).
        addCard(Zone.BATTLEFIELD, playerA, "Crypt Ghast", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);

        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{B}{B}", manaOptions);
    }

    @Test
    public void testDampingSphere() {
        // If a land is tapped for two or more mana, it produces {C} instead of any other type and amount.
        // Each spell a player casts costs {1} more to cast for each other spell that player has cast this turn.
        addCard(Zone.BATTLEFIELD, playerA, "Damping Sphere", 1);
        // {T}: Add {C}.
        // {T}: Add {C}{C}. Spend this mana only to cast colorless Eldrazi spells or activate abilities of colorless Eldrazi.
        addCard(Zone.BATTLEFIELD, playerA, "Eldrazi Temple", 1);

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);

        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{C}", manaOptions);
    }

    @Test
    public void testCharmedPedant() {
        // {T}, Put the top card of your library into your graveyard: For each colored mana symbol in that card's mana cost, add one mana of that color.
        // Activate this ability only any time you could cast an instant.
        addCard(Zone.BATTLEFIELD, playerA, "Charmed Pendant", 1);
        // {T}: Add {C}.
        // {T}: Add {C}{C}. Spend this mana only to cast colorless Eldrazi spells or activate abilities of colorless Eldrazi.
        addCard(Zone.BATTLEFIELD, playerA, "Eldrazi Temple", 1);

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);

        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{C}{C}", manaOptions);
    }

    @Test
    public void testManaSourcesWithCosts() {
        // {T}: Add {C} to your mana pool.
        // {5}, {T}: Add {W}{U}{B}{R}{G} to your mana pool.
        addCard(Zone.BATTLEFIELD, playerA, "Crystal Quarry", 1);

        // {T}: Add {C} to your mana pool.
        // {W/B}, {T}: Add {W}{W}, {W}{B}, or {B}{B} to your mana pool.
        addCard(Zone.BATTLEFIELD, playerA, "Fetid Heath", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);

        Assert.assertEquals("mana variations don't fit", 16, manaOptions.size());
        assertManaOptions("{C}{C}{C}{C}{W}{W}{W}", manaOptions);
        assertManaOptions("{C}{C}{C}{W}{W}{W}{W}", manaOptions);
        assertManaOptions("{C}{C}{C}{W}{W}{W}{B}", manaOptions);
        assertManaOptions("{C}{C}{C}{W}{W}{B}{B}", manaOptions);
        assertManaOptions("{C}{C}{W}{W}{W}{W}{W}", manaOptions);
        assertManaOptions("{C}{C}{W}{W}{W}{W}{B}", manaOptions);
        assertManaOptions("{C}{C}{W}{W}{W}{B}{B}", manaOptions);
        assertManaOptions("{C}{C}{W}{W}{B}{B}{B}", manaOptions);
        assertManaOptions("{C}{C}{W}{B}{B}{B}{B}", manaOptions);
        assertManaOptions("{C}{W}{W}{W}{W}{W}{W}", manaOptions);
        assertManaOptions("{C}{W}{W}{W}{W}{W}{B}", manaOptions);
        assertManaOptions("{C}{W}{W}{W}{W}{B}{B}", manaOptions);
        assertManaOptions("{C}{W}{W}{W}{B}{B}{B}", manaOptions);
        assertManaOptions("{C}{W}{W}{B}{B}{B}{B}", manaOptions);
        assertManaOptions("{C}{W}{B}{B}{B}{B}{B}", manaOptions);
        assertManaOptions("{C}{B}{B}{B}{B}{B}{B}", manaOptions);
    }

    @Test
    public void testSungrassPrairie() {
        // {1}, {T}: Add {G}{W}.
        addCard(Zone.BATTLEFIELD, playerA, "Sungrass Prairie", 1);
        // {T}: Add one mana of any color to your mana pool.
        addCard(Zone.BATTLEFIELD, playerA, "Alloy Myr", 2);

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);

        Assert.assertEquals("mana variations don't fit", 2, manaOptions.size());

        assertManaOptions("{G}{W}{Any}", manaOptions);
        assertManaOptions("{Any}{Any}", manaOptions);
    }

    @Test
    public void testSungrassPrairie2() {
        // {1}, {T}: Add {G}{W}.
        addCard(Zone.BATTLEFIELD, playerA, "Sungrass Prairie", 5);
        // ({T}: Add {U} or {W} to your mana pool.)
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 9);
        // ({T}: Add {G} or {U} to your mana pool.)
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 3);

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);

        Assert.assertEquals("mana variations don't fit", 88, manaOptions.size());

        assertManaOptions("{G}{G}{G}{G}{G}{G}{G}{G}{W}{W}{W}{W}{W}{W}{W}{W}{W}", manaOptions);
        assertManaOptions("{G}{G}{G}{G}{G}{G}{G}{G}{W}{W}{W}{W}{W}{W}{W}{W}{U}", manaOptions);
    }

    @Test
    public void testSungrassPrairie3() {
        // {1}, {T}: Add {G}{W}.
        addCard(Zone.BATTLEFIELD, playerA, "Sungrass Prairie", 1);
        // ({T}: Add {U} or {W} to your mana pool.)
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 1);
        // ({T}: Add {G} or {U} to your mana pool.)
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 1);

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);

        Assert.assertEquals("mana variations don't fit", 4, manaOptions.size());
        assertManaOptions("{U}{U}", manaOptions);
        assertManaOptions("{G}{G}{W}", manaOptions);
        assertManaOptions("{G}{W}{U}", manaOptions);
        assertManaOptions("{G}{W}{W}", manaOptions);
    }

}
