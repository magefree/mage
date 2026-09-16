package mage.player.ai;

import java.util.*;

import mage.abilities.common.PassAbility;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.game.Game;
import mage.game.GameState;
import mage.player.ai.encoder.ActionEncoder;
import mage.players.Player;
import mage.players.PlayerScript;
import mage.util.RandomUtil;
import org.apache.log4j.Logger;
import java.util.Random;

import org.apache.commons.math3.distribution.GammaDistribution;
import org.apache.commons.math3.random.JDKRandomGenerator;

import static java.lang.Math.*;

/**
 *
 * @author BetaSteward_at_googlemail.com
 * @author willwroble@gmail.com
 *
 * this expanded MCTS system uses replay scripts to simulate micro decisions (CHOOSE_TARGET, MAKE_CHOICE, CHOOSE_AMOUNT, CHOOSE_USE).
 *
 */
public class MCTSNode {

    protected static final Logger logger = Logger.getLogger(MCTSNode.class);


    //neural network fields
    public float[] policy = null;
    public double networkScore;//initial score given from value network

    //shared (per tree)
    private final Game rootGame; //single game is reused with different states
    protected final ComputerPlayerMCTS basePlayer;
    protected final UUID targetPlayer;

    //node statistics
    private int visits = 0;
    private int depth = 1;
    private long dirichletSeed = 0;
    private double prior = 1;
    private double score = 0;

    //action fields - how the node represents the state - only one is not null at a time
    private Ability priorityAction;
    private Integer amountAction;
    private UUID targetAction;
    private String choiceAction;
    private Boolean useAction;

    //structure
    protected final List<MCTSNode> children = new ArrayList<>();
    protected int initialChildren;
    protected MCTSNode parent = null;

    //validation (these fields are populated in validateState)
    protected UUID playerId;
    private boolean terminal = false;
    private boolean winner;
    private boolean isRandomTransition = false;
    Set<Integer> stateVector; //encoder derived state vector (used for ML and validation)
    String stateString;
    ActionEncoder.ActionType actionType;
    private GameState state; //the saved logical game state of this node. Should always be a stable priority window
    //prefix scripts represent the sequence of actions that need to be taken since the last priority to represent this microstate
    private PlayerScript prefixScript = new PlayerScript();
    private PlayerScript opponentPrefixScript = new PlayerScript();



