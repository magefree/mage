package mage.player.ai;


import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.game.Game;
import mage.game.GameException;
import mage.player.ai.encoder.ActionEncoder;
import mage.player.ai.encoder.StateEncoder;
import mage.player.ai.score.GameStateEvaluator2;
import mage.players.Player;
import mage.players.PlayerScript;
import mage.util.RandomUtil;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 *
 * AlphaZero style MCTS that uses a Neural Network with the PUCT formula. Supports almost all decision points for MTG (priority, choose_target, choose_use, make_choice, choose_mode)
 * All decision types besides make_choice have their own learnable priors as heads of the neural network. priorityA and priorityB are deck dependent; choose_target and choose_use are matchup dependent.
 * See StateEncoder.java for how MTG states are vectorized for the network.
 *
 *
 * @author WillWroble
 */
public class ComputerPlayerMCTS2 extends ComputerPlayerMCTS {

    private static final Logger logger = Logger.getLogger(ComputerPlayerMCTS2.class);
    public AtomicInteger pendingNodes =  new AtomicInteger(0);

    /** How many concurrent network eval calls are allowed per tree. keep small to keep MCTS expansion deterministic */
    public static int MAX_PENDING = 4;
    /** 0 means use random seed*/
    public static long DEFAULT_SEED = 2612645407030963366L;
    public static boolean SHOW_THREAD_INFO = true;
    /**if offline mode is on it won't use a neural network and will instead use a heuristic value function and uniform priors.
    is enabled by default if no network is found*/
    public boolean offlineMode = false;
    public String defaultURL = "http://127.0.0.1:50052";
    public transient RemoteModelEvaluator nn;
    MCTSNode2 root;
    public boolean allowDuplicates = true;



    public ComputerPlayerMCTS2(String name, RangeOfInfluence range, int skill) {
        super(name, range, skill);
        if(stateEncoder == null){
            long gameSeed;
            //declare random seed
            if(DEFAULT_SEED == 0) {
                gameSeed = ThreadLocalRandom.current().nextLong();
            } else {
                gameSeed = DEFAULT_SEED;
            }
            logger.info("Using seed: " + gameSeed);
            RandomUtil.setSeed(gameSeed);
        }
    }

    protected ComputerPlayerMCTS2(UUID id) {
        super(id);
        if(stateEncoder == null){
            long gameSeed;
            //declare random seed
            if(DEFAULT_SEED == 0) {
                gameSeed = ThreadLocalRandom.current().nextLong();
            } else {
                gameSeed = DEFAULT_SEED;
            }
            logger.info("Using seed: " + gameSeed);
            RandomUtil.setSeed(gameSeed);
        }
    }

