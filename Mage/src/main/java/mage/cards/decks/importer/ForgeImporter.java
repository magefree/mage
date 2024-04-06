package mage.cards.decks.importer;
import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;
import java.util.Optional;


public class ForgeImporter extends PlainTextDeckImporter {

    private boolean sideboard = false;
    private boolean skipHeader = true;

    @Override
    protected void readLine(String line, DeckCardLists deckList, FixedInfo fixedInfo) {
        
        line = line.trim();

        if (line.equals("[metadata]") || line.equals("[quest]") || line.equals("[shop]") || line.equals("[general]")) {
            skipHeader = true;
            return;
        }

        if (line.equals("[commander]") || line.equals("[main]")) {
            sideboard = false;
            skipHeader = false;
            return;
        }

        if (line.equals("[sideboard]")) {
            sideboard = true;
            skipHeader = false;
            return;
        }

        if (skipHeader) {
            return;
        }

        int delim = line.indexOf(' ');
        String lineNum = line.substring(0, delim).trim();
        String lineOther = line.substring(delim).trim();

        String lineSet = null;
        String lineName = lineOther;

        int delim2 = lineOther.indexOf('|');
        if (delim2 > 0) {
            lineName = lineOther.substring(0, delim2).trim();
            lineSet = lineOther.substring(delim2 + 1).trim();
        }

        try {
            int num = Integer.parseInt(lineNum);
            Optional<CardInfo> cardLookup = getCardLookup().lookupCardInfo(lineName, lineSet);
            if (!cardLookup.isPresent()) {
                sbMessage.append("Could not find card: '").append(lineName).append("' at line ").append(lineCount).append('\n');
            } else {
                CardInfo cardInfo = cardLookup.get();
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
