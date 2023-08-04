package org.mage.test.utils;

import mage.cards.Card;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.cards.repository.CardScanner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

/**
 * Testing of that all the cards in CardRepository can be copied.
 *
 * @author Suscr
 */
public class AllCardsCanBeCopiedTest {

    @Before
    public void setUp() {
        CardScanner.scan();
    }

    /**
     * Test all the unique cards in the repository, and ensure they can be copied.
     * This detects copy loops that would cause StackOverflowException on calling copy().
     */
    @Test
    public void test_AllCardsCanBeCopied() {

        Set<String> allCards = CardRepository.instance.getNames();

        int good = 0;
        int bad = 0;
        int unknown = 0;

        //System.out.println("Starting copy test for " + allCards.size() + " unique cards");
        for (String name : allCards) {
            CardInfo info = CardRepository.instance.findCard(name);
            if (info == null) {
                unknown += 1;
                continue;
            }

            Card card = info.getCard();
            if (card == null) {
                unknown += 1;
                continue;
            }

            //System.out.print("copying " + name + " ...");
            try {
                Card card2 = card.copy();
                good += 1;
                //System.out.println("done");
            } catch (StackOverflowError err) {
                bad += 1;
                //System.out.println();
                System.err.println(name + " cause an infinite loop on copy.");

                // Stopping there, there are enough bad cards detected.
                if (bad >= 50) {
                    System.out.println("End of copy test.");
                    System.out.println("           Total: " + allCards.size());
                    System.out.println("            Good: " + good);
                    System.out.println("             Bad: " + bad);
                    System.out.println("         Unknown: " + unknown);
                    System.out.println("      Not Tested: " + (allCards.size() - good - bad - unknown));
                    Assert.fail(bad + " cards at least are causing StackOverflow on copy");
                }
            }

        }

        System.out.println("End of copy test.");
        System.out.println("           Total: " + allCards.size());
        System.out.println("            Good: " + good);
        System.out.println("             Bad: " + bad);
        System.out.println("         Unknown: " + unknown);

        if (bad > 0) {
            Assert.fail(bad + " cards cause infinite loop on copy");
        }
        if (unknown > 0) {
            Assert.fail(unknown + " cards in repository were not actually Card");
        }
    }
}
