package org.mage.test.ai.bugs;

import junit.framework.Assert;
import mage.Constants;
import mage.game.permanent.Permanent;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestBase;

public class BugCantFindCardInLibrary extends CardTestBase {

    @Test
    public void testWithSquadronHawk() {
        addCard(Constants.Zone.HAND, playerA, "Squadron Hawk");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains");

        execute();
        Permanent merfolk = getPermanent("Squadron Hawk", playerA.getId());
        Assert.assertNotNull(merfolk);
    }
}
