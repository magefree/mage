package org.mage.test.cards.single.avr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.s.StolenGoods Stolen Goods}
 * {3}{U}
 * Sorcery
 * Target opponent exiles cards from the top of their library until they exile a nonland card.
 * Until end of turn, you may cast that card without paying its mana cost.
 *
 @author Alex-Vasile
 */
public class StolenGoodsTest extends CardTestPlayerBase {

    private static final String stolenGoods = "Stolen Goods";

    /**
     * Reported bug: https://github.com/magefree/mage/issues/9430
     *      "[[Stolen Goods]] will let you cast spells without paying their mana costs, but only if you have enough mana to cast them.
     *      In this example, I want to cast [[Kolvori, God of Kinship]], but can't because I have no green sources."
     */
    @Test
    public void castDualFaceCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, stolenGoods);

        addCard(Zone.LIBRARY, playerB, "Kolvori, God of Kinship");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, stolenGoods);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Kolvori, God of Kinship");
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
