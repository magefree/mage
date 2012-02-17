package org.mage.test.cards.conditional;

import junit.framework.Assert;
import mage.Constants;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.game.permanent.Permanent;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestBase;

/**
 * @author ayrat
 */
public class SejiriMerfolkTest extends CardTestBase {

    @Test
    public void testWithoutPlains() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Sejiri Merfolk");
        setStopAt(1, Constants.PhaseStep.DRAW);
        execute();
        Permanent merfolk = getPermanent("Sejiri Merfolk", playerA.getId());
        Assert.assertNotNull(merfolk);
        Assert.assertFalse(merfolk.getAbilities().contains(FirstStrikeAbility.getInstance()));
        Assert.assertFalse(merfolk.getAbilities().contains(LifelinkAbility.getInstance()));
    }

    @Test
    public void testWithPlains() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Sejiri Merfolk");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains");
        setStopAt(1, Constants.PhaseStep.DRAW);
        execute();
        Permanent merfolk = getPermanent("Sejiri Merfolk", playerA.getId());
        Assert.assertNotNull(merfolk);
        Assert.assertTrue(merfolk.getAbilities().contains(FirstStrikeAbility.getInstance()));
        Assert.assertTrue(merfolk.getAbilities().contains(LifelinkAbility.getInstance()));
    }
}
