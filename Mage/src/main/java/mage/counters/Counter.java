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
package mage.counters;

import java.io.Serializable;
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
     * Increases the {@code count} by 1
     */
    public void increase() {
        count++;
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Counter counter = (Counter) o;

        return count == counter.count && !(name != null ? !name.equals(counter.name) : counter.name != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + count;
        return result;
    }
}
