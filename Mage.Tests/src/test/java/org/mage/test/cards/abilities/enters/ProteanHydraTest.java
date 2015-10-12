package org.mage.test.cards.abilities.enters;

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
public class ProteanHydraTest extends CardTestPlayerBase {

    @Test
    public void testEnteringWithCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        // Protean Hydra enters the battlefield with X +1/+1 counters on it.
        // If damage would be dealt to Protean Hydra, prevent that damage and remove that many +1/+1 counters from it.
        // Whenever a +1/+1 counter is removed from Protean Hydra, put two +1/+1 counters on it at the beginning of the next end step.
        addCard(Zone.HAND, playerA, "Protean Hydra"); // CREATURE - {X}{G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Protean Hydra");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
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
