package mage.cards.decks.exporter;

import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class MtgoDeckExporterTest {

    @Test
    public void writeDeck() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DeckCardLists deck = new DeckCardLists();
        deck.getCards().add(new DeckCardInfo("Forest", "RNA", "1", 2));
        deck.getCards().add(new DeckCardInfo("Plains", "RNA", "2", 3));
        deck.getSideboard().add(new DeckCardInfo("Island", "RNA", "3", 2));
        MtgoDeckExporter exporter = new MtgoDeckExporter();
        exporter.writeDeck(baos, deck);
        assertEquals("2 Forest" + System.lineSeparator() +
                        "3 Plains" + System.lineSeparator() +
                        System.lineSeparator() +
                        System.lineSeparator() +
                        "2 Island" + System.lineSeparator() +
                        System.lineSeparator(),
                baos.toString());
    }

}