    public ComputerPlayerMCTS2(final ComputerPlayerMCTS2 player) {
        super(player);
        nn = player.nn;
        offlineMode = player.offlineMode;
        defaultURL = player.defaultURL;
    }
    public void RLInit(Game game) {
        logger.info("RL init for " + getName() + " (MZ ver1.0.2)");
        Player opponent = game.getOpponent(playerId);
        //make encoder
        stateEncoder = new StateEncoder();
        stateEncoder.setAgent(getId());
        stateEncoder.setOpponent(opponent.getId());
        //find model endpoint
        try {
            nn = new RemoteModelEvaluator(defaultURL);
        } catch (Exception e) {
            logger.warn("Failed to establish connection to network model; falling back to offline mode");
            offlineMode = true;
        }

    }
    public static void printAllActionsFromDeck(Deck deck, ActionEncoder actionEncoder) throws GameException {
        logger.info("createAllActionsFromDeck; deck size: " + deck.getCards().size());
        if (deck.getMaindeckCards().size() < 40) {
            throw new IllegalArgumentException("Couldn't load deck, deck size=" + deck.getMaindeckCards().size());
        }
        //pass is always 0
        List<Card> sortedCards = new ArrayList<>(deck.getCards());
        sortedCards.sort(Comparator.comparing(Card::getName));
        for(Card card : sortedCards) {
            List<Ability> sortedAbilities = new ArrayList<>(card.getAbilities());
            sortedAbilities.sort(Comparator.comparing(Ability::toString));
            for(Ability aa : sortedAbilities) {
                if(aa instanceof ActivatedAbility) {
                    String name = aa.toString();
                    logger.info("ACTION: " + name + "maps to " + actionEncoder.getActionIndex(aa, true));
                }
            }
        }
    }
    @Override
    public ComputerPlayerMCTS2 copy() {
        return new ComputerPlayerMCTS2(this);
    }
    @Override
    public boolean priority(Game game) {
        if (game.getTurnStepType() == PhaseStep.END_TURN) {
            GameStateEvaluator2.printBattlefield(game, game.getActivePlayerId());
        }
        return super.priority(game);
    }
    /**
     * core MCTS search implementation, with legality and transposition pruning
     */
    @Override
    protected void applyMCTS(final Game game, final ActionEncoder.ActionType action) {
        if(!action.equals(ActionEncoder.ActionType.PRIORITY)) {
            allMana = false;
        }
        int initialVisits = root.getVisits();
        int childVisits = getChildVisitsFromRoot().stream().mapToInt(Integer::intValue).sum();
        if (SHOW_THREAD_INFO) logger.info(String.format("STARTING ROOT VISITS: %d", initialVisits));


        double totalThinkTimeThisMove = 0;

        // Apply dirichlet noise once at the start of the search for this turn
        if(!noNoise) root.randomizeNoiseSeed();

        long startTime = System.nanoTime();
        long endTime = (long) (startTime + (searchTimeout * 1_000_000_000L));
        long maxEndTime = startTime + 60_000_000_000L;
        int simCount = 0;
        int illegalPurged = 0;
        int validDuplicatesPurged = 0;
        int invalidDuplicatesPurged = 0;


        while (true) {
            if(System.nanoTime() > maxEndTime) {
                logger.warn("force time out after one minute - couldn't find legal move");
                break;
            }
            if(root.size() >= MAX_TREE_NODES) {
                logger.info("too many nodes in tree, ending search");
                break;
            }
            if(root.maxDepth() >= MAX_TREE_DEPTH) {
                logger.info("tree is too deep, ending search");
                break;
            }
            if(root.containsLegalNode()) { //can only normal exit search if the tree contains a priority node (meaning a future legal state)
                if (System.nanoTime() > endTime) {
                    logger.info("timed out, ending search");
                    break;
                }
                if (root.getVisits() >= searchBudget) {
                    logger.info("required visits reached, ending search");
                    break;
                }
            }

            MCTSNode2 current = root;

            // selection
            while (!current.isLeaf() || current.evaluationPending) {
                current.awaitEvaluation();
                current = (MCTSNode2) current.select(this.playerId);
            }
            if(current.getParent() == null) {
                logger.error("root not pre-expanded");
            }
            if(!current.isTerminal()) {//if terminal is true current must be finalized so skip getGame()

                current.validateState();//can become terminal here

                if(!current.isTerminal()) {
                    //remove child if failed script
                    if (current.getPlayer().scriptFailed) {
                        illegalPurged++;
                        current.getParent().prune(current);
                        continue;
                    }
                    //remove child if node is already in the tree
                    MCTSNode2 match = (MCTSNode2) current.getPlayerScope(current.parent.playerId).getMatchingStateInScope(current.stateVector, current.parent.playerId);
                    while(match != null && !allowDuplicates) {
                        MCTSNode oldPath = match.getChildOfCommonAncestor(current);
                        MCTSNode newPath = current.getChildOfCommonAncestor(match);
                        //use common ancestor visits as canonical decider
                        if (newPath.parent == match || newPath.getVisits() <= oldPath.getVisits()) {
                            //prune new
                            validDuplicatesPurged++;
                            current.backpropagate(match.getMeanScore());
                            current.getParent().prune(current);
                            current = null;
                            break;
                        } else {
                            //prune old
                            invalidDuplicatesPurged++;
                            match.getParent().prune(match);
                            match = (MCTSNode2) current.getPlayerScope(current.parent.playerId).getMatchingStateInScope(current.stateVector, current.parent.playerId);
                        }
                    }
                    if(current==null) {
                        continue;
                    }
                }
            }
            double result;
            if (!current.isTerminal()) {
                // eval
                current.evaluate();
                //expand
                current.expand();
                //temporary result (virtual loss)
                result = -1;

            } else {
                result = current.isWinner() ? 1.0 : -1.0;
                logger.debug("found terminal node in tree");
            }
            // backprop
            current.backpropagate(result);
            simCount++;
        }
        totalSimulations += simCount;
        totalThinkTimeThisMove = (System.nanoTime() - startTime)/1e9;
        totalThinkTime += totalThinkTimeThisMove;

        if (SHOW_THREAD_INFO && !allMana) {
            logger.info(String.format("Pending Nodes: %d", pendingNodes.get()));
            logger.info(String.format("Ran %d simulations.", simCount));
            logger.info(String.format("COMPOSITE CHILDREN: %s", getChildVisitsFromRoot().toString()));
            logger.info("Player: " + name + " simulated " + simCount + " evaluations in " + totalThinkTimeThisMove
                    + " seconds - nodes in tree: " + root.size());
            logger.info("Total: simulated " + totalSimulations + " evaluations in " + totalThinkTime
                    + " seconds - Average: " + (totalThinkTime > 0 ? totalSimulations / totalThinkTime : 0));
            logger.info(illegalPurged + " illegals purged, " + validDuplicatesPurged + " valid duplicates purged, " +  invalidDuplicatesPurged + " invalid duplicates purged.");
        }
    }
    int[] getActionVec(MCTSNode node, Game game) {
        int[] out = new int[128];
        Arrays.fill(out, 0);
        for (MCTSNode child : node.getChildren()) {
            int idx = child.getActionIndex(game);
            if (idx < 0) {
                return null;
            }
            int v = child.getVisits();//un normalized counts
            out[idx%128] += v;
        }
        return out;
    }

