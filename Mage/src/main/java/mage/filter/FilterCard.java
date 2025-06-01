package mage.filter;

import mage.cards.Card;
import mage.constants.TargetController;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Works with cards only. For objects like commanders you must override your canTarget method.
 *
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public class FilterCard extends FilterObject<Card> {

    private static final long serialVersionUID = 1L;

    public FilterCard() {
        super("card");
    }

    public FilterCard(String name) {
        super(name);
    }

    protected FilterCard(final FilterCard filter) {
        super(filter);
    }

    @Override
    public FilterCard copy() {
        return new FilterCard(this);
    }

    public boolean match(Card card, UUID playerId, Game game) {
        return match(card, playerId, null, game);
    }

    public Set<Card> filter(Set<Card> cards, Game game) {
        return cards.stream().filter(card -> match(card, game)).collect(Collectors.toSet());
    }

    public static void checkPredicateIsSuitableForCardFilter(Predicate predicate) {
        // card filter can't contain controller predicate (only permanents on battlefield and StackObjects have controller)
        List<Predicate> list = new ArrayList<>();
        Predicates.collectAllComponents(predicate, list);
        if (list.stream().anyMatch(TargetController.ControllerPredicate.class::isInstance)) {
            throw new IllegalArgumentException("Wrong code usage: card filter doesn't support controller predicate");
        }
    }


    public FilterCard withMessage(String message) {
        this.setMessage(message);
        return this;
    }

    @Override
    public void add(ObjectSourcePlayerPredicate predicate) {
        // verify checks
        checkPredicateIsSuitableForCardFilter(predicate);
        Predicates.makeSurePredicateCompatibleWithFilter(predicate, Card.class);
        this.addExtra(predicate);
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return object instanceof Card;
    }
}
