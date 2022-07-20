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

    /*
        Harmonize {2}{G}{G}
        Sorcery

        Draw three cards.
    */
    String harmonize = "Harmonize";

    /*
        Harrow {2}{G}
        Instant

        As an additional cost to cast this spell, sacrifice a land.
        Search your library for up to two basic land cards, put them onto the battlefield, then shuffle.
    */
    String harrow = "Harrow";

    /*
        Chemister's Insight {3}{U}
        Instant

        Draw two cards.
        Jump-start
    */
    String chemistersInsight = "Chemister's Insight";

    /*
        Island
        Basic Land - Island

        ({T}: Add {U}.)
    */
    String island = "Island";

    /**
     * Test with two players:
     * A with Cultivate
     * B with Smuggler's Share
     *
     * A plays Cultivate, B gets 1 Treasure token
     */
    @Test
    public void testTreasure() {
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

    /**
     * Test with two players:
     * A with Harmonize
     * B with Smuggler's Share
     *
     * A plays Harmonize, B draws 1 card
     */
    @Test
    public void testDraw() {
        assertHandCount(playerB, 0);

        removeAllCardsFromLibrary(playerA);

        addCard(Zone.BATTLEFIELD, playerB, smugglersShare, 1);
        addCard(Zone.BATTLEFIELD, playerA, forest, 4);
        addCard(Zone.LIBRARY, playerA, forest, 4);
        addCard(Zone.HAND, playerA, harmonize, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, harmonize);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, forest);

        setStopAt(1, PhaseStep.CLEANUP);
        execute();
        assertAllCommandsUsed();

        // More than 2 cards were drawn by an opponent, draw a card.
        assertHandCount(playerB, 1);
    }

    /**
     * Test with two players:
     * A with Harmonize and Cultivate
     * B with Smuggler's Share
     *
     * A plays Harmonize, B draws 1 card
     */
    @Test
    public void testDrawAndTreasure() {
        assertHandCount(playerB, 0);

        removeAllCardsFromLibrary(playerA);

        addCard(Zone.BATTLEFIELD, playerA, forest, 6);
        addCard(Zone.BATTLEFIELD, playerB, smugglersShare, 1);

        addCard(Zone.LIBRARY, playerA, forest, 6);
        addCard(Zone.HAND, playerA, cultivate, 1);
        addCard(Zone.HAND, playerA, harmonize, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cultivate);
        addTarget(playerA, forest + "^" + forest);
        setChoice(playerA, forest);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, forest);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, harmonize);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStopAt(1, PhaseStep.CLEANUP);
        execute();
        assertAllCommandsUsed();

        // 2 lands entered the battlefield under opponent's control, create Treasure token.
        assertPermanentCount(playerB, "Treasure Token", 1);

        // More than 2 cards were drawn by an opponent, draw a card.
        assertHandCount(playerB, 1);
    }

    /**
     * Test with four players:
     * A with Harmonize and Cultivate
     * B with Chemister's Insight
     * C with Harrow
     * D with Smuggler's Share
     *
     * A plays Harmonize, B draws 1 card
     */
    @Test
    public void testMultipleOpponents() {
        assertHandCount(playerB, 0);

        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromLibrary(playerB);
        removeAllCardsFromLibrary(playerC);

        addCard(Zone.BATTLEFIELD, playerA, forest, 6);
        addCard(Zone.BATTLEFIELD, playerB, island, 4);
        addCard(Zone.BATTLEFIELD, playerC, forest, 4);
        addCard(Zone.BATTLEFIELD, playerD, smugglersShare, 1);

        addCard(Zone.LIBRARY, playerA, forest, 6);
        addCard(Zone.HAND, playerA, cultivate, 1);
        addCard(Zone.HAND, playerA, harmonize, 1);

        addCard(Zone.LIBRARY, playerB, island, 2);
        addCard(Zone.HAND, playerB, chemistersInsight, 1);

        addCard(Zone.LIBRARY, playerC, forest, 2);
        addCard(Zone.HAND, playerC, harrow, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cultivate);
        addTarget(playerA, forest + "^" + forest);
        setChoice(playerA, forest);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, forest);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, harmonize);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, chemistersInsight);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerC, harrow);
        setChoice(playerC, forest);
        addTarget(playerC, forest + "^" + forest);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStopAt(1, PhaseStep.CLEANUP);
        execute();
        assertAllCommandsUsed();

        // 2 lands entered the battlefield for 2 different opponents, create 2 Treasure tokens.
        assertPermanentCount(playerD, "Treasure Token", 2);
        // More than 2 cards were drawn by 2 opponents, draw 2 cards.
        assertHandCount(playerD, 2);
    }
}