package mage.cards.decks.importer;

import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;

/**
 * Deck import: MTGO xml format
 * <p>
 * Outdated, see actual format in TxtDeckImporter
 *
 * @author royk
 */
public class DekDeckImporter extends PlainTextDeckImporter {

    @Override
    protected void readLine(String line, DeckCardLists deckList, FixedInfo fixedInfo) {

        if (line.isEmpty() || line.startsWith("#") || !line.contains("<Cards ")) {
            return;
        }
        try {
            // e.g. <Cards CatID="61202" Quantity="1" Sideboard="false" Name="Vildin-Pack Outcast" />
            Integer cardCount = Integer.parseInt(extractAttribute(line, "Quantity"));
            String cardName = extractAttribute(line, "Name");
            DeckCardInfo.makeSureCardAmountFine(cardCount, cardName);

            // fix double faces name to be compatible with xmage
            // Refuse/Cooperate -> Refuse // Cooperate
            if (!cardName.contains("//") && cardName.contains("/")) {
                cardName = cardName.replace("/", " // ");
            }

            boolean isSideboard = "true".equals(extractAttribute(line, "Sideboard"));
            CardInfo cardInfo = getCardLookup().lookupCardInfo(cardName).orElse(null);
            if (cardInfo == null) {
                sbMessage.append("Could not find card: '").append(cardName).append("' at line ").append(lineCount).append('\n');
            } else {
                DeckCardInfo deckCardInfo = new DeckCardInfo(cardInfo.getName(), cardInfo.getCardNumber(), cardInfo.getSetCode());
                for (int i = 0; i < cardCount; i++) {
                    if (isSideboard) {
                        deckList.getSideboard().add(deckCardInfo.copy());
                    } else {
                        deckList.getCards().add(deckCardInfo.copy());
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
