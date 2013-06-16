package org.mage.test.cards.abilities.flicker;

import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.IntimidateAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
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
     * Tests that copy effect is discarded and Clone can enter as a copy of another creature.
     * Also tests that copy two creature won't 'collect' abilities, after 'Cloudshift' effect Clone should enter as a copy of another creature.
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
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cloudshift", "Clone");
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

        addCard(Zone.HAND, playerA, "Cloudshift");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {1}", "Silvercoat Lion");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cloudshift", "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
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
