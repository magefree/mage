
package org.mage.test.cards.modal;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SameModeMoreThanOnceTest extends CardTestPlayerBase {

    @Test
    public void testEachModeOnce() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        // Choose three. You may choose the same mode more than once.
        // - Target player draws a card and loses 1 life;
        // - Target creature gets -2/-2 until end of turn;
        // - Return target creature card from your graveyard to your hand.
        addCard(Zone.HAND, playerA, "Wretched Confluence"); // Instant {3}{B}{B}

        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wretched Confluence", "mode=1targetPlayer=PlayerA^mode=2Pillarfield Ox^mode=3Silvercoat Lion");
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "2");
        setModeChoice(playerA, "3");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Wretched Confluence", 1);
        assertLife(playerA, 19);
        assertLife(playerB, 20);
        assertHandCount(playerA, 2);
        assertPowerToughness(playerB, "Pillarfield Ox", 0, 2);
        assertGraveyardCount(playerA, "Silvercoat Lion", 0);

    }

    @Test
    public void testSecondModeTwiceThridModeOnce() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        // Choose three. You may choose the same mode more than once.
        // - Target player draws a card and loses 1 life;
        // - Target creature gets -2/-2 until end of turn;
        // - Return target creature card from your graveyard to your hand.
        addCard(Zone.HAND, playerA, "Wretched Confluence"); // Instant {3}{B}{B}

        addCard(Zone.BATTLEFIELD, playerB, "Wall of Air");
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox");
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wretched Confluence", "mode=1Pillarfield Ox^mode=2Wall of Air^mode=3Silvercoat Lion");
        setModeChoice(playerA, "2");
        setModeChoice(playerA, "2");
        setModeChoice(playerA, "3");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Wretched Confluence", 1);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPowerToughness(playerB, "Wall of Air", -1, 3);
        assertPowerToughness(playerB, "Pillarfield Ox", 0, 2);
        assertGraveyardCount(playerA, "Silvercoat Lion", 0);

    }
}
