package org.mage.test.cards.abilities.enters;

import junit.framework.Assert;
import mage.Constants;
import mage.game.permanent.Permanent;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class DearlyDepartedTest extends CardTestPlayerBase {

    @Test
    public void testEnteringWithCounters() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Constants.Zone.GRAVEYARD, playerA, "Dearly Departed");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Thraben Doomsayer");

        activateAbility(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Put a 1/1 white Human creature token onto the battlefield.");

        setStopAt(2, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Human", 1);

        // check that the +1/+1 counter was added to the token
        Permanent humanToken = getPermanent("Human", playerA.getId());
        Assert.assertEquals(2, humanToken.getPower().getValue());
        Assert.assertEquals(2, humanToken.getToughness().getValue());
    }
}
