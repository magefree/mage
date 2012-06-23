/**
 * Property.java
 * 
 * Created on 24.08.2010
 */

package org.mage.plugins.card.dl.beans.properties;


/**
 * The class Property.
 * 
 * @version V0.0 24.08.2010
 * @author Clemens Koza
 */
public interface Property<T> {
    public void setValue(T value);

    public T getValue();

    /**
     * A property's hash code is its value's hashCode, or {@code null} if the value is null.
     */
    @Override
    public int hashCode();

    /**
     * Two properties are equal if their values are equal.
     */
    @Override
    public boolean equals(Object obj);
}
