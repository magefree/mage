package org.mage.test.cards.abilities.keywords;

import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class SoulbondKeywordTest extends CardTestPlayerBase {

    @Test
    public void testPairOnCast() {
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.HAND, playerA, "Trusted Forcemage");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Trusted Forcemage");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
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
        addCard(Zone.HAND, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerA, "Trusted Forcemage");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Vanguard");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
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
        addCard(Zone.HAND, playerA, "Trusted Forcemage", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Trusted Forcemage");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Trusted Forcemage");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Trusted Forcemage", 2);
        assertPowerToughness(playerA, "Trusted Forcemage", 4, 4, Filter.ComparisonScope.All);
    }

    /**
     * Tests no Soulbond effect possible on single creature
     */
    @Test
    public void testNoPairOnSingleCreature() {
        addCard(Zone.HAND, playerA, "Trusted Forcemage", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Trusted Forcemage");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Trusted Forcemage", 1);
        assertPowerToughness(playerA, "Trusted Forcemage", 2, 2);
    }

    /**
     * Tests Soulbond effect disabling whenever Soulbond creature changes its
     * controller
     */
    @Test
    public void testChangeControllerForSoulbondCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        // Soulbond (You may pair this creature with another unpaired creature when either enters the battlefield. They remain paired for as long as you control both of them.)
        // As long as Trusted Forcemage is paired with another creature, each of those creatures gets +1/+1.
        addCard(Zone.HAND, playerA, "Trusted Forcemage");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        // Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn. (It can attack and Tap this turn.)
        addCard(Zone.HAND, playerB, "Act of Treason");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Trusted Forcemage");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Act of Treason", "Trusted Forcemage");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Trusted Forcemage", 0);
        assertPermanentCount(playerA, "Elite Vanguard", 1);
        assertPowerToughness(playerA, "Elite Vanguard", 2, 1);

        assertPermanentCount(playerB, "Trusted Forcemage", 1);
        assertPowerToughness(playerB, "Trusted Forcemage", 2, 2);

    }

    /**
     * Tests Soulbond effect disabling when paired creture changes its
     * controller
     */
    @Test
    public void testChangeControllerForAnotherCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard"); // 2,1
        // Soulbond (You may pair this creature with another unpaired creature when either enters the battlefield. They remain paired for as long as you control both of them.)
        // As long as Trusted Forcemage is paired with another creature, each of those creatures gets +1/+1.
        addCard(Zone.HAND, playerA, "Trusted Forcemage"); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        addCard(Zone.HAND, playerB, "Act of Treason");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Trusted Forcemage");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Act of Treason", "Elite Vanguard");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Trusted Forcemage", 1);
        assertPermanentCount(playerA, "Elite Vanguard", 0);
        assertPowerToughness(playerA, "Trusted Forcemage", 2, 2);

        assertPermanentCount(playerB, "Elite Vanguard", 1);
        assertPowerToughness(playerB, "Elite Vanguard", 2, 1);
    }

    /**
     * Tests Soulbond effect disabling when Soulbond creature changes its
     * controller and then returns back. Effect should not be restored.
     */
    @Test
    public void testChangeControllerAndGettingBack() {
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.HAND, playerA, "Trusted Forcemage");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        addCard(Zone.HAND, playerB, "Act of Treason");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Trusted Forcemage");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Act of Treason", "Trusted Forcemage");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Trusted Forcemage", 1);
        assertPowerToughness(playerA, "Trusted Forcemage", 2, 2);
        assertPermanentCount(playerA, "Elite Vanguard", 1);
        assertPowerToughness(playerA, "Elite Vanguard", 2, 1);
    }

    /**
     * Tests that stealing creature will allow to use Soulbond ability on
     * controller's creature
     */
    @Test
    public void testSoulbondWorksOnControllerSide() {
        addCard(Zone.HAND, playerA, "Trusted Forcemage");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        addCard(Zone.HAND, playerB, "Act of Treason");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Zone.HAND, playerB, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Trusted Forcemage");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Act of Treason", "Trusted Forcemage");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Elite Vanguard");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
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
        addCard(Zone.HAND, playerA, "Trusted Forcemage");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        addCard(Zone.HAND, playerB, "Act of Treason");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Zone.HAND, playerB, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Trusted Forcemage");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Act of Treason", "Trusted Forcemage");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Elite Vanguard");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN); // Effect of "Act of Treason" will end here
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
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.HAND, playerA, "Trusted Forcemage");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, "Unsummon", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Trusted Forcemage");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unsummon", "Elite Vanguard");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Elite Vanguard", 0);
        assertPowerToughness(playerA, "Trusted Forcemage", 2, 2);
    }

    /**
     * Tests that it is possible to animate land and pair it on next coming
     * Soulbond creature
     */
    @Test
    public void testPairOnAnimatedLand() {
        addCard(Zone.HAND, playerA, "Trusted Forcemage");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Blinkmoth Nexus", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}: ");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Trusted Forcemage");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // test paired with boost
        assertPowerToughness(playerA, "Trusted Forcemage", 3, 3);
        assertPowerToughness(playerA, "Blinkmoth Nexus", 2, 2);
    }

    /**
     * Tests no effect whether land was animated after Soulbond creature has
     * entered the battlefield
     */
    @Test
    public void testPairOnPostAnimatedLand() {
        addCard(Zone.HAND, playerA, "Trusted Forcemage");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Blinkmoth Nexus", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Trusted Forcemage");
        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "{1}: ");

        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
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
        addCard(Zone.HAND, playerA, "Trusted Forcemage");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Blinkmoth Nexus", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}: ");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Trusted Forcemage");

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // test boost loss
        assertPowerToughness(playerA, "Trusted Forcemage", 2, 2);
    }

    /**
     * Tests that after loosing first pair it is possible to pair creature with
     * another one
     */
    @Test
    public void testRebondOnNextCreature() {
        // When Phantasmal Bear becomes the target of a spell or ability, sacrifice it.
        addCard(Zone.HAND, playerA, "Phantasmal Bear");
        // Soulbond
        // As long as Trusted Forcemage is paired with another creature, each of those creatures gets +1/+1.
        addCard(Zone.HAND, playerA, "Trusted Forcemage");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard"); // 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Trusted Forcemage");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Elite Vanguard");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Phantasmal Bear");

        setStopAt(1, PhaseStep.END_TURN);
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
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard"); // 2/1
        // Soulbond
        // As long as Nearheath Pilgrim is paired with another creature, both creatures have lifelink.
        addCard(Zone.HAND, playerA, "Nearheath Pilgrim");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nearheath Pilgrim");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Nearheath Pilgrim", 2, 1);
        assertPowerToughness(playerA, "Elite Vanguard", 2, 1);

        Abilities<Ability> abilities = new AbilitiesImpl<>();
        abilities.add(LifelinkAbility.getInstance());
        assertAbilities(playerA, "Nearheath Pilgrim", abilities);
        assertAbilities(playerA, "Elite Vanguard", abilities);
    }

    @Test
    public void testExileAndReturnBack() {
        addCard(Zone.HAND, playerA, "Elite Vanguard");
        addCard(Zone.HAND, playerA, "Cloudshift");
        // Soulbond (You may pair this creature with another unpaired creature when either enters the battlefield. They remain paired for as long as you control both of them.)
        // As long as Trusted Forcemage is paired with another creature, each of those creatures gets +1/+1.
        addCard(Zone.BATTLEFIELD, playerA, "Trusted Forcemage");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Vanguard");
        setChoice(playerA, true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cloudshift", "Trusted Forcemage");
        setChoice(playerA, false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Trusted Forcemage", 1);
        assertPowerToughness(playerA, "Trusted Forcemage", 2, 2);
        assertPowerToughness(playerA, "Elite Vanguard", 2, 1);

        Permanent trustedForcemange = getPermanent("Trusted Forcemage", playerA.getId());
        Permanent eliteVanguard = getPermanent("Elite Vanguard", playerA.getId());
        Assert.assertNull(trustedForcemange.getPairedCard());
        Assert.assertNull(eliteVanguard.getPairedCard());
    }

    /**
     * Reported bug: Soulbond should use the stack, but unable to use instant speed removal since no trigger occurs
     */
    @Test
    public void testRespondToSoulboundWithRemoval() {
        // When Palinchron enters the battlefield, untap up to seven lands.
        // {2}{U}{U}: Return Palinchron to its owner's hand.
        addCard(Zone.BATTLEFIELD, playerA, "Palinchron"); // 4/5 flying

        // Soulbond
        // As long as Deadeye Navigator is paired with another creature, each of those creatures has "{1}{U}: Exile this creature, then return it to the battlefield under your control."
        addCard(Zone.HAND, playerA, "Deadeye Navigator"); // 5/5
        addCard(Zone.BATTLEFIELD, playerA, "Island", 8);
        addCard(Zone.HAND, playerB, "Doom Blade", 1); // {1}{B} instant: Destroy target non-black creature
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deadeye Navigator");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Doom Blade", "Deadeye Navigator");

        // Deadeye's ability should not be usable since was destroyed before Soulbond trigger resolved
        checkPlayableAbility("Can't activate", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{U}:", false);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        Permanent palinchron = getPermanent("Palinchron", playerA);
        Assert.assertNull(palinchron.getPairedCard()); // should not be paired
        assertGraveyardCount(playerA, "Deadeye Navigator", 1);
        assertGraveyardCount(playerB, "Doom Blade", 1);
        assertPermanentCount(playerA, "Palinchron", 1);
    }
}
