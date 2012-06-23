/**
 * AbstractBoundBean.java
 * 
 * Created on 24.08.2010
 */

package org.mage.plugins.card.dl.beans;


import java.beans.PropertyChangeListener;


/**
 * The class AbstractBoundBean.
 * 
 * @version V0.0 24.08.2010
 * @author Clemens Koza
 */
public abstract class AbstractBoundBean implements BoundBean {
    protected PropertyChangeSupport s = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        s.addPropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        s.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        s.removePropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        s.removePropertyChangeListener(propertyName, listener);
    }
}
