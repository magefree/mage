package org.mage.test.deck;

import mage.cards.Card;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.deck.Alchemy;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test suite for the Alchemy Format implementation.
 *
 * Tests verify:
 * 1. Alchemy set is properly recognized
 * 2. Alchemy format is available
 * 3. Automatic card replacement works (paper -> Alchemy versions)
 * 4. A-Luminarch Aspirant is properly registered
 *
 * @author Vernon
 */
public class AlchemyFormatTest {

    /**
     * Test: Alchemy format exists and is instantiable
     */
    @Test
    public void testAlchemyFormatExists() {
        Alchemy alchemy = new Alchemy();
        assertNotNull(alchemy);
        assertEquals("Constructed - Alchemy", alchemy.getName());
    }

    /**
     * Test: Alchemy set code is included in the format
     */
    @Test
    public void testAlchemySetCodeIncluded() {
        Alchemy alchemy = new Alchemy();
        // Check that ALC (Alchemy) set code is included
        assertTrue("ALC set code should be in Alchemy format", alchemy.isSetAllowed("ALC"));
    }

    /**
     * Test: A-Luminarch Aspirant card is registered in ALC set
     */
    @Test
    public void testALuminarchAspirantExists() {
        CardInfo cardInfo = CardRepository.instance.findCard("A-Luminarch Aspirant", "ALC");
        assertNotNull("A-Luminarch Aspirant should exist in ALC set", cardInfo);

        try {
            Card card = cardInfo.getCard();
            assertEquals("A-Luminarch Aspirant", card.getName());
            assertEquals("ALC", card.getExpansionSetCode());
        } catch (Exception e) {
            fail("Should be able to instantiate A-Luminarch Aspirant: " + e.getMessage());
        }
    }

    /**
     * Test: Alchemy rebalance mapping includes Luminarch Aspirant
     */
    @Test
    public void testAlchemyRebalanceMappingExists() {
        assertTrue("Luminarch Aspirant should have Alchemy rebalance",
                   Alchemy.hasAlchemyRebalance("Luminarch Aspirant"));

        String alchemyVersion = Alchemy.getAlchemyCardName("Luminarch Aspirant");
        assertEquals("A-Luminarch Aspirant", alchemyVersion);
    }

    /**
     * Test: Non-rebalanced cards are returned unchanged
     */
    @Test
    public void testNonRebalancedCardUnchanged() {
        String cardName = "Grizzly Bears";
        assertFalse("Grizzly Bears should not have Alchemy rebalance",
                    Alchemy.hasAlchemyRebalance(cardName));

        String result = Alchemy.getAlchemyCardName(cardName);
        assertEquals("Non-rebalanced card name should be unchanged", cardName, result);
    }

    /**
     * Test: Get all rebalanced card names
     */
    @Test
    public void testGetRebalancedCardNames() {
        var rebalanced = Alchemy.getRebalancedCardNames();
        assertNotNull("Rebalanced card names set should not be null", rebalanced);
        assertTrue("Should contain at least Luminarch Aspirant",
                   rebalanced.contains("Luminarch Aspirant"));
    }

    /**
     * Test: Alchemy card has correct type
     */
    @Test
    public void testALuminarchAspirantType() {
        CardInfo cardInfo = CardRepository.instance.findCard("A-Luminarch Aspirant", "ALC");
        assertNotNull(cardInfo);

        try {
            Card card = cardInfo.getCard();
            assertTrue("Card should be a creature", card.isCreature());
            assertEquals("Card should cost {1}{W}", "{1}{W}", card.getManaCost().toString());
        } catch (Exception e) {
            fail("Error checking card properties: " + e.getMessage());
        }
    }
}

