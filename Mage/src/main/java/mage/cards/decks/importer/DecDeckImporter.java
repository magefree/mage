package mage.cards.decks.importer;

import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;

import java.util.Optional;

/**
 * Deck import: Decked Builder, Apprentice and old Magic Online
 * <p>
 * Outdated, see actual format in TxtDeckImporter
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DecDeckImporter extends PlainTextDeckImporter {

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
        String lineName = line.substring(delim).trim();
        try {
            int num = Integer.parseInt(lineNum);
            Optional<CardInfo> cardLookup = getCardLookup().lookupCardInfo(lineName);
            if (!cardLookup.isPresent()) {
                sbMessage.append("Could not find card: '").append(lineName).append("' at line ").append(lineCount).append('\n');
            } else {
                CardInfo cardInfo = cardLookup.get();
                DeckCardInfo.makeSureCardAmountFine(num, cardInfo.getName());
                DeckCardInfo deckCardInfo = new DeckCardInfo(cardInfo.getName(), cardInfo.getCardNumber(), cardInfo.getSetCode());
                for (int i = 0; i < num; i++) {
                    if (sideboard) {
                        deckList.getSideboard().add(deckCardInfo.copy());
                    } else {
                        deckList.getCards().add(deckCardInfo.copy());
                    }
                }
            }
        } catch (NumberFormatException nfe) {
            sbMessage.append("Invalid number: ").append(lineNum).append(" at line ").append(lineCount).append('\n');
        }
    }

}
