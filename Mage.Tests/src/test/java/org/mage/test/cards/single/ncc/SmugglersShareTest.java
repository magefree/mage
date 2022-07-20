package org.mage.test.cards.single.ncc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 *
 * @author Slanman3755
 */
public class SmugglersShareTest extends CardTestCommander4Players {
    /*
        Smuggler's Share {2}{W}
        Enchantment

        At the beginning of each end step, draw a card for each opponent who drew two or more cards this turn, then
        create a Treasure token for each opponent who had two or more lands enter the battlefield under their control
        this turn.
    */
    String smugglersShare = "Smuggler's Share";

    /*
        Cultivate {2}{G}
        Sorcery

        Search your library for up to two basic land cards, reveal those cards, put one onto the battlefield tapped and
        the other into your hand, then shuffle.
    */
    String cultivate = "Cultivate";

    /*
        Forest
        Basic Land - Forest

        ({T}: Add {G}.)
    */
    String forest = "Forest";

    /**
     * Test with two players:
     * A with Cultivate
     * B with Smuggler's Share
     *
     * A plays Cultivate, B gets 1 Treasure token
     */
    @Test
    public void testCreateTreasureToken() {
        removeAllCardsFromLibrary(playerA);

        addCard(Zone.BATTLEFIELD, playerB, smugglersShare, 1);
        addCard(Zone.BATTLEFIELD, playerA, forest, 3);
        addCard(Zone.LIBRARY, playerA, forest, 3);
        addCard(Zone.HAND, playerA, cultivate, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cultivate);
        addTarget(playerA, forest + "^" + forest);
        setChoice(playerA, forest);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, forest);

        setStopAt(1, PhaseStep.CLEANUP);
        execute();
        assertAllCommandsUsed();

        // 2 lands entered the battlefield under opponent's control, create Treasure token.
        assertPermanentCount(playerB, "Treasure Token", 1);
    }
}