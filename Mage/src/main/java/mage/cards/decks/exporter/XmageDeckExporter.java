package mage.cards.decks.exporter;

import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLayout;
import mage.cards.decks.DeckCardLists;
import mage.cards.decks.DeckFileFilter;

import javax.swing.filechooser.FileFilter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class XmageDeckExporter extends DeckExporter {

    private final String ext = "dck";
    private final String description = "XMage's deck format (*.dck)";
    private final FileFilter fileFilter = new DeckFileFilter(ext, description);

    @Override
    public void writeDeck(PrintWriter out, DeckCardLists deck) {
        List<DeckCardInfo> deckMain = new ArrayList<>();
        List<DeckCardInfo> deckSideboard = new ArrayList<>();
        Map<String, Integer> amount = new HashMap<>();

        // info
        if (deck.getName() != null && !deck.getName().isEmpty()) {
            out.println("NAME:" + deck.getName());
        }
        if (deck.getAuthor() != null && !deck.getAuthor().isEmpty()) {
            out.println("AUTHOR:" + deck.getAuthor());
        }

        // main
        for (DeckCardInfo card : deck.getCards()) {
            String code = "M@" + card.getCardKey();
            int curAmount = amount.getOrDefault(code, 0);
            if (curAmount == 0) {
                deckMain.add(card);
            }
            amount.put(code, curAmount + card.getQuantity());
        }
        // sideboard
        for (DeckCardInfo card : deck.getSideboard()) {
            String code = "S@" + card.getCardKey();
            int curAmount = amount.getOrDefault(code, 0);
            if (curAmount == 0) {
                deckSideboard.add(card);
            }
            amount.put(code, curAmount + card.getQuantity());
        }

        // cards print
        for (DeckCardInfo card : deckMain) {
            out.printf("%d [%s:%s] %s%n", amount.get("M@" + card.getCardKey()), card.getSetCode(), card.getCardNum(), card.getCardName());
        }
        for (DeckCardInfo card : deckSideboard) {
            out.printf("SB: %d [%s:%s] %s%n", amount.get("S@" + card.getCardKey()), card.getSetCode(), card.getCardNum(), card.getCardName());
        }

        // layout print
        if (deck.getCardLayout() != null) {
            out.print("LAYOUT MAIN:");
            writeCardLayout(out, deck.getCardLayout());
            out.println("");
            out.print("LAYOUT SIDEBOARD:");
            writeCardLayout(out, deck.getSideboardLayout());
            out.println("");
        }
    }

    private static void writeCardLayout(PrintWriter out, DeckCardLayout layout) {
        if (layout == null) {
            return;
        }
        List<List<List<DeckCardInfo>>> cardGrid = layout.getCards();
        int height = cardGrid.size();
        int width = (height > 0) ? cardGrid.get(0).size() : 0;
        out.print("(" + height + ',' + width + ')');
        out.print(layout.getSettings());
        out.print("|");
        for (List<List<DeckCardInfo>> row : cardGrid) {
            for (List<DeckCardInfo> stack : row) {
                out.print("(");
                for (int i = 0; i < stack.size(); ++i) {
                    DeckCardInfo info = stack.get(i);
                    out.printf("[%s:%s]", info.getSetCode(), info.getCardNum());
                    if (i != stack.size() - 1) {
                        out.print(",");
                    }
                }
                out.print(")");
            }
        }
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
