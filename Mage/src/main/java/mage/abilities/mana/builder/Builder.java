
package mage.abilities.mana.builder;

import java.io.Serializable;

/**
 * @author noxx
 */
@FunctionalInterface
public interface Builder<T> extends Serializable {

    T build(Object... options);
}
