

package mage.view;

import java.io.Serializable;
import mage.counters.Counter;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CounterView implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int count;

    public CounterView(Counter counter) {
        this.name = counter.getName();
        this.count = counter.getCount();
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (!(other instanceof CounterView)) {
            return false;
        }
        CounterView oth = (CounterView)other;
        return
                (count == oth.count) &&
                (name.equals(oth.name));
    }
}
