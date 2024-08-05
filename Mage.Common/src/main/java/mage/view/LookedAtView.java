

package mage.view;

import mage.MageObject;
import mage.cards.Card;
import mage.cards.Cards;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;

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
            if (card instanceof PermanentCard && card.isFaceDown(game)) {
                MageObject trueCard = ((Permanent) card).getBasicMageObject();
                this.cards.put(card.getId(), new SimpleCardView(trueCard.getId(), trueCard.getExpansionSetCode(), trueCard.getCardNumber(), trueCard.getUsesVariousArt()));
            } else {
                this.cards.put(card.getId(), new SimpleCardView(card.getId(), card.getExpansionSetCode(), card.getCardNumber(), card.getUsesVariousArt()));
            }
        }
    }

    public String getName() {
        return name;
    }

    public SimpleCardsView getCards() {
        return cards;
    }
}
