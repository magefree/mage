
package mage.abilities.condition.common;

import java.util.EnumSet;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.cards.Card;
import mage.constants.CardType;
import mage.game.Game;
import mage.players.Player;

/**
 *
 *
 * @author fireshoes
 */
public enum DeliriumCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            EnumSet<CardType> foundCardTypes = EnumSet.noneOf(CardType.class);
            for (Card card : controller.getGraveyard().getCards(game)) {
                foundCardTypes.addAll(card.getCardType());
            }
            return foundCardTypes.size() >= 4;
        }
        return false;
    }

    @Override
    public String toString() {
        return "if there are four or more card types among cards in your graveyard";
    }

}
