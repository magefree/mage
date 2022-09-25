package org.mage.test.cards.planes;

import mage.constants.PhaseStep;
import mage.constants.Planes;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class UndercityReachesTest extends CardTestPlayerBase {

    @Test
    public void test_CanTriggerByController() {
        removeAllCardsFromHand(playerA);

        // Whenever a creature deals combat damage to a player, its controller may a draw a card
        addPlane(playerA, Planes.PLANE_UNDERCITY_REACHES);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);

        attack(1, playerA, "Balduvian Bears");
        setChoice(playerA, true); // draw

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 2);
        assertHandCount(playerA, 1);
    }

    @Test
    public void test_CanTriggerByOther() {
        removeAllCardsFromHand(playerB);

        // Whenever a creature deals combat damage to a player, its controller may a draw a card
        addPlane(playerA, Planes.PLANE_UNDERCITY_REACHES);
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1);

        attack(2, playerB, "Balduvian Bears");
        setChoice(playerB, true); // draw

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 2);
        assertHandCount(playerB, 1 + 1); // +1 from turn draw, +1 from trigger
    }
}
