

package mage.view;

import mage.cards.Card;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SimpleCardsView extends LinkedHashMap<UUID, SimpleCardView> {

    public SimpleCardsView() {}

    public SimpleCardsView(Collection<Card> cards, boolean isGameObject) {
        for (Card card: cards) {
            this.put(card.getId(), new SimpleCardView(card.getId(), card.getExpansionSetCode(), card.getCardNumber(), card.getUsesVariousArt(), isGameObject));
        }
    }

}
