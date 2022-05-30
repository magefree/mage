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
import mage.abilities.mana.ManaAbility;
import mage.cards.*;
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
import mage.game.events.DeclareAttackerEvent;
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
import mage.util.CardUtil;
import mage.util.GameLog;
import mage.util.ManaUtil;
import mage.util.MessageToClient;
import org.apache.log4j.Logger;

import java.awt.*;
import java.io.Serializable;
import java.util.List;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import static mage.constants.PlayerAction.REQUEST_AUTO_ANSWER_RESET_ALL;
import static mage.constants.PlayerAction.TRIGGER_AUTO_ORDER_RESET_ALL;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class HumanPlayer extends PlayerImpl {

    private static final boolean ALLOW_USERS_TO_PUT_NON_PLAYABLE_SPELLS_ON_STACK_WORKAROUND = false; // warning, see workaround's info on usage

    private transient Boolean responseOpenedForAnswer = false; // can't get response until prepared target (e.g. until send all fire events to all players)
    private final transient PlayerResponse response = new PlayerResponse();

    protected static FilterCreatureForCombatBlock filterCreatureForCombatBlock = new FilterCreatureForCombatBlock();
    protected static FilterCreatureForCombat filterCreatureForCombat = new FilterCreatureForCombat();
    protected static FilterAttackingCreature filterAttack = new FilterAttackingCreature();
    protected static FilterBlockingCreature filterBlock = new FilterBlockingCreature();
    protected final Choice replacementEffectChoice;
    private static final Logger logger = Logger.getLogger(HumanPlayer.class);

    protected HashSet<String> autoSelectReplacementEffects = new LinkedHashSet<>(); // must be sorted
    protected ManaCost currentlyUnpaidMana;

    protected Set<UUID> triggerAutoOrderAbilityFirst = new HashSet<>();
    protected Set<UUID> triggerAutoOrderAbilityLast = new HashSet<>();
    protected Set<String> triggerAutoOrderNameFirst = new HashSet<>();
    protected Set<String> triggerAutoOrderNameLast = new HashSet<>();

    // auto-answer
    protected Map<String, Boolean> requestAutoAnswerId = new HashMap<>();
    protected Map<String, Boolean> requestAutoAnswerText = new HashMap<>();

    protected boolean holdingPriority;

    protected ConcurrentLinkedQueue<PlayerResponse> actionQueue = new ConcurrentLinkedQueue<>();
    protected ConcurrentLinkedQueue<PlayerResponse> actionQueueSaved = new ConcurrentLinkedQueue<>();
    protected int actionIterations = 0;
    protected boolean recordingMacro = false;
    protected boolean macroTriggeredSelectionFlag;
    protected boolean activatingMacro = false;

    public HumanPlayer(String name, RangeOfInfluence range, int skill) {
        super(name, range);
        human = true;

        replacementEffectChoice = new ChoiceImpl(true);
        replacementEffectChoice.setMessage("Choose replacement effect to resolve first");
        replacementEffectChoice.setSpecial(
                true,
                false,
                "Remember answer",
                "Choose same answer next time (you can reset saved answers by battlefield popup menu)"
        );
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
                // game frozen -- need to report about error and continue to execute
                String s = "Game frozen in waitResponseOpen for user " + getName() + " (connection problem)";
                logger.warn(s);
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
            actionQueue = new ConcurrentLinkedQueue<>(actionQueueSaved);
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

            // game recived immediately response on OTHER player concede -- need to process end game and continue to wait
            if (response.getResponseConcedeCheck()) {
                ((GameImpl) game).checkConcede();
                if (game.hasEnded()) {
                    return;
                }

                if (canRespond()) {
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
        if (gameInCheckPlayableState(game)) {
            return true;
        }

        while (canRespond()) {
            int nextHandSize = game.mulliganDownTo(playerId);
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

            updateGameStatePriority("chooseMulligan", game);
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireAskPlayerEvent(playerId, new MessageToClient(message), null, options);
            }
            waitForResponse(game);

            if (response.getBoolean() != null) {
                return response.getBoolean();
            }
        }

        return false;
    }

    @Override
    public boolean chooseUse(Outcome outcome, String message, Ability source, Game game) {
        return this.chooseUse(outcome, message, null, "Yes", "No", source, game);
    }

    @Override
    public boolean chooseUse(Outcome outcome, String message, String secondMessage, String trueText, String falseText, Ability source, Game game) {
        if (game.inCheckPlayableState()) {
            return true;
        }

        MessageToClient messageToClient = new MessageToClient(message, secondMessage);
        Map<String, Serializable> options = new HashMap<>(2);
        if (trueText != null) {
            options.put("UI.left.btn.text", trueText);
        }
        if (falseText != null) {
            options.put("UI.right.btn.text", falseText);
        }
        if (source != null) {
            //options.put(Constants.Option.ORIGINAL_ID, "")
        }


        // auto-answer
        Boolean answer = null;
        if (source != null) {
            // ability + text
            answer = requestAutoAnswerId.get(source.getOriginalId() + "#" + message);
        }
        if (answer == null) {
            // text
            answer = requestAutoAnswerText.get(message);
        }
        if (answer != null) {
            return answer;
        }

        while (canRespond()) {
            if (messageToClient.getSecondMessage() == null) {
                messageToClient.setSecondMessage(getRelatedObjectName(source, game));
            }

            updateGameStatePriority("chooseUse", game);
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireAskPlayerEvent(playerId, messageToClient, source, options);
            }
            waitForResponse(game);

            if (response.getBoolean() != null) {
                return response.getBoolean();
            }
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
        if (gameInCheckPlayableState(game, true)) { // ignore warning logs until double call for TAPPED_FOR_MANA will be fix
            return 0;
        }

        if (rEffects.size() <= 1) {
            return 0;
        }

        // use auto-choice:
        // - uses "always first" logic (choose in same order as user answer saves)
        // - search same effects by text (object name [id]: rules)
        // - autoSelectReplacementEffects is sorted set
        // - must get "same settings" option between cycle/response (user can change it by preferences)

        boolean useSameSettings = getControllingPlayersUserData(game).isUseSameSettingsForReplacementEffects();
        if (!autoSelectReplacementEffects.isEmpty()) {
            for (String autoText : autoSelectReplacementEffects) {
                int count = 0;
                // find effect with same saved text
                for (String effectKey : rEffects.keySet()) {
                    String currentText = prepareReplacementText(rEffects.get(effectKey), useSameSettings);
                    if (currentText.equals(autoText)) {
                        return count;
                    }
                    count++;
                }
            }
        }

        replacementEffectChoice.clearChoice();
        replacementEffectChoice.getChoices().clear();
        replacementEffectChoice.setKeyChoices(rEffects);

        // if same choices then select first
        int differentChoices = 0;
        String lastChoice = "";
        for (String value : replacementEffectChoice.getKeyChoices().values()) {
            if (!lastChoice.equalsIgnoreCase(value)) {
                lastChoice = value;
                differentChoices++;
            }
        }
        if (differentChoices <= 1) {
            return 0;
        }

        while (canRespond()) {
            updateGameStatePriority("chooseEffect", game);
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireChooseChoiceEvent(playerId, replacementEffectChoice);
            }
            waitForResponse(game);

            logger.debug("Choose effect: " + response.getString());
            if (response.getString() != null) {
                // save auto-choice (effect's text)
                if (response.getString().startsWith("#")) {
                    replacementEffectChoice.setChoiceByKey(response.getString().substring(1), true);
                    if (replacementEffectChoice.isChosen()) {
                        // put auto-choice to the end
                        useSameSettings = getControllingPlayersUserData(game).isUseSameSettingsForReplacementEffects();
                        String effectText = prepareReplacementText(replacementEffectChoice.getChoiceValue(), useSameSettings);
                        autoSelectReplacementEffects.remove(effectText);
                        autoSelectReplacementEffects.add(effectText);
                    }
                } else {
                    replacementEffectChoice.setChoiceByKey(response.getString(), false);
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

    private String prepareReplacementText(String fullText, boolean useSameSettingsForDifferentObjects) {
        // remove object id from the rules text (example: object [abd]: rules -> object : rules)
        if (useSameSettingsForDifferentObjects) {
            fullText = fullText.replaceAll("\\[\\w+\\]", "");
        }
        return fullText;
    }

    @Override
    public boolean choose(Outcome outcome, Choice choice, Game game) {
        if (gameInCheckPlayableState(game)) {
            return true;
        }

        if (Outcome.PutManaInPool == outcome) {
            if (currentlyUnpaidMana != null
                    && ManaUtil.tryToAutoSelectAManaColor(choice, currentlyUnpaidMana)) {
                return true;
            }
        }

        while (canRespond()) {
            updateGameStatePriority("choose(3)", game);
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
                // cancel
                return false;
            }
        }

        return false;
    }

    @Override
    public boolean choose(Outcome outcome, Target target, Ability source, Game game) {
        return choose(outcome, target, source, game, null);
    }

    @Override
    public boolean choose(Outcome outcome, Target target, Ability source, Game game, Map<String, Serializable> options) {
        if (gameInCheckPlayableState(game)) {
            return true;
        }

        // choose one or multiple permanents
        UUID abilityControllerId = playerId;
        if (target.getTargetController() != null
                && target.getAbilityController() != null) {
            abilityControllerId = target.getAbilityController();
        }
        if (options == null) {
            options = new HashMap<>();
        }

        while (canRespond()) {
            Set<UUID> targetIds = target.possibleTargets(abilityControllerId, source, game);
            if (targetIds == null || targetIds.isEmpty()) {
                return target.getTargets().size() >= target.getNumberOfTargets();
            }

            boolean required = target.isRequired(source != null ? source.getSourceId() : null, game);
            if (target.getTargets().size() >= target.getNumberOfTargets()) {
                required = false;
            }

            java.util.List<UUID> chosen = target.getTargets();
            options.put("chosen", (Serializable) chosen);

            updateGameStatePriority("choose(5)", game);
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireSelectTargetEvent(getId(), new MessageToClient(target.getMessage(), getRelatedObjectName(source, game)), targetIds, required, getOptions(target, options));
            }
            waitForResponse(game);

            UUID responseId = getFixedResponseUUID(game);
            if (responseId != null) {
                // selected some target

                // remove selected
                if (target.getTargets().contains(responseId)) {
                    target.remove(responseId);
                    continue;
                }

                if (!targetIds.contains(responseId)) {
                    continue;
                }

                if (target instanceof TargetPermanent) {
                    if (((TargetPermanent) target).canTarget(abilityControllerId, responseId, source, game, false)) {
                        target.add(responseId, game);
                        if (target.doneChoosing()) {
                            return true;
                        }
                    }
                } else {
                    MageObject object = game.getObject(source);
                    if (object instanceof Ability) {
                        if (target.canTarget(responseId, (Ability) object, game)) {
                            if (target.getTargets().contains(responseId)) { // if already included remove it with
                                target.remove(responseId);
                            } else {
                                target.addTarget(responseId, (Ability) object, game);
                                if (target.doneChoosing()) {
                                    return true;
                                }
                            }
                        }
                    } else if (target.canTarget(responseId, game)) {
                        if (target.getTargets().contains(responseId)) { // if already included remove it with
                            target.remove(responseId);
                        } else {
                            target.addTarget(responseId, null, game);
                            if (target.doneChoosing()) {
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
        if (gameInCheckPlayableState(game)) {
            return true;
        }

        // choose one or multiple targets
        UUID abilityControllerId = playerId;
        if (target.getAbilityController() != null) {
            abilityControllerId = target.getAbilityController();
        }

        Map<String, Serializable> options = new HashMap<>();

        while (canRespond()) {
            Set<UUID> possibleTargets = target.possibleTargets(abilityControllerId, source, game);
            boolean required = target.isRequired(source != null ? source.getSourceId() : null, game);
            if (possibleTargets.isEmpty()
                    || target.getTargets().size() >= target.getNumberOfTargets()) {
                required = false;
            }

            java.util.List<UUID> chosen = target.getTargets();
            options.put("chosen", (Serializable) chosen);

            updateGameStatePriority("chooseTarget", game);
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireSelectTargetEvent(getId(), new MessageToClient(target.getMessage(), getRelatedObjectName(source, game)),
                        possibleTargets, required, getOptions(target, options));
            }
            waitForResponse(game);

            UUID responseId = getFixedResponseUUID(game);
            if (responseId != null) {
                // remove selected
                if (target.getTargets().contains(responseId)) {
                    target.remove(responseId);
                    continue;
                }

                if (possibleTargets.contains(responseId)) {
                    if (target.canTarget(abilityControllerId, responseId, source, game)) {
                        target.addTarget(responseId, source, game);
                        if (target.doneChoosing()) {
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
        if (gameInCheckPlayableState(game)) {
            return true;
        }

        // choose one or multiple cards
        if (cards == null || cards.isEmpty()) {
            return false;
        }

        UUID abilityControllerId = playerId;
        if (target.getTargetController() != null
                && target.getAbilityController() != null) {
            abilityControllerId = target.getAbilityController();
        }

        while (canRespond()) {
            boolean required = target.isRequired(null, game);
            int count = cards.count(target.getFilter(), abilityControllerId, game);
            if (count == 0
                    || target.getTargets().size() >= target.getNumberOfTargets()) {
                required = false;
            }

            Map<String, Serializable> options = getOptions(target, null);
            java.util.List<UUID> chosen = target.getTargets();
            options.put("chosen", (Serializable) chosen);
            java.util.List<UUID> choosable = new ArrayList<>();
            for (UUID cardId : cards) {
                if (target.canTarget(abilityControllerId, cardId, null, cards, game)) {
                    choosable.add(cardId);
                }
            }
            if (!choosable.isEmpty()) {
                options.put("choosable", (Serializable) choosable);
            }

            // if nothing to choose then show dialog (user must see non selectable items and click on any of them)
            if (required && choosable.isEmpty()) {
                required = false;
            }

            updateGameStatePriority("choose(4)", game);
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireSelectTargetEvent(playerId, new MessageToClient(target.getMessage()), cards, required, options);
            }
            waitForResponse(game);

            UUID responseId = getFixedResponseUUID(game);
            if (responseId != null) {
                if (target.getTargets().contains(responseId)) { // if already included remove it with
                    target.remove(responseId);
                } else {
                    if (target.canTarget(abilityControllerId, responseId, null, cards, game)) {
                        target.add(responseId, game);
                        if (target.doneChoosing()) {
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

    // choose one or multiple target cards
    @Override
    public boolean chooseTarget(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
        if (gameInCheckPlayableState(game)) {
            return true;
        }

        if (cards == null || cards.isEmpty()) {
            return false;
        }

        UUID abilityControllerId = playerId;
        if (target.getTargetController() != null
                && target.getAbilityController() != null) {
            abilityControllerId = target.getAbilityController();
        }

        while (canRespond()) {
            boolean required = target.isRequiredExplicitlySet() ? target.isRequired() : target.isRequired(source);
            int count = cards.count(target.getFilter(), abilityControllerId, game);
            if (count == 0
                    || target.getTargets().size() >= target.getNumberOfTargets()) {
                required = false;
            }

            Map<String, Serializable> options = getOptions(target, null);
            java.util.List<UUID> chosen = target.getTargets();
            options.put("chosen", (Serializable) chosen);
            java.util.List<UUID> choosable = new ArrayList<>();
            for (UUID cardId : cards) {
                if (target.canTarget(abilityControllerId, cardId, source, cards, game)) {
                    choosable.add(cardId);
                }
            }
            if (!choosable.isEmpty()) {
                options.put("choosable", (Serializable) choosable);
            }

            // if nothing to choose then show dialog (user must see non selectable items and click on any of them)
            if (required && choosable.isEmpty()) {
                required = false;
            }

            updateGameStatePriority("chooseTarget(5)", game);
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireSelectTargetEvent(playerId, new MessageToClient(target.getMessage(), getRelatedObjectName(source, game)), cards, required, options);
            }
            waitForResponse(game);

            UUID responseId = getFixedResponseUUID(game);
            if (responseId != null) {
                if (target.getTargets().contains(responseId)) { // if already included remove it
                    target.remove(responseId);
                } else if (target.canTarget(abilityControllerId, responseId, source, cards, game)) {
                    target.addTarget(responseId, source, game);
                    if (target.doneChoosing()) {
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
        // human can choose or un-choose MULTIPLE targets at once
        if (gameInCheckPlayableState(game)) {
            return true;
        }

        UUID abilityControllerId = playerId;
        if (target.getAbilityController() != null) {
            abilityControllerId = target.getAbilityController();
        }

        int amountTotal = target.getAmountTotal(game, source);

        // Two steps logic:
        // 1. Select targets
        // 2. Distribute amount between selected targets

        // 1. Select targets
        while (canRespond()) {
            Set<UUID> possibleTargets = target.possibleTargets(abilityControllerId, source, game);
            boolean required = target.isRequired(source != null ? source.getSourceId() : null, game);
            if (possibleTargets.isEmpty()
                    || target.getSize() >= target.getNumberOfTargets()) {
                required = false;
            }

            // selected
            Map<String, Serializable> options = getOptions(target, null);
            java.util.List<UUID> chosen = target.getTargets();
            options.put("chosen", (Serializable) chosen);
            // selectable
            java.util.List<UUID> choosable = new ArrayList<>();
            for (UUID targetId : possibleTargets) {
                if (target.canTarget(abilityControllerId, targetId, source, game)) {
                    choosable.add(targetId);
                }
            }
            if (!choosable.isEmpty()) {
                options.put("choosable", (Serializable) choosable);
            }

            // if nothing to choose then show dialog (user must see non selectable items and click on any of them)
            if (required && choosable.isEmpty()) {
                required = false;
            }

            updateGameStatePriority("chooseTargetAmount", game);
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                // target amount uses for damage only, if you see another use case then message must be changed here and on getMultiAmount call
                String message = String.format("Select targets to distribute %d damage (selected %d)", amountTotal, target.getTargets().size());
                game.fireSelectTargetEvent(playerId, new MessageToClient(message, getRelatedObjectName(source, game)), possibleTargets, required, options);
            }
            waitForResponse(game);

            UUID responseId = getFixedResponseUUID(game);
            if (responseId != null) {
                if (target.contains(responseId)) {
                    // unselect
                    target.remove(responseId);
                } else if (possibleTargets.contains(responseId) && target.canTarget(abilityControllerId, responseId, source, game)) {
                    // select
                    target.addTarget(responseId, source, game);
                }
            } else if (!required) {
                break;
            }
        }

        // no targets to choose or disconnected
        List<UUID> targets = target.getTargets();
        if (targets.isEmpty()) {
            return false;
        }

        // 2. Distribute amount between selected targets

        // prepare targets list with p/t or life stats (cause that's dialog used for damage distribute)
        List<String> targetNames = new ArrayList<>();
        for (UUID targetId : targets) {
            MageObject targetObject = game.getObject(targetId);
            if (targetObject != null) {
                targetNames.add(String.format("%s, P/T: %d/%d",
                        targetObject.getIdName(),
                        targetObject.getPower().getValue(),
                        targetObject.getToughness().getValue()
                ));
            } else {
                Player player = game.getPlayer(targetId);
                if (player != null) {
                    targetNames.add(String.format("%s, life: %d", player.getName(), player.getLife()));
                } else {
                    targetNames.add("ERROR, unknown target " + targetId.toString());
                }
            }
        }

        // ask and assign new amount
        List<Integer> targetValues = getMultiAmount(outcome, targetNames, 1, amountTotal, MultiAmountType.DAMAGE, game);
        for (int i = 0; i < targetValues.size(); i++) {
            int newAmount = targetValues.get(i);
            UUID targetId = targets.get(i);
            if (newAmount <= 0) {
                // remove target
                target.remove(targetId);
            } else {
                // set amount
                target.setTargetAmount(targetId, newAmount, source, game);
            }
        }
        return true;
    }

    @Override
    public boolean priority(Game game) {
        passed = false;
        // TODO: fix problems with turn under out control:
        // TODO: change pass and other states like passedUntilStackResolved for controlling player, not for "this"
        // TODO: check and change all "this" to controling player calls, many bugs with hand, mana, skips - https://github.com/magefree/mage/issues/2088
        // TODO: use controlling player in all choose dialogs (and canRespond too, what's with take control of player AI?!)
        if (canRespond()) {
            HumanPlayer controllingPlayer = this;
            if (isGameUnderControl()) { // TODO: must be ! to get real controlling player
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
            if (isGameUnderControl()) { // TODO: remove to enable quick stop for controlling player
                // if was attacked - always stop BEFORE blocker step (to cast extra spells)
                if (game.getTurn().getStepType() == PhaseStep.DECLARE_ATTACKERS
                        && game.getCombat().getPlayerDefenders(game).contains(playerId)) {
                    FilterCreatureForCombatBlock filter = filterCreatureForCombatBlock.copy();
                    filter.add(new ControllerIdPredicate(playerId));
                    // stop skip on any/zero permanents available
                    int possibleBlockersCount = game.getBattlefield().count(filter, playerId, null, game);
                    boolean canStopOnAny = possibleBlockersCount != 0 && getControllingPlayersUserData(game).getUserSkipPrioritySteps().isStopOnDeclareBlockersWithAnyPermanents();
                    boolean canStopOnZero = possibleBlockersCount == 0 && getControllingPlayersUserData(game).getUserSkipPrioritySteps().isStopOnDeclareBlockersWithZeroPermanents();
                    quickStop = canStopOnAny || canStopOnZero;
                }
            }

            // SKIP - use the skip actions only if the player itself controls its turn
            if (!quickStop && isGameUnderControl()) { // TODO: remove to enable skips for controlling player

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
                holdingPriority = false;

                updateGameStatePriority("priority", game);
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
                    if (!activatingMacro && passWithManaPoolCheck(game)) {
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

            UUID responseId = getFixedResponseUUID(game);
            if (response.getString() != null
                    && response.getString().equals("special")) {
                activateSpecialAction(game, null);
            } else if (responseId != null) {
                boolean result = false;
                MageObject object = game.getObject(responseId);
                if (object != null) {
                    Zone zone = game.getState().getZone(object.getId());
                    if (zone != null) {
                        // look at card or try to cast/activate abilities
                        LinkedHashMap<UUID, ActivatedAbility> useableAbilities = new LinkedHashMap<>();

                        Player actingPlayer = null;
                        if (playerId.equals(game.getPriorityPlayerId())) {
                            actingPlayer = this;
                        } else if (getPlayersUnderYourControl().contains(game.getPriorityPlayerId())) {
                            actingPlayer = game.getPlayer(game.getPriorityPlayerId());
                        }
                        if (actingPlayer != null) {
                            useableAbilities = actingPlayer.getPlayableActivatedAbilities(object, zone, game);

                            // GUI: workaround to enable users to put spells on stack without real available mana
                            // (without highlighting, like it was in old versions before June 2020)
                            // Reason: some gain ability adds cost modification and other things to spells on stack only,
                            // e.g. xmage can't find playable ability before put that spell on stack (wtf example: Chief Engineer,
                            // see ConvokeTest)
                            // TODO: it's a BAD workaround  -- users can't see that card/ability is broken and will not report to us, AI can't play that ability too
                            // Enable it on massive broken cards/abilities only or for manual tests
                            if (ALLOW_USERS_TO_PUT_NON_PLAYABLE_SPELLS_ON_STACK_WORKAROUND) {
                                if (object instanceof Card) {
                                    for (Ability ability : ((Card) object).getAbilities(game)) {
                                        if (ability instanceof SpellAbility && ((SpellAbility) ability).canActivate(actingPlayer.getId(), game).canActivate()
                                                || ability instanceof PlayLandAbility) {
                                            useableAbilities.putIfAbsent(ability.getId(), (ActivatedAbility) ability);
                                        }
                                    }
                                }
                            }
                        }

                        if (object instanceof Card
                                && ((Card) object).isFaceDown(game)
                                && lookAtFaceDownCard((Card) object, game, useableAbilities.size())) {
                            result = true;
                        } else {
                            if (!useableAbilities.isEmpty()) {
                                activateAbility(useableAbilities, object, game);
                                result = true;
                            }
                        }
                    }
                }
                return result;
            } else {
                return response.getManaType() == null;
            }
            return true;
        }

        return false;
    }

    private UUID getFixedResponseUUID(Game game) {
        // user can clicks on any side of multi/double faces card, but game must process click to main card all the time
        MageObject object = game.getObject(response.getUUID());

        // mdf cards
        if (object instanceof ModalDoubleFacesCardHalf) {
            if (!Zone.BATTLEFIELD.equals(game.getState().getZone(object.getId()))
                    && !Zone.STACK.equals(game.getState().getZone(object.getId()))) {
                return ((ModalDoubleFacesCardHalf) object).getMainCard().getId();
            }
        }

        return response.getUUID();
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
    public TriggeredAbility chooseTriggeredAbility(java.util.List<TriggeredAbility> abilities, Game game) {
        // choose triggered abilitity from list
        if (gameInCheckPlayableState(game)) {
            return null;
        }

        String autoOrderRuleText = null;
        boolean autoOrderUse = getControllingPlayersUserData(game).isAutoOrderTrigger();
        while (canRespond()) {
            // try to set trigger auto order
            java.util.List<TriggeredAbility> abilitiesWithNoOrderSet = new ArrayList<>();
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

            UUID responseId = getFixedResponseUUID(game);
            if (responseId != null) {
                for (TriggeredAbility ability : abilitiesWithNoOrderSet) {
                    if (ability.getId().equals(responseId)
                            || (!macroTriggeredSelectionFlag
                            && ability.getSourceId().equals(responseId))) {
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
        try {
            return playManaHandling(abilityToCast, unpaid, promptText, game);
        } finally {
            payManaMode = false;
        }
    }

    protected boolean playManaHandling(Ability abilityToCast, ManaCost unpaid, String promptText, Game game) {
        // choose mana to pay (from permanents or from pool)
        if (gameInCheckPlayableState(game)) {
            return true;
        }

        // TODO: make canRespond cycle?
        if (canRespond()) {
            Map<String, Serializable> options = new HashMap<>();
            updateGameStatePriority("playMana", game);
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.firePlayManaEvent(playerId, "Pay " + promptText, options);
            }
            waitForResponse(game);

            UUID responseId = getFixedResponseUUID(game);
            if (response.getBoolean() != null) {
                return false;
            } else if (responseId != null) {
                playManaAbilities(responseId, abilityToCast, unpaid, game);
            } else if (response.getString() != null
                    && response.getString().equals("special")) {
                if (unpaid instanceof ManaCostsImpl) {
                    activateSpecialAction(game, unpaid);
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

        return false; // must return false to stop mana pay cycle with non responding player
    }

    /**
     * Gets the number of times the user wants to repeat their macro
     *
     * @param game
     * @return
     */
    public int announceRepetitions(Game game) {
        if (gameInCheckPlayableState(game)) {
            return 0;
        }

        int xValue = 0;
        while (canRespond()) {
            updateGameStatePriority("announceRepetitions", game);
            prepareForResponse(game);
            game.fireGetAmountEvent(playerId, "How many times do you want to repeat your shortcut?", 0, 999);
            waitForResponse(game);

            if (response.getInteger() != null) {
                break;
            }
        }

        if (response.getInteger() != null) {
            xValue = response.getInteger();
        }
        return xValue;
    }

    /**
     * Gets the amount of mana the player want to spent for a x spell
     *
     * @param min
     * @param max
     * @param multiplier - X multiplier after replace events
     * @param message
     * @param ability
     * @param game
     * @return
     */
    @Override
    public int announceXMana(int min, int max, int multiplier, String message, Game game, Ability ability) {
        if (gameInCheckPlayableState(game)) {
            return 0;
        }

        int xValue = 0;
        String extraMessage = (multiplier == 1 ? "" : ", X will be increased by " + multiplier + " times");
        while (canRespond()) {
            updateGameStatePriority("announceXMana", game);
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireGetAmountEvent(playerId, message + extraMessage, min, max);
            }
            waitForResponse(game);

            if (response.getInteger() != null) {
                break;
            }
        }

        if (response.getInteger() != null) {
            xValue = response.getInteger();
        }
        return xValue;
    }

    @Override
    public int announceXCost(int min, int max, String message, Game game, Ability ability, VariableCost variableCost) {
        if (gameInCheckPlayableState(game)) {
            return 0;
        }

        int xValue = 0;
        while (canRespond()) {
            updateGameStatePriority("announceXCost", game);
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireGetAmountEvent(playerId, message, min, max);
            }
            waitForResponse(game);

            if (response.getInteger() != null) {
                break;
            }
        }

        if (response.getInteger() != null) {
            xValue = response.getInteger();
        }
        return xValue;
    }

    protected void playManaAbilities(UUID objectId, Ability abilityToCast, ManaCost unpaid, Game game) {
        updateGameStatePriority("playManaAbilities", game);
        MageObject object = game.getObject(objectId);
        if (object == null) {
            return;
        }

        // GUI: for user's information only - check if mana abilities allows to use here (getUseableManaAbilities already filter it)
        // Reason: when you use special mana ability then normal mana abilities will be restricted to pay. Users
        // can't see lands as playable and must know the reason (if they click on land then they get that message)
        if (abilityToCast.getAbilityType() == AbilityType.SPELL) {
            Spell spell = game.getStack().getSpell(abilityToCast.getSourceId());
            boolean haveManaAbilities = object.getAbilities().stream().anyMatch(ManaAbility.class::isInstance);
            if (spell != null && !spell.isResolving() && haveManaAbilities) {
                switch (spell.getCurrentActivatingManaAbilitiesStep()) {
                    // if you used special mana ability like convoke then normal mana abilities will be restricted to use, see Convoke for details
                    case BEFORE:
                    case NORMAL:
                        break;
                    case AFTER:
                        game.informPlayer(this, "You can no longer use activated mana abilities to pay for the current spell (special mana pay already used). Cancel and recast the spell to activate mana abilities first.");
                        return;
                }
            }
        }

        Zone zone = game.getState().getZone(object.getId());
        if (zone != null) {
            LinkedHashMap<UUID, ActivatedManaAbilityImpl> useableAbilities = getUseableManaAbilities(object, zone, game);
            if (!useableAbilities.isEmpty()) {
                useableAbilities = ManaUtil.tryToAutoPay(unpaid, useableAbilities); // eliminates other abilities if one fits perfectly
                currentlyUnpaidMana = unpaid;
                activateAbility(useableAbilities, object, game);
                currentlyUnpaidMana = null;
            }
        }
    }

    @Override
    public void selectAttackers(Game game, UUID attackingPlayerId) {
        if (gameInCheckPlayableState(game)) {
            return;
        }

        FilterCreatureForCombat filter = filterCreatureForCombat.copy();
        filter.add(new ControllerIdPredicate(attackingPlayerId));

        while (canRespond()) {
            java.util.List<UUID> possibleAttackers = new ArrayList<>();
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

            updateGameStatePriority("selectAttackers", game);
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireSelectEvent(playerId, "Select attackers", options);
            }
            waitForResponse(game);

            UUID responseId = getFixedResponseUUID(game);
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
                            new DeclareAttackerEvent(attackedDefender, attacker.getId(), attacker.getControllerId()), game)) {
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
            } else if (responseId != null) {
                Permanent attacker = game.getPermanent(responseId);
                if (attacker != null) {
                    if (filterCreatureForCombat.match(attacker, playerId, null, game)) {
                        selectDefender(game.getCombat().getDefenders(), attacker.getId(), game);
                    } else if (filterAttack.match(attacker, playerId, null, game) && game.getStack().isEmpty()) {
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
                            new DeclareAttackerEvent(forcedToAttackId, attacker.getId(), attacker.getControllerId()), game)) {
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
            if (forcedToAttack) {
                StringBuilder sb = new StringBuilder(target.getTargetName());
                Permanent attacker = game.getPermanent(attackerId);
                if (attacker != null) {
                    sb.append(" (").append(attacker.getName()).append(')');
                    target.setTargetName(sb.toString());
                }
            }
            if (chooseTarget(Outcome.Damage, target, null, game)) {
                UUID defenderId = getFixedResponseUUID(game);
                declareAttacker(attackerId, defenderId, game, true);
                return true;
            }
        }
        return false;
    }

    protected UUID selectDefenderForAllAttack(Set<UUID> defenders, Game game) {
        TargetDefender target = new TargetDefender(defenders, null);
        if (chooseTarget(Outcome.Damage, target, null, game)) {
            return getFixedResponseUUID(game);
        }
        return null;
    }

    @Override
    public void selectBlockers(Ability source, Game game, UUID defendingPlayerId) {
        if (gameInCheckPlayableState(game)) {
            return;
        }

        FilterCreatureForCombatBlock filter = filterCreatureForCombatBlock.copy();
        filter.add(new ControllerIdPredicate(defendingPlayerId));

        // stop skip on any/zero permanents available
        int possibleBlockersCount = game.getBattlefield().count(filter, playerId, source, game);
        boolean canStopOnAny = possibleBlockersCount != 0 && getControllingPlayersUserData(game).getUserSkipPrioritySteps().isStopOnDeclareBlockersWithAnyPermanents();
        boolean canStopOnZero = possibleBlockersCount == 0 && getControllingPlayersUserData(game).getUserSkipPrioritySteps().isStopOnDeclareBlockersWithZeroPermanents();

        // skip declare blocker step
        // as opposed to declare attacker - it can be skipped by ANY skip button TODO: make same for declare attackers and rework skip buttons (normal and forced)
        boolean skipButtonActivated = passedAllTurns
                || passedUntilEndStepBeforeMyTurn
                || passedTurn
                || passedUntilEndOfTurn
                || passedUntilNextMain;
        if (skipButtonActivated && !canStopOnAny && !canStopOnZero) {
            return;
        }

        while (canRespond()) {
            updateGameStatePriority("selectBlockers", game);
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                Map<String, Serializable> options = new HashMap<>();
                java.util.List<UUID> possibleBlockers = game.getBattlefield().getActivePermanents(filter, playerId, game).stream()
                        .map(p -> p.getId())
                        .collect(Collectors.toList());
                options.put(Constants.Option.POSSIBLE_BLOCKERS, (Serializable) possibleBlockers);
                game.fireSelectEvent(playerId, "Select blockers", options);
            }
            waitForResponse(game);

            UUID responseId = getFixedResponseUUID(game);
            if (response.getBoolean() != null) {
                return;
            } else if (response.getInteger() != null) {
                return;
            } else if (responseId != null) {
                Permanent blocker = game.getPermanent(responseId);
                if (blocker != null) {
                    boolean removeBlocker = false;
                    // does not block yet and can block or can block more attackers
                    if (filter.match(blocker, playerId, source, game)) {
                        selectCombatGroup(defendingPlayerId, blocker.getId(), game);
                    } else if (filterBlock.match(blocker, playerId, source, game)
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
    public UUID chooseAttackerOrder(java.util.List<Permanent> attackers, Game game) {
        if (gameInCheckPlayableState(game)) {
            return null;
        }

        while (canRespond()) {
            updateGameStatePriority("chooseAttackerOrder", game);
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireSelectTargetEvent(playerId, "Pick attacker", attackers, true);
            }
            waitForResponse(game);

            UUID responseId = getFixedResponseUUID(game);
            if (responseId != null) {
                for (Permanent perm : attackers) {
                    if (perm.getId().equals(responseId)) {
                        return perm.getId();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public UUID chooseBlockerOrder(java.util.List<Permanent> blockers, CombatGroup combatGroup, java.util.List<UUID> blockerOrder, Game game) {
        if (gameInCheckPlayableState(game)) {
            return null;
        }

        while (canRespond()) {
            updateGameStatePriority("chooseBlockerOrder", game);
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireSelectTargetEvent(playerId, "Pick blocker", blockers, true);
            }
            waitForResponse(game);

            UUID responseId = getFixedResponseUUID(game);
            if (responseId != null) {
                for (Permanent perm : blockers) {
                    if (perm.getId().equals(responseId)) {
                        return perm.getId();
                    }
                }
            }
        }
        return null;
    }

    protected void selectCombatGroup(UUID defenderId, UUID blockerId, Game game) {
        if (gameInCheckPlayableState(game)) {
            return;
        }
        TargetAttackingCreature target = new TargetAttackingCreature();

        // TODO: add canRespond cycle?
        if (!canRespond()) {
            return;
        }

        updateGameStatePriority("selectCombatGroup", game);
        prepareForResponse(game);
        if (!isExecutingMacro()) {
            // possible attackers to block
            Set<UUID> attackers = target.possibleTargets(playerId, null, game);
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

        UUID responseId = getFixedResponseUUID(game);
        if (response.getBoolean() != null) {
            // do nothing
        } else if (responseId != null) {
            CombatGroup group = game.getCombat().findGroup(responseId);
            if (group != null) {
                // check if already blocked, if not add
                if (!group.getBlockers().contains(blockerId)) {
                    declareBlocker(defenderId, blockerId, responseId, game);
                } else { // else remove from block
                    game.getCombat().removeBlockerGromGroup(blockerId, group, game);
                }
            }
        }
    }

    @Override
    public void assignDamage(int damage, java.util.List<UUID> targets, String singleTargetName, UUID attackerId, Ability source, Game game) {
        updateGameStatePriority("assignDamage", game);
        int remainingDamage = damage;
        while (remainingDamage > 0 && canRespond()) {
            Target target = new TargetAnyTarget();
            target.setNotTarget(true);
            if (singleTargetName != null) {
                target.setTargetName(singleTargetName);
            }
            choose(Outcome.Damage, target, source, game);
            if (targets.isEmpty() || targets.contains(target.getFirstTarget())) {
                int damageAmount = getAmount(0, remainingDamage, "Select amount", game);
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    permanent.damage(damageAmount, attackerId, source, game, false, true);
                    remainingDamage -= damageAmount;
                } else {
                    Player player = game.getPlayer(target.getFirstTarget());
                    if (player != null) {
                        player.damage(damageAmount, attackerId, source, game);
                        remainingDamage -= damageAmount;
                    }
                }
            }
        }
    }

    @Override
    public int getAmount(int min, int max, String message, Game game) {
        if (gameInCheckPlayableState(game)) {
            return 0;
        }

        while (canRespond()) {
            updateGameStatePriority("getAmount", game);
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireGetAmountEvent(playerId, message, min, max);
            }
            waitForResponse(game);

            if (response.getInteger() != null) {
                break;
            }
        }

        if (response.getInteger() != null) {
            return response.getInteger();
        } else {
            return 0;
        }
    }

    @Override
    public List<Integer> getMultiAmount(Outcome outcome, List<String> messages, int min, int max, MultiAmountType type, Game game) {
        int needCount = messages.size();
        List<Integer> defaultList = MultiAmountType.prepareDefaltValues(needCount, min, max);
        if (needCount == 0) {
            return defaultList;
        }

        if (gameInCheckPlayableState(game)) {
            return defaultList;
        }

        List<Integer> answer = null;
        while (canRespond()) {
            updateGameStatePriority("getMultiAmount", game);
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                Map<String, Serializable> options = new HashMap<>(2);
                options.put("title", type.getTitle());
                options.put("header", type.getHeader());
                game.fireGetMultiAmountEvent(playerId, messages, min, max, options);
            }
            waitForResponse(game);

            // waiting correct values only
            if (response.getString() != null) {
                answer = MultiAmountType.parseAnswer(response.getString(), needCount, min, max, false);
                if (MultiAmountType.isGoodValues(answer, needCount, min, max)) {
                    break;
                } else {
                    // it's not normal: can be cheater or a wrong GUI checks
                    answer = null;
                    logger.error(String.format("GUI return wrong MultiAmountType values: %d %d %d - %s", needCount, min, max, response.getString()));
                    game.informPlayer(this, "Error, you must enter correct values.");
                }
            }
        }

        if (answer != null) {
            return answer;
        } else {
            // something wrong, e.g. player disconnected
            return defaultList;
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
    public void pickCard(java.util.List<Card> cards, Deck deck, Draft draft) {
        draft.firePickCardEvent(playerId);
    }

    /**
     * Activate special action (normal or mana)
     *
     * @param game
     * @param unpaidForManaAction - set unpaid for mana actions like convoke
     */
    protected void activateSpecialAction(Game game, ManaCost unpaidForManaAction) {
        if (gameInCheckPlayableState(game)) {
            return;
        }

        if (!canRespond()) {
            return;
        }

        Map<UUID, SpecialAction> specialActions = game.getState().getSpecialActions().getControlledBy(playerId, unpaidForManaAction != null);
        if (!specialActions.isEmpty()) {

            updateGameStatePriority("specialAction", game);
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireGetChoiceEvent(playerId, name, null, new ArrayList<>(specialActions.values()));
            }
            waitForResponse(game);

            UUID responseId = getFixedResponseUUID(game);
            if (responseId != null) {
                if (specialActions.containsKey(responseId)) {
                    SpecialAction specialAction = specialActions.get(responseId);
                    if (unpaidForManaAction != null) {
                        specialAction.setUnpaidMana(unpaidForManaAction);
                    }
                    activateAbility(specialAction, game);
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
        if (gameInCheckPlayableState(game)) {
            return;
        }

        if (!canRespond()) {
            return;
        }

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
        if (userData.isUseFirstManaAbility() && object instanceof Permanent && object.isLand(game)) {
            ActivatedAbility ability = abilities.values().iterator().next();
            if (ability instanceof ActivatedManaAbilityImpl) {
                activateAbility(ability, game);
                return;
            }
        }

        String message = "Choose spell or ability to play";
        if (object != null) {
            message = message + "<br>" + object.getLogName();
        }

        // TODO: add canRespond cycle?
        prepareForResponse(game);
        if (!isExecutingMacro()) {
            game.fireGetChoiceEvent(playerId, message, object, new ArrayList<>(abilities.values()));
        }
        waitForResponse(game);

        UUID responseId = getFixedResponseUUID(game);
        if (responseId != null) {
            if (abilities.containsKey(responseId)) {
                activateAbility(abilities.get(responseId), game);
            }
        }
    }

    /**
     * Hide ability picker dialog on one available ability to activate
     *
     * @param ability
     * @param game
     * @return
     */
    private boolean suppressAbilityPicker(ActivatedAbility ability, Game game) {
        if (getControllingPlayersUserData(game).isShowAbilityPickerForced()) {
            // user activated an ability picker in preferences

            // force to show ability picker for double faces cards in hand/commander/exile and other zones
            Card mainCard = game.getCard(CardUtil.getMainCardId(game, ability.getSourceId()));
            if (mainCard != null && !Zone.BATTLEFIELD.equals(game.getState().getZone(mainCard.getId()))) {
                if (mainCard instanceof SplitCard
                        || mainCard instanceof AdventureCard
                        || mainCard instanceof ModalDoubleFacesCard) {
                    return false;
                }
            }

            // hide on land play
            if (ability instanceof PlayLandAbility) {
                return true;
            }

            // hide on alternative cost activated
            if (!getCastSourceIdWithAlternateMana().contains(ability.getSourceId())
                    && ability.getManaCostsToPay().manaValue() > 0) {
                return true;
            }

            // hide on mana activate and show all other
            return ability instanceof ActivatedManaAbilityImpl;
        }
        return true;
    }

    @Override
    public SpellAbility chooseAbilityForCast(Card card, Game game, boolean noMana) {
        if (gameInCheckPlayableState(game)) {
            return null;
        }

        // TODO: add canRespond cycle?
        if (!canRespond()) {
            return null;
        }

        MageObject object = game.getObject(card.getId()); // must be object to find real abilities (example: commander)
        if (object != null) {
            String message = "Choose ability to cast" + (noMana ? " for FREE" : "") + "<br>" + object.getLogName();
            LinkedHashMap<UUID, SpellAbility> useableAbilities = PlayerImpl.getCastableSpellAbilities(game, playerId, object, game.getState().getZone(object.getId()), noMana);
            if (useableAbilities != null
                    && useableAbilities.size() == 1) {
                return useableAbilities.values().iterator().next();
            } else if (useableAbilities != null
                    && !useableAbilities.isEmpty()) {

                updateGameStatePriority("chooseAbilityForCast", game);
                prepareForResponse(game);
                if (!isExecutingMacro()) {
                    game.fireGetChoiceEvent(playerId, message, object, new ArrayList<>(useableAbilities.values()));
                }
                waitForResponse(game);

                UUID responseId = getFixedResponseUUID(game);
                if (responseId != null) {
                    if (useableAbilities.containsKey(responseId)) {
                        return useableAbilities.get(responseId);
                    }
                }
            }
        }

        // default ability (example: on disconnect or cancel)
        return card.getSpellAbility();
    }

    @Override
    public Mode chooseMode(Modes modes, Ability source, Game game) {
        // choose mode to activate
        if (gameInCheckPlayableState(game)) {
            return null;
        }

        if (modes.size() > 1) {
            // done option for up to choices
            boolean canEndChoice = modes.getSelectedModes().size() >= modes.getMinModes() || modes.isMayChooseNone();
            MageObject obj = game.getObject(source);
            Map<UUID, String> modeMap = new LinkedHashMap<>();
            int modeIndex = 0;
            AvailableModes:
            for (Mode mode : modes.getAvailableModes(source, game)) {
                modeIndex++;
                int timesSelected = modes.getSelectedStats(mode.getId());
                for (UUID selectedModeId : modes.getSelectedModes()) {
                    Mode selectedMode = modes.get(selectedModeId);
                    if (mode.getId().equals(selectedMode.getId())) {
                        // mode selected
                        if (modes.isEachModeMoreThanOnce()) {
                            // can select again
                        } else {
                            // hide mode from dialog
                            continue AvailableModes; // TODO: test 2x cheat here
                        }
                    }
                }

                if (mode.getTargets().canChoose(source.getControllerId(), source, game)) { // and needed targets have to be available
                    String modeText = mode.getEffects().getText(mode);
                    if (obj != null) {
                        modeText = modeText.replace("{this}", obj.getName());
                    }
                    if (modes.isEachModeMoreThanOnce()) {
                        if (timesSelected > 0) {
                            modeText = "(selected " + timesSelected + "x) " + modeText;
                        }
                    }
                    if (!modeText.isEmpty()) {
                        modeText = Character.toUpperCase(modeText.charAt(0)) + modeText.substring(1);
                    }
                    modeMap.put(mode.getId(), modeIndex + ". " + modeText);
                }
            }

            if (!modeMap.isEmpty()) {

                // can done for up to
                if (canEndChoice) {
                    modeMap.put(Modes.CHOOSE_OPTION_DONE_ID, "Done");
                }
                modeMap.put(Modes.CHOOSE_OPTION_CANCEL_ID, "Cancel");

                boolean done = false;
                while (!done && canRespond()) {

                    String message = "Choose mode (selected " + modes.getSelectedModes().size() + " of " + modes.getMaxModes(game, source)
                            + ", min " + modes.getMinModes() + ")";
                    if (obj != null) {
                        message = message + "<br>" + obj.getLogName();
                    }

                    updateGameStatePriority("chooseMode", game);
                    prepareForResponse(game);
                    if (!isExecutingMacro()) {
                        game.fireGetModeEvent(playerId, message, modeMap);
                    }
                    waitForResponse(game);

                    UUID responseId = getFixedResponseUUID(game);
                    if (responseId != null) {
                        for (Mode mode : modes.getAvailableModes(source, game)) {
                            if (mode.getId().equals(responseId)) {
                                // TODO: add checks on 2x selects (cheaters can rewrite client side code and select same mode multiple times)
                                // reason: wrong setup eachModeMoreThanOnce and eachModeOnlyOnce in many cards
                                return mode;
                            }
                        }

                        // end choice by done option in ability pickup dialog
                        if (canEndChoice && Modes.CHOOSE_OPTION_DONE_ID.equals(responseId)) {
                            done = true;
                        }

                        // cancel choice (remove all selections)
                        if (Modes.CHOOSE_OPTION_CANCEL_ID.equals(responseId)) {
                            modes.clearSelectedModes();
                        }
                    } else if (canEndChoice) {
                        // end choice by done button in feedback panel
                        // disable after done option implemented
                        // done = true;
                    }

                    // triggered abilities can't be skipped by cancel or wrong answer
                    if (source.getAbilityType() != AbilityType.TRIGGERED) {
                        done = true;
                    }
                }
            }
            return null;
        }

        return modes.getMode();
    }

    @Override
    public boolean choosePile(Outcome outcome, String message, java.util.List<? extends Card> pile1, java.util.List<? extends Card> pile2, Game game) {
        if (gameInCheckPlayableState(game)) {
            return true;
        }

        while (canRespond()) {
            updateGameStatePriority("choosePile", game);
            prepareForResponse(game);
            if (!isExecutingMacro()) {
                game.fireChoosePileEvent(playerId, message, pile1, pile2);
            }
            waitForResponse(game);

            if (response.getBoolean() != null) {
                break;
            }
        }

        if (response.getBoolean() != null) {
            return response.getBoolean();
        } else {
            return false;
        }
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
        // call that for every choose cycle before prepareForResponse
        // (some choose logic can asks another question with different game state priority)
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
            // TODO: chooseUse and other dialogs must be under controlling player
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

    private boolean gameInCheckPlayableState(Game game) {
        return gameInCheckPlayableState(game, false);
    }

    private boolean gameInCheckPlayableState(Game game, boolean ignoreWarning) {
        if (game.inCheckPlayableState()) {
            if (!ignoreWarning) {
                logger.warn(String.format("Current stack: %d - %s",
                        game.getStack().size(),
                        game.getStack().stream().map(Object::toString).collect(Collectors.joining(", "))
                ));
                logger.warn("Player interaction in checkPlayableState", new Throwable());
            }
            return true;
        }
        return false;
    }
}
