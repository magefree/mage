package mage.cards.decks.exporter;

import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class XmageDeckExporterTest {

    @Test
    public void writeDeck() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DeckCardLists deck = new DeckCardLists();
        deck.getCards().add(new DeckCardInfo("Forest", "1", "RNA", 2));
        deck.getCards().add(new DeckCardInfo("Plains", "2", "RNA", 3));
        deck.getCards().add(new DeckCardInfo("Plains", "2", "RNA", 5)); // must combine
        deck.getCards().add(new DeckCardInfo("Mountain", "3", "RNA", 1));
        deck.getSideboard().add(new DeckCardInfo("Island", "1", "RNA", 2));
        deck.getSideboard().add(new DeckCardInfo("Island", "1", "RNA", 5)); // must combine
        deck.getSideboard().add(new DeckCardInfo("Mountain", "2", "RNA", 3));
        DeckExporter exporter = new XmageDeckExporter();
        exporter.writeDeck(baos, deck);
        assertEquals("2 [RNA:1] Forest" + System.lineSeparator() +
                        "8 [RNA:2] Plains" + System.lineSeparator() +
                        "1 [RNA:3] Mountain" + System.lineSeparator() +
                        "SB: 7 [RNA:1] Island" + System.lineSeparator() +
                        "SB: 3 [RNA:2] Mountain" + System.lineSeparator(),
                baos.toString());
    }

}