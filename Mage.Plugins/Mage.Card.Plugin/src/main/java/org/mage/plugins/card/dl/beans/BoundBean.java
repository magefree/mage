/**
 * BoundBean.java
 * 
 * Created on 24.08.2010
 */

package org.mage.plugins.card.dl.beans;


import java.beans.PropertyChangeListener;


/**
 * The class BoundBean.
 * 
 * @version V0.0 24.08.2010
 * @author Clemens Koza
 */
public interface BoundBean {
    public void addPropertyChangeListener(PropertyChangeListener listener);
    
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);
    
    public void removePropertyChangeListener(PropertyChangeListener listener);
    
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);
}
