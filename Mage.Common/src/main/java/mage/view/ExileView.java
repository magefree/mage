

package mage.view;

import java.util.UUID;
import mage.cards.Card;
import mage.game.ExileZone;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ExileView extends CardsView {
    private static final long serialVersionUID = 1L;

    private final String name;
    private final UUID id;

    public ExileView(ExileZone exileZone, Game game, UUID createdForPlayerId) {
        this.name = exileZone.getName();
        this.id = exileZone.getId();
        for (Card card: exileZone.getCards(game)) {
            this.put(card.getId(), new CardView(card, game, CardUtil.canShowAsControlled(card, createdForPlayerId)));
        }
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

}