    /**
     * root constructor, mostly finalized, needs to be validated and pre-expanded externally before use.
     * @param targetPlayer
     * @param game
     */
    public MCTSNode(ComputerPlayerMCTS targetPlayer, Game game, ActionEncoder.ActionType actionType, PlayerScript prefixA, PlayerScript prefixB) {
        this.rootGame = game;
        this.state = game.getState().copy();
        this.basePlayer = targetPlayer;
        this.targetPlayer = targetPlayer.getId();
        this.terminal = game.checkIfGameIsOver();
        this.winner = isWinner(game, targetPlayer.getId());
        this.priorityAction = null; //root can have null action (prev action doesn't matter)
        this.actionType = actionType;
        this.prefixScript = prefixA;
        this.opponentPrefixScript = prefixB;
    }
    protected MCTSNode(MCTSNode parent) {
        this.parent = parent;
        this.targetPlayer = parent.targetPlayer;
        this.basePlayer = parent.basePlayer;
        this.rootGame= parent.rootGame;
    }
    protected MCTSNode createChild() {
        return new MCTSNode(this);
    }
    public Ability getPriorityAction() {
        return priorityAction;
    }
    public UUID getTargetAction() {
        return targetAction;
    }
    public String getChoiceAction() {
        return  choiceAction;
    }
    public boolean getUseAction() {
        return useAction;
    }
    public int getAmountAction() {
        return  amountAction;
    }
    public MCTSNode getMatchingState(Set<Integer> state, String stateString) {
        ArrayDeque<MCTSNode> queue = new ArrayDeque<>();
        queue.add(this);
        while (!queue.isEmpty()) {
            MCTSNode current = queue.remove();
            if(current.children.isEmpty()) continue; //tree can have unfinalized nodes
            if(current.stateVector.equals(state)) {
                return current;
            }
            queue.addAll(current.children);
        }
        return null;
    }
    public MCTSNode getMatchingStateInScope(Set<Integer> state, UUID scopePlayerId) {
        ArrayDeque<MCTSNode> queue = new ArrayDeque<>();
        queue.add(this);
        while (!queue.isEmpty()) {
            MCTSNode current = queue.remove();
            if(current.children.isEmpty()) continue; //tree can have unfinalized nodes
            if(current.stateVector.equals(state)) {
                return current;
            }
            if(current.playerId.equals(scopePlayerId) || current.children.size()==1) {
                queue.addAll(current.children);
            }
        }
        return null;
    }
    public List<MCTSNode> getChildren() {
        return children;
    }
    public MCTSNode getParent() {
        return parent;
    }
    public MCTSNode getRoot() {
        if(parent == null) {
            return this;
        }
        return parent.getRoot();
    }
    public MCTSNode getPlayerScope() {
        return getPlayerScope(playerId);
    }
    public MCTSNode getPlayerScope(UUID currentId) {
        if(parent == null) {
            return this;
        }
        if(!parent.playerId.equals(currentId) && parent.children.size() > 1) {
            return this;
        }
        return parent.getPlayerScope(currentId);
    }
    public Game getGame() {
        return rootGame;
    }
    /**
     * @return the player to act at this node
     */
    public MCTSPlayer getPlayer() {
        return (MCTSPlayer) rootGame.getPlayer(playerId);
    }
    public double getMeanScore() {
        if (getVisits() > 0)
            return (score)/((visits)* 1.0);
        return -1;
    }
    public int getVisits() {
        return visits;
    }
    public int getAverageVisits() {
        if(children.isEmpty()) return 0;
        return visits/children.size();
    }
    MCTSNode getChildOfCommonAncestor(MCTSNode node) {
        if(parent == null || parent ==  node) {
            logger.warn("duplicate of parent - gameplay loop found?");
            return this;
        }
        if(parent.isParentOf(node)) {
            return this;
        }
        return parent.getChildOfCommonAncestor(node);
    }
    public int getActionIndex(Game game) {
        ActionEncoder.ActionType actionType = parent.actionType;
        int idx;
        if(actionType == ActionEncoder.ActionType.PRIORITY) {
            idx = basePlayer.actionEncoder.getActionIndex(getPriorityAction(), parent.playerId.equals(targetPlayer));
        } else if(actionType == ActionEncoder.ActionType.CHOOSE_TARGET) {
            idx = basePlayer.actionEncoder.getTargetIndex(game.getEntityName(targetAction, targetPlayer));
        } else if(actionType == ActionEncoder.ActionType.CHOOSE_USE) {
            idx = useAction ? 1 : 0;
        } else {
            idx = -1;
        }
        return idx;
    }
    public String getOrderString(Game game) {
        StringBuilder sb = new StringBuilder();
        sb.append(getActionIndex(game));
        if(priorityAction != null) {
            sb.append(priorityAction.getSourceId());
        }
        if(targetAction != null) {
            sb.append(targetAction);
        }
        return sb.toString();
    }
    public boolean isRandomTransition() { return isRandomTransition; }

    public void setParent(MCTSNode node) {
        this.parent = node;
    }

