package org.mage.test.cards.single.ncc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 * {@link mage.cards.p.PhabineBosssConfidant Phabine, Boss's Confidant}
 * {3}{R}{G}{W}
 * Legendary Creature — Cat Advisor
 * Creature tokens you control have haste.
 * Parley — At the beginning of combat on your turn, each player reveals the top card of their library.
 *          For each land card revealed this way, you create a 1/1 green and white Citizen creature token.
 *          Then creatures you control get +1/+1 until end of turn for each nonland card revealed this way.
 *          Then each player draws a card.
 */
public class PhabineBosssConfidantTest extends CardTestCommander4Players {
    String phabineBosssConfidant = "Phabine, Boss's Confidant";

    /**
     * Reported bug: https://github.com/magefree/mage/issues/9603
     *      The "creatures you control get +1/+1 until end of turn" part of Phabine's trigger never works.
     */
    @Test
    public void boostWorks() {
        addCard(Zone.BATTLEFIELD, playerA, phabineBosssConfidant);
        // Creature to trigger the +1/+1 part of the effect
        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion");

        skipInitShuffling();
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Citizen Token", 3);
        assertPowerToughness(playerA, "Citizen Token", 2, 2);
    }
}
