package mage.abilities.dynamicvalue.common;

import mage.abilities.dynamicvalue.RoleAssignment;
import mage.cards.Card;
import mage.game.Game;

import java.util.HashSet;
import java.util.Set;

public class ColorAssignment extends RoleAssignment<String> {

    public ColorAssignment() {
        super("W", "U", "B", "R", "G");
    }

    @Override
    protected Set<String> makeSet(Card card, Game game) {
        Set<String> strings = new HashSet<>();
        for (char c : card.getColor(game).toString().toCharArray()) {
            strings.add("" + c);
        }
        return strings;
    }
}
