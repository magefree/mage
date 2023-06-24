package mage.cards.decks.importer;

import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class MWSDeckImporter extends PlainTextDeckImporter {

    @Override
    protected void readLine(String line, DeckCardLists deckList, FixedInfo fixedInfo) {

        if (line.isEmpty() || line.startsWith("//")) {
            return;
        }
        boolean sideboard = false;
        if (line.startsWith("SB:")) {
            line = line.substring(3).trim();
            sideboard = true;
        }
        int delim = line.indexOf(' ');
        String lineNum = line.substring(0, delim).trim();
        String setCode = "";
        if (line.indexOf('[') != -1) {
            int setStart = line.indexOf('[') + 1;
            int setEnd = line.indexOf(']');
            setCode = line.substring(setStart, setEnd).trim();
            delim = setEnd;
        }
        String lineName = line.substring(delim + 1).trim();
        try {
            int num = Integer.parseInt(lineNum);
            CardInfo cardInfo = null;
            if (setCode.isEmpty()) {
                cardInfo = getCardLookup().lookupCardInfo(lineName, setCode).orElse(null);
            } else {
                cardInfo = getCardLookup().lookupCardInfo(lineName).orElse(null);
            }

            if (cardInfo == null) {
                sbMessage.append("Could not find card: '").append(lineName).append("' at line ").append(lineCount).append('\n');
            } else {
                for (int i = 0; i < num; i++) {
                    if (!sideboard) {
                        deckList.getCards().add(new DeckCardInfo(cardInfo.getName(), cardInfo.getCardNumber(), cardInfo.getSetCode()));
                    } else {
                        deckList.getSideboard().add(new DeckCardInfo(cardInfo.getName(), cardInfo.getCardNumber(), cardInfo.getSetCode()));
                    }
                }
            }
        } catch (NumberFormatException nfe) {
            sbMessage.append("Invalid number: ").append(lineNum).append(" at line ").append(lineCount).append('\n');
        }
    }
}
