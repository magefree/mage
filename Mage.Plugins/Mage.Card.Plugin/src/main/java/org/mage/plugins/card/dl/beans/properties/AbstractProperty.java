/**
 * AbstractProperty.java
 * 
 * Created on 24.08.2010
 */

package org.mage.plugins.card.dl.beans.properties;


import static java.lang.String.*;


/**
 * The class AbstractProperty.
 * 
 * @version V0.0 24.08.2010
 * @author Clemens Koza
 */
public abstract class AbstractProperty<T> implements Property<T> {
    @Override
    public int hashCode() {
        T value = getValue();
        return value == null? 0:value.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Property<?>)) return false;
        Object value = getValue();
        Object other = ((Property<?>) obj).getValue();
        return value == other || (value != null && value.equals(other));
    }
    
    @Override
    public String toString() {
        return valueOf(getValue());
    }
}
