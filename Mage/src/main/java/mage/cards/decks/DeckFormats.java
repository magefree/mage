package mage.cards.decks;

import mage.cards.decks.exporter.DckExporter;
import mage.cards.decks.exporter.DeckExporter;
import mage.cards.decks.exporter.MtgoExporter;

import java.io.*;
import java.util.Optional;

public enum DeckFormats {

    DCK(new DckExporter()),
    MTGO(new MtgoExporter());

    private final DeckExporter exporter;

    DeckFormats(DeckExporter exporter) {
        this.exporter = exporter;
    }

    public DeckExporter getExporter() {
        return exporter;
    }

    public static Optional<DeckFormats> getFormatForExtension(String filename) {
        return getExtension(filename).map(c -> {
            try {
                return DeckFormats.valueOf(c);
            } catch (IllegalArgumentException e) {
                return null;
            }
        });
    }

    public static Optional<String> getExtension(String filename) {
        int i = filename.lastIndexOf('.');
        if (i > 0) {
            return Optional.of(filename.substring(i+1).toUpperCase());
        } else {
            return Optional.empty();
        }
    }

    public static void writeDeck(String file, DeckCardLists deck) throws IOException {
        writeDeck(new File(file), deck);
    }

    public static void writeDeck(String file, DeckCardLists deck, DeckFormats format) throws IOException {
        writeDeck(new File(file), deck, format);
    }

    public static void writeDeck(String file, DeckCardLists deck, DeckExporter exporter) throws IOException {
        writeDeck(new File(file), deck, exporter);
    }

    public static void writeDeck(File file, DeckCardLists deck) throws IOException {
        DeckFormats format = DeckFormats.getFormatForExtension(file.getName()).orElseGet(() -> {
            throw new IllegalArgumentException("Could not determine deck export format.");
        });
        writeDeck(file, deck, format);
    }

    public static void writeDeck(File file, DeckCardLists deck, DeckFormats format) throws IOException {
        writeDeck(file, deck, format.getExporter());
    }

    public static void writeDeck(File file, DeckCardLists deck, DeckExporter exporter) throws IOException {
        try (FileOutputStream out = new FileOutputStream(file)){
            writeDeck(out, deck, exporter);
        }
    }

    public static void writeDeck(OutputStream out, DeckCardLists deck, DeckFormats format) {
        writeDeck(new PrintWriter(out), deck, format);
    }

    public static void writeDeck(OutputStream out, DeckCardLists deck, DeckExporter exporter) {
        writeDeck(new PrintWriter(out), deck, exporter);
    }

    public static void writeDeck(PrintWriter out, DeckCardLists deck, DeckFormats format) {
        writeDeck(out, deck, format.getExporter());
    }

    public static void writeDeck(PrintWriter out, DeckCardLists deck, DeckExporter exporter) {
        exporter.writeDeck(out, deck);
        out.flush();
    }

}
