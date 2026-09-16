package mage.player.ai;

import mage.MageObject;
import mage.abilities.*;
import mage.abilities.common.PassAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.cards.Card;
import mage.cards.Cards;
import mage.choices.Choice;
import mage.constants.*;
import mage.game.Game;
import mage.player.ai.config.MCTSDefaults;
import mage.player.ai.encoder.ActionEncoder;
import mage.player.ai.encoder.StateEncoder;
import mage.players.Player;
import mage.target.Target;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

import static mage.target.TargetImpl.STOP_CHOOSING;

/**
 * traditional MCTS (Monte Carlo Tree Search), expanded to incorporate micro decisions
 *
 * @author BetaSteward_at_googlemail.com, WillWroble
 *
 */
public class ComputerPlayerMCTS extends ComputerPlayer {

    public double searchTimeout = MCTSDefaults.CURRENT.searchTimeout;//seconds
    public int searchBudget = MCTSDefaults.CURRENT.searchBudget;//per thread


    public transient ActionEncoder actionEncoder = null;
    public transient StateEncoder stateEncoder = null;

    //these flags should be set through fields in ParallelDataGenerator.java
    public boolean noNoise = MCTSDefaults.CURRENT.noNoise;
    public boolean noPolicyPriority = MCTSDefaults.CURRENT.noPolicyPriority;
    public boolean noPolicyTarget = MCTSDefaults.CURRENT.noPolicyTarget;
    public boolean noPolicyUse = MCTSDefaults.CURRENT.noPolicyUse;
    public boolean noPolicyOpponent = MCTSDefaults.CURRENT.noPolicyOpponent;

    //dirichlet noise is applied once to the priors of the root node; this represents how much of those priors should be noise
    public double dirichletNoiseEps = MCTSDefaults.CURRENT.dirichletNoiseEps;
    public double selectionTemperature = MCTSDefaults.CURRENT.selectionTemperature;
    public static double DIRICHLET_NOISE_ALPHA = 0.03;
    //how confident to be in network policy priors
    public double priorTemp = MCTSDefaults.CURRENT.priorTemp; //probability exponent (Higher = more confident)
    public double priorBonus = MCTSDefaults.CURRENT.priorBonus; //minimum exploration budget (Higher = more stable)
    //how much to discount the Q scores backpropagation through MCTS; lower means less confident in simulated outcomes
    public double backpropDiscount = MCTSDefaults.CURRENT.backpropDiscount;
    //exploration constant
    public static double C_PUCT = 1;
    //adjust based on available RAM and threads running
    public static int MAX_TREE_NODES = 100000;
    public static int MAX_TREE_DEPTH = 1000;

    public transient MCTSNode root;

    protected String lastPhase = "";
    protected double totalThinkTime = 0;
    protected long totalSimulations = 0;
    public boolean allMana = true;

    protected static final Logger logger = Logger.getLogger(ComputerPlayerMCTS.class);


    public ComputerPlayerMCTS(String name, RangeOfInfluence range, int skill) {
        super(name, range);
        human = false;
    }

    protected ComputerPlayerMCTS(UUID id) {
        super(id);
    }

    public ComputerPlayerMCTS(final ComputerPlayerMCTS player) {
        super(player);
        this.searchTimeout = player.searchTimeout;
        this.searchBudget = player.searchBudget;
        this.noNoise = player.noNoise;
        this.noPolicyPriority = player.noPolicyPriority;
        this.noPolicyTarget = player.noPolicyTarget;
        this.noPolicyUse = player.noPolicyUse;
        this.noPolicyOpponent = player.noPolicyOpponent;
        this.actionEncoder = player.actionEncoder;
        this.stateEncoder = player.stateEncoder;
        this.root = player.root;
        this.lastPhase = player.lastPhase;
        this.totalThinkTime = player.totalThinkTime;
        this.totalSimulations = player.totalSimulations;
        this.allMana = player.allMana;
    }

    @Override
    public ComputerPlayerMCTS copy() {
        return new ComputerPlayerMCTS(this);
    }


