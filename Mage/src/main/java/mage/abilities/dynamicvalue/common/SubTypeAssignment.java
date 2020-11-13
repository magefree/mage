package mage.abilities.dynamicvalue.common;

import mage.abilities.dynamicvalue.RoleAssignment;
import mage.cards.Card;
import mage.constants.SubType;
import mage.game.Game;

import java.util.Set;
import java.util.stream.Collectors;

public class SubTypeAssignment extends RoleAssignment<SubType> {

    public SubTypeAssignment(SubType... subTypes) {
        super(subTypes);
    }

    @Override
    protected Set<SubType> makeSet(Card card, Game game) {
        return attributes
                .stream()
                .filter(subType -> card.hasSubtype(subType, game))
                .collect(Collectors.toSet());
    }
}
