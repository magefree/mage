package mage.cards.decks.importer;

import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;

/**
 * Created by royk on 11-Sep-16.
 */
public class DekDeckImporter extends PlainTextDeckImporter {

    @Override
    protected void readLine(String line, DeckCardLists deckList, FixedInfo fixedInfo) {

        if (line.isEmpty() || line.startsWith("#") || !line.contains("<Cards CatID")) {
            return;
        }
        try {
            // e.g. <Cards CatID="61202" Quantity="1" Sideboard="false" Name="Vildin-Pack Outcast" />
            Integer cardCount = Integer.parseInt(extractAttribute(line, "Quantity"));
            String cardName = extractAttribute(line, "Name");
            boolean isSideboard = "true".equals(extractAttribute(line, "Sideboard"));
            CardInfo cardInfo = CardRepository.instance.findPreferredCoreExpansionCard(cardName);
            if (cardInfo == null) {
                sbMessage.append("Could not find card: '").append(cardName).append("' at line ").append(lineCount).append('\n');
            } else {
                for (int i = 0; i < cardCount; i++) {
                    DeckCardInfo deckCardInfo = new DeckCardInfo(cardInfo.getName(), cardInfo.getCardNumber(), cardInfo.getSetCode());
                    if (isSideboard) {
                        deckList.getSideboard().add(deckCardInfo);
                    } else {
                        deckList.getCards().add(deckCardInfo);
                    }
                }
            }
        } catch (NumberFormatException nfe) {
            sbMessage.append("Invalid number: ").append(extractAttribute(line, "Quantity")).append(" at line ").append(lineCount).append('\n');
        }

    }

    private String extractAttribute(String line, String name) {
        String searchString = name + "=\"";
        int startDelim = line.indexOf(searchString) + searchString.length();
        int endDelim = line.substring(startDelim).indexOf('\"');
        return line.substring(startDelim, startDelim + endDelim);
    }

}
