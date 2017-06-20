package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Created by alexsandro on 14/03/17.
 */
public class MachinateTest extends CardTestPlayerBase {
    @Test
    public void checkDrawCount() {
        addCard(Zone.BATTLEFIELD, playerA, "Seat of the Synod", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Seat of the Synod", 2);

        // Any number just to have cards to draw
        addCard(Zone.LIBRARY, playerA, "Island", 10);

        addCard(Zone.HAND, playerA, "Machinate", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Machinate");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 1);
    }

    @Test
    public void checkNoDraw() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Seat of the Synod", 2);

        // Any number just to have cards to draw
        addCard(Zone.LIBRARY, playerA, "Island", 10);

        addCard(Zone.HAND, playerA, "Machinate", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Machinate");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 0);
    }
}
