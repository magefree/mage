
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.cards.Card;
import mage.constants.CardType;
import mage.game.Game;
import mage.players.Player;

/**
 * @author jeffwadsworth
 */
public class TopLibraryCardTypeCondition implements Condition {

    private CardType type;

    public TopLibraryCardTypeCondition(CardType type) {
        this.type = type;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && controller.getLibrary().hasCards()) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                switch (this.type) {
                    case CREATURE:
                        return card.isCreature(game);
                    case LAND:
                        return card.isLand(game);
                    case SORCERY:
                       return card.isSorcery(game);
                    case INSTANT:
                        return card.isInstant(game);
                }
            }
        }
        return false;
    }
}
