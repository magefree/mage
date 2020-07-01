package mage.abilities.dynamicvalue.common;

import java.util.HashSet;
import java.util.Set;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.PermanentToken;
import mage.players.Player;

/**
 * @author JayDi85
 */
public enum CardTypesInGraveyardCount implements DynamicValue {

    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player controller = game.getPlayer(sourceAbility.getControllerId());
        if (controller != null) {
            Set<CardType> foundCardTypes = new HashSet<>();
            for (Card card : controller.getGraveyard().getCards(game)) {
                // 4/8/2016 In some rare cases, you can have a token or a copy of a spell in your graveyard at the moment that an object’s delirium ability counts the card types among cards in your graveyard, before that token or copy ceases to exist. Because tokens and copies of spells are not cards, even if they are copies of cards, their types will never be counted.
                if (!card.isCopy() && !(card instanceof PermanentToken)) {
                    foundCardTypes.addAll(card.getCardType());
                }
            }
            return foundCardTypes.size();
        }
        return 0;
    }

    @Override
    public CardTypesInGraveyardCount copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "the number of opponents you attacked this turn";
    }
}
