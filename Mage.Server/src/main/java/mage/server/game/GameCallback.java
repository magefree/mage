

package mage.server.game;

import mage.MageException;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
@FunctionalInterface
public interface GameCallback {

    void gameResult(String result) throws MageException;
}
