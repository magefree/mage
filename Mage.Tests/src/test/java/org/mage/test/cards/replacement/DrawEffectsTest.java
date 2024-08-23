package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2, xenohedron
 */
public class DrawEffectsTest extends CardTestPlayerBase {

    private static final String drawOne = "Radical Idea"; // 1U instant
    private static final String drawTwo = "Quick Study"; // 2U instant
    private static final String drawThree = "Jace's Ingenuity"; // 3UU instant

    private static final String reflection = "Thought Reflection";
    // If you would draw a card, draw two cards instead.
    private static final String scrivener = "Blood Scrivener";
    // If you would draw a card while you have no cards in hand, instead you draw two cards and you lose 1 life.
    private static final String notionThief = "Notion Thief";
    // If an opponent would draw a card except the first one they draw in each of their draw steps,
    // instead that player skips that draw and you draw a card.
    private static final String asmodeus = "Asmodeus the Archfiend";
    // If you would draw a card, exile the top card of your library face down instead.
    private static final String almsCollector = "Alms Collector";
    // If an opponent would draw two or more cards, instead you and that player each draw a card.

    private void testBase(String cardDraw, int handPlayerA, int handPlayerB) {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);
        addCard(Zone.HAND, playerA, cardDraw);
        addCard(Zone.HAND, playerB, cardDraw);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cardDraw);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, cardDraw);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, cardDraw, 1);
        assertGraveyardCount(playerB, cardDraw, 1);
        assertHandCount(playerA, handPlayerA);
        assertHandCount(playerB, handPlayerB);
    }

    private void testSingle(String cardDraw, int handPlayerA, int handPlayerB, String... choices) {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, cardDraw);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cardDraw);
        for (String choice : choices) {
            setChoice(playerA, choice);
        }

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, cardDraw, 1);
        assertHandCount(playerA, handPlayerA);
        assertHandCount(playerB, handPlayerB);
    }

    @Test
    public void testReflection1() {
        addCard(Zone.BATTLEFIELD, playerA, reflection);
        testBase(drawOne, 2, 1);
    }

    @Test
    public void testReflection2() {
        addCard(Zone.BATTLEFIELD, playerA, reflection);
        testBase(drawTwo, 4, 2);
    }

    @Test
    public void testReflection3() {
        addCard(Zone.BATTLEFIELD, playerA, reflection);
        testBase(drawThree, 6, 3);
    }

    @Test
    public void testScrivener1() {
        addCard(Zone.BATTLEFIELD, playerA, scrivener);
        testBase(drawOne, 2, 1);
        assertLife(playerA, 19);
    }

    @Test
    public void testScrivener2() {
        addCard(Zone.BATTLEFIELD, playerA, scrivener);
        testBase(drawTwo, 3, 2);
        assertLife(playerA, 19);
    }

    @Test
    public void testScrivener3() {
        addCard(Zone.BATTLEFIELD, playerA, scrivener);
        testBase(drawThree, 4, 3);
        assertLife(playerA, 19);
    }

    /*
     * Each additional Blood Scrivener you control will effectively add one card and 1 life lost.
     * Say you control two Blood Scriveners and would draw a card while you have no cards in hand.
     * The effect of one Blood Scrivener will replace the event “draw a card” with “draw two cards and lose 1 life.”
     * The effect of the other Blood Scrivener will replace the drawing of the first of those two cards with
     * “draw two cards and lose 1 life.” You’ll draw two cards and lose 1 life,
     * then draw another card and lose another 1 life. (2013-04-15)
     */

    @Test
    public void testDoubleScrivener1() {
        addCard(Zone.BATTLEFIELD, playerA, scrivener, 2);
        testSingle(drawOne, 3, 0, scrivener);
        assertLife(playerA, 18);
    }

    @Test
    public void testDoubleScrivener2() {
        addCard(Zone.BATTLEFIELD, playerA, scrivener, 2);
        testSingle(drawTwo, 4, 0, scrivener);
        assertLife(playerA, 18);
    }

    @Test
    public void testDoubleScrivener3() {
        addCard(Zone.BATTLEFIELD, playerA, scrivener, 2);
        testSingle(drawThree, 5, 0, scrivener);
        assertLife(playerA, 18);
    }

    @Test
    public void testAsmodeus1() {
        addCard(Zone.BATTLEFIELD, playerA, asmodeus);
        testBase(drawOne, 0, 1);
        assertExileCount(playerA, 1);
    }

    @Test
    public void testAsmodeus2() {
        addCard(Zone.BATTLEFIELD, playerA, asmodeus);
        testBase(drawTwo, 0, 2);
        assertExileCount(playerA, 2);
    }

    @Test
    public void testAsmodeus3() {
        addCard(Zone.BATTLEFIELD, playerA, asmodeus);
        testBase(drawThree, 0, 3);
        assertExileCount(playerA, 3);
    }

    @Test
    public void testReflectionAsmodeus1() {
        addCard(Zone.BATTLEFIELD, playerA, asmodeus);
        addCard(Zone.BATTLEFIELD, playerA, reflection);
        testSingle(drawOne, 0, 0, reflection);
        assertExileCount(playerA, 2);
    }

    @Test
    public void testReflectionAsmodeus2() {
        addCard(Zone.BATTLEFIELD, playerA, asmodeus);
        addCard(Zone.BATTLEFIELD, playerA, reflection);
        testSingle(drawTwo, 0, 0, reflection, reflection);
        assertExileCount(playerA, 4);
    }

    @Test
    public void testReflectionAsmodeus3() {
        addCard(Zone.BATTLEFIELD, playerA, asmodeus);
        addCard(Zone.BATTLEFIELD, playerA, reflection);
        testSingle(drawThree, 0, 0, reflection, reflection, reflection);
        assertExileCount(playerA, 6);
    }

    @Test
    public void testAsmodeusReflection1() {
        addCard(Zone.BATTLEFIELD, playerA, asmodeus);
        addCard(Zone.BATTLEFIELD, playerA, reflection);
        testSingle(drawOne, 0, 0, asmodeus);
        assertExileCount(playerA, 1);
    }

    @Test
    public void testAsmodeusReflection2() {
        addCard(Zone.BATTLEFIELD, playerA, asmodeus);
        addCard(Zone.BATTLEFIELD, playerA, reflection);
        testSingle(drawTwo, 0, 0, asmodeus, asmodeus);
        assertExileCount(playerA, 2);
    }

    @Test
    public void testAsmodeusReflection3() {
        addCard(Zone.BATTLEFIELD, playerA, asmodeus);
        addCard(Zone.BATTLEFIELD, playerA, reflection);
        testSingle(drawThree, 0, 0, asmodeus, asmodeus, asmodeus);
        assertExileCount(playerA, 3);
    }

    @Test
    public void testAlmsCollectorReflection1() {
        addCard(Zone.BATTLEFIELD, playerB, almsCollector);
        addCard(Zone.BATTLEFIELD, playerA, reflection);
        testSingle(drawOne, 1, 1);
    }

    @Test
    public void testAlmsCollectorReflection2() {
        addCard(Zone.BATTLEFIELD, playerB, almsCollector);
        addCard(Zone.BATTLEFIELD, playerA, reflection);
        testSingle(drawTwo, 2, 1);
    }

    @Test
    public void testAlmsCollectorReflection3() {
        addCard(Zone.BATTLEFIELD, playerB, almsCollector);
        addCard(Zone.BATTLEFIELD, playerA, reflection);
        testSingle(drawThree, 2, 1);
    }

    @Test
    public void testNotionThiefReflection1() {
        addCard(Zone.BATTLEFIELD, playerB, notionThief);
        addCard(Zone.BATTLEFIELD, playerA, reflection);
        testSingle(drawOne, 0, 1, notionThief);
    }

    @Test
    public void testNotionThiefReflection2() {
        addCard(Zone.BATTLEFIELD, playerB, notionThief);
        addCard(Zone.BATTLEFIELD, playerA, reflection);
        testSingle(drawTwo, 0, 2, notionThief, notionThief);
    }

    @Test
    public void testNotionThiefReflection3() {
        addCard(Zone.BATTLEFIELD, playerB, notionThief);
        addCard(Zone.BATTLEFIELD, playerA, reflection);
        testSingle(drawThree, 0, 3, notionThief, notionThief, notionThief);
    }

    @Test
    public void testReflectionNotionThief1() {
        addCard(Zone.BATTLEFIELD, playerB, notionThief);
        addCard(Zone.BATTLEFIELD, playerA, reflection);
        testSingle(drawOne, 0, 2, reflection);
    }


    /**
     * The effects of multiple Thought Reflections are cumulative. For example,
     * if you have three Thought Reflections on the battlefield, you'll draw
     * eight times the original number of cards.
     */
    @Test
    public void testCard() {
        // If you would draw a card, draw two cards instead.
        addCard(Zone.BATTLEFIELD, playerB, "Thought Reflection", 3);

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        Assert.assertEquals("Player B has to have 8 cards in hand", 8, playerB.getHand().size());

    }

    /**
     * http://www.slightlymagic.net/forum/viewtopic.php?f=70&t=17295&start=75#p181427
     * If I have a Notion Thief on the battlefield and cast Opportunity,
     * targeting my opponent, during my opponent's upkeep, the opponent
     * incorrectly draws the cards.
     */
    @Test
    public void testNotionThief() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        // Flash
        // If an opponent would draw a card except the first one they draw in each of their draw steps, instead that player skips that draw and you draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Notion Thief", 1);

        // Target player draws four cards.
        addCard(Zone.HAND, playerA, "Opportunity", 1);

        castSpell(2, PhaseStep.UPKEEP, playerA, "Opportunity", playerB);

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Opportunity", 1);
        assertHandCount(playerA, 4);
        assertHandCount(playerB, 1);
    }

    /**
     * Notion thief and Reforge the Soul - opponent got 0 cards - ok but I got
     * only 7 cards (should be 14)
     */
    @Test
    public void testNotionThief2() {
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion", 3);
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        // Flash
        // If an opponent would draw a card except the first one they draw in each of their draw steps, instead that player skips that draw and you draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Notion Thief", 1);
        // Each player discards their hand, then draws seven cards.
        // Miracle {1}{R}
        addCard(Zone.HAND, playerA, "Reforge the Soul", 1);

        addCard(Zone.HAND, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reforge the Soul");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Reforge the Soul", 1);
        assertGraveyardCount(playerB, "Mountain", 1);
        assertHandCount(playerA, 14);
        assertHandCount(playerB, 0);
    }

    @Test
    public void WordsOfWilding() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        // {1}: The next time you would draw a card this turn, create a 2/2 green Bear creature token instead.
        addCard(Zone.BATTLEFIELD, playerA, "Words of Wilding", 1);

        // Draw two cards.
        addCard(Zone.HAND, playerA, "Counsel of the Soratami", 1); // Sorcery {2}{U}

        activateAbility(1, PhaseStep.UPKEEP, playerA, "{1}");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Counsel of the Soratami");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Counsel of the Soratami", 1);
        assertPermanentCount(playerA, "Bear Token", 1);
        assertHandCount(playerA, 1);
    }

    @Test
    public void testAlmsCollector() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Alms Collector");
        // If an opponent would draw two or more cards, instead you and that player each draw a card.

        // Draw two cards.
        addCard(Zone.HAND, playerA, "Counsel of the Soratami", 1); // Sorcery {2}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Counsel of the Soratami");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Counsel of the Soratami", 1);
        assertHandCount(playerA, 1);
        assertHandCount(playerB, 1);
    }

}
