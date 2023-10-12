package mage.client.util;

import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.view.DeckView;
import mage.view.SimpleCardView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for decks.
 *
 * @author nantuko
 */
public final class DeckUtil {
    private static final Logger logger = LoggerFactory.getLogger(DeckUtil.class);

    private DeckUtil() {
    }

    public static Deck construct(DeckView view) {
        Deck deck = new Deck();
        for (SimpleCardView cardView : view.getCards().values()) {
            CardInfo cardInfo = CardRepository.instance.findCard(cardView.getExpansionSetCode(), cardView.getCardNumber());
            Card card = cardInfo != null ? cardInfo.getMockCard() : null;
            if (card != null) {
                deck.getCards().add(card);
            } else {
                logger.error("(Deck constructing) Couldn't find card: set=" + cardView.getExpansionSetCode() + ", cid=" + Integer.valueOf(cardView.getCardNumber()));
            }
        }
        for (SimpleCardView cardView : view.getSideboard().values()) {
            CardInfo cardInfo = CardRepository.instance.findCard(cardView.getExpansionSetCode(), cardView.getCardNumber());
            Card card = cardInfo != null ? cardInfo.getMockCard() : null;
            if (card != null) {
                deck.getSideboard().add(card);
            } else {
                logger.error("(Deck constructing) Couldn't find card: set=" + cardView.getExpansionSetCode() + ", cid=" + Integer.valueOf(cardView.getCardNumber()));
            }
        }
        return deck;
    }
}
