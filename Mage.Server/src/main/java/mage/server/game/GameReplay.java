package mage.server.game;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import mage.game.Game;
import mage.game.GameState;
import mage.game.GameStates;
import mage.server.Main;
import mage.util.CopierObjectInputStream;
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
        try (ObjectInput input = new CopierObjectInputStream(Main.classLoader, new GZIPInputStream(new BufferedInputStream(Files.newInputStream(Paths.get("saved/" + gameId.toString() + ".game")))))) {
            Game loadGame = (Game) input.readObject();
            GameStates states = (GameStates) input.readObject();
            loadGame.loadGameStates(states);
            return loadGame;
        } catch (ClassNotFoundException ex) {
            logger.fatal("Cannot load game. Class not found.", ex);
        } catch (IOException ex) {
            logger.fatal("Cannot load game:" + gameId, ex);
        }
        return null;
    }
}
