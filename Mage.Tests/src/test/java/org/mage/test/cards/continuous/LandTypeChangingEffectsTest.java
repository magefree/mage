/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package org.mage.test.cards.continuous;

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
 *
 * @author LevelX2
 */
public class LandTypeChangingEffectsTest extends CardTestPlayerBase {

    /**
     *
     * Playing a commander game. Opponent had a Magus of the Moon, and I later
     * dropped a Chromatic Lantern.
     *
     * I was not allowed to use the Chromatic Lantern's ability. Since layers
     * are tricky I asked on the Judge's chat to confirm and the user "luma"
     * said it should work on this scenario.
     *
     */
    @Test
    public void testMagusOfTheMoonAndChromaticLantern() {
        // Nonbasic lands are Mountains.
        addCard(Zone.BATTLEFIELD, playerA, "Magus of the Moon");

        addCard(Zone.BATTLEFIELD, playerB, "Canopy Vista", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        // Lands you control have "{T}: Add one mana of any color to your mana pool."
        // {T}: Add one mana of any color to your mana pool.
        addCard(Zone.HAND, playerB, "Chromatic Lantern");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Chromatic Lantern");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Chromatic Lantern", 1);

        assertType("Canopy Vista", CardType.LAND, "Mountain");
        assertAbility(playerB, "Canopy Vista", new AnyColorManaAbility(), true);
    }

    @Test
    public void testChromaticLanternBeforeMagusOfTheMoon() {
        // Nonbasic lands are Mountains.
        addCard(Zone.HAND, playerA, "Magus of the Moon");// {2}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        addCard(Zone.BATTLEFIELD, playerB, "Canopy Vista", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        // Lands you control have "{T}: Add one mana of any color to your mana pool."
        // {T}: Add one mana of any color to your mana pool.
        addCard(Zone.HAND, playerB, "Chromatic Lantern");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Chromatic Lantern");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Magus of the Moon");
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Chromatic Lantern", 1);
        assertPermanentCount(playerA, "Magus of the Moon", 1);

        assertType("Canopy Vista", CardType.LAND, "Mountain");
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
        // {T}: Add {W} to your mana pool.
        // {1}{W}: Forbidding Watchtower becomes a 1/5 white Soldier creature until end of turn. It's still a land.
        addCard(Zone.BATTLEFIELD, playerB, "Forbidding Watchtower", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aquitect's Will", "Forbidding Watchtower");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{1}{W}:");
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Aquitect's Will", 1);

        assertPermanentCount(playerB, "Forbidding Watchtower", 1);
        assertCounterCount("Forbidding Watchtower", CounterType.FLOOD, 1);
        assertType("Forbidding Watchtower", CardType.LAND, "Island");
        assertPowerToughness(playerB, "Forbidding Watchtower", 1, 5);
    }

    String urborgtoy = "Urborg, Tomb of Yawgmoth";
    String bloodmoon = "Blood Moon";
    String canopyvista = "Canopy Vista";

    /*
    NOTE: this test is currently failing due to bug in code. See issue #3072
     */
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

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, bloodmoon, 1);
        assertPermanentCount(playerA, urborgtoy, 1);
        assertType(canopyvista, CardType.LAND, "Mountain");
        assertNotSubtype(canopyvista, "Island");
        assertNotSubtype(canopyvista, "Swamp");
        assertType(urborgtoy, CardType.LAND, "Mountain");
        assertNotSubtype(urborgtoy, "Swamp");
        Assert.assertTrue("The mana the land can produce should be [{R}] but it's " + playerB.getManaAvailable(currentGame).toString(), playerB.getManaAvailable(currentGame).toString().equals("[{R}]"));
    }

    /*
    NOTE: this test is currently failing due to bug in code. See issue #3072
     */
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

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, bloodmoon, 1);
        assertPermanentCount(playerA, urborgtoy, 1);
        assertType(canopyvista, CardType.LAND, "Mountain");
        assertNotSubtype(canopyvista, "Island");
        assertNotSubtype(canopyvista, "Swamp");
        assertType(urborgtoy, CardType.LAND, "Mountain");
        assertNotSubtype(urborgtoy, "Swamp");
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

        // At the beginning of each player's upkeep, that player puts a flood counter on target non-Island land he or she controls of his or her choice.
        //  That land is an Island for as long as it has a flood counter on it.
        // At the beginning of each end step, if all lands on the battlefield are Islands, remove all flood counters from them.
        addCard(Zone.HAND, playerB, "Quicksilver Fountain", 1); // Artifact {3}
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 3);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, urborgtoy);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Kormus Bell");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Quicksilver Fountain");
        addTarget(playerA, "Mountain");

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
            if (permanent.getSubtype(currentGame).contains(SubType.ISLAND)) {
                islandTypes++;
            }
            if (permanent.getSubtype(currentGame).contains(SubType.MOUNTAIN)) {
                mountainTypes++;
            }
            if (permanent.getSubtype(currentGame).contains(SubType.SWAMP)) {
                swampTypes++;
            }
            if (permanent.isCreature()) {
                creatures++;
            }
        }
        Assert.assertTrue("1 land has to be of island type", islandTypes == 1);
        Assert.assertTrue("3 lands have to be of mountain type", mountainTypes == 3);
        Assert.assertTrue("4 lands have to be of swamp type", swampTypes == 4);
        Assert.assertTrue("4 lands have to be creatures but there are " + creatures, creatures == 4);
    }

}
