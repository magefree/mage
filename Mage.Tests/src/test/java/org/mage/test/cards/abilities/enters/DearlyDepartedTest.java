package org.mage.test.cards.abilities.enters;

import junit.framework.Assert;
import mage.constants.PhaseStep;
import mage.constants.Zone;
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
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.GRAVEYARD, playerA, "Dearly Departed");
        addCard(Zone.BATTLEFIELD, playerA, "Thraben Doomsayer");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Put a 1/1 white Human creature token onto the battlefield.");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
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
