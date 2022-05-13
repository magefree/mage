package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class BroodSliverTest extends CardTestPlayerBase {

    /*
    Reported bug: It lets the controller of Brood Sliver choose whether or not the token is created, instead of the attacking Sliver's controller.
     */
    @Test
    public void testTokenCreatedBySliverController() {

        // Brood Sliver {4}{G} 3/3 Sliver
        // Whenever a Sliver deals combat damage to a player, its controller may put a 1/1 colorless Sliver creature token onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerB, "Brood Sliver");
        addCard(Zone.BATTLEFIELD, playerA, "Venom Sliver"); // 1/1 deathtouch granting sliver

        attack(1, playerA, "Venom Sliver");
        setChoice(playerA, true); // controller of Venom Sliver dealing damage should get the choice to create token
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 19);
        assertPermanentCount(playerA, "Sliver Token", 1);
        assertPermanentCount(playerB, "Sliver Token", 0);
    }
}
