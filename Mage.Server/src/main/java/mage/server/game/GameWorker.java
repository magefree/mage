package mage.server.game;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.Callable;

import mage.MageException;
import mage.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @param <T>
 * @author BetaSteward_at_googlemail.com
 */
public class GameWorker<T> implements Callable {
    private static final Logger logger = LoggerFactory.getLogger(GameWorker.class);

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
            logger.debug("GAME WORKER started gameId " + game.getId());
            Thread.currentThread().setName("GAME " + game.getId());
            game.start(choosingPlayerId);
            game.fireUpdatePlayersEvent();
            gameController.gameResult(game.getWinner());
            game.cleanUp();
        } catch (MageException ex) {
            logger.error("GameWorker mage error [" + game.getId() + "] " + ex, ex);
        } catch (Exception e) {
            logger.error("GameWorker general exception [" + game.getId() + "] " + e.getMessage(), e);
            if (e instanceof NullPointerException) {
                logger.info(Arrays.toString(e.getStackTrace()));
            }
        } catch (Error err) {
            logger.error("GameWorker general error [" + game.getId() + "] " + err, err);
        }
        return null;
    }

}