    @Override
    public boolean priority(Game game) {
        if (game.getTurnStepType() == PhaseStep.UPKEEP) {
            if (!lastPhase.equals(game.getTurn().getValue(game.getTurnNum()))) {
                logList(game.getTurn().getValue(game.getTurnNum()) + name + " hand: ", new ArrayList<>(hand.getCards(game)));
                lastPhase = game.getTurn().getValue(game.getTurnNum());
            }
        }
        game.getState().setPriorityPlayerId(playerId);
        game.firePriorityEvent(playerId);
        List<ActivatedAbility> playableAbilities = getPlayableAbilities(game);
        if(playableAbilities.size() < 2 && !game.isCheckPoint(playerId)) {
            logger.info("auto pass");
            pass(game);
            return false;
        }
        allMana = true;
        for(ActivatedAbility ability : playableAbilities) {
            allMana &= (ability.isManaActivatedAbility() || ability instanceof PassAbility);
        }
        game.setLastPriority(playerId);
        Ability ability = null;
        MCTSNode best = getNextAction(game, ActionEncoder.ActionType.PRIORITY);
        boolean success = false;
        if(best != null && best.getPriorityAction() != null) {
            ability = best.getPriorityAction().copy();
            //ability = findMatchingAbility(ability, playableAbilities, game);
            success = activateAbility((ActivatedAbility) ability, game);
            root = best;
        }
        if(!success) {
            logger.error("failed to activate chosen ability: " + ability.toString());
            throw new IllegalStateException("failed to activate chosen ability - game failure");
        }
        if(getPlayerHistory().prioritySequence.isEmpty()) {
            logger.error("priority sequence update failure");
        }
        if (ability instanceof PassAbility) {
            return false;
        }
        if(!allMana) {
            logger.info("playable abilities: " + playableAbilities);
            logger.info(game.getTurn().getValue(game.getTurnNum()) + "chose action:" + ability + " success ratio: " + root.getMeanScore());
            logLife(game);
            printBattlefieldScore(game, playerId);
        } else if(!getManaPool().isEmpty()) {
            logger.info("pool=" + getManaPool());
        }
        return true;
    }
    private Ability findMatchingAbility(Ability ability, List<ActivatedAbility> playableAbilities, Game game) {
        for(ActivatedAbility playable : playableAbilities) {
            if(playable.isSameInstance(ability)) {
                if(ability.getSourceId() != null) {
                    if(playable.getSourceId() != null) {
                        if(game.getEntityValue(playable.getSourceId(), playerId).equals(game.getEntityValue(ability.getSourceId(), playerId))) {
                            return playable;
                        }
                        logger.info(game.getEntityValue(playable.getSourceId(), playerId) + " DOESNT EQUAL " + game.getEntityValue(ability.getSourceId(), playerId));
                        continue;
                    }
                }
                return playable;
            }
        }
        logger.error("ability not found: " + ability.toString() + ability.getRule(true) + ability.getClass().getSimpleName());
        return null;
    }
    protected MCTSNode calculateActions(Game game, ActionEncoder.ActionType action) {
        applyMCTS(game, action);
        if (root != null && root.bestChild(game) != null) {
            return root.bestChild(game);
        } else {
            logger.fatal("no root found");
            return null;
        }
    }
    protected MCTSNode getNextAction(Game game, ActionEncoder.ActionType actionType) {
        //TODO: implement. right now only RL version supported
        return null;
    }

    @Override
    public void selectAttackers(Game game, UUID attackingPlayerId) {
        selectAttackersOneAtATime(game, attackingPlayerId);
    }

    @Override
    public void selectBlockers(Ability source, Game game, UUID defendingPlayerId) {
        selectBlockersOneAtATime(source, game, defendingPlayerId);
    }
    @Override
    public boolean playManaHandling (Ability ability, ManaCost unpaid, final Game game) {
        if(autoTap) {
            return super.playManaHandling(ability, unpaid, game);
        } else {
            return autoPayFromPool(ability, unpaid, game);
        }
    }

