package org.mage.test.decks.importer;

import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.cards.decks.importer.TxtDeckImporter;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.cards.repository.CardScanner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TxtDeckImporterTest {

    @BeforeEach
    public void setUp() {
        CardScanner.scan();
    }

    @Test
    public void testImportWithBlankLineAboveSideboard() {
        TxtDeckImporter importer = new TxtDeckImporter(false);

        CardInfo card;
        DeckCardLists deck = new DeckCardLists();

        String[] cards = {"Plains", "Forest", "Island"};
        String[] sideboard = {"Swamp", "Mountain"};

        for (String c : cards) {
            card = CardRepository.instance.findPreferredCoreExpansionCard(c);
            Assertions.assertNotNull(card, String.format("Card %s was null", c));
            deck.getCards().add(new DeckCardInfo(card.getName(), card.getCardNumber(), card.getSetCode()));
        }

        for (String s : sideboard) {
            card = CardRepository.instance.findPreferredCoreExpansionCard(s);
            Assertions.assertNotNull(card, String.format("Card %s was null", s));
            deck.getSideboard().add(new DeckCardInfo(card.getName(), card.getCardNumber(), card.getSetCode()));
        }

        Assertions.assertEquals(3, deck.getCards().size(), "Deck does not contain 3 cards, found " + deck.getCards().size());
        Assertions.assertEquals(2, deck.getSideboard().size(), "Sideboard does not contain 2 cards, found " + deck.getSideboard().size());

        DeckCardLists imported = importer.importDeck("JustLands.txt", false);

        Assertions.assertEquals(3, imported.getCards().size(), "Imported deck does not contain 3 cards, found " + imported.getCards().size());
        Assertions.assertEquals(2, imported.getSideboard().size(), "Imported sideboard does not contain 2 cards, found " + imported.getSideboard().size());
    }
}
