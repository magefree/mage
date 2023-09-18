package mage.collation;

import mage.util.RandomUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A class for shuffling a list by choosing a random starting point and looping through it
 *
 * @author TheElk801
 */
public class Rotater<T> {

    private final List<T> items;
    private int position;

    public Rotater(T item) {
        this(true, item);
    }

    public Rotater(T item1, T item2) {
        this(true, item1, item2);
    }

    public Rotater(boolean keepOrder, T... items) {
        if (keepOrder) {
            this.items = Arrays.asList(items);
            this.position = RandomUtil.nextInt(this.items.size());
        } else {
            this.items = new ArrayList<T>();
            Collections.addAll(this.items, items);
            Collections.shuffle(this.items, RandomUtil.getRandom());
            this.position = 0;
        }
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
}