    @Override
    protected boolean makeChoice(Outcome outcome, Target target, Ability source, Game game, Cards fromCards) {
        // choose itself for starting player all the time
        if (target.getMessage(game).equals("Select a starting player")) {
            target.add(this.getId(), game);
            return true;
        }
        if(game.isSimulation()) {
            return super.makeChoice(outcome, target, source, game, fromCards);
        }

        // nothing to choose
        if (fromCards != null && fromCards.isEmpty()) {
            return false;
        }

        UUID abilityControllerId = target.getAffectedAbilityControllerId(getId());

        // nothing to choose, e.g. X=0
        if (target.isChoiceCompleted(abilityControllerId, source, game, fromCards)) {
            return false;
        }
        logger.info("base choose target " + (source == null ? "null" : source.toString()));
        Set<UUID> possible = target.possibleTargets(abilityControllerId, source, game, fromCards).stream().filter(id -> !target.contains(id)).collect(Collectors.toSet());
        logger.info("possible targets: " + possible.size());
        // nothing to choose, e.g. no valid targets
        if (possible.isEmpty()) {
            return false;
        }
        int n = possible.size();
        n += target.isChosen(game) ? 1 : 0;

        if (n == 1) {
            //if only one possible just choose it and leave
            UUID id = possible.iterator().next();
            target.addTarget(id, source, game);
            getPlayerHistory().targetSequence.add(id);
            return true;
        }

        root = getNextAction(game, ActionEncoder.ActionType.CHOOSE_TARGET);
        if(root == null) {
            return super.makeChoice( outcome,  target,  source,  game,  fromCards);
        }
        UUID targetId = root.getTargetAction();
        if(targetId == null) {
            logger.error("target id is null");
            return false;
        }
        logger.info(String.format("Targeting %s", game.getEntityName(targetId, playerId)));
        getPlayerHistory().targetSequence.add(targetId);

        if(!targetId.equals(STOP_CHOOSING)) {
            target.addTarget(targetId, source, game);
            makeChoice(outcome, target, source, game, fromCards);
        }

        return target.isChosen(game) && !target.getTargets().isEmpty();

    }
    @Override
    public boolean choose(Outcome outcome, Choice choice, Game game) {
        if (choice.getMessage() != null && (choice.getMessage().equals("Choose creature type") || choice.getMessage().equals("Choose a creature type"))) {
            if (chooseCreatureType(outcome, choice, game)) {
                return true;
            }
        }
        logger.info("base make choice " + choice.toString());
        choiceOptions = new HashSet<>(choice.getKeyChoices().keySet());
        if(choiceOptions.isEmpty()) {
            choiceOptions = choice.getChoices();
        }
        if(choiceOptions.isEmpty()) {
            logger.info("choice is empty, spell fizzled");
            return false;
        }
        root = getNextAction(game, ActionEncoder.ActionType.MAKE_CHOICE);
        if(root == null) {
            return false;
        }
        String chosen = root.getChoiceAction();
        logger.info(String.format("Choosing %s", chosen));
        getPlayerHistory().choiceSequence.add(chosen);
        if(!choice.getKeyChoices().isEmpty()) {
            choice.setChoiceByKey(chosen);
        } else {
            choice.setChoice(chosen);
        }

        return true;
    }
    @Override
    protected int makeChoiceAmount(int min, int max, Game game, Ability source, boolean isManaPay) {
        if(game.isPaused() || game.checkIfGameIsOver()) {
            return min;
        }
        if(min >= max) {//one or fewer choices
            return min;
        }
        if(max - min > 64) {//clamp for safety
            max = min + 64;
        }
        numOptionsSize = max - min + 1;
        logger.info("base choose amount " + min + " to " + max);
        root = getNextAction(game, ActionEncoder.ActionType.CHOOSE_NUM);
        if(root == null) {
            getPlayerHistory().numSequence.add(min);
            return min;
        }
        int chosenNum = root.getAmountAction();
        logger.info(String.format("Choosing num %s", chosenNum + min));
        getPlayerHistory().numSequence.add(chosenNum);

        return chosenNum + min;
    }
    public Mode chooseMode(Modes modes, Ability source, Game game) {
        List<Mode> modeOptions = modes.getAvailableModes(source, game).stream()
                .filter(mode -> !modes.getSelectedModes().contains(mode.getId()))
                .filter(mode -> mode.getTargets().canChoose(source.getControllerId(), source, game)).collect(Collectors.toList());
        //if(modes.getMinModes() == 0)
        if(!modeOptions.contains(null)) {
            modeOptions.add(0, null);
        }
        int chosenMode = makeChoiceAmount(0, modeOptions.size()-1, game, source, false);
        return modeOptions.get(chosenMode);
    }
    @Override
    public boolean chooseUse(Outcome outcome, String message, String secondMessage, String trueText, String falseText, Ability source, Game game) {
        logger.info("base choose use " + message);
        if(game.isSimulation()) {
            return false;
        }
        root = getNextAction(game, ActionEncoder.ActionType.CHOOSE_USE);
        if(root == null) {
            getPlayerHistory().useSequence.add(false);
            return false;
        }
        boolean chosen = root.getUseAction();
        logger.info("use " + message + ": " + chosen);
        getPlayerHistory().useSequence.add(chosen);
        return chosen;
    }
    @Override
    public boolean chooseMulligan(Game game) {
        if(getHand().size() < 6 || !allowMulligans) {//TODO: make this toggleable
            return false;
        }
        logger.info(getHand().getCards(game).toString());
        return chooseUse(Outcome.Neutral, "Mulligan Hand?", null, game);
    }


