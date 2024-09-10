package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jeffwadsworth
 */
public class AbattoirGhoulTest extends CardTestPlayerBase {

    @Test
    public void testAbattoirGhoulEffect() {
        // Whenever a creature dealt damage by Abattoir Ghoul this turn dies, you gain life equal to that creature's toughness.
        addCard(Zone.BATTLEFIELD, playerA, "Abattoir Ghoul", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Shivan Dragon", 1);

        attack(1, playerA, "Abattoir Ghoul");
        block(1, playerB, "Memnite", "Abattoir Ghoul");
        block(1, playerB, "Shivan Dragon", "Abattoir Ghoul");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 21);
        assertLife(playerB, 20);

    }

}
