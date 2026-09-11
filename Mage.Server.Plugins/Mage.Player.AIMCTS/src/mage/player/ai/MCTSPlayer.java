package mage.player.ai;

import mage.abilities.*;
import mage.abilities.common.PassAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.cards.Cards;
import mage.choices.Choice;
import mage.constants.Outcome;
import mage.game.Game;
import mage.player.ai.encoder.ActionEncoder;
import mage.player.ai.encoder.StateEncoder;
import mage.players.Player;
import mage.players.PlayerScript;
import mage.target.Target;
import mage.util.RateLimitedLogger;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

import static mage.target.TargetImpl.STOP_CHOOSING;

/**
 * Dummy player for MCTS sims. replays through micro-decisions with deterministic action scripts created by
 * controlling/thinking player.
 *
 * @author willwroble@gmail.com
 */
public class MCTSPlayer extends ComputerPlayer {

    //the script of actions this dummy player is supposed to follow to catch up to the latest decision
    public PlayerScript actionScript = new PlayerScript();
    public boolean scriptFailed = false;
    private boolean lastToAct = false;
    private boolean isRandomTransition;


    private ActionEncoder.ActionType actionType;


    //additional text for state encoder that describes the decision the player is currently making
    private String decisionText;
    private UUID targetPlayer;
    private Set<Integer> stateVector;
    private StateEncoder encoder;

    private static final Logger logger = Logger.getLogger(MCTSPlayer.class);


    public MCTSPlayer(UUID id) {
        super(id);
    }
    public MCTSPlayer(UUID id, UUID target, StateEncoder encoder) {
        super(id);
        this.targetPlayer = target;
        this.encoder = encoder;
    }


    public MCTSPlayer(final MCTSPlayer player) {
        super(player);
        this.actionType = player.actionType;
        this.targetPlayer = player.targetPlayer;
        this.encoder = player.encoder;
    }
    public void clearFields() {
        lastToAct = false;
        isRandomTransition = false;
        scriptFailed = false;
        getPlayerHistory().clear();
        actionScript.clear();
        chooseTargetOptions.clear();
        choiceOptions.clear();
        numOptionsSize = 0;
        playables.clear();
    }

    @Override
    public MCTSPlayer copy() {
        return new MCTSPlayer(this);
    }

    public ActionEncoder.ActionType getNextAction() {
        return actionType;
    }

    public boolean isRandomTransition() {return isRandomTransition;}
    public boolean isLastToAct() {return lastToAct;}
    public Set<Integer> getStateVector() {return stateVector;}


    @Override
    public void restore(Player player) {
        // simulated player can be created from any player type
        super.restore(player.getRealPlayer());
    }
    @Override
    public int drawCards(int num, Ability source, Game game) {
        isRandomTransition = true;
        return drawCards(num, source, game, null);
    }
    private void freezeState(Game game) {
        game.pause();
        lastToAct = true;
        stateVector = encoder.processState(game, playerId, actionType, decisionText);
    }
    @Override
    public boolean priority(Game game) {
        if(game.isPaused() || game.checkIfGameIsOver()) return false;
        if(!actionScript.prioritySequence.isEmpty()) {
            game.getState().setPriorityPlayerId(playerId);
            ActivatedAbility ability = (ActivatedAbility) actionScript.prioritySequence.pollFirst().copy();
            boolean success = activateAbility(ability, game);
            if(!success && !game.isPaused()) {//if decision costs need to be resolved let them simulate out
                RateLimitedLogger.warn(game.getTurn().getValue(game.getTurnNum()) + " INVALID SCRIPT AT: " + ability.toString());
                illegalGameState(game);
                return false;
            }
            return !(ability instanceof PassAbility);
            //priority history is handled in base player activateAbility()
        }
        playables = getPlayableAbilities(game);
        if(playables.size() == 1 && !game.isCheckPoint(playerId)) {
            pass(game);
            return false;
        }
        game.setLastPriority(playerId);
        decisionText = "priority";
        actionType = ActionEncoder.ActionType.PRIORITY;
        freezeState(game);
        return false;
    }
    @Override
    public void selectAttackers(Game game, UUID attackingPlayerId) {
        if(game.isPaused() || game.checkIfGameIsOver()) return;
        selectAttackersOneAtATime(game, attackingPlayerId);
    }
    @Override
    public void selectBlockers(Ability source, Game game, UUID defendingPlayerId) {
        if(game.isPaused() || game.checkIfGameIsOver()) return;
        selectBlockersOneAtATime(source, game, defendingPlayerId);
    }
    //MCTS always uses manual tapping
    @Override
    public boolean playManaHandling (Ability ability, ManaCost unpaid, final Game game) {
        //if(game.isPaused() || game.checkIfGameIsOver()) return false;
        boolean out;
        if(autoTap) {
            out = super.playManaHandling(ability, unpaid, game);
        } else {
            out = autoPayFromPool(ability, unpaid, game);
        }
        if(!out) {
            //illegalGameState(game);
            return false;
        }
        return true;
    }

