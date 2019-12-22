package mage.cards.decks;

import mage.cards.decks.exporter.DeckExporter;
import mage.cards.decks.exporter.MtgArenaDeckExporter;
import mage.cards.decks.exporter.MtgOnlineDeckExporter;
import mage.cards.decks.exporter.XmageDeckExporter;

import javax.swing.filechooser.FileFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import mage.cards.decks.exporter.XmageInfoDeckExporter;

public enum DeckFormats {

    XMAGE(new XmageDeckExporter()),
    XMAGE_INFO(new XmageInfoDeckExporter()),
    MTG_ONLINE(new MtgOnlineDeckExporter()),
    MTG_ARENA(new MtgArenaDeckExporter());

    private final DeckExporter exporter;

    DeckFormats(DeckExporter exporter) {
        this.exporter = exporter;
    }

    public DeckExporter getExporter() {
        return exporter;
    }

    public static Optional<DeckFormats> getFormatForExtension(String filename) {
        String exp = getExtension(filename).orElse("");
        for (DeckFormats df : values()) {
            if (!exp.isEmpty() && df.getExporter().getDefaultFileExt().equals(exp)) {
                return Optional.of(df);
            }
        }
        return Optional.empty();
    }

    public static Optional<String> getExtension(String filename) {
        int i = filename.lastIndexOf('.');
        if (i > 0 && i < filename.length() - 1) {
            return Optional.of(filename.substring(i + 1).toLowerCase(Locale.ENGLISH));
        } else {
            return Optional.empty();
        }
    }

    public static Optional<DeckFormats> getFormatForFilter(FileFilter filter) {
        for (DeckFormats df : values()) {
            if (df.getExporter().getFileFilter().equals(filter)) {
                return Optional.of(df);
            }
        }
        return Optional.empty();
    }

    public static String getDefaultFileExtForFilter(FileFilter filter) {
        return getFormatForFilter(filter)
                .map(df -> df.getExporter().getDefaultFileExt())
                .orElse("");
    }

    public static List<FileFilter> getFileFilters() {
        List<FileFilter> res = new ArrayList<>();
        for (DeckFormats df : values()) {
            res.add(df.getExporter().getFileFilter());
        }
        return res;
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
        try (FileOutputStream out = new FileOutputStream(file)) {
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
