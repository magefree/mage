
package mage.util.functions;

/**
 * @author nantuko
 */
@FunctionalInterface
public interface Function<X, Y> {
    X apply(Y in);
}
