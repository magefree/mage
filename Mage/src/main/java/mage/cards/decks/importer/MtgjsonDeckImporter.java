package mage.cards.decks.importer;

import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Optional;


/**
 * @author github: timhae
 */
public class MtgjsonDeckImporter extends JsonDeckImporter {

    @Override
    protected void readJson(JSONObject rootObj, DeckCardLists deckList) {
        JSONObject data = (JSONObject) rootObj.get("data");
        if (data == null) {
            sbMessage.append("Could not find data in json").append("'\n");
            return;
        }

        // info
        String deckSet = (String) data.get("code");
        String name = (String) data.get("name");
        if (name != null) {
            deckList.setName(name);
        }
        // mainboard
        JSONArray mainBoard = (JSONArray) data.get("mainBoard");
        List<mage.cards.decks.DeckCardInfo> mainDeckList = deckList.getCards();
        addBoardToList(mainBoard, mainDeckList, deckSet);
        // sideboard
        JSONArray sideBoard = (JSONArray) data.get("sideBoard");
        List<mage.cards.decks.DeckCardInfo> sideDeckList = deckList.getSideboard();
        addBoardToList(sideBoard, sideDeckList, deckSet);
    }

    private void addBoardToList(JSONArray board, List<mage.cards.decks.DeckCardInfo> list, String deckSet) {
        board.forEach(arrayCard -> {
            JSONObject card = (JSONObject) arrayCard;
            String cardNum = (String) card.get("number");
            String cardName = (String) card.get("name");
            String setCode = (String) card.get("setCode");
            if (setCode == null || setCode.isEmpty()) {
                setCode = deckSet;
            }

            int count = ((Number) card.get("count")).intValue();
            CardInfo foundedCard = CardRepository.instance.findCard(setCode, cardNum, true);
            if (foundedCard == null) {
            	sbMessage.append("Could not find card: '").append(cardName).append("'\n");
            } else {
                for (int i = 0; i < count; i++) {
                    list.add(new DeckCardInfo(foundedCard.getName(), foundedCard.getCardNumber(), foundedCard.getSetCode()));
                }
            }
        });
    }
}
