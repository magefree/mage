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

import mage.Constants.PhaseStep;
import mage.Constants.RangeOfInfluence;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.PassAbility;
import mage.cards.Card;
import mage.game.Game;
import mage.game.combat.Combat;
import mage.game.combat.CombatGroup;
import mage.player.ai.MCTSPlayer.NextAction;
import mage.players.Player;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ComputerPlayerMCTS extends ComputerPlayer<ComputerPlayerMCTS> implements Player {

    private static final int THINK_MIN_RATIO = 40;
    private static final int THINK_MAX_RATIO = 100;
    private static final double THINK_TIME_MULTIPLIER = 2.0;
    private static final boolean USE_MULTIPLE_THREADS = true;

    protected transient MCTSNode root;
    protected int maxThinkTime;
     private static final transient Logger logger = Logger.getLogger(ComputerPlayerMCTS.class);
    private transient ExecutorService pool;
    private int cores;

    public ComputerPlayerMCTS(String name, RangeOfInfluence range, int skill) {
        super(name, range);
        human = false;
        maxThinkTime = (int) (skill * THINK_TIME_MULTIPLIER);
        cores = Runtime.getRuntime().availableProcessors();
        pool = Executors.newFixedThreadPool(cores);
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
    public boolean priority(Game game) {
        if (game.getStep().getType() == PhaseStep.PRECOMBAT_MAIN)
            logList("computer player " + name + " hand: ", new ArrayList(hand.getCards(game)));
        game.getState().setPriorityPlayerId(playerId);
        game.firePriorityEvent(playerId);
        getNextAction(game, NextAction.PRIORITY);
        Ability ability =  root.getAction();
        if (ability == null)
            logger.fatal("null ability");
        activateAbility((ActivatedAbility)ability, game);
        if (ability instanceof PassAbility)
            return false;
        return true;
    }

    protected void calculateActions(Game game, NextAction action) {
        if (root == null) {
            Game sim = createMCTSGame(game);
            MCTSPlayer player = (MCTSPlayer) sim.getPlayer(playerId);
            player.setNextAction(action);
            root = new MCTSNode(sim);
        }
        applyMCTS(game, action);
        root = root.bestChild();
        root.emancipate();
    }

    protected void getNextAction(Game game, NextAction nextAction) {
        if (root != null) {
            MCTSNode newRoot;
            newRoot = root.getMatchingState(game.getState().getValue(false, game));
            if (newRoot != null) {
                newRoot.emancipate();
                logger.info("choose action:" + newRoot.getAction() + " success ratio: " + newRoot.getWinRatio());
            }
            else
                logger.info("unable to find matching state");
            root = newRoot;
        }
        calculateActions(game, nextAction);
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
//        getNextAction(game, NextAction.CHOOSE_USE);
//        return root.get
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
    public void selectAttackers(Game game, UUID attackingPlayerId) {
        getNextAction(game, NextAction.SELECT_ATTACKERS);
        Combat combat = root.getCombat();
        UUID opponentId = game.getCombat().getDefenders().iterator().next();
        for (UUID attackerId: combat.getAttackers()) {
            this.declareAttacker(attackerId, opponentId, game);
        }
    }

    @Override
    public void selectBlockers(Game game, UUID defendingPlayerId) {
        getNextAction(game, NextAction.SELECT_BLOCKERS);
        Combat combat = root.getCombat();
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

    protected void applyMCTS(final Game game, final NextAction action) {
        int thinkTime = calculateThinkTime(game, action);

        long startTime = System.nanoTime();
        long endTime = startTime + (thinkTime * 1000000000l);
        logger.info("applyMCTS - Thinking for " + (endTime - startTime)/1000000000.0 + "s");

        if (thinkTime > 0) {
            if (USE_MULTIPLE_THREADS) {
                List<MCTSExecutor> tasks = new ArrayList<MCTSExecutor>();
                for (int i = 0; i < cores; i++) {
                    Game sim = createMCTSGame(game);
                    MCTSPlayer player = (MCTSPlayer) sim.getPlayer(playerId);
                    player.setNextAction(action);
                    MCTSExecutor exec = new MCTSExecutor(sim, playerId, thinkTime);
                    tasks.add(exec);
                }

                try {
                    pool.invokeAll(tasks);
                } catch (InterruptedException ex) {
                    logger.warn("applyMCTS interrupted");
                }

                for (MCTSExecutor task: tasks) {
                    root.merge(task.getRoot());
                    task.clear();
                }
                tasks.clear();
            }
            else {
                MCTSNode current;
                int simCount = 0;
                while (true) {
                    long currentTime = System.nanoTime();
                    if (currentTime > endTime)
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
                logger.info("Simulated " + simCount + " games - nodes in tree: " + root.size());
            }
            displayMemory();
        }

//        root.print(1);
    }

    //try to ensure that there are at least THINK_MIN_RATIO simulations per node at all times
    private int calculateThinkTime(Game game, NextAction action) {
        int thinkTime;
        int nodeSizeRatio = 0;
        if (root.getNumChildren() > 0)
            nodeSizeRatio = root.getVisits() / root.getNumChildren();
        logger.info("Ratio: " + nodeSizeRatio);
        PhaseStep curStep = game.getStep().getType();
        if (action == NextAction.SELECT_ATTACKERS || action == NextAction.SELECT_BLOCKERS) {
            if (nodeSizeRatio < THINK_MIN_RATIO) {
                thinkTime = maxThinkTime;
            }
            else if (nodeSizeRatio >= THINK_MAX_RATIO) {
                thinkTime = 0;
            }
            else {
                thinkTime = maxThinkTime / 2;
            }
        }
        else if (game.getActivePlayerId().equals(playerId) && (curStep == PhaseStep.PRECOMBAT_MAIN || curStep == PhaseStep.POSTCOMBAT_MAIN) && game.getStack().isEmpty()) {
            if (nodeSizeRatio < THINK_MIN_RATIO) {
                thinkTime = maxThinkTime;
            }
            else if (nodeSizeRatio >= THINK_MAX_RATIO) {
                thinkTime = 0;
            }
            else {
                thinkTime = maxThinkTime / 2;
            }
        }
        else {
            if (nodeSizeRatio < THINK_MIN_RATIO) {
                thinkTime = maxThinkTime / 2;
            }
            else {
                thinkTime = 0;
            }
        }
        return thinkTime;
    }

    /**
     * Copies game and replaces all players in copy with mcts players
     * Shuffles each players library so that there is no knowledge of its order
     * Swaps all other players hands with random cards from the library so that
     * there is no knowledge of what cards are in opponents hands
     * The most knowledge that is known is what cards are in an opponents deck
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
            if (!newPlayer.getId().equals(playerId)) {
                int handSize = newPlayer.getHand().size();
                newPlayer.getLibrary().addAll(newPlayer.getHand().getCards(mcts), mcts);
                newPlayer.getHand().clear();
                newPlayer.getLibrary().shuffle();
                for (int i = 0; i < handSize; i++) {
                    Card card = newPlayer.getLibrary().removeFromTop(mcts);
                    mcts.setZone(card.getId(), Zone.HAND);
                    newPlayer.getHand().add(card);
                }
            }
            else {
                newPlayer.getLibrary().shuffle();                
            }
            mcts.getState().getPlayers().put(copyPlayer.getId(), newPlayer);
        }
        mcts.setSimulation(true);
        mcts.resume();
        return mcts;
    }

    protected void displayMemory() {
        long heapSize = Runtime.getRuntime().totalMemory();
        long heapMaxSize = Runtime.getRuntime().maxMemory();
        long heapFreeSize = Runtime.getRuntime().freeMemory();
        long heapUsedSize = heapSize - heapFreeSize;
        long mb = 1024 * 1024;

        logger.info("Max heap size: " + heapMaxSize/mb + " Heap size: " + heapSize/mb + " Used: " + heapUsedSize/mb);
    }

}
