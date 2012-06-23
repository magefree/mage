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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import mage.game.*;
import mage.server.Main;
import mage.util.CopierObjectInputStream;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GameReplay {

    private final static Logger logger = Logger.getLogger(GameReplay.class);

    private GameStates savedGame;
    private Game game;
    private int stateIndex;

    public GameReplay(UUID gameId) {
        this.game = loadGame(gameId);
        this.savedGame = game.getGameStates();
    }

    public void start() {
        this.stateIndex = 0;
    }

    public GameState next() {
        if (this.stateIndex < savedGame.getSize()) {
            return savedGame.get(stateIndex++);
        }
        return null;
    }

    public GameState previous() {
        if (this.stateIndex > 0) {
            return savedGame.get(--stateIndex);
        }
        return null;
    }

    public Game getGame() {
        return this.game;
    }

    private Game loadGame(UUID gameId) {
        try{
            InputStream file = new FileInputStream("saved/" + gameId.toString() + ".game");
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new CopierObjectInputStream(Main.classLoader, new GZIPInputStream(buffer));
            try {
                Game loadGame = (Game)input.readObject();
                GameStates states = (GameStates)input.readObject();
                loadGame.loadGameStates(states);
                return loadGame;
            }
            finally {
                input.close();
            }
        }
        catch(ClassNotFoundException ex) {
            logger.fatal("Cannot load game. Class not found.", ex);
        }
        catch(IOException ex) {
            logger.fatal("Cannot load game:" + gameId, ex);
        }
        return null;
    }

}
