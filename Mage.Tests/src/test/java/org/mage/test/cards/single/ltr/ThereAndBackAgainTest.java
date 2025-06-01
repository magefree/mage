package org.mage.test.cards.single.ltr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class ThereAndBackAgainTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.ThereAndBackAgain There and Back Again} {3}{R}{R}
     * Enchantment — Saga
     * I — Up to one target creature can’t block for as long as you control this Saga. The Ring tempts you.
     * II — Search your library for a Mountain card, put it onto the battlefield, then shuffle.
     * III — Create Smaug, a legendary 6/6 red Dragon creature token with flying, haste, and “When Smaug dies, create fourteen Treasure tokens.”
     */
    private static final String again = "There and Back Again";

    @Test
    public void test_SimplePlay() {
        addCard(Zone.HAND, playerA, again, 1);

        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Gaea's Protector"); // 4/2 Must be blocked if able
        addCard(Zone.BATTLEFIELD, playerB, "Amaranthine Wall"); // 0/6

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, again);
        addTarget(playerA, "Amaranthine Wall");

        attack(1, playerA, "Gaea's Protector");
        // Wall can't block.

        // turn 3
        addTarget(playerA, "Mountain");
        attack(3, playerA, "Gaea's Protector");
        // Wall can't block.

        // turn 5
        attack(5, playerA, "Gaea's Protector");
        // Wall must block

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 4 * 2);
        assertDamageReceived(playerB, "Amaranthine Wall", 4);
        assertPermanentCount(playerA, "Smaug", 1);
    }
}
