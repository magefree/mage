package mage.collation;

import mage.util.RandomUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A class for shuffling a list by choosing a random starting point and looping through it
 *
 * @author TheElk801
 */
public class Rotater<T> {

    protected final List<T> items;
    private final boolean keepOrder;
    private int position = 0;

    public Rotater(T item) {
        this(true, item);
    }

    public Rotater(T item1, T item2) {
        this(true, item1, item2);
    }

    public Rotater(boolean keepOrder, T... items) {
        this.items = Arrays.asList(items);
        this.keepOrder = keepOrder;
    }

    public int iterate() {
        int i = position;
        position++;
        position %= items.size();
        return i;
    }

    public T getNext() {
        return items.get(iterate());
    }

    public void shuffle() {
        position = RandomUtil.nextInt(items.size());
        if (!keepOrder) {
            Collections.shuffle(items, RandomUtil.getRandom());
        }
    }
}
