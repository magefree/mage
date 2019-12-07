package org.mage.test.cards.abilities.oneshot.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Created by Alexsandr0x.
 */
public class CitadelOfPainTest extends CardTestPlayerBase {

    @Test
    public void testDamage() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // At the beginning of each player's end step, Citadel of Pain deals X damage to that
        // player, where X is the number of untapped lands they control.
        addCard(Zone.BATTLEFIELD, playerA, "Citadel of Pain");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 17);
    }
}
