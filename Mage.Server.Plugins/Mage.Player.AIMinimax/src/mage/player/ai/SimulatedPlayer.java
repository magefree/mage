

package mage.player.ai;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.PassAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.game.Game;
import mage.game.combat.Combat;
import mage.game.events.GameEvent;
import mage.game.match.MatchPlayer;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.players.Player;
import mage.target.Target;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SimulatedPlayer extends ComputerPlayer {

    private static final Logger logger = Logger.getLogger(SimulatedPlayer.class);
    private final boolean isSimulatedPlayer;
    private transient ConcurrentLinkedQueue<Ability> allActions;
    private static final PassAbility pass = new PassAbility();
    protected int maxDepth;

    public SimulatedPlayer(Player originalPlayer, boolean isSimulatedPlayer, int maxDepth) {
        super(originalPlayer.getId());
        this.maxDepth = maxDepth;
        pass.setControllerId(playerId);
        this.isSimulatedPlayer = isSimulatedPlayer;
        this.matchPlayer = new MatchPlayer(originalPlayer.getMatchPlayer(), this);
    }

    public SimulatedPlayer(final SimulatedPlayer player) {
        super(player);
        this.isSimulatedPlayer = player.isSimulatedPlayer;
        this.maxDepth = player.maxDepth;
    }

    @Override
    public SimulatedPlayer copy() {
        return new SimulatedPlayer(this);
    }

    public List<Ability> simulatePriority(Game game) {
        allActions = new ConcurrentLinkedQueue<>();
        Game sim = game.copy();

        simulateOptions(sim, pass);

        List<Ability> list = new ArrayList<>(allActions);
        //Collections.shuffle(list);
        Collections.reverse(list);
        return list;
    }

    protected void simulateOptions(Game game, ActivatedAbility previousActions) {
        allActions.add(previousActions);
        List<ActivatedAbility> playables = game.getPlayer(playerId).getPlayable(game, isSimulatedPlayer);
        for (ActivatedAbility ability: playables) {
            List<Ability> options = game.getPlayer(playerId).getPlayableOptions(ability, game);
            if (options.isEmpty()) {
                if (!ability.getManaCosts().getVariableCosts().isEmpty()) {
                    simulateVariableCosts(ability, game);
                }
                else {
                    allActions.add(ability);
                }
//                simulateAction(game, previousActions, ability);
            }
            else {
//                ExecutorService simulationExecutor = Executors.newFixedThreadPool(4);
                for (Ability option: options) {
                    if (!ability.getManaCosts().getVariableCosts().isEmpty()) {
                        simulateVariableCosts(option, game);
                    }
                    else {
                        allActions.add(option);
                    }
//                    SimulationWorker worker = new SimulationWorker(game, this, previousActions, option);
//                    simulationExecutor.submit(worker);
                }
//                simulationExecutor.shutdown();
//                while(!simulationExecutor.isTerminated()) {}
            }
        }
    }

//    protected void simulateAction(Game game, SimulatedAction previousActions, Ability action) {
//        List<Ability> actions = new ArrayList<Ability>(previousActions.getAbilities());
//        actions.add(action);
//        Game sim = game.copy();
//        if (sim.getPlayer(playerId).activateAbility((ActivatedAbility) action.copy(), sim)) {
//            sim.applyEffects();
//            sim.getPlayers().resetPassed();
//            allActions.add(new SimulatedAction(sim, actions));
//        }
//    }

    //add a generic mana cost for each amount possible
    protected void simulateVariableCosts(Ability ability, Game game) {
        int numAvailable = getAvailableManaProducers(game).size() - ability.getManaCosts().manaValue();
        int start = 0;
        if (!(ability instanceof SpellAbility)) {
            //only use x=0 on spell abilities
            if (numAvailable == 0)
                return;
            else
                start = 1;
        }
        for (int i = start; i < numAvailable; i++) {
            Ability newAbility = ability.copy();
            newAbility.getManaCostsToPay().add(new GenericManaCost(i));
            allActions.add(newAbility);
        }
    }

    /*@Override
    public boolean playXMana(VariableManaCost cost, ManaCosts<ManaCost> costs, Game game) {
        //simulateVariableCosts method adds a generic mana cost for each option
        for (ManaCost manaCost: costs) {
            if (manaCost instanceof GenericManaCost) {
                cost.setPayment(manaCost.getPayment());
                logger.debug("simulating -- X = " + cost.getPayment().count());
                break;
            }
        }
        cost.setPaid();
        return true;
    }*/

    public List<Combat> addAttackers(Game game) {
        Map<Integer, Combat> engagements = new HashMap<>();
        //useful only for two player games - will only attack first opponent
        UUID defenderId = game.getOpponents(playerId).iterator().next();
        List<Permanent> attackersList = super.getAvailableAttackers(defenderId, game);
        //use binary digits to calculate powerset of attackers
        int powerElements = (int) Math.pow(2, attackersList.size());
        StringBuilder binary = new StringBuilder();
        for (int i = powerElements - 1; i >= 0; i--) {
            Game sim = game.copy();
            binary.setLength(0);
            binary.append(Integer.toBinaryString(i));
            while (binary.length() < attackersList.size()) {
                binary.insert(0, '0');
            }
            for (int j = 0; j < attackersList.size(); j++) {
                if (binary.charAt(j) == '1') {
                    setStoredBookmark(sim.bookmarkState()); // makes it possible to UNDO a declared attacker with costs from e.g. Propaganda
                    if (!sim.getCombat().declareAttacker(attackersList.get(j).getId(), defenderId, playerId, sim)) {
                        sim.undo(playerId);
                    }                    
                }
            }
            if (engagements.put(sim.getCombat().getValue().hashCode(), sim.getCombat()) != null) {
                logger.debug("simulating -- found redundant attack combination");
            }
            else if (logger.isDebugEnabled()) {
                logger.debug("simulating -- attack:" + sim.getCombat().getGroups().size());
            }
        }
        return new ArrayList<>(engagements.values());
    }

    public List<Combat> addBlockers(Game game) {
        Map<Integer, Combat> engagements = new HashMap<>();
        int numGroups = game.getCombat().getGroups().size();
        if (numGroups == 0) return new ArrayList<>();

        //add a node with no blockers
        Game sim = game.copy();
        engagements.put(sim.getCombat().getValue().hashCode(), sim.getCombat());
        sim.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARED_BLOCKERS, playerId, playerId));

        List<Permanent> blockers = getAvailableBlockers(game);
        addBlocker(game, blockers, engagements);

        return new ArrayList<>(engagements.values());
    }

    protected void addBlocker(Game game, List<Permanent> blockers, Map<Integer, Combat> engagements) {
        if (blockers.isEmpty())
            return;
        int numGroups = game.getCombat().getGroups().size();
        //try to block each attacker with each potential blocker
        Permanent blocker = blockers.get(0);
        if (logger.isDebugEnabled())
            logger.debug("simulating -- block:" + blocker);
        List<Permanent> remaining = remove(blockers, blocker);
        for (int i = 0; i < numGroups; i++) {
            if (game.getCombat().getGroups().get(i).canBlock(blocker, game)) {
                Game sim = game.copy();
                sim.getCombat().getGroups().get(i).addBlocker(blocker.getId(), playerId, sim);
                if (engagements.put(sim.getCombat().getValue().hashCode(), sim.getCombat()) != null)
                    logger.debug("simulating -- found redundant block combination");
                addBlocker(sim, remaining, engagements);  // and recurse minus the used blocker
            }
        }
        addBlocker(game, remaining, engagements);
    }

    @Override
    public boolean triggerAbility(TriggeredAbility source, Game game) {
        Ability ability = source.copy();
        List<Ability> options = getPlayableOptions(ability, game);
        if (options.isEmpty()) {
            if (logger.isDebugEnabled())
                logger.debug("simulating -- triggered ability:" + ability);
            game.getStack().push(new StackAbility(ability, playerId));
            if (ability.activate(game, false) && ability.isUsesStack()) {
                game.fireEvent(new GameEvent(GameEvent.EventType.TRIGGERED_ABILITY, ability.getId(), ability, ability.getControllerId()));
            }
            game.applyEffects();
            game.getPlayers().resetPassed();
        }
        else {
            SimulationNode parent = (SimulationNode) game.getCustomData();
            if (parent.getDepth() == maxDepth) return true;
            logger.debug(indent(parent.getDepth()) + "simulating -- triggered ability - adding children:" + options.size());
            for (Ability option: options) {
                addAbilityNode(parent, option, game);
            }
        }
        return true;
    }

    protected void addAbilityNode(SimulationNode parent, Ability ability, Game game) {
        Game sim = game.copy();
        sim.getStack().push(new StackAbility(ability, playerId));
        ability.activate(sim, false);
        if (ability.activate(sim, false) && ability.isUsesStack()) {
            game.fireEvent(new GameEvent(GameEvent.EventType.TRIGGERED_ABILITY, ability.getId(), ability, ability.getControllerId()));
        }
        sim.applyEffects();
        SimulationNode newNode = new SimulationNode(parent, sim, playerId);
        logger.debug(indent(newNode.getDepth()) + "simulating -- node #:" + SimulationNode.getCount() + " triggered ability option");
        for (Target target: ability.getTargets()) {
            for (UUID targetId: target.getTargets()) {
                newNode.getTargets().add(targetId);
            }
        }
        parent.children.add(newNode);
    }

    @Override
    public boolean priority(Game game) {
        //should never get here
        return false;
    }

    protected String indent(int num) {
        char[] fill = new char[num];
        Arrays.fill(fill, ' ');
        return new String(fill);
    }

}
