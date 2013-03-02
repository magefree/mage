/**
 * ListenableCollections.java
 * 
 * Created on 25.04.2010
 */

package org.mage.plugins.card.dl.beans.collections;


import java.io.Serializable;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.AbstractSequentialList;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;


/**
 * The class ListenableCollections supports applications that need to listen for modifications on different
 * collections. Unlike most listener models, a listenable collection does not have public methods for adding or
 * removing listeners. Instead, the wrapping methods take exactly one listener, because listening to collections is
 * low-level. That single listener will usually manage multiple high-level listeners.
 * 
 * @version V0.0 25.04.2010
 * @author Clemens Koza
 */
public final class ListenableCollections {
    private ListenableCollections() {}

    public static <E> List<E> listenableList(List<E> list, ListListener<E> listener) {
        if (list instanceof RandomAccess) {
            return new ListenableList<E>(list, listener);
        } else {
            return new ListenableSequentialList<E>(list, listener);
        }
    }

    public static <E> Set<E> listenableSet(Set<E> set, SetListener<E> listener) {
        return new ListenableSet<E>(set, listener);
    }

    public static <K, V> Map<K, V> listenableMap(Map<K, V> map, MapListener<K, V> listener) {
        return new ListenableMap<K, V>(map, listener);
    }

    public interface ListListener<E> extends Serializable {
        /**
         * Notified after a value was added to the list.
         */
        void add(int index, E newValue);

        /**
         * Notified after a value in the list was changed.
         */
        void set(int index, E oldValue, E newValue);

        /**
         * Notified after a value was removed from the list.
         */
        void remove(int index, E oldValue);
    }

    private static class ListenableList<E> extends AbstractList<E> implements RandomAccess, Serializable {
        private static final long serialVersionUID = 8622608480525537692L;

        private List<E>           delegate;
        private ListListener<E>   listener;

        public ListenableList(List<E> delegate, ListListener<E> listener) {
            this.delegate = delegate;
            this.listener = listener;
        }

        @Override
        public void add(int index, E e) {
            delegate.add(index, e);
            listener.add(index, e);
        }

        @Override
        public E set(int index, E element) {
            E e = delegate.set(index, element);
            listener.set(index, e, element);
            return e;
        }

        @Override
        public E remove(int index) {
            E e = delegate.remove(index);
            listener.remove(index, e);
            return e;
        }

        @Override
        public E get(int index) {
            return delegate.get(index);
        }

        @Override
        public int size() {
            return delegate.size();
        }
    }

    private static class ListenableSequentialList<E> extends AbstractSequentialList<E> implements Serializable {
        private static final long serialVersionUID = 3630474556578001885L;

        private List<E>           delegate;
        private ListListener<E>   listener;

        public ListenableSequentialList(List<E> delegate, ListListener<E> listener) {
            this.delegate = delegate;
            this.listener = listener;
        }

        @Override
        public ListIterator<E> listIterator(final int index) {
            return new ListIterator<E>() {
                private final ListIterator<E> it = delegate.listIterator(index);
                private int                   lastIndex;
                private E                     lastValue;

                @Override
                public boolean hasNext() {
                    return it.hasNext();
                }

                @Override
                public boolean hasPrevious() {
                    return it.hasPrevious();
                }

                @Override
                public E next() {
                    lastIndex = it.nextIndex();
                    lastValue = it.next();
                    return lastValue;
                }

                @Override
                public int nextIndex() {
                    return it.nextIndex();
                }

                @Override
                public E previous() {
                    lastIndex = it.previousIndex();
                    lastValue = it.previous();
                    return lastValue;
                }

                @Override
                public int previousIndex() {
                    return it.previousIndex();
                }

                @Override
                public void add(E o) {
                    it.add(o);
                    listener.add(previousIndex(), o);
                }

                @Override
                public void set(E o) {
                    it.set(o);
                    listener.set(lastIndex, lastValue, o);
                }

                @Override
                public void remove() {
                    it.remove();
                    listener.remove(lastIndex, lastValue);
                }
            };
        }

        @Override
        public int size() {
            return delegate.size();
        }
    }

    public static interface SetListener<E> extends Serializable {
        /**
         * Notified after a value was added to the set.
         */
        public void add(E newValue);

