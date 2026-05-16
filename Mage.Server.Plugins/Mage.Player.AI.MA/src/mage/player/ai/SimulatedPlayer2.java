package mage.player.ai;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.PassAbility;
import mage.abilities.costs.OptionalAdditionalSourceCosts;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.Effect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.combat.Combat;
import mage.game.events.GameEvent;
import mage.game.match.MatchPlayer;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.players.net.UserData;
import mage.target.Target;
import mage.target.TargetAmount;
import mage.util.CardUtil;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * AI: helper class to simulate games with computer bot (each player replaced by simulated)
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class SimulatedPlayer2 extends ComputerPlayer {

    private static final Logger logger = Logger.getLogger(SimulatedPlayer2.class);

    private static final boolean AI_SIMULATE_ALL_BAD_AND_GOOD_TARGETS = false; // TODO: enable and do performance test (it's increase calculations by x2, but is it useful?)

    // warning, simulated player do not restore own data by game rollback
    private final boolean isSimulatedPlayer;
    private final int maxAttackCombinations;
    private final int maxTargetOptionsPerAbility;
    private final boolean useHandQualityDiscardTargeting;
    private final boolean useStrategicCastVariants;
    private transient ConcurrentLinkedQueue<Ability> allActions; // all possible abilities to play (copies with already selected targets)
    private final Player originalPlayer; // copy of the original player, source of choices/results in tests

    public SimulatedPlayer2(Player originalPlayer, boolean isSimulatedPlayer) {
        this(originalPlayer, isSimulatedPlayer, 0, 0);
    }

    public SimulatedPlayer2(Player originalPlayer, boolean isSimulatedPlayer, int maxAttackCombinations) {
        this(originalPlayer, isSimulatedPlayer, maxAttackCombinations, 0);
    }

    public SimulatedPlayer2(Player originalPlayer, boolean isSimulatedPlayer, int maxAttackCombinations, int maxTargetOptionsPerAbility) {
        this(originalPlayer, isSimulatedPlayer, maxAttackCombinations, maxTargetOptionsPerAbility, false);
    }

    public SimulatedPlayer2(Player originalPlayer, boolean isSimulatedPlayer, int maxAttackCombinations,
                            int maxTargetOptionsPerAbility, boolean useHandQualityDiscardTargeting) {
        this(originalPlayer, isSimulatedPlayer, maxAttackCombinations, maxTargetOptionsPerAbility, useHandQualityDiscardTargeting, false);
    }

    public SimulatedPlayer2(Player originalPlayer, boolean isSimulatedPlayer, int maxAttackCombinations,
                            int maxTargetOptionsPerAbility, boolean useHandQualityDiscardTargeting,
                            boolean useStrategicCastVariants) {
        super(originalPlayer.getId());
        this.originalPlayer = originalPlayer.copy();
        this.isSimulatedPlayer = isSimulatedPlayer;
        this.maxAttackCombinations = maxAttackCombinations;
        this.maxTargetOptionsPerAbility = maxTargetOptionsPerAbility;
        this.useHandQualityDiscardTargeting = useHandQualityDiscardTargeting;
        this.useStrategicCastVariants = useStrategicCastVariants;
        this.userData = UserData.getDefaultUserDataView();
        this.matchPlayer = new MatchPlayer(originalPlayer.getMatchPlayer(), this);
    }

    public SimulatedPlayer2(final SimulatedPlayer2 player) {
        super(player);
        this.isSimulatedPlayer = player.isSimulatedPlayer;
        this.maxAttackCombinations = player.maxAttackCombinations;
        this.maxTargetOptionsPerAbility = player.maxTargetOptionsPerAbility;
        this.useHandQualityDiscardTargeting = player.useHandQualityDiscardTargeting;
        this.useStrategicCastVariants = player.useStrategicCastVariants;
        // this.allActions = player.allActions; // dynamic, no need to copy
        this.originalPlayer = player.originalPlayer.copy();
    }

    @Override
    public void restore(Player player) {
        // simulated player can be created from any player type
        if (!originalPlayer.getClass().equals(player.getClass())) {
            throw new IllegalArgumentException("Wrong code usage: simulated player must use same player class all the time. Need "
                    + originalPlayer.getClass().getSimpleName() + ", but try to restore " + player.getClass().getSimpleName());
        }

        super.restore(player.getRealPlayer());
    }

    @Override
    public SimulatedPlayer2 copy() {
        return new SimulatedPlayer2(this);
    }

    @Override
    protected PossibleTargetsSelector createPossibleTargetsSelector(Outcome outcome, Target target,
                                                                    UUID abilityControllerId, Ability source, Game game) {
        return new PossibleTargetsSelector(
                outcome,
                target,
                abilityControllerId,
                source,
                game,
                useHandQualityDiscardTargeting
        );
    }

    /**
     * Find all playable abilities with all possible targets (targets already selected in ability)
     */
    public List<Ability> simulatePriority(Game game) {
        allActions = new ConcurrentLinkedQueue<>();
        if (isSearchInterrupted()) {
            return Collections.singletonList(new PassAbility());
        }
        Game sim = game.createSimulationForAI();
        if (isSearchInterrupted()) {
            return Collections.singletonList(new PassAbility());
        }
        simulateOptions(sim);

        // possible actions
        List<Ability> list = new ArrayList<>(allActions);
        Collections.reverse(list);

        // pass action
        list.add(new PassAbility());

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

    private void simulateOptions(Game game) {
        List<ActivatedAbility> playables = game.getPlayer(playerId).getPlayable(game, isSimulatedPlayer);
        for (ActivatedAbility ability : playables) {
            if (isSearchInterrupted()) {
                break;
            }
            if (ability.isManaAbility()) {
                continue;
            }
            for (Ability castVariant : getStrategicCastVariants(game, ability)) {
                List<Ability> options = game.getPlayer(playerId).getPlayableOptions(castVariant, game);
                options = optimizeOptions(game, options, castVariant);
                if (options.isEmpty()) {
                    allActions.add(castVariant);
                } else {
                    for (Ability option : options) {
                        allActions.add(option);
                    }
                }
            }
        }
    }

    private List<Ability> getStrategicCastVariants(Game game, ActivatedAbility ability) {
        List<Ability> variants = new ArrayList<>();
        variants.add(ability);
        if (!useStrategicCastVariants || !(ability instanceof SpellAbility)) {
            return variants;
        }
        MageObject sourceObject = ability.getSourceObject(game);
        if (sourceObject == null) {
            return variants;
        }
        for (Ability sourceAbility : CardUtil.getAbilities(sourceObject, game)) {
            if (!(sourceAbility instanceof OptionalAdditionalSourceCosts)) {
                continue;
            }
            List<Ability> optionalVariants = ((OptionalAdditionalSourceCosts) sourceAbility)
                    .getOptionalAdditionalCostVariants(ability, game);
            for (Ability optionalVariant : optionalVariants) {
                if (optionalVariant != null) {
                    variants.add(optionalVariant);
                }
            }
        }
        return variants;
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

                for (int mana = variableManaCost.getMinX(); mana <= numAvailable && !isSearchInterrupted(); mana++) {
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
                        newAbility.addManaCostsToPay(new ManaCostsImpl<>(new StringBuilder("{").append(xAnnounceValue).append('}').toString()));
                        newAbility.getManaCostsToPay().setX(xAnnounceValue, xAnnounceValue * xInstancesCount);
                        if (varCost != null) {
                            varCost.setPaid();
                        }
                        newAbility.adjustTargets(game);
                        // add the different possible target option for the specific X value
                        if (newAbility.getTargets().getNextUnchosen(game) != null) {
                            addTargetOptions(options, newAbility, targetNum, game);
                        }
                    }

                }
            }

        }
    }

    protected List<Ability> optimizeOptions(Game game, List<Ability> options, Ability ability) {
        if (options.isEmpty()) {
            return options;
        }

        // remove invalid targets
        // TODO: is it useless cause it already filtered before?
        options.removeIf(option -> !option.getTargets().isChosen(game));

        if (AI_SIMULATE_ALL_BAD_AND_GOOD_TARGETS) {
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
                    if (permanent != null && !game.getOpponents(playerId, true).contains(permanent.getControllerId())) {
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
                    if (permanent != null && game.getOpponents(playerId, true).contains(permanent.getControllerId())) {
                        iterator.remove();
                        continue;
                    }
                    if (game.getOpponents(playerId, true).contains(ability1.getFirstTarget())) {
                        iterator.remove();
                    }
                }
            }
        }

        return limitEquivalentTargetOptions(game, options);
    }

    private List<Ability> limitEquivalentTargetOptions(Game game, List<Ability> options) {
        if (maxTargetOptionsPerAbility <= 0 || options.size() <= 1) {
            return options;
        }
        Map<String, Ability> uniqueOptions = new LinkedHashMap<>();
        for (Ability option : options) {
            uniqueOptions.putIfAbsent(getAbilityOptionSignature(game, option), option);
        }

        List<Ability> limitedOptions = new ArrayList<>(uniqueOptions.values());
        if (maxTargetOptionsPerAbility > 0 && limitedOptions.size() > maxTargetOptionsPerAbility) {
            return new ArrayList<>(limitedOptions.subList(0, maxTargetOptionsPerAbility));
        }
        return limitedOptions;
    }

    private String getAbilityOptionSignature(Game game, Ability ability) {
        StringBuilder signature = new StringBuilder(String.valueOf(ability.getSourceId()))
                .append('|')
                .append(ability.getRule());
        int targetIndex = 0;
        for (Target target : ability.getAllSelectedTargets()) {
            signature.append("|target").append(targetIndex++)
                    .append(':')
                    .append(target.isSkipChoice());
            for (UUID targetId : target.getTargets()) {
                signature.append(':');
                if (target instanceof TargetAmount) {
                    signature.append(((TargetAmount) target).getTargetAmount(targetId)).append('@');
                }
                signature.append(getTargetSignature(game, targetId));
            }
        }
        return signature.toString();
    }

    private String getTargetSignature(Game game, UUID targetId) {
        Player player = game.getPlayer(targetId);
        if (player != null) {
            return "player:" + player.getId();
        }

        Permanent permanent = game.getPermanent(targetId);
        if (permanent != null) {
            return new StringBuilder("permanent:")
                    .append(permanent.getControllerId()).append(':')
                    .append(permanent.getName()).append(':')
                    .append(permanent.getCardType(game)).append(':')
                    .append(permanent.getSubtype(game)).append(':')
                    .append(permanent.getPower().getValue()).append('/')
                    .append(permanent.getToughness().getValue()).append(':')
                    .append(permanent.getDamage()).append(':')
                    .append(permanent.isTapped()).append(':')
                    .append(permanent.getCounters(game)).append(':')
                    .append(permanent.getAttachments().size()).append(':')
                    .append(permanent.getAttachedTo())
                    .toString();
        }

        StackObject stackObject = game.getStack().getStackObject(targetId);
        if (stackObject != null) {
            return "stack:" + stackObject.getControllerId() + ':' + stackObject.getName() + ':' + stackObject.toString();
        }

        MageObject object = game.getObject(targetId);
        if (object != null) {
            return "object:" + object.getName() + ':' + object.getCardType(game) + ':' + object.getSubtype(game) + ':' + object.getManaValue();
        }

        return "unknown:" + targetId;
    }

    public List<Combat> addAttackers(Game game) {
        Map<Integer, Combat> engagements = new HashMap<>();
        if (isSearchInterrupted()) {
            return Collections.emptyList();
        }
        //useful only for two player games - will only attack first opponent
        UUID defenderId = game.getOpponents(playerId, true).iterator().next();
        List<Permanent> attackersList = super.getAvailableAttackers(defenderId, game);
        if (maxAttackCombinations > 0
                && attackersList.size() > 1
                && wouldExceedAttackCombinationLimit(attackersList.size(), maxAttackCombinations)) {
            for (boolean[] attackPlan : buildCappedAttackPlans(attackersList.size(), maxAttackCombinations)) {
                if (isSearchInterrupted()) {
                    break;
                }
                addAttackEngagement(game, defenderId, attackersList, attackPlan, engagements);
            }
        } else {
            //use binary digits to calculate powerset of attackers
            int powerElements = (int) Math.pow(2, attackersList.size());
            StringBuilder binary = new StringBuilder();
            for (int i = powerElements - 1; i >= 0 && !isSearchInterrupted(); i--) {
                binary.setLength(0);
                binary.append(Integer.toBinaryString(i));
                while (binary.length() < attackersList.size()) {
                    binary.insert(0, '0');
                }
                boolean[] attackPlan = new boolean[attackersList.size()];
                for (int j = 0; j < attackersList.size(); j++) {
                    attackPlan[j] = binary.charAt(j) == '1';
                }
                addAttackEngagement(game, defenderId, attackersList, attackPlan, engagements);
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

    private boolean wouldExceedAttackCombinationLimit(int attackerCount, int limit) {
        if (attackerCount >= Integer.SIZE - 1) {
            return true;
        }
        return (1 << attackerCount) > limit;
    }

    private List<boolean[]> buildCappedAttackPlans(int attackerCount, int limit) {
        List<boolean[]> plans = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        addAttackPlan(plans, seen, filledAttackPlan(attackerCount, true), limit);
        addAttackPlan(plans, seen, filledAttackPlan(attackerCount, false), limit);

        for (int i = 0; i < attackerCount && plans.size() < limit; i++) {
            boolean[] plan = new boolean[attackerCount];
            plan[i] = true;
            addAttackPlan(plans, seen, plan, limit);
        }

        for (int i = 0; i < attackerCount && plans.size() < limit; i++) {
            boolean[] plan = filledAttackPlan(attackerCount, true);
            plan[i] = false;
            addAttackPlan(plans, seen, plan, limit);
        }

        for (int i = 0; i < attackerCount && plans.size() < limit; i++) {
            for (int j = i + 1; j < attackerCount && plans.size() < limit; j++) {
                boolean[] plan = new boolean[attackerCount];
                plan[i] = true;
                plan[j] = true;
                addAttackPlan(plans, seen, plan, limit);
            }
        }

        return plans;
    }

    private boolean[] filledAttackPlan(int attackerCount, boolean value) {
        boolean[] plan = new boolean[attackerCount];
        Arrays.fill(plan, value);
        return plan;
    }

    private void addAttackPlan(List<boolean[]> plans, Set<String> seen, boolean[] attackPlan, int limit) {
        if (plans.size() >= limit) {
            return;
        }
        String key = Arrays.toString(attackPlan);
        if (seen.add(key)) {
            plans.add(attackPlan);
        }
    }

    private void addAttackEngagement(Game game, UUID defenderId, List<Permanent> attackersList, boolean[] attackPlan, Map<Integer, Combat> engagements) {
        if (isSearchInterrupted()) {
            return;
        }
        Game sim = game.createSimulationForAI();
        if (isSearchInterrupted()) {
            return;
        }
        for (int j = 0; j < attackersList.size(); j++) {
            if (attackPlan[j]) {
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

    public List<Combat> addBlockers(Game game) {
        Map<Integer, Combat> engagements = new HashMap<>();
        if (isSearchInterrupted()) {
            return Collections.emptyList();
        }
        int numGroups = game.getCombat().getGroups().size();
        if (numGroups == 0) {
            return Collections.emptyList();
        }

        //add a node with no blockers
        Game sim = game.createSimulationForAI();
        engagements.put(sim.getCombat().getValue().hashCode(), sim.getCombat());
        sim.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARED_BLOCKERS, playerId, playerId));

        List<Permanent> blockers = getAvailableBlockers(game);
        addBlocker(game, blockers, engagements);

        return new ArrayList<>(engagements.values());
    }

    protected void addBlocker(Game game, List<Permanent> blockers, Map<Integer, Combat> engagements) {
        if (blockers.isEmpty() || isSearchInterrupted()) {
            return;
        }
        int numGroups = game.getCombat().getGroups().size();
        //try to block each attacker with each potential blocker
        Permanent blocker = blockers.get(0);
        logger.debug("simulating -- block:" + blocker);
        List<Permanent> remaining = remove(blockers, blocker);
        for (int i = 0; i < numGroups && !isSearchInterrupted(); i++) {
            if (game.getCombat().getGroups().get(i).canBlock(blocker, game)) {
                Game sim = game.createSimulationForAI();
                if (isSearchInterrupted()) {
                    return;
                }
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
        if (isSearchInterrupted()) {
            return true;
        }
        Ability ability = source.copy();
        List<Ability> options = new ArrayList<>(getPlayableOptions(ability, game));
        if (maxTargetOptionsPerAbility > 0) {
            options.removeIf(option -> !option.getTargets().isChosen(game));
            options = limitEquivalentTargetOptions(game, options);
        }
        if (options.isEmpty()) {
            // no options - activate as is
            logger.debug("simulating -- triggered ability:" + ability);
            game.getStack().push(game, new StackAbility(ability, playerId));
            if (ability.activate(game, false) && ability.isUsesStack()) {
                game.fireEvent(new GameEvent(GameEvent.EventType.TRIGGERED_ABILITY, ability.getId(), ability, ability.getControllerId()));
            }
            game.applyEffects();
            game.getPlayers().resetPassed();
        } else {
            // many options - activate and add to sims tree
            // TODO: AI run all sims, but do not use best option for triggers yet
            SimulationNode2 parent = (SimulationNode2) game.getCustomData();
            int depth = parent.getDepth() - 1;
            if (depth == 0) {
                return true;
            }
            logger.debug("simulating -- triggered ability - adding children:" + options.size());
            for (Ability option : options) {
                if (isSearchInterrupted()) {
                    break;
                }
                addAbilityNode(parent, option, depth, game);
            }
        }
        return true;
    }

    protected void addAbilityNode(SimulationNode2 parent, Ability ability, int depth, Game game) {
        if (isSearchInterrupted()) {
            return;
        }
        Game sim = game.createSimulationForAI();
        if (isSearchInterrupted()) {
            return;
        }
        sim.getStack().push(sim, new StackAbility(ability, playerId));
        if (ability.activate(sim, false) && ability.isUsesStack()) {
            sim.fireEvent(new GameEvent(GameEvent.EventType.TRIGGERED_ABILITY, ability.getId(), ability, ability.getControllerId()));
        }
        sim.applyEffects();
        SimulationNode2 newNode = new SimulationNode2(parent, sim, depth, playerId);
        logger.debug("simulating -- node #:" + SimulationNode2.getCount() + " triggered ability option");
        for (Target target : ability.getTargets()) {
            for (UUID targetId : target.getTargets()) {
                newNode.getTargets().add(targetId); // save for info only (real targets in newNode.game.stack already)
            }
        }
        parent.children.add(newNode);
    }

    private boolean isSearchInterrupted() {
        return Thread.currentThread().isInterrupted();
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
