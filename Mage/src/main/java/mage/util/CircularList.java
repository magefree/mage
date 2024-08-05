package mage.util;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Component: a thread-safe circular list, used for players list
 *
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class CircularList<E> implements List<E>, Iterable<E>, Serializable {
    //TODO: might have to make E extend Copyable

    protected final List<E> list = new ArrayList<>();

    protected final ReentrantLock lock = new ReentrantLock();

    protected int modCount;
    protected int index;

    public CircularList() {
    }

    public CircularList(final CircularList<E> cList) {
        this.modCount = cList.modCount;
        for (E entry : cList.list) {
            this.list.add((E) entry);
        }
        this.index = cList.index;
    }

    public CircularList<E> copy() {
        return new CircularList<>(this);
    }

    /**
     * Insert new element at the current position (make new element as current)
     */
    @Override
    public boolean add(E element) {
        add(this.index, element);
        return true;
    }

    /**
     * Insert new element at selected position (keep old element as current)
     */
    @Override
    public void add(int index, E element) {
        lock.lock();
        try {
            list.add(index, element);
            modCount++;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Set current position to an element
     *
     * @return false on unknown element
     */
    public boolean setCurrent(E element) {
        if (list.contains(element)) {
            this.index = list.indexOf(element);
            return true;
        }
        return false;
    }

    /**
     * Find current element
     */
    public E get() {
        return get(this.index);
    }

    /**
     * Find element at searching position
     */
    @Override
    public E get(int index) {
        if (list.size() > this.index) {
            return list.get(this.index);
        }
        return null;
    }

    /**
     * Returns the next element in the list. Will loop around to the beginning
     * of the list if the current element is the last.
     *
     * @return the next element in the list
     */
    public E getNext() {
        return list.get(incrementPointer());
    }

    /**
     * Returns the previous element in the list. Will loop around to the end of
     * the list if the current element is the first.
     *
     * @return the previous element in the list
     */
    public E getPrevious() {
        return list.get(decrementPointer());
    }

    /**
     * Removes the current element from the list
     *
     * @return true on successfully removed
     */
    public boolean remove() {
        return this.remove(get());
    }

    /**
     * Removes element from searching position
     *
     * @return true on successfully removed
     */
    @Override
    public E remove(int index) {
        lock.lock();
        try {
            E ret = list.remove(index);
            fixInvalidPointer();
            modCount++;
            return ret;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean remove(Object o) {
        lock.lock();
        try {
            boolean ret = list.remove(o);
            fixInvalidPointer();
            modCount++;
            return ret;
        } finally {
            lock.unlock();
        }
    }

    private int incrementPointer() {
        lock.lock();
        try {
            index = findNextListPointer(index);
            return index;
        } finally {
            lock.unlock();
        }
    }

    private int findNextListPointer(int currentIndex) {
        currentIndex++;
        if (currentIndex >= list.size()) {
            currentIndex = 0;
        }
        return currentIndex;
    }

    private int decrementPointer() {
        lock.lock();
        try {
            index = findPrevListPointer(index);
            return index;
        } finally {
            lock.unlock();
        }
    }

    private int findPrevListPointer(int currentIndex) {
        currentIndex--;
        if (currentIndex < 0) {
            currentIndex = list.size() - 1;
        }
        return currentIndex;
    }

    /**
     * This method should only be called from a locked method thus it is not
     * necessary to lock from this method
     */
    private void fixInvalidPointer() {
        if (index > list.size()) {
            index = list.size() - 1;
        } else if (index < 0) {
            index = 0;
        }
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Object[] toArray() {
        return toArray(new UUID[0]);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        T[] res = list.toArray(a);

        // sort due default order
        Iterator<E> iter = this.iterator();
        int insertIndex = 0;
        while (iter.hasNext()) {
            res[insertIndex] = (T) iter.next();
            insertIndex++;
        }

        return res;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return this.addAll(this.index, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        lock.lock();
        try {
            modCount++;
            return list.addAll(index, c);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        lock.lock();
        try {
            boolean ret = list.removeAll(c);
            modCount++;
            fixInvalidPointer();
            return ret;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        lock.lock();
        try {
            boolean ret = list.retainAll(c);
            modCount++;
            fixInvalidPointer();
            return ret;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void clear() {
        lock.lock();
        try {
            list.clear();
            modCount++;
            index = 0;
        } finally {
            lock.unlock();
        }
    }

    public E set(E element) {
        return this.set(this.index, element);
    }

    @Override
    public E set(int index, E element) {
        lock.lock();
        try {
            modCount++;
            return list.set(index, element);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    @Override
    public Iterator<E> iterator() {
        return new CircularIterator<>();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new CircularListIterator<>();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new CircularListIterator<>(index);
    }

    private class CircularIterator<E> implements Iterator<E> {

        int cursor;
        final int lastIndex;
        final int curModCount;
        boolean hasMoved = false;

        private CircularIterator() {
            curModCount = modCount;
            cursor = index;
            lastIndex = cursor;
        }

        @Override
        public boolean hasNext() {
            if (!hasMoved && size() > 0) {
                return true;
            }
            return cursor != lastIndex;
        }

        @Override
        public E next() {
            if (!this.hasNext()) {
                throw new IllegalStateException();
            }
            if (curModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            E data = (E) list.get(cursor);
            cursor = findNextListPointer(cursor);
            hasMoved = true;
            return data;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

    private class CircularListIterator<E> implements ListIterator<E> {

        int cursor;
        final int lastIndex;
        final int firstIndex;
        final int curModCount;
        boolean hasMoved = false;

        private CircularListIterator() {
            this(index);
        }

        private CircularListIterator(int index) {
            curModCount = modCount;
            cursor = index;
            firstIndex = index;
            lastIndex = index;
        }

        @Override
        public boolean hasNext() {
            if (!hasMoved && size() > 0) {
                return true;
            }
            return cursor != lastIndex;
        }

        @Override
        public E next() {
            if (!this.hasNext()) {
                throw new IllegalStateException();
            }
            if (curModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            E data = (E) list.get(cursor);
            cursor = findNextListPointer(cursor);
            hasMoved = true;
            return data;
        }

        @Override
        public boolean hasPrevious() {
            if (!hasMoved && size() > 0) {
                return true;
            }
            return cursor != firstIndex;
        }

        @Override
        public E previous() {
            if (!this.hasPrevious()) {
                throw new IllegalStateException();
            }
            if (curModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            cursor = findPrevListPointer(cursor);
            hasMoved = true;
            return (E) list.get(cursor);
        }

        @Override
        public int nextIndex() {
            if (this.hasNext()) {
                return findNextListPointer(cursor);
            }
            return list.size();
        }

        @Override
        public int previousIndex() {
            if (this.hasPrevious()) {
                return findPrevListPointer(cursor);
            }
            return -1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void set(E arg0) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void add(E arg0) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

}
