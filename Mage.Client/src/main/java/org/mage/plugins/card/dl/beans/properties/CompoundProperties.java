/**
 * CompoundProperties.java
 * 
 * Created on 24.08.2010
 */

package org.mage.plugins.card.dl.beans.properties;


import static java.util.Arrays.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * The class CompoundProperties.
 * 
 * @version V0.0 24.08.2010
 * @author Clemens Koza
 */
public class CompoundProperties extends AbstractProperties {
    private List<Properties> delegates;

    public CompoundProperties(Properties... delegates) {
        this.delegates = asList(delegates);
        Collections.reverse(this.delegates);
    }

    public CompoundProperties(List<Properties> delegates) {
        this.delegates = new ArrayList<Properties>(delegates);
        Collections.reverse(this.delegates);
    }

    public <T> Property<T> property(String name, Property<T> property) {
        for(Properties p:delegates)
            property = p.property(name, property);
        return property;
    }

    public <E> List<E> list(String name, List<E> list) {
        for(Properties p:delegates)
            list = p.list(name, list);
        return list;
    }

    public <E> Set<E> set(String name, Set<E> set) {
        for(Properties p:delegates)
            set = p.set(name, set);
        return set;
    }

    public <K, V> Map<K, V> map(String name, Map<K, V> map) {
        for(Properties p:delegates)
            map = p.map(name, map);
        return map;
    }
}