    @Override
    protected boolean makeChoice(Outcome outcome, Target target, Ability source, Game game, Cards fromCards) {
        if (game.isPaused() || game.checkIfGameIsOver())
            return makeChoiceFallback(outcome, target, source, game, fromCards); //if game is already paused don't overwrite last decision

        // choose itself for starting player all the time
        if (target.getMessage(game).equals("Select a starting player")) {
            target.add(this.getId(), game);
            return true;
        }

        // nothing to choose
        if (fromCards != null && fromCards.isEmpty()) {
            logger.debug("no cards to choose from");
            return false;
        }

        UUID abilityControllerId = target.getAffectedAbilityControllerId(getId());

        // nothing to choose, e.g. X=0
        if (target.isChoiceCompleted(abilityControllerId, source, game, fromCards)) {
            return false;
        }

        Set<UUID> possible = target.possibleTargets(abilityControllerId, source, game, fromCards).stream().filter(id -> !target.contains(id)).collect(Collectors.toSet());

        // nothing to choose, e.g. no valid targets
        if (possible.isEmpty()) {
            logger.debug("none possible - fizzle");
            return false;
        }
        if(target.isChosen(game)) {
            possible.add(STOP_CHOOSING);//finish choosing early flag
        }
        if (!actionScript.targetSequence.isEmpty()) {
            UUID choice = actionScript.targetSequence.pollFirst();
            getPlayerHistory().targetSequence.add(choice);
            if(!choice.equals(STOP_CHOOSING)) {
                target.addTarget(choice, source, game);
                //choose another?
                makeChoice(outcome, target, source, game, fromCards);
            }
            return target.isChosen(game) && !target.getTargets().isEmpty();
        }
        if(possible.size()==1) {
            //if only one possible just choose it and leave
            UUID id = possible.iterator().next();
            target.addTarget(id, source, game); //id can never be STOP_CHOOSING here
            getPlayerHistory().targetSequence.add(id);
            return true;
        }
        StringBuilder sb = new StringBuilder();
        chooseTargetOptions = possible;
        if(source == null) {
            logger.debug("choose target source is null");
            sb.append("null");
        } else {
            sb.append(source.getRule());
        }
        sb.append(":Choose a target:").append(target.getTargetName());
        decisionText = sb.toString();
        actionType = ActionEncoder.ActionType.CHOOSE_TARGET;
        freezeState(game);
        return makeChoiceFallback(outcome, target, source, game, fromCards);//continue with default target until able to pause
    }
    @Override
    public boolean choose(Outcome outcome, Choice choice, Game game) {
        if(game.isPaused() || game.checkIfGameIsOver()) {
            return chooseFallback(outcome, choice, game);
        }
        if (choice.getChoices().size() == 1) {
            return chooseHelper(outcome, choice, game);
        }
        if (choice.getMessage() != null && (choice.getMessage().equals("Choose creature type") || choice.getMessage().equals("Choose a creature type"))) {
            if (chooseCreatureType(outcome, choice, game)) {
                return true;
            }
        }
        //for choosing colors/types etc
        if (!actionScript.choiceSequence.isEmpty()) {
            String chosen = actionScript.choiceSequence.pollFirst();
            if(!choice.getKeyChoices().isEmpty()) {
                choice.setChoiceByKey(chosen);
            } else {
                choice.setChoice(chosen);
            }
            getPlayerHistory().choiceSequence.add(chosen);
            return true;
        }
        choiceOptions = new HashSet<>(choice.getKeyChoices().keySet());
        if(choiceOptions.isEmpty()) {
            choiceOptions = choice.getChoices();
        }
        if(choiceOptions.isEmpty()) {
            logger.debug("no choice options - fizzle");
            return false; //fizzle
        }
        decisionText = choice.getMessage();
        actionType = ActionEncoder.ActionType.MAKE_CHOICE;
        freezeState(game);
        return chooseFallback(outcome, choice, game);
    }
    @Override
    public boolean chooseUse(Outcome outcome, String message, String secondMessage, String trueText, String falseText, Ability source, Game game) {
        if(game.isPaused() || game.checkIfGameIsOver()) {
            return false;
        }
        if(!actionScript.useSequence.isEmpty()) {
            Boolean chosen =  actionScript.useSequence.pollFirst();
            getPlayerHistory().useSequence.add(chosen);
            return chosen;
        }
        //don't simulate opponent's mulligan decisions - assume they keep 7
        if(message.equals("Mulligan Hand?") && !playerId.equals(targetPlayer)) {
            getPlayerHistory().useSequence.add(false);
            return false;
        }
        decisionText = message;
        actionType = ActionEncoder.ActionType.CHOOSE_USE;
        freezeState(game);
        return false;
    }
    @Override
    public boolean chooseMulligan(Game game) {
        if(getHand().size() < 6 || !allowMulligans) {//TODO: this is safe mulligan, needs complete freedom eventually
            return false;
        }
        return chooseUse(Outcome.Neutral, "Mulligan Hand?", null, game);
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
            logger.warn("unbounded amount selection - limiting to 64");
            max = min + 64;
        }
        numOptionsSize = max - min + 1;
        if (!actionScript.numSequence.isEmpty()) {
            int chosenNum = actionScript.numSequence.pollFirst();
            getPlayerHistory().numSequence.add(chosenNum);
            return chosenNum + min;
        }
        decisionText = "choose num for " + source.toString();
        actionType = ActionEncoder.ActionType.CHOOSE_NUM;
        freezeState(game);
        return min;
    }
    @Override
    public boolean isManualTappingAI() {
        return !autoTap;
    }
    @Override
    public Mode chooseMode(Modes modes, Ability source, Game game) {
        if(game.isPaused() || game.checkIfGameIsOver()) {
            return super.chooseModeHelper(modes, source, game);
        }
        List<Mode> modeOptions = modes.getAvailableModes(source, game).stream()
                .filter(mode -> !modes.getSelectedModes().contains(mode.getId()))
                .filter(mode -> mode.getTargets().canChoose(source.getControllerId(), source, game)).collect(Collectors.toList());
        //if(modes.getMinModes() == 0)
        if(!modeOptions.contains(null)) {
            modeOptions.add(0, null);
        }
        if (modeOptions.isEmpty()) {
            //illegalGameState(game);
            logger.error("no legal mode exists");
            return null;
        }
        int selected = makeChoiceAmount(0, modeOptions.size()-1, game, source, false);
        return modeOptions.get(selected);
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
    @Override
    public void illegalGameState(Game game) {
        if(game.isPaused()) return;
        scriptFailed = true;
        lastToAct = true;
        game.pause();
    }
}

