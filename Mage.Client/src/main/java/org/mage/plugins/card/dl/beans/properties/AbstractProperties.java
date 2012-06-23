/**
 * AbstractProperties.java
 * 
 * Created on 24.08.2010
 */

package org.mage.plugins.card.dl.beans.properties;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mage.plugins.card.dl.beans.properties.basic.BasicProperty;


/**
 * The class AbstractProperties.
 * 
 * @version V0.0 24.08.2010
 * @author Clemens Koza
 */
public abstract class AbstractProperties implements Properties {
    public <T> Property<T> property(String name, T value) {
        return property(name, new BasicProperty<T>(value));
    }

    public <T> Property<T> property(String name) {
        return property(name, new BasicProperty<T>());
    }

    public <E> List<E> list(String name) {
        return list(name, new ArrayList<E>());
    }

    public <E> Set<E> set(String name) {
        return set(name, new HashSet<E>());
    }

    public <K, V> Map<K, V> map(String name) {
        return map(name, new HashMap<K, V>());
    }

}
