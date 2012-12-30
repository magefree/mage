package org.mage.test.cards.abilities.flicker;

import mage.Constants;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.IntimidateAbility;
import mage.abilities.keyword.LifelinkAbility;
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
     */
    @Test
    public void testSpellFizzle() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain");

        addCard(Constants.Zone.HAND, playerA, "Cloudshift");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Elite Vanguard");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", "Elite Vanguard");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        // should be alive because of Cloudshift
        assertPermanentCount(playerA, "Elite Vanguard", 1);
    }

    /**
     * Tests that copy effect is discarded and Clone can enter as a copy of another creature.
     * Also tests that copy two creature won't 'collect' abilities, after 'Cloudshift' effect Clone should enter as a copy of another creature.
     */
    @Test
    public void testCopyEffectDiscarded() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 4);

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Knight of Meadowgrain");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Heirs of Stromkirk");

        addCard(Constants.Zone.HAND, playerA, "Clone");
        addCard(Constants.Zone.HAND, playerA, "Cloudshift");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, "Knight of Meadowgrain");
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Cloudshift", "Clone");
        setChoice(playerA, "Heirs of Stromkirk");

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        Permanent clone = getPermanent("Heirs of Stromkirk", playerA.getId());
        Assert.assertNotNull(clone);
        Assert.assertTrue(clone.getAbilities().contains(IntimidateAbility.getInstance()));
        Assert.assertFalse(clone.getAbilities().contains(LifelinkAbility.getInstance()));
        Assert.assertFalse(clone.getAbilities().contains(FirstStrikeAbility.getInstance()));
    }
    @Test
    public void testEquipmentDetached() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Bonesplitter");

        addCard(Constants.Zone.HAND, playerA, "Cloudshift");

        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {1}", "Silvercoat Lion");
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Cloudshift", "Silvercoat Lion");

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        Permanent bonesplitter = getPermanent("Bonesplitter", playerA.getId());
        Permanent silvercoatLion = getPermanent("Silvercoat Lion", playerA.getId());

        assertLife(playerA, 20);
        Assert.assertTrue(silvercoatLion.getAttachments().isEmpty());
        Assert.assertTrue("Bonesplitter must not be connected to Silvercoat Lion",bonesplitter.getAttachedTo() == null);
        Assert.assertEquals("Silvercoat Lion's power without equipment has to be 2",2, silvercoatLion.getPower().getValue());
        Assert.assertEquals("Silvercoat Lion's toughness has to be 2",2, silvercoatLion.getToughness().getValue());
    }

}
