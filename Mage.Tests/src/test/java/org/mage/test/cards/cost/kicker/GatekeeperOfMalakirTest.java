package org.mage.test.cards.cost.kicker;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class GatekeeperOfMalakirTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Gatekeeper of Malakir");
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves");

        // Kicker {B} (You may pay an additional {B} as you cast this spell.)
        // When Gatekeeper of Malakir enters the battlefield, if it was kicked, target player sacrifices a creature.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gatekeeper of Malakir");
        setChoice(playerA, true); // use kicker

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Gatekeeper of Malakir", 1);
        assertPermanentCount(playerB, "Llanowar Elves", 0);
        assertGraveyardCount(playerB, 1);
    }
}
