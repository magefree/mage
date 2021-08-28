package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author klayhamn
 */
public class RippleTest extends CardTestPlayerBase {

    /**
     * 702.59.Ripple
     * 702.59a Ripple is a triggered ability that functions only while the card with ripple is on the stack. “Ripple N” means
     * “When you cast this spell, you may reveal the top N cards of your library, or, if there are fewer than N cards in your
     * library, you may reveal all the cards in your library. If you reveal cards from your library this way, you may cast any
     * of those cards with the same name as this spell without paying their mana costs, then put all revealed cards not cast
     * this way on the bottom of your library in any order.”
     * 702.59b If a spell has multiple instances of ripple, each triggers separately.
     */

    @Test
    public void testRippleWhenSameCardNotFound() {

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.HAND, playerA, "Surging Dementia",2 );
        addCard(Zone.LIBRARY, playerA, "Swamp", 4);


        addCard(Zone.HAND, playerB, "Island", 3);
        addCard(Zone.LIBRARY, playerB, "Island", 3);


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Surging Dementia", playerB);
        setChoice(playerA, true);

        setStopAt(2, PhaseStep.END_TURN);

        execute();

        assertHandCount(playerB, 3); // should have 1 less
        assertHandCount(playerA, 1); // after cast, one remains

        assertGraveyardCount(playerA, "Surging Dementia", 1); // 1 cast
        assertGraveyardCount(playerB, "Island", 1); // 1 discarded
    }

    @Test
    public void testRippleWhenSameCardFoundOnce() {

        removeAllCardsFromLibrary(playerA);

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.HAND, playerA, "Surging Dementia",2 );
        addCard(Zone.LIBRARY, playerA, "Surging Dementia",1);
        addCard(Zone.LIBRARY, playerA, "Swamp", 3);


        addCard(Zone.HAND, playerB, "Island", 3);
        addCard(Zone.LIBRARY, playerB, "Island", 3);


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Surging Dementia", playerB);
        setChoice(playerA, true);
        addTarget(playerA, playerB);

        setStopAt(2, PhaseStep.END_TURN);

        execute();

        assertHandCount(playerB, 2); // should have 2 less
        assertHandCount(playerA, 1); // after cast, one remains in hand
        assertGraveyardCount(playerA, "Surging Dementia", 2); // 2 were cast
        assertGraveyardCount(playerB, "Island", 2); // 2 were discarded

    }

    @Test
    public void testRippleWhenSameCardFoundMoreThanOnce() {


        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.HAND, playerA, "Surging Dementia",2 );

        addCard(Zone.LIBRARY, playerA, "Surging Dementia",1);
        addCard(Zone.LIBRARY, playerA, "Swamp", 2);
        addCard(Zone.LIBRARY, playerA, "Surging Dementia",1);
        addCard(Zone.LIBRARY, playerA, "Swamp", 2);


        addCard(Zone.HAND, playerB, "Island", 3);
        addCard(Zone.LIBRARY, playerB, "Island", 3);


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Surging Dementia", playerB);
        setChoice(playerA, true);
        addTarget(playerA, playerB);

        setStopAt(2, PhaseStep.END_TURN);

        execute();

        assertHandCount(playerB, 1); // should have 3 less
        assertHandCount(playerA, 1); // after cast, one remains in hand
        assertGraveyardCount(playerA, "Surging Dementia", 3); // 3 were cast
        assertGraveyardCount(playerB, "Island", 3); // 3 were discarded
    }
}
