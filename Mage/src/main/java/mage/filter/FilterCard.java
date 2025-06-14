package mage.filter;

import mage.cards.Card;
import mage.constants.TargetController;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;

import java.util.ArrayList;
import java.util.List;

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
    public FilterCard add(ObjectSourcePlayerPredicate predicate) {
        // verify checks
        checkPredicateIsSuitableForCardFilter(predicate);
        Predicates.makeSurePredicateCompatibleWithFilter(predicate, Card.class);
        this.addExtra(predicate);
        return this;
    }

    @Override
    public boolean checkObjectClass(Object object) {
        // TODO: investigate if we can/should exclude Permanent here.
        //       as it does extend Card (if so do cleanup the
        //       MultiFilterImpl that match Permanent and Card/Spell)
        return object instanceof Card;
    }
}