    /**
     * @apiNote must be called directly before evaluation and expansion.
     * central engine call of MCTS system. uses XMage to validate the game state at this node and populates necessary fields
     */
    public void validateState() {
        PlayerScript myScript = new PlayerScript();
        PlayerScript opponentScript = new PlayerScript();
        populateActionScripts(myScript, opponentScript);
        GameState baseState;
        if(parent == null) {
            baseState = state.copy();
        } else {
            baseState = parent.state.copy();
        }
        resetRootGame(baseState);
        MCTSPlayer playerA = (MCTSPlayer) rootGame.getPlayer(targetPlayer);
        MCTSPlayer playerB =  (MCTSPlayer) rootGame.getOpponent(targetPlayer);
        playerA.actionScript = myScript; //set base player actions
        playerB.actionScript = opponentScript; //set opponent actions
        //will run until next decision (see MCTSPlayer)
        if(rootGame.getPhase() == null) {
            rootGame.getOptions().skipInitShuffling = true;
            rootGame.getState().resume();
            rootGame.start(rootGame.getState().getChoosingPlayerId());
        } else {
            rootGame.resume();
        }
        setPlayer(rootGame); //populates playerId with the player whose decision it is at this node

        this.terminal = rootGame.checkIfGameIsOver();
        this.winner = isWinner(rootGame, targetPlayer);
        this.prefixScript = new PlayerScript(playerA.getPlayerHistory());
        this.opponentPrefixScript = new PlayerScript(playerB.getPlayerHistory());

        if(this.terminal) return; //cant determine acting player after game has ended

        MCTSPlayer actingPlayer = (MCTSPlayer) rootGame.getPlayer(playerId);

        if(actingPlayer.scriptFailed) return; //dont calc state value and vector for failed scripts

        actionType = actingPlayer.getNextAction();
        stateVector = actingPlayer.getStateVector();
        stateString = rootGame.getState().getValue(rootGame, targetPlayer);
        if(parent != null) {
            if (actingPlayer.getNextAction() == ActionEncoder.ActionType.PRIORITY) {//priority point, use current state value
                this.state = rootGame.getState();
            } else {//micro point, use previous state value
                this.state = parent.state;
            }
        }
    }
    private void setPlayer(Game game) {
        for (Player p : game.getPlayers().values()) {
            MCTSPlayer mctsP = (MCTSPlayer) p;
            if(mctsP.isLastToAct()) {
                playerId = p.getId();
            }
            if(mctsP.isRandomTransition()) {
                isRandomTransition = true;
            }
        }
        if(playerId != null) {
            return;
        }
        if(game.checkIfGameIsOver()) {
            logger.info("TERMINAL STATE");
            terminal = true;
            winner = isWinner(game, targetPlayer);
            return;
        }
        logger.warn("this should not happen");
    }
    /**
     * resets root game to the given state
     * @param state state to reset to
     */
    public void resetRootGame(GameState state) {

        rootGame.setState(state);
        rootGame.getPlayerList().setCurrent(state.getPlayerByOrderId());
        // clear ephemeral caches / rebuild effects
        rootGame.resetLKI();
        rootGame.resetShortLivingLKI();
        rootGame.applyEffects(); // rebuild layers/CEs
        //for sanity
        for (Player p : rootGame.getPlayers().values()) {
            MCTSPlayer mp = (MCTSPlayer) p;
            mp.clearFields();
        }
    }
    /**
     * populates action lists by back tracing through the tree (opponent player is the non-target player)
     * @param myScript
     * @param opponentScript
     */
    public void populateActionScripts(PlayerScript myScript, PlayerScript opponentScript) {

        if(parent == null) {
            myScript.append(prefixScript);
            opponentScript.append(opponentPrefixScript);
            return;
        }
        myScript.append(parent.prefixScript);
        opponentScript.append(parent.opponentPrefixScript);

        if(parent.actionType.equals(ActionEncoder.ActionType.CHOOSE_TARGET)) {
            if(parent.playerId.equals(targetPlayer)) {
                myScript.targetSequence.add(targetAction);
            } else {
                opponentScript.targetSequence.add(targetAction);
            }
        } else if (parent.actionType.equals(ActionEncoder.ActionType.MAKE_CHOICE)) {
            if(parent.playerId.equals(targetPlayer)) {
                myScript.choiceSequence.add(choiceAction);
            } else {
                opponentScript.choiceSequence.add(choiceAction);
            }
        } else if(parent.actionType.equals(ActionEncoder.ActionType.CHOOSE_USE)) {
            if(parent.playerId.equals(targetPlayer)) {
                myScript.useSequence.add(useAction);
            } else {
                opponentScript.useSequence.add(useAction);
            }
        } else if(parent.actionType.equals(ActionEncoder.ActionType.CHOOSE_NUM)) {
            if (parent.playerId.equals(targetPlayer)) {
                myScript.numSequence.add(amountAction);
            } else {
                opponentScript.numSequence.add(amountAction);
            }
        } else if(parent.actionType.equals(ActionEncoder.ActionType.PRIORITY)) {
            if(parent.playerId.equals(targetPlayer)) {
                myScript.prioritySequence.add(priorityAction);
            } else {
                opponentScript.prioritySequence.add(priorityAction);
            }
        } else {
            logger.error("no action found in node");
        }
    }

