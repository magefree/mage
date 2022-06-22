
package mage.player.ai;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.PlayLandAbility;
import mage.abilities.common.PassAbility;
import mage.cards.Card;
import mage.game.Game;
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

    public static final boolean USE_ACTION_CACHE = false;
    private static final double selectionCoefficient = Math.sqrt(2.0);
    private static final double passRatioTolerance = 0.0;
    private static final Logger logger = Logger.getLogger(MCTSNode.class);

    private int visits = 0;
    private int wins = 0;
    private MCTSNode parent;
    private final List<MCTSNode> children = new ArrayList<>();
    private Ability action;
    private Game game;
    private Combat combat;
    private final String stateValue;
    private final String fullStateValue;
    private UUID playerId;
    private boolean terminal = false;
    private UUID targetPlayer;

    private static int nodeCount;

    public MCTSNode(UUID targetPlayer, Game game) {
        this.targetPlayer = targetPlayer;
        this.game = game;
        this.stateValue = game.getState().getValue(game, targetPlayer);
        this.fullStateValue = game.getState().getValue(true, game);
        this.terminal = game.checkIfGameIsOver();
        setPlayer();
        nodeCount = 1;
//        logger.info(this.stateValue);
    }    

    protected MCTSNode(MCTSNode parent, Game game, Ability action) {
        this.targetPlayer = parent.targetPlayer;
        this.game = game;
        this.stateValue = game.getState().getValue(game, targetPlayer);
        this.fullStateValue = game.getState().getValue(true, game);
        this.terminal = game.checkIfGameIsOver();
        this.parent = parent;
        this.action = action;
        setPlayer();
        nodeCount++;
//        logger.info(this.stateValue);
    }

    protected MCTSNode(MCTSNode parent, Game game, Combat combat) {
        this.targetPlayer = parent.targetPlayer;
        this.game = game;
        this.combat = combat;
        this.stateValue = game.getState().getValue(game, targetPlayer);
        this.fullStateValue = game.getState().getValue(true, game);
        this.terminal = game.checkIfGameIsOver();
        this.parent = parent;
        setPlayer();
        nodeCount++;
//        logger.info(this.stateValue);
    }

    private void setPlayer() {
        if (game.getStep().getStepPart() == StepPart.PRIORITY) {
            playerId = game.getPriorityPlayerId();
        } else {
            if (game.getStep().getType() == PhaseStep.DECLARE_BLOCKERS) {
                playerId = game.getCombat().getDefenders().iterator().next();
            } else {
                playerId = game.getActivePlayerId();
            }
        }
    }

    public MCTSNode select(UUID targetPlayerId) {
        double bestValue = Double.NEGATIVE_INFINITY;
        boolean isTarget = playerId.equals(targetPlayerId);
        MCTSNode bestChild = null;
        if (children.size() == 1) {
            return children.get(0);
        }
        for (MCTSNode node: children) {
            double uct;
            if (node.visits > 0)
                if (isTarget)
                    uct = (node.wins / (node.visits)) + (selectionCoefficient * Math.sqrt(Math.log(visits) / (node.visits)));
                else
                    uct = ((node.visits - node.wins) / (node.visits)) + (selectionCoefficient * Math.sqrt(Math.log(visits) / (node.visits)));
            else
                // ensure that a random unvisited node is played first
                uct = 10000 + 1000 * Math.random();
            if (uct > bestValue) {
                bestChild = node;
                bestValue = uct;
            }
        }
        return bestChild;
    }

    public void expand() {
        MCTSPlayer player = (MCTSPlayer) game.getPlayer(playerId);
        if (player.getNextAction() == null) {
            logger.fatal("next action is null");
        }
        switch (player.getNextAction()) {
            case PRIORITY:
//                logger.info("Priority for player:" + player.getName() + " turn: " + game.getTurnNum() + " phase: " + game.getPhase().getType() + " step: " + game.getStep().getType());
                List<Ability> abilities;
                if (!USE_ACTION_CACHE)
                    abilities = player.getPlayableOptions(game);
                else
                    abilities = getPlayables(player, fullStateValue, game);
                for (Ability ability: abilities) {
                    Game sim = game.copy();
//                    logger.info("expand " + ability.toString());
                    MCTSPlayer simPlayer = (MCTSPlayer) sim.getPlayer(player.getId());
                    simPlayer.activateAbility((ActivatedAbility)ability, sim);
                    sim.resume();
                    children.add(new MCTSNode(this, sim, ability));
                }
                break;
            case SELECT_ATTACKERS:
//                logger.info("Select attackers:" + player.getName());
                List<List<UUID>> attacks;
                if (!USE_ACTION_CACHE)
                    attacks = player.getAttacks(game);
                else
                    attacks = getAttacks(player, fullStateValue, game);
                UUID defenderId = game.getOpponents(player.getId()).iterator().next();
                for (List<UUID> attack: attacks) {
                    Game sim = game.copy();
                    MCTSPlayer simPlayer = (MCTSPlayer) sim.getPlayer(player.getId());
                    for (UUID attackerId: attack) {
                        simPlayer.declareAttacker(attackerId, defenderId, sim, false);
                    }
                    sim.resume();
                    children.add(new MCTSNode(this, sim, sim.getCombat()));
                }
                break;
            case SELECT_BLOCKERS:
//                logger.info("Select blockers:" + player.getName());
                List<List<List<UUID>>> blocks;
                if (!USE_ACTION_CACHE)
                    blocks = player.getBlocks(game);
                else
                    blocks = getBlocks(player, fullStateValue, game);
                for (List<List<UUID>> block: blocks) {
                    Game sim = game.copy();
                    MCTSPlayer simPlayer = (MCTSPlayer) sim.getPlayer(player.getId());
                    List<CombatGroup> groups = sim.getCombat().getGroups();
                    for (int i = 0; i < groups.size(); i++) {
                        if (i < block.size()) {
                            for (UUID blockerId: block.get(i)) {
                                simPlayer.declareBlocker(simPlayer.getId(), blockerId, groups.get(i).getAttackers().get(0), sim);
                            }
                        }
                    }
                    sim.resume();
                    children.add(new MCTSNode(this, sim, sim.getCombat()));
                }
                break;
        }
        game = null;
    }

    public int simulate(UUID playerId) {
//        long startTime = System.nanoTime();
        Game sim = createSimulation(game, playerId);
        sim.resume();
//        long duration = System.nanoTime() - startTime;
        int retVal = -1;  //anything other than a win is a loss
        for (Player simPlayer: sim.getPlayers().values()) {
//            logger.info(simPlayer.getName() + " calculated " + ((SimulatedPlayerMCTS)simPlayer).getActionCount() + " actions in " + duration/1000000000.0 + "s");
            if (simPlayer.getId().equals(playerId) && simPlayer.hasWon()) {
//                logger.info("AI won the simulation");
                retVal = 1;
            }
        }
        return retVal;
    }

    public void backpropagate(int result) {
        if (result == 0)
            return;
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
        if (children.size() == 1)
            return children.get(0);
        double bestCount = -1;
        double bestRatio = 0;
        boolean bestIsPass = false;
        MCTSNode bestChild = null;
        for (MCTSNode node: children) {
            //favour passing vs any other action except for playing land if ratio is close
            if (node.visits > bestCount) {
                if (bestIsPass) {
                    double ratio = node.wins/(node.visits * 1.0);
                    if (ratio < bestRatio + passRatioTolerance)
                        continue;
                }
                bestChild = node;
                bestCount = node.visits;
                bestRatio = node.wins/(node.visits * 1.0);
                bestIsPass = false;
            }
            else if (node.action instanceof PassAbility && node.visits > 10 && !(bestChild.action instanceof PlayLandAbility)) {
                //favour passing vs any other action if ratio is close
                double ratio = node.wins/(node.visits * 1.0);
                if (ratio > bestRatio - passRatioTolerance) {
                    logger.info("choosing pass over " + bestChild.getAction());
                    bestChild = node;
                    bestCount = node.visits;
                    bestRatio = ratio;
                    bestIsPass = true;
                }
            }
        }
        return bestChild;
    }

    public void emancipate() {
        if (parent != null) {
            this.parent.children.remove(this);
            this.parent = null;
        }
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

    public String getStateValue() {
        return stateValue;
    }

    public double getWinRatio() {
        if (visits > 0)
            return wins/(visits * 1.0);
        return -1.0;
    }

    public int getVisits() {
        return visits;
    }

    /**
     * Copies game and replaces all players in copy with simulated players
     * Shuffles each players library so that there is no knowledge of its order
     *
     * @param game
     * @return a new game object with simulated players
     */
    protected Game createSimulation(Game game, UUID playerId) {
        Game sim = game.copy();

        for (Player oldPlayer: sim.getState().getPlayers().values()) {
            Player origPlayer = game.getState().getPlayers().get(oldPlayer.getId()).copy();
            SimulatedPlayerMCTS newPlayer = new SimulatedPlayerMCTS(oldPlayer, true);
            newPlayer.restore(origPlayer);
            sim.getState().getPlayers().put(oldPlayer.getId(), newPlayer);
        }
        randomizePlayers(sim, playerId);
        sim.setSimulation(true);
        return sim;
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
                    Card card = player.getLibrary().removeFromTop(game);
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

    public boolean isWinner(UUID playerId) {
        if (game != null) {
            Player player = game.getPlayer(playerId);
            if (player != null && player.hasWon())
                return true;
        }
        return false;
    }

    /**
     * 
     * performs a breadth first search for a matching game state
     * 
     * @param state - the game state that we are looking for
     * @return the matching state or null if no match is found
     */
    public MCTSNode getMatchingState(String state) {
        ArrayDeque<MCTSNode> queue = new ArrayDeque<>();
        queue.add(this);

        while (!queue.isEmpty()) {
            MCTSNode current = queue.remove();
            if (current.stateValue.equals(state))
                return current;
            for (MCTSNode child: current.children) {
                queue.add(child);
            }
        }
        return null;
    }

    public void merge(MCTSNode merge) {
        if (!stateValue.equals(merge.stateValue)) {
            logger.info("mismatched merge states at root");
            return;
        }

        this.visits += merge.visits;
        this.wins += merge.wins;
        int mismatchCount = 0;
        
        List<MCTSNode> mergeChildren = new ArrayList<>();
        for (MCTSNode child: merge.children) {
            mergeChildren.add(child);
        }

        for (MCTSNode child: children) {
            for (MCTSNode mergeChild: mergeChildren) {
                if (mergeChild.action != null && child.action != null) {
                    if (mergeChild.action.toString().equals(child.action.toString())) {
                        if (!mergeChild.stateValue.equals(child.stateValue)) {
                            mismatchCount++;
//                            logger.info("mismatched merge states");
//                            mergeChildren.remove(mergeChild);
                        }
                        else {
                            child.merge(mergeChild);
                            mergeChildren.remove(mergeChild);
                        }
                        break;
                    }
                }
                else {
                    if (mergeChild.combat.getValue().equals(child.combat.getValue())) {
                        if (!mergeChild.stateValue.equals(child.stateValue)) {
                            mismatchCount++;
//                            logger.info("mismatched merge states");
//                            mergeChildren.remove(mergeChild);
                        }
                        else {
                            child.merge(mergeChild);
                            mergeChildren.remove(mergeChild);
                        }
                        break;
                    }
                }
            }
        }
        if (!mergeChildren.isEmpty()) {
            for (MCTSNode child: mergeChildren) {
                child.parent = this;
                children.add(child);
            }
        }
//        if (mismatchCount > 0)
//            logger.info("mismatched merge states: " + mismatchCount);
    }

//    public void print(int depth) {
//        String indent = String.format("%1$-" + depth + "s", "");
//        StringBuilder sb = new StringBuilder();
//        MCTSPlayer player = (MCTSPlayer) game.getPlayer(playerId);
//        sb.append(indent).append(player.getName()).append(" ").append(visits).append(":").append(wins).append(" - ");
//        if (action != null)
//            sb.append(action.toString());
//        System.out.println(sb.toString());
//        for (MCTSNode child: children) {
//            child.print(depth + 1);
//        }
//    }

    public int size() {
        int num = 1;
        for (MCTSNode child: children) {
            num += child.size();
        }
        return num;
    }

    private static final ConcurrentHashMap<String, List<Ability>> playablesCache = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, List<List<UUID>>> attacksCache = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, List<List<List<UUID>>>> blocksCache = new ConcurrentHashMap<>();

    private static long playablesHit = 0;
    private static long playablesMiss = 0;
    private static long attacksHit = 0;
    private static long attacksMiss = 0;
    private static long blocksHit = 0;
    private static long blocksMiss = 0;
    
    private static List<Ability> getPlayables(MCTSPlayer player, String state, Game game) {
        if (playablesCache.containsKey(state)) {
            playablesHit++;
            return playablesCache.get(state);
        }
        else {
            playablesMiss++;
            List<Ability> abilities = player.getPlayableOptions(game);
            playablesCache.put(state, abilities);
            return abilities;
        }
    }
    
    private static List<List<UUID>> getAttacks(MCTSPlayer player, String state, Game game) {
        if (attacksCache.containsKey(state)) {
            attacksHit++;
            return attacksCache.get(state);
        }
        else {
            attacksMiss++;
            List<List<UUID>> attacks = player.getAttacks(game);
            attacksCache.put(state, attacks);
            return attacks;
        }
    }
    
    private static List<List<List<UUID>>> getBlocks(MCTSPlayer player, String state, Game game) {
        if (blocksCache.containsKey(state)) {
            blocksHit++;
            return blocksCache.get(state);
        }
        else {
            blocksMiss++;
            List<List<List<UUID>>> blocks = player.getBlocks(game);
            blocksCache.put(state, blocks);
            return blocks;
        }
    }
    
    public static int cleanupCache(int turnNum) {
        Set<String> playablesKeys = playablesCache.keySet();
        Iterator<String> playablesIterator = playablesKeys.iterator();
        int count = 0;
        while(playablesIterator.hasNext()) {
            String next = playablesIterator.next();
            int cacheTurn = Integer.parseInt(next.split(":", 2)[0].substring(1));
            if (cacheTurn < turnNum) {
                playablesIterator.remove();
                count++;
            }
        }

        Set<String> attacksKeys = attacksCache.keySet();
        Iterator<String> attacksIterator = attacksKeys.iterator();
        while(attacksIterator.hasNext()) {
            int cacheTurn = Integer.parseInt(attacksIterator.next().split(":", 2)[0].substring(1));
            if (cacheTurn < turnNum) {
                attacksIterator.remove();
                count++;
            }
        }
        
        Set<String> blocksKeys = blocksCache.keySet();
        Iterator<String> blocksIterator = blocksKeys.iterator();
        while(blocksIterator.hasNext()) {
            int cacheTurn = Integer.parseInt(blocksIterator.next().split(":", 2)[0].substring(1));
            if (cacheTurn < turnNum) {
                blocksIterator.remove();
                count++;
            }
        }

        return count;
    }
    
    public static void logHitMiss() {
        if (USE_ACTION_CACHE) {
            StringBuilder sb = new StringBuilder();
            sb.append("Playables Cache -- Hits: ").append(playablesHit).append(" Misses: ").append(playablesMiss).append('\n');
            sb.append("Attacks Cache -- Hits: ").append(attacksHit).append(" Misses: ").append(attacksMiss).append('\n');
            sb.append("Blocks Cache -- Hits: ").append(blocksHit).append(" Misses: ").append(blocksMiss).append('\n');
            logger.info(sb.toString());
        }
    }    
}