    @Override
    protected MCTSNode2 getNextAction(Game game, ActionEncoder.ActionType actionType) {

        if(stateEncoder == null) {
            RLInit(game);
        }
        if(actionEncoder == null) {
            actionEncoder = new ActionEncoder();
            try {
                printAllActionsFromDeck(getMatchPlayer().getDeck(), actionEncoder);
            } catch (GameException e) {
                throw new RuntimeException(e);
            }
        }

        Game sim = createMCTSGame(game.getLastPriority());
        PlayerScript prefixScript = new PlayerScript(getPlayerHistory());
        PlayerScript opponentPrefixScript = new PlayerScript(game.getOpponent(playerId).getPlayerHistory());
        MCTSNode2 newRoot = new MCTSNode2(this, sim, actionType, prefixScript, opponentPrefixScript);

        //do first expansion automatically
        newRoot.validateState();
        newRoot.expand();
        newRoot.evaluate();

        if(!prefixScript.isEmpty() || !opponentPrefixScript.isEmpty() ) {
            logger.info("prefix at root: " + prefixScript);
            logger.info("opponent prefix at root: " + opponentPrefixScript);
        }
        if (root != null) {
            root = (MCTSNode2) root.getMatchingState(newRoot.stateVector, newRoot.stateString);
        }
        if (root == null) {
            root = newRoot;
        }
        root.emancipate();
        MCTSNode2 out = (MCTSNode2) calculateActions(game, actionType);
        //first try regenerating tree (allowing duplicates)
        if(out == null) {
            logger.warn("invalid tree - regenerating from scratch");
            //allowDuplicates = true;
            root = newRoot;
            out = (MCTSNode2) calculateActions(game, actionType);
        }
        if(out == null) {
            logger.warn("no best child");
        }
        //allowDuplicates = false;
        return out;
    }
    @Override
    protected MCTSNode calculateActions(Game game, ActionEncoder.ActionType action) {

        applyMCTS(game, action);

        MCTSNode best = root.bestChild(game);

        int[] actionVec = getActionVec(root, game);

        if(actionVec != null) stateEncoder.addLabeledState(root.stateVector, actionVec, root.getMeanScore(), action, true);

        return best;

    }
    /**
     * Helper method to get the visit counts of the root's children for a single tree.
     */
    private List<Integer> getChildVisitsFromRoot() {
        if (root == null || root.getChildren().isEmpty()) {
            return new ArrayList<>();
        }
        return root.getChildren().stream().map(MCTSNode::getVisits).collect(Collectors.toList());
    }
}