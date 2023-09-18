package org.mage.test.cards.continuous;

import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class LandTypeChangingEffectsTest extends CardTestPlayerBase {

    /**
     * Playing a commander game. Opponent had a Magus of the Moon, and I later
     * dropped a Chromatic Lantern.
     * <p>
     * I was not allowed to use the Chromatic Lantern's ability. Since layers
     * are tricky I asked on the Judge's chat to confirm and the user "luma"
     * said it should work on this scenario.
     */
    @Test
    public void testMagusOfTheMoonAndChromaticLantern() {
        // Nonbasic lands are Mountains.
        addCard(Zone.BATTLEFIELD, playerA, "Magus of the Moon");

        addCard(Zone.BATTLEFIELD, playerB, "Canopy Vista", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        // Lands you control have "{T}: Add one mana of any color."
        // {T}: Add one mana of any color.
        addCard(Zone.HAND, playerB, "Chromatic Lantern");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Chromatic Lantern");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Chromatic Lantern", 1);

        assertType("Canopy Vista", CardType.LAND, SubType.MOUNTAIN);
        assertAbility(playerB, "Canopy Vista", new AnyColorManaAbility(), true);
    }

    @Test
    public void testChromaticLanternBeforeMagusOfTheMoon() {
        // Nonbasic lands are Mountains.
        addCard(Zone.HAND, playerA, "Magus of the Moon");// {2}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        addCard(Zone.BATTLEFIELD, playerB, "Canopy Vista", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        // Lands you control have "{T}: Add one mana of any color."
        // {T}: Add one mana of any color.
        addCard(Zone.HAND, playerB, "Chromatic Lantern");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Chromatic Lantern");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Magus of the Moon");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Chromatic Lantern", 1);
        assertPermanentCount(playerA, "Magus of the Moon", 1);

        assertType("Canopy Vista", CardType.LAND, SubType.MOUNTAIN);
        assertAbility(playerB, "Canopy Vista", new AnyColorManaAbility(), true);
    }

    /**
     * Currently, a land hit by Aquitect's Will loses all of its other
     * abilities, making it a cheap Spreading Seas. It should function like
     * Urborg, Tomb of Yawgmoth, not Spreading Seas or Blood Moon.
     */
    @Test
    public void testLandDoesNotLooseOtherAbilities() {
        // Put a flood counter on target land.
        // That land is an Island in addition to its other types for as long as it has a flood counter on it.
        // If you control a Merfolk, draw a card.
        addCard(Zone.HAND, playerA, "Aquitect's Will");// Tribal Sorcery{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        // Forbidding Watchtower enters the battlefield tapped.
        // {T}: Add {W}.
        // {1}{W}: Forbidding Watchtower becomes a 1/5 white Soldier creature until end of turn. It's still a land.
        addCard(Zone.BATTLEFIELD, playerB, "Forbidding Watchtower", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aquitect's Will", "Forbidding Watchtower");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{1}{W}:");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Aquitect's Will", 1);

        assertPermanentCount(playerB, "Forbidding Watchtower", 1);
        assertCounterCount("Forbidding Watchtower", CounterType.FLOOD, 1);
        assertType("Forbidding Watchtower", CardType.LAND, SubType.ISLAND);
        assertPowerToughness(playerB, "Forbidding Watchtower", 1, 5);
    }

    String urborgtoy = "Urborg, Tomb of Yawgmoth";
    String bloodmoon = "Blood Moon";
    String canopyvista = "Canopy Vista";

    @Test
    public void testBloodMoonBeforeUrborg() {
        // Blood Moon   2R
        // Enchantment
        // Nonbasic lands are Mountains
        addCard(Zone.HAND, playerA, bloodmoon);
        // Each land is a Swamp in addition to its other land types.
        addCard(Zone.HAND, playerA, urborgtoy);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        addCard(Zone.BATTLEFIELD, playerB, canopyvista, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bloodmoon);
        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, urborgtoy);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, bloodmoon, 1);
        assertPermanentCount(playerA, urborgtoy, 1);
        assertType(canopyvista, CardType.LAND, SubType.MOUNTAIN);
        assertNotSubtype(canopyvista, SubType.ISLAND);
        assertNotSubtype(canopyvista, SubType.SWAMP);
        assertType(urborgtoy, CardType.LAND, SubType.MOUNTAIN);
        assertNotSubtype(urborgtoy, SubType.SWAMP);
        Assert.assertTrue("The mana the land can produce should be [{R}] but it's " + playerB.getManaAvailable(currentGame).toString(), playerB.getManaAvailable(currentGame).toString().equals("[{R}]"));
    }

    @Test
    public void testBloodMoonAfterUrborg() {
        // Blood Moon   2R
        // Enchantment
        // Nonbasic lands are Mountains
        addCard(Zone.HAND, playerA, bloodmoon);
        // Each land is a Swamp in addition to its other land types.
        addCard(Zone.HAND, playerA, urborgtoy);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        addCard(Zone.BATTLEFIELD, playerB, canopyvista, 1);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, urborgtoy);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bloodmoon);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, bloodmoon, 1);
        assertPermanentCount(playerA, urborgtoy, 1);
        assertType(canopyvista, CardType.LAND, SubType.MOUNTAIN);
        assertNotSubtype(canopyvista, SubType.ISLAND);
        assertNotSubtype(canopyvista, SubType.SWAMP);
        assertType(urborgtoy, CardType.LAND, SubType.MOUNTAIN);
        assertNotSubtype(urborgtoy, SubType.SWAMP);
        Assert.assertTrue("The mana the land can produce should be [{R}] but it's " + playerB.getManaAvailable(currentGame).toString(), playerB.getManaAvailable(currentGame).toString().equals("[{R}]"));
    }

    /*
       I had Kormus Bell (all swamps are 1/1s) and Urborg, Tomb of Yawgmoth (all lands are swamps) and only basic lands (swamps) otherwise.
       Opponent had Quicksilver Fountain (put a flood counter on non-island land).
       In terms of time-stamp order, Urborg was down first, then Kormus Bell, then Quicksilver.
       When I put a flood counter on a basic swamp, it would become a 0/0 instead of a 1/1 and die.
     */

    @Test
    public void testCormusBellAfterUrborg() {
        // Land - Legendary
        // Each land is a Swamp in addition to its other land types.
        addCard(Zone.HAND, playerA, urborgtoy);

        // All Swamps are 1/1 black creatures that are still lands.
        addCard(Zone.HAND, playerA, "Kormus Bell"); // Artifact {4}

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        // At the beginning of each player's upkeep, that player puts a flood counter on target non-Island land they control of their choice.
        //  That land is an Island for as long as it has a flood counter on it.
        // At the beginning of each end step, if all lands on the battlefield are Islands, remove all flood counters from them.
        addCard(Zone.HAND, playerB, "Quicksilver Fountain", 1); // Artifact {3}
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 3);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, urborgtoy);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Kormus Bell");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Quicksilver Fountain");

        addTarget(playerA, "Mountain");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, urborgtoy, 1);
        assertPermanentCount(playerA, "Kormus Bell", 1);
        assertPermanentCount(playerB, "Quicksilver Fountain", 1);

        assertGraveyardCount(playerA, "Mountain", 0);

        int islandTypes = 0;
        int swampTypes = 0;
        int mountainTypes = 0;
        int creatures = 0;
        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_LAND, playerA.getId(), currentGame)) {
            if (permanent.hasSubtype(SubType.ISLAND, currentGame)) {
                islandTypes++;
            }
            if (permanent.hasSubtype(SubType.MOUNTAIN, currentGame)) {
                mountainTypes++;
            }
            if (permanent.hasSubtype(SubType.SWAMP, currentGame)) {
                swampTypes++;
            }
            if (permanent.isCreature(currentGame)) {
                creatures++;
            }
        }
        Assert.assertTrue("1 land has to be of island type", islandTypes == 1);
        Assert.assertTrue("3 lands have to be of mountain type", mountainTypes == 3);
        Assert.assertTrue("4 lands have to be of swamp type", swampTypes == 4);
        Assert.assertTrue("4 lands have to be creatures but there are " + creatures, creatures == 4);
    }

    @Test
    public void testBloodSunWithUrborgtoyAndStormtideLeviathanMan() {

        addCard(Zone.BATTLEFIELD, playerA, urborgtoy);  // all lands are swamps in addition to their other types
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Blood Sun");  // all lands lose all abilities except for mana-producing
        addCard(Zone.BATTLEFIELD, playerA, "Stormtide Leviathan"); // all lands are islands in addition to their other types
        addCard(Zone.BATTLEFIELD, playerA, "Darksteel Citadel");  // land has indestructible ability

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        Permanent darksteel = getPermanent("Darksteel Citadel", playerA.getId());
        Assert.assertNotNull(darksteel);
        Assert.assertFalse(darksteel.getAbilities().contains(IndestructibleAbility.getInstance()));  // The ability is removed

        /*
        If a continuous effect has started applying in an earlier layer, it will continue to apply in 
        later layers even if the ability that created that effect has been removed.
        Urborg ability is applied in the 4th layer.  The Blood Sun works in the 6th.  So the effect still applies to the lands.
         */
        assertType(urborgtoy, CardType.LAND, SubType.SWAMP);
        assertType("Mountain", CardType.LAND, SubType.SWAMP);
        assertType(urborgtoy, CardType.LAND, SubType.ISLAND);
        assertType("Mountain", CardType.LAND, SubType.ISLAND);
    }
}
