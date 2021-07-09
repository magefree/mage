package mage.abilities.dynamicvalue.common;

import mage.abilities.dynamicvalue.RoleAssignment;
import mage.cards.Card;
import mage.constants.CardType;
import mage.game.Game;

import java.util.Set;
import java.util.stream.Collectors;

public class CardTypeAssignment extends RoleAssignment<CardType> {

    public CardTypeAssignment(CardType... subTypes) {
        super(subTypes);
    }

    @Override
    protected Set<CardType> makeSet(Card card, Game game) {
        return attributes
                .stream()
                .filter(subType -> card.getCardType(game).contains(subType))
                .collect(Collectors.toSet());
    }
}
