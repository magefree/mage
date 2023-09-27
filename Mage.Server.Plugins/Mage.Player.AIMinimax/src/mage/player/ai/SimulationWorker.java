package mage.player.ai;

import java.util.concurrent.Callable;
import mage.abilities.Ability;
import mage.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SimulationWorker implements Callable {

    private static final Logger logger = LoggerFactory.getLogger(SimulationWorker.class);

    private Game game;
    private SimulatedAction previousActions;
    private Ability action;
    private SimulatedPlayer player;

    public SimulationWorker(Game game, SimulatedPlayer player, SimulatedAction previousActions, Ability action) {
        this.game = game;
        this.player = player;
        this.previousActions = previousActions;
        this.action = action;
    }

    @Override
    public Object call() {
        try {
//            player.simulateAction(game, previousActions, action);
        } catch (Exception ex) {
            logger.error(null, ex);
        }
        return null;
    }

}

