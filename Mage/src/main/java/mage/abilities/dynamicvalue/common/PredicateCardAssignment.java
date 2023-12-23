package mage.abilities.dynamicvalue.common;

import mage.abilities.dynamicvalue.RoleAssignment;
import mage.cards.Card;
import mage.filter.predicate.Predicate;
import mage.game.Game;

import java.util.Set;
import java.util.stream.Collectors;

public class PredicateCardAssignment extends RoleAssignment<Predicate<? super Card>> {

    public PredicateCardAssignment(Predicate<? super Card>... predicates) {
        super(predicates);
    }

    @Override
    protected Set<Predicate<? super Card>> makeSet(Card card, Game game) {
        return attributes
                .stream()
                .filter(predicate -> predicate.apply(card, game))
                .collect(Collectors.toSet());
    }
}
