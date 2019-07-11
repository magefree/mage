package mage.player.human;

import mage.MageObject;
import mage.abilities.*;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.hint.HintUtils;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.decks.Deck;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterBlockingCreature;
import mage.filter.common.FilterCreatureForCombat;
import mage.filter.common.FilterCreatureForCombatBlock;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.GameImpl;
import mage.game.combat.CombatGroup;
import mage.game.draft.Draft;
import mage.game.events.GameEvent;
import mage.game.match.Match;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.tournament.Tournament;
import mage.players.Player;
import mage.players.PlayerImpl;
import mage.players.PlayerList;
import mage.target.Target;
import mage.target.TargetAmount;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetAttackingCreature;
import mage.target.common.TargetDefender;
import mage.util.GameLog;
import mage.util.ManaUtil;
import mage.util.MessageToClient;
import org.apache.log4j.Logger;

import java.awt.*;
import java.io.Serializable;
import java.util.List;
import java.util.Queue;
import java.util.*;
import java.util.stream.Collectors;

import static mage.constants.PlayerAction.REQUEST_AUTO_ANSWER_RESET_ALL;
import static mage.constants.PlayerAction.TRIGGER_AUTO_ORDER_RESET_ALL;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class HumanPlayer extends PlayerImpl {

    private transient Boolean responseOpenedForAnswer = false; // can't get response until prepared target (e.g. until send all fire events to all players)
    private final transient PlayerResponse response = new PlayerResponse();

    protected static FilterCreatureForCombatBlock filterCreatureForCombatBlock = new FilterCreatureForCombatBlock();
    protected static FilterCreatureForCombat filterCreatureForCombat = new FilterCreatureForCombat();
    protected static FilterAttackingCreature filterAttack = new FilterAttackingCreature();
    protected static FilterBlockingCreature filterBlock = new FilterBlockingCreature();
    protected final Choice replacementEffectChoice;
    private static final Logger logger = Logger.getLogger(HumanPlayer.class);

    protected HashSet<String> autoSelectReplacementEffects = new HashSet<>();
    protected ManaCost currentlyUnpaidMana;

    protected Set<UUID> triggerAutoOrderAbilityFirst = new HashSet<>();
    protected Set<UUID> triggerAutoOrderAbilityLast = new HashSet<>();
    protected Set<String> triggerAutoOrderNameFirst = new HashSet<>();
    protected Set<String> triggerAutoOrderNameLast = new HashSet<>();

    protected Map<String, Boolean> requestAutoAnswerId = new HashMap<>();
    protected Map<String, Boolean> requestAutoAnswerText = new HashMap<>();

    protected boolean holdingPriority;

    protected Queue<PlayerResponse> actionQueue = new LinkedList<>();
    protected Queue<PlayerResponse> actionQueueSaved = new LinkedList<>();
    protected int actionIterations = 0;
    protected boolean recordingMacro = false;
    protected boolean macroTriggeredSelectionFlag;
    protected boolean activatingMacro = false;

    public HumanPlayer(String name, RangeOfInfluence range, int skill) {
        super(name, range);
        replacementEffectChoice = new ChoiceImpl(true);
        replacementEffectChoice.setMessage("Choose replacement effect to resolve first");
        human = true;
    }

    public HumanPlayer(final HumanPlayer player) {
        super(player);
        this.replacementEffectChoice = player.replacementEffectChoice;
        this.autoSelectReplacementEffects.addAll(player.autoSelectReplacementEffects);
        this.currentlyUnpaidMana = player.currentlyUnpaidMana;

        this.triggerAutoOrderAbilityFirst.addAll(player.triggerAutoOrderAbilityFirst);
        this.triggerAutoOrderAbilityLast.addAll(player.triggerAutoOrderAbilityLast);
        this.triggerAutoOrderNameFirst.addAll(player.triggerAutoOrderNameFirst);
        this.triggerAutoOrderNameLast.addAll(player.triggerAutoOrderNameLast);

        this.requestAutoAnswerId.putAll(player.requestAutoAnswerId);
        this.requestAutoAnswerText.putAll(player.requestAutoAnswerText);

        this.holdingPriority = player.holdingPriority;

        this.actionQueue.addAll(player.actionQueue);
        this.actionQueueSaved.addAll(player.actionQueueSaved);
        this.actionIterations = player.actionIterations;
        this.recordingMacro = player.recordingMacro;
        this.macroTriggeredSelectionFlag = player.macroTriggeredSelectionFlag;
        this.activatingMacro = player.activatingMacro;
    }

    protected boolean isExecutingMacro() {
        return !recordingMacro
                && (!actionQueue.isEmpty()
                || (actionIterations > 0 && !actionQueueSaved.isEmpty()));
    }

    protected void waitResponseOpen() {
        // wait response open for answer process
        int numTimesWaiting = 0;
        while (!responseOpenedForAnswer && canRespond()) {
            numTimesWaiting++;
            if (numTimesWaiting >= 300) {
                // game freezed -- need to report about error and continue to execute
                String s = "ERROR - game freezed in waitResponseOpen for user " + getName() + " (connection problem)";
                //Throwable th = new IllegalStateException(s); stack info
                logger.error(s);
                break;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.warn("Response waiting interrupted for " + getId());
            }
        }
    }

    protected boolean pullResponseFromQueue(Game game) {
        if (actionQueue.isEmpty() && actionIterations > 0 && !actionQueueSaved.isEmpty()) {
            actionQueue = new LinkedList<>(actionQueueSaved);
            actionIterations--;
//            logger.info("MACRO iteration: " + actionIterations);
        }
        PlayerResponse action = actionQueue.poll();
        if (action != null) {
            if (action.getString() != null
                    && action.getString().equals("resolveStack")) {
                action = actionQueue.poll();
                if (action == null) {
                    return false;
                }
                sendPlayerAction(PlayerAction.PASS_PRIORITY_UNTIL_STACK_RESOLVED, game, null);
            }
            //waitResponseOpen(); // it's a macro action, no need it here?
            synchronized (response) {
                response.copy(action);
                response.notifyAll();
                macroTriggeredSelectionFlag = false;
                return true;
            }
        }
        return false;
    }

    protected void prepareForResponse(Game game) {
        //logger.info("Prepare waiting " + getId());
        responseOpenedForAnswer = false;
    }

    protected void waitForResponse(Game game) {
        if (isExecutingMacro()) {
            pullResponseFromQueue(game);
//            logger.info("MACRO pull from queue: " + response.toString());
//            try {
//                TimeUnit.MILLISECONDS.sleep(1000);
//            } catch (InterruptedException e) {
//            }
            return;
        }

        // wait player's answer loop
        boolean loop = true;
        while (loop) {
            // start waiting for next answer
            response.clear();
            game.resumeTimer(getTurnControlledBy());
            responseOpenedForAnswer = true;

            loop = false;

            synchronized (response) {
                try {
                    response.wait();
                } catch (InterruptedException ex) {
                    logger.error("Response error for player " + getName() + " gameId: " + game.getId(), ex);
                } finally {
                    responseOpenedForAnswer = false;
                    game.pauseTimer(getTurnControlledBy());
                }
            }

            // game recived immidiate response on OTHER player concede -- need to process end game and continue to wait
            if (response.getResponseConcedeCheck()) {
                ((GameImpl) game).checkConcede();
                if (game.hasEnded()) {
                    return;
                }

                if (isInGame()) {
                    // wait another answer
                    loop = true;
                }
            }
        }

        if (recordingMacro && !macroTriggeredSelectionFlag) {
            actionQueueSaved.add(new PlayerResponse(response));
        }
    }

    @Override
    public boolean chooseMulligan(Game game) {
        updateGameStatePriority("chooseMulligan", game);
        int nextHandSize = game.mulliganDownTo(playerId);
        do {
            String cardsCountInfo = nextHandSize + (nextHandSize == 1 ? " card" : " cards");
            String message;
            if (getHand().size() > nextHandSize) {
                // pay
                message = "Mulligan " + HintUtils.prepareText("down to " + cardsCountInfo, Color.YELLOW) + "?";
            } else {
                // free
                message = "Mulligan " + HintUtils.prepareText("for free", Color.GREEN) + ", draw another " + cardsCountInfo + "?";
            }
            Map<String, Serializable> options = new HashMap<>();
            options.put("UI.left.btn.text", "Mulligan");
            options.put("UI.right.btn.text", "Keep");
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireAskPlayerEvent(playerId, new MessageToClient(message), null, options);
            }
            waitForResponse(game);
        } while (response.getBoolean() == null && !abort);
        if (!abort) {
            return response.getBoolean();
        }
        return false;
    }

    @Override
    public boolean chooseUse(Outcome outcome, String message, Ability source, Game game) {
        return this.chooseUse(outcome, message, null, "Yes", "No", source, game);
    }

    @Override
    public boolean chooseUse(Outcome outcome, String message, String secondMessage, String trueText, String falseText, Ability source, Game game) {
        MessageToClient messageToClient = new MessageToClient(message, secondMessage);
        Map<String, Serializable> options = new HashMap<>(2);
        if (trueText != null) {
            options.put("UI.left.btn.text", trueText);
        }
        if (falseText != null) {
            options.put("UI.right.btn.text", falseText);
        }
        if (source != null) {
            Boolean answer = requestAutoAnswerId.get(source.getOriginalId() + "#" + message);
            if (answer != null) {
                return answer;
            } else {
                answer = requestAutoAnswerText.get(message);
                if (answer != null) {
                    return answer;
                }
            }
        }
        updateGameStatePriority("chooseUse", game);
        do {
            if (messageToClient.getSecondMessage() == null) {
                messageToClient.setSecondMessage(getRelatedObjectName(source, game));
            }
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireAskPlayerEvent(playerId, messageToClient, source, options);
            }
            waitForResponse(game);
        } while (response.getBoolean() == null && !abort);
        if (!abort) {
            return response.getBoolean();
        }
        return false;
    }

    private String getRelatedObjectName(Ability source, Game game) {
        if (source != null) {
            return getRelatedObjectName(source.getSourceId(), game);
        }
        return null;
    }

    private String getRelatedObjectName(UUID sourceId, Game game) {
        MageObject mageObject = game.getObject(sourceId);
        if (mageObject != null) {
            return mageObject.getLogName();
        }
        return null;
    }

    private String addSecondLineWithObjectName(String message, UUID sourceId, Game game) {
        if (sourceId != null) {
            MageObject mageObject = game.getPermanent(sourceId);
            if (mageObject == null) {
                mageObject = game.getCard(sourceId);
            }
            if (mageObject != null) {
                message += "<div style='font-size:11pt'>" + mageObject.getLogName() + "</div>";
            }
        }
        return message;
    }

    @Override
    public int chooseReplacementEffect(Map<String, String> rEffects, Game game) {
        updateGameStatePriority("chooseEffect", game);
        if (rEffects.size() <= 1) {
            return 0;
        }
        if (!autoSelectReplacementEffects.isEmpty()) {
            for (String autoKey : autoSelectReplacementEffects) {
                int count = 0;
                for (String effectKey : rEffects.keySet()) {
                    if (effectKey.equals(autoKey)) {
                        return count;
                    }
                    count++;
                }
            }
        }

        replacementEffectChoice.getChoices().clear();
        replacementEffectChoice.setKeyChoices(rEffects);

        // Check if there are different ones
        int differentChoices = 0;
        String lastChoice = "";
        for (String value : replacementEffectChoice.getKeyChoices().values()) {
            if (!lastChoice.equalsIgnoreCase(value)) {
                lastChoice = value;
                differentChoices++;
            }
        }

        while (!abort && differentChoices > 1) {
            updateGameStatePriority("chooseEffect", game);
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireChooseChoiceEvent(playerId, replacementEffectChoice);
            }
            waitForResponse(game);
            logger.debug("Choose effect: " + response.getString());
            if (response.getString() != null) {
                if (response.getString().startsWith("#")) {
                    autoSelectReplacementEffects.add(response.getString().substring(1));
                    replacementEffectChoice.setChoiceByKey(response.getString().substring(1));
                } else {
                    replacementEffectChoice.setChoiceByKey(response.getString());
                }
                if (replacementEffectChoice.getChoiceKey() != null) {
                    int index = 0;
                    for (String key : rEffects.keySet()) {
                        if (replacementEffectChoice.getChoiceKey().equals(key)) {
                            return index;
                        }
                        index++;
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public boolean choose(Outcome outcome, Choice choice, Game game) {
        if (Outcome.PutManaInPool == outcome) {
            if (currentlyUnpaidMana != null
                    && ManaUtil.tryToAutoSelectAManaColor(choice, currentlyUnpaidMana)) {
                return true;
            }
        }
        updateGameStatePriority("choose(3)", game);
        while (canRespond()) {
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireChooseChoiceEvent(playerId, choice);
            }
            waitForResponse(game);
            String val = response.getString();
            if (val != null && !val.isEmpty()) {
                if (choice.isKeyChoice()) {
                    choice.setChoiceByKey(val);
                } else {
                    choice.setChoice(val);
                }
                return true;
            } else if (!choice.isRequired()) {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean choose(Outcome outcome, Target target, UUID sourceId, Game game) {
        return choose(outcome, target, sourceId, game, null);
    }

    @Override
    public boolean choose(Outcome outcome, Target target, UUID sourceId, Game game, Map<String, Serializable> options) {
        // choose one or multiple permanents
        updateGameStatePriority("choose(5)", game);
        UUID abilityControllerId = playerId;
        if (target.getTargetController() != null
                && target.getAbilityController() != null) {
            abilityControllerId = target.getAbilityController();
        }
        if (options == null) {
            options = new HashMap<>();
        }
        while (!abort) {
            Set<UUID> targetIds = target.possibleTargets(sourceId, abilityControllerId, game);
            if (targetIds == null
                    || targetIds.isEmpty()) {
                return target.getTargets().size() >= target.getNumberOfTargets();
            }
            boolean required = target.isRequired(sourceId, game);
            if (target.getTargets().size() >= target.getNumberOfTargets()) {
                required = false;
            }

            List<UUID> chosen = target.getTargets();
            options.put("chosen", (Serializable) chosen);

            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireSelectTargetEvent(getId(), new MessageToClient(target.getMessage(), getRelatedObjectName(sourceId, game)), targetIds, required, getOptions(target, options));
            }
            waitForResponse(game);
            if (response.getUUID() != null) {
                // selected some target

                // remove selected
                if (target.getTargets().contains(response.getUUID())) {
                    target.remove(response.getUUID());
                    continue;
                }

                if (!targetIds.contains(response.getUUID())) {
                    continue;
                }
                if (target instanceof TargetPermanent) {
                    if (((TargetPermanent) target).canTarget(abilityControllerId, response.getUUID(), sourceId, game, false)) {
                        target.add(response.getUUID(), game);
                        if (target.doneChosing()) {
                            return true;
                        }
                    }
                } else {
                    MageObject object = game.getObject(sourceId);
                    if (object instanceof Ability) {
                        if (target.canTarget(response.getUUID(), (Ability) object, game)) {
                            if (target.getTargets().contains(response.getUUID())) { // if already included remove it with
                                target.remove(response.getUUID());
                            } else {
                                target.addTarget(response.getUUID(), (Ability) object, game);
                                if (target.doneChosing()) {
                                    return true;
                                }
                            }
                        }
                    } else if (target.canTarget(response.getUUID(), game)) {
                        if (target.getTargets().contains(response.getUUID())) { // if already included remove it with
                            target.remove(response.getUUID());
                        } else {
                            target.addTarget(response.getUUID(), null, game);
                            if (target.doneChosing()) {
                                return true;
                            }
                        }
                    }
                }
            } else {
                // send other command like cancel or done (??sends other commands like concede??)

                // auto-complete on all selected
                if (target.getTargets().size() >= target.getNumberOfTargets()) {
                    return true;
                }

                // cancel/done button
                if (!required) {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Target target, Ability source, Game game) {
        // choose one or multiple targets
        updateGameStatePriority("chooseTarget", game);
        UUID abilityControllerId = playerId;
        if (target.getAbilityController() != null) {
            abilityControllerId = target.getAbilityController();
        }
        while (!abort) {
            Set<UUID> possibleTargets = target.possibleTargets(source == null ? null : source.getSourceId(), abilityControllerId, game);
            boolean required = target.isRequired(source != null ? source.getSourceId() : null, game);
            if (possibleTargets.isEmpty()
                    || target.getTargets().size() >= target.getNumberOfTargets()) {
                required = false;
            }

            prepareForResponse(game);
            if (!isExecutingMacro()) {
                // hmm
                game.fireSelectTargetEvent(getId(), new MessageToClient(target.getMessage(), getRelatedObjectName(source, game)), possibleTargets, required, getOptions(target, null));
            }
            waitForResponse(game);
            if (response.getUUID() != null) {

                // remove selected
                if (target.getTargets().contains(response.getUUID())) {
                    target.remove(response.getUUID());
                    continue;
                }

                if (possibleTargets.contains(response.getUUID())) {
                    if (target.canTarget(abilityControllerId, response.getUUID(), source, game)) {
                        target.addTarget(response.getUUID(), source, game);
                        if (target.doneChosing()) {
                            return true;
                        }
                    }
                }
            } else {
                if (target.getTargets().size() >= target.getNumberOfTargets()) {
                    return true;
                }
                if (!required) {
                    return false;
                }
            }
        }
        return false;
    }

    private Map<String, Serializable> getOptions(Target target, Map<String, Serializable> options) {
        if (options == null) {
            options = new HashMap<>();
        }
        if (target.getTargets().size() >= target.getNumberOfTargets()
                && !options.containsKey("UI.right.btn.text")) {
            options.put("UI.right.btn.text", "Done");
        }
        options.put("targetZone", target.getZone());
        return options;
    }

    @Override
    public boolean choose(Outcome outcome, Cards cards, TargetCard target, Game game) {
        // choose one or multiple cards
        if (cards == null) {
            return false;
        }
        updateGameStatePriority("choose(4)", game);
        while (!abort) {
            boolean required = target.isRequired();
            // if there is no cards to select from, then add possibility to cancel choosing action
            int count = cards.count(target.getFilter(), game);
            if (count == 0) {
                required = false;
            }
            if (target.getTargets().size() >= target.getNumberOfTargets()) {
                required = false;
            }
            Map<String, Serializable> options = getOptions(target, null);
            List<UUID> chosen = target.getTargets();
            options.put("chosen", (Serializable) chosen);
            List<UUID> choosable = new ArrayList<>();
            for (UUID cardId : cards) {
                if (target.canTarget(cardId, cards, game)) {
                    choosable.add(cardId);
                }
            }
            if (!choosable.isEmpty()) {
                options.put("choosable", (Serializable) choosable);
            }

            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireSelectTargetEvent(playerId, new MessageToClient(target.getMessage()), cards, required, options);
            }
            waitForResponse(game);
            if (response.getUUID() != null) {
                if (target.canTarget(response.getUUID(), cards, game)) {
                    if (target.getTargets().contains(response.getUUID())) { // if already included remove it with
                        target.remove(response.getUUID());
                    } else {
                        target.add(response.getUUID(), game);
                        if (target.doneChosing()) {
                            return true;
                        }
                    }
                }
            } else {
                if (target.getTargets().size() >= target.getNumberOfTargets()) {
                    return true;
                }
                if (!required) {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
        // choose one or multiple target cards
        updateGameStatePriority("chooseTarget(5)", game);
        while (!abort) {
            boolean required;
            if (target.isRequiredExplicitlySet()) {
                required = target.isRequired();
            } else {
                required = target.isRequired(source);
            }
            // if there is no cards to select from, then add possibility to cancel choosing action
            if (cards == null) {
                required = false;
            } else {
                int count = cards.count(target.getFilter(), game);
                if (count == 0) {
                    required = false;
                }
            }
            if (target.getTargets().size() >= target.getNumberOfTargets()) {
                required = false;
            }
            Map<String, Serializable> options = getOptions(target, null);
            List<UUID> chosen = target.getTargets();
            options.put("chosen", (Serializable) chosen);
            List<UUID> choosable = new ArrayList<>();
            for (UUID cardId : cards) {
                if (target.canTarget(cardId, cards, game)) {
                    choosable.add(cardId);
                }
            }
            if (!choosable.isEmpty()) {
                options.put("choosable", (Serializable) choosable);
            }

            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireSelectTargetEvent(playerId, new MessageToClient(target.getMessage(), getRelatedObjectName(source, game)), cards, required, options);
            }
            waitForResponse(game);

            if (response.getUUID() != null) {
                if (target.getTargets().contains(response.getUUID())) { // if already included remove it
                    target.remove(response.getUUID());
                } else if (target.canTarget(response.getUUID(), cards, game)) {
                    target.addTarget(response.getUUID(), source, game);
                    if (target.doneChosing()) {
                        return true;
                    }
                }
            } else {
                if (target.getTargets().size() >= target.getNumberOfTargets()) {
                    return true;
                }
                if (!required) {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public boolean chooseTargetAmount(Outcome outcome, TargetAmount target, Ability source, Game game) {
        // choose amount
        updateGameStatePriority("chooseTargetAmount", game);
        while (!abort) {
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireSelectTargetEvent(playerId, new MessageToClient(target.getMessage() + "\n Amount remaining:" + target.getAmountRemaining(), getRelatedObjectName(source, game)),
                        target.possibleTargets(source == null ? null : source.getSourceId(), playerId, game),
                        target.isRequired(source),
                        getOptions(target, null));
            }
            waitForResponse(game);
            if (response.getUUID() != null) {
                if (target.canTarget(response.getUUID(), source, game)) {
                    UUID targetId = response.getUUID();
                    int amountSelected = getAmount(1, target.getAmountRemaining(), "Select amount", game);
                    target.addTarget(targetId, amountSelected, source, game);
                    return true;
                }
            } else if (!target.isRequired(source)) {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean priority(Game game) {
        passed = false;
        if (!abort) {
            HumanPlayer controllingPlayer = this;
            if (isGameUnderControl()) {
                Player player = game.getPlayer(getTurnControlledBy());
                if (player instanceof HumanPlayer) {
                    controllingPlayer = (HumanPlayer) player;
                }
            }

            if (getJustActivatedType() != null && !holdingPriority) {
                if (controllingPlayer.getUserData().isPassPriorityCast()
                        && getJustActivatedType() == AbilityType.SPELL) {
                    setJustActivatedType(null);
                    pass(game);
                    return false;
                }
                if (controllingPlayer.getUserData().isPassPriorityActivation()
                        && getJustActivatedType() == AbilityType.ACTIVATED) {
                    setJustActivatedType(null);
                    pass(game);
                    return false;
                }
            }

            // STOP conditions (temporary stop without skip reset)
            boolean quickStop = false;
            if (isGameUnderControl()) {

                // if was attacked - always stop BEFORE blocker step (to cast extra spells)
                if (game.getTurn().getStepType() == PhaseStep.DECLARE_ATTACKERS
                        && game.getCombat().getPlayerDefenders(game).contains(playerId)) {

                    FilterCreatureForCombatBlock filter = filterCreatureForCombatBlock.copy();
                    filter.add(new ControllerIdPredicate(playerId));
                    // stop skip on any/zero permanents available
                    int possibleBlockersCount = game.getBattlefield().count(filter, null, playerId, game);
                    boolean canStopOnAny = possibleBlockersCount != 0 && getControllingPlayersUserData(game).getUserSkipPrioritySteps().isStopOnDeclareBlockersWithAnyPermanents();
                    boolean canStopOnZero = possibleBlockersCount == 0 && getControllingPlayersUserData(game).getUserSkipPrioritySteps().isStopOnDeclareBlockersWithZeroPermanents();
                    quickStop = canStopOnAny || canStopOnZero;
                }
            }

            // SKIP - use the skip actions only if the player itself controls its turn
            if (!quickStop && isGameUnderControl()) {

                if (passedAllTurns || passedTurnSkipStack) {
                    if (passWithManaPoolCheck(game)) {
                        return false;
                    }
                }

                if (passedUntilEndStepBeforeMyTurn) {
                    if (game.getTurn().getStepType() != PhaseStep.END_TURN) {
                        // other step
                        if (passWithManaPoolCheck(game)) {
                            return false;
                        }
                    } else {
                        // end step - search yourself
                        PlayerList playerList = game.getState().getPlayerList(playerId);
                        if (!playerList.getPrevious().equals(game.getActivePlayerId())) {
                            if (passWithManaPoolCheck(game)) {
                                return false;
                            }
                        } else {
                            // stop
                            passedUntilEndStepBeforeMyTurn = false;
                        }
                    }
                }

                if (game.getStack().isEmpty()) {
                    // empty stack

                    boolean dontCheckPassStep = false;
                    if (passedUntilStackResolved) { // Don't skip to next step with this action. It always only resolves a stack. If stack is empty it does nothing.
                        passedUntilStackResolved = false;
                        dontCheckPassStep = true;
                    }

                    if (passedTurn
                            || passedTurnSkipStack) {
                        if (passWithManaPoolCheck(game)) {
                            return false;
                        }
                    }

                    if (passedUntilNextMain) {
                        if (game.getTurn().getStepType() == PhaseStep.POSTCOMBAT_MAIN
                                || game.getTurn().getStepType() == PhaseStep.PRECOMBAT_MAIN) {
                            // it's main step
                            if (!skippedAtLeastOnce
                                    || (!playerId.equals(game.getActivePlayerId())
                                    && !controllingPlayer.getUserData().getUserSkipPrioritySteps().isStopOnAllMainPhases())) {
                                skippedAtLeastOnce = true;
                                if (passWithManaPoolCheck(game)) {
                                    return false;
                                }
                            } else {
                                dontCheckPassStep = true;
                                passedUntilNextMain = false; // reset skip action
                            }
                        } else {
                            skippedAtLeastOnce = true;
                            if (passWithManaPoolCheck(game)) {
                                return false;
                            }
                        }
                    }

                    if (passedUntilEndOfTurn) {
                        if (game.getTurn().getStepType() == PhaseStep.END_TURN) {
                            // it's end of turn step
                            if (!skippedAtLeastOnce
                                    || (playerId.equals(game.getActivePlayerId())
                                    && !controllingPlayer
                                    .getUserData()
                                    .getUserSkipPrioritySteps()
                                    .isStopOnAllEndPhases())) {
                                skippedAtLeastOnce = true;
                                if (passWithManaPoolCheck(game)) {
                                    return false;
                                }
                            } else {
                                dontCheckPassStep = true;
                                passedUntilEndOfTurn = false; // reset skip action
                            }
                        } else {
                            skippedAtLeastOnce = true;
                            if (passWithManaPoolCheck(game)) {
                                return false;
                            }
                        }
                    }

                    if (!dontCheckPassStep
                            && checkPassStep(game, controllingPlayer)) {
                        if (passWithManaPoolCheck(game)) {
                            return false;
                        }
                    }

                } else {
                    // non empty stack
                    boolean haveNewObjectsOnStack = !Objects.equals(dateLastAddedToStack, game.getStack().getDateLastAdded());
                    dateLastAddedToStack = game.getStack().getDateLastAdded();
                    if (passedUntilStackResolved) {
                        if (haveNewObjectsOnStack
                                && (playerId.equals(game.getActivePlayerId())
                                && controllingPlayer
                                .getUserData()
                                .getUserSkipPrioritySteps()
                                .isStopOnStackNewObjects())) {
                            // new objects on stack -- disable "pass until stack resolved"
                            passedUntilStackResolved = false;
                        } else {
                            // no new objects on stack -- go to next priority
                        }
                    }
                    if (passedUntilStackResolved) {
                        if (passWithManaPoolCheck(game)) {
                            return false;
                        }
                    }
                }
            }

            while (canRespond()) {
                updateGameStatePriority("priority", game);
                holdingPriority = false;
                prepareForResponse(game);
                if (!isExecutingMacro()) {
                    game.firePriorityEvent(playerId);
                }
                waitForResponse(game);
                if (game.executingRollback()) {
                    return true;
                }
                if (response.getBoolean() != null
                        || response.getInteger() != null) {
                    if (passWithManaPoolCheck(game) && !activatingMacro) {
                        return false;
                    } else {
                        if (activatingMacro) {
                            synchronized (actionQueue) {
                                actionQueue.notifyAll();
                            }
                        }
                        continue;
                    }
                }
                break;
            }

            if (response.getString() != null
                    && response.getString().equals("special")) {
                specialAction(game);
            } else if (response.getUUID() != null) {
                boolean result = false;
                MageObject object = game.getObject(response.getUUID());
                if (object != null) {
                    Zone zone = game.getState().getZone(object.getId());
                    if (zone != null) {
                        // look at card or try to cast/activate abilities
                        Player actingPlayer = null;
                        LinkedHashMap<UUID, ActivatedAbility> useableAbilities = null;
                        if (playerId.equals(game.getPriorityPlayerId())) {
                            actingPlayer = this;
                        } else if (getPlayersUnderYourControl().contains(game.getPriorityPlayerId())) {
                            actingPlayer = game.getPlayer(game.getPriorityPlayerId());
                        }
                        if (actingPlayer != null) {
                            useableAbilities = actingPlayer.getUseableActivatedAbilities(object, zone, game);
                        }

                        if (object instanceof Card
                                && ((Card) object).isFaceDown(game)
                                && lookAtFaceDownCard((Card) object, game, useableAbilities == null ? 0 : useableAbilities.size())) {
                            result = true;
                        } else {
                            if (useableAbilities != null
                                    && !useableAbilities.isEmpty()) {
                                activateAbility(useableAbilities, object, game);
                                result = true;
                            }
                        }
                    }
                }
                return result;
            } else return response.getManaType() == null;
            return true;
        }
        return false;
    }

    private boolean checkPassStep(Game game, HumanPlayer controllingPlayer) {
        try {

            if (playerId.equals(game.getActivePlayerId())) {
                return !controllingPlayer.getUserData().getUserSkipPrioritySteps().getYourTurn().isPhaseStepSet(game.getStep().getType());
            } else {
                return !controllingPlayer.getUserData().getUserSkipPrioritySteps().getOpponentTurn().isPhaseStepSet(game.getStep().getType());
            }
        } catch (NullPointerException ex) {
            if (controllingPlayer.getUserData() != null) {
                if (controllingPlayer.getUserData().getUserSkipPrioritySteps() != null) {
                    if (game.getStep() != null) {
                        if (game.getStep().getType() == null) {
                            logger.error("game.getStep().getType() == null");
                        }
                    } else {
                        logger.error("game.getStep() == null");
                    }
                } else {
                    logger.error("UserData.getUserSkipPrioritySteps == null");
                }
            } else {
                logger.error("UserData == null");
            }
        }
        return false;
    }

    @Override
    public TriggeredAbility chooseTriggeredAbility(List<TriggeredAbility> abilities, Game game) {
        // choose triggered abilitity from list
        String autoOrderRuleText = null;
        boolean autoOrderUse = getControllingPlayersUserData(game).isAutoOrderTrigger();
        while (!abort) {
            // try to set trigger auto order
            List<TriggeredAbility> abilitiesWithNoOrderSet = new ArrayList<>();
            TriggeredAbility abilityOrderLast = null;
            for (TriggeredAbility ability : abilities) {
                if (triggerAutoOrderAbilityFirst.contains(ability.getOriginalId())) {
                    return ability;
                }
                MageObject object = game.getObject(ability.getSourceId());
                String rule = ability.getRule(object != null ? object.getName() : null);
                if (triggerAutoOrderNameFirst.contains(rule)) {
                    return ability;
                }
                if (triggerAutoOrderAbilityLast.contains(ability.getOriginalId())) {
                    abilityOrderLast = ability;
                    continue;
                }
                if (triggerAutoOrderNameLast.contains(rule)) {
                    abilityOrderLast = ability;
                    continue;
                }
                if (autoOrderUse) {
                    if (autoOrderRuleText == null) {
                        autoOrderRuleText = rule;
                    } else if (!rule.equals(autoOrderRuleText)) {
                        autoOrderUse = false;
                    }
                }
                abilitiesWithNoOrderSet.add(ability);
            }
            if (abilitiesWithNoOrderSet.isEmpty()) {
                return abilityOrderLast;
            }
            if (abilitiesWithNoOrderSet.size() == 1
                    || autoOrderUse) {
                return abilitiesWithNoOrderSet.iterator().next();
            }
            macroTriggeredSelectionFlag = true;
            updateGameStatePriority("chooseTriggeredAbility", game);
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireSelectTargetTriggeredAbilityEvent(playerId, "Pick triggered ability (goes to the stack first)", abilitiesWithNoOrderSet);
            }
            waitForResponse(game);
            if (response.getUUID() != null) {
                for (TriggeredAbility ability : abilitiesWithNoOrderSet) {
                    if (ability.getId().equals(response.getUUID())
                            || (!macroTriggeredSelectionFlag
                            && ability.getSourceId().equals(response.getUUID()))) {
                        if (recordingMacro) {
                            PlayerResponse tResponse = new PlayerResponse();
                            tResponse.setUUID(ability.getSourceId());
                            actionQueueSaved.add(tResponse);
                            logger.debug("Adding Triggered Ability Source: " + tResponse);
                        }
                        macroTriggeredSelectionFlag = false;
                        return ability;
                    }
                }
            }
        }
        macroTriggeredSelectionFlag = false;
        return null;
    }

    @Override
    public boolean playMana(Ability abilityToCast, ManaCost unpaid, String promptText, Game game) {
        payManaMode = true;
        boolean result = playManaHandling(abilityToCast, unpaid, promptText, game);
        payManaMode = false;
        return result;
    }

    protected boolean playManaHandling(Ability abilityToCast, ManaCost unpaid, String promptText, Game game) {
        // choose mana to pay (from permanents or from pool)
        updateGameStatePriority("playMana", game);
        Map<String, Serializable> options = new HashMap<>();
        prepareForResponse(game);
        if (!isExecutingMacro()) {
            game.firePlayManaEvent(playerId, "Pay " + promptText, options);
        }
        waitForResponse(game);
        if (!this.canRespond()) {
            return false;
        }
        if (response.getBoolean() != null) {
            return false;
        } else if (response.getUUID() != null) {
            playManaAbilities(abilityToCast, unpaid, game);
        } else if (response.getString() != null
                && response.getString().equals("special")) {
            if (unpaid instanceof ManaCostsImpl) {
                specialManaAction(unpaid, game);
            }
        } else if (response.getManaType() != null) {
            // this mana type can be paid once from pool
            if (response.getResponseManaTypePlayerId().equals(this.getId())) {
                this.getManaPool().unlockManaType(response.getManaType());
            }
            // TODO: Handle if mana pool
        }
        return true;
    }

    /**
     * Gets the number of times the user wants to repeat their macro
     *
     * @param game
     * @return
     */
    public int announceRepetitions(Game game) {
        int xValue = 0;
        updateGameStatePriority("announceRepetitions", game);
        do {
            prepareForResponse(game);
            game.fireGetAmountEvent(playerId, "How many times do you want to repeat your shortcut?", 0, 999);
            waitForResponse(game);
        } while (response.getInteger() == null
                && !abort);
        if (response != null
                && response.getInteger() != null) {
            xValue = response.getInteger();
        }
        return xValue;
    }

    /**
     * Gets the amount of mana the player want to spent for a x spell
     *
     * @param multiplier - X multiplier after replace events
     */
    @Override
    public int announceXMana(int min, int max, int multiplier, String message, Game game, Ability ability) {
        int xValue = 0;
        String extraMessage = (multiplier == 1 ? "" : ", X will be increased by " + multiplier + " times");
        updateGameStatePriority("announceXMana", game);
        do {
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireGetAmountEvent(playerId, message + extraMessage, min, max);
            }
            waitForResponse(game);
        } while (response.getInteger() == null
                && !abort);
        if (response != null
                && response.getInteger() != null) {
            xValue = response.getInteger();
        }
        return xValue;
    }

    @Override
    public int announceXCost(int min, int max, String message, Game game, Ability ability, VariableCost variableCost) {
        int xValue = 0;
        updateGameStatePriority("announceXCost", game);
        do {
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireGetAmountEvent(playerId, message, min, max);
            }
            waitForResponse(game);
        } while (response.getInteger() == null
                && !abort);
        if (response != null
                && response.getInteger() != null) {
            xValue = response.getInteger();
        }
        return xValue;
    }

    protected void playManaAbilities(Ability abilityToCast, ManaCost unpaid, Game game) {
        updateGameStatePriority("playManaAbilities", game);
        MageObject object = game.getObject(response.getUUID());
        if (object == null) {
            return;
        }
        if (AbilityType.SPELL.equals(abilityToCast.getAbilityType())) {
            Spell spell = game.getStack().getSpell(abilityToCast.getSourceId());
            if (spell != null && !spell.isResolving()
                    && spell.isDoneActivatingManaAbilities()) {
                game.informPlayer(this, "You can no longer use activated mana abilities to pay for the current spell. Cancel and recast the spell and activate mana abilities first.");
                return;
            }
        }
        Zone zone = game.getState().getZone(object.getId());
        if (zone != null) {
            LinkedHashMap<UUID, ActivatedManaAbilityImpl> useableAbilities = getUseableManaAbilities(object, zone, game);
            if (useableAbilities != null
                    && !useableAbilities.isEmpty()) {
                useableAbilities = ManaUtil.tryToAutoPay(unpaid, useableAbilities); // eliminates other abilities if one fits perfectly
                currentlyUnpaidMana = unpaid;
                activateAbility(useableAbilities, object, game);
                currentlyUnpaidMana = null;
            }
        }
    }

    @Override
    public void selectAttackers(Game game, UUID attackingPlayerId) {
        updateGameStatePriority("selectAttackers", game);
        FilterCreatureForCombat filter = filterCreatureForCombat.copy();
        filter.add(new ControllerIdPredicate(attackingPlayerId));

        while (!abort) {

            List<UUID> possibleAttackers = new ArrayList<>();
            for (Permanent possibleAttacker : game.getBattlefield().getActivePermanents(filter, attackingPlayerId, game)) {
                if (possibleAttacker.canAttack(null, game)) {
                    possibleAttackers.add(possibleAttacker.getId());
                }
            }

            // skip declare attack step
            // old version:
            // - passedAllTurns, passedUntilEndStepBeforeMyTurn: always skipped
            // - other: on disabled option skipped
            if (passedAllTurns
                    || passedUntilEndStepBeforeMyTurn
                    || (!getControllingPlayersUserData(game)
                    .getUserSkipPrioritySteps()
                    .isStopOnDeclareAttackers()
                    && (passedTurn
                    || passedTurnSkipStack
                    || passedUntilEndOfTurn
                    || passedUntilNextMain))) {
                if (checkIfAttackersValid(game)) {
                    return;
                }
            }

            /*
            // new version:
            // - all: on disabled option skipped (if attackers selected)
            if (!getControllingPlayersUserData(game)
                    .getUserSkipPrioritySteps()
                    .isStopOnDeclareAttackers()
                    && (possibleAttackers.size() > 0)) {
                if (checkIfAttackersValid(game)) {
                    return;
                }
            }
            */

            Map<String, Serializable> options = new HashMap<>();
            options.put(Constants.Option.POSSIBLE_ATTACKERS, (Serializable) possibleAttackers);
            if (!possibleAttackers.isEmpty()) {
                options.put(Constants.Option.SPECIAL_BUTTON, "All attack");
            }

            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireSelectEvent(playerId, "Select attackers", options);
            }
            waitForResponse(game);
            if (response.getString() != null
                    && response.getString().equals("special")) { // All attack
                setStoredBookmark(game.bookmarkState());
                UUID attackedDefender = null;
                if (game.getCombat().getDefenders().size() > 1) {
                    attackedDefender = selectDefenderForAllAttack(game.getCombat().getDefenders(), game);
                } else if (game.getCombat().getDefenders().size() == 1) {
                    attackedDefender = game.getCombat().getDefenders().iterator().next();
                }
                for (Permanent attacker : game.getBattlefield().getAllActivePermanents(filterCreatureForCombat, getId(), game)) {
                    if (game.getContinuousEffects().checkIfThereArePayCostToAttackBlockEffects(
                            GameEvent.getEvent(GameEvent.EventType.DECLARE_ATTACKER,
                                    attackedDefender, attacker.getId(), attacker.getControllerId()), game)) {
                        continue;
                    }
                    // if attacker needs a specific defender to attack so select that one instead
                    if (game.getCombat().getCreaturesForcedToAttack().containsKey(attacker.getId())) {
                        Set<UUID> possibleDefenders = game.getCombat().getCreaturesForcedToAttack().get(attacker.getId());
                        if (!possibleDefenders.isEmpty() && !possibleDefenders.contains(attackedDefender)) {
                            declareAttacker(attacker.getId(), possibleDefenders.iterator().next(), game, false);
                            continue;
                        }
                    }
                    // attack selected default defender
                    declareAttacker(attacker.getId(), attackedDefender, game, false);
                }
            } else if (response.getInteger() != null) { // F-Key
                if (checkIfAttackersValid(game)) {
                    return;
                }
            } else if (response.getBoolean() != null) { // ok button
                if (checkIfAttackersValid(game)) {
                    return;
                }
            } else if (response.getUUID() != null) {
                Permanent attacker = game.getPermanent(response.getUUID());
                if (attacker != null) {
                    if (filterCreatureForCombat.match(attacker, null, playerId, game)) {
                        selectDefender(game.getCombat().getDefenders(), attacker.getId(), game);
                    } else if (filterAttack.match(attacker, null, playerId, game) && game.getStack().isEmpty()) {
                        removeAttackerIfPossible(game, attacker);
                    }
                }
            }
        }
    }

    private boolean checkIfAttackersValid(Game game) {
        if (!game.getCombat().getCreaturesForcedToAttack().isEmpty()) {
            if (!game.getCombat().getAttackers().containsAll(game.getCombat().getCreaturesForcedToAttack().keySet())) {
                int forcedAttackers = 0;
                StringBuilder sb = new StringBuilder();
                for (UUID creatureId : game.getCombat().getCreaturesForcedToAttack().keySet()) {
                    boolean validForcedAttacker = false;
                    if (game.getCombat().getAttackers().contains(creatureId)) {
                        Set<UUID> possibleDefender = game.getCombat().getCreaturesForcedToAttack().get(creatureId);
                        if (possibleDefender.isEmpty()
                                || possibleDefender.contains(game.getCombat().getDefenderId(creatureId))) {
                            validForcedAttacker = true;
                        }
                    }
                    if (validForcedAttacker) {
                        forcedAttackers++;
                    } else {
                        Permanent creature = game.getPermanent(creatureId);
                        if (creature != null) {
                            sb.append(creature.getIdName()).append(' ');
                        }
                    }

                }
                if (game.getCombat().getMaxAttackers() > forcedAttackers) {
                    int requireToAttack = Math.min(game.getCombat().getMaxAttackers() - forcedAttackers, game.getCombat().getCreaturesForcedToAttack().size() - forcedAttackers);
                    String message = (requireToAttack == 1 ? " more attacker that is " : " more attackers that are ")
                            + "forced to attack.\nCreature"
                            + (requireToAttack == 1 ? "" : "s") + " forced to attack: ";
                    game.informPlayer(this, sb.insert(0, message)
                            .insert(0, requireToAttack)
                            .insert(0, "You have to attack with ").toString());
                    return false;
                }
            }
        }
        // check if enough attackers are declared
        // check if players have to be attacked
        Set<UUID> playersToAttackIfAble = new HashSet<>();
        for (Map.Entry<RequirementEffect, Set<Ability>> entry : game.getContinuousEffects().getApplicableRequirementEffects(null, true, game).entrySet()) {
            RequirementEffect effect = entry.getKey();
            for (Ability ability : entry.getValue()) {
                UUID playerToAttack = effect.playerMustBeAttackedIfAble(ability, game);
                if (playerToAttack != null) {
                    playersToAttackIfAble.add(playerToAttack);
                }
            }
        }
        if (!playersToAttackIfAble.isEmpty()) {
            Set<UUID> checkPlayersToAttackIfAble = new HashSet<>(playersToAttackIfAble);
            for (CombatGroup combatGroup : game.getCombat().getGroups()) {
                checkPlayersToAttackIfAble.remove(combatGroup.getDefendingPlayerId());
            }

            for (UUID forcedToAttackId : checkPlayersToAttackIfAble) {
                Player forcedToAttack = game.getPlayer(forcedToAttackId);

                for (Permanent attacker : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, getId(), game)) {

                    if (game.getContinuousEffects().checkIfThereArePayCostToAttackBlockEffects(
                            GameEvent.getEvent(GameEvent.EventType.DECLARE_ATTACKER,
                                    forcedToAttackId, attacker.getId(), attacker.getControllerId()), game)) {
                        continue;
                    }
                    // if attacker needs a specific defender to attack so select that one instead
                    if (game.getCombat().getCreaturesForcedToAttack().containsKey(attacker.getId())) {
                        Set<UUID> possibleDefenders = game.getCombat().getCreaturesForcedToAttack().get(attacker.getId());
                        if (!possibleDefenders.isEmpty() && !possibleDefenders.contains(forcedToAttackId)) {
                            continue;
                        }
                    }
                    UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(attacker.getId(), game);
                    if (playersToAttackIfAble.contains(defendingPlayerId)) {
                        // already attacks other player taht has to be attacked
                        continue;
                    }
                    if (defendingPlayerId != null || attacker.canAttackInPrinciple(forcedToAttackId, game)) {
                        game.informPlayer(this, "You are forced to attack " + forcedToAttack.getName() + " or a controlled planeswalker e.g. with " + attacker.getIdName() + ".");
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void removeAttackerIfPossible(Game game, Permanent attacker) {
        for (Map.Entry entry : game.getContinuousEffects().getApplicableRequirementEffects(attacker, false, game).entrySet()) {
            RequirementEffect effect = (RequirementEffect) entry.getKey();
            if (effect.mustAttack(game)) {
                if (game.getCombat().getMaxAttackers() >= game.getCombat().getCreaturesForcedToAttack().size()
                        && game.getCombat().getDefenders().size() == 1) {
                    return; // we can't change creatures forced to attack if only one possible defender exists and all forced creatures can attack
                }
            }
        }
        game.getCombat().removeAttacker(attacker.getId(), game);
    }

    /**
     * Selects a defender for an attacker and adds the attacker to combat
     *
     * @param defenders  - list of possible defender
     * @param attackerId - UUID of attacker
     * @param game
     * @return
     */
    protected boolean selectDefender(Set<UUID> defenders, UUID attackerId, Game game) {
        boolean forcedToAttack = false;
        Set<UUID> possibleDefender = game.getCombat().getCreaturesForcedToAttack().get(attackerId);
        if (possibleDefender != null) {
            forcedToAttack = true;
        }
        if (possibleDefender == null
                || possibleDefender.isEmpty()) {
            possibleDefender = defenders;
        }
        if (possibleDefender.size() == 1) {
            declareAttacker(attackerId, possibleDefender.iterator().next(), game, true);
            return true;
        } else {
            TargetDefender target = new TargetDefender(possibleDefender, attackerId);
            target.setNotTarget(true); // player or planswalker hexproof does not prevent attacking a player
            if (forcedToAttack) {
                StringBuilder sb = new StringBuilder(target.getTargetName());
                Permanent attacker = game.getPermanent(attackerId);
                if (attacker != null) {
                    sb.append(" (").append(attacker.getName()).append(')');
                    target.setTargetName(sb.toString());
                }
            }
            if (chooseTarget(Outcome.Damage, target, null, game)) {
                UUID defenderId = response.getUUID();
                for (Player player : game.getPlayers().values()) {
                    if (player.getId().equals(response.getUUID())) {
                        defenderId = player.getId(); // get the correct player object
                        break;
                    }
                }
                declareAttacker(attackerId, defenderId, game, true);
                return true;
            }
        }
        return false;
    }

    protected UUID selectDefenderForAllAttack(Set<UUID> defenders, Game game) {
        TargetDefender target = new TargetDefender(defenders, null);
        target.setNotTarget(true); // player or planswalker hexproof does not prevent attacking a player
        if (chooseTarget(Outcome.Damage, target, null, game)) {
            return response.getUUID();
        }
        return null;
    }

    @Override
    public void selectBlockers(Game game, UUID defendingPlayerId) {
        updateGameStatePriority("selectBlockers", game);
        FilterCreatureForCombatBlock filter = filterCreatureForCombatBlock.copy();
        filter.add(new ControllerIdPredicate(defendingPlayerId));

        // stop skip on any/zero permanents available
        int possibleBlockersCount = game.getBattlefield().count(filter, null, playerId, game);
        boolean canStopOnAny = possibleBlockersCount != 0 && getControllingPlayersUserData(game).getUserSkipPrioritySteps().isStopOnDeclareBlockersWithAnyPermanents();
        boolean canStopOnZero = possibleBlockersCount == 0 && getControllingPlayersUserData(game).getUserSkipPrioritySteps().isStopOnDeclareBlockersWithZeroPermanents();
        if (!canStopOnAny && !canStopOnZero) {
            return;
        }

        while (!abort) {
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                Map<String, Serializable> options = new HashMap<>();
                List<UUID> possibleBlockers = game.getBattlefield().getActivePermanents(filter, playerId, game).stream()
                        .map(p -> p.getId())
                        .collect(Collectors.toList());
                options.put(Constants.Option.POSSIBLE_BLOCKERS, (Serializable) possibleBlockers);
                game.fireSelectEvent(playerId, "Select blockers", options);
            }
            waitForResponse(game);
            if (response.getBoolean() != null) {
                return;
            } else if (response.getInteger() != null) {
                return;
            } else if (response.getUUID() != null) {
                Permanent blocker = game.getPermanent(response.getUUID());
                if (blocker != null) {
                    boolean removeBlocker = false;
                    // does not block yet and can block or can block more attackers
                    if (filter.match(blocker, null, playerId, game)) {
                        selectCombatGroup(defendingPlayerId, blocker.getId(), game);
                    } else if (filterBlock.match(blocker, null, playerId, game)
                            && game.getStack().isEmpty()) {
                        removeBlocker = true;
                    }

                    if (removeBlocker) {
                        game.getCombat().removeBlocker(blocker.getId(), game);
                    }
                }
            }
        }
    }

    @Override
    public UUID chooseAttackerOrder(List<Permanent> attackers, Game game) {
        updateGameStatePriority("chooseAttackerOrder", game);
        while (!abort) {
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireSelectTargetEvent(playerId, "Pick attacker", attackers, true);
            }
            waitForResponse(game);
            if (response.getUUID() != null) {
                for (Permanent perm : attackers) {
                    if (perm.getId().equals(response.getUUID())) {
                        return perm.getId();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public UUID chooseBlockerOrder(List<Permanent> blockers, CombatGroup combatGroup, List<UUID> blockerOrder, Game game) {
        updateGameStatePriority("chooseBlockerOrder", game);
        while (!abort) {
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireSelectTargetEvent(playerId, "Pick blocker", blockers, true);
            }
            waitForResponse(game);
            if (response.getUUID() != null) {
                for (Permanent perm : blockers) {
                    if (perm.getId().equals(response.getUUID())) {
                        return perm.getId();
                    }
                }
            }
        }
        return null;
    }

    protected void selectCombatGroup(UUID defenderId, UUID blockerId, Game game) {
        updateGameStatePriority("selectCombatGroup", game);
        TargetAttackingCreature target = new TargetAttackingCreature();
        prepareForResponse(game);
        if (!isExecutingMacro()) {
            // possible attackers to block
            Set<UUID> attackers = target.possibleTargets(null, playerId, game);
            Permanent blocker = game.getPermanent(blockerId);
            Set<UUID> possibleTargets = new HashSet<>();
            for (UUID attackerId : attackers) {
                CombatGroup group = game.getCombat().findGroup(attackerId);
                if (group != null && blocker != null && group.canBlock(blocker, game)) {
                    possibleTargets.add(attackerId);
                }
            }

            game.fireSelectTargetEvent(playerId, new MessageToClient("Select attacker to block", getRelatedObjectName(blockerId, game)),
                    possibleTargets, false, getOptions(target, null));
        }

        waitForResponse(game);
        if (response.getBoolean() != null) {
            // do nothing
        } else if (response.getUUID() != null) {
            CombatGroup group = game.getCombat().findGroup(response.getUUID());
            if (group != null) {
                // check if already blocked, if not add
                if (!group.getBlockers().contains(blockerId)) {
                    declareBlocker(defenderId, blockerId, response.getUUID(), game);
                } else { // else remove from block
                    game.getCombat().removeBlockerGromGroup(blockerId, group, game);
                }
            }
        }
    }

    @Override
    public void assignDamage(int damage, List<UUID> targets, String singleTargetName, UUID sourceId, Game game) {
        updateGameStatePriority("assignDamage", game);
        int remainingDamage = damage;
        while (remainingDamage > 0 && canRespond()) {
            Target target = new TargetAnyTarget();
            target.setNotTarget(true);
            if (singleTargetName != null) {
                target.setTargetName(singleTargetName);
            }
            choose(Outcome.Damage, target, sourceId, game);
            if (targets.isEmpty() || targets.contains(target.getFirstTarget())) {
                int damageAmount = getAmount(0, remainingDamage, "Select amount", game);
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    permanent.damage(damageAmount, sourceId, game, false, true);
                    remainingDamage -= damageAmount;
                } else {
                    Player player = game.getPlayer(target.getFirstTarget());
                    if (player != null) {
                        player.damage(damageAmount, sourceId, game, false, true);
                        remainingDamage -= damageAmount;
                    }
                }
            }
        }
    }

    @Override
    public int getAmount(int min, int max, String message, Game game) {
        updateGameStatePriority("getAmount", game);
        do {
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireGetAmountEvent(playerId, message, min, max);
            }
            waitForResponse(game);
        } while (response.getInteger() == null && !abort);
        if (response != null && response.getInteger() != null) {
            return response.getInteger();
        } else {
            return 0;
        }
    }

    @Override
    public void sideboard(Match match, Deck deck) {
        match.fireSideboardEvent(playerId, deck);
    }

    @Override
    public void construct(Tournament tournament, Deck deck) {
        tournament.fireConstructEvent(playerId);
    }

    @Override
    public void pickCard(List<Card> cards, Deck deck, Draft draft) {
        draft.firePickCardEvent(playerId);
    }

    protected void specialAction(Game game) {
        Map<UUID, SpecialAction> specialActions = game.getState().getSpecialActions().getControlledBy(playerId, false);
        if (!specialActions.isEmpty()) {
            updateGameStatePriority("specialAction", game);
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireGetChoiceEvent(playerId, name, null, new ArrayList<>(specialActions.values()));
            }
            waitForResponse(game);
            if (response.getUUID() != null) {
                if (specialActions.containsKey(response.getUUID())) {
                    activateAbility(specialActions.get(response.getUUID()), game);
                }
            }
        }
    }

    protected void specialManaAction(ManaCost unpaid, Game game) {
        Map<UUID, SpecialAction> specialActions = game.getState().getSpecialActions().getControlledBy(playerId, true);
        if (!specialActions.isEmpty()) {
            updateGameStatePriority("specialAction", game);
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireGetChoiceEvent(playerId, name, null, new ArrayList<>(specialActions.values()));
            }
            waitForResponse(game);
            if (response.getUUID() != null) {
                if (specialActions.containsKey(response.getUUID())) {
                    SpecialAction specialAction = specialActions.get(response.getUUID());
                    if (specialAction != null) {
                        specialAction.setUnpaidMana(unpaid);
                        activateAbility(specialActions.get(response.getUUID()), game);
                    }
                }
            }
        }
    }

    @Override
    public boolean activateAbility(ActivatedAbility ability, Game game) {
        getManaPool().setStock(); // needed for the "mana already in the pool has to be used manually" option
        return super.activateAbility(ability, game);
    }

    protected void activateAbility(LinkedHashMap<UUID, ? extends ActivatedAbility> abilities, MageObject object, Game game) {
        updateGameStatePriority("activateAbility", game);
        if (abilities.size() == 1
                && suppressAbilityPicker(abilities.values().iterator().next(), game)) {
            ActivatedAbility ability = abilities.values().iterator().next();
            if (!ability.getTargets().isEmpty()
                    || !(ability.getCosts().size() == 1
                    && ability.getCosts().get(0) instanceof SacrificeSourceCost)
                    || !(ability.getCosts().size() == 2
                    && ability.getCosts().get(0) instanceof TapSourceCost
                    && ability.getCosts().get(0) instanceof SacrificeSourceCost)) {
                activateAbility(ability, game);
                return;
            }
        }
        if (userData.isUseFirstManaAbility() && object instanceof Permanent && object.isLand()) {
            ActivatedAbility ability = abilities.values().iterator().next();
            if (ability instanceof ActivatedManaAbilityImpl) {
                activateAbility(ability, game);
                return;
            }
        }

        prepareForResponse(game);
        if (!isExecutingMacro()) {
            game.fireGetChoiceEvent(playerId, name, object, new ArrayList<>(abilities.values()));
        }

        waitForResponse(game);
        if (response.getUUID() != null && isInGame()) {
            if (abilities.containsKey(response.getUUID())) {
                activateAbility(abilities.get(response.getUUID()), game);
            }
        }
    }

    private boolean suppressAbilityPicker(ActivatedAbility ability, Game game) {
        if (getControllingPlayersUserData(game).isShowAbilityPickerForced()) {
            if (ability instanceof PlayLandAbility) {
                return true;
            }
            if (!ability.getSourceId().equals(getCastSourceIdWithAlternateMana())
                    && ability.getManaCostsToPay().convertedManaCost() > 0) {
                return true;
            }
            return ability instanceof ActivatedManaAbilityImpl;
        }
        return true;
    }

    @Override
    public SpellAbility chooseSpellAbilityForCast(SpellAbility ability, Game game, boolean noMana) {
        switch (ability.getSpellAbilityType()) {
            case SPLIT:
            case SPLIT_FUSED:
            case SPLIT_AFTERMATH:
                MageObject object = game.getObject(ability.getSourceId());
                if (object != null) {
                    LinkedHashMap<UUID, ActivatedAbility> useableAbilities = getSpellAbilities(object, game.getState().getZone(object.getId()), game);
                    if (useableAbilities != null
                            && useableAbilities.size() == 1) {
                        return (SpellAbility) useableAbilities.values().iterator().next();
                    } else if (useableAbilities != null
                            && !useableAbilities.isEmpty()) {
                        prepareForResponse(game);
                        if (!isExecutingMacro()) {
                            game.fireGetChoiceEvent(playerId, name, object, new ArrayList<>(useableAbilities.values()));
                        }
                        waitForResponse(game);
                        if (response.getUUID() != null) {
                            if (useableAbilities.containsKey(response.getUUID())) {
                                return (SpellAbility) useableAbilities.get(response.getUUID());
                            }
                        }
                    }
                }
                return null;
            default:
                return ability;
        }
    }

    @Override
    public Mode chooseMode(Modes modes, Ability source, Game game) {
        // choose mode to activate
        updateGameStatePriority("chooseMode", game);
        if (modes.size() > 1) {
            MageObject obj = game.getObject(source.getSourceId());
            Map<UUID, String> modeMap = new LinkedHashMap<>();
            AvailableModes:
            for (Mode mode : modes.getAvailableModes(source, game)) {
                int timesSelected = 0;
                for (UUID selectedModeId : modes.getSelectedModes()) {
                    Mode selectedMode = modes.get(selectedModeId);
                    if (mode.getId().equals(selectedMode.getId())) {
                        if (modes.isEachModeMoreThanOnce()) {
                            timesSelected++;
                        } else {
                            continue AvailableModes;
                        }
                    }
                }
                if (mode.getTargets().canChoose(source.getSourceId(), source.getControllerId(), game)) { // and needed targets have to be available
                    String modeText = mode.getEffects().getText(mode);
                    if (obj != null) {
                        modeText = modeText.replace("{source}", obj.getName()).replace("{this}", obj.getName());
                    }
                    if (modes.isEachModeMoreThanOnce()) {
                        if (timesSelected > 0) {
                            modeText = "(selected " + timesSelected + "x) " + modeText;
                        }
                    }
                    modeMap.put(mode.getId(), modeText);
                }
            }
            if (!modeMap.isEmpty()) {
                boolean done = false;
                while (!done) {
                    prepareForResponse(game);
                    if (!isExecutingMacro()) {
                        game.fireGetModeEvent(playerId, "Choose Mode", modeMap);
                    }
                    waitForResponse(game);
                    if (response.getUUID() != null) {
                        for (Mode mode : modes.getAvailableModes(source, game)) {
                            if (mode.getId().equals(response.getUUID())) {
                                return mode;
                            }
                        }
                    }
                    if (source.getAbilityType() != AbilityType.TRIGGERED) {
                        done = true;
                    }
                    if (!canRespond()) {
                        return null;
                    }
                }
            }
            return null;
        }

        return modes.getMode();
    }

    @Override
    public boolean choosePile(Outcome outcome, String message, List<? extends Card> pile1, List<? extends Card> pile2, Game game) {
        updateGameStatePriority("choosePile", game);
        do {
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireChoosePileEvent(playerId, message, pile1, pile2);
            }
            waitForResponse(game);
        } while (response.getBoolean() == null && !abort);
        if (!abort) {
            return response.getBoolean();
        }
        return false;
    }

    @Override
    public void setResponseString(String responseString) {
        waitResponseOpen();
        synchronized (response) {
            response.setString(responseString);
            response.notifyAll();
            logger.debug("Got response string from player: " + getId());
        }
    }

    @Override
    public void setResponseManaType(UUID manaTypePlayerId, ManaType manaType) {
        waitResponseOpen();
        synchronized (response) {
            response.setManaType(manaType);
            response.setResponseManaTypePlayerId(manaTypePlayerId);
            response.notifyAll();
            logger.debug("Got response mana type from player: " + getId());
        }
    }

    @Override
    public void setResponseUUID(UUID responseUUID) {
        waitResponseOpen();
        synchronized (response) {
            response.setUUID(responseUUID);
            response.notifyAll();
            logger.debug("Got response UUID from player: " + getId());
        }
    }

    @Override
    public void setResponseBoolean(Boolean responseBoolean) {
        waitResponseOpen();
        synchronized (response) {
            response.setBoolean(responseBoolean);
            response.notifyAll();
            logger.debug("Got response boolean from player: " + getId());
        }
    }

    @Override
    public void setResponseInteger(Integer responseInteger) {
        waitResponseOpen();
        synchronized (response) {
            response.setInteger(responseInteger);
            response.notifyAll();
            logger.debug("Got response integer from player: " + getId());
        }
    }

    @Override
    public void abort() {
        abort = true;
        waitResponseOpen();
        synchronized (response) {
            response.notifyAll();
            logger.debug("Got cancel action from player: " + getId());
        }
    }

    @Override
    public void signalPlayerConcede() {
        //waitResponseOpen(); //concede is direct event, no need to wait it
        synchronized (response) {
            response.setResponseConcedeCheck();
            response.notifyAll();
            logger.debug("Set check concede for waiting player: " + getId());
        }
    }

    @Override
    public void skip() {
        // waitResponseOpen(); //skip is direct event, no need to wait it
        synchronized (response) {
            response.setInteger(0);
            response.notifyAll();
            logger.debug("Got skip action from player: " + getId());
        }
    }

    @Override
    public HumanPlayer copy() {
        return new HumanPlayer(this);
    }

    protected void updateGameStatePriority(String methodName, Game game) {
        if (game.getState().getPriorityPlayerId() != null) { // don't do it if priority was set to null before (e.g. discard in cleanaup)
            if (getId() == null) {
                logger.fatal("Player with no ID: " + name);
                this.quit(game);
                return;
            }
            logger.debug("Setting game priority to " + getId() + " [" + methodName + ']');
            game.getState().setPriorityPlayerId(getId());
        }
    }

    @Override
    public void sendPlayerAction(PlayerAction playerAction, Game game, Object data) {
        switch (playerAction) {
            case RESET_AUTO_SELECT_REPLACEMENT_EFFECTS:
                autoSelectReplacementEffects.clear();
                break;
            case TRIGGER_AUTO_ORDER_ABILITY_FIRST:
            case TRIGGER_AUTO_ORDER_ABILITY_LAST:
            case TRIGGER_AUTO_ORDER_NAME_FIRST:
            case TRIGGER_AUTO_ORDER_NAME_LAST:
            case TRIGGER_AUTO_ORDER_RESET_ALL:
                setTriggerAutoOrder(playerAction, game, data);
                break;
            case REQUEST_AUTO_ANSWER_ID_NO:
            case REQUEST_AUTO_ANSWER_ID_YES:
            case REQUEST_AUTO_ANSWER_TEXT_NO:
            case REQUEST_AUTO_ANSWER_TEXT_YES:
            case REQUEST_AUTO_ANSWER_RESET_ALL:
                setRequestAutoAnswer(playerAction, game, data);
                break;
            case HOLD_PRIORITY:
                holdingPriority = true;
                break;
            case UNHOLD_PRIORITY:
                holdingPriority = false;
                break;
            case TOGGLE_RECORD_MACRO:
                if (recordingMacro) {
                    logger.debug("Finished Recording Macro");
                    activatingMacro = true;
                    recordingMacro = false;
                    actionIterations = announceRepetitions(game);
                    try {
                        synchronized (actionQueue) {
                            actionQueue.wait();
                        }
                    } catch (InterruptedException ex) {
                    } finally {
                        activatingMacro = false;
                    }
                } else {
                    logger.debug("Starting Recording Macro");
                    resetPlayerPassedActions();
                    recordingMacro = true;
                    actionIterations = 0;
                    actionQueueSaved.clear();
                    actionQueue.clear();
                }
                break;
            case PASS_PRIORITY_UNTIL_STACK_RESOLVED:
                // stop recording only, real stack processing in PlayerImpl
                if (recordingMacro) {
                    logger.debug("Adding a resolveStack");
                    PlayerResponse tResponse = new PlayerResponse();
                    tResponse.setString("resolveStack");
                    actionQueueSaved.add(tResponse);
                }
                super.sendPlayerAction(playerAction, game, data);
                break;
            default:
                super.sendPlayerAction(playerAction, game, data);
                break;
        }
    }

    private void setRequestAutoAnswer(PlayerAction playerAction, Game game, Object data) {
        if (playerAction == REQUEST_AUTO_ANSWER_RESET_ALL) {
            requestAutoAnswerId.clear();
            requestAutoAnswerText.clear();
            return;
        }
        if (data instanceof String) {
            String key = (String) data;
            switch (playerAction) {
                case REQUEST_AUTO_ANSWER_ID_NO:
                    requestAutoAnswerId.put(key, false);
                    break;
                case REQUEST_AUTO_ANSWER_TEXT_NO:
                    requestAutoAnswerText.put(key, false);
                    break;
                case REQUEST_AUTO_ANSWER_ID_YES:
                    requestAutoAnswerId.put(key, true);
                    break;
                case REQUEST_AUTO_ANSWER_TEXT_YES:
                    requestAutoAnswerText.put(key, true);
                    break;
            }
        }
    }

    private void setTriggerAutoOrder(PlayerAction playerAction, Game game, Object data) {
        if (playerAction == TRIGGER_AUTO_ORDER_RESET_ALL) {
            triggerAutoOrderAbilityFirst.clear();
            triggerAutoOrderAbilityLast.clear();
            triggerAutoOrderNameFirst.clear();
            triggerAutoOrderNameLast.clear();
            return;
        }
        if (data instanceof UUID) {
            UUID abilityId = (UUID) data;
            UUID originalId = null;
            for (TriggeredAbility ability : game.getState().getTriggered(getId())) {
                if (ability.getId().equals(abilityId)) {
                    originalId = ability.getOriginalId();
                    break;
                }
            }
            if (originalId != null) {
                switch (playerAction) {
                    case TRIGGER_AUTO_ORDER_ABILITY_FIRST:
                        triggerAutoOrderAbilityFirst.add(originalId);
                        break;
                    case TRIGGER_AUTO_ORDER_ABILITY_LAST:
                        triggerAutoOrderAbilityFirst.add(originalId);
                        break;
                }
            }
        } else if (data instanceof String) {
            String abilityName = (String) data;
            switch (playerAction) {
                case TRIGGER_AUTO_ORDER_NAME_FIRST:
                    triggerAutoOrderNameFirst.add(abilityName);
                    break;
                case TRIGGER_AUTO_ORDER_NAME_LAST:
                    triggerAutoOrderNameLast.add(abilityName);
                    break;
            }
        }
    }

    protected boolean passWithManaPoolCheck(Game game) {
        if (userData.confirmEmptyManaPool()
                && game.getStack().isEmpty() && getManaPool().count() > 0) {
            String activePlayerText;
            if (game.isActivePlayer(playerId)) {
                activePlayerText = "Your turn";
            } else {
                activePlayerText = game.getPlayer(game.getActivePlayerId()).getName() + "'s turn";
            }
            String priorityPlayerText = "";
            if (!isGameUnderControl()) {
                priorityPlayerText = " / priority " + game.getPlayer(game.getPriorityPlayerId()).getName();
            }
            if (!chooseUse(Outcome.Detriment, GameLog.getPlayerConfirmColoredText("You still have mana in your mana pool. Pass regardless?")
                    + GameLog.getSmallSecondLineText(activePlayerText + " / " + game.getStep().getType().toString() + priorityPlayerText), null, game)) {
                sendPlayerAction(PlayerAction.PASS_PRIORITY_CANCEL_ALL_ACTIONS, game, null);
                return false;
            }
        }
        pass(game);
        return true;
    }

    @Override
    public String getHistory() {
        return "no available";
    }
}
