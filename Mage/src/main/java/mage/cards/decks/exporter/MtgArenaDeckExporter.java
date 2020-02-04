package mage.cards.decks.exporter;

import com.google.common.collect.ImmutableMap;
import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.cards.decks.DeckFileFilter;

import javax.swing.filechooser.FileFilter;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author JayDi85
 */
public class MtgArenaDeckExporter extends DeckExporter {

    private static final String ext = "mtga";
    private static final String description = "MTG Arena's deck format (*.mtga)";
    private static final FileFilter fileFilter = new DeckFileFilter(ext, description);

    private static final Map<String, String> SET_CODE_REPLACEMENTS = ImmutableMap.of("DOM", "DAR");

    @Override
    public void writeDeck(PrintWriter out, DeckCardLists deck) {
        Map<String, Integer> amount = new HashMap<>();
        List<String> deckMain = prepareCardsList(deck.getCards(), amount, "M@");
        List<String> deckSideboard = prepareCardsList(deck.getSideboard(), amount, "S@");

        printCards(out, deckMain, amount, "M@");
        if (deckSideboard.size() > 0) {
            out.println();
            printCards(out, deckSideboard, amount, "S@");
        }
    }

    private List<String> prepareCardsList(List<DeckCardInfo> sourceCards, Map<String, Integer> amount, String prefix) {
        List<String> res = new ArrayList<>();
        for (DeckCardInfo card : sourceCards) {
            String setCode = card.getSetCode().toUpperCase(Locale.ENGLISH);
            setCode = SET_CODE_REPLACEMENTS.getOrDefault(setCode, setCode);
            String name = card.getCardName() + " (" + setCode + ") " + card.getCardNum();
            String code = prefix + name;
            int curAmount = amount.getOrDefault(code, 0);
            if (curAmount == 0) {
                res.add(name);
            }
            amount.put(code, curAmount + card.getQuantity());
        }
        return res;
    }

    private void printCards(PrintWriter out, List<String> deck, Map<String, Integer> amount, String prefix) {
        if (deck.size() == 0) return;

        boolean firstCard = true;
        for (String name : deck) {
            if (!firstCard) out.println();
            out.print(amount.get(prefix + name));
            out.print(' ');
            out.print(name);
            firstCard = false;
        }
        out.println();
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
