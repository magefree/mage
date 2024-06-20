package mage.abilities.assignment.common;

import mage.abilities.assignment.RoleAssignment;
import mage.cards.Card;
import mage.constants.MagicType;
import mage.game.Game;

import java.util.Set;
import java.util.stream.Collectors;

public class TypeAssignment extends RoleAssignment<MagicType> {

    public TypeAssignment(MagicType... types) {
        super(types);
    }

    @Override
    protected Set<MagicType> makeSet(Card card, Game game) {
        return attributes
                .stream()
                .filter(type -> type.checkObject(card, game))
                .collect(Collectors.toSet());
    }
}
