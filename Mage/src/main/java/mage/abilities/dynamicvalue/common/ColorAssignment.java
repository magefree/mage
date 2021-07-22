package mage.abilities.dynamicvalue.common;

import mage.abilities.dynamicvalue.RoleAssignment;
import mage.cards.Card;
import mage.game.Game;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ColorAssignment extends RoleAssignment<String> {

    public ColorAssignment(String... colors) {
        super(colors);
    }

    @Override
    protected Set<String> makeSet(Card card, Game game) {
        return Arrays.stream(card.getColor(game).toString().split(""))
                .filter(attributes::contains)
                .collect(Collectors.toSet());
    }
}