    /**
     * searches tree for a legal priority node. not including root.
     * @return
     */
    public boolean containsLegalNode() {
        boolean found = false;
        for(MCTSNode child : children) {
            if(child.isLegalState()) {
                return true;
            }
            found |= child.containsLegalNode();
        }
        return found;
    }
    public void randomizeNoiseSeed() {
        dirichletSeed = RandomUtil.nextInt();
    }
    public MCTSNode select(UUID targetPlayerId) {
        if(children.isEmpty()) {
            logger.error("no children available for selection");
            return null;
        }
        // Single‐child shortcut
        if (children.size() == 1) {
            return children.get(0);
        }

        boolean isTarget = playerId.equals(targetPlayerId);
        double sign = isTarget ? +1.0 : -1.0;

        MCTSNode best    = null;
        double bestVal = Double.NEGATIVE_INFINITY;

        double sqrtN = Math.sqrt(getVisits());

        for (MCTSNode child : children) {
            // value term: 0 if unvisited, else average reward
            double q = (child.getVisits() > 0)
                    ? (child.getMeanScore())
                    : 0.0;
            // exploration term
            double u = ComputerPlayerMCTS.C_PUCT * (child.prior) * (sqrtN / (1 + child.getVisits()));

            // combined PUCT
            double val = sign * q + u;

            if (val > bestVal) {
                bestVal = val;
                best = child;
            }
        }

        // best should never be null once visits>0 on the root
        if(best == null) {
            logger.error("no best child. best val is: " + bestVal);
        }
        return best;
    }
    public List<MCTSNode> createChildren(ActionEncoder.ActionType actionType, MCTSPlayer player, Game game) {
        List<MCTSNode> children = new ArrayList<>();
        if(actionType == ActionEncoder.ActionType.PRIORITY) {
            for(Ability playable : player.playables) {
                logger.trace(game.getTurn().getValue(game.getTurnNum()) + " expanding: " + playable.toString());
                MCTSNode node = createChild();
                node.priorityAction = playable.copy();
                children.add(node);
            }
        } else if(actionType == ActionEncoder.ActionType.CHOOSE_TARGET) {
            Set<UUID> targetOptions = player.chooseTargetOptions;
            for(UUID target : targetOptions) {
                logger.trace(game.getTurn().getValue(game.getTurnNum()) + " expanding: " + game.getEntityName(target, targetPlayer));
                MCTSNode node = createChild();
                node.targetAction = target;
                children.add(node);
            }
        } else if(actionType == ActionEncoder.ActionType.MAKE_CHOICE) {
            Set<String> choiceOptions = player.choiceOptions;
            for(String choice : choiceOptions) {
                logger.trace(game.getTurn().getValue(game.getTurnNum()) + " expanding: " + choice);
                MCTSNode node = createChild();
                node.choiceAction = choice;
                children.add(node);
            }
        } else if(actionType == ActionEncoder.ActionType.CHOOSE_NUM) {
            for (int mode = 0; mode < player.numOptionsSize; mode++) {
                logger.trace(game.getTurn().getValue(game.getTurnNum()) + " expanding: " + mode);
                MCTSNode node = createChild();
                node.amountAction = mode;
                children.add(node);
            }
        } else if(actionType == ActionEncoder.ActionType.CHOOSE_USE) {
            MCTSNode nodeTrue = createChild();
            MCTSNode nodeFalse = createChild();
            nodeTrue.useAction = true;
            nodeFalse.useAction = false;
            //always add false before true
            children.add(nodeFalse);
            children.add(nodeTrue);
            logger.trace(game.getTurn().getValue(game.getTurnNum()) + " expanding: true");
            logger.trace(game.getTurn().getValue(game.getTurnNum()) + " expanding: false");
        } else {
            logger.error("unknown nextAction");
        }
        initialChildren = children.size();
        children.sort(Comparator.comparing(n -> n.getOrderString(game)));
        return children;
    }
    public void expand() {

        MCTSPlayer player = (MCTSPlayer) rootGame.getPlayer(playerId);
        if (player.getNextAction() == null) {
            logger.fatal("next action is null");
        }
        ActionEncoder.ActionType actionType = player.getNextAction();
        children.addAll(createChildren(actionType, player, rootGame));
        logger.debug(children.size() + " children expanded");
        for (MCTSNode node : children) {
            node.depth = depth + 1;
            node.prior = 1.0/children.size();
        }
    }
    public synchronized void setPriors() {
        if (policy != null && actionType != ActionEncoder.ActionType.MAKE_CHOICE && actionType != ActionEncoder.ActionType.CHOOSE_NUM) {

            double priorTemperature = basePlayer.priorTemp; // This controls 'spikiness' of prior distribution; higher means less spiky

            //find max logit for numeric stability
            double maxLogit = Float.NEGATIVE_INFINITY;
            for (MCTSNode node : children) {
                int idx = node.getActionIndex(rootGame);
                maxLogit = Math.max(maxLogit, policy[idx]);
            }

            //compute raw exps and sum
            double sumExp = 0;
            for (MCTSNode node : children) {
                int idx = node.getActionIndex(rootGame);
                double raw = Math.exp((policy[idx] - maxLogit)/priorTemperature);
                node.prior = raw;
                sumExp += raw;
            }

            // 4) normalize in place
            for (MCTSNode node : children) {
                node.prior /= sumExp;
                //assign small exploration bonus to non-mana abilities
                if(node.priorityAction == null || (!node.priorityAction.isManaAbility() && !(node.priorityAction instanceof PassAbility))) {
                    node.prior += basePlayer.priorBonus;
                }
            }

            long seed = this.dirichletSeed;
            if (seed != 0) {
                logger.warn("using dirichlet seed: " + seed);
                double alpha = ComputerPlayerMCTS.DIRICHLET_NOISE_ALPHA;
                double eps = basePlayer.dirichletNoiseEps;
                int K = children.size();
                double[] dir = new double[K];
                double sum = 0;

                JDKRandomGenerator rg = new JDKRandomGenerator();
                rg.setSeed(seed);

                GammaDistribution gd = new GammaDistribution(rg, alpha, 1.0);

                for (int i = 0; i < K; i++) {
                    dir[i] = gd.sample();
                    sum += dir[i];
                }
                for (int i = 0; i < K; i++) {
                    dir[i] /= sum;
                    children.get(i).prior = (1 - eps) * children.get(i).prior + eps * dir[i];
                }

                // 4) mark done
                dirichletSeed = 0;
            }
        }
    }
    public void backpropagate(double result) {
        backpropagate(result, 1);
    }
    public synchronized void backpropagate(double result, int n) {

        visits+=n;
        score += result;

        if (parent != null) {
            parent.backpropagate(result * basePlayer.backpropDiscount, n);
        }

    }
    public MCTSNode bestChild(Game baseGame) {
        ComputerPlayerMCTS myPlayer = basePlayer;
        if (children.size() == 1) {
            if(children.get(0).isLegalState() || children.get(0).containsLegalNode()) {
                return children.get(0);
            } else {
                return null;
            }
        }
        //mask illegal moves
        for(MCTSNode node : children) {
            if(!(node.isLegalState() || node.containsLegalNode())) {
                node.reset();
            }
        }
        StringBuilder sb = new StringBuilder();
        if(baseGame.getTurnStepType() == null) {
            sb.append("pre-game");
        } else {
            sb.append(baseGame.getTurnStepType().toString());
        }
        HashMap<String, MCTSNode> actionNames = new HashMap<>();
        sb.append(baseGame.getStack().toString());
        sb.append("pool=").append(myPlayer.getManaPool().getMana());
        sb.append(" actions: ");
        for (MCTSNode node: children) {
            if(node.targetAction != null) {
                sb.append(String.format("[%s score: %.3f count: %d] ", baseGame.getEntityName(node.targetAction, targetPlayer), node.getMeanScore(), node.getVisits()));
            } else if(node.choiceAction != null) {
                sb.append(String.format("[%s score: %.3f count: %d] ", node.choiceAction, node.getMeanScore(), node.getVisits()));
            } else if(node.useAction != null) {
                sb.append(String.format("[%s score: %.3f count: %d] ", node.useAction, node.getMeanScore(), node.getVisits()));
            } else if(node.amountAction != null) {
                sb.append(String.format("[%s score: %.3f count: %d] ", node.amountAction, node.getMeanScore(), node.getVisits()));
            } else if(node.priorityAction != null){
                sb.append(String.format("[%s score: %.3f count: %d] ", node.priorityAction, node.getMeanScore(), node.getVisits()));
                if(actionNames.containsKey(node.priorityAction.toString()) && actionNames.get(node.priorityAction.toString()) != null && actionNames.get(node.priorityAction.toString()).stateVector != null) {
                    logger.warn("FOUND DUPLICATE ACTION " + node.priorityAction.toString());
                    if(node.stateVector != null) {
                        HashSet<Integer> intersection = new HashSet<>(actionNames.get(node.priorityAction.toString()).stateVector);
                        intersection.retainAll(node.stateVector);
                        HashSet<Integer> onlyA = new HashSet<>(actionNames.get(node.priorityAction.toString()).stateVector);
                        onlyA.removeAll(intersection);
                        HashSet<Integer> onlyB = new HashSet<>(node.stateVector);
                        onlyB.removeAll(intersection);
                        logger.warn("ONLY IN A: " + onlyA);
                        logger.warn("ONLY IN B: " + onlyB);
                    }
                } else {
                    actionNames.put(node.priorityAction.toString(), node);
                }
            } else {
                logger.error("no action in node");
            }
        }
        if(!children.isEmpty() && !myPlayer.allMana) {
            logger.info(sb.toString());
        }
        //derive temp from value
        double temperature = basePlayer.selectionTemperature;

        //normal selection
        if (dirichletSeed==0 || temperature < 0.01) {
            MCTSNode best = null;
            double bestCount = 0;
            for (MCTSNode node : children) {
                if (node.getVisits() > bestCount) {
                    best = node;
                    bestCount = node.getVisits();
                }
            }
            return best;
        }

        //temp-based sampling selection
        List<Double> logProbs = new ArrayList<>();
        double maxLogProb = Double.NEGATIVE_INFINITY;
        for (MCTSNode node : children) {
            double logProb = Math.log(node.getVisits()) / temperature;
            logProbs.add(logProb);
            if (logProb > maxLogProb) {
                maxLogProb = logProb;
            }
        }
        if(maxLogProb == Double.NEGATIVE_INFINITY) {
            return null;
        }
        List<Double> probabilities = new ArrayList<>();
        double distributionSum = 0.0;
        for (double logProb : logProbs) {
            double probability = Math.exp(logProb - maxLogProb);
            probabilities.add(probability);
            distributionSum += probability;
        }

        for (int i = 0; i < probabilities.size(); i++) {
            probabilities.set(i, probabilities.get(i) / distributionSum);
        }

        double randomValue = new Random().nextDouble();
        double cumulativeProbability = 0.0;
        for (int i = 0; i < children.size(); i++) {
            cumulativeProbability += probabilities.get(i);
            if (randomValue <= cumulativeProbability) {
                return children.get(i);
            }
        }

        return null;
    }

