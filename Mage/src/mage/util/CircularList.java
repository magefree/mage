/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.locks.ReentrantLock;

/**
 * a thread-safe circular list
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CircularList<E> implements List<E>, Iterable<E>, Serializable {
    //TODO: might have to make E extend Copyable

    protected List<E> list = new ArrayList<E>();

    protected final ReentrantLock lock = new ReentrantLock();

    protected int modCount;
    protected int index;

    public CircularList() {}

    public CircularList(final CircularList<E> cList) {
        this.modCount = cList.modCount;
        for (E entry: cList.list) {
            this.list.add((E)entry);
        }
        this.index = cList.index;
    }

    public CircularList<E> copy() {
        return new CircularList<E>(this);
    }

    /**
     *  Inserts an element into the current position
     */
    @Override
    public boolean add(E e) {
        list.add(this.index, e);
        return true;
    }

    @Override
    public void add(int index, E element) {
        lock.lock();
        try {
            list.add(index, element);
            modCount++;
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * 
     * @param e the element to set as current
     * @return true if element e exists and index was set
     */
    public boolean setCurrent(E e) {
        if (list.contains(e)) {
            this.index = list.indexOf(e);
            return true;
        }
        return false;
    }

    /**
     *  Retrieves the element at the current position
     */
    public E get() {
        return list.get(this.index);
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    /**
     * Returns the next element in the list.  Will loop around to the beginning
     * of the list if the current element is the last.
     * 
     * @return the next element in the list
     */
    public E getNext() {
        return list.get(incrementPointer());
    }

    /**
     * Returns the previous element in the list.  Will loop around to the end
     * of the list if the current element is the first.
     *
     * @return the previous element in the list
     */
    public E getPrevious() {
        return list.get(decrementPointer());
    }

    /**
     * Removes the current element from the list
     *
     * @return <tt>true</tt> is the item was successfully removed
     */
    public boolean remove() {
        return this.remove(get());
    }

    @Override
    public E remove(int index) {
        lock.lock();
        try {
            E ret = list.remove(index);
            checkPointer();
            modCount++;
            return ret;
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public boolean remove(Object o) {
        lock.lock();
        try {
            boolean ret = list.remove(o);
            checkPointer();
            modCount++;
            return ret;
        }
        finally {
            lock.unlock();
        }
    }

    private int incrementPointer() {
        lock.lock();
        try {
            index = incrementListPointer(index);
            return index;
        }
        finally {
            lock.unlock();
        }
    }

    private int incrementListPointer(int index) {
        index++;
        if (index >= list.size())
            index = 0;
        return index;
    }

    private int decrementPointer() {
        lock.lock();
        try {
            index = decrementListPointer(index);
            return index;
        }
        finally {
            lock.unlock();
        }
    }

    private int decrementListPointer(int index) {
        index--;
        if (index < 0)
            index = list.size() - 1;
        return index;
    }

    /**
     *  This method should only be called from a locked method
     *  thus it is not necessary to lock from this method
     */
    private int checkPointer() {
        if (index > list.size()) {
            index = list.size() - 1;
        }
        else if (index < 0)
            index = 0;
        return index;
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
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    public List<E> toList() {
        return list;
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
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        lock.lock();
        try {
            boolean ret = list.removeAll(c);
            modCount++;
            checkPointer();
            return ret;
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        lock.lock();
        try {
            boolean ret = list.retainAll(c);
            modCount++;
            checkPointer();
            return ret;
        }
        finally {
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
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public E set(int index, E element) {
        lock.lock();
        try {
            modCount++;
            return list.set(index, element);
        }
        finally {
            lock.unlock();
        }
    }

    public E set(E element) {
        return this.set(this.index, element);
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
        return new CircularIterator<E>();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new CircularListIterator<E>();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new CircularListIterator<E>(index);
    }

    private class CircularIterator<E>  implements Iterator<E> {

        int cursor;
        int lastIndex;
        int curModCount;
        boolean hasMoved = false;

        private CircularIterator() {
            curModCount = modCount;
            cursor = index;
            lastIndex = cursor;
        }

        @Override
        public boolean hasNext() {
            if (!hasMoved && size() > 0)
                return true;
            return cursor != lastIndex;
        }

        @Override
        public E next() {
            if (!this.hasNext())
                throw new IllegalStateException();
            if (curModCount != modCount)
                throw new ConcurrentModificationException();
            E data = (E) list.get(cursor);
            cursor = incrementListPointer(cursor);
            hasMoved = true;
            return data;
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

    private class CircularListIterator<E>  implements ListIterator<E> {
        int cursor;
        int lastIndex;
        int firstIndex;
        int curModCount;
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

        public boolean hasNext() {
            if (!hasMoved && size() > 0)
                return true;
            return cursor != lastIndex;
        }

        public E next() {
            if (!this.hasNext())
                throw new IllegalStateException();
            if (curModCount != modCount)
                throw new ConcurrentModificationException();
            E data = (E) list.get(cursor);
            cursor = incrementListPointer(cursor);
            hasMoved = true;
            return data;
        }

        public boolean hasPrevious() {
            if (!hasMoved && size() > 0)
                return true;
            return cursor != firstIndex;
        }

        public E previous() {
            if (!this.hasPrevious())
                throw new IllegalStateException();
            if (curModCount != modCount)
                throw new ConcurrentModificationException();
            cursor = decrementListPointer(cursor);
            hasMoved = true;
            return (E) list.get(cursor);
        }

        public int nextIndex() {
            if (this.hasNext())
                return incrementListPointer(cursor);
            return list.size();
        }

        public int previousIndex() {
            if (this.hasPrevious())
                return decrementListPointer(cursor);
            return -1;
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void set(E arg0) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void add(E arg0) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }


}
