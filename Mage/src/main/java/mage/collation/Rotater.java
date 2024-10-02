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
    private int position, stripeLen = 0, stripeWidth, stripeDepth;

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

//  for striped collation
    public Rotater(int sLen, T... items) {
//          should there be an error check?
//          assert ( items.size() % sLen ) == 0;
            this.stripeLen = sLen;
            this.items = Arrays.asList(items);
            this.position = RandomUtil.nextInt(this.items.size());
            this.stripeWidth= this.nextWidth();
            this.stripeDepth= 1+ RandomUtil.nextInt(this.stripeWidth);
    }

// choose a stripe width between 2 & 5 inclusive
// ToDo when data available: enable different widths to have different likelihoods
    private int nextWidth() {
        return 2+ RandomUtil.nextInt(4);
    }
    
    public int iterate() {
        int i = position;
        if( stripeLen >0 ){
          if( stripeDepth < stripeWidth ){
            ++stripeDepth;
            position -= 10;
            if (position <0 ){
              position += items.size();
            }
          }else{
            stripeDepth= 1;
            if( (position % stripeLen) >0 ){
              position += stripeLen * (stripeWidth-1);
              position %= items.size();
            }else{
              this.stripeWidth= this.nextWidth();
            }
            position -= 1;
            if (position <0 ){
              position += items.size();
            }
          }
        }else{
          position++;
          position %= items.size();
        }
        return i;
    }

    public T getNext() {
        return items.get(iterate());
    }
}
