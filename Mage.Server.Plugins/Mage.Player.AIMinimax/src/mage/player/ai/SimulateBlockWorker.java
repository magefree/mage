

package mage.player.ai;

import org.apache.log4j.Logger;

import java.util.concurrent.Callable;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SimulateBlockWorker implements Callable {

    private static final Logger logger = Logger.getLogger(SimulationWorker.class);

    private SimulationNode node;
    private ComputerPlayer3 player;

    public SimulateBlockWorker(ComputerPlayer3 player, SimulationNode node) {
        this.player = player;
        this.node = node;
    }

    @Override
    public Object call() {
        try {
//            player.simulateBlock(node);
        } catch (Exception ex) {
            logger.error(null, ex);
        }
        return null;
    }
}
