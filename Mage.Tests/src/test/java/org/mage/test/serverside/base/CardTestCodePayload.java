package org.mage.test.serverside.base;

import mage.game.Game;
import mage.players.Player;

/**
 * @author JayDi85
 */
@FunctionalInterface
public interface CardTestCodePayload {

    /**
     * Run dynamic code in unit tests on player's priority.
     *
     * @param info
     * @param player activate player who would execute the code on their priority
     * @param game
     */
    void run(String info, Player player, Game game);

}
