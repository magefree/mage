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

    private static final String vanguard = "Elite Vanguard"; // 2/1
    private static final String forcemage = "Trusted Forcemage"; // 2/2 soulbond +1/+1
    private static final String treason = "Act of Treason";
    private static final String blinkmoth = "Blinkmoth Nexus";
    private static final String nearheath = "Nearheath Pilgrim"; // 2/1 soulbond lifelink
    private static final String phantasmalBear = "Phantasmal Bear"; // 2/2
    private static final String bolt = "Lightning Bolt";

    @Test
    public void testPairOnCast() {
        addCard(Zone.BATTLEFIELD, playerA, vanguard);
        addCard(Zone.HAND, playerA, forcemage);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, forcemage);
        setChoice(playerA, true);
        setChoice(playerA, vanguard);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, forcemage, 1);
        assertPowerToughness(playerA, forcemage, 3, 3);
        assertPowerToughness(playerA, vanguard, 3, 2);
    }

    /**
     * Tests pair on another creature without Soulbond entering battlefield
     */
    @Test
    public void testPairOnEntersBattlefield() {
        addCard(Zone.HAND, playerA, vanguard);
        addCard(Zone.BATTLEFIELD, playerA, forcemage);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, vanguard);
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, forcemage, 1);
        assertPowerToughness(playerA, forcemage, 3, 3);
        assertPowerToughness(playerA, vanguard, 3, 2);
    }

    /**
     * Tests two creatures with Soulbond paired with each other
     */
    @Test
    public void testTwoSoulbondCreaturesOnBattlefield() {
        addCard(Zone.HAND, playerA, forcemage, 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, forcemage, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, forcemage);
        setChoice(playerA, "Soulbond"); // order triggers
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, forcemage, 2);
        assertPowerToughness(playerA, forcemage, 4, 4, Filter.ComparisonScope.All);
    }

    /**
     * Tests no Soulbond effect possible on single creature
     */
    @Test
    public void testNoPairOnSingleCreature() {
        addCard(Zone.HAND, playerA, forcemage, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, forcemage);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, forcemage, 1);
        assertPowerToughness(playerA, forcemage, 2, 2);
    }

    /**
     * Tests Soulbond effect disabling whenever Soulbond creature changes its
     * controller
     */
    @Test
    public void testChangeControllerForSoulbondCreature() {
        addCard(Zone.BATTLEFIELD, playerA, vanguard);
        // Soulbond (You may pair this creature with another unpaired creature when either enters the battlefield. They remain paired for as long as you control both of them.)
        // As long as Trusted Forcemage is paired with another creature, each of those creatures gets +1/+1.
        addCard(Zone.HAND, playerA, forcemage);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        // Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn. (It can attack and Tap this turn.)
        addCard(Zone.HAND, playerB, treason);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, forcemage);
        setChoice(playerA, true);
        setChoice(playerA, vanguard);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, treason, forcemage);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, forcemage, 0);
        assertPermanentCount(playerA, vanguard, 1);
        assertPowerToughness(playerA, vanguard, 2, 1);

        assertPermanentCount(playerB, forcemage, 1);
        assertPowerToughness(playerB, forcemage, 2, 2);

    }

    /**
     * Tests Soulbond effect disabling when paired creature changes its
     * controller
     */
    @Test
    public void testChangeControllerForAnotherCreature() {
        addCard(Zone.BATTLEFIELD, playerA, vanguard); // 2,1
        // Soulbond (You may pair this creature with another unpaired creature when either enters the battlefield. They remain paired for as long as you control both of them.)
        // As long as Trusted Forcemage is paired with another creature, each of those creatures gets +1/+1.
        addCard(Zone.HAND, playerA, forcemage); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        addCard(Zone.HAND, playerB, treason);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, forcemage);
        setChoice(playerA, true);
        setChoice(playerA, vanguard);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, treason, vanguard);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, forcemage, 1);
        assertPermanentCount(playerA, vanguard, 0);
        assertPowerToughness(playerA, forcemage, 2, 2);

        assertPermanentCount(playerB, vanguard, 1);
        assertPowerToughness(playerB, vanguard, 2, 1);
    }

    /**
     * Tests Soulbond effect disabling when Soulbond creature changes its
     * controller and then returns back. Effect should not be restored.
     */
    @Test
    public void testChangeControllerAndGettingBack() {
        addCard(Zone.BATTLEFIELD, playerA, vanguard);
        addCard(Zone.HAND, playerA, forcemage);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        addCard(Zone.HAND, playerB, treason);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, forcemage);
        setChoice(playerA, true);
        setChoice(playerA, vanguard);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, treason, forcemage);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, forcemage, 1);
        assertPowerToughness(playerA, forcemage, 2, 2);
        assertPermanentCount(playerA, vanguard, 1);
        assertPowerToughness(playerA, vanguard, 2, 1);
    }

    /**
     * Tests that stealing creature will allow to use Soulbond ability on
     * controller's creature
     */
    @Test
    public void testSoulbondWorksOnControllerSide() {
        addCard(Zone.HAND, playerA, forcemage);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        addCard(Zone.HAND, playerB, treason);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Zone.HAND, playerB, vanguard);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, forcemage);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, treason, forcemage);
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, vanguard);
        setChoice(playerB, true);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        // stolen
        assertPermanentCount(playerA, forcemage, 0);

        // both paired and have boost
        assertPowerToughness(playerB, forcemage, 3, 3);
        assertPowerToughness(playerB, vanguard, 3, 2);
    }

    /**
     * Tests effect also disappeared when creature is returned back to owner
     */
    @Test
    public void testReturnBack() {
        addCard(Zone.HAND, playerA, forcemage);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        addCard(Zone.HAND, playerB, treason);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Zone.HAND, playerB, vanguard);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, forcemage);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, treason, forcemage);
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, vanguard);
        setChoice(playerB, true);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN); // Effect of "Act of Treason" will end here
        execute();

        // returned back with no boost
        assertPermanentCount(playerA, forcemage, 1);
        assertPowerToughness(playerA, forcemage, 2, 2);

        // no boost on next turn (gets unpaired)
        assertPowerToughness(playerB, vanguard, 2, 1);
    }

    /**
     * Tests returning one of the paired creatures back to its owner's hand
     */
    @Test
    public void testUnsummon() {
        addCard(Zone.BATTLEFIELD, playerA, vanguard);
        addCard(Zone.HAND, playerA, forcemage);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, "Unsummon", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, forcemage);
        setChoice(playerA, true);
        setChoice(playerA, vanguard);

        checkPT("paired boost", 1, PhaseStep.BEGIN_COMBAT, playerA, forcemage, 3, 3);
        checkPT("paired boost", 1, PhaseStep.BEGIN_COMBAT, playerA, vanguard, 3, 2);

        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerA, "Unsummon", vanguard);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, vanguard, 0);
        assertPowerToughness(playerA, forcemage, 2, 2);
    }

    /**
     * Tests that it is possible to animate land and pair it on next coming
     * Soulbond creature
     */
    @Test
    public void testPairOnAnimatedLand() {
        addCard(Zone.HAND, playerA, forcemage);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, blinkmoth, 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}: ");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, forcemage);
        setChoice(playerA, true);
        setChoice(playerA, blinkmoth);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // test paired with boost
        assertPowerToughness(playerA, forcemage, 3, 3);
        assertPowerToughness(playerA, blinkmoth, 2, 2);
    }

    /**
     * Tests no effect whether land was animated after Soulbond creature has
     * entered the battlefield
     */
    @Test
    public void testPairOnPostAnimatedLand() {
        addCard(Zone.HAND, playerA, forcemage);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, blinkmoth, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, forcemage);
        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "{1}: ");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();

        // no effect on later animation
        assertPowerToughness(playerA, forcemage, 2, 2);
        assertPowerToughness(playerA, blinkmoth, 1, 1);
    }

    /**
     * Tests that creature type loss leads to Soulbond effect disabling
     */
    @Test
    public void testCreatureTypeLoss() {
        addCard(Zone.HAND, playerA, forcemage);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, blinkmoth, 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}: ");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, forcemage);
        setChoice(playerA, true);
        setChoice(playerA, blinkmoth);

        checkPT("paired boost", 1, PhaseStep.BEGIN_COMBAT, playerA, forcemage, 3, 3);
        checkPT("paired boost", 1, PhaseStep.BEGIN_COMBAT, playerA, blinkmoth, 2, 2);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // test boost loss
        assertPowerToughness(playerA, forcemage, 2, 2);
    }

    /**
     * Tests that after loosing first pair it is possible to pair creature with
     * another one
     */
    @Test
    public void testRebondOnNextCreature() {
        // When Phantasmal Bear becomes the target of a spell or ability, sacrifice it.
        addCard(Zone.HAND, playerA, phantasmalBear);
        // Soulbond
        // As long as Trusted Forcemage is paired with another creature, each of those creatures gets +1/+1.
        addCard(Zone.HAND, playerA, forcemage);
        addCard(Zone.BATTLEFIELD, playerA, vanguard); // 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        addCard(Zone.HAND, playerA, bolt, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, forcemage, true);
        setChoice(playerA, true);
        setChoice(playerA, vanguard);

        checkPT("paired boost", 1, PhaseStep.BEGIN_COMBAT, playerA, forcemage, 3, 3);
        checkPT("paired boost", 1, PhaseStep.BEGIN_COMBAT, playerA, vanguard, 3, 2);

        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerA, bolt, vanguard);

        checkPT("unpaired", 1, PhaseStep.END_COMBAT, playerA, forcemage, 2, 2);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, phantasmalBear);
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, vanguard, 0);
        assertPermanentCount(playerA, phantasmalBear, 1);

        assertPowerToughness(playerA, forcemage, 3, 3);
        assertPowerToughness(playerA, phantasmalBear, 3, 3);
    }

    /**
     * Tests Soulbond that adds an ability to both creatures
     */
    @Test
    public void testGrantingAbility() {
        addCard(Zone.BATTLEFIELD, playerA, vanguard); // 2/1
        // Soulbond
        // As long as Nearheath Pilgrim is paired with another creature, both creatures have lifelink.
        addCard(Zone.HAND, playerA, nearheath);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, nearheath);
        setChoice(playerA, true);
        setChoice(playerA, vanguard);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, nearheath, 2, 1);
        assertPowerToughness(playerA, vanguard, 2, 1);

        Abilities<Ability> abilities = new AbilitiesImpl<>();
        abilities.add(LifelinkAbility.getInstance());
        assertAbilities(playerA, nearheath, abilities);
        assertAbilities(playerA, vanguard, abilities);
    }

    @Test
    public void testExileAndReturnBack() {
        addCard(Zone.HAND, playerA, vanguard);
        addCard(Zone.HAND, playerA, "Cloudshift");
        // Soulbond (You may pair this creature with another unpaired creature when either enters the battlefield. They remain paired for as long as you control both of them.)
        // As long as Trusted Forcemage is paired with another creature, each of those creatures gets +1/+1.
        addCard(Zone.BATTLEFIELD, playerA, forcemage);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, vanguard);
        setChoice(playerA, true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cloudshift", forcemage);
        setChoice(playerA, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, forcemage, 1);
        assertPowerToughness(playerA, forcemage, 2, 2);
        assertPowerToughness(playerA, vanguard, 2, 1);

        Permanent trustedForcemage = getPermanent(forcemage, playerA.getId());
        Permanent eliteVanguard = getPermanent(vanguard, playerA.getId());
        Assert.assertNull(trustedForcemage.getPairedCard());
        Assert.assertNull(eliteVanguard.getPairedCard());
    }

    /**
     * Reported bug: Soulbond should use the stack, but unable to use instant speed removal since no trigger occurs
     */
    @Test
    public void testRespondToSoulbondWithRemoval() {
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

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        Permanent palinchron = getPermanent("Palinchron", playerA);
        Assert.assertNull(palinchron.getPairedCard()); // should not be paired
        assertGraveyardCount(playerA, "Deadeye Navigator", 1);
        assertGraveyardCount(playerB, "Doom Blade", 1);
        assertPermanentCount(playerA, "Palinchron", 1);
    }
}
