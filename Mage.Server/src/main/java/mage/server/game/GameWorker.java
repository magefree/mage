package mage.server.game;

import mage.MageException;
import mage.game.Game;
import org.apache.log4j.Logger;

import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * Game: main thread to process full game (one thread per game)
 *
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class GameWorker implements Callable<Boolean> {

    private static final Logger LOGGER = Logger.getLogger(GameWorker.class);

    private final GameCallback gameController;
    private final Game game;
    private final UUID choosingPlayerId;

    public GameWorker(Game game, UUID choosingPlayerId, GameCallback gameController) {
        this.game = game;
        this.choosingPlayerId = choosingPlayerId;
        this.gameController = gameController;
    }

    @Override
    public Boolean call() {
        try {
            // play game
            Thread.currentThread().setName("GAME " + game.getId());
            game.start(choosingPlayerId);

            // save result and start next game or close finished table
            game.fireUpdatePlayersEvent(); // TODO: no needs in update event (gameController.endGameWithResult already send game end dialog)?
            gameController.endGameWithResult(game.getWinner());

            // clear resources
            game.cleanUp();// TODO: no needs in cleanup code (cards list are useless for memory optimization, game states are more important)?
        } catch (MageException e) {
            LOGGER.fatal("GameWorker mage error [" + game.getId() + " - " + game + "]: " + e, e);
        } catch (Throwable e) {
            LOGGER.fatal("GameWorker system error [" + game.getId() + " - " + game + "]: " + e, e);
        }
        return null;
    }
}
