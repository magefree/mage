/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.server.game;

import java.util.UUID;
import java.util.concurrent.Callable;
import mage.MageException;
import mage.game.Game;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GameWorker<T> implements Callable {

    private static final Logger logger = Logger.getLogger(GameWorker.class);

    private final GameCallback gameController;
    private final Game game;
    private final UUID choosingPlayerId;

    public GameWorker(Game game, UUID choosingPlayerId, GameCallback gameController) {
        this.game = game;
        this.choosingPlayerId = choosingPlayerId;
        this.gameController = gameController;
    }

    @Override
    public Object call() {
        try {
            logger.debug("GAME WORKER started gameId "+ game.getId());
            Thread.currentThread().setName("GAME " + game.getId());
            game.start(choosingPlayerId);
            game.fireUpdatePlayersEvent();
            gameController.gameResult(game.getWinner());
            game.cleanUp();
        } catch (MageException ex) {
            logger.fatal("GameWorker mage error [" + game.getId() + "]" + ex, ex);
            ex.printStackTrace();
        } catch (Exception e) {
            logger.fatal("GameWorker general exception [" + game.getId() + "]" + e.getMessage(), e);
            e.printStackTrace();
        } catch (Error err) {
            logger.fatal("GameWorker general error [" + game.getId() + "]" +err, err);
            err.printStackTrace();
        }
        return null;
    }

}
