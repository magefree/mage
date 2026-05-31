
package mage.filter;

import mage.MageObject;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;

/**
 * // TODO: migrate all FilterObject to more specific ones, then remove this class?
 *
 * @param <E>
 * @author North
 */
public class FilterObject<E extends MageObject> extends FilterImpl<E> {

    public FilterObject(String name) {
        super(name);
    }

    protected FilterObject(final FilterObject<E> filter) {
        super(filter);
    }

    @Override
    public FilterObject<E> copy() {
        return new FilterObject(this);
    }

    @Override
    public FilterObject<E> add(ObjectSourcePlayerPredicate predicate) {
        // verify checks
        Predicates.makeSurePredicateCompatibleWithFilter(predicate, MageObject.class);
        this.addExtra(predicate);
        return this;
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return object instanceof MageObject;
    }
}
