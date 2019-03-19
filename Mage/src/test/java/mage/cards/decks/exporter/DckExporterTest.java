package mage.cards.decks.exporter;

import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class DckExporterTest {

    @Test
    public void writeDeck() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DeckCardLists deck = new DeckCardLists();
        deck.getCards().add(new DeckCardInfo("Forest", "RNA", "1", 2));
        deck.getCards().add(new DeckCardInfo("Plains", "RNA", "2", 3));
        deck.getSideboard().add(new DeckCardInfo("Island", "RNA", "3", 2));
        DckExporter exporter = new DckExporter();
        exporter.writeDeck(baos, deck);
        assertEquals(
                "2 [1:RNA] Forest\n" +
                "3 [2:RNA] Plains\n" +
                "SB: 2 [3:RNA] Island\n" +
                "LAYOUT MAIN:\n" +
                "LAYOUT SIDEBOARD:\n",
                new String(baos.toByteArray()));
    }

}