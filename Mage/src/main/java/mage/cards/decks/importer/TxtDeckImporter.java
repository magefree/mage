package mage.cards.decks.importer;

import mage.cards.decks.CardNameUtil;
import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TxtDeckImporter extends PlainTextDeckImporter {

    private static final String[] SET_VALUES = new String[]{"lands", "creatures", "planeswalkers", "other spells", "sideboard cards",
            "Instant", "Land", "Enchantment", "Artifact", "Sorcery", "Planeswalker", "Creature"};
    private static final Set<String> IGNORE_NAMES = new HashSet<>(Arrays.asList(SET_VALUES));

    private boolean sideboard = false;
    private boolean switchSideboardByEmptyLine = true; // all cards after first empty line will be sideboard (like mtgo format)
    private boolean wasCardLines = false;

    public TxtDeckImporter(boolean haveSideboardSection) {
        if (haveSideboardSection) {
            switchSideboardByEmptyLine = false;
        }
    }

    @Override
    protected void readLine(String line, DeckCardLists deckList, FixedInfo fixedInfo) {

        line = line.trim();

        // process comment:
        // skip or force to sideboard
        String commentString = line.toLowerCase(Locale.ENGLISH);
        if (commentString.startsWith("//")) {
            // use start, not contains (card names may contain commands like "Legerdemain")

            if (commentString.startsWith("//sideboard")) {
                sideboard = true;
            }

            // skip comment line
            return;
        }

        // remove inner card comments from text line: 2 Blinding Fog #some text (like deckstats format)
        int commentDelim = line.indexOf('#');
        if (commentDelim >= 0) {
            line = line.substring(0, commentDelim).trim();
        }

        // ignore all empty lines until real cards starts
        if (line.isEmpty() && !wasCardLines) {
            return;
        }

        // switch sideboard by empty line
        if (switchSideboardByEmptyLine && line.isEmpty()) {
            if (!sideboard) {
                sideboard = true;
            } else {
                sbMessage.append("Found empty line at ").append(lineCount).append(", but sideboard already used. Use //sideboard switcher OR use only one empty line to devide your cards.").append('\n');
            }

            // skip empty line
            return;
        }

        // single line sideboard card from deckstats.net
        // SB: 3 Carnage Tyrant
        boolean singleLineSideBoard = false;
        if (line.startsWith("SB:")) {
            line = line.replace("SB:", "").trim();
            singleLineSideBoard = true;
        }

        line = line.replace("\t", " "); // changing tabs to blanks as delimiter
        int delim = line.indexOf(' ');
        String lineNum = "";
        if (delim > 0) {
            lineNum = line.substring(0, delim).trim();
            if (IGNORE_NAMES.contains(lineNum)) {
                return;
            }
        }

        // amount
        int cardAmount = 0;
        boolean haveCardAmout = false;
        if (!lineNum.isEmpty()) {
            try {
                cardAmount = Integer.parseInt(lineNum.replaceAll("\\D+", ""));
                if ((cardAmount <= 0) || (cardAmount >= 100)) {
                    sbMessage.append("Invalid number (too small or too big): ").append(lineNum).append(" at line ").append(lineCount).append('\n');
                    return;
                }
                haveCardAmout = true;
            } catch (NumberFormatException nfe) {
                // card without amount
            }
        }

        String lineName;
        if (haveCardAmout) {
            lineName = line.substring(delim).trim();
        } else {
            lineName = line.trim();
            cardAmount = 1;
        }

        lineName = CardNameUtil.normalizeCardName(lineName);
        if (lineName.contains("//") && !lineName.contains(" // ")) {
            lineName = lineName.replace("//", " // ");
        }
        lineName = lineName.replaceFirst("(?<=[^/])\\s*/\\s*(?=[^/])", " // ");

        if (IGNORE_NAMES.contains(lineName)) {
            return;
        }

        wasCardLines = true;

        CardInfo cardInfo = CardRepository.instance.findPreferredCoreExpansionCard(lineName, true);
        if (cardInfo == null) {
            sbMessage.append("Could not find card: '").append(lineName).append("' at line ").append(lineCount).append('\n');
        } else {
            for (int i = 0; i < cardAmount; i++) {
                if (!sideboard && !singleLineSideBoard) {
                    deckList.getCards().add(new DeckCardInfo(cardInfo.getName(), cardInfo.getCardNumber(), cardInfo.getSetCode()));
                } else {
                    deckList.getSideboard().add(new DeckCardInfo(cardInfo.getName(), cardInfo.getCardNumber(), cardInfo.getSetCode()));
                }
            }
        }
    }
}
