package org.mage.test.cards.single.plc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.w.WildPair Wild Pair}
 * {4}{G}{G}
 * Enchantment
 * Whenever a creature enters the battlefield, if you cast it from your hand,
 * you may search your library for a creature card with the same total power and toughness,
 * put it onto the battlefield, then shuffle.
 *
 * @author TheElk801
 */
public class WildPairTest extends CardTestPlayerBase {

    private static final String pair = "Wild Pair";
    private static final String serpent = "Stonecoil Serpent"; // 0/0 with X +1/+1 counters
    private static final String shimmerer = "Adaptive Shimmerer"; // 0/0 with 3 +1/+1 counters
    private static final String crocodile = "Dross Crocodile"; // 5/1
    private static final String maro = "Maro"; // */* equal to cards in hand
    private static final String memnite = "Memnite";// 1/1

    @Test
    public void testSerpentShimmerer() {
        addCard(Zone.BATTLEFIELD, playerA, pair);
        addCard(Zone.HAND, playerA, serpent);
        addCard(Zone.LIBRARY, playerA, shimmerer);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, serpent);
        setChoice(playerA, "X=0");
        setChoice(playerA, "Yes");
        addTarget(playerA, shimmerer);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, serpent, 1);
        assertPermanentCount(playerA, shimmerer, 1);
    }

    @Test
    public void testShimmererCourser() {
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 5);
        addCard(Zone.BATTLEFIELD, playerA, pair);
        addCard(Zone.HAND, playerA, shimmerer);
        addCard(Zone.LIBRARY, playerA, crocodile);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shimmerer);
        setChoice(playerA, "Yes");
        addTarget(playerA, crocodile);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, shimmerer, 1);
        assertPermanentCount(playerA, crocodile, 1);
    }

    @Test
    public void testMemniteMaro() {
        addCard(Zone.BATTLEFIELD, playerA, pair);
        addCard(Zone.HAND, playerA, "Wastes");
        addCard(Zone.HAND, playerA, memnite);
        addCard(Zone.LIBRARY, playerA, maro);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, memnite);
        setChoice(playerA, "Yes");
        addTarget(playerA, maro);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, memnite, 1);
        assertPermanentCount(playerA, maro, 1);
    }
}
