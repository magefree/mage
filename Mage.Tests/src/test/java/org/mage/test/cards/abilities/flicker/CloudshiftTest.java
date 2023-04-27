package org.mage.test.cards.abilities.flicker;

import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.IntimidateAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class CloudshiftTest extends CardTestPlayerBase {

    /**
     * Tests that casting Cloudshift makes targeting spell fizzling
     *
     * Cloudshift Exile target creature you control, then return that card to
     * the battlefield under your control.
     */
    @Test
    public void testSpellFizzle() {
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        addCard(Zone.HAND, playerA, "Cloudshift");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Elite Vanguard");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", "Elite Vanguard");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // should be alive because of Cloudshift
        assertPermanentCount(playerA, "Elite Vanguard", 1);
    }

    /**
     * Tests that copy effect is discarded and Clone can enter as a copy of
     * another creature. Also tests that copy two creature won't 'collect'
     * abilities, after 'Cloudshift' effect Clone should enter as a copy of
     * another creature.
     */
    @Test
    public void testCopyEffectDiscarded() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        addCard(Zone.BATTLEFIELD, playerB, "Knight of Meadowgrain");
        addCard(Zone.BATTLEFIELD, playerB, "Heirs of Stromkirk");

        addCard(Zone.HAND, playerA, "Clone");
        addCard(Zone.HAND, playerA, "Cloudshift");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, "Knight of Meadowgrain");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cloudshift", "Knight of Meadowgrain"); // clone has name of copied permanent
        setChoice(playerA, "Heirs of Stromkirk");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        Permanent clone = getPermanent("Heirs of Stromkirk", playerA.getId());
        Assert.assertNotNull(clone);
        Assert.assertTrue(clone.getAbilities().contains(IntimidateAbility.getInstance()));
        Assert.assertFalse(clone.getAbilities().contains(LifelinkAbility.getInstance()));
        Assert.assertFalse(clone.getAbilities().contains(FirstStrikeAbility.getInstance()));
    }

    @Test
    public void testEquipmentDetached() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Bonesplitter");

        // Exile target creature you control, then return that card to the battlefield under your control.
        addCard(Zone.HAND, playerA, "Cloudshift");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {1}", "Silvercoat Lion");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cloudshift", "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        Permanent bonesplitter = getPermanent("Bonesplitter", playerA.getId());
        Permanent silvercoatLion = getPermanent("Silvercoat Lion", playerA.getId());

        assertLife(playerA, 20);
        Assert.assertTrue(silvercoatLion.getAttachments().isEmpty());
        Assert.assertTrue("Bonesplitter must not be connected to Silvercoat Lion", bonesplitter.getAttachedTo() == null);
        Assert.assertEquals("Silvercoat Lion's power without equipment has to be 2", 2, silvercoatLion.getPower().getValue());
        Assert.assertEquals("Silvercoat Lion's toughness has to be 2", 2, silvercoatLion.getToughness().getValue());
    }

    /**
     * Tests that casting Cloudshift makes creature able to block again if it
     * before was targeted with can't block effect
     *
     */
    @Test
    public void testCreatureCanBlockAgainAfterCloudshift() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Timberland Guide");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);

        addCard(Zone.HAND, playerA, "Cloudshift");
        // Haste
        // When Fervent Cathar enters the battlefield, target creature can't block this turn.
        addCard(Zone.HAND, playerB, "Fervent Cathar");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Fervent Cathar");
        addTarget(playerB, "Timberland Guide");
        attack(2, playerB, "Fervent Cathar");
        castSpell(2, PhaseStep.DECLARE_ATTACKERS, playerA, "Cloudshift", "Timberland Guide");
        block(2, playerA, "Timberland Guide", "Fervent Cathar");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        // blocked and therefore no more on the battlefield
        assertPermanentCount(playerB, "Fervent Cathar", 0);
        assertPermanentCount(playerA, "Timberland Guide", 0);

    }

    @Test
    public void testThatCardIsHandledAsNewInstanceAfterCloudshift() {
        // Whenever another creature enters the battlefield under your control, you gain life equal to that creature's toughness.
        // {1}{G}{W}, {T}: Populate. (Create a tokenonto the battlefield that's a copy of a creature token you control.)
        addCard(Zone.BATTLEFIELD, playerA, "Trostani, Selesnya's Voice");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Grizzly Bears"); // 2/2
        addCard(Zone.HAND, playerA, "Giant Growth");
        addCard(Zone.HAND, playerA, "Cloudshift");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", "Grizzly Bears", "you gain life equal to that creature's toughness");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", "Grizzly Bears", null, "you gain life equal to that creature's toughness");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);

        execute();

        assertLife(playerA, 27); // 5 from the first with Giant Growth + 2 from the second bear.
    }

    /*
    I had a Stoneforge Mystic equipped with a Umesawa's Jitte. I activated Jitte 4 times to make
    Stoneforge Mystic 9/10. My opponent put into play a Flickerwisp with their Aether Vial and
    targeted my Stoneforge Mystic. At the end of my turn, Stoneforge Mystic came back as a 9/10,
    before going down to 1/2 normally once my turn ended.
     */
    @Test
    public void testDontApplyEffectToNewInstanceOfPreviousEquipedPermanent() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Umezawa's Jitte");

        // Exile target creature you control, then return that card to the battlefield under your control.
        addCard(Zone.HAND, playerA, "Cloudshift");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {2}", "Silvercoat Lion");

        attack(3, playerA, "Silvercoat Lion");

        activateAbility(3, PhaseStep.END_COMBAT, playerA, "Remove a charge counter from {this}: Choose one &mdash;<br>&bull  Equipped creature gets");
        setModeChoice(playerA, "1");
        castSpell(3, PhaseStep.END_COMBAT, playerA, "Cloudshift", "Silvercoat Lion", "Remove a charge counter from");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        Permanent Umezawa = getPermanent("Umezawa's Jitte", playerA.getId());
        Permanent silvercoatLion = getPermanent("Silvercoat Lion", playerA.getId());

        assertLife(playerA, 20);
        assertLife(playerB, 18);
        assertCounterCount("Umezawa's Jitte", CounterType.CHARGE, 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerA, "Cloudshift", 1);
        Assert.assertTrue(silvercoatLion.getAttachments().isEmpty());
        Assert.assertTrue("Umezawa must not be connected to Silvercoat Lion", Umezawa.getAttachedTo() == null);
        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2);

    }

    @Test
    public void testDontApplyEffectToNewInstanceOfPreviousEquipedPermanentFlickerwisp() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Umezawa's Jitte");

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);
        // Flying
        // When Flickerwisp enters the battlefield, exile another target permanent. Return that
        // card to the battlefield under its owner's control at the beginning of the next end step.
        addCard(Zone.HAND, playerB, "Flickerwisp");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {2}", "Silvercoat Lion");

        attack(3, playerA, "Silvercoat Lion");

        activateAbility(4, PhaseStep.DRAW, playerA, "Remove a charge counter from {this}: Choose one &mdash;<br>&bull  Equipped creature gets");
        setModeChoice(playerA, "1");
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Flickerwisp");
        addTarget(playerB, "Silvercoat Lion");

        setStopAt(4, PhaseStep.END_TURN);
        execute();

        Permanent Umezawa = getPermanent("Umezawa's Jitte", playerA.getId());
        Permanent silvercoatLion = getPermanent("Silvercoat Lion", playerA.getId());

        assertLife(playerA, 20);
        assertLife(playerB, 18);
        assertCounterCount("Umezawa's Jitte", CounterType.CHARGE, 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Flickerwisp", 1);
        Assert.assertTrue(silvercoatLion.getAttachments().isEmpty());
        Assert.assertTrue("Umezawa must not be connected to Silvercoat Lion", Umezawa.getAttachedTo() == null);
        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2);

    }

    /**
     * Test that if I cast cloudshift and it goes to the stack and another
     * instant spell exiles the target creature as response, cloudshift does not
     * bring back that creature from exile because it's a complete other object
     * (400.7). 400.7g allows Cloudshift to bring it back only if it was exiled
     * by cloudshift itself.
     *
     */
    @Test
    public void testReturnIfExiledByAnotherSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.HAND, playerA, "Cloudshift");

        addCard(Zone.BATTLEFIELD, playerB, "Plains");
        // Exile target creature. Its controller gains life equal to its power.
        addCard(Zone.HAND, playerB, "Swords to Plowshares");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Swords to Plowshares", "Silvercoat Lion", "Cloudshift");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Cloudshift", 1);
        assertGraveyardCount(playerB, "Swords to Plowshares", 1);

        assertLife(playerA, 22);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Silvercoat Lion", 0);
        assertExileCount("Silvercoat Lion", 1);

    }

    /**
     * Test that if a creature returns from cloudshift it returns under the
     * control of the controller of Cloudshift.
     */
    @Test
    public void testReturnOfOwnerIsAnotherPlayer() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn.
        addCard(Zone.HAND, playerA, "Act of Treason");

        addCard(Zone.HAND, playerA, "Cloudshift");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Act of Treason", "Silvercoat Lion");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cloudshift", "Silvercoat Lion");

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Cloudshift", 1);
        assertGraveyardCount(playerA, "Act of Treason", 1);

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 0);

    }

    /**
     * Test that if a creature returns from Conjurer's Closet it returns under
     * the control of the controller of Conjurer's Closet.
     */
    @Test
    public void testReturnOfOwnerIsAnotherPlayerConjurersCloset() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn.
        addCard(Zone.HAND, playerA, "Act of Treason");
        // At the beginning of your end step, you may exile target creature you control, then return that card to the battlefield under your control
        addCard(Zone.BATTLEFIELD, playerA, "Conjurer's Closet");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Act of Treason", "Silvercoat Lion");
        // Silvercoat Lion is autochosen

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Conjurer's Closet", 1);
        assertGraveyardCount(playerA, "Act of Treason", 1);

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 0);

    }

    /**
     * During a game i play a Flickerwisp main step who targets something and a
     * second Flickerwisp who targets the first . End step : the first
     * Flickerwisp return at the battlefield and target a Courser of Kruphix,
     * normally she's return on the battlefield at the next end step (here end
     * step of my opponent) but she's returned on the battlefield immediatly
     *
     * 8/1/2008 The exiled card will return to the battlefield at the beginning
     * of the end step even if Flickerwisp is no longer on the battlefield.
     * 8/1/2008 If the permanent that returns to the battlefield has any
     * abilities that trigger at the beginning of the end step, those abilities
     * won't trigger that turn.
     */
    @Test
    public void testDoubleFlickerwisp() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        // Flying
        // When Flickerwisp enters the battlefield, exile another target permanent.
        // Return that card to the battlefield under its owner's control at the beginning of the next end step.
        addCard(Zone.HAND, playerA, "Flickerwisp", 2); // Creature {1}{W}{W}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Courser of Kruphix");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flickerwisp");
        addTarget(playerA, "Silvercoat Lion");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flickerwisp");
        addTarget(playerA, "Flickerwisp");
        addTarget(playerA, "Courser of Kruphix");

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Flickerwisp", 2);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Courser of Kruphix", 0);
        assertExileCount("Courser of Kruphix", 1);
    }
}