    public void emancipate() {
        if (parent != null) {
            this.parent.children.remove(this);
            this.parent = null;
        }
    }
    public void prune(MCTSNode node) {
        if (!children.contains(node)) {
            logger.error("invalid prune");
            return;
        }
        children.remove(node);
        node.parent = null;

        if (!children.isEmpty() || parent == null) {
            //correct MCTS stats
            if (node.visits > 0) {
                backpropagate(-node.score * basePlayer.backpropDiscount, -node.getVisits());
            }
        } else {
            parent.prune(this);
        }

    }
    /*
     * Shuffles each players library so that there is no knowledge of its order
     * Swaps all other players hands with random cards from the library so that
     * there is no knowledge of what cards are in opponents hands
     */
    protected void randomizePlayers(Game game, UUID playerId) {
        for (Player player: game.getState().getPlayers().values()) {
            if (!player.getId().equals(playerId)) {
                int handSize = player.getHand().size();
                player.getLibrary().addAll(player.getHand().getCards(game), game);
                player.getHand().clear();
                player.getLibrary().shuffle();
                for (int i = 0; i < handSize; i++) {
                    Card card = player.getLibrary().drawFromTop(game);
                    card.setZone(Zone.HAND, game);
                    player.getHand().add(card);
                }
            }
            else {
                player.getLibrary().shuffle();
            }
        }
    }

