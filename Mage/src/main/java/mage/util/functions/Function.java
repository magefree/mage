package mage.util.functions;

import mage.game.Game;

/**
 * @author nantuko
 */
@FunctionalInterface
public interface Function<X, Y> {
    X apply(Y in, Game game);
}
