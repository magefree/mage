

package mage.view;

import java.io.Serializable;

import mage.cards.Card;
import mage.cards.Cards;
import mage.game.Game;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class RevealedView implements Serializable {

    private final String name;
    private final CardsView cards = new CardsView();

    public RevealedView(String name, Cards cards, Game game) {
        this.name = name;
        for (Card card : cards.getCards(game)) {
            this.cards.put(card.getId(), new CardView(card, game, card.getId()));
        }
    }

    public String getName() {
        return name;
    }

    public CardsView getCards() {
        return cards;
    }
}
