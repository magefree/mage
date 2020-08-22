package mage.cards.decks.importer;

import java.util.Optional;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;


/**
 *
 * @author github: timhae
 */
public class MtgjsonDeckImporter extends JsonDeckImporter {

    @Override
    protected void readJson(JSONObject rootObj, DeckCardLists deckList) {
        // set
        String set = (String) rootObj.get("code");
        // mainboard
        JSONArray mainBoard = (JSONArray) rootObj.get("mainBoard");
        List<mage.cards.decks.DeckCardInfo> mainDeckList = deckList.getCards();
        addBoardToList(mainBoard, mainDeckList, set);
        // sideboard
        JSONArray sideBoard = (JSONArray) rootObj.get("sideBoard");
        List<mage.cards.decks.DeckCardInfo> sideDeckList = deckList.getSideboard();
        addBoardToList(sideBoard, sideDeckList, set);
    }

    private void addBoardToList(JSONArray board, List<mage.cards.decks.DeckCardInfo> list, String set) {
        board.forEach(arrayCard -> {
            JSONObject card = (JSONObject) arrayCard;
            String name = (String) card.get("name");
            int num = ((Number) card.get("count")).intValue();
            Optional<CardInfo> cardLookup = getCardLookup().lookupCardInfo(name, set);
            if (!cardLookup.isPresent()) {
                sbMessage.append("Could not find card: '").append(name).append("'\n");
            } else {
                CardInfo cardInfo = cardLookup.get();
                for (int i = 0; i < num; i++) {
                    list.add(new DeckCardInfo(cardInfo.getName(), cardInfo.getCardNumber(), cardInfo.getSetCode()));
                }
            }
        });
    }
}
