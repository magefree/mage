package mage.cards.decks.exporter;

import mage.cards.decks.DeckCardLists;
import mage.cards.decks.DeckFormats;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public abstract class DeckExporter {

    public void writeDeck(String file, DeckCardLists deck) throws IOException {
        DeckFormats.writeDeck(file, deck, this);
    }

    public void writeDeck(File directory, String filename, DeckCardLists deck) throws IOException {
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String path = new File(directory, filename).getPath();
        DeckFormats.writeDeck(path, deck, this);
    }

    public void writeDeck(File file, DeckCardLists deck) throws IOException {
        DeckFormats.writeDeck(file, deck, this);
    }

    public void writeDeck(OutputStream out, DeckCardLists deck) {
        DeckFormats.writeDeck(out, deck, this);
    }

    public abstract void writeDeck(PrintWriter out, DeckCardLists deck);

    public abstract FileFilter getFileFilter();

    public abstract String getDefaultFileExt();

    public abstract String getDescription();
}
