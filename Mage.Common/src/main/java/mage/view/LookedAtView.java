

package mage.view;

import mage.cards.Card;
import mage.cards.Cards;
import mage.game.Game;

import java.io.Serializable;

/**
 *
 * @author BetaSteward_at_googlemail.com, nantuko
 */
public class LookedAtView implements Serializable {

    private final String name;
    private final SimpleCardsView cards = new SimpleCardsView();

    public LookedAtView(String name, Cards cards, Game game) {
        this.name = name;
        for (Card card: cards.getCards(game)) {
            this.cards.put(card.getId(), new SimpleCardView(card.getId(), card.getExpansionSetCode(), card.getCardNumber(), card.getUsesVariousArt()));
        }
    }

    public String getName() {
        return name;
    }

    public SimpleCardsView getCards() {
        return cards;
    }
}
