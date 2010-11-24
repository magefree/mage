/**
 * BasicProperty.java
 * 
 * Created on 16.07.2010
 */

package org.mage.plugins.card.dl.beans.properties.bound;


import java.beans.PropertyChangeSupport;

import org.mage.plugins.card.dl.beans.properties.AbstractProperty;
import org.mage.plugins.card.dl.beans.properties.Property;


/**
 * The class BasicProperty.
 * 
 * @version V0.0 16.07.2010
 * @author Clemens Koza
 */
public class BoundProperty<T> extends AbstractProperty<T> {
    private PropertyChangeSupport s;
    private String                name;
    private Property<T>           property;
    
    public BoundProperty(PropertyChangeSupport s, String name, Property<T> property) {
        if(property == null) throw new IllegalArgumentException();
        this.s = s;
        this.name = name;
        this.property = property;
        
    }
    
    public void setValue(T value) {
        T old = getValue();
        property.setValue(value);
        s.firePropertyChange(name, old, getValue());
    }
    
    public T getValue() {
        return property.getValue();
    }
}
