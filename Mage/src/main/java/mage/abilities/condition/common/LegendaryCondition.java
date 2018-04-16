package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;

/**
 * @author JRHerlehy
 *         Created on 4/7/18.
 */
public enum LegendaryCondition implements Condition {

    instance;

    private static final FilterPermanent filter = new FilterPermanent("legendary creature or planeswalker");

    static {
        filter.add(
                Predicates.and(
                        new SupertypePredicate(SuperType.LEGENDARY),
                        Predicates.or(
                                new CardTypePredicate(CardType.CREATURE),
                                new CardTypePredicate(CardType.PLANESWALKER)
                        )
                )
        );
    }


    @Override
    public boolean apply(Game game, Ability source) {
        return game.getBattlefield().contains(filter, source.getControllerId(), 1, game);
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
