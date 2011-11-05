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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants.PhaseStep;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.game.Game;
import mage.game.GameState;
import mage.game.combat.Combat;
import mage.game.combat.CombatGroup;
import mage.game.turn.Step.StepPart;
import mage.players.Player;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MCTSNode {
    
    private static final double selectionCoefficient = 1;
 	private final static transient Logger logger = Logger.getLogger(MCTSNode.class);
   
    private int visits = 0;
    private int wins = 0;
    private MCTSNode parent;
    private List<MCTSNode> children = new ArrayList<MCTSNode>();
    private Ability action;
    private Combat combat;
    private Game game;
    private int stateValue;
    
    private static int nodeCount;
    
    public MCTSNode(Game game) {
        this.game = game;
        this.stateValue = game.getState().getValue().hashCode();
        nodeCount = 1;
    }    
    
    protected MCTSNode(MCTSNode parent, Game game, int state, Ability action) {
        this.game = game;
        this.stateValue = state;
        this.parent = parent;
        this.action = action;
        nodeCount++;
    }
    
    protected MCTSNode(MCTSNode parent, Game game, int state, Combat combat) {
        this.game = game;
        this.stateValue = state;
        this.parent = parent;
        this.combat = combat;
        nodeCount++;
    }

    public MCTSNode select() {
        double bestValue = Double.NEGATIVE_INFINITY;
        MCTSNode bestChild = null;
//        logger.info("start select");
        if (children.size() == 1) {
            return children.get(0);
        }
        for (MCTSNode node: children) {
            double uct;
            if (node.visits > 0)
                uct = (node.wins / (node.visits + 1.0)) + (selectionCoefficient * Math.sqrt(Math.log(visits + 1.0) / (node.visits + 1.0)));
            else
                // ensure that a random unvisited node is played first
                uct = 10000 + 1000 * Math.random();
//            logger.info("uct: " + uct);
            if (uct > bestValue) {
                bestChild = node;
                bestValue = uct;
            }
        }
//        logger.info("stop select");
        return bestChild;
    }
    
    public void expand() {
        MCTSPlayer player;
        if (game.getStep().getStepPart() == StepPart.PRIORITY)
            player = (MCTSPlayer) game.getPlayer(game.getPriorityPlayerId());
        else {
            if (game.getStep().getType() == PhaseStep.DECLARE_BLOCKERS)
                player = (MCTSPlayer) game.getPlayer(game.getCombat().getDefenders().iterator().next());
            else
                player = (MCTSPlayer) game.getPlayer(game.getActivePlayerId());
        }
        if (player.getNextAction() == null) {
            logger.fatal("next action is null");
        }
        switch (player.getNextAction()) {
            case PRIORITY:
                logger.info("Priority for player:" + player.getName() + " turn: " + game.getTurnNum() + " phase: " + game.getPhase().getType() + " step: " + game.getStep().getType());
                List<Ability> abilities = player.getPlayableOptions(game);
                for (Ability ability: abilities) {
                    Game sim = game.copy();
                    int simState = sim.getState().getValue().hashCode();
                    logger.info("expand " + ability.toString());
                    MCTSPlayer simPlayer = (MCTSPlayer) sim.getPlayer(player.getId());
                    simPlayer.activateAbility((ActivatedAbility)ability, sim);
                    sim.resume();
                    children.add(new MCTSNode(this, sim, simState, ability));
                }
                break;
            case SELECT_ATTACKERS:
                logger.info("Select attackers:" + player.getName());
                List<List<UUID>> attacks = player.getAttacks(game);
                UUID defenderId = game.getOpponents(player.getId()).iterator().next();
                for (List<UUID> attack: attacks) {
                    Game sim = game.copy();
                    int simState = sim.getState().getValue().hashCode();
                    MCTSPlayer simPlayer = (MCTSPlayer) sim.getPlayer(player.getId());
                    for (UUID attackerId: attack) {
                        simPlayer.declareAttacker(attackerId, defenderId, sim);
                    }
                    sim.resume();
                    children.add(new MCTSNode(this, sim, simState, sim.getCombat()));
                }
                break;
            case SELECT_BLOCKERS:
                logger.info("Select blockers:" + player.getName());
                List<List<List<UUID>>> blocks = player.getBlocks(game);
                for (List<List<UUID>> block: blocks) {
                    Game sim = game.copy();
                    int simState = sim.getState().getValue().hashCode();
                    MCTSPlayer simPlayer = (MCTSPlayer) sim.getPlayer(player.getId());
                    List<CombatGroup> groups = sim.getCombat().getGroups();
                    for (int i = 0; i < groups.size(); i++) {
                        if (i < block.size()) {
                            for (UUID blockerId: block.get(i)) {
                                simPlayer.declareBlocker(blockerId, groups.get(i).getAttackers().get(0), sim);
                            }
                        }
                    }
                    sim.resume();
                    children.add(new MCTSNode(this, sim, simState, sim.getCombat()));
                }
                break;
        }
    }
        
    public int simulate(UUID playerId) {
		long startTime = System.nanoTime();
        Game sim = createSimulation(game);
        sim.resume();
        long duration = System.nanoTime() - startTime;
        int retVal = 0;
        for (Player simPlayer: sim.getPlayers().values()) {
			logger.info(simPlayer.getName() + " calculated " + ((SimulatedPlayerMCTS)simPlayer).getActionCount() + " actions in " + duration/1000000000.0 + "s");
            if (simPlayer.getId().equals(playerId) && simPlayer.hasWon()) {
                logger.info("AI won the simulation");
                retVal = 1;
            }
        }
        return retVal;
    }
    
    public void backpropagate(int result) {
        if (result == 1)
            wins++;
        visits++;
        if (parent != null)
            parent.backpropagate(result);
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public MCTSNode bestChild() {
        double bestCount = -1;
        MCTSNode bestChild = null;
        for (MCTSNode node: children) {
            if (node.visits > bestCount) {
                bestChild = node;
                bestCount = node.visits;
            }
        }
        return bestChild;
    }
    
    public void emancipate() {
        this.parent = null;
    }
    
    public Ability getAction() {
        return action;
    }
    
    public int getNumChildren() {
        return children.size();
    }
    
    public MCTSNode getParent() {
        return parent;
    }
    
    public Combat getCombat() {
        return combat;
    }
    
    public int getNodeCount() {
        return nodeCount;
    }

    /**
	 * Copies game and replaces all players in copy with simulated players
     * Shuffles each players library so that there is no knowledge of its order
	 *
	 * @param game
	 * @return a new game object with simulated players
	 */
	protected Game createSimulation(Game game) {
		Game sim = game.copy();

		for (Player copyPlayer: sim.getState().getPlayers().values()) {
			Player origPlayer = game.getState().getPlayers().get(copyPlayer.getId()).copy();
			SimulatedPlayerMCTS newPlayer = new SimulatedPlayerMCTS(copyPlayer.getId(), true);
			newPlayer.restore(origPlayer);
            newPlayer.shuffleLibrary(sim);
			sim.getState().getPlayers().put(copyPlayer.getId(), newPlayer);
		}
		sim.setSimulation(true);
		return sim;
	}

    public boolean isTerminal() {
        return game.isGameOver();
    }

    public boolean isWinner(UUID playerId) {
        Player player = game.getPlayer(playerId);
        if (player != null && player.hasWon())
            return true;
        return false;
    }

    public MCTSNode getMatchingState(int state) {
        for (MCTSNode node: children) {
//            logger.info(state);
//            logger.info(node.stateValue);
            if (node.stateValue == state) {
                return node;
            }
            MCTSNode match = node.getMatchingState(state);
            if (match != null)
                return node;
        }
        return null;
    }

}
