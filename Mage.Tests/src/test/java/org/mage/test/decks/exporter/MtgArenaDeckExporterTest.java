package org.mage.test.decks.exporter;

import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.cards.decks.exporter.DeckExporter;
import mage.cards.decks.exporter.MtgArenaDeckExporter;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author JayDi85
 */
public class MtgArenaDeckExporterTest {

    @Test
    public void writeDeck() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DeckCardLists deck = new DeckCardLists();
        deck.getCards().add(new DeckCardInfo("Forest", "1", "RNA", 2));
        deck.getCards().add(new DeckCardInfo("Plains", "2", "RNA", 3));
        deck.getCards().add(new DeckCardInfo("Plains", "2", "RNA", 5)); // must combine
        deck.getCards().add(new DeckCardInfo("Mountain", "3", "RNA", 1));
        deck.getCards().add(new DeckCardInfo("Goblin Chainwhirler", "129", "DOM", 4));
        deck.getSideboard().add(new DeckCardInfo("Island", "1", "RNA", 2));
        deck.getSideboard().add(new DeckCardInfo("Island", "1", "RNA", 5)); // must combine
        deck.getSideboard().add(new DeckCardInfo("Mountain", "2", "RNA", 3));
        DeckExporter exporter = new MtgArenaDeckExporter();
        exporter.writeDeck(baos, deck);
        assertEquals("2 Forest (RNA) 1" + System.lineSeparator() +
                        "8 Plains (RNA) 2" + System.lineSeparator() +
                        "1 Mountain (RNA) 3" + System.lineSeparator() +
                        "4 Goblin Chainwhirler (DAR) 129" + System.lineSeparator() +
                        System.lineSeparator() +
                        "7 Island (RNA) 1" + System.lineSeparator() +
                        "3 Mountain (RNA) 2" + System.lineSeparator(),
                baos.toString());
    }
}