package org.mage.test.cards.single.clb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class BattleAngelsOfTyrTest extends CardTestPlayerBase {

    /**
     * Battle Angels of Tyr {2}{W}{W}
     * Creature - Angel Knight 4/4
     * Flying
     * Myriad
     * Whenever Battle Angels of Tyr deals combat damage to a player, draw a card if that player has more cards
     * in hand than each other player. Then you create a Treasure token if that player controls more lands than
     * each other player. Then you gain 3 life if that player has more life than each other player.
     */
    private static final String angels = "Battle Angels of Tyr";

    @Test
    public void test_MoreLands_CreatesTreasure() {
        addCard(Zone.BATTLEFIELD, playerA, angels);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 7);

        attack(1, playerA, angels, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // each player has more than two lands, so the count must not stop at 2 (#9829)
        assertPermanentCount(playerA, "Treasure Token", 1);
        // hands are tied and the damaged player has less life, so no draw and no life gain
        assertHandCount(playerA, 0);
        assertLife(playerA, 20);
        assertLife(playerB, 20 - 4);
    }

    @Test
    public void test_TiedLands_NoTreasure() {
        addCard(Zone.BATTLEFIELD, playerA, angels);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 3);

        attack(1, playerA, angels, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Treasure Token", 0);
    }

    @Test
    public void test_MoreCardsAndLife_DrawsAndGainsLife() {
        addCard(Zone.BATTLEFIELD, playerA, angels);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.HAND, playerB, "Forest", 2);
        setLife(playerB, 30);

        attack(1, playerA, angels, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 1);
        assertLife(playerA, 20 + 3);
        assertLife(playerB, 30 - 4);
        assertPermanentCount(playerA, "Treasure Token", 0);
    }
}
