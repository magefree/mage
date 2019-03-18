

package mage.server.game;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import mage.game.Game;
import mage.game.GameState;
import mage.game.GameStates;
import mage.server.Main;
import mage.util.CopierObjectInputStream;
import mage.utils.StreamUtils;
import org.apache.log4j.Logger;



/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GameReplay {

    private static final Logger logger = Logger.getLogger(GameReplay.class);

    private final GameStates savedGame;
    private final Game game;
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
        InputStream file = null;
        InputStream buffer = null;
        InputStream gzip = null;
        ObjectInput input = null;
        try{
            file = new FileInputStream("saved/" + gameId.toString() + ".game");
            buffer = new BufferedInputStream(file);
            gzip = new GZIPInputStream(buffer);
            input = new CopierObjectInputStream(Main.classLoader, gzip);
            Game loadGame = (Game) input.readObject();
            GameStates states = (GameStates) input.readObject();
            loadGame.loadGameStates(states);
            return loadGame;

        }
        catch(ClassNotFoundException ex) {
            logger.fatal("Cannot load game. Class not found.", ex);
        }
        catch(IOException ex) {
            logger.fatal("Cannot load game:" + gameId, ex);
        } finally {
            StreamUtils.closeQuietly(file);
            StreamUtils.closeQuietly(buffer);
            StreamUtils.closeQuietly(input);
            StreamUtils.closeQuietly(gzip);
        }
        return null;
    }

}
