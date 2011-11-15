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

import java.util.List;
import java.util.UUID;
import mage.Constants.RangeOfInfluence;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.game.Game;
import mage.game.combat.Combat;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ComputerPlayerMCTS extends ComputerPlayer<ComputerPlayerMCTS> implements Player {

    protected transient MCTSNode root;
    protected int thinkTime;
 	private final static transient Logger logger = Logger.getLogger(ComputerPlayerMCTS.class);
    
	public ComputerPlayerMCTS(String name, RangeOfInfluence range, int skill) {
		super(name, range);
		human = false;
        thinkTime = skill;
	}

	protected ComputerPlayerMCTS(UUID id) {
		super(id);
	}

	public ComputerPlayerMCTS(final ComputerPlayerMCTS player) {
		super(player);
	}
    
    @Override
    public ComputerPlayerMCTS copy() {
        return new ComputerPlayerMCTS(this);
    }

    @Override
    public void priority(Game game) {
        getNextAction(game);
        Ability ability =  root.getAction();
        activateAbility((ActivatedAbility)ability, game);
    }

    protected void calculateActions(Game game) {
        if (root == null) {
            Game sim = createMCTSGame(game);
            MCTSPlayer player = (MCTSPlayer) sim.getPlayer(playerId);
            player.setNextAction(MCTSPlayer.NextAction.PRIORITY);
            root = new MCTSNode(sim);
        }
        applyMCTS();
        root = root.bestChild();
        root.emancipate();
    }
    
    protected void getNextAction(Game game) {
        if (root != null) {
            root = root.getMatchingState(game.getState().getValue().hashCode());
            if (root != null)
                root.emancipate();
        }
        calculateActions(game);
    }
    
//    @Override
//    public boolean choose(Outcome outcome, Target target, UUID sourceId, Game game) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public boolean choose(Outcome outcome, Target target, UUID sourceId, Game game, Map<String, Serializable> options) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public boolean choose(Outcome outcome, Cards cards, TargetCard target, Game game) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public boolean chooseTarget(Outcome outcome, Target target, Ability source, Game game) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public boolean chooseTarget(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public boolean chooseTargetAmount(Outcome outcome, TargetAmount target, Ability source, Game game) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public boolean chooseMulligan(Game game) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public boolean chooseUse(Outcome outcome, String message, Game game) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public boolean choose(Outcome outcome, Choice choice, Game game) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }

//    @Override
//    public boolean playMana(ManaCost unpaid, Game game) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }

//    @Override
//    public boolean playXMana(VariableManaCost cost, ManaCosts<ManaCost> costs, Game game) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public int chooseEffect(List<ReplacementEffect> rEffects, Game game) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public TriggeredAbility chooseTriggeredAbility(TriggeredAbilities abilities, Game game) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public Mode chooseMode(Modes modes, Ability source, Game game) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }

    @Override
    public void selectAttackers(Game game) {
        Game sim = createMCTSGame(game);
        MCTSPlayer player = (MCTSPlayer) sim.getPlayer(playerId);
        player.setNextAction(MCTSPlayer.NextAction.SELECT_ATTACKERS);
        root = new MCTSNode(sim);
        applyMCTS();
        Combat combat = root.bestChild().getCombat();
        UUID opponentId = game.getCombat().getDefenders().iterator().next();
        for (UUID attackerId: combat.getAttackers()) {
            this.declareAttacker(attackerId, opponentId, game);
        }
    }

    @Override
    public void selectBlockers(Game game) {
        Game sim = createMCTSGame(game);
        MCTSPlayer player = (MCTSPlayer) sim.getPlayer(playerId);
        player.setNextAction(MCTSPlayer.NextAction.SELECT_BLOCKERS);
        root = new MCTSNode(sim);
        applyMCTS();
        Combat combat = root.bestChild().getCombat();
        List<CombatGroup> groups = game.getCombat().getGroups();
        for (int i = 0; i < groups.size(); i++) {
            if (i < combat.getGroups().size()) {
                for (UUID blockerId: combat.getGroups().get(i).getBlockers()) {
                    this.declareBlocker(blockerId, groups.get(i).getAttackers().get(0), game);
                }
            }
        }
    }

//    @Override
//    public UUID chooseAttackerOrder(List<Permanent> attacker, Game game) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public UUID chooseBlockerOrder(List<Permanent> blockers, Game game) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public void assignDamage(int damage, List<UUID> targets, String singleTargetName, UUID sourceId, Game game) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public int getAmount(int min, int max, String message, Game game) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public void sideboard(Match match, Deck deck) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public void construct(Tournament tournament, Deck deck) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public void pickCard(List<Card> cards, Deck deck, Draft draft) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }

    protected void applyMCTS() {
		long startTime = System.nanoTime();
        long endTime = startTime + (thinkTime * 1000000000l);
        MCTSNode current;
        
        if (root.getNumChildren() == 1)
            //there is only one possible action
            return;
        
        logger.info("applyMCTS - Thinking for " + (endTime - startTime)/1000000000.0 + "s");
        while (true) {
            long currentTime = System.nanoTime();
            logger.info("Remaining time: " + (endTime - currentTime)/1000000000.0 + "s");
            if (currentTime > endTime)
                break;
            current = root;
            
            // Selection
            while (!current.isLeaf()) {
                current = current.select();
            }
            
            int result;
            if (!current.isTerminal()) {
                // Expansion
                current.expand();
                
                if (current == root && current.getNumChildren() == 1)
                    //there is only one possible action
                    return;

                // Simulation
                current = current.select();
                result = current.simulate(this.playerId);
            }
            else {
                result = current.isWinner(this.playerId)?1:0;
            }
            // Backpropagation
            current.backpropagate(result);
        }
        logger.info("Created " + root.getNodeCount() + " nodes");
        return;
    }
            
    /**
	 * Copies game and replaces all players in copy with mcts players
     * Shuffles each players library so that there is no knowledge of its order
	 *
	 * @param game
	 * @return a new game object with simulated players
	 */
	protected Game createMCTSGame(Game game) {
		Game mcts = game.copy();

		for (Player copyPlayer: mcts.getState().getPlayers().values()) {
			Player origPlayer = game.getState().getPlayers().get(copyPlayer.getId());
			MCTSPlayer newPlayer = new MCTSPlayer(copyPlayer.getId());
			newPlayer.restore(origPlayer);
            //FIXME: this shuffling causes event to trigger that is not correct
            newPlayer.shuffleLibrary(mcts);
			mcts.getState().getPlayers().put(copyPlayer.getId(), newPlayer);
		}
		mcts.setSimulation(true);
		return mcts;
	}
    
}
