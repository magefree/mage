
package mage.counters;

import java.io.Serializable;
import java.util.Objects;

import org.apache.log4j.Logger;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Counter implements Serializable {

    private static final Logger logger = Logger.getLogger(Counter.class);

    protected final String name;
    protected int count;

    /**
     * Creates a {@link Counter} with the provided {@code name} and a default
     * value of 1
     *
     * @param name the name of this counter.
     */
    public Counter(final String name) {
        this.name = name;
        this.count = 1;
    }

    /**
     * Creates a {@link Counter} with the provided {@code name} and
     * {@code count}
     *
     * @param name the name of this counter.
     * @param count the value of this counter.
     */
    public Counter(final String name, final int count) {
        this.name = name;
        this.count = count;
    }

    /**
     * Creates a {@link Counter} from an existing {@link Counter} object.
     *
     * @param counter the {@link Counter} to create a copy from.
     */
    public Counter(final Counter counter) {
        this.name = counter.name;
        this.count = counter.count;
    }

    /**
     * Adds the passed in {@code amount} to the {@code count}
     *
     * @param amount the value to add to the {@code count}
     */
    public void add(int amount) {
        count += amount;
    }

    /**
     * Decreases the {@code count} by one. Will not allow the count to be less
     * than 0. If an attempt is made to make the count be less than zero, the
     * call will be logged.
     */
    public void decrease() {
        if (count > 0) {
            count--;
        } else {
            logger.warn("An attempt was made to set the counter '" + name
                    + "' to less than 0. Setting to 0.");
        }
    }

    /**
     * Decreases the {@code count} by the passed in {@code amount}. Will not
     * allow the count to be less than 0. If an attempt is made to make the
     * count be less than zero, the call will be logged.
     *
     * @param amount
     */
    public void remove(int amount) {
        if (count >= amount) {
            count -= amount;
        } else {
            logger.warn("An attempt was made to set the counter '" + name
                    + "' to less than 0. Setting to 0.");
            count = 0;
        }
    }

    /**
     * Returns the name of this {@link Counter}
     *
     * @return the name of this {@link Counter}
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the count of this {@link Counter}
     *
     * @return the count of this {@link Counter}
     */
    public int getCount() {
        return count;
    }

    /**
     * Returns a deep copy of this object.
     *
     * @return a deep copy of this object.
     */
    public Counter copy() {
        return new Counter(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Counter that = (Counter) obj;
        return this.count == that.count
                && Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, count);
    }
}
