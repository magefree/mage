package org.mage.test.cards.abilities.enters;

import junit.framework.Assert;
import mage.Constants;
import mage.game.permanent.Permanent;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class ProteanHydraTest extends CardTestPlayerBase {

    @Test
    public void testEnteringWithCounters() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Constants.Zone.HAND, playerA, "Protean Hydra");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Protean Hydra");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);

        for (Permanent permanent : currentGame.getBattlefield().getAllPermanents()) {
            if (permanent.getName().equals("Forest")) {
                // check all mana was spent
                Assert.assertTrue(permanent.isTapped());
            }
        }

        assertPermanentCount(playerA, "Protean Hydra", 1);

        Permanent proteanHydra = getPermanent("Protean Hydra", playerA.getId());
        Assert.assertEquals(4, proteanHydra.getPower().getValue());
        Assert.assertEquals(4, proteanHydra.getToughness().getValue());
    }
}
