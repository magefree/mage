package mage.abilities.assignment.common;

import mage.abilities.assignment.RoleAssignment;
import mage.cards.Card;
import mage.game.Game;

import java.util.Set;
import java.util.stream.Collectors;

public class NameAssignment extends RoleAssignment<String> {

    public NameAssignment(String... names) {
        super(names);
    }

    @Override
    protected Set<String> makeSet(Card card, Game game) {
        return attributes
                .stream()
                .filter(s -> card.hasName(s, game))
                .collect(Collectors.toSet());
    }
}
