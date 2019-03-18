
package mage.player.ai;

import java.util.UUID;
import java.util.concurrent.Callable;
import mage.game.Game;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MCTSExecutor implements Callable<Boolean> {

    protected transient MCTSNode root;
    protected int thinkTime;
    protected UUID playerId;
    protected int simCount;

    private static final Logger logger = Logger.getLogger(ComputerPlayerMCTS.class);

    public MCTSExecutor(Game sim, UUID playerId, int thinkTime) {
        this.playerId = playerId;
        this.thinkTime = thinkTime;
        root = new MCTSNode(playerId, sim);
    }

    @Override
    public Boolean call() {
        simCount = 0;
        MCTSNode current;


        while (true) {
            current = root;

            // Selection
            while (!current.isLeaf()) {
                current = current.select(this.playerId);
            }

            int result;
            if (!current.isTerminal()) {
                // Expansion
                current.expand();

                // only run simulations for nodes that have siblings
                if (current.getNumChildren() > 1) {
                    // Simulation
                    current = current.select(this.playerId);
                    result = current.simulate(this.playerId);
                    simCount++;
                }
                else {
                    current = current.select(this.playerId);
                    result = 0;
                }
            }
            else {
                result = current.isWinner(this.playerId)?1:-1;
            }
            // Backpropagation
            current.backpropagate(result);
        }
    }

    public MCTSNode getRoot() {
        return root;
    }

    public void clear() {
        root = null;
    }

    public int getSimCount() {
        return simCount;
    }
}
