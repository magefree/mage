/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
 * 
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 * 
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 * 
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 * 
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
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
    
 	private final static transient Logger logger = Logger.getLogger(ComputerPlayerMCTS.class);
    
    public MCTSExecutor(Game sim, UUID playerId, int thinkTime) {
        this.playerId = playerId;
        this.thinkTime = thinkTime;
        root = new MCTSNode(sim);
    }
    
    @Override
    public Boolean call() {
        int simCount = 0;
		long startTime = System.nanoTime();
        long endTime = startTime + (thinkTime * 1000000000l);
        MCTSNode current;
        
//        if (root.getNumChildren() == 1)
//            //there is only one possible action - don't spend a lot of time thinking
//            endTime = startTime + 1000000000l;
        
//        logger.info("applyMCTS - Thinking for " + (endTime - startTime)/1000000000.0 + "s");
        while (true) {
            long currentTime = System.nanoTime();
//            logger.info("Remaining time: " + (endTime - currentTime)/1000000000.0 + "s");
            if (currentTime > endTime)
//            if (root.getNodeCount() > 50)
                break;
            current = root;
            
            // Selection
            while (!current.isLeaf()) {
                current = current.select(this.playerId);
            }
            
            int result;
            if (!current.isTerminal()) {
                // Expansion
                current.expand();
                
//                if (current == root && current.getNumChildren() == 1)
//                    //there is only one possible action - don't spend a lot of time thinking
//                    endTime = startTime + 1000000000l;

                // Simulation
                current = current.select(this.playerId);
                result = current.simulate(this.playerId);
                simCount++;
            }
            else {
                result = current.isWinner(this.playerId)?1:-1;
            }
            // Backpropagation
            current.backpropagate(result);
        }
//        logger.info("Created " + root.getNodeCount() + " nodes");
        logger.info("Simulated " + simCount + " nodes");
        return true;
    }

    public MCTSNode getRoot() {
        return root;
    }
    
    public void clear() {
        root = null;
    }
    
}
