package mage.util;

/**
 * @author BetaSteward_at_googlemail.com
 */
@FunctionalInterface
public interface Copyable<T> {
    T copy();
}
