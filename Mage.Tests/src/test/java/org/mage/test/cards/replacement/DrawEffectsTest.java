
package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class DrawEffectsTest extends CardTestPlayerBase {

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
}
