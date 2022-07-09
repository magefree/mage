package org.mage.test.decks.importer;

import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.cards.decks.importer.TxtDeckImporter;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import org.junit.Assert;
import org.junit.Test;

public class TxtDeckImporterTest {

    @Test
    public void testImportWithBlankLineAboveSideboard() {
        TxtDeckImporter importer = new TxtDeckImporter(false);

        CardInfo card;
        DeckCardLists deck = new DeckCardLists();

        String[] cards = {"Plains", "Forest", "Island"};
        String[] sideboard = {"Swamp", "Mountain"};

        for (String c : cards) {
            card = CardRepository.instance.findPreferredCoreExpansionCard(c);
            assert card != null;
            deck.getCards().add(new DeckCardInfo(card.getName(), card.getCardNumber(), card.getSetCode()));
        }

        for (String s : sideboard) {
            card = CardRepository.instance.findPreferredCoreExpansionCard(s);
            assert card != null;
            deck.getSideboard().add(new DeckCardInfo(card.getName(), card.getCardNumber(), card.getSetCode()));
        }

        Assert.assertEquals("Deck does not contain 3 cards, found " + deck.getCards().size(), 3, deck.getCards().size());
        Assert.assertEquals("Sideboard does not contain 2 cards, found " + deck.getSideboard().size(), 2, deck.getSideboard().size());

        DeckCardLists imported = importer.importDeck("JustLands.txt", false);

        Assert.assertEquals("Imported deck does not contain 3 cards, found " + imported.getCards().size(), 3, imported.getCards().size());
        Assert.assertEquals("Imported sideboard does not contain 2 cards, found " + imported.getSideboard().size(), 2, imported.getSideboard().size());
    }
}
