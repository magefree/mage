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

    void addPropertyChangeListener(PropertyChangeListener listener);

    void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);
}
