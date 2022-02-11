package mage.cards.decks.importer;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;
import mage.util.JsonUtil;

import java.util.List;
import java.util.Optional;


/**
 * @author github: timhae
 */
public class MtgjsonDeckImporter extends JsonDeckImporter {

    @Override
    protected void readJson(JsonObject json, DeckCardLists deckList) {
        JsonObject data = JsonUtil.getAsObject(json, "data");
        if (data == null) {
            sbMessage.append("Could not find data in json").append("'\n");
            return;
        }

        // info
        String deckSet = JsonUtil.getAsString(data, "code");
        String name = JsonUtil.getAsString(data, "name");
        if (!name.isEmpty()) {
            deckList.setName(name);
        }

        // mainboard
        JsonArray mainBoard = JsonUtil.getAsArray(data, "mainBoard");
        List<mage.cards.decks.DeckCardInfo> mainDeckList = deckList.getCards();
        addBoardToList(mainBoard, mainDeckList, deckSet);

        // sideboard
        JsonArray sideBoard = JsonUtil.getAsArray(data, "sideBoard");
        List<mage.cards.decks.DeckCardInfo> sideDeckList = deckList.getSideboard();
        addBoardToList(sideBoard, sideDeckList, deckSet);
    }

    private void addBoardToList(JsonArray board, List<mage.cards.decks.DeckCardInfo> list, String deckSet) {
        if (board == null || board.isEmpty()) {
            return;
        }

        board.forEach(arrayCard -> {
            JsonObject card = (JsonObject) arrayCard;
            String name = JsonUtil.getAsString(card, "name");
            String setCode = JsonUtil.getAsString(card, "setCode");
            if (setCode.isEmpty()) {
                setCode = deckSet;
            }

            int num = JsonUtil.getAsInt(card, "count");
            Optional<CardInfo> cardLookup = getCardLookup().lookupCardInfo(name, setCode);
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
