package org.mage.test.cards.abilities.lose;

import mage.Constants;
import mage.abilities.keyword.FlyingAbility;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class LoseAbilityTest extends CardTestPlayerBase {

    @Test
    public void testLoseFlyingByEnchantCreature() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Constants.Zone.HAND, playerA, "Grounded", 2);

        addCard(Constants.Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Air Elemental");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Grounded", "Elite Vanguard");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Grounded", "Air Elemental");

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);

        Permanent eliteVanguard = getPermanent("Elite Vanguard", playerA.getId());
        Assert.assertNotNull(eliteVanguard);
        Assert.assertFalse(eliteVanguard.getAbilities().contains(FlyingAbility.getInstance()));

        Permanent airElemental = getPermanent("Air Elemental", playerA.getId());
        Assert.assertNotNull(airElemental);
        // should NOT have flying
        Assert.assertFalse(airElemental.getAbilities().contains(FlyingAbility.getInstance()));
    }

    /**
     * Tests that first losing ability and then gaining it will results in Flying existence
     */
    @Test
    public void testLoseVsGainAbility() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Air Elemental");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Constants.Zone.HAND, playerA, "Grounded");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Constants.Zone.HAND, playerA, "Drake Umbra");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Grounded", "Air Elemental");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Drake Umbra", "Air Elemental");

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        Permanent airElemental = getPermanent("Air Elemental", playerA.getId());
        Assert.assertNotNull(airElemental);

        Assert.assertTrue(airElemental.getAttachments().size() == 2);
        // should have flying
        Assert.assertTrue(airElemental.getAbilities().contains(FlyingAbility.getInstance()));
    }

    /**
     * Tests that first gaining multiple copies of ability and then losing it will results in Flying not existence
     */
    @Test
    public void testMultiGainVsLoseAbility() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Air Elemental");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 10);
        addCard(Constants.Zone.HAND, playerA, "Grounded");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 10);
        addCard(Constants.Zone.HAND, playerA, "Drake Umbra", 2);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Drake Umbra", "Air Elemental");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Drake Umbra", "Air Elemental");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Grounded", "Air Elemental");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        Permanent airElemental = getPermanent("Air Elemental", playerA.getId());
        Assert.assertNotNull(airElemental);

        Assert.assertEquals(3, airElemental.getAttachments().size());
        // should NOT have flying
        Assert.assertFalse(airElemental.getAbilities().contains(FlyingAbility.getInstance()));
    }
}
