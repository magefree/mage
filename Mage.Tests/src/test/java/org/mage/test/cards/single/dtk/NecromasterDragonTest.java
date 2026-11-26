package org.mage.test.cards.single.dtk;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Necromaster Dragon
 * {3}{U}{B}
 * Creature - Dragon
 * Flying
 * Whenever Necromaster Dragon deals combat damage to a player, you may pay {2}. If you do, create a
 * 2/2 black Zombie creature token and each opponent mills two cards.
 * <p>
 * Both the token and the mill hang on the payment (CR 608.2c).
 */
public class NecromasterDragonTest extends CardTestPlayerBase {

    private static final String dragon = "Necromaster Dragon";

    @Test
    public void test_Declined_NoTokenNoMill() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, dragon);
        addCard(Zone.LIBRARY, playerB, "Plains", 2);

        attack(1, playerA, dragon);
        setChoice(playerA, false); // decline to pay {2}

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 4);
        assertPermanentCount(playerA, "Zombie Token", 0);
        assertGraveyardCount(playerB, 0);
    }

    @Test
    public void test_Paid_TokenAndMill() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, dragon);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.LIBRARY, playerB, "Plains", 2);

        attack(1, playerA, dragon);
        setChoice(playerA, true); // pay {2}

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 4);
        assertPermanentCount(playerA, "Zombie Token", 1);
        assertGraveyardCount(playerB, 2);
    }
}
