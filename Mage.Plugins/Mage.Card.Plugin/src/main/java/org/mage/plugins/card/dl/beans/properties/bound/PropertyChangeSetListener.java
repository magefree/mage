/**
 * PropertyChangeSetListener.java
 * 
 * Created on 16.07.2010
 */

package org.mage.plugins.card.dl.beans.properties.bound;


import java.beans.PropertyChangeEvent;
import java.util.Set;

import org.mage.plugins.card.dl.beans.PropertyChangeSupport;
import org.mage.plugins.card.dl.beans.collections.ListenableCollections.SetListener;


/**
 * The class PropertyChangeSetListener. This listener always fires events with {@link Set} -> {@link Set} as the
 * value parameters, as nonindexed collection properties. The events will be {@link SetEvent} instances.
 * 
 * @version V0.0 16.07.2010
 * @author Clemens Koza
 */
public class PropertyChangeSetListener<E> implements SetListener<E> {
    private static final long     serialVersionUID = 625853864429729560L;
    
    private PropertyChangeSupport s;
    private Set<E>                set;
    private String                propertyName;
    
    public PropertyChangeSetListener(PropertyChangeSupport s, Set<E> set, String propertyName) {
        this.s = s;
        this.set = set;
        this.propertyName = propertyName;
    }
    
    public void add(E newValue) {
        s.firePropertyChange(new SetAddEvent<E>(s.getSourceBean(), propertyName, set, newValue));
    }
    
    public void remove(E oldValue) {
        s.firePropertyChange(new SetRemoveEvent<E>(s.getSourceBean(), propertyName, set, oldValue));
    }
    
    public static abstract class SetEvent<E> extends PropertyChangeEvent {
        private static final long serialVersionUID = -651568020675693544L;
        
        private Set<E>            set;
        
        public SetEvent(Object source, String propertyName, Set<E> set) {
            super(source, propertyName, null, null);
            this.set = set;
        }
        
        @Override
        public Set<E> getOldValue() {
            //old and new value must not be equal
            return null;
        }
        
        @Override
        public Set<E> getNewValue() {
            return set;
        }
    }
    
    public static class SetAddEvent<E> extends SetEvent<E> {
        private static final long serialVersionUID = 9041766866796759871L;
        
        private E                 newElement;
        
        public SetAddEvent(Object source, String propertyName, Set<E> set, E newElement) {
            super(source, propertyName, set);
            this.newElement = newElement;
        }
        
        public E getNewElement() {
            return newElement;
        }
    }
    
    public static class SetRemoveEvent<E> extends SetEvent<E> {
        private static final long serialVersionUID = -1315342339926392385L;
        
        private E                 oldElement;
        
        public SetRemoveEvent(Object source, String propertyName, Set<E> set, E oldElement) {
            super(source, propertyName, set);
            this.oldElement = oldElement;
        }
        
        public E getOldElement() {
            return oldElement;
        }
    }
}
