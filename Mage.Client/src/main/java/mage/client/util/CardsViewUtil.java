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
                cards.put(simple.getId(), new CardView(card, simple.getId()));
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
                cards.put(simple.getId(), new CardView(card, simple.getId()));
            }
        }

        return cards;
    }

    public static CardsView convertCommandObject(List<CommandObjectView> view) {
        CardsView cards = new CardsView();

        for (CommandObjectView commandObject : view) {
            if (commandObject instanceof EmblemView) {
                CardView cardView = new CardView((EmblemView) commandObject);
                cards.put(commandObject.getId(), cardView);
            } else if (commandObject instanceof PlaneView) {
                CardView cardView = new CardView((PlaneView) commandObject);
                cards.put(commandObject.getId(), cardView);
            } else if (commandObject instanceof CommanderView) {
                cards.put(commandObject.getId(), (CommanderView) commandObject);
            }
        }

        return cards;
    }
}
