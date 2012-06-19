/**
 * PropertyChangeSupport.java
 * 
 * Created on 16.07.2010
 */

package org.mage.plugins.card.dl.beans;




/**
 * The class PropertyChangeSupport.
 * 
 * @version V0.0 16.07.2010
 * @author Clemens Koza
 */
public class PropertyChangeSupport extends java.beans.PropertyChangeSupport {
    private static final long serialVersionUID = -4241465377828790076L;

    private Object            sourceBean;

    public PropertyChangeSupport(Object sourceBean) {
        super(sourceBean);
        this.sourceBean = sourceBean;
    }

    public Object getSourceBean() {
        return sourceBean;
    }
}
