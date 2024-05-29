package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class RipplesOfUndeathTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.r.RipplesOfUndeath Ripples of Undeath} {1}{B}
     * Enchantment
     * At the beginning of your precombat main phase, mill three cards. Then you may pay {1} and 3 life. If you do, put a card from among those cards into your hand.
     */
    private static final String ripples = "Ripples of Undeath";

    @Test
    public void test_CantPay() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, ripples);
        addCard(Zone.LIBRARY, playerA, "Taiga", 3);

        setChoice(playerA, false); // no to "you may pay"

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Taiga", 3);
    }

    @Test
    public void test_Pay() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, ripples);
        addCard(Zone.LIBRARY, playerA, "Taiga", 2);
        addCard(Zone.LIBRARY, playerA, "Savannah", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains");

        setChoice(playerA, true); // yes to "you may pay"
        setChoice(playerA, "Savannah"); // return this one

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Taiga", 2);
        assertHandCount(playerA, "Savannah", 1);
        assertTappedCount("Plains", true, 1);
        assertLife(playerA, 20 - 3);
    }
}
