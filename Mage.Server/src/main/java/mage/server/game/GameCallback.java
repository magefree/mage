package mage.server.game;

import mage.MageException;

/**
 * @author JayDi85
 */
public interface GameCallback {

    void endGameWithResult(String result) throws MageException;

    /**
     * A game thread died with an error before a normal end: a game can't continue, but it must be
     * closed anyway, otherwise a table stays forever (concede and /fix can't help without a game thread)
     * See #16176
     */
    default void endGameWithError(Throwable error) {
    }
}
