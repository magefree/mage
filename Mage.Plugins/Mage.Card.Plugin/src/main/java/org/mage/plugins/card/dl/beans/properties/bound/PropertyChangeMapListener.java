/**
 * PropertyChangeMapListener.java
 * 
 * Created on 16.07.2010
 */

package org.mage.plugins.card.dl.beans.properties.bound;


import java.beans.PropertyChangeEvent;
import java.util.Map;

import org.mage.plugins.card.dl.beans.PropertyChangeSupport;
import org.mage.plugins.card.dl.beans.collections.ListenableCollections.MapListener;


/**
 * The class PropertyChangeMapListener. This listener alway fires events with {@link Map} -> {@link Map} as the
 * value parameters, as nonindexed collection properties. The events will be {@link MapEvent} instances.
 * 
 * @version V0.0 16.07.2010
 * @author Clemens Koza
 */
public class PropertyChangeMapListener<K, V> implements MapListener<K, V> {
    private static final long     serialVersionUID = 625853864429729560L;
    
    private PropertyChangeSupport s;
    private Map<K, V>             map;
    private String                propertyName;
    
    public PropertyChangeMapListener(PropertyChangeSupport s, Map<K, V> map, String propertyName) {
        this.s = s;
        this.map = map;
        this.propertyName = propertyName;
    }
    
    public void put(K key, V newValue) {
        s.firePropertyChange(new MapPutEvent<K, V>(s.getSourceBean(), propertyName, map, key, newValue));
    }
    
    public void set(K key, V oldValue, V newValue) {
        s.firePropertyChange(new MapSetEvent<K, V>(s.getSourceBean(), propertyName, map, key, oldValue, newValue));
    }
    
    public void remove(K key, V oldValue) {
        s.firePropertyChange(new MapRemoveEvent<K, V>(s.getSourceBean(), propertyName, map, key, oldValue));
    }
    
    public static abstract class MapEvent<K, V> extends PropertyChangeEvent {
        private static final long serialVersionUID = -651568020675693544L;
        
        private Map<K, V>         map;
        private K                 key;
        
        public MapEvent(Object source, String propertyName, Map<K, V> map, K key) {
            super(source, propertyName, null, null);
            this.map = map;
            this.key = key;
        }
        
        @Override
        public Map<K, V> getOldValue() {
            //old and new value must not be equal
            return null;
        }
        
        @Override
        public Map<K, V> getNewValue() {
            return map;
        }
        
        public K getKey() {
            return key;
        }
    }
    
    public static class MapPutEvent<K, V> extends MapEvent<K, V> {
        private static final long serialVersionUID = 6006291474676939650L;
        
        private V                 newElement;
        
        public MapPutEvent(Object source, String propertyName, Map<K, V> map, K key, V newElement) {
            super(source, propertyName, map, key);
            this.newElement = newElement;
        }
        
        public V getNewElement() {
            return newElement;
        }
    }
    
    public static class MapSetEvent<K, V> extends MapEvent<K, V> {
        private static final long serialVersionUID = -2419438379909500079L;
        
        private V                 oldElement, newElement;
        
        public MapSetEvent(Object source, String propertyName, Map<K, V> map, K key, V oldElement, V newElement) {
            super(source, propertyName, map, key);
            this.oldElement = oldElement;
            this.newElement = newElement;
        }
        
        public V getOldElement() {
            return oldElement;
        }
        
        public V getNewElement() {
            return newElement;
        }
    }
    
    public static class MapRemoveEvent<K, V> extends MapEvent<K, V> {
        private static final long serialVersionUID = -2644879706878221895L;
        
        private V                 oldElement;
        
        public MapRemoveEvent(Object source, String propertyName, Map<K, V> map, K key, V oldElement) {
            super(source, propertyName, map, key);
            this.oldElement = oldElement;
        }
        
        public V getOldElement() {
            return oldElement;
        }
    }
}
