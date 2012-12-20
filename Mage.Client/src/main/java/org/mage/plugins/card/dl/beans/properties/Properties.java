/**
 * Properties.java
 * 
 * Created on 24.08.2010
 */

package org.mage.plugins.card.dl.beans.properties;


import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mage.plugins.card.dl.beans.properties.bound.BoundProperties;


/**
 * The class Properties. A Properties object is a factory for bean properties. For example, the
 * {@link BoundProperties} class produces properties that can be observed using {@link PropertyChangeListener}s.
 * 
 * @version V0.0 24.08.2010
 * @author Clemens Koza
 */
public interface Properties {

    <T> Property<T> property(String name, Property<T> property);

    <E> List<E> list(String name, List<E> list);

    <E> Set<E> set(String name, Set<E> set);

    <K, V> Map<K, V> map(String name, Map<K, V> map);


    <T> Property<T> property(String name, T value);

    <T> Property<T> property(String name);

    <E> List<E> list(String name);

    <E> Set<E> set(String name);

    <K, V> Map<K, V> map(String name);
}
