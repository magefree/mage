package mage.client.util;

import mage.cards.Card;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.view.*;

import java.util.List;
import java.util.Map;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class CardsViewUtil {

    public static CardsView convertSimple(SimpleCardsView view) {
        CardsView cards = new CardsView();

        for (SimpleCardView simple : view.values()) {
            CardInfo cardInfo = CardRepository.instance.findCard(simple.getExpansionSetCode(), simple.getCardNumber());
            Card card = cardInfo != null ? cardInfo.getMockCard() : null;
            if (card != null) {
                cards.put(simple.getId(), new CardView(card, simple));
            }
        }

        return cards;
    }

    public static CardsView convertSimple(SimpleCardsView view, Map<String, Card> loadedCards) {
        CardsView cards = new CardsView();

        for (SimpleCardView simple : view.values()) {
            String key = simple.getExpansionSetCode() + '_' + simple.getCardNumber();
            Card card = loadedCards.get(key);
            if (card == null) {
                CardInfo cardInfo = CardRepository.instance.findCard(simple.getExpansionSetCode(), simple.getCardNumber());
                card = cardInfo != null ? cardInfo.getMockCard() : null;
                loadedCards.put(key, card);
            }
            if (card != null) {
                cards.put(simple.getId(), new CardView(card, simple));
            }
        }

        return cards;
    }

    public static CardsView convertCommandObject(List<CommandObjectView> view) {
        CardsView cards = new CardsView();
        for (CommandObjectView commandObject : view) {
            CardView cardView;
            if (commandObject instanceof EmblemView) {
                cardView = new CardView((EmblemView) commandObject);
            } else if (commandObject instanceof DungeonView) {
                cardView = new CardView((DungeonView) commandObject);
            } else if (commandObject instanceof PlaneView) {
                cardView = new CardView((PlaneView) commandObject);
            } else if (commandObject instanceof CommanderView) {
                cardView = (CommanderView) commandObject;
            } else {
                throw new IllegalStateException("ERROR, unsupported commander object type: " + commandObject.getClass().getSimpleName());
            }
            cards.put(commandObject.getId(), cardView);
        }
        return cards;
    }
}
