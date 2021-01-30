package org.mage.plugins.card.dl.beans;

import com.google.common.base.Function;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.*;
import static java.util.Arrays.asList;

/**
 * The class EventListenerList.
 *
 * @version V0.0 08.04.2010
 * @author Clemens Koza
 */
public class EventListenerList extends javax.swing.event.EventListenerList {

    private static final long serialVersionUID = -7545754245081842909L;

    /**
     * Returns an iterable over all listeners for the specified classes. the
     * listener classes are in the specified order. for every class, listeners
     * are in the reverse order of registering. A listener contained multiple
     * times (for a single or multiple classes) is only returned the first time
     * it occurs.
     *
     * @param <T>
     * @param listenerClass
     * @return
     */
    public <T extends EventListener> Iterable<T> getIterable(final Class<? extends T>... listenerClass) {
        //transform class -> iterable
        List<Iterable<T>> l = Lists.transform(asList(listenerClass), new ClassToIterableFunction<>());

        //compose iterable (use an arraylist to memoize the function's results)
        final Iterable<T> it = Iterables.concat(new ArrayList<>(l));

        //transform to singleton iterators
        return () -> new SingletonIterator<>(it.iterator());
    }

    /**
     * Returns an iterator over all listeners for the specified classes. the
     * listener classes are in the specified order. for every class, listeners
     * are in the reverse order of registering. A listener contained multiple
     * times (for a single or multiple classes) is only returned the first time
     * it occurs.
     *
     * @param <T>
     * @param listenerClass
     * @return
     */
    public <T extends EventListener> Iterator<T> getIterator(Class<T>... listenerClass) {
        return getIterable(listenerClass).iterator();
    }

    /**
     * Iterates backwards over the listeners registered for a class by using the
     * original array. The Listener runs backwards, just as listener
     * notification usually works.
     */
    private class ListenerIterator<T> extends AbstractIterator<T> {

        private final Class<? extends T> listenerClass;
        private final Object[] listeners = listenerList;
        private int index = listeners.length;

        private ListenerIterator(Class<? extends T> listenerClass) {
            this.listenerClass = listenerClass;
        }

        @Override
        @SuppressWarnings("unchecked")
        protected T computeNext() {
            for (index -= 2; index >= 0; index -= 2) {
                if (listenerClass == listeners[index]) {
                    return (T) listeners[index + 1];
                }
            }
            return endOfData();
        }
    }

    /**
     * Transforms a class to the associated listener iterator
     */
    private class ClassToIterableFunction<T> implements Function<Class<? extends T>, Iterable<T>> {

        public Iterable<T> apply(final Class<? extends T> from) {
            return () -> new ListenerIterator<>(from);
        }
    }

    /**
     * Filters the delegate iterator so that every but the first occurrence of
     * every element is ignored.
     */
    private static class SingletonIterator<T> extends AbstractIterator<T> {

        private final Iterator<T> it;
        private final HashSet<T> previous = new HashSet<>();

        public SingletonIterator(Iterator<T> it) {
            this.it = it;
        }

        @Override
        protected T computeNext() {
            while (it.hasNext()) {
                T next = it.next();
                if (previous.add(next)) {
                    return next;
                }
            }
            return endOfData();
        }
    }
}
