package org.mage.test.cards.abilities.keywords;

import mage.Constants;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.keyword.LifelinkAbility;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class SoulbondKeywordTest extends CardTestPlayerBase {

    @Test
    public void testPairOnCast() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Constants.Zone.HAND, playerA, "Trusted Forcemage");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Trusted Forcemage");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Trusted Forcemage", 1);
        assertPowerToughness(playerA, "Trusted Forcemage", 3, 3);
        assertPowerToughness(playerA, "Elite Vanguard", 3, 2);
    }

    /**
     * Tests pair on another creature without Soulbond entering battlefield
     */
    @Test
    public void testPairOnEntersBattlefield() {
        addCard(Constants.Zone.HAND, playerA, "Elite Vanguard");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Trusted Forcemage");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 1);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Vanguard");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Trusted Forcemage", 1);
        assertPowerToughness(playerA, "Trusted Forcemage", 3, 3);
        assertPowerToughness(playerA, "Elite Vanguard", 3, 2);
    }

    /**
     * Tests two creatures with Soulbond paired with each other
     */
    @Test
    public void testTwoSoulbondCreaturesOnBattlefield() {
        addCard(Constants.Zone.HAND, playerA, "Trusted Forcemage", 2);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 6);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Trusted Forcemage");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Trusted Forcemage");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Trusted Forcemage", 2);
        assertPowerToughness(playerA, "Trusted Forcemage", 4, 4, Filter.ComparisonScope.All);
    }

    /**
     * Tests no Soulbond effect possible on single creature
     */
    @Test
    public void testNoPairOnSingleCreature() {
        addCard(Constants.Zone.HAND, playerA, "Trusted Forcemage", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Trusted Forcemage");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Trusted Forcemage", 1);
        assertPowerToughness(playerA, "Trusted Forcemage", 2, 2);
    }

    /**
     * Tests Soulbond effect disabling whenever Soulbond creature changes its controller
     */
    @Test
    public void testChangeControllerForSoulbondCreature() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Constants.Zone.HAND, playerA, "Trusted Forcemage");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 3);

        addCard(Constants.Zone.HAND, playerB, "Act of Treason");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Mountain", 3);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Trusted Forcemage");
        castSpell(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Act of Treason", "Trusted Forcemage");

        setStopAt(2, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Trusted Forcemage", 0);
        assertPermanentCount(playerA, "Elite Vanguard", 1);
        assertPowerToughness(playerA, "Elite Vanguard", 2, 1);

        assertPermanentCount(playerB, "Trusted Forcemage", 1);
        assertPowerToughness(playerB, "Trusted Forcemage", 2, 2);

    }

    /**
     * Tests Soulbond effect disabling when paired creture changes its controller
     */
    @Test
    public void testChangeControllerForAnotherCreature() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Constants.Zone.HAND, playerA, "Trusted Forcemage");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 3);

        addCard(Constants.Zone.HAND, playerB, "Act of Treason");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Mountain", 3);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Trusted Forcemage");
        castSpell(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Act of Treason", "Elite Vanguard");

        setStopAt(2, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Trusted Forcemage", 1);
        assertPermanentCount(playerA, "Elite Vanguard", 0);
        assertPowerToughness(playerA, "Trusted Forcemage", 2, 2);

        assertPermanentCount(playerB, "Elite Vanguard", 1);
        assertPowerToughness(playerB, "Elite Vanguard", 2, 1);
    }

    /**
     * Tests Soulbond effect disabling when Soulbond creture changes its controller and then returns back.
     * Effect should not be restored.
     */
    @Test
    public void testChangeControllerAndGettingBack() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Constants.Zone.HAND, playerA, "Trusted Forcemage");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 3);

        addCard(Constants.Zone.HAND, playerB, "Act of Treason");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Mountain", 3);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Trusted Forcemage");
        castSpell(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Act of Treason", "Trusted Forcemage");

        setStopAt(3, Constants.PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Trusted Forcemage", 1);
        assertPowerToughness(playerA, "Trusted Forcemage", 2, 2);
        assertPermanentCount(playerA, "Elite Vanguard", 1);
        assertPowerToughness(playerA, "Elite Vanguard", 2, 1);
    }

    /**
     * Tests that stealing creature will allow to use Soulbond ability on controller's creature
     */
    @Test
    public void testSoulbondWorksOnControllerSide() {
        addCard(Constants.Zone.HAND, playerA, "Trusted Forcemage");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 3);

        addCard(Constants.Zone.HAND, playerB, "Act of Treason");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Constants.Zone.HAND, playerB, "Elite Vanguard");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Plains", 3);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Trusted Forcemage");
        castSpell(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Act of Treason", "Trusted Forcemage");
        castSpell(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Elite Vanguard");

        setStopAt(2, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        // stolen
        assertPermanentCount(playerA, "Trusted Forcemage", 0);

        // both paired and have boost
        assertPowerToughness(playerB, "Trusted Forcemage", 3, 3);
        assertPowerToughness(playerB, "Elite Vanguard", 3, 2);
    }

    /**
     * Tests effect also disappeared when creature is returned back to owner
     */
    @Test
    public void testReturnBack() {
        addCard(Constants.Zone.HAND, playerA, "Trusted Forcemage");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 3);

        addCard(Constants.Zone.HAND, playerB, "Act of Treason");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Constants.Zone.HAND, playerB, "Elite Vanguard");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Plains", 3);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Trusted Forcemage");
        castSpell(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Act of Treason", "Trusted Forcemage");
        castSpell(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Elite Vanguard");

        setStopAt(3, Constants.PhaseStep.PRECOMBAT_MAIN); // Effect of "Act of Treason" will end here
        execute();

        // returned back with no boost
        assertPermanentCount(playerA, "Trusted Forcemage", 1);
        assertPowerToughness(playerA, "Trusted Forcemage", 2, 2);

        // no boost on next turn (gets unpaired)
        assertPowerToughness(playerB, "Elite Vanguard", 2, 1);
    }

    /**
     * Tests returning one of the paired creatures back to its owner's hand
     */
    @Test
    public void testUnsummon() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Constants.Zone.HAND, playerA, "Trusted Forcemage");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Constants.Zone.HAND, playerA, "Unsummon", 1);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Trusted Forcemage");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Unsummon", "Elite Vanguard");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Elite Vanguard", 0);
        assertPowerToughness(playerA, "Trusted Forcemage", 2, 2);
    }

    /**
     * Tests that it is possible to animate land and pair it on next coming Soulbond creature
     */
    @Test
    public void testPairOnAnimatedLand() {
        addCard(Constants.Zone.HAND, playerA, "Trusted Forcemage");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Blinkmoth Nexus", 1);

        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "{1}: ");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Trusted Forcemage");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        // test paired with boost
        assertPowerToughness(playerA, "Trusted Forcemage", 3, 3);
        assertPowerToughness(playerA, "Blinkmoth Nexus", 2, 2);
    }

    /**
     * Tests no effect whether land was animated after Soulbond creature has entered the battlefield
     */
    @Test
    public void testPairOnPostAnimatedLand() {
        addCard(Constants.Zone.HAND, playerA, "Trusted Forcemage");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Blinkmoth Nexus", 1);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Trusted Forcemage");
        activateAbility(1, Constants.PhaseStep.BEGIN_COMBAT, playerA, "{1}: ");

        setStopAt(1, Constants.PhaseStep.DECLARE_ATTACKERS);
        execute();

        // no effect on later animation
        assertPowerToughness(playerA, "Trusted Forcemage", 2, 2);
        assertPowerToughness(playerA, "Blinkmoth Nexus", 1, 1);
    }

    /**
     * Tests that creature type loss leads to Soulbond effect disabling
     */
    @Test
    public void testCreatureTypeLoss() {
        addCard(Constants.Zone.HAND, playerA, "Trusted Forcemage");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Blinkmoth Nexus", 1);

        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "{1}: ");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Trusted Forcemage");

        setStopAt(2, Constants.PhaseStep.PRECOMBAT_MAIN);
        execute();

        // test boost loss
        assertPowerToughness(playerA, "Trusted Forcemage", 2, 2);
    }

    /**
     * Tests that after loosing first pair it is possible to pair creature with another one
     */
    @Test
    public void testRebondOnNextCreature() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Constants.Zone.HAND, playerA, "Phantasmal Bear");
        addCard(Constants.Zone.HAND, playerA, "Trusted Forcemage");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 3);

        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt", 1);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Trusted Forcemage");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Elite Vanguard");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Phantasmal Bear");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Elite Vanguard", 0);
        assertPermanentCount(playerA, "Phantasmal Bear", 1);

        assertPowerToughness(playerA, "Trusted Forcemage", 3, 3);
        assertPowerToughness(playerA, "Phantasmal Bear", 3, 3);
    }

    /**
     * Tests Soulbond that adds an ability to both creatures
     */
    @Test
    public void testGrantingAbility() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Constants.Zone.HAND, playerA, "Nearheath Pilgrim");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Nearheath Pilgrim");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Nearheath Pilgrim", 2, 1);
        assertPowerToughness(playerA, "Elite Vanguard", 2, 1);

        Abilities abilities = new AbilitiesImpl();
        abilities.add(LifelinkAbility.getInstance());
        assertAbilities(playerA, "Nearheath Pilgrim", abilities);
        assertAbilities(playerA, "Elite Vanguard", abilities);
    }

    @Test
    public void testExileAndReturnBack() {
        addCard(Constants.Zone.HAND, playerA, "Elite Vanguard");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Trusted Forcemage");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Vanguard");
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Cloudshift", "Trusted Forcemage");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Trusted Forcemage", 1);
        assertPowerToughness(playerA, "Trusted Forcemage", 3, 3);
        assertPowerToughness(playerA, "Elite Vanguard", 3, 2);
    }
}
