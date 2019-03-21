package mage.cards.decks.exporter;

import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.cards.decks.DeckFileFilter;

import javax.swing.filechooser.FileFilter;
import java.io.PrintWriter;
import java.util.List;
import java.util.TreeMap;

public class MtgoDeckExporter extends DeckExporter {

    private final String ext = "dek";
    private final String description = "MTGO's deck format (*.dek)";
    private final FileFilter fileFilter = new DeckFileFilter(ext, description);

    @Override
    public void writeDeck(PrintWriter out, DeckCardLists deck) {
        TreeMap<String, Integer> deckCards = toCardMap(deck.getCards());
        TreeMap<String, Integer> sideboard = toCardMap(deck.getSideboard());
        deckCards.forEach((name, count) -> {
            out.print(count);
            out.print(' ');
            out.println(name);
        });

        out.println();
        out.println();

        sideboard.forEach((name, count) -> {
            out.print(count);
            out.print(' ');
            out.println(name);
        });

        out.println();
    }

    private TreeMap<String, Integer> toCardMap(List<DeckCardInfo> cards) {
        TreeMap<String, Integer> counts = new TreeMap<>();
        for (DeckCardInfo card : cards) {
            int count = counts.getOrDefault(card.getCardName(), 0) + card.getQuantity();
            counts.put(card.getCardName(), count);
        }
        return counts;
    }

    @Override
    public FileFilter getFileFilter() {
        return fileFilter;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getDefaultFileExt() {
        return ext;
    }
}
