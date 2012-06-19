/**
 * BoundProperties.java
 * 
 * Created on 24.08.2010
 */

package org.mage.plugins.card.dl.beans.properties.bound;


import static org.mage.plugins.card.dl.beans.collections.ListenableCollections.listenableList;
import static org.mage.plugins.card.dl.beans.collections.ListenableCollections.listenableMap;
import static org.mage.plugins.card.dl.beans.collections.ListenableCollections.listenableSet;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mage.plugins.card.dl.beans.PropertyChangeSupport;
import org.mage.plugins.card.dl.beans.properties.AbstractProperties;
import org.mage.plugins.card.dl.beans.properties.Property;


/**
 * The class BoundProperties.
 * 
 * @version V0.0 24.08.2010
 * @author Clemens Koza
 */
public class BoundProperties extends AbstractProperties {
    public final PropertyChangeSupport s;

    public BoundProperties(Object sourceBean) {
        this(new PropertyChangeSupport(sourceBean));
    }

    public BoundProperties(PropertyChangeSupport s) {
        if(s == null) throw new IllegalArgumentException("s == null");
        this.s = s;
    }

    public <T> Property<T> property(String name, Property<T> property) {
        return new BoundProperty<T>(s, name, property);
    }

    public <E> List<E> list(String name, List<E> list) {
        return listenableList(list, new PropertyChangeListListener<E>(s, name));
    }

    public <E> Set<E> set(String name, Set<E> set) {
        return listenableSet(set, new PropertyChangeSetListener<E>(s, set, name));
    }

    public <K, V> Map<K, V> map(String name, Map<K, V> map) {
        return listenableMap(map, new PropertyChangeMapListener<K, V>(s, map, name));
    }
}
