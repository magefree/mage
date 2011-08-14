/**
 * BoundProperties.java
 * 
 * Created on 24.08.2010
 */

package org.mage.plugins.card.dl.beans.properties.basic;


import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mage.plugins.card.dl.beans.properties.AbstractProperties;
import org.mage.plugins.card.dl.beans.properties.Property;


/**
 * The class BoundProperties.
 * 
 * @version V0.0 24.08.2010
 * @author Clemens Koza
 */
public class BasicProperties extends AbstractProperties {
    public <T> Property<T> property(String name, Property<T> value) {
        return value;
    }
    
    public <E> List<E> list(String name, List<E> list) {
        return list;
    }
    
    public <E> Set<E> set(String name, Set<E> set) {
        return set;
    }
    
    public <K, V> Map<K, V> map(String name, Map<K, V> map) {
        return map;
    }
}