        /**
         * Notified after a value was removed from the set.
         */
        public void remove(E oldValue);
    }

    private static class ListenableSet<E> extends AbstractSet<E> implements Serializable {
        private static final long serialVersionUID = 7728087988927063221L;

        private Set<E>            delegate;
        private SetListener<E>    listener;

        public ListenableSet(Set<E> set, SetListener<E> listener) {
            delegate = set;
            this.listener = listener;
        }

        @Override
        public boolean contains(Object o) {
            return delegate.contains(o);
        }

        @Override
        public boolean add(E o) {
            boolean b = delegate.add(o);
            if (b) {
                listener.add(o);
            }
            return b;
        };

        @SuppressWarnings("unchecked")
        @Override
        public boolean remove(Object o) {
            boolean b = delegate.remove(o);
            if (b) {
                listener.remove((E) o);
            }
            return b;
        }

        @Override
        public Iterator<E> iterator() {
            return new Iterator<E>() {
                private final Iterator<E> it = delegate.iterator();
                private boolean           hasLast;
                private E                 last;

                @Override
                public boolean hasNext() {
                    return it.hasNext();
                }

                @Override
                public E next() {
                    last = it.next();
                    hasLast = true;
                    return last;
                }

                @Override
                public void remove() {
                     if(!hasLast) {
                        throw new IllegalStateException();
                    }
                    it.remove();
                    listener.remove(last);
                }
            };
        }

        @Override
        public int size() {
            return delegate.size();
        }
    }

    public static interface MapListener<K, V> extends Serializable {
        /**
         * Notified after a value was put into the map.
         */
        public void put(K key, V newValue);

        /**
         * Notified after a value in the map was changed.
         */
        public void set(K key, V oldValue, V newValue);

        /**
         * Notified after a value was removed from the map.
         */
        public void remove(K key, V oldValue);
    }

    private static class ListenableMap<K, V> extends AbstractMap<K, V> implements Serializable {
        private static final long serialVersionUID = 4032087477448965103L;

        private Map<K, V>         delegate;
        private MapListener<K, V> listener;
        private Set<Entry<K, V>>  entrySet;

        public ListenableMap(Map<K, V> map, MapListener<K, V> listener) {
            this.listener = listener;
            delegate = map;
            entrySet = new EntrySet();
        }

        @Override
        public V put(K key, V value) {
            if(delegate.containsKey(key)) {
                V old = delegate.put(key, value);
                listener.set(key, old, value);
                return old;
            } else {
                delegate.put(key, value);
                listener.put(key, value);
                return null;
            }

        }

        @SuppressWarnings("unchecked")
        @Override
        public V remove(Object key) {
            if(delegate.containsKey(key)) {
                V old = delegate.remove(key);
                listener.remove((K) key, old);
                return old;
            } else {
                return null;
            }
        }

        @Override
        public V get(Object key) {
            return delegate.get(key);
        }

        @Override
        public boolean containsKey(Object key) {
            return delegate.containsKey(key);
        }

        @Override
        public boolean containsValue(Object value) {
            return delegate.containsValue(value);
        }

        @Override
        public Set<java.util.Map.Entry<K, V>> entrySet() {
            return entrySet;
        }

        private final class EntrySet extends AbstractSet<Entry<K, V>> implements Serializable {
            private static final long      serialVersionUID = -780485106953107075L;
            private final Set<Entry<K, V>> delegate         = ListenableMap.this.delegate.entrySet();

            @Override
            public int size() {
                return delegate.size();
            }

            @Override
            public Iterator<Entry<K, V>> iterator() {
                return new Iterator<Entry<K, V>>() {
                    private final Iterator<Entry<K, V>> it = delegate.iterator();
                    private boolean                     hasLast;
                    private Entry<K, V>                 last;

                    @Override
                    public boolean hasNext() {
                        return it.hasNext();
                    }

                    @Override
                    public Entry<K, V> next() {
                        last = it.next();
                        hasLast = true;
                        return last;
                    }

                    @Override
                    public void remove() {
                         if(!hasLast) {
                            throw new IllegalStateException();
                        }
                        hasLast = false;
                        it.remove();
                        listener.remove(last.getKey(), last.getValue());
                    }
                };
            }
        }
    }
}
