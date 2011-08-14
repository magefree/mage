/**
 * BasicProperty.java
 * 
 * Created on 16.07.2010
 */

package org.mage.plugins.card.dl.beans.properties.basic;


import org.mage.plugins.card.dl.beans.properties.AbstractProperty;


/**
 * The class BasicProperty.
 * 
 * @version V0.0 16.07.2010
 * @author Clemens Koza
 */
public class BasicProperty<T> extends AbstractProperty<T> {
    private T value;
    
    public BasicProperty() {
        this(null);
    }
    
    public BasicProperty(T initialValue) {
        value = initialValue;
    }
    
    public void setValue(T value) {
        this.value = value;
    }
    
    public T getValue() {
        return value;
    }
}
