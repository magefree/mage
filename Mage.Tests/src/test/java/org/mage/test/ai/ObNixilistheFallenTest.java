package org.mage.test.ai;

import junit.framework.Assert;
import mage.Constants;
import mage.game.permanent.Permanent;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestBase;

/**
 * @author ayratn
 */
public class ObNixilistheFallenTest extends CardTestBase {

    /**
     * Reproduces bug when AI doesn't use good "may" ability.
     */
    @Test
    @Ignore
    public void testMayAbilityUsed() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Ob Nixilis, the Fallen");
        addCard(Constants.Zone.HAND, playerA, "Swamp", 1);

        playLand(playerA, "Swamp");
        execute();

        Permanent master = getPermanent("Ob Nixilis, the Fallen", playerA.getId());
        Assert.assertNotNull(master);
        Assert.assertEquals(6, master.getPower().getValue());
        Assert.assertEquals(6, master.getToughness().getValue());

        assertLife(playerA, 20);
        assertLife(playerB, 11); // lose 3 life + attack with 6\6
    }
}
