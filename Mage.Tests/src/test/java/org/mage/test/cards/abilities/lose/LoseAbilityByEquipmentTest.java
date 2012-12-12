package org.mage.test.cards.abilities.lose;

import mage.Constants;
import mage.abilities.keyword.FlyingAbility;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class LoseAbilityByEquipmentTest extends CardTestPlayerBase {

    /**
     * Tests that gaining flying by and after that losing flying by eqipments results in not have flying
     */
    @Test
    public void testGainVsLoseByEquipmentAbility() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 4);

        addCard(Constants.Zone.HAND, playerA, "Magebane Armor"); // loses Flying
        addCard(Constants.Zone.HAND, playerA, "Cobbled Wings"); // gives Flying

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Magebane Armor");
        castSpell(3, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Cobbled Wings");
        activateAbility(3, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {1}", "Silvercoat Lion"); // give Flying
        activateAbility(3, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Equip {2}", "Silvercoat Lion"); // lose Flying

        setStopAt(3, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        Permanent silvercoatLion = getPermanent("Silvercoat Lion", playerA.getId());
        Assert.assertNotNull(silvercoatLion);
        Assert.assertEquals("Silvercoat Lion equipments", 2, silvercoatLion.getAttachments().size());
        Assert.assertEquals("Silvercoat Lion power",4, silvercoatLion.getPower().getValue());
        Assert.assertEquals("Silvercoat Lion toughness",6, silvercoatLion.getToughness().getValue());

        // should NOT have flying
        Assert.assertFalse("Silvercoat Lion has flying but shouldn't have",silvercoatLion.getAbilities().contains(FlyingAbility.getInstance()));
    }

}
