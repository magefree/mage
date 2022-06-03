package mage.player.ai;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.AbilityImpl;
import mage.abilities.ActivatedAbility;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.PassAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.AbilityType;
import mage.game.Game;
import mage.game.combat.Combat;
import mage.game.events.GameEvent;
import mage.game.match.MatchPlayer;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.players.Player;
import mage.players.net.UserData;
import mage.target.Target;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * AI: mock player in simulated games (each player replaced by simulated)
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SimulatedPlayer2 extends ComputerPlayer {

    private static final Logger logger = Logger.getLogger(SimulatedPlayer2.class);
    private static final PassAbility pass = new PassAbility();

    private final boolean isSimulatedPlayer;
    private final List<String> suggested;
    private transient ConcurrentLinkedQueue<Ability> allActions;
    private boolean forced;
    private final Player originalPlayer; // copy of the original player, source of choices/results in tests

    public SimulatedPlayer2(Player originalPlayer, boolean isSimulatedPlayer, List<String> suggested) {
        super(originalPlayer.getId());
        this.originalPlayer = originalPlayer.copy();
        pass.setControllerId(playerId);
        this.isSimulatedPlayer = isSimulatedPlayer;
        this.suggested = suggested;
        this.userData = UserData.getDefaultUserDataView();
        this.matchPlayer = new MatchPlayer(originalPlayer.getMatchPlayer(), this);
    }

    public SimulatedPlayer2(final SimulatedPlayer2 player) {
        super(player);
        this.isSimulatedPlayer = player.isSimulatedPlayer;
        this.suggested = new ArrayList<>(player.suggested);
        // this.allActions = player.allActions; // dynamic, no need to copy
        this.originalPlayer = player.originalPlayer.copy();
    }

    @Override
    public SimulatedPlayer2 copy() {
        return new SimulatedPlayer2(this);
    }

    public List<Ability> simulatePriority(Game game) {
        allActions = new ConcurrentLinkedQueue<>();
        Game sim = game.copy();
        sim.setSimulation(true);
        forced = false;
        simulateOptions(sim);

        List<Ability> list = new ArrayList<>(allActions);
        Collections.reverse(list);

        if (!forced) {
            list.add(pass);
        }

        if (logger.isTraceEnabled()) {
            for (Ability a : allActions) {
                logger.info("ability==" + a);
                if (!a.getTargets().isEmpty()) {
                    MageObject mageObject = game.getObject(a.getFirstTarget());
                    if (mageObject != null) {
                        logger.info("   target=" + mageObject.getName());
                    } else {
                        Player player = game.getPlayer(a.getFirstTarget());
                        if (player != null) {
                            logger.info("   target=" + player.getName());
                        }
                    }
                }
            }
        }

        return list;
    }

    protected void simulateOptions(Game game) {
        List<ActivatedAbility> playables = game.getPlayer(playerId).getPlayable(game, isSimulatedPlayer);
        playables = filterAbilities(game, playables, suggested);
        for (ActivatedAbility ability : playables) {
            if (ability.getAbilityType() == AbilityType.MANA) {
                continue;
            }
            List<Ability> options = game.getPlayer(playerId).getPlayableOptions(ability, game);
            options = filterOptions(game, options, ability, suggested);
            options = optimizeOptions(game, options, ability);
            if (options.isEmpty()) {
                allActions.add(ability);
            } else {
                for (Ability option : options) {
                    allActions.add(option);
                }
            }
        }
    }

    @Override
    protected void addVariableXOptions(List<Ability> options, Ability ability, int targetNum, Game game) {
        // calculate the mana that can be used for the x part
        int numAvailable = getAvailableManaProducers(game).size() - ability.getManaCosts().manaValue();

        if (numAvailable > 0) {
            // check if variable mana costs is included and get the multiplier
            VariableManaCost variableManaCost = null;
            for (ManaCost cost : ability.getManaCostsToPay()) {
                if (cost instanceof VariableManaCost && !cost.isPaid()) {
                    variableManaCost = (VariableManaCost) cost;
                    break; // only one VariableManCost per spell (or is it possible to have more?)
                }
            }
            if (variableManaCost != null) {
                int xInstancesCount = variableManaCost.getXInstancesCount();

                for (int mana = variableManaCost.getMinX(); mana <= numAvailable; mana++) {
                    if (mana % xInstancesCount == 0) { // use only values dependant from multiplier
                        // find possible X value to pay
                        int xAnnounceValue = mana / xInstancesCount;
                        Ability newAbility = ability.copy();
                        VariableManaCost varCost = null;
                        for (ManaCost cost : newAbility.getManaCostsToPay()) {
                            if (cost instanceof VariableManaCost && !cost.isPaid()) {
                                varCost = (VariableManaCost) cost;
                                break; // only one VariableManCost per spell (or is it possible to have more?)
                            }
                        }
                        // find real X value after replace events
                        int xMultiplier = 1;
                        if (newAbility instanceof AbilityImpl) {
                            xMultiplier = ((AbilityImpl) newAbility).handleManaXMultiplier(game, xMultiplier);
                        }
                        newAbility.getManaCostsToPay().add(new ManaCostsImpl(new StringBuilder("{").append(xAnnounceValue).append('}').toString()));
                        newAbility.getManaCostsToPay().setX(xAnnounceValue * xMultiplier, xAnnounceValue * xInstancesCount);
                        if (varCost != null) {
                            varCost.setPaid();
                        }
                        newAbility.adjustTargets(game);
                        // add the different possible target option for the specific X value
                        if (!newAbility.getTargets().getUnchosen().isEmpty()) {
                            addTargetOptions(options, newAbility, targetNum, game);
                        }
                    }

                }
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

    /**
     * if suggested abilities exist, return only those from playables
     *
     * @param game
     * @param playables
     * @param suggested
     * @return
     */
    protected List<ActivatedAbility> filterAbilities(Game game, List<ActivatedAbility> playables, List<String> suggested) {
        if (playables.isEmpty()) {
            return playables;
        }
        if (suggested == null || suggested.isEmpty()) {
            return playables;
        }
        List<ActivatedAbility> filtered = new ArrayList<>();
        for (ActivatedAbility ability : playables) {
            Card card = game.getCard(ability.getSourceId());
            if (card != null) {
                for (String s : suggested) {
                    if (s.equals(card.getName())) {
                        logger.debug("matched: " + s);
                        forced = true;
                        filtered.add(ability);
                    }
                }
            }
        }
        if (!filtered.isEmpty()) {
            return filtered;
        }
        return playables;
    }

    protected List<Ability> filterOptions(Game game, List<Ability> options, ActivatedAbility ability, List<String> suggested) {
        if (options.isEmpty()) {
            return options;
        }
        if (suggested == null || suggested.isEmpty()) {
            return options;
        }
        List<Ability> filtered = new ArrayList<>();
        for (Ability option : options) {
            if (!option.getTargets().isEmpty() && option.getTargets().get(0).getMaxNumberOfTargets() == 1) {
                Card card = game.getCard(ability.getSourceId());
                if (card != null) {
                    for (String s : suggested) {
                        String[] groups = s.split(";");
                        logger.trace("s=" + s + ";groups=" + groups.length);
                        if (groups.length == 2) {
                            if (groups[0].equals(card.getName()) && groups[1].startsWith("name=")) {
                                // extract target and compare to suggested
                                String targetName = groups[1].split("=")[1];
                                Player player = game.getPlayer(option.getFirstTarget());
                                if (player != null && targetName.equals(player.getName())) {
                                    System.out.println("matched(option): " + s);
                                    filtered.add(option);
                                    return filtered;
                                } else {
                                    Card target = game.getCard(option.getFirstTarget());
                                    if (target != null && target.getName().equals(targetName)) {
                                        System.out.println("matched(option): " + s);
                                        filtered.add(option);
                                        return filtered;
                                    }
                                    System.out.println("not equal UUID for target, player=" + player);
                                }
                            }
                        }
                    }
                }
            }
        }
        // no option was found
        return options;
    }

    protected List<Ability> optimizeOptions(Game game, List<Ability> options, Ability ability) {
        if (options.isEmpty()) {
            return options;
        }

        // determine if all effects are bad or good
        Iterator<Ability> iterator = options.iterator();
        boolean bad = true;
        boolean good = true;

        // TODO: add custom outcome from ability?
        for (Effect effect : ability.getEffects()) {
            if (effect.getOutcome().isGood()) {
                bad = false;
            } else {
                good = false;
            }
        }

        if (bad) {
            // remove its own creatures, player itself for bad effects with one target
            while (iterator.hasNext()) {
                Ability ability1 = iterator.next();
                if (ability1.getTargets().size() == 1 && ability1.getTargets().get(0).getTargets().size() == 1) {
                    Permanent permanent = game.getPermanent(ability1.getFirstTarget());
                    if (permanent != null && !game.getOpponents(playerId).contains(permanent.getControllerId())) {
                        iterator.remove();
                        continue;
                    }
                    if (ability1.getFirstTarget().equals(playerId)) {
                        iterator.remove();
                    }
                }
            }
        }
        if (good) {
            // remove opponent creatures and opponent for only good effects with one target
            while (iterator.hasNext()) {
                Ability ability1 = iterator.next();
                if (ability1.getTargets().size() == 1 && ability1.getTargets().get(0).getTargets().size() == 1) {
                    Permanent permanent = game.getPermanent(ability1.getFirstTarget());
                    if (permanent != null && game.getOpponents(playerId).contains(permanent.getControllerId())) {
                        iterator.remove();
                        continue;
                    }
                    if (game.getOpponents(playerId).contains(ability1.getFirstTarget())) {
                        iterator.remove();
                    }
                }
            }
        }

        return options;
    }

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
            } else {
                logger.debug("simulating -- attack:" + sim.getCombat().getGroups().size());
            }
        }
        List list = new ArrayList<>(engagements.values());
        Collections.sort(list, new Comparator<Combat>() {
            @Override
            public int compare(Combat o1, Combat o2) {
                return Integer.valueOf(o2.getGroups().size()).compareTo(o1.getGroups().size());
            }
        });
        return list;
    }

    public List<Combat> addBlockers(Game game) {
        Map<Integer, Combat> engagements = new HashMap<>();
        int numGroups = game.getCombat().getGroups().size();
        if (numGroups == 0) {
            return Collections.emptyList();
        }

        //add a node with no blockers
        Game sim = game.copy();
        engagements.put(sim.getCombat().getValue().hashCode(), sim.getCombat());
        sim.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARED_BLOCKERS, playerId, playerId));

        List<Permanent> blockers = getAvailableBlockers(game);
        addBlocker(game, blockers, engagements);

        return new ArrayList<>(engagements.values());
    }

    protected void addBlocker(Game game, List<Permanent> blockers, Map<Integer, Combat> engagements) {
        if (blockers.isEmpty()) {
            return;
        }
        int numGroups = game.getCombat().getGroups().size();
        //try to block each attacker with each potential blocker
        Permanent blocker = blockers.get(0);
        logger.debug("simulating -- block:" + blocker);
        List<Permanent> remaining = remove(blockers, blocker);
        for (int i = 0; i < numGroups; i++) {
            if (game.getCombat().getGroups().get(i).canBlock(blocker, game)) {
                Game sim = game.copy();
                sim.getCombat().getGroups().get(i).addBlocker(blocker.getId(), playerId, sim);
                if (engagements.put(sim.getCombat().getValue().hashCode(), sim.getCombat()) != null) {
                    logger.debug("simulating -- found redundant block combination");
                }
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
            logger.debug("simulating -- triggered ability:" + ability);
            game.getStack().push(new StackAbility(ability, playerId));
            if (ability.activate(game, false) && ability.isUsesStack()) {
                game.fireEvent(new GameEvent(GameEvent.EventType.TRIGGERED_ABILITY, ability.getId(), ability, ability.getControllerId()));
            }
            game.applyEffects();
            game.getPlayers().resetPassed();
        } else {
            SimulationNode2 parent = (SimulationNode2) game.getCustomData();
            int depth = parent.getDepth() - 1;
            if (depth == 0) {
                return true;
            }
            logger.debug("simulating -- triggered ability - adding children:" + options.size());
            for (Ability option : options) {
                addAbilityNode(parent, option, depth, game);
            }
        }
        return true;
    }

    protected void addAbilityNode(SimulationNode2 parent, Ability ability, int depth, Game game) {
        Game sim = game.copy();
        sim.getStack().push(new StackAbility(ability, playerId));
        if (ability.activate(sim, false) && ability.isUsesStack()) {
            game.fireEvent(new GameEvent(GameEvent.EventType.TRIGGERED_ABILITY, ability.getId(), ability, ability.getControllerId()));
        }
        sim.applyEffects();
        SimulationNode2 newNode = new SimulationNode2(parent, sim, depth, playerId);
        logger.debug("simulating -- node #:" + SimulationNode2.getCount() + " triggered ability option");
        for (Target target : ability.getTargets()) {
            for (UUID targetId : target.getTargets()) {
                newNode.getTargets().add(targetId);
            }
        }
        parent.children.add(newNode);
    }

    @Override
    public boolean priority(Game game) {
        // simulated player do nothing - it must pass until stack resolve to see final game score after action apply

        // it's a workaround for Karn Liberated restart ability (see CommandersGameRestartTest)
        // reason: restarted game is broken (miss clear code of some game/player data?) and ai can't simulate it
        // so game is freezes on non empty stack (last part of karn's restart ability)
        if (game.getStack().isEmpty()) {
            game.pause();
        }
        pass(game);
        return false;
    }

    @Override
    public boolean flipCoinResult(Game game) {
        // same random results set up support in AI tests, see TestComputerPlayer for docs
        return originalPlayer.flipCoinResult(game);
    }

    @Override
    public int rollDieResult(int sides, Game game) {
        // same random results set up support in AI tests, see TestComputerPlayer for docs
        return originalPlayer.rollDieResult(sides, game);
    }
}
