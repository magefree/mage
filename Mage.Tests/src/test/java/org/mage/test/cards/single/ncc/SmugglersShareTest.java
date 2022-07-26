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

    /*
        Plains
        Basic Land - Plains

        ({T}: Add {W}.)
    */
    String plains = "Plains";

    /*
        Treasure
        Token Artifact - Treasure

        {T}, Sacrifice this artifact: Add one mana of any color.
    */
    String treasureToken = "Treasure Token";

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
        assertPermanentCount(playerB, treasureToken, 1);
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
        assertPermanentCount(playerB, treasureToken, 1);

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
        addCard(Zone.LIBRARY, playerA, forest, 6);

        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerB, island, 2);

        removeAllCardsFromLibrary(playerC);
        addCard(Zone.LIBRARY, playerC, forest, 2);

        addCard(Zone.BATTLEFIELD, playerA, forest, 6);
        addCard(Zone.BATTLEFIELD, playerB, island, 4);
        addCard(Zone.BATTLEFIELD, playerC, forest, 4);
        addCard(Zone.BATTLEFIELD, playerD, smugglersShare, 1);

        addCard(Zone.HAND, playerA, cultivate, 1);
        addCard(Zone.HAND, playerA, harmonize, 1);
        addCard(Zone.HAND, playerB, chemistersInsight, 1);
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
        assertPermanentCount(playerD, treasureToken, 2);

        // More than 2 cards were drawn by 2 opponents, draw 2 cards.
        assertHandCount(playerD, 2);
    }

    /**
     * Test with two players:
     * A with Smuggler's Share
     * B with Harrow and Chemister's Insight
     *
     * B plays Harrow and Chemister's Insight, A plays Smuggler's Share, A draws 1 card and creates 1 Treasure token
     */
    @Test
    public void testDrawAndTreasureEarly() {
        assertHandCount(playerA, 0);


        addCard(Zone.BATTLEFIELD, playerB, forest, 4);
        addCard(Zone.BATTLEFIELD, playerB, island, 4);

        addCard(Zone.BATTLEFIELD, playerA, plains, 3);

        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerB, forest, 5);

        addCard(Zone.HAND, playerB, harrow, 1);
        addCard(Zone.HAND, playerB, chemistersInsight, 1);

        addCard(Zone.HAND, playerA, smugglersShare, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, harrow);
        setChoice(playerB, forest);
        addTarget(playerB, forest + "^" + forest);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, chemistersInsight);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, smugglersShare);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        // 1 card drawn for turn
        assertHandCount(playerA, 1);
        // No treasure tokens created yet
        assertPermanentCount(playerA, treasureToken, 0);

        setStopAt(1, PhaseStep.CLEANUP);
        execute();
        assertAllCommandsUsed();

        // 2 lands entered the battlefield under opponent's control, create Treasure token.
        assertPermanentCount(playerA, treasureToken, 1);

        // More than 2 cards were drawn by an opponent, draw a card.
        assertHandCount(playerA, 2);
    }
}