    protected void applyMCTS(final Game game, final ActionEncoder.ActionType action) {
        //TODO: implement. right now only RL version supported

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
        Game mcts = game.createSimulationForAI();
        for (Player copyPlayer : mcts.getState().getPlayers().values()) {
            Player origPlayer = game.getState().getPlayers().get(copyPlayer.getId());
            MCTSPlayer newPlayer = new MCTSPlayer(copyPlayer.getId(), getId(), stateEncoder);
            newPlayer.restore(origPlayer);
            newPlayer.setMatchPlayer(origPlayer.getMatchPlayer());
            //dont shuffle here
            mcts.getState().getPlayers().put(copyPlayer.getId(), newPlayer);
        }
        mcts.pause();
        mcts.setMCTSSimulation(true);
        return mcts;
    }
    //TODO: make true stochastic MCTS (hidden feature set + MCTS discount works for now)
    public static void shuffleUnknowns(Game mcts, UUID playerId) {
        for (Player newPlayer : mcts.getState().getPlayers().values()) {
            if (!newPlayer.getId().equals(playerId)) {
                int handSize = newPlayer.getHand().size();
                newPlayer.getLibrary().addAll(newPlayer.getHand().getCards(mcts), mcts);
                newPlayer.getHand().clear();
                newPlayer.getLibrary().shuffle();
                for (int i = 0; i < handSize; i++) {
                    Card card = newPlayer.getLibrary().drawFromTop(mcts);
                    assert (newPlayer.getLibrary().size() != 0);
                    assert (card != null);
                    card.setZone(Zone.HAND, mcts);
                    newPlayer.getHand().add(card);
                }
            } else {
                newPlayer.getLibrary().shuffle();
            }
        }
    }
    protected void logLife(Game game) {
        StringBuilder sb = new StringBuilder();
        sb.append(game.getTurn().getValue(game.getTurnNum()));
        for (Player player : game.getPlayers().values()) {
            sb.append("[player ").append(player.getName()).append(':').append(player.getLife()).append(']');
        }
        logger.info(sb.toString());
    }
    protected void printBattlefieldScore(Game game, UUID playerId) {
        // hand
        Player player = game.getPlayer(playerId);
        logger.info("[" + game.getPlayer(playerId).getName() + "]" +
                ", life = " + player.getLife());
        String cardsInfo = player.getHand().getCards(game).stream()
                .map(MageObject::getName)
                .collect(Collectors.joining("; "));
        StringBuilder sb = new StringBuilder("-> Hand: [")
                .append(cardsInfo)
                .append("]");
        logger.info(sb.toString());
        for(Player myPlayer : game.getPlayers().values()) {
            // battlefield
            sb.setLength(0);
            String ownPermanentsInfo = game.getBattlefield().getAllPermanents().stream()
                    .filter(p -> p.isOwnedBy(myPlayer.getId()))
                    .map(p -> p.getName()
                            + (p.isTapped() ? ",tapped" : "")
                            + (p.isAttacking() ? ",attacking" : "")
                            + (p.getBlocking() > 0 ? ",blocking" : ""))
                    .collect(Collectors.joining("; "));
            sb.append("-> Permanents: [").append(ownPermanentsInfo).append("]");
            logger.info(sb.toString());
        }

    }
    @Override
    public boolean isManualTappingAI() {
        return !autoTap;
    }
    @Override
    protected List<ActivatedAbility> getPlayableAbilities(Game originalGame) {
        if(autoTap) {
            List<ActivatedAbility> out = getPlayable(originalGame, true);
            out = out.stream().filter(a -> !a.isManaAbility()).collect(Collectors.toList());
            out.add(new PassAbility());
            return out;
        } else {
            return super.getPlayableAbilities(originalGame);
        }
    }
}