    public boolean isTerminal() {
        return terminal;
    }
    public boolean isLeaf() {
        return children.isEmpty();
    }
    public boolean isValidated() {
        return terminal || !children.isEmpty();
    }
    boolean isParentOf(MCTSNode node) {
        if(node.parent == null || this == node) {
            return false;
        }
        if(node.parent == this) {
            return true;
        }
        return isParentOf(node.parent);
    }
    public boolean isLegalState() {
        if(this.terminal) return true;
        if(children.isEmpty()) return false;
        return actionType.equals(ActionEncoder.ActionType.PRIORITY);
    }
    public boolean isWinner(Game game, UUID playerId) {
        if (game != null) {
            Player player = game.getPlayer(playerId);
            return player != null && player.hasWon();
        }
        return false;
    }
    public boolean isWinner() {
        return winner;
    }

    public int size() {
        int num = 1;
        for (MCTSNode child : children) {
            num += child.size();
        }
        return num;
    }
    public int maxDepth() {
        int max = 0;
        for (MCTSNode child : children) {
            max = Math.max(max, child.maxDepth());
        }
        return max+1;
    }

    public void reset() {
        children.clear();
        score = 0;
        visits = 0;
        depth = 1;
    }
    /**
     * Copies game and replaces all players in copy with simulated players
     * Shuffles each players library so that there is no knowledge of its order
     *
     * @param game
     * @return a new game object with simulated players
     */
    @Deprecated
    protected Game createSimulation(Game game, UUID playerId) {
        Game sim = game.createSimulationForAI();

        for (Player oldPlayer: sim.getState().getPlayers().values()) {
            Player origPlayer = game.getState().getPlayers().get(oldPlayer.getId()).copy();
            SimulatedPlayerMCTS newPlayer = new SimulatedPlayerMCTS(oldPlayer, true);
            newPlayer.restore(origPlayer);
            sim.getState().getPlayers().put(oldPlayer.getId(), newPlayer);
        }
        randomizePlayers(sim, playerId);
        return sim;
    }
    @Deprecated
    public int simulate(UUID playerId, Game game) {
        Game sim = createSimulation(game, playerId);
        sim.resume();
        int retVal = -1;  //anything other than a win is a loss
        for (Player simPlayer: sim.getPlayers().values()) {
            if (simPlayer.getId().equals(playerId) && simPlayer.hasWon()) {
                logger.info("AI won the simulation");
                retVal = 1;
            }
        }
        return retVal;
    }
}