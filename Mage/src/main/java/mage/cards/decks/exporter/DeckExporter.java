package mage.cards.decks.exporter;

import mage.cards.decks.DeckCardLists;
import mage.cards.decks.DeckFormats;

import java.io.*;

public abstract class DeckExporter {

    public void writeDeck(String file, DeckCardLists deck) throws IOException {
        DeckFormats.writeDeck(file, deck, this);
    }

    public void writeDeck(File file, DeckCardLists deck) throws IOException {
        DeckFormats.writeDeck(file, deck, this);
    }

    public void writeDeck(OutputStream out, DeckCardLists deck) {
        DeckFormats.writeDeck(out, deck, this);
    }

    public abstract void writeDeck(PrintWriter out, DeckCardLists deck);

}
