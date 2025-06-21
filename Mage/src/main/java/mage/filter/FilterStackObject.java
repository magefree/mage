package mage.filter;

import mage.cards.Card;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

/**
 * @author North
 */
public class FilterStackObject extends FilterObject<StackObject> {


    public FilterStackObject() {
        this("spell or ability");
    }

    public FilterStackObject(String name) {
        super(name);
    }

    protected FilterStackObject(final FilterStackObject filter) {
        super(filter);
    }

    @Override
    public FilterStackObject copy() {
        return new FilterStackObject(this);
    }

    @Override
    public final FilterStackObject add(ObjectSourcePlayerPredicate predicate) {
        // verify checks
        // Spell implements Card interface, so it can use some default predicates like owner
        Predicates.makeSurePredicateCompatibleWithFilter(predicate, StackObject.class, Spell.class, Card.class);
        this.addExtra(predicate);
        return this;
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return object instanceof StackObject
                || object instanceof Card; // TODO: investigate. Is sometimes used for checking a spell's characteristic before cast
    }
}
