
package mage.cards.decks.importer;

import java.util.List;

import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.util.RandomUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MWSDeckImporter extends DeckImporter {

    @Override
    protected void readLine(String line, DeckCardLists deckList) {
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
            if (!setCode.isEmpty()) {
                CardCriteria criteria = new CardCriteria();
                criteria.name(lineName);
                criteria.setCodes(setCode);
                List<CardInfo> cards = CardRepository.instance.findCards(criteria);
                if (!cards.isEmpty()) {
                    cardInfo = cards.get(RandomUtil.nextInt(cards.size()));
                }
            } 
            if (cardInfo == null) {
                cardInfo = CardRepository.instance.findPreferedCoreExpansionCard(lineName, true);
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
