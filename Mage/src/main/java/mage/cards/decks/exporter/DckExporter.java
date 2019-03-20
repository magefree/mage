package mage.cards.decks.exporter;

import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLayout;
import mage.cards.decks.DeckCardLists;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DckExporter extends DeckExporter {

    public void writeDeck(PrintWriter out, DeckCardLists deck) {
        Map<String, DeckCardInfo> deckCards = new HashMap<>();
        Map<String, DeckCardInfo> sideboard = new HashMap<>();

        if (deck.getName() != null && !deck.getName().isEmpty()) {
            out.println("NAME:" + deck.getName());
        }
        if (deck.getAuthor() != null && !deck.getAuthor().isEmpty()) {
            out.println("AUTHOR:" + deck.getAuthor());
        }
        for (DeckCardInfo deckCardInfo : deck.getCards()) {
            if (deckCards.containsKey(deckCardInfo.getCardKey())) {
                deckCards.put(deckCardInfo.getCardKey(), deckCards.get(deckCardInfo.getCardKey()).increaseQuantity());
            } else {
                deckCards.put(deckCardInfo.getCardKey(), deckCardInfo);
            }
        }

        for (DeckCardInfo deckCardInfo : deck.getSideboard()) {
            if (sideboard.containsKey(deckCardInfo.getCardKey())) {
                sideboard.put(deckCardInfo.getCardKey(), sideboard.get(deckCardInfo.getCardKey()).increaseQuantity());
            } else {
                sideboard.put(deckCardInfo.getCardKey(), deckCardInfo);
            }
        }

        // Write out all of the cards
        for (Map.Entry<String, DeckCardInfo> entry : deckCards.entrySet()) {
            out.printf("%d [%s:%s] %s%n", entry.getValue().getQuantity(), entry.getValue().getSetCode(), entry.getValue().getCardNum(), entry.getValue().getCardName());
        }
        for (Map.Entry<String, DeckCardInfo> entry : sideboard.entrySet()) {
            out.printf("SB: %d [%s:%s] %s%n", entry.getValue().getQuantity(), entry.getValue().getSetCode(), entry.getValue().getCardNum(), entry.getValue().getCardName());
        }

        // Write out the layout
        out.print("LAYOUT MAIN:");
        writeCardLayout(out, deck.getCardLayout());
        out.println("");
        out.print("LAYOUT SIDEBOARD:");
        writeCardLayout(out, deck.getSideboardLayout());
        out.println("");
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

}
