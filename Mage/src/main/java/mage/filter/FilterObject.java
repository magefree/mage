
package mage.filter;

import mage.MageObject;

/**
 *
 * @author North
 * @param <E>
 */
public class FilterObject<E extends MageObject> extends FilterImpl<E> {

    @Override
    public FilterObject<E> copy() {
        return new FilterObject<>(this);
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return object instanceof MageObject;
    }

    public FilterObject(String name) {
        super(name);
    }

    protected FilterObject(final FilterObject<E> filter) {
        super(filter);
    }
}
