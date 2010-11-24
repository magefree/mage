/**
 * This package contains bean-related utilities, most importantly
 * <ul>
 * <li>{@link net.slightlymagic.beans.BoundBean} and {@link net.slightlymagic.beans.AbstractBoundBean}, an interface and an
 * abstract class for easier property change support for bean classes</li>
 * <li>{@link net.slightlymagic.beans.properties.Properties} and its implementations. These make it easy for beans
 * to use delegates, by providing a centralized configuration for bean properties. For example, a {@link
 * net.slightlymagic.beans.properties.bound.BoundProperties} object creates properties that do automatic property
 * change notifications. What exact configuration is used is thus hidden from the delegate.</li>
 * <li>The {@link net.slightlymagic.beans.relational.Relations} class provides relational properties that model
 * bidirectional 1:1, 1:n and m:n relationships.</li>
 * </ul>
 */

package org.mage.plugins.card.dl.beans;


