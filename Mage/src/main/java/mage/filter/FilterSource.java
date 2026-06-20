
package mage.filter;

import mage.MageObject;
import mage.cards.Card;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.game.command.CommandObject;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;

/**
 * @author Susucr
 */
public class FilterSource extends FilterObject<MageObject> {

    public FilterSource() {
        super("source");
    }

    public FilterSource(String name) {
        super(name);
    }

    private FilterSource(final FilterSource filter) {
        super(filter);
    }

    @Override
    public FilterSource copy() {
        return new FilterSource(this);
    }

    @Override
    public FilterSource add(ObjectSourcePlayerPredicate predicate) {
        // verify checks
        // A source can be a lot of different things, so a variety of predicates can be fed here
        Predicates.makeSurePredicateCompatibleWithFilter(predicate, Permanent.class, Card.class, StackObject.class, CommandObject.class);
        this.addExtra(predicate);
        return this;
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return object instanceof Permanent
                || object instanceof Card
                || object instanceof StackObject
                || object instanceof CommandObject;
    }
}
