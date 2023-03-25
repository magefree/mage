package org.mage.test.player;

import mage.*;
import mage.abilities.*;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.AlternativeSourceCosts;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.mana.ManaOptions;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.decks.Deck;
import mage.choices.Choice;
import mage.constants.*;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.designations.Designation;
import mage.designations.DesignationType;
import mage.filter.*;
import mage.filter.common.*;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.SummoningSicknessPredicate;
import mage.game.Game;
import mage.game.GameImpl;
import mage.game.Graveyard;
import mage.game.Table;
import mage.game.combat.CombatGroup;
import mage.game.draft.Draft;
import mage.game.events.GameEvent;
import mage.game.match.Match;
import mage.game.match.MatchPlayer;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.game.tournament.Tournament;
import mage.player.ai.ComputerPlayer;
import mage.players.*;
import mage.players.net.UserData;
import mage.target.*;
import mage.target.common.*;
import mage.util.CardUtil;
import mage.util.RandomUtil;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.mage.test.serverside.base.impl.CardTestPlayerAPIImpl.*;

/**
 * @author BetaSteward_at_googlemail.com
 * @author Simown
 * @author JayDi85
 */
@Ignore
public class TestPlayer implements Player {

    private static final Logger LOGGER = Logger.getLogger(TestPlayer.class);

    public static final String TARGET_SKIP = "[target_skip]"; // stop/skip targeting
    public static final String CHOICE_SKIP = "[choice_skip]"; // stop/skip choice
    public static final String MANA_CANCEL = "[mana_cancel]"; // cancel payment
    public static final String SKIP_FAILED_COMMAND = "[skip_failed_command]"; // skip next command in player's queue (can remove cast commands after try to activate)
    public static final String BLOCK_SKIP = "[block_skip]";
    public static final String ATTACK_SKIP = "[attack_skip]";
    public static final String NO_TARGET = "NO_TARGET"; // cast spell or activate ability without target defines
    public static final String FLIPCOIN_RESULT_TRUE = "[flipcoin_true]";
    public static final String FLIPCOIN_RESULT_FALSE = "[flipcoin_false]";
    public static final String DIE_ROLL = "[die_roll]: ";

    private int maxCallsWithoutAction = 400;
    private int foundNoAction = 0;

    // full playable AI, TODO: can be deleted?
    private boolean AIPlayer;
    // AI simulates a real game, e.g. ignores strict mode and play command/priority, see aiXXX commands
    // true - unit tests uses real AI logic (e.g. AI hints and AI workarounds in cards)
    // false - unit tests uses Human logic and dialogs
    private boolean AIRealGameSimulation = false;

    private final List<PlayerAction> actions = new ArrayList<>();
    private final Map<PlayerAction, PhaseStep> actionsToRemoveLater = new HashMap<>(); // remove actions later, on next step (e.g. for AI commands)
    private final Map<Integer, HashMap<UUID, ArrayList<PlayerAction>>> rollbackActions = new HashMap<>(); // actions to add after a executed rollback
    private final List<String> choices = new ArrayList<>(); // choices stack for choice
    private final List<String> targets = new ArrayList<>(); // targets stack for choose (it's uses on empty direct target by cast command)
    private final Map<String, UUID> aliases = new HashMap<>(); // aliases for game objects/players (use it for cards with same name to save and use)
    private final List<String> modesSet = new ArrayList<>();

    private final ComputerPlayer computerPlayer; // real player

    // Strict mode for all choose dialogs:
    // - enable checks for wrong or missing choice commands (you must set up all choices by unit test)
    // - enable inner choice dialogs accessable by set up choices
    //   (example: card call TestPlayer's choice, but it uses another choices, see docs in TestComputerPlayer)
    private boolean strictChooseMode = false;

    private String[] groupsForTargetHandling = null;

    // Tracks the initial turns (turn 0s) both players are given at the start of the game.
    // Before actual turns start. Needed for checking attacker/blocker legality in the tests
    private static int initialTurns = 0;

    public TestPlayer(TestComputerPlayer computerPlayer) {
        this.computerPlayer = computerPlayer;
        AIPlayer = false;
        computerPlayer.setTestPlayerLink(this);
    }

    public TestPlayer(TestComputerPlayer7 computerPlayer) {
        this.computerPlayer = computerPlayer;
        AIPlayer = false;
        computerPlayer.setTestPlayerLink(this);
    }

    public TestPlayer(TestComputerPlayerMonteCarlo computerPlayer) {
        this.computerPlayer = computerPlayer;
        AIPlayer = false;
        computerPlayer.setTestPlayerLink(this);
    }

    public TestPlayer(final TestPlayer testPlayer) {
        this.AIPlayer = testPlayer.AIPlayer;
        this.AIRealGameSimulation = testPlayer.AIRealGameSimulation;
        this.foundNoAction = testPlayer.foundNoAction;
        this.actions.addAll(testPlayer.actions);
        this.choices.addAll(testPlayer.choices);
        this.targets.addAll(testPlayer.targets);
        this.aliases.putAll(testPlayer.aliases);
        this.modesSet.addAll(testPlayer.modesSet);
        this.computerPlayer = testPlayer.computerPlayer.copy();
        if (testPlayer.groupsForTargetHandling != null) {
            this.groupsForTargetHandling = testPlayer.groupsForTargetHandling.clone();
        }
        this.strictChooseMode = testPlayer.strictChooseMode;
    }

    public void addChoice(String choice) {
        choices.add(choice);
    }

    public List<String> getChoices() {
        return this.choices;
    }

    public List<String> getTargets() {
        return this.targets;
    }

    public Map<String, UUID> getAliases() {
        return this.aliases;
    }

    public UUID getAliasByName(String searchName) {
        if (searchName.startsWith(ALIAS_PREFIX)) {
            return this.aliases.getOrDefault(searchName.substring(1), null);
        } else {
            return this.aliases.getOrDefault(searchName, null);
        }
    }

    public void addModeChoice(String mode) {
        modesSet.add(mode);
    }

    public void addTarget(String target) {
        targets.add(target);
    }

    public void addAlias(String name, UUID Id) {
        aliases.put(name, Id);
    }

    public ManaOptions getAvailableManaTest(Game game) {
        return computerPlayer.getManaAvailable(game);
    }

    public void addAction(int turnNum, PhaseStep step, String action) {
        actions.add(new PlayerAction("", turnNum, step, action));
    }

    public void addAction(String actionName, int turnNum, PhaseStep step, String action) {
        actions.add(new PlayerAction(actionName, turnNum, step, action));
    }

    public void addAction(PlayerAction playerAction) {
        actions.add(playerAction);
    }

    public List<PlayerAction> getActions() {
        return actions;
    }

    /**
     * @param maxCallsWithoutAction max number of priority passes a player may
     *                              have for this test (default = 100)
     */
    public void setMaxCallsWithoutAction(int maxCallsWithoutAction) {
        this.maxCallsWithoutAction = maxCallsWithoutAction;
    }

    public void setInitialTurns(int turns) {
        initialTurns = turns;
    }

    private Permanent findPermanent(FilterPermanent filter, String name, UUID controllerID, Game game) {
        return findPermanent(filter, name, controllerID, game, true);
    }

    /**
     * Finds a permanent based on a general filter an their name and possible
     * index.
     * <p>
     * An index is permitted after the permanent's name to denote their index on
     * the battlefield Either use name="<permanent>" which will get the first
     * permanent with that name on the battlefield that meets the filter
     * criteria or name="<permanent>:<index>" to get the named permanent with
     * that index on the battlefield.
     * <p>
     * Permanents are zero indexed in the order they entered the battlefield for
     * each controller:
     * <p>
     * findPermanent(new AttackingCreatureFilter(), "Human", <controllerID>,
     * <game>) Will find the first "Human" creature that entered the battlefield
     * under this controller and is attacking.
     * <p>
     * findPermanent(new FilterControllerPermanent(), "Fabled Hero:3",
     * <controllerID>, <game>) Will find the 4th permanent named "Fabled Hero"
     * that entered the battlefield under this controller
     * <p>
     * An exception will be thrown if no permanents match the criteria or the
     * index is larger than the number of permanents found with that name.
     * <p>
     * failOnNotFound boolean controls if this function returns null for a
     * permanent not found on the battlefield. Currently used only as a
     * workaround for attackers in selectAttackers() being able to attack
     * multiple times each combat. See issue #3038
     */
    private Permanent findPermanent(FilterPermanent filter, String name, UUID controllerID, Game game, boolean failOnNotFound) {
        String filteredName = name;
        Pattern indexedName = Pattern.compile("^([\\w| ]+):(\\d+)$"); // Ends with <:number>
        Matcher indexedMatcher = indexedName.matcher(filteredName);
        int index = 0;
        if (indexedMatcher.matches()) {
            filteredName = indexedMatcher.group(1);
            index = Integer.parseInt(indexedMatcher.group(2));
        }
        filter.add(new NamePredicate(filteredName, true)); // must find any cards even without names
        List<Permanent> allPermanents = game.getBattlefield().getAllActivePermanents(filter, controllerID, game);
        if (allPermanents.isEmpty()) {
            if (failOnNotFound) {
                throw new AssertionError("No permanents found called " + filteredName + " that match the filter criteria \"" + filter.getMessage() + "\"");
            }
            return null;
        } else if (allPermanents.size() - 1 < index) {
            if (failOnNotFound) {
                throw new AssertionError("Cannot find " + filteredName + ":" + index + " that match the filter criteria \"" + filter.getMessage() + "\"" + ".\nOnly " + allPermanents.size() + " called " + filteredName + " found for this controller(zero indexed).");
            }
            return null;
        }
        return allPermanents.get(index);
    }

    private boolean checkExecuteCondition(String[] groups, Game game) {
        if (groups[2].startsWith("spellOnStack=")) {
            String spellOnStack = groups[2].substring(13);
            for (StackObject stackObject : game.getStack()) {
                if (stackObject.getStackAbility().toString().contains(spellOnStack)) {
                    return true;
                }
            }
            return false;
        } else if (groups[2].startsWith("spellCopyOnStack=")) {
            String spellOnStack = groups[2].substring(17);
            for (StackObject stackObject : game.getStack()) {
                if (stackObject.getStackAbility().toString().contains(spellOnStack)) {
                    if (stackObject.isCopy()) {
                        return true;
                    }
                }
            }
            return false;
        } else if (groups[2].startsWith("!spellOnStack=")) {
            String spellNotOnStack = groups[2].substring(14);
            for (StackObject stackObject : game.getStack()) {
                if (stackObject.getStackAbility().toString().contains(spellNotOnStack)) {
                    return false;
                }
            }
            return true;
        } else if (groups[2].startsWith("spellOnTopOfStack=")) {
            String spellOnTopOFStack = groups[2].substring(18);
            if (!game.getStack().isEmpty()) {
                StackObject stackObject = game.getStack().getFirst();
                return stackObject != null && stackObject.getStackAbility().toString().contains(spellOnTopOFStack);
            }
            return false;
        } else if (groups[2].startsWith("manaInPool=")) {
            String manaInPool = groups[2].substring(11);
            int amountOfMana = Integer.parseInt(manaInPool);
            return computerPlayer.getManaPool().getMana().count() >= amountOfMana;
        }
        return true;
    }

    @Override
    public boolean addTargets(Ability ability, Game game) {
        if (groupsForTargetHandling == null) {
            return true;
        }
        boolean result = true;
        for (int i = 1; i < groupsForTargetHandling.length; i++) {
            String group = groupsForTargetHandling[i];
            if (group.startsWith("spell") || group.startsWith("!spell") || group.startsWith("target=null") || group.startsWith("manaInPool=")) {
                break;
            }
            result = handleTargetString(group, ability, game);
        }
        return result;
    }

    private boolean handleTargetString(String target, Ability ability, Game game) {
        boolean result = false;
        if (target.startsWith("targetPlayer=")) {
            result = handlePlayerTarget(target.substring(target.indexOf("targetPlayer=") + 13), ability, game);
        } else if (target.startsWith("target=")) {
            result = handleNonPlayerTargetTarget(target.substring(target.indexOf("target=") + 7), ability, game);
        }
        return result;
    }

    private boolean handlePlayerTarget(String target, Ability ability, Game game) {
        boolean result = true;
        int targetsSet = 0;
        for (Player player : game.getPlayers().values()) {
            if (player.getName().equals(target)) {
                if (ability.getTargets().isEmpty()) {
                    throw new UnsupportedOperationException("Ability has no targets, but there is a player target set - " + ability);
                }
                if (ability.getTargets().get(0) instanceof TargetAmount) {
                    return true; // targetAmount have to be set by setTargetAmount in the test script
                }
                ability.getTargets().get(0).addTarget(player.getId(), ability, game);
                targetsSet++;
                break;
            }
        }
        if (targetsSet < 1) {
            result = false;
        }
        return result;
    }

    public String generateAliasName(String baseAlias, boolean useMiltiNames, int iteration) {
        if (useMiltiNames) {
            return baseAlias + "." + iteration;
        } else {
            return baseAlias;
        }
    }

    public boolean hasAbilityTargetNameOrAlias(Game game, Ability ability, String nameOrAlias) {
        // use cases:
        // * Cast cardName with extra
        // * Cast @ref
        // * Ability text
        // * @ref ability text from ref object
        boolean foundObject;
        boolean foundAbility;
        if (nameOrAlias.startsWith("Cast ")) {
            // object name:
            // Cast cardName with extra
            // Cast @ref
            String searchObject = nameOrAlias.substring("Cast ".length());
            if (searchObject.contains(" with ")
                    || searchObject.contains(" using ")
                    || searchObject.contains("fused ")) {
                Assert.assertFalse("alternative spell don't support alias", searchObject.startsWith(ALIAS_PREFIX));
                foundObject = true;
                foundAbility = ability.toString().startsWith(nameOrAlias);
            } else {
                foundObject = hasObjectTargetNameOrAlias(game.getObject(ability.getSourceId()), searchObject);
                foundAbility = searchObject.startsWith(ALIAS_PREFIX) || ability.toString().startsWith(nameOrAlias);
            }
        } else if (nameOrAlias.startsWith(ALIAS_PREFIX)) {
            // object alias with ability text:
            // @ref ability text from ref object
            Assert.assertTrue("ability alias must contains space", nameOrAlias.contains(" "));
            String searchObject = nameOrAlias.substring(0, nameOrAlias.indexOf(" "));
            String searchAbility = nameOrAlias.substring(nameOrAlias.indexOf(" ") + 1);
            foundObject = hasObjectTargetNameOrAlias(game.getObject(ability.getSourceId()), searchObject);
            foundAbility = ability.toString().startsWith(searchAbility);
        } else {
            // ability text
            foundObject = true;
            foundAbility = ability.toString().startsWith(nameOrAlias);
        }

        return foundObject && foundAbility;
    }

    public boolean hasObjectTargetNameOrAlias(MageObject object, String nameOrAlias) {
        if (object == null || nameOrAlias == null) {
            return false;
        }

        if (nameOrAlias.startsWith(ALIAS_PREFIX) && object.getId().equals(getAliasByName(nameOrAlias))) {
            return true;
        }

        // must search any names, even empty (face down cards)
        if (CardUtil.haveSameNames(nameOrAlias, object.getName(), true)) {
            return true;
        }

        // no more empty names needs
        if (nameOrAlias.isEmpty()) {
            return false;
        }

        // two search mode: for cards/permanents (strict) and for abilities (like)
        if (object instanceof Ability) {
            return object.getName().startsWith(nameOrAlias);
        } else if (object instanceof Spell) {
            return ((Spell) object).getSpellAbility().getName().startsWith(nameOrAlias);
        } else {
            return object.getName().equals(nameOrAlias);
        }
    }

    private boolean handleNonPlayerTargetTarget(String target, Ability ability, Game game) {
        boolean result = true;
        if (target == null) {
            return true; // needed if spell has no target but waits until spell is on the stack
        }
        String[] targetList = target.split("\\^");
        int index = 0;
        int targetsSet = 0;
        for (String targetName : targetList) {
            Mode selectedMode;
            if (targetName.startsWith("mode=")) {
                int modeNr = Integer.parseInt(targetName.substring(5, 6));
                if (modeNr == 0 || modeNr > (ability.getModes().isEachModeMoreThanOnce() ? ability.getModes().getSelectedModes().size() : ability.getModes().size())) {
                    throw new UnsupportedOperationException("Given mode number (" + modeNr + ") not available for " + ability.toString());
                }
                UUID modeId = ability.getModes().getModeId(modeNr);
                selectedMode = ability.getModes().get(modeId);
                if (!Objects.equals(modeId, ability.getModes().getMode().getId())) {
                    ability.getModes().setActiveMode(modeId);
                    index = 0; // reset target index if mode changes
                }
                targetName = targetName.substring(6);
            } else {
                selectedMode = ability.getModes().getMode();
            }
            if (selectedMode == null) {
                throw new UnsupportedOperationException("Mode not available for " + ability);
            }
            if (selectedMode.getTargets().isEmpty()) {
                throw new AssertionError("Ability has no targets. " + ability);
            }
            if (index >= selectedMode.getTargets().size()) {
                break; // this can happen if targets should be set but can't be used because of hexproof e.g.
            }
            Target currentTarget = selectedMode.getTargets().get(index);
            if (targetName.startsWith("targetPlayer=")) {
                target = targetName.substring(targetName.indexOf("targetPlayer=") + 13);
                for (Player player : game.getPlayers().values()) {
                    if (player.getName().equals(target)) {
                        currentTarget.addTarget(player.getId(), ability, game);
                        index++;
                        targetsSet++;
                        break;
                    }
                }
            } else {
                boolean originOnly = false;
                boolean copyOnly = false;
                if (targetName.endsWith("]")) {
                    if (targetName.endsWith("[no copy]")) {
                        originOnly = true;
                        targetName = targetName.substring(0, targetName.length() - 9);
                    }
                    if (targetName.endsWith("[only copy]")) {
                        copyOnly = true;
                        targetName = targetName.substring(0, targetName.length() - 11);
                    }
                }
                for (UUID id : currentTarget.possibleTargets(ability.getControllerId(), ability, game)) {
                    if (!currentTarget.getTargets().contains(id)) {
                        MageObject object = game.getObject(id);

                        if (object == null) {
                            continue;
                        }

                        // only origin
                        if (originOnly && object.isCopy()) {
                            continue;
                        }

                        // only copy
                        if (copyOnly && !object.isCopy()) {
                            continue;
                        }

                        // need by alias or by name
                        if (!hasObjectTargetNameOrAlias(object, targetName)) {
                            continue;
                        }

                        // found, can use as target
                        if (currentTarget.getNumberOfTargets() == 1) {
                            currentTarget.clearChosen();
                        }
                        if (currentTarget.getOriginalTarget() instanceof TargetCreaturePermanentAmount) {
                            // supports only to set the complete amount to one target
                            TargetCreaturePermanentAmount targetAmount = (TargetCreaturePermanentAmount) currentTarget.getOriginalTarget();
                            targetAmount.setAmount(ability, game);
                            int amount = targetAmount.getAmountRemaining();
                            targetAmount.addTarget(id, amount, ability, game);
                            targetsSet++;
                        } else {
                            currentTarget.addTarget(id, ability, game);
                            targetsSet++;
                        }
                        if (currentTarget.getTargets().size() == currentTarget.getMaxNumberOfTargets()) {
                            index++;
                        }
                        break;
                    }
                }
            }
        }
        if (targetsSet != targetList.length) {
            result = false;
        }
        return result;
    }

    @Override
    public int getActionCount() {
        return actions.size();
    }

    @Override
    public TestPlayer copy() {
        return new TestPlayer(this);
    }

    @Override
    public boolean priority(Game game) {
        // later remove actions (ai commands related)
        if (actionsToRemoveLater.size() > 0) {
            List<PlayerAction> removed = new ArrayList<>();
            actionsToRemoveLater.forEach((action, step) -> {
                if (game.getTurnStepType() != step) {
                    action.onActionRemovedLater(game, this);
                    actions.remove(action);
                    removed.add(action);
                }
            });
            removed.forEach(actionsToRemoveLater::remove);
        }

        // fake test ability for triggers and events
        Ability source = new SimpleStaticAbility(Zone.OUTSIDE, new InfoEffect("adding testing cards"));
        source.setControllerId(this.getId());

        int numberOfActions = actions.size();
        List<PlayerAction> tempActions = new ArrayList<>();
        tempActions.addAll(actions);
        for (PlayerAction action : tempActions) {
            if (action.getTurnNum() == game.getTurnNum() && action.getStep() == game.getTurnStepType()) {

                if (action.getAction().startsWith(ACTIVATE_ABILITY)) {
                    String command = action.getAction();
                    command = command.substring(command.indexOf(ACTIVATE_ABILITY) + ACTIVATE_ABILITY.length());
                    groupsForTargetHandling = null;
                    String[] groups = command.split("\\$");
                    if (groups.length > 2 && !checkExecuteCondition(groups, game)) {
                        break;
                    }
                    // must process all duplicated abilities (aliases need objects to search)
                    for (ActivatedAbility ability : computerPlayer.getPlayable(game, true, Zone.ALL, false)) { // add wrong action log?
                        if (hasAbilityTargetNameOrAlias(game, ability, groups[0])) {
                            int bookmark = game.bookmarkState();
                            ActivatedAbility newAbility = ability.copy();
                            if (groups.length > 1 && !groups[1].equals("target=" + NO_TARGET)) {
                                groupsForTargetHandling = groups;
                            }
                            if (computerPlayer.activateAbility(newAbility, game)) {
                                actions.remove(action);
                                groupsForTargetHandling = null;
                                foundNoAction = 0; // Reset enless loop check because of no action
                                return true;
                            } else {
                                computerPlayer.restoreState(bookmark, ability.getRule(), game);

                                // skip failed command
                                if (!choices.isEmpty() && choices.get(0).equals(SKIP_FAILED_COMMAND)) {
                                    actions.remove(action);
                                    choices.remove(0);
                                    return true;
                                }
                            }
                            groupsForTargetHandling = null;
                        }
                    }
                    printStart("Available for " + this.getName());
                    printAbilities(game, this.getPlayable(game, true));
                    printEnd();
                    Assert.fail("Can't find ability to activate command: " + command);
                } else if (action.getAction().startsWith(ACTIVATE_MANA)) {
                    String command = action.getAction();
                    command = command.substring(command.indexOf(ACTIVATE_MANA) + ACTIVATE_MANA.length());
                    String[] groups = command.split("\\$");
                    List<MageObject> manaObjects = computerPlayer.getAvailableManaProducers(game);

                    for (MageObject mageObject : manaObjects) {
                        if (mageObject instanceof Permanent) {
                            for (Ability manaAbility : ((Permanent) mageObject).getAbilities(game).getAvailableActivatedManaAbilities(Zone.BATTLEFIELD, getId(), game)) {
                                if (hasAbilityTargetNameOrAlias(game, manaAbility, groups[0])) {
                                    Ability newManaAbility = manaAbility.copy();
                                    computerPlayer.activateAbility((ActivatedAbility) newManaAbility, game);
                                    actions.remove(action);
                                    return true;
                                }
                            }
                        } else if (mageObject instanceof Card) {
                            for (Ability manaAbility : ((Card) mageObject).getAbilities(game).getAvailableActivatedManaAbilities(game.getState().getZone(mageObject.getId()), getId(), game)) {
                                if (hasAbilityTargetNameOrAlias(game, manaAbility, groups[0])) {
                                    Ability newManaAbility = manaAbility.copy();
                                    computerPlayer.activateAbility((ActivatedAbility) newManaAbility, game);
                                    actions.remove(action);
                                    return true;
                                }
                            }
                        } else {
                            for (Ability manaAbility : mageObject.getAbilities().getAvailableActivatedManaAbilities(game.getState().getZone(mageObject.getId()), getId(), game)) {
                                if (hasAbilityTargetNameOrAlias(game, manaAbility, groups[0])) {
                                    Ability newManaAbility = manaAbility.copy();
                                    computerPlayer.activateAbility((ActivatedAbility) newManaAbility, game);
                                    actions.remove(action);
                                    return true;
                                }
                            }
                        }
                    }
                    List<Permanent> manaPermsWithCost = computerPlayer.getAvailableManaProducersWithCost(game);
                    for (Permanent perm : manaPermsWithCost) {
                        for (ActivatedManaAbilityImpl manaAbility : perm.getAbilities().getAvailableActivatedManaAbilities(Zone.BATTLEFIELD, getId(), game)) {
                            if (hasAbilityTargetNameOrAlias(game, manaAbility, groups[0])
                                    && manaAbility.canActivate(computerPlayer.getId(), game).canActivate()) {
                                Ability newManaAbility = manaAbility.copy();
                                computerPlayer.activateAbility((ActivatedAbility) newManaAbility, game);
                                actions.remove(action);
                                return true;
                            }
                        }
                    }
                    printStart("Available for " + this.getName());
                    printAbilities(game, this.getPlayable(game, true));
                    printEnd();
                    // TODO: enable assert and rewrite failed activateManaAbility tests
                    //  (must use checkAbility instead multiple mana calls)
                    LOGGER.warn("WARNING, test must be rewritten to use checkAbility instead multiple mana calls");
                    //Assert.fail("Can't find mana ability to activate command: " + command);
                } else if (action.getAction().startsWith("addCounters:")) {
                    String command = action.getAction();
                    command = command.substring(command.indexOf("addCounters:") + 12);
                    String[] groups = command.split("\\$");
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents()) {
                        if (hasObjectTargetNameOrAlias(permanent, groups[0])) {
                            CounterType counterType = CounterType.findByName(groups[1]);
                            Assert.assertNotNull("Invalid counter type " + groups[1], counterType);
                            Counter counter = counterType.createInstance(Integer.parseInt(groups[2]));
                            permanent.addCounters(counter, source.getControllerId(), source, game);
                            actions.remove(action);
                            return true;
                        }
                    }
                } else if (action.getAction().startsWith("waitStackResolved")) {
                    boolean skipOneStackObjectOnly = action.getAction().equals("waitStackResolved:1");
                    if (game.getStack().isEmpty()) {
                        // all done, can use next command
                        actions.remove(action);
                        continue;
                    } else {
                        // need to wait (don't remove command, except one skip only)
                        tryToPlayPriority(game);
                        if (skipOneStackObjectOnly) {
                            actions.remove(action);
                        }
                        return true;
                    }
                } else if (action.getAction().startsWith("playerAction:")) {
                    String command = action.getAction();
                    command = command.substring(command.indexOf("playerAction:") + 13);
                    groupsForTargetHandling = null;
                    String[] groups = command.split("\\$");
                    if (groups.length > 0) {
                        if (groups[0].equals("Rollback")) {
                            if (groups.length > 2 && groups[1].startsWith("turns=") && groups[2].startsWith("rollbackBlock=")) {
                                int turns = Integer.parseInt(groups[1].substring(6));
                                int rollbackBlockNumber = Integer.parseInt(groups[2].substring(14));
                                game.rollbackTurns(turns);
                                actions.remove(action);
                                addActionsAfterRollback(game, rollbackBlockNumber);
                                return true;
                            } else {
                                Assert.fail("Rollback command misses parameter: " + command);
                            }
                        }
                        if (groups[0].equals("Concede")) {
                            game.concede(getId());
                            ((GameImpl) game).checkConcede();
                            actions.remove(action);
                            return true;
                        }
                    }
                } else if (action.getAction().startsWith(AI_PREFIX)) {
                    String command = action.getAction();
                    command = command.substring(command.indexOf(AI_PREFIX) + AI_PREFIX.length());

                    // play priority
                    if (command.equals(AI_COMMAND_PLAY_PRIORITY)) {
                        AIRealGameSimulation = true; // disable on action's remove
                        computerPlayer.priority(game);
                        actions.remove(action);
                        return true;
                    }

                    // play step
                    if (command.equals(AI_COMMAND_PLAY_STEP)) {
                        AIRealGameSimulation = true; // disable on action's remove
                        actionsToRemoveLater.put(action, game.getTurnStepType());
                        computerPlayer.priority(game);
                        return true;
                    }

                    Assert.fail("Unknown ai command: " + command);
                } else if (action.getAction().startsWith(RUN_PREFIX)) {
                    String command = action.getAction();
                    command = command.substring(command.indexOf(RUN_PREFIX) + RUN_PREFIX.length());

                    // custom code execute
                    if (command.equals(RUN_COMMAND_CODE)) {
                        action.getCodePayload().run(action.getActionName(), computerPlayer, game);
                        actions.remove(action);
                        return true;
                    }

                    Assert.fail("Unknown run command: " + command);
                } else if (action.getAction().startsWith(CHECK_PREFIX)) {
                    String command = action.getAction();
                    command = command.substring(command.indexOf(CHECK_PREFIX) + CHECK_PREFIX.length());

                    String[] params = command.split(CHECK_PARAM_DELIMETER);
                    boolean wasProccessed = false;
                    if (params.length > 0) {

                        // check PT: card name, P, T
                        if (params[0].equals(CHECK_COMMAND_PT) && params.length == 4) {
                            assertPT(action, game, computerPlayer, params[1], Integer.parseInt(params[2]), Integer.parseInt(params[3]));
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // check damage: card name, damage
                        if (params[0].equals(CHECK_COMMAND_DAMAGE) && params.length == 3) {
                            assertDamage(action, game, computerPlayer, params[1], Integer.parseInt(params[2]));
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // check life: life
                        if (params[0].equals(CHECK_COMMAND_LIFE) && params.length == 2) {
                            assertLife(action, game, computerPlayer, Integer.parseInt(params[1]));
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // check player in game: target player, must be in game
                        if (params[0].equals(CHECK_COMMAND_PLAYER_IN_GAME) && params.length == 3) {
                            assertPlayerInGame(action, game, game.getPlayer(UUID.fromString(params[1])), Boolean.parseBoolean(params[2]));
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // check ability: card name, ability class, must have
                        if (params[0].equals(CHECK_COMMAND_ABILITY) && params.length == 4) {
                            assertAbility(action, game, computerPlayer, params[1], params[2], Boolean.parseBoolean(params[3]));
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // check playable ability: ability text, must have
                        if (params[0].equals(CHECK_COMMAND_PLAYABLE_ABILITY) && params.length == 3) {
                            assertPlayableAbility(action, game, computerPlayer, params[1], Boolean.parseBoolean(params[2]));
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // check battlefield count: target player, card name, count
                        if (params[0].equals(CHECK_COMMAND_PERMANENT_COUNT) && params.length == 4) {
                            assertPermanentCount(action, game, game.getPlayer(UUID.fromString(params[1])), params[2], Integer.parseInt(params[3]));
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // check permanent tapped count: target player, card name, tapped status, count
                        if (params[0].equals(CHECK_COMMAND_PERMANENT_TAPPED) && params.length == 5) {
                            assertPermanentTapped(action, game, game.getPlayer(UUID.fromString(params[1])), params[2], Boolean.parseBoolean(params[3]), Integer.parseInt(params[4]));
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // check permanent counters: card name, counter type, count
                        if (params[0].equals(CHECK_COMMAND_PERMANENT_COUNTERS) && params.length == 4) {
                            assertPermanentCounters(action, game, computerPlayer, params[1], CounterType.findByName(params[2]), Integer.parseInt(params[3]));
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // check card counters: card name, counter type, count
                        if (params[0].equals(CHECK_COMMAND_CARD_COUNTERS) && params.length == 4) {
                            assertCardCounters(action, game, computerPlayer, params[1], CounterType.findByName(params[2]), Integer.parseInt(params[3]));
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // check exile count: card name, count
                        if (params[0].equals(CHECK_COMMAND_EXILE_COUNT) && params.length == 3) {
                            assertExileCount(action, game, params[1], Integer.parseInt(params[2]));
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // check graveyard count: card name, count
                        if (params[0].equals(CHECK_COMMAND_GRAVEYARD_COUNT) && params.length == 3) {
                            assertGraveyardCount(action, game, computerPlayer, params[1], Integer.parseInt(params[2]));
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // check library count: card name, count
                        if (params[0].equals(CHECK_COMMAND_LIBRARY_COUNT) && params.length == 3) {
                            assertLibraryCount(action, game, computerPlayer, params[1], Integer.parseInt(params[2]));
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // check hand count: count
                        if (params[0].equals(CHECK_COMMAND_HAND_COUNT) && params.length == 2) {
                            assertHandCount(action, game, computerPlayer, Integer.parseInt(params[1]));
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // check hand card count: card name, count
                        if (params[0].equals(CHECK_COMMAND_HAND_CARD_COUNT) && params.length == 3) {
                            assertHandCardCount(action, game, computerPlayer, params[1], Integer.parseInt(params[2]));
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // check command card count: card name, count
                        if (params[0].equals(CHECK_COMMAND_COMMAND_CARD_COUNT) && params.length == 3) {
                            assertCommandCardCount(action, game, computerPlayer, params[1], Integer.parseInt(params[2]));
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // check color: card name, colors, must have
                        if (params[0].equals(CHECK_COMMAND_COLOR) && params.length == 4) {
                            assertColor(action, game, computerPlayer, params[1], params[2], Boolean.parseBoolean(params[3]));
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // check type: card name, type, must have
                        if (params[0].equals(CHECK_COMMAND_TYPE) && params.length == 4) {
                            assertType(action, game, computerPlayer, params[1], CardType.fromString(params[2]), Boolean.parseBoolean(params[3]));
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // check subtype: card name, subtype, must have
                        if (params[0].equals(CHECK_COMMAND_SUBTYPE) && params.length == 4) {
                            assertSubType(action, game, computerPlayer, params[1], SubType.fromString(params[2]), Boolean.parseBoolean(params[3]));
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // check mana pool: colors, amount
                        if (params[0].equals(CHECK_COMMAND_MANA_POOL) && params.length == 3) {
                            assertManaPool(action, game, computerPlayer, params[1], Integer.parseInt(params[2]));
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // check alias at zone: alias name, zone, must have (only for TestPlayer)
                        if (params[0].equals(CHECK_COMMAND_ALIAS_ZONE) && params.length == 4) {
                            assertAliasZone(action, game, this, params[1], Zone.valueOf(params[2]), Boolean.parseBoolean(params[3]));
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // check stack size: need size
                        if (params[0].equals(CHECK_COMMAND_STACK_SIZE) && params.length == 2) {
                            assertStackSize(action, game, Integer.parseInt(params[1]));
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // check stack object: stack ability name, amount
                        if (params[0].equals(CHECK_COMMAND_STACK_OBJECT) && params.length == 3) {
                            assertStackObject(action, game, params[1], Integer.parseInt(params[2]));
                            actions.remove(action);
                            wasProccessed = true;
                        }
                    }
                    if (wasProccessed) {
                        return true;
                    } else {
                        Assert.fail("Unknown check command or params: " + command);
                    }
                } else if (action.getAction().startsWith(SHOW_PREFIX)) {
                    String command = action.getAction();
                    command = command.substring(command.indexOf(SHOW_PREFIX) + SHOW_PREFIX.length());

                    String[] params = command.split(CHECK_PARAM_DELIMETER);
                    boolean wasProccessed = false;
                    if (params.length > 0) {

                        // show library
                        if (params[0].equals(SHOW_COMMAND_LIBRARY) && params.length == 1) {
                            printStart(action.getActionName());
                            printCards(computerPlayer.getLibrary().getCards(game), false); // do not sort
                            printEnd();
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // show hand
                        if (params[0].equals(SHOW_COMMAND_HAND) && params.length == 1) {
                            printStart(action.getActionName());
                            printCards(computerPlayer.getHand().getCards(game));
                            printEnd();
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // show command
                        if (params[0].equals(SHOW_COMMAND_COMMAND) && params.length == 1) {
                            printStart(action.getActionName());
                            CardsImpl cards = new CardsImpl(game.getCommandersIds(computerPlayer, CommanderCardType.ANY, false));
                            printCards(cards.getCards(game));
                            printEnd();
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // show battlefield
                        if (params[0].equals(SHOW_COMMAND_BATTLEFIELD) && params.length == 1) {
                            printStart(action.getActionName());
                            printPermanents(game, game.getBattlefield().getAllActivePermanents(computerPlayer.getId()));
                            printEnd();
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // show graveyard
                        if (params[0].equals(SHOW_COMMAND_GRAVEYARD) && params.length == 1) {
                            printStart(action.getActionName());
                            printCards(computerPlayer.getGraveyard().getCards(game));
                            printEnd();
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // show exile
                        if (params[0].equals(SHOW_COMMAND_EXILE) && params.length == 1) {
                            printStart(action.getActionName());
                            printCards(game.getExile().getAllCards(game).stream()
                                    .filter(card -> card.isOwnedBy(computerPlayer.getId()))
                                    .collect(Collectors.toList()), true);
                            printEnd();
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // show available abilities
                        if (params[0].equals(SHOW_COMMAND_AVAILABLE_ABILITIES) && params.length == 1) {
                            printStart(action.getActionName());
                            printAbilities(game, computerPlayer.getPlayable(game, true));
                            printEnd();
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // show available mana
                        if (params[0].equals(SHOW_COMMAND_AVAILABLE_MANA) && params.length == 1) {
                            printStart(action.getActionName());
                            printMana(game, computerPlayer.getManaAvailable(game));
                            printEnd();
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // show aliases
                        if (params[0].equals(SHOW_COMMAND_ALIASES) && params.length == 1) {
                            printStart(action.getActionName());
                            printAliases(game, this);
                            printEnd();
                            actions.remove(action);
                            wasProccessed = true;
                        }

                        // show stack
                        if (params[0].equals(SHOW_COMMAND_STACK) && params.length == 1) {
                            printStart(action.getActionName());
                            printStack(game);
                            printEnd();
                            actions.remove(action);
                            wasProccessed = true;
                        }
                    }

                    if (wasProccessed) {
                        return true;
                    } else {
                        Assert.fail("Unknown show command or params: " + command);
                    }
                }

                // you don't need to use stack command all the time, so some cast commands can be skiped to next check
                if (game.getStack().isEmpty()) {
                    this.chooseStrictModeFailed("cast/activate", game,
                            "Can't find available command - " + action.getAction() + " (use checkPlayableAbility for \"non available\" checks)", true);
                }
            } // turn/step
        }

        tryToPlayPriority(game);

        // check to prevent endless loops
        if (numberOfActions == actions.size()) {
            foundNoAction++;
            if (foundNoAction > maxCallsWithoutAction) {
                throw new AssertionError("Too much priority calls to " + getName()
                        + " without taking any action than allowed (" + maxCallsWithoutAction + ") on turn " + game.getTurnNum());
            }
        } else {
            foundNoAction = 0;
        }
        return false;
    }

    /**
     * Adds actions to the player actions after an executed rollback Actions
     * have to be added after the rollback because otherwise the actions are
     * not valid because other ot the same actions are already taken before the
     * rollback.
     *
     * @param game
     * @param rollbackBlockNumber rollback block to add the actions for
     */
    private void addActionsAfterRollback(Game game, int rollbackBlockNumber) {
        Map<UUID, ArrayList<PlayerAction>> rollbackBlock = rollbackActions.get(rollbackBlockNumber);
        if (rollbackBlock != null && !rollbackBlock.isEmpty()) {
            for (Map.Entry<UUID, ArrayList<PlayerAction>> entry : rollbackBlock.entrySet()) {
                TestPlayer testPlayer = (TestPlayer) game.getPlayer(entry.getKey());
                if (testPlayer != null) {
                    // Add the actions at the start of the action list
                    int pos = 0;
                    for (PlayerAction playerAction : entry.getValue()) {
                        testPlayer.getActions().add(pos, playerAction);
                        pos++;
                    }
                }
            }
        }
    }

    private void tryToPlayPriority(Game game) {
        if (AIPlayer) {
            computerPlayer.priority(game);
        } else {
            computerPlayer.pass(game);
        }
    }

    private Permanent findPermanentWithAssert(PlayerAction action, Game game, Player player, String cardName) {
        for (Permanent perm : game.getBattlefield().getAllPermanents()) {
            // need by controller
            if (!perm.getControllerId().equals(player.getId())) {
                continue;
            }

            // need by alias or by name
            if (!hasObjectTargetNameOrAlias(perm, cardName)) {
                continue;
            }

            // all fine
            return perm;
        }
        Assert.fail(action.getActionName() + " - can''t find permanent to check: " + cardName);
        return null;
    }

    private void printStart(String name) {
        System.out.println("\n" + name + ":");
    }

    private void printEnd() {
        System.out.println();
    }

    private void printCards(Set<Card> cards) {
        printCards(new ArrayList<>(cards), true);
    }

    private void printCards(List<Card> cards, boolean sorted) {
        System.out.println("Total cards: " + cards.size());

        List<String> data;
        if (sorted) {
            data = cards.stream()
                    .map(Card::getIdName)
                    .sorted()
                    .collect(Collectors.toList());
        } else {
            data = cards.stream()
                    .map(Card::getIdName)
                    .collect(Collectors.toList());
        }

        for (String s : data) {
            System.out.println(s);
        }
    }

    private String getPrintableAliases(String prefix, UUID objectId, String postfix) {
        // [@al.1, @al.2, @al.3]
        List<String> aliases = new ArrayList<>();
        this.aliases.forEach((name, id) -> {
            if (id.equals(objectId)) {
                aliases.add(ALIAS_PREFIX + name);
            }
        });

        if (aliases.size() > 0) {
            return prefix + String.join(", ", aliases) + postfix;
        } else {
            return "";
        }
    }

    private void printPermanents(Game game, List<Permanent> cards) {
        System.out.println("Total permanents: " + cards.size());

        List<String> data = cards.stream()
                .map(c -> (((c instanceof PermanentToken) ? "[T] " : "[C] ")
                        + c.getIdName()
                        + (c.isCopy() ? " [copy of " + c.getCopyFrom().getId().toString().substring(0, 3) + "]" : "")
                        + " - " + c.getPower().getValue() + "/" + c.getToughness().getValue()
                        + (c.isPlaneswalker(game) ? " - L" + c.getCounters(game).getCount(CounterType.LOYALTY) : "")
                        + ", " + (c.isTapped() ? "Tapped" : "Untapped")
                        + getPrintableAliases(", [", c.getId(), "]")
                        + (c.getAttachedTo() == null ? "" : ", attached to " + game.getPermanent(c.getAttachedTo()).getIdName())))
                .sorted()
                .collect(Collectors.toList());

        for (String s : data) {
            System.out.println(s);
        }
    }

    private void printMana(Game game, ManaOptions manaOptions) {
        System.out.println("Total mana options: " + manaOptions.size());
        manaOptions.forEach(mana -> {
            System.out.println(mana.toString());
        });
    }

    private void printAbilities(Game game, List<? extends Ability> abilities) {
        printAbilities("Total abilities", game, abilities);
    }

    private void printAbilities(String info, Game game, List<? extends Ability> abilities) {
        System.out.println(info + ": " + (abilities != null ? abilities.size() : 0));
        if (abilities == null) {
            return;
        }

        List<String> data = abilities.stream()
                .map(a -> (a.getZone() + " -> "
                        + a.getSourceObject(game).getIdName() + " -> "
                        + (a.toString().startsWith("Cast ") ? "[" + a.getManaCostsToPay().getText() + "] -> " : "") // printed cost, not modified
                        + (a.toString().length() > 0
                        ? a.toString().substring(0, Math.min(40, a.toString().length())) + "..."
                        : a.getClass().getSimpleName())
                ))
                .sorted()
                .collect(Collectors.toList());

        for (String s : data) {
            System.out.println(s);
        }
    }

    private String getAliasInfo(Game game, TestPlayer player, String aliasName) {
        MageItem item = findAliasObject(game, player, aliasName);
        if (item == null) {
            return aliasName + " [not exists]";
        }

        if (item instanceof MageObject) {
            Zone zone = game.getState().getZone(item.getId());
            return aliasName + " - " + ((MageObject) item).getIdName() + " - " + (zone != null ? zone.toString() : "null");
        }

        if (item instanceof Player) {
            return aliasName + " - " + ((Player) item).getName();
        }

        return aliasName + " [unknown object " + item.getId() + "]";
    }

    private void printAliases(Game game, TestPlayer player) {
        System.out.println("Total aliases: " + player.getAliases().size());

        List<String> data = player.getAliases().entrySet().stream()
                .map(entry -> (getAliasInfo(game, player, entry.getKey())))
                .sorted()
                .collect(Collectors.toList());

        for (String s : data) {
            System.out.println(s);
        }
    }

    private void printStack(Game game) {
        System.out.println("Stack objects: " + game.getStack().size());
        game.getStack().forEach(stack -> {
            System.out.println(stack.getStackAbility().toString());
        });
    }

    private void assertPT(PlayerAction action, Game game, Player player, String permanentName, int Power, int Toughness) {
        Permanent perm = findPermanentWithAssert(action, game, player, permanentName);

        Assert.assertEquals(action.getActionName() + " - permanent " + permanentName + " have wrong power: " + perm.getPower().getValue() + " <> " + Power,
                Power, perm.getPower().getValue());
        Assert.assertEquals(action.getActionName() + " - permanent " + permanentName + " have wrong toughness: " + perm.getToughness().getValue() + " <> " + Toughness,
                Toughness, perm.getToughness().getValue());
    }

    private void assertDamage(PlayerAction action, Game game, Player player, String permanentName, int damage) {
        Permanent perm = findPermanentWithAssert(action, game, player, permanentName);

        Assert.assertEquals(action.getActionName() + " - permanent " + permanentName + " have wrong damage: " + perm.getDamage() + " <> " + damage, damage, perm.getDamage());
    }

    private void assertLife(PlayerAction action, Game game, Player player, int Life) {
        Assert.assertEquals(action.getActionName() + " - " + player.getName() + " have wrong life: " + player.getLife() + " <> " + Life,
                Life, player.getLife());
    }

    private void assertPlayerInGame(PlayerAction action, Game game, Player targetPlayer, boolean mustBeInGame) {
        Assert.assertNotNull("Can't find target player", targetPlayer);

        if (targetPlayer.isInGame() && !mustBeInGame) {
            Assert.fail(action.getActionName() + " - player " + targetPlayer.getName() + " must NOT be in game");
        }

        if (!targetPlayer.isInGame() && mustBeInGame) {
            Assert.fail(action.getActionName() + " - player " + targetPlayer.getName() + " must be in game");
        }
    }

    private void assertAbility(PlayerAction action, Game game, Player player, String permanentName, String abilityClass, boolean mustHave) {
        Permanent perm = findPermanentWithAssert(action, game, player, permanentName);

        boolean found = false;
        for (Ability ability : perm.getAbilities(game)) {
            if (ability.getClass().getName().equals(abilityClass)) {
                found = true;
                break;
            }
        }

        if (mustHave) {
            Assert.assertEquals(action.getActionName() + " - permanent " + permanentName + " must have the ability " + abilityClass, true, found);
        } else {
            Assert.assertEquals(action.getActionName() + " - permanent " + permanentName + " must not have the ability " + abilityClass, false, found);
        }
    }

    private void assertPlayableAbility(PlayerAction action, Game game, Player player, String abilityStartText, boolean mustHave) {
        boolean found = false;
        for (Ability ability : computerPlayer.getPlayable(game, true)) {
            if (ability.toString().startsWith(abilityStartText)) {
                found = true;
                break;
            }
        }

        if (mustHave && !found) {
            printStart("Available mana for " + computerPlayer.getName());
            printMana(game, computerPlayer.getManaAvailable(game));
            printStart(action.getActionName());
            printAbilities(game, computerPlayer.getPlayable(game, true));
            printEnd();
            Assert.fail("Must have playable ability, but not found: " + abilityStartText);
        }

        if (!mustHave && found) {
            printStart("Available mana for " + computerPlayer.getName());
            printMana(game, computerPlayer.getManaAvailable(game));
            printStart(action.getActionName());
            printAbilities(game, computerPlayer.getPlayable(game, true));
            printEnd();
            Assert.fail("Must not have playable ability, but found: " + abilityStartText);
        }
    }

    private void assertPermanentCount(PlayerAction action, Game game, Player player, String permanentName, int count) {
        int foundCount = 0;
        for (Permanent perm : game.getBattlefield().getAllPermanents()) {
            if (hasObjectTargetNameOrAlias(perm, permanentName) && perm.getControllerId().equals(player.getId())) {
                foundCount++;
            }
        }

        if (foundCount != count) {
            printStart("Permanents of " + player.getName());
            printPermanents(game, game.getBattlefield().getAllActivePermanents(player.getId()));
            printEnd();
            Assert.fail(action.getActionName() + " - permanent " + permanentName + " must exists in " + count + " instances, but found " + foundCount);
        }
    }

    private void assertPermanentTapped(PlayerAction action, Game game, Player player, String permanentName, boolean tapped, int count) {
        int foundCount = 0;
        for (Permanent perm : game.getBattlefield().getAllPermanents()) {
            if (hasObjectTargetNameOrAlias(perm, permanentName)
                    && perm.getControllerId().equals(player.getId())
                    && perm.isTapped() == tapped) {
                foundCount++;
            }
        }

        if (foundCount != count) {
            printStart("Permanents of " + player.getName());
            printPermanents(game, game.getBattlefield().getAllActivePermanents(player.getId()));
            printEnd();
            Assert.fail(action.getActionName() + " - must have " + count + (tapped ? " tapped " : " untapped ")
                    + "permanents with name " + permanentName + ", but found " + foundCount);
        }
    }

    private void assertPermanentCounters(PlayerAction action, Game game, Player player, String permanentName, CounterType counterType, int count) {
        int foundCount = 0;
        for (Permanent perm : game.getBattlefield().getAllPermanents()) {
            if (hasObjectTargetNameOrAlias(perm, permanentName) && perm.getControllerId().equals(player.getId())) {
                foundCount = perm.getCounters(game).getCount(counterType);
            }
        }

        Assert.assertEquals(action.getActionName() + " - permanent " + permanentName + " must have " + count + " " + counterType.toString(), count, foundCount);
    }

    private void assertCardCounters(PlayerAction action, Game game, Player player, String cardName, CounterType counterType, int count) {
        int foundCount = 0;

        Set<Card> allCards = new HashSet<>();

        // collect possible cards from visible zones except library
        allCards.addAll(player.getHand().getCards(game));
        allCards.addAll(player.getGraveyard().getCards(game));
        allCards.addAll(game.getExile().getAllCards(game));

        for (Card card : allCards) {
            if (hasObjectTargetNameOrAlias(card, cardName) && card.getOwnerId().equals(player.getId())) {
                foundCount = card.getCounters(game).getCount(counterType);
            }
        }

        Assert.assertEquals(action.getActionName() + " - card " + cardName + " must have " + count + " " + counterType.toString(), count, foundCount);
    }

    private void assertExileCount(PlayerAction action, Game game, String permanentName, int count) {
        int foundCount = 0;
        for (Card card : game.getExile().getAllCards(game)) {
            if (hasObjectTargetNameOrAlias(card, permanentName)) {
                foundCount++;
            }
        }

        if (foundCount != count) {
            printStart("Exile cards");
            printCards(game.getExile().getAllCards(game), true);
            printEnd();
            Assert.fail(action.getActionName() + " - exile zone must have " + count + " cards with name " + permanentName + ", but found " + foundCount);
        }
    }

    private void assertGraveyardCount(PlayerAction action, Game game, Player player, String permanentName, int count) {
        int foundCount = 0;
        for (Card card : player.getGraveyard().getCards(game)) {
            if (hasObjectTargetNameOrAlias(card, permanentName) && card.isOwnedBy(player.getId())) {
                foundCount++;
            }
        }

        if (foundCount != count) {
            printStart("Graveyard of " + player.getName());
            printCards(player.getGraveyard().getCards(game));
            printEnd();
            Assert.fail(action.getActionName() + " - graveyard zone must have " + count + " cards with name " + permanentName + ", but found " + foundCount);
        }
    }

    private void assertLibraryCount(PlayerAction action, Game game, Player player, String permanentName, int count) {
        int foundCount = 0;
        for (Card card : player.getLibrary().getCards(game)) {
            if (hasObjectTargetNameOrAlias(card, permanentName)) {
                foundCount++;
            }
        }

        Assert.assertEquals(action.getActionName() + " - card " + permanentName + " must exists in library with " + count + " instances", count, foundCount);
    }

    private void assertHandCount(PlayerAction action, Game game, Player player, int count) {
        if (player.getHand().size() != count) {
            printStart("Hand of " + player.getName());
            printCards(player.getHand().getCards(game));
            printEnd();
            Assert.fail(action.getActionName() + " - hand must contain " + count + ", but found " + player.getHand().size());
        }
    }

    private void assertHandCardCount(PlayerAction action, Game game, Player player, String cardName, int count) {
        int realCount = 0;
        for (UUID cardId : player.getHand()) {
            Card card = game.getCard(cardId);
            if (hasObjectTargetNameOrAlias(card, cardName)) {
                realCount++;
            }
        }

        Assert.assertEquals(action.getActionName() + " - hand must contain " + count + " cards of " + cardName, count, realCount);
    }

    private void assertCommandCardCount(PlayerAction action, Game game, Player player, String cardName, int count) {
        int realCount = 0;
        for (UUID cardId : game.getCommandersIds(player, CommanderCardType.ANY, false)) {
            Card card = game.getCard(cardId);
            if (hasObjectTargetNameOrAlias(card, cardName) && Zone.COMMAND.equals(game.getState().getZone(cardId))) {
                realCount++;
            }
        }

        if (realCount != count) {
            printStart("Cards in command zone from " + player.getName());
            printCards(game.getCommanderCardsFromCommandZone(player, CommanderCardType.COMMANDER_OR_OATHBREAKER));
            printEnd();
            Assert.fail(action.getActionName() + " - must have " + count + " cards with name " + cardName + ", but found " + realCount);
        }
    }

    private void assertColor(PlayerAction action, Game game, Player player, String permanentName, String colors, boolean mustHave) {
        Assert.assertNotEquals(action.getActionName() + " - must setup colors", "", colors);

        Permanent card = findPermanentWithAssert(action, game, player, permanentName);
        ObjectColor cardColor = card.getColor(game);
        ObjectColor searchColors = new ObjectColor(colors);

        List<ObjectColor> colorsHave = new ArrayList<>();
        List<ObjectColor> colorsDontHave = new ArrayList<>();

        for (ObjectColor searchColor : searchColors.getColors()) {
            if (cardColor.shares(searchColor)) {
                colorsHave.add(searchColor);
            } else {
                colorsDontHave.add(searchColor);
            }
        }

        if (mustHave) {
            Assert.assertEquals(action.getActionName() + " - must contain colors [" + searchColors + "] but found only [" + cardColor.toString() + "]", 0, colorsDontHave.size());
        } else {
            Assert.assertEquals(action.getActionName() + " - must not contain colors [" + searchColors + "] but found [" + cardColor.toString() + "]", 0, colorsHave.size());
        }
    }

    private void assertType(PlayerAction action, Game game, Player player, String permanentName, CardType type, boolean mustHave) {

        Permanent perm = findPermanentWithAssert(action, game, player, permanentName);

        boolean found = false;
        for (CardType ct : perm.getCardType(game)) {
            if (ct.equals(type)) {
                found = true;
                break;
            }
        }

        if (mustHave) {
            Assert.assertEquals(action.getActionName() + " - permanent " + permanentName + " must have type " + type, true, found);
        } else {
            Assert.assertEquals(action.getActionName() + " - permanent " + permanentName + " must have not type " + type, false, found);
        }
    }

    private void assertSubType(PlayerAction action, Game game, Player player, String permanentName, SubType subType, boolean mustHave) {

        Permanent perm = findPermanentWithAssert(action, game, player, permanentName);

        boolean found = false;
        for (SubType st : perm.getSubtype(game)) {
            if (st.equals(subType)) {
                found = true;
                break;
            }
        }

        if (mustHave) {
            Assert.assertEquals(action.getActionName() + " - permanent " + permanentName + " must have subtype " + subType, true, found);
        } else {
            Assert.assertEquals(action.getActionName() + " - permanent " + permanentName + " must have not subtype " + subType, false, found);
        }
    }

    private MageItem findAliasObject(Game game, TestPlayer player, String aliasName) {
        UUID objectId = player.getAliasByName(aliasName);
        if (objectId == null) {
            return null;
        }

        MageObject itemObject = game.getObject(objectId);
        if (itemObject != null) {
            return itemObject;
        }

        Player itemPlayer = game.getPlayer(objectId);
        return itemPlayer;

    }

    private void assertAliasZone(PlayerAction action, Game game, TestPlayer player, String aliasName, Zone needZone, boolean mustHave) {
        MageItem item = findAliasObject(game, player, aliasName);
        Zone currentZone = (item == null ? null : game.getState().getZone(item.getId()));

        if (mustHave) {
            Assert.assertEquals(action.getActionName() + " - alias " + aliasName + " must have zone " + needZone.toString(), needZone, currentZone);
        } else {
            Assert.assertNotEquals(action.getActionName() + " - alias " + aliasName + " must have not zone " + needZone.toString(), needZone, currentZone);
        }
    }

    private void assertStackSize(PlayerAction action, Game game, int needStackSize) {
        if (game.getStack().size() != needStackSize) {
            printAbilities("Current stack", game, game.getStack().stream().map(StackObject::getStackAbility).collect(Collectors.toList()));
        }
        Assert.assertEquals(action.getActionName() + " - stack size must be " + needStackSize + " but is " + game.getStack().size(), needStackSize, game.getStack().size());
    }

    private void assertStackObject(PlayerAction action, Game game, String stackAbilityName, int needAmount) {
        long foundAmount = game.getStack()
                .stream()
                .filter(stack -> stack.getStackAbility().toString().startsWith(stackAbilityName))
                .count();
        if (needAmount != foundAmount) {
            printStack(game);
            Assert.fail(action.getActionName() + " - stack must have " + needAmount + " objects with ability [" + stackAbilityName + "] but have " + foundAmount);
        }
    }

    private void assertManaPoolInner(PlayerAction action, Player player, ManaType manaType, Integer amount) {
        Integer normal = player.getManaPool().getMana().get(manaType);
        Integer conditional = player.getManaPool().getConditionalMana().stream().mapToInt(a -> a.get(manaType)).sum(); // calcs FULL conditional mana, not real conditions
        Integer current = normal + conditional;
        Assert.assertEquals(action.getActionName() + " - mana pool must contain [" + amount.toString() + " " + manaType + "], but found [" + current + "]", amount, current);
    }

    private void assertManaPool(PlayerAction action, Game game, Player player, String colors, Integer amount) {
        Assert.assertNotEquals(action.getActionName() + " - must setup color", "", colors);

        // Can't use ObjectColor -- it's doesn't contain colorless -- need to use custom parse
        for (int i = 0; i < colors.length(); i++) {
            switch (colors.charAt(i)) {
                case 'W':
                    assertManaPoolInner(action, player, ManaType.WHITE, amount);
                    break;

                case 'U':
                    assertManaPoolInner(action, player, ManaType.BLUE, amount);
                    break;

                case 'B':
                    assertManaPoolInner(action, player, ManaType.BLACK, amount);
                    break;

                case 'R':
                    assertManaPoolInner(action, player, ManaType.RED, amount);
                    break;

                case 'G':
                    assertManaPoolInner(action, player, ManaType.GREEN, amount);
                    break;

                case 'C':
                    assertManaPoolInner(action, player, ManaType.COLORLESS, amount);
                    break;

                default:
                    Assert.fail(action.getActionName() + " - unknown color char [" + colors.charAt(i) + "]");
                    break;
            }
        }
    }


    /*
     *  Iterates through each player on the current turn and asserts if they can attack or block legally this turn
     */
    private void checkLegalMovesThisTurn(Game game) {
        // Each player is given priority before actual turns start for e.g. leylines and pre-game initialisation
        if (initialTurns < game.getPlayers().size()) {
            initialTurns++;
            return;
        }
        // Check actions for next turn are going to be valid
        int turnNum = game.getTurnNum();
        // Loop through all game players and check if they are allowed to attack/block this turn
        for (UUID playerID : game.getPlayers().keySet()) {
            Player player = game.getPlayer(playerID);
            // Has to be a TestPlayer to get a list of actions
            if (player instanceof TestPlayer) {
                // Check each player trying to attack or block on this turn
                for (PlayerAction playerAction : ((TestPlayer) player).getActions()) {
                    String action = playerAction.getAction();
                    boolean currentPlayersTurn = playerID.equals(getId());
                    String playerName = player.getName();
                    int actionTurnNum = playerAction.getTurnNum();
                    // If the action is performed on this turn...
                    if (turnNum == actionTurnNum) {
                        // Attacking and it's not their turn is illegal
                        if (action.startsWith("attack:") && !currentPlayersTurn) {
                            throw new UnsupportedOperationException(playerName + " can't attack on turn " + turnNum + " as it is not their turn");
                        }
                        // Blocking and it is their turn is illegal
                        if (action.startsWith("block:") && currentPlayersTurn) {
                            throw new UnsupportedOperationException(playerName + " can't block on turn " + turnNum + " as it is their turn");
                        }
                    }
                }
            }
        }
    }

    @Override
    public void selectAttackers(Game game, UUID attackingPlayerId) {
        // Loop through players and validate can attack/block this turn
        UUID defenderId = null;
        boolean mustAttackByAction = false;
        boolean madeAttackByAction = false;
        for (Iterator<org.mage.test.player.PlayerAction> it = actions.iterator(); it.hasNext(); ) {
            PlayerAction action = it.next();

            // aiXXX commands
            if (action.getTurnNum() == game.getTurnNum() && action.getAction().equals(AI_PREFIX + AI_COMMAND_PLAY_STEP)) {
                mustAttackByAction = true;
                madeAttackByAction = true;
                this.computerPlayer.selectAttackers(game, attackingPlayerId);
                // play step action will be removed on step end
                continue;
            }

            if (action.getTurnNum() == game.getTurnNum() && action.getAction().startsWith("attack:")) {
                mustAttackByAction = true;
                String command = action.getAction();
                command = command.substring(command.indexOf("attack:") + 7);

                // skip attack
                if (command.startsWith(ATTACK_SKIP)) {
                    it.remove();
                    madeAttackByAction = true;
                    break;
                }

                String[] groups = command.split("\\$");
                for (int i = 1; i < groups.length; i++) {
                    String group = groups[i];
                    if (group.startsWith("planeswalker=")) {
                        String planeswalkerName = group.substring(group.indexOf("planeswalker=") + 13);
                        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_PLANESWALKER, game)) {
                            if (hasObjectTargetNameOrAlias(permanent, planeswalkerName)) {
                                defenderId = permanent.getId();
                            }
                        }
                    }
                    if (group.startsWith("defendingPlayer=")) {
                        String defendingPlayerName = group.substring(group.indexOf("defendingPlayer=") + 16);
                        for (Player defendingPlayer : game.getPlayers().values()) {
                            if (defendingPlayer.getName().equals(defendingPlayerName)) {
                                defenderId = defendingPlayer.getId();
                                break;
                            }
                        }
                    }
                }
                if (defenderId == null) {
                    for (UUID uuid : game.getCombat().getDefenders()) {
                        Player defender = game.getPlayer(uuid);
                        if (defender != null) {
                            defenderId = uuid;
                        }
                    }
                }
                // First check to see if this controller actually owns the creature
                FilterControlledPermanent firstFilter = new FilterControlledPermanent();
                findPermanent(firstFilter, groups[0], computerPlayer.getId(), game);
                // Second check to filter creature for combat - less strict to workaround issue in #3038
                FilterCreatureForCombat secondFilter = new FilterCreatureForCombat();
                // secondFilter.add(Predicates.not(AttackingPredicate.instance));
                secondFilter.add(Predicates.not(SummoningSicknessPredicate.instance));
                // TODO: Cannot enforce legal attackers multiple times per combat. See issue #3038
                Permanent attacker = findPermanent(secondFilter, groups[0], computerPlayer.getId(), game, false);
                if (attacker != null && attacker.canAttack(defenderId, game)) {
                    computerPlayer.declareAttacker(attacker.getId(), defenderId, game, false);
                    it.remove();
                    madeAttackByAction = true;
                }
            }
        }

        if (mustAttackByAction && !madeAttackByAction) {
            this.chooseStrictModeFailed("attacker", game, "select attackers must use attack command but don't");
        }

        // AI FULL play if no actions available
        if (!mustAttackByAction && this.AIPlayer) {
            this.computerPlayer.selectAttackers(game, attackingPlayerId);
        }
    }

    @Override
    public List<UUID> getTurnControllers() {
        return computerPlayer.getTurnControllers();
    }

    @Override
    public void selectBlockers(Ability source, Game game, UUID defendingPlayerId) {
        List<PlayerAction> tempActions = new ArrayList<>(actions);

        UUID opponentId = game.getOpponents(computerPlayer.getId()).iterator().next();
        Map<MageObjectReference, List<MageObjectReference>> blockedCreaturesList = getBlockedCreaturesByCreatureList(game);

        boolean mustBlockByAction = false;
        for (PlayerAction action : tempActions) {

            // aiXXX commands
            if (action.getTurnNum() == game.getTurnNum() && action.getAction().equals(AI_PREFIX + AI_COMMAND_PLAY_STEP)) {
                mustBlockByAction = true;
                this.computerPlayer.selectBlockers(source, game, defendingPlayerId);
                // play step action will be removed on step end
                continue;
            }

            if (action.getTurnNum() == game.getTurnNum() && action.getAction().startsWith("block:")) {
                mustBlockByAction = true;
                String command = action.getAction();
                command = command.substring(command.indexOf("block:") + 6);

                // skip block
                if (command.startsWith(BLOCK_SKIP)) {
                    actions.remove(action);
                    break;
                }

                String[] groups = command.split("\\$");
                String blockerName = groups[0];
                String attackerName = groups[1];
                Permanent attacker = findPermanent(new FilterAttackingCreature(), attackerName, opponentId, game);
                Permanent blocker = findPermanent(new FilterControlledPermanent(), blockerName, computerPlayer.getId(), game);

                if (canBlockAnother(game, blocker, attacker, blockedCreaturesList)) {
                    computerPlayer.declareBlocker(defendingPlayerId, blocker.getId(), attacker.getId(), game);
                    actions.remove(action);
                } else {
                    throw new UnsupportedOperationException(blockerName + " cannot block " + attackerName + " it is already blocking the maximum amount of creatures.");
                }
            }
        }
        checkMultipleBlockers(game, blockedCreaturesList);

        // AI FULL play if no actions available
        if (!mustBlockByAction && this.AIPlayer) {
            this.computerPlayer.selectBlockers(source, game, defendingPlayerId);
        }
    }

    private Map<MageObjectReference, List<MageObjectReference>> getBlockedCreaturesByCreatureList(Game game) {
        // collect already declared blockers info (e.g. after auto-adding on block requirements)
        // blocker -> blocked attackers
        Map<MageObjectReference, List<MageObjectReference>> blockedCreaturesByCreature = new HashMap<>();
        for (CombatGroup combatGroup : game.getCombat().getGroups()) {
            for (UUID combatBlockerId : combatGroup.getBlockers()) {
                Permanent blocker = game.getPermanent(combatBlockerId);
                if (blocker != null) {
                    // collect all blocked attackers
                    List<MageObjectReference> blocked = getBlockedAttackers(game, blocker, blockedCreaturesByCreature);
                    for (UUID combatAttackerId : combatGroup.getAttackers()) {
                        Permanent combatAttacker = game.getPermanent(combatAttackerId);
                        if (combatAttacker != null) {
                            blocked.add(new MageObjectReference(combatAttacker, game));
                        }
                    }
                }
            }
        }
        return blockedCreaturesByCreature;
    }

    private List<MageObjectReference> getBlockedAttackers(Game game, Permanent blocker, Map<MageObjectReference, List<MageObjectReference>> blockedCreaturesByCreature) {
        // finds creatures list blocked by blocker permanent
        MageObjectReference blockerRef = new MageObjectReference(blocker, game);
        for (MageObjectReference r : blockedCreaturesByCreature.keySet()) {
            if (r.equals(blockerRef)) {
                // already exist
                blockerRef = r;
            }
        }

        if (!blockedCreaturesByCreature.containsKey(blockerRef)) {
            blockedCreaturesByCreature.put(blockerRef, new ArrayList<>());
        }
        List<MageObjectReference> blocked = blockedCreaturesByCreature.getOrDefault(blockerRef, new ArrayList<>());
        return blocked;
    }

    private boolean canBlockAnother(Game game, Permanent blocker, Permanent attacker, Map<MageObjectReference, List<MageObjectReference>> blockedCreaturesByCreature) {
        // check if blocker can block one more attacker and adds it
        List<MageObjectReference> blocked = getBlockedAttackers(game, blocker, blockedCreaturesByCreature);
        int numBlocked = blocked.size();

        // Can't block any more creatures
        if (++numBlocked > blocker.getMaxBlocks()) {
            return false;
        }

        // Add the attacker reference to the list of creatures this creature is blocking
        blocked.add(new MageObjectReference(attacker, game));
        return true;
    }

    private void checkMultipleBlockers(Game game, Map<MageObjectReference, List<MageObjectReference>> blockedCreaturesByCreature) {
        // Check for Menace type abilities - if creatures can be blocked by >X or <Y only

        // Stores the total number of blockers for each attacker
        Map<MageObjectReference, Integer> blockersForAttacker = new HashMap<>();

        // Calculate the number of blockers each attacker has
        for (List<MageObjectReference> attackers : blockedCreaturesByCreature.values()) {
            for (MageObjectReference mr : attackers) {
                Integer blockers = blockersForAttacker.getOrDefault(mr, 0);
                blockersForAttacker.put(mr, blockers + 1);
            }
        }

        // Check each attacker is blocked by an allowed amount of creatures
        for (Map.Entry<MageObjectReference, Integer> entry : blockersForAttacker.entrySet()) {
            Permanent attacker = entry.getKey().getPermanent(game);
            Integer blockers = entry.getValue();
            // If getMaxBlockedBy() == 0 it means any number of creatures can block this creature
            if (attacker.getMaxBlockedBy() != 0 && blockers > attacker.getMaxBlockedBy()) {
                throw new UnsupportedOperationException(attacker.getName() + " is blocked by " + blockers + " creature(s). It can only be blocked by " + attacker.getMaxBlockedBy() + " or less.");
            } else if (blockers < attacker.getMinBlockedBy()) {
                throw new UnsupportedOperationException(attacker.getName() + " is blocked by " + blockers + " creature(s). It has to be blocked by " + attacker.getMinBlockedBy() + " or more.");
            }
        }
        // No errors raised - all the blockers pass the test!
    }

    private String getInfo(MageObject o) {
        return "Object: " + (o != null ? o.getClass().getSimpleName() + ": " + o.getName() : "null");
    }

    private String getInfo(Ability o, Game game) {
        if (o != null) {
            MageObject object = o.getSourceObject(game);
            return "Ability: " + (object == null ? "" : object.getName() + " - " + o.getClass().getSimpleName() + ": " + o.getRule());
        }
        return "Ability: null";
    }

    private String getInfo(Target o) {
        return "Target: " + (o != null ? o.getClass().getSimpleName() + ": " + o.getMessage() : "null");
    }

    private void assertAliasSupportInChoices(boolean methodSupportAliases) {
        // TODO: add alias support for all false methods (replace name compare by isObjectHaveTargetNameOrAlias)
        if (!methodSupportAliases && !choices.isEmpty()) {
            if (choices.get(0).contains(ALIAS_PREFIX)) {
                Assert.fail("That choice method do not support aliases, but found " + choices.get(0));
            }
        }
    }

    private void assertAliasSupportInTargets(boolean methodSupportAliases) {
        // TODO: add alias support for all false methods (replace name compare by isObjectHaveTargetNameOrAlias)
        if (!methodSupportAliases && !targets.isEmpty()) {
            if (targets.get(0).contains(ALIAS_PREFIX)) {
                Assert.fail("That target method do not support aliases, but found " + targets.get(0));
            }
        }
    }

    private void chooseStrictModeFailed(String choiceType, Game game, String reason) {
        chooseStrictModeFailed(choiceType, game, reason, false);
    }

    private void chooseStrictModeFailed(String choiceType, Game game, String reason, boolean printAbilities) {
        if (!this.canChooseByComputer()) {
            if (printAbilities) {
                printStart("Available mana for " + computerPlayer.getName());
                printMana(game, computerPlayer.getManaAvailable(game));
                printStart("Available abilities for " + computerPlayer.getName());
                printAbilities(game, computerPlayer.getPlayable(game, true));
                printEnd();
            }
            if (choiceType.equals("choice")) {
                printStart("Unused choices");
                if (!choices.isEmpty()) {
                    System.out.println(String.join("\n", choices));
                }
                printEnd();
            }
            if (choiceType.equals("target")) {
                printStart("Unused targets");
                if (!targets.isEmpty()) {
                    System.out.println(String.join("\n", targets));
                }
                printEnd();
            }
            Assert.fail("Missing " + choiceType.toUpperCase(Locale.ENGLISH) + " def for"
                    + " turn " + game.getTurnNum()
                    + ", step " + (game.getStep() != null ? game.getTurnStepType().name() : "not started")
                    + ", " + this.getName()
                    + "\n" + reason);
        }
    }

    @Override
    public Mode chooseMode(Modes modes, Ability source, Game game) {
        if (!modesSet.isEmpty() && modes.getMaxModes(game, source) > modes.getSelectedModes().size()) {
            // set mode to null to select less than maximum modes if multiple modes are allowed
            if (modesSet.get(0) == null) {
                modesSet.remove(0);
                return null;
            }
            int needMode = Integer.parseInt(modesSet.get(0));
            int i = 1;
            for (Mode mode : modes.getAvailableModes(source, game)) {
                if (i == needMode) {
                    modesSet.remove(0);
                    return mode;
                }
                i++;
            }
        }
        if (modes.getMinModes() <= modes.getSelectedModes().size()) {
            return null;
        }

        StringBuilder modesInfo = new StringBuilder();
        modesInfo.append("\nAvailable modes:");
        int i = 1;
        for (Mode mode : modes.getAvailableModes(source, game)) {
            if (modesInfo.length() > 0) {
                modesInfo.append("\n");
            }
            modesInfo.append(String.format("%d: %s", i, mode.getEffects().getText(mode)));
            i++;
        }

        this.chooseStrictModeFailed("mode", game, getInfo(source, game) + modesInfo);
        return computerPlayer.chooseMode(modes, source, game);
    }

    @Override
    public boolean choose(Outcome outcome, Choice choice, Game game) {
        assertAliasSupportInChoices(false);

        if (!choices.isEmpty()) {
            String possibleChoice = choices.get(0);

            // skip choices
            if (possibleChoice.equals(CHOICE_SKIP)) {
                choices.remove(0);
                return true;
            }

            if (choice.setChoiceByAnswers(choices, true)) {
                return true;
            }

            assertWrongChoiceUsage(possibleChoice);
        }

        String choicesInfo;
        if (choice.isKeyChoice()) {
            choicesInfo = String.join("\n", choice.getKeyChoices().values());
        } else {
            choicesInfo = String.join("\n", choice.getChoices());
        }
        this.chooseStrictModeFailed("choice", game,
                "Message: " + choice.getMessage() + "\nPossible choices:\n" + choicesInfo);
        return computerPlayer.choose(outcome, choice, game);
    }

    @Override
    public int chooseReplacementEffect(Map<String, String> rEffects, Game game) {
        if (rEffects.size() <= 1) {
            return 0;
        }
        assertAliasSupportInChoices(false);
        if (!choices.isEmpty()) {
            String choice = choices.get(0);

            int index = 0;
            for (Map.Entry<String, String> entry : rEffects.entrySet()) {
                if (entry.getValue().startsWith(choice)) {
                    choices.remove(0);
                    return index;
                }
                index++;
            }

            assertWrongChoiceUsage(choice);
        }

        this.chooseStrictModeFailed("choice", game, String.join("\n", rEffects.values()));
        return computerPlayer.chooseReplacementEffect(rEffects, game);
    }

    @Override
    public boolean choose(Outcome outcome, Target target, Ability source, Game game, Map<String, Serializable> options) {
        UUID abilityControllerId = computerPlayer.getId();
        if (target.getTargetController() != null && target.getAbilityController() != null) {
            abilityControllerId = target.getAbilityController();
        }

        // ignore player select
        if (target.getMessage().equals("Select a starting player")) {
            return computerPlayer.choose(outcome, target, source, game, options);
        }

        assertAliasSupportInChoices(true);
        if (!choices.isEmpty()) {

            // skip choices
            if (choices.get(0).equals(CHOICE_SKIP)) {
                Assert.assertTrue("found skip choice, but it require more choices, needs "
                                + (target.getMinNumberOfTargets() - target.getTargets().size()) + " more",
                        target.getTargets().size() >= target.getMinNumberOfTargets());
                choices.remove(0);
                return true;
            }

            List<Integer> usedChoices = new ArrayList<>();
            List<UUID> usedTargets = new ArrayList<>();


            if ((target.getOriginalTarget() instanceof TargetPermanent)
                    || (target.getOriginalTarget() instanceof TargetPermanentOrPlayer)) { // player target not implemted yet
                FilterPermanent filterPermanent;
                if (target.getOriginalTarget() instanceof TargetPermanentOrPlayer) {
                    filterPermanent = ((TargetPermanentOrPlayer) target.getOriginalTarget()).getFilterPermanent();
                } else {
                    filterPermanent = ((TargetPermanent) target.getOriginalTarget()).getFilter();
                }
                for (String choiceRecord : choices) {
                    String[] targetList = choiceRecord.split("\\^");
                    boolean targetFound = false;
                    for (String targetName : targetList) {
                        boolean originOnly = false;
                        boolean copyOnly = false;
                        if (targetName.endsWith("]")) {
                            if (targetName.endsWith("[no copy]")) {
                                originOnly = true;
                                targetName = targetName.substring(0, targetName.length() - 9);
                            }
                            if (targetName.endsWith("[only copy]")) {
                                copyOnly = true;
                                targetName = targetName.substring(0, targetName.length() - 11);
                            }
                        }
                        for (Permanent permanent : game.getBattlefield().getActivePermanents(filterPermanent, abilityControllerId, source, game)) {
                            if (target.getTargets().contains(permanent.getId())) {
                                continue;
                            }
                            if (hasObjectTargetNameOrAlias(permanent, targetName)) {
                                if (target.isNotTarget() || target.canTarget(abilityControllerId, permanent.getId(), source, game)) {
                                    if ((permanent.isCopy() && !originOnly) || (!permanent.isCopy() && !copyOnly)) {
                                        target.add(permanent.getId(), game);
                                        targetFound = true;
                                        break;
                                    }
                                }
                            } else if ((permanent.getName() + '-' + permanent.getExpansionSetCode()).equals(targetName)) { // TODO: remove search by exp code?
                                if (target.isNotTarget() || target.canTarget(abilityControllerId, permanent.getId(), source, game)) {
                                    if ((permanent.isCopy() && !originOnly) || (!permanent.isCopy() && !copyOnly)) {
                                        target.add(permanent.getId(), game);
                                        targetFound = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (targetFound) {
                        choices.remove(choiceRecord);
                        return true;
                    }
                }
            }

            if (target instanceof TargetPlayer) {
                for (Player player : game.getPlayers().values()) {
                    for (String choose2 : choices) {
                        if (player.getName().equals(choose2)) {
                            if (target.canTarget(abilityControllerId, player.getId(), null, game) && !target.getTargets().contains(player.getId())) {
                                target.add(player.getId(), game);
                                choices.remove(choose2);
                                return true;
                            }
                        }
                    }
                }
            }

            // TODO: add same choices fixes for other target types (one choice must uses only one time for one target)
            if (target.getOriginalTarget() instanceof TargetCard) {
                // one choice per target
                // only unique targets
                //TargetCard targetFull = ((TargetCard) target);

                usedChoices.clear();
                usedTargets.clear();
                boolean targetCompleted = false;

                CheckAllChoices:
                for (int choiceIndex = 0; choiceIndex < choices.size(); choiceIndex++) {
                    String choiceRecord = choices.get(choiceIndex);
                    if (targetCompleted) {
                        break CheckAllChoices;
                    }

                    boolean targetFound = false;
                    String[] possibleChoices = choiceRecord.split("\\^");

                    CheckOneChoice:
                    for (String possibleChoice : possibleChoices) {
                        Set<UUID> possibleCards = target.possibleTargets(abilityControllerId, source, game);
                        CheckTargetsList:
                        for (UUID targetId : possibleCards) {
                            MageObject targetObject = game.getCard(targetId);
                            if (hasObjectTargetNameOrAlias(targetObject, possibleChoice)) {
                                if (target.canTarget(targetObject.getId(), game)) {
                                    // only unique targets
                                    if (usedTargets.contains(targetObject.getId())) {
                                        continue;
                                    }

                                    // OK, can use it
                                    target.add(targetObject.getId(), game);
                                    targetFound = true;
                                    usedTargets.add(targetObject.getId());

                                    // break on full targets list
                                    if (target.getTargets().size() >= target.getMaxNumberOfTargets()) {
                                        targetCompleted = true;
                                        break CheckOneChoice;
                                    }

                                    // restart search
                                    break CheckTargetsList;
                                }
                            }
                        }
                    }

                    if (targetFound) {
                        usedChoices.add(choiceIndex);
                    }
                }

                // apply only on ALL targets or revert
                if (usedChoices.size() > 0) {
                    if (target.isChosen()) {
                        // remove all used choices
                        for (int i = choices.size(); i >= 0; i--) {
                            if (usedChoices.contains(i)) {
                                choices.remove(i);
                            }
                        }
                        return true;
                    } else {
                        Assert.fail("Not full targets list.");
                        target.clearChosen();
                    }
                }
            }

            if (target.getOriginalTarget() instanceof TargetSource) {
                Set<UUID> possibleTargets;
                TargetSource t = ((TargetSource) target.getOriginalTarget());
                possibleTargets = t.possibleTargets(abilityControllerId, source, game);
                for (String choiceRecord : choices) {
                    String[] targetList = choiceRecord.split("\\^");
                    boolean targetFound = false;
                    for (String targetName : targetList) {
                        for (UUID targetId : possibleTargets) {
                            MageObject targetObject = game.getObject(targetId);
                            if (targetObject != null) {
                                if (hasObjectTargetNameOrAlias(targetObject, targetName)) {
                                    List<UUID> alreadyTargetted = target.getTargets();
                                    if (t.canTarget(targetObject.getId(), game)) {
                                        if (alreadyTargetted != null && !alreadyTargetted.contains(targetObject.getId())) {
                                            target.add(targetObject.getId(), game);
                                            choices.remove(choiceRecord);
                                            targetFound = true;
                                        }
                                    }
                                }
                            }
                            if (targetFound) {
                                choices.remove(choiceRecord);
                                return true;
                            }
                        }
                    }
                }
            }

            // TODO: enable fail checks and fix tests
            if (!target.getTargetName().equals("starting player")) {
                assertWrongChoiceUsage(choices.size() > 0 ? choices.get(0) : "empty list");
            }
        }

        this.chooseStrictModeFailed("choice", game, getInfo(game.getObject(source)) + ";\n" + getInfo(target));
        return computerPlayer.choose(outcome, target, source, game, options);
    }

    private void checkTargetDefinitionMarksSupport(Target needTarget, String targetDefinition, String canSupportChars) {
        // fail on wrong chars in definition    `   `   `   `   `   `   `   ``````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````
        // ^ - multiple targets
        // [] - special option like [no copy]
        // = - target type like targetPlayer=PlayerA
        boolean foundMulti = targetDefinition.contains("^");
        boolean foundSpecialStart = targetDefinition.contains("[");
        boolean foundSpecialClose = targetDefinition.contains("]");
        boolean foundEquals = targetDefinition.contains("=");

        boolean canMulti = canSupportChars.contains("^");
        boolean canSpecialStart = canSupportChars.contains("[");
        boolean canSpecialClose = canSupportChars.contains("]");
        boolean canEquals = canSupportChars.contains("=");

        // how to fix: change target definition for addTarget in test's code or update choose from targets implementation in TestPlayer
        if ((foundMulti && !canMulti) || (foundSpecialStart && !canSpecialStart) || (foundSpecialClose && !canSpecialClose) || (foundEquals && !canEquals)) {
            Assert.fail(this.getName() + " - Targets list was setup by addTarget with " + targets + ", but target definition [" + targetDefinition + "]"
                    + " is not supported by [" + canSupportChars + "] for target class " + needTarget.getClass().getSimpleName());
        }
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Target target, Ability source, Game game) {
        UUID abilityControllerId = computerPlayer.getId();
        if (target.getTargetController() != null && target.getAbilityController() != null) {
            abilityControllerId = target.getAbilityController();
        }
        UUID sourceId = source != null ? source.getSourceId() : null;

        assertAliasSupportInTargets(true);
        if (!targets.isEmpty()) {

            // skip targets
            if (targets.get(0).equals(TARGET_SKIP)) {
                Assert.assertTrue("found skip target, but it require more targets, needs "
                                + (target.getMinNumberOfTargets() - target.getTargets().size()) + " more",
                        target.getTargets().size() >= target.getMinNumberOfTargets());
                targets.remove(0);
                return true;
            }

            Set<Zone> targetCardZonesChecked = new HashSet<>(); // control miss implementation

            // player
            if (target.getOriginalTarget() instanceof TargetPlayer
                    || target.getOriginalTarget() instanceof TargetAnyTarget
                    || target.getOriginalTarget() instanceof TargetCreatureOrPlayer
                    || target.getOriginalTarget() instanceof TargetPermanentOrPlayer
                    || target.getOriginalTarget() instanceof TargetDefender) {
                for (String targetDefinition : targets) {
                    if (!targetDefinition.startsWith("targetPlayer=")) {
                        continue;
                    }
                    checkTargetDefinitionMarksSupport(target, targetDefinition, "=");
                    String playerName = targetDefinition.substring(targetDefinition.indexOf("targetPlayer=") + 13);
                    for (Player player : game.getPlayers().values()) {
                        if (player.getName().equals(playerName)
                                && target.canTarget(abilityControllerId, player.getId(), source, game)) {
                            target.addTarget(player.getId(), source, game);
                            targets.remove(targetDefinition);
                            return true;
                        }
                    }
                }
            }

            // permanent in battlefield
            if ((target.getOriginalTarget() instanceof TargetPermanent)
                    || (target.getOriginalTarget() instanceof TargetPermanentOrPlayer)
                    || (target.getOriginalTarget() instanceof TargetAnyTarget)
                    || (target.getOriginalTarget() instanceof TargetCreatureOrPlayer)
                    || (target.getOriginalTarget() instanceof TargetDefender)
                    || (target.getOriginalTarget() instanceof TargetPermanentOrSuspendedCard)) {
                for (String targetDefinition : targets) {
                    if (targetDefinition.startsWith("targetPlayer=")) {
                        continue;
                    }
                    checkTargetDefinitionMarksSupport(target, targetDefinition, "^[]");
                    String[] targetList = targetDefinition.split("\\^");
                    boolean targetFound = false;
                    for (String targetName : targetList) {
                        targetFound = false; // must have all valid targets from list
                        boolean originOnly = false;
                        boolean copyOnly = false;
                        if (targetName.endsWith("]")) {
                            if (targetName.endsWith("[no copy]")) {
                                originOnly = true;
                                targetName = targetName.substring(0, targetName.length() - 9);
                            }
                            if (targetName.endsWith("[only copy]")) {
                                copyOnly = true;
                                targetName = targetName.substring(0, targetName.length() - 11);
                            }
                        }
                        Filter filter = target.getOriginalTarget().getFilter();
                        if (filter instanceof FilterCreatureOrPlayer) {
                            filter = ((FilterCreatureOrPlayer) filter).getCreatureFilter();
                        }
                        if (filter instanceof FilterPermanentOrPlayer) {
                            filter = ((FilterPermanentOrPlayer) filter).getPermanentFilter();
                        }
                        if (filter instanceof FilterPlaneswalkerOrPlayer) {
                            filter = ((FilterPlaneswalkerOrPlayer) filter).getFilterPermanent();
                        }
                        if (filter instanceof FilterPermanentOrSuspendedCard) {
                            filter = ((FilterPermanentOrSuspendedCard) filter).getPermanentFilter();
                        }
                        for (Permanent permanent : game.getBattlefield().getActivePermanents((FilterPermanent) filter, abilityControllerId, source, game)) {
                            if (hasObjectTargetNameOrAlias(permanent, targetName) || (permanent.getName() + '-' + permanent.getExpansionSetCode()).equals(targetName)) { // TODO: remove exp code search?
                                if (target.canTarget(abilityControllerId, permanent.getId(), source, game) && !target.getTargets().contains(permanent.getId())) {
                                    if ((permanent.isCopy() && !originOnly) || (!permanent.isCopy() && !copyOnly)) {
                                        target.addTarget(permanent.getId(), source, game);
                                        targetFound = true;
                                        break; // return to next targetName
                                    }
                                }
                            }
                        }
                    }
                    if (targetFound) {
                        targets.remove(targetDefinition);
                        return true;
                    }
                }
            }

            // card in hand (only own hand supports here)
            // cards from non-own hand must be targeted through revealed cards
            if (target.getOriginalTarget() instanceof TargetCardInHand
                    || target.getOriginalTarget() instanceof TargetDiscard
                    || (target.getOriginalTarget() instanceof TargetCard && target.getOriginalTarget().getZone() == Zone.HAND)) {
                targetCardZonesChecked.add(Zone.HAND);
                for (String targetDefinition : targets) {
                    checkTargetDefinitionMarksSupport(target, targetDefinition, "^");
                    String[] targetList = targetDefinition.split("\\^");
                    boolean targetFound = false;
                    for (String targetName : targetList) {
                        for (Card card : computerPlayer.getHand().getCards(((TargetCard) target.getOriginalTarget()).getFilter(), game)) {
                            if (hasObjectTargetNameOrAlias(card, targetName) || (card.getName() + '-' + card.getExpansionSetCode()).equals(targetName)) { // TODO: remove set code search?
                                if (target.canTarget(abilityControllerId, card.getId(), source, game) && !target.getTargets().contains(card.getId())) {
                                    target.addTarget(card.getId(), source, game);
                                    targetFound = true;
                                    break; // return to next targetName
                                }
                            }
                        }
                    }
                    if (targetFound) {
                        targets.remove(targetDefinition);
                        return true;
                    }
                }
            }

            // card in exile
            if (target.getOriginalTarget() instanceof TargetCardInExile
                    || target.getOriginalTarget() instanceof TargetPermanentOrSuspendedCard
                    || (target.getOriginalTarget() instanceof TargetCard && target.getOriginalTarget().getZone() == Zone.EXILED)) {
                targetCardZonesChecked.add(Zone.EXILED);
                FilterCard filter = null;
                if (target.getOriginalTarget().getFilter() instanceof FilterCard) {
                    filter = (FilterCard) target.getOriginalTarget().getFilter();
                } else if (target.getOriginalTarget().getFilter() instanceof FilterPermanentOrSuspendedCard) {
                    filter = ((FilterPermanentOrSuspendedCard) target.getOriginalTarget().getFilter()).getCardFilter();
                }
                if (filter == null) {
                    Assert.fail("Unsupported exile target filter in TestPlayer: "
                            + target.getOriginalTarget().getClass().getCanonicalName());
                }

                for (String targetDefinition : targets) {
                    checkTargetDefinitionMarksSupport(target, targetDefinition, "^");
                    String[] targetList = targetDefinition.split("\\^");
                    boolean targetFound = false;
                    for (String targetName : targetList) {
                        for (Card card : game.getExile().getCards(filter, game)) {
                            if (hasObjectTargetNameOrAlias(card, targetName) || (card.getName() + '-' + card.getExpansionSetCode()).equals(targetName)) { // TODO: remove set code search?
                                if (target.canTarget(abilityControllerId, card.getId(), source, game) && !target.getTargets().contains(card.getId())) {
                                    target.addTarget(card.getId(), source, game);
                                    targetFound = true;
                                    break; // return to next targetName
                                }
                            }
                        }
                    }
                    if (targetFound) {
                        targets.remove(targetDefinition);
                        return true;
                    }
                }
            }

            // card in battlefield
            if (target instanceof TargetCardInGraveyardBattlefieldOrStack) {
                TargetCard targetFull = (TargetCard) target;
                for (String targetDefinition : targets) {
                    checkTargetDefinitionMarksSupport(target, targetDefinition, "^");
                    String[] targetList = targetDefinition.split("\\^");
                    boolean targetFound = false;
                    for (String targetName : targetList) {
                        for (Card card : game.getBattlefield().getAllActivePermanents()) {
                            if (hasObjectTargetNameOrAlias(card, targetName) || (card.getName() + '-' + card.getExpansionSetCode()).equals(targetName)) { // TODO: remove set code search?
                                if (targetFull.canTarget(abilityControllerId, card.getId(), source, game) && !targetFull.getTargets().contains(card.getId())) {
                                    targetFull.add(card.getId(), game);
                                    targetFound = true;
                                    break; // return to next targetName
                                }
                            }
                        }
                    }
                    if (targetFound) {
                        targets.remove(targetDefinition);
                        return true;
                    }
                }
            }

            // card in graveyard
            if (target.getOriginalTarget() instanceof TargetCardInOpponentsGraveyard
                    || target.getOriginalTarget() instanceof TargetCardInYourGraveyard
                    || target.getOriginalTarget() instanceof TargetCardInGraveyard
                    || target.getOriginalTarget() instanceof TargetCardInGraveyardBattlefieldOrStack
                    || (target.getOriginalTarget() instanceof TargetCard && target.getOriginalTarget().getZone() == Zone.GRAVEYARD)) {
                targetCardZonesChecked.add(Zone.GRAVEYARD);
                TargetCard targetFull = (TargetCard) target.getOriginalTarget();

                List<UUID> needPlayers = game.getState().getPlayersInRange(getId(), game).toList();
                // fix for opponent graveyard
                if (target.getOriginalTarget() instanceof TargetCardInOpponentsGraveyard) {
                    // current player remove
                    Assert.assertTrue(needPlayers.contains(getId()));
                    needPlayers.remove(getId());
                    Assert.assertFalse(needPlayers.contains(getId()));
                }
                // fix for your graveyard
                if (target.getOriginalTarget() instanceof TargetCardInYourGraveyard) {
                    // only current player
                    Assert.assertTrue(needPlayers.contains(getId()));
                    needPlayers.clear();
                    needPlayers.add(getId());
                    Assert.assertEquals(1, needPlayers.size());
                }

                for (String targetDefinition : targets) {
                    checkTargetDefinitionMarksSupport(target, targetDefinition, "^");

                    String[] targetList = targetDefinition.split("\\^");
                    boolean targetFound = false;
                    for (String targetName : targetList) {
                        IterateGraveyards:
                        for (UUID playerId : needPlayers) {
                            Player player = game.getPlayer(playerId);
                            for (Card card : player.getGraveyard().getCards(targetFull.getFilter(), game)) {
                                if (hasObjectTargetNameOrAlias(card, targetName) || (card.getName() + '-' + card.getExpansionSetCode()).equals(targetName)) { // TODO: remove set code search?
                                    if (target.canTarget(abilityControllerId, card.getId(), source, game) && !target.getTargets().contains(card.getId())) {
                                        target.addTarget(card.getId(), source, game);
                                        targetFound = true;
                                        break IterateGraveyards;  // return to next targetName
                                    }
                                }
                            }

                        }
                    }
                    if (targetFound) {
                        targets.remove(targetDefinition);
                        return true;
                    }
                }

            }

            // stack
            if (target.getOriginalTarget() instanceof TargetSpell) {
                for (String targetDefinition : targets) {
                    checkTargetDefinitionMarksSupport(target, targetDefinition, "^");
                    String[] targetList = targetDefinition.split("\\^");
                    boolean targetFound = false;
                    for (String targetName : targetList) {
                        for (StackObject stackObject : game.getStack()) {
                            if (hasObjectTargetNameOrAlias(stackObject, targetName)) {
                                if (target.canTarget(abilityControllerId, stackObject.getId(), source, game) && !target.getTargets().contains(stackObject.getId())) {
                                    target.addTarget(stackObject.getId(), source, game);
                                    targetFound = true;
                                    break; // return to next targetName
                                }
                            }
                        }
                    }
                    if (targetFound) {
                        targets.remove(targetDefinition);
                        return true;
                    }
                }
            }

            // library
            if (target.getOriginalTarget() instanceof TargetCardInLibrary
                    || (target.getOriginalTarget() instanceof TargetCard && target.getOriginalTarget().getZone() == Zone.LIBRARY)) {
                // user don't have access to library, so it must be targeted through list/revealed cards
                Assert.fail("Library zone is private, you must target through cards list, e.g. revealed: " + target.getOriginalTarget().getClass().getCanonicalName());
            }

            // uninplemented TargetCard's zone
            if (target.getOriginalTarget() instanceof TargetCard && !targetCardZonesChecked.contains(target.getOriginalTarget().getZone())) {
                Assert.fail("Found unimplemented TargetCard's zone or TargetCard's extented class: "
                        + target.getOriginalTarget().getClass().getCanonicalName()
                        + ", zone " + target.getOriginalTarget().getZone()
                        + ", from " + (source == null ? "unknown source" : source.getSourceObject(game)));
            }
        }

        // wrong target settings by addTarget
        // how to fix: implement target class processing above
        if (!targets.isEmpty()) {
            String message;

            if (source != null) {
                message = this.getName() + " - Targets list was setup by addTarget with " + targets + ", but not used"
                        + "\nCard: " + source.getSourceObject(game)
                        + "\nAbility: " + source.getClass().getSimpleName() + " (" + source.getRule() + ")"
                        + "\nTarget: " + target.getClass().getSimpleName() + " (" + target.getMessage() + ")"
                        + "\nYou must implement target class support in TestPlayer or setup good targets";
            } else {
                message = this.getName() + " - Targets list was setup by addTarget with " + targets + ", but not used"
                        + "\nCard: unknown source"
                        + "\nAbility: unknown source"
                        + "\nTarget: " + target.getClass().getSimpleName() + " (" + target.getMessage() + ")"
                        + "\nYou must implement target class support in TestPlayer or setup good targets";
            }
            Assert.fail(message);
        }

        this.chooseStrictModeFailed("target", game, getInfo(source, game) + "\n" + getInfo(target));
        return computerPlayer.chooseTarget(outcome, target, source, game);
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
        UUID abilityControllerId = computerPlayer.getId();
        if (target.getTargetController() != null && target.getAbilityController() != null) {
            abilityControllerId = target.getAbilityController();
        }

        assertAliasSupportInTargets(false);
        if (!targets.isEmpty()) {
            // skip targets
            if (targets.get(0).equals(TARGET_SKIP)) {
                Assert.assertTrue("found skip target, but it require more targets, needs "
                                + (target.getMinNumberOfTargets() - target.getTargets().size()) + " more",
                        target.getTargets().size() >= target.getMinNumberOfTargets());
                targets.remove(0);
                return true;
            }
            for (String targetDefinition : targets) {
                String[] targetList = targetDefinition.split("\\^");
                boolean targetFound = false;
                for (String targetName : targetList) {
                    for (Card card : cards.getCards(game)) {
                        if (hasObjectTargetNameOrAlias(card, targetName)
                                && !target.getTargets().contains(card.getId())
                                && target.canTarget(abilityControllerId, card.getId(), source, cards, game)) {
                            target.addTarget(card.getId(), source, game);
                            targetFound = true;
                            break;
                        }
                    }
                }
                if (targetFound) {
                    targets.remove(targetDefinition);
                    return true;
                }
            }

            // TODO: enable fail checks and fix tests
            //Assert.fail("Wrong target");
            LOGGER.warn("Wrong target");
        }

        this.chooseStrictModeFailed("target", game, getInfo(source, game) + "\n" + getInfo(target));
        return computerPlayer.chooseTarget(outcome, cards, target, source, game);
    }

    @Override
    public TriggeredAbility chooseTriggeredAbility(List<TriggeredAbility> abilities, Game game) {
        assertAliasSupportInChoices(false);
        if (!choices.isEmpty()) {
            String choice = choices.get(0);

            for (TriggeredAbility ability : abilities) {
                if (ability.toString().startsWith(choice)) {
                    choices.remove(0);
                    return ability;
                }
            }

            assertWrongChoiceUsage(choice);
        }

        this.chooseStrictModeFailed("choice", game,
                "Triggered list (total " + abilities.size() + "):\n"
                        + abilities.stream().map(a -> getInfo(a, game)).collect(Collectors.joining("\n")));
        return computerPlayer.chooseTriggeredAbility(abilities, game);
    }

    @Override
    public boolean chooseUse(Outcome outcome, String message, Ability source, Game game) {
        return this.chooseUse(outcome, message, null, null, null, source, game);
    }

    @Override
    public boolean chooseUse(Outcome outcome, String message, String secondMessage, String trueText, String falseText, Ability source, Game game) {
        if (message.equals("Scry 1?")) {
            return false;
        }
        assertAliasSupportInChoices(false);
        if (!choices.isEmpty()) {
            String choice = choices.get(0);

            if (choice.equals("No")) {
                choices.remove(0);
                return false;
            }
            if (choice.equals("Yes")) {
                choices.remove(0);
                return true;
            }

            assertWrongChoiceUsage(choice);
        }

        this.chooseStrictModeFailed("choice", game, getInfo(source, game)
                + "\nMessage: " + message
                + "\nChoices: " + (trueText != null ? trueText : "Yes") + " - " + (falseText != null ? falseText : "No")
                + ((trueText != null || falseText != null) ? "\nUse Yes/No in unit tests for text choices." : ""));
        return computerPlayer.chooseUse(outcome, message, secondMessage, trueText, falseText, source, game);
    }

    @Override
    public int announceXMana(int min, int max, int multiplier, String message, Game game, Ability ability) {
        assertAliasSupportInChoices(false);
        if (!choices.isEmpty()) {
            for (String choice : choices) {
                if (choice.startsWith("X=")) {
                    int xValue = Integer.parseInt(choice.substring(2));
                    choices.remove(choice);
                    return xValue;
                }
            }
        }

        this.chooseStrictModeFailed("choice", game, getInfo(ability, game)
                + "\nMessage: " + message);
        return computerPlayer.announceXMana(min, max, multiplier, message, game, ability);
    }

    @Override
    public int announceXCost(int min, int max, String message, Game game, Ability ability, VariableCost variablCost) {
        assertAliasSupportInChoices(false);
        if (!choices.isEmpty()) {
            if (choices.get(0).startsWith("X=")) {
                int xValue = Integer.parseInt(choices.get(0).substring(2));
                choices.remove(0);
                return xValue;
            }
        }

        this.chooseStrictModeFailed("choice", game, getInfo(ability, game)
                + "\nMessage: " + message);
        return computerPlayer.announceXCost(min, max, message, game, ability, null);
    }

    @Override
    public int getAmount(int min, int max, String message, Game game) {
        assertAliasSupportInChoices(false);
        if (!choices.isEmpty()) {
            if (choices.get(0).startsWith("X=")) {
                int xValue = Integer.parseInt(choices.get(0).substring(2));
                choices.remove(0);
                return xValue;
            }
        }

        this.chooseStrictModeFailed("choice", game, message);
        return computerPlayer.getAmount(min, max, message, game);
    }

    @Override
    public List<Integer> getMultiAmount(Outcome outcome, List<String> messages, int min, int max, MultiAmountType type, Game game) {
        assertAliasSupportInChoices(false);

        int needCount = messages.size();
        List<Integer> defaultList = MultiAmountType.prepareDefaltValues(needCount, min, max);
        if (needCount == 0) {
            return defaultList;
        }

        List<Integer> answer = new ArrayList<>(defaultList);
        if (!choices.isEmpty()) {
            // must fill all possible choices or skip it
            for (int i = 0; i < messages.size(); i++) {
                if (!choices.isEmpty()) {
                    // normal choice
                    if (choices.get(0).startsWith("X=")) {
                        answer.set(i, Integer.parseInt(choices.get(0).substring(2)));
                        choices.remove(0);
                        continue;
                    }
                    // skip
                    if (choices.get(0).equals(CHOICE_SKIP)) {
                        choices.remove(0);
                        break;
                    }
                }
                Assert.fail(String.format("Missing choice in multi amount: %s (pos %d - %s)", type.getHeader(), i + 1, messages.get(i)));
            }

            // extra check
            if (!MultiAmountType.isGoodValues(answer, needCount, min, max)) {
                Assert.fail("Wrong choices in multi amount: " + answer
                        .stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")));
            }

            return answer;
        }

        this.chooseStrictModeFailed("choice", game, "Multi amount: " + type.getHeader());
        return computerPlayer.getMultiAmount(outcome, messages, min, max, type, game);
    }

    @Override
    public void addAbility(Ability ability) {
        computerPlayer.addAbility(ability);
    }

    @Override
    public boolean activateAbility(ActivatedAbility ability, Game game) {
        return computerPlayer.activateAbility(ability, game);
    }

    @Override
    public void abort() {
        computerPlayer.abort();
    }

    @Override
    public void signalPlayerConcede() {
        computerPlayer.signalPlayerConcede();
    }

    @Override
    public void abortReset() {
        computerPlayer.abortReset();
    }

    @Override
    public void won(Game game) {
        computerPlayer.won(game);
    }

    @Override
    public void restore(Player player) {
        // no rollback for test player meta data (modesSet, actions, choices, targets, aliases)
        computerPlayer.restore(player);
    }

    @Override
    public void useDeck(Deck deck, Game game) {
        computerPlayer.useDeck(deck, game);
    }

    @Override
    public void init(Game game) {
        initialTurns = 0;
        computerPlayer.init(game);
    }

    @Override
    public void init(Game game, boolean testMode) {
        initialTurns = 0;
        computerPlayer.init(game, testMode);
    }

    @Override
    public void reset() {
        computerPlayer.reset();
    }

    @Override
    public Counters getCounters() {
        return computerPlayer.getCounters();
    }

    @Override
    public void beginTurn(Game game) {
        checkLegalMovesThisTurn(game);
        computerPlayer.beginTurn(game);
    }

    @Override
    public RangeOfInfluence getRange() {
        return computerPlayer.getRange();
    }

    @Override
    public Set<UUID> getInRange() {
        return computerPlayer.getInRange();
    }

    @Override
    public Set<UUID> getPlayersUnderYourControl() {
        return computerPlayer.getPlayersUnderYourControl();
    }

    @Override
    public void controlPlayersTurn(Game game, UUID playerId) {
        computerPlayer.controlPlayersTurn(game, playerId);
    }

    @Override
    public void setTurnControlledBy(UUID playerId) {
        computerPlayer.setTurnControlledBy(playerId);
    }

    @Override
    public UUID getTurnControlledBy() {
        return computerPlayer.getTurnControlledBy();
    }

    @Override
    public void resetOtherTurnsControlled() {
        computerPlayer.resetOtherTurnsControlled();
    }

    @Override
    public boolean isGameUnderControl() {
        return computerPlayer.isGameUnderControl();
    }

    @Override
    public void setGameUnderYourControl(boolean value) {
        computerPlayer.setGameUnderYourControl(value);
    }

    @Override
    public void setGameUnderYourControl(boolean value, boolean fullRestore) {
        computerPlayer.setGameUnderYourControl(value, fullRestore);
    }

    @Override
    public void endOfTurn(Game game) {
        computerPlayer.endOfTurn(game);
    }

    @Override
    public boolean canBeTargetedBy(MageObject source, UUID sourceControllerId, Game game) {
        return computerPlayer.canBeTargetedBy(source, sourceControllerId, game);
    }

    @Override
    public boolean hasProtectionFrom(MageObject source, Game game) {
        return computerPlayer.hasProtectionFrom(source, game);
    }

    @Override
    public int drawCards(int num, Ability source, Game game) {
        return computerPlayer.drawCards(num, source, game);
    }

    @Override
    public int drawCards(int num, Ability source, Game game, GameEvent event) {
        return computerPlayer.drawCards(num, source, game, event);
    }

    @Override
    public void discardToMax(Game game) {
        computerPlayer.discardToMax(game);
    }

    @Override
    public boolean removeFromHand(Card card, Game game) {
        return computerPlayer.removeFromHand(card, game);
    }

    @Override
    public boolean removeFromLibrary(Card card, Game game) {
        return computerPlayer.removeFromLibrary(card, game);
    }

    @Override
    public Card discardOne(boolean random, boolean payForCost, Ability source, Game game) {
        return computerPlayer.discardOne(random, payForCost, source, game);
    }

    @Override
    public Cards discard(int amount, boolean random, boolean payForCost, Ability source, Game game) {
        return computerPlayer.discard(amount, random, payForCost, source, game);
    }

    @Override
    public Cards discard(int minAmount, int maxAmount, boolean payForCost, Ability source, Game game) {
        return computerPlayer.discard(minAmount, maxAmount, payForCost, source, game);
    }

    @Override
    public Cards discard(Cards cards, boolean payForCost, Ability source, Game game) {
        return computerPlayer.discard(cards, payForCost, source, game);
    }

    @Override
    public boolean discard(Card card, boolean payForCost, Ability source, Game game) {
        return computerPlayer.discard(card, payForCost, source, game);
    }

    @Override
    public List<UUID> getAttachments() {
        return computerPlayer.getAttachments();
    }

    @Override
    public boolean addAttachment(UUID permanentId, Ability source, Game game) {
        return computerPlayer.addAttachment(permanentId, source, game);
    }

    @Override
    public boolean removeAttachment(Permanent attachment, Ability source, Game game) {
        return computerPlayer.removeAttachment(attachment, source, game);
    }

    @Override
    public boolean removeFromBattlefield(Permanent permanent, Ability source, Game game) {
        return computerPlayer.removeFromBattlefield(permanent, source, game);
    }

    @Override
    public boolean putInGraveyard(Card card, Game game) {
        return computerPlayer.putInGraveyard(card, game);
    }

    @Override
    public boolean removeFromGraveyard(Card card, Game game) {
        return computerPlayer.removeFromGraveyard(card, game);
    }

    @Override
    public boolean putCardsOnBottomOfLibrary(Card card, Game game, Ability source, boolean anyOrder) {
        return computerPlayer.putCardsOnBottomOfLibrary(card, game, source, anyOrder);
    }

    @Override
    public boolean putCardsOnBottomOfLibrary(Cards cards, Game game, Ability source, boolean anyOrder) {
        return computerPlayer.putCardsOnBottomOfLibrary(cards, game, source, anyOrder);
    }

    @Override
    public boolean putCardOnTopXOfLibrary(Card card, Game game, Ability source, int xFromTheTop, boolean withName) {
        return computerPlayer.putCardOnTopXOfLibrary(card, game, source, xFromTheTop, withName);
    }

    @Override
    public boolean putCardsOnTopOfLibrary(Cards cards, Game game, Ability source, boolean anyOrder) {
        return computerPlayer.putCardsOnTopOfLibrary(cards, game, source, anyOrder);
    }

    @Override
    public boolean putCardsOnTopOfLibrary(Card card, Game game, Ability source, boolean anyOrder) {
        return computerPlayer.putCardsOnTopOfLibrary(card, game, source, anyOrder);
    }

    @Override
    public boolean shuffleCardsToLibrary(Cards cards, Game game, Ability source) {
        return computerPlayer.shuffleCardsToLibrary(cards, game, source);
    }

    @Override
    public boolean shuffleCardsToLibrary(Card card, Game game, Ability source) {
        return computerPlayer.shuffleCardsToLibrary(card, game, source);
    }

    @Override
    public void setCastSourceIdWithAlternateMana(UUID sourceId, ManaCosts manaCosts, Costs costs) {
        computerPlayer.setCastSourceIdWithAlternateMana(sourceId, manaCosts, costs);
    }

    @Override
    public Set<UUID> getCastSourceIdWithAlternateMana() {
        return computerPlayer.getCastSourceIdWithAlternateMana();
    }

    @Override
    public Map<UUID, ManaCosts<ManaCost>> getCastSourceIdManaCosts() {
        return computerPlayer.getCastSourceIdManaCosts();
    }

    @Override
    public Map<UUID, Costs<Cost>> getCastSourceIdCosts() {
        return computerPlayer.getCastSourceIdCosts();
    }

    @Override
    public void clearCastSourceIdManaCosts() {
        computerPlayer.clearCastSourceIdManaCosts();
    }

    @Override
    public boolean isInPayManaMode() {
        return computerPlayer.isInPayManaMode();
    }

    @Override
    public boolean cast(SpellAbility ability, Game game, boolean noMana, ApprovingObject approvingObject) {
        // TestPlayer, ComputerPlayer always call inherited cast() from PlayerImpl
        // that's why chooseSpellAbilityForCast will be ignored in TestPlayer, see workaround with TestComputerPlayerXXX
        return computerPlayer.cast(ability, game, noMana, approvingObject);
    }

    @Override
    public boolean playCard(Card card, Game game, boolean noMana, ApprovingObject approvingObject) {
        return computerPlayer.playCard(card, game, noMana, approvingObject);
    }

    @Override
    public boolean playLand(Card card, Game game, boolean ignoreTiming) {
        return computerPlayer.playLand(card, game, ignoreTiming);
    }

    @Override
    public boolean triggerAbility(TriggeredAbility source, Game game) {
        return computerPlayer.triggerAbility(source, game);
    }

    @Override
    public LinkedHashMap<UUID, ActivatedAbility> getPlayableActivatedAbilities(MageObject object, Zone zone, Game game) {
        return computerPlayer.getPlayableActivatedAbilities(object, zone, game);
    }

    @Override
    public int getLandsPlayed() {
        return computerPlayer.getLandsPlayed();
    }

    @Override
    public boolean canPlayLand() {
        return computerPlayer.canPlayLand();
    }

    @Override
    public void shuffleLibrary(Ability source, Game game) {
        computerPlayer.shuffleLibrary(source, game);
    }

    @Override
    public void revealCards(Ability source, Cards cards, Game game) {
        computerPlayer.revealCards(source, cards, game);
    }

    @Override
    public void revealCards(String name, Cards cards, Game game) {
        computerPlayer.revealCards(name, cards, game);
    }

    @Override
    public void revealCards(Ability source, String name, Cards cards, Game game) {
        computerPlayer.revealCards(name, cards, game);
    }

    @Override
    public void revealCards(String name, Cards cards, Game game, boolean postToLog) {
        computerPlayer.revealCards(name, cards, game, postToLog);
    }

    @Override
    public void revealCards(Ability source, String name, Cards cards, Game game, boolean postToLog) {
        computerPlayer.revealCards(name, cards, game, postToLog);
    }

    @Override
    public void lookAtCards(String name, Cards cards, Game game) {
        computerPlayer.lookAtCards(name, cards, game);
    }

    @Override
    public void lookAtCards(Ability source, String name, Cards cards, Game game) {
        computerPlayer.lookAtCards(source, name, cards, game);
    }

    @Override
    public void lookAtCards(String name, Card card, Game game) {
        computerPlayer.lookAtCards(name, card, game);
    }

    @Override
    public void phasing(Game game) {
        computerPlayer.phasing(game);
    }

    @Override
    public void untap(Game game) {
        computerPlayer.untap(game);
    }

    @Override
    public void updateRange(Game game) {
        computerPlayer.updateRange(game);
    }

    @Override
    public UUID getId() {
        return computerPlayer.getId();
    }

    @Override
    public Cards getHand() {
        return computerPlayer.getHand();
    }

    @Override
    public Graveyard getGraveyard() {
        return computerPlayer.getGraveyard();
    }

    @Override
    public ManaPool getManaPool() {
        return computerPlayer.getManaPool();
    }

    @Override
    public String getName() {
        return computerPlayer.getName();
    }

    @Override
    public String getLogName() {
        return computerPlayer.getLogName();
    }

    @Override
    public boolean isHuman() {
        return false;
    }

    @Override
    public boolean isComputer() {
        // all players in unit tests are computers, so it allows testing different logic (Human vs AI)
        if (isTestsMode()) {
            // AIRealGameSimulation = true - full plyable AI
            // AIRealGameSimulation = false - choose assisted AI (Human)
            return AIRealGameSimulation;
        } else {
            throw new IllegalStateException("Can't use test player outside of unit tests");
        }
    }

    @Override
    public Library getLibrary() {
        return computerPlayer.getLibrary();
    }

    @Override
    public Cards getSideboard() {
        return computerPlayer.getSideboard();
    }

    @Override
    public int getLife() {
        return computerPlayer.getLife();
    }

    @Override
    public void initLife(int life) {
        computerPlayer.initLife(life);
    }

    @Override
    public void setLife(int life, Game game, Ability source) {
        computerPlayer.setLife(life, game, source);
    }

    @Override
    public void setLifeTotalCanChange(boolean lifeTotalCanChange) {
        computerPlayer.setLifeTotalCanChange(lifeTotalCanChange);
    }

    @Override
    public boolean isLifeTotalCanChange() {
        return computerPlayer.isLifeTotalCanChange();
    }

    @Override
    public List<AlternativeSourceCosts> getAlternativeSourceCosts() {
        return computerPlayer.getAlternativeSourceCosts();
    }

    @Override
    public boolean isCanLoseLife() {
        return computerPlayer.isCanLoseLife();
    }

    @Override
    public void setCanLoseLife(boolean canLoseLife) {
        computerPlayer.setCanLoseLife(canLoseLife);
    }

    @Override
    public int loseLife(int amount, Game game, Ability source, boolean atCombat, UUID attackerId) {
        return computerPlayer.loseLife(amount, game, source, atCombat, attackerId);
    }

    @Override
    public int loseLife(int amount, Game game, Ability source, boolean atCombat) {
        return computerPlayer.loseLife(amount, game, source, atCombat);
    }

    @Override
    public boolean isCanGainLife() {
        return computerPlayer.isCanGainLife();
    }

    @Override
    public void setCanGainLife(boolean canGainLife) {
        computerPlayer.setCanGainLife(canGainLife);
    }

    @Override
    public int gainLife(int amount, Game game, Ability source) {
        return computerPlayer.gainLife(amount, game, source);
    }

    @Override
    public void exchangeLife(Player player, Ability source, Game game) {
        computerPlayer.exchangeLife(player, source, game);
    }

    @Override
    public int damage(int damage, Ability source, Game game) {
        return computerPlayer.damage(damage, source, game);
    }

    @Override
    public int damage(int damage, UUID attackerId, Ability source, Game game) {
        return computerPlayer.damage(damage, attackerId, source, game);
    }

    @Override
    public int damage(int damage, UUID attackerId, Ability source, Game game, boolean combatDamage, boolean preventable) {
        return computerPlayer.damage(damage, attackerId, source, game, combatDamage, preventable);
    }

    @Override
    public int damage(int damage, UUID attackerId, Ability source, Game game, boolean combatDamage, boolean preventable, List<UUID> appliedEffects) {
        return computerPlayer.damage(damage, attackerId, source, game, combatDamage, preventable, appliedEffects);
    }

    @Override
    public boolean addCounters(Counter counter, UUID playerAddingCounters, Ability source, Game game) {
        return computerPlayer.addCounters(counter, source.getControllerId(), source, game);
    }

    @Override
    public void removeCounters(String name, int amount, Ability source, Game game) {
        computerPlayer.removeCounters(name, amount, source, game);
    }

    @Override
    public Abilities<Ability> getAbilities() {
        return computerPlayer.getAbilities();
    }

    @Override
    public int getLandsPerTurn() {
        return computerPlayer.getLandsPerTurn();
    }

    @Override
    public void setLandsPerTurn(int landsPerTurn) {
        computerPlayer.setLandsPerTurn(landsPerTurn);
    }

    @Override
    public int getMaxHandSize() {
        return computerPlayer.getMaxHandSize();
    }

    @Override
    public void setMaxHandSize(int maxHandSize) {
        computerPlayer.setMaxHandSize(maxHandSize);
    }

    @Override
    public void setMaxAttackedBy(int maxAttackedBy) {
        computerPlayer.setMaxAttackedBy(maxAttackedBy);
    }

    @Override
    public int getMaxAttackedBy() {
        return computerPlayer.getMaxAttackedBy();
    }

    @Override
    public void setResponseString(String responseString) {
        computerPlayer.setResponseString(responseString);
    }

    @Override
    public void setResponseManaType(UUID manaTypePlayerId, ManaType responseManaType) {
        computerPlayer.setResponseManaType(manaTypePlayerId, responseManaType);
    }

    @Override
    public void setResponseUUID(UUID responseUUID) {
        computerPlayer.setResponseUUID(responseUUID);
    }

    @Override
    public void setResponseBoolean(Boolean responseBoolean) {
        computerPlayer.setResponseBoolean(responseBoolean);
    }

    @Override
    public void setResponseInteger(Integer responseInteger) {
        computerPlayer.setResponseInteger(responseInteger);
    }

    @Override
    public boolean isPassed() {
        return computerPlayer.isPassed();
    }

    @Override
    public void pass(Game game) {
        computerPlayer.pass(game);
    }

    @Override
    public void resetPassed() {
        computerPlayer.resetPassed();
    }

    @Override
    public void resetPlayerPassedActions() {
        computerPlayer.resetPlayerPassedActions();
    }

    @Override
    public void quit(Game game) {
        computerPlayer.quit(game);
    }

    @Override
    public void timerTimeout(Game game) {
        computerPlayer.timerTimeout(game);
    }

    @Override
    public void idleTimeout(Game game) {
        computerPlayer.idleTimeout(game);
    }

    @Override
    public void concede(Game game) {
        computerPlayer.concede(game);
    }

    @Override
    public void sendPlayerAction(mage.constants.PlayerAction playerAction, Game game, Object data) {
        computerPlayer.sendPlayerAction(playerAction, game, data);
    }

    @Override
    public void leave() {
        computerPlayer.leave();
    }

    @Override
    public boolean hasLeft() {
        return computerPlayer.hasLeft();
    }

    @Override
    public void lost(Game game) {
        computerPlayer.lost(game);
    }

    @Override
    public boolean hasDrew() {
        return computerPlayer.hasDrew();
    }

    @Override
    public void drew(Game game) {
        computerPlayer.drew(game);
    }

    @Override
    public void lostForced(Game game) {
        computerPlayer.lostForced(game);
    }

    @Override
    public boolean canLose(Game game) {
        return computerPlayer.canLose(game);
    }

    @Override
    public boolean hasLost() {
        return computerPlayer.hasLost();
    }

    @Override
    public boolean isInGame() {
        return computerPlayer.isInGame();
    }

    @Override
    public boolean canRespond() {
        return computerPlayer.canRespond();
    }

    @Override
    public boolean hasWon() {
        return computerPlayer.hasWon();
    }

    @Override
    public void declareAttacker(UUID attackerId, UUID defenderId, Game game, boolean allowUndo) {
        computerPlayer.declareAttacker(attackerId, defenderId, game, allowUndo);
    }

    @Override
    public void declareBlocker(UUID defenderId, UUID blockerId, UUID attackerId, Game game) {
        computerPlayer.declareBlocker(defenderId, blockerId, attackerId, game);
    }

    @Override
    public void declareBlocker(UUID defenderId, UUID blockerId, UUID attackerId, Game game, boolean allowUndo) {
        computerPlayer.declareBlocker(defenderId, blockerId, attackerId, game, allowUndo);
    }

    @Override
    public boolean searchLibrary(TargetCardInLibrary target, Ability source, Game game) {
        return computerPlayer.searchLibrary(target, source, game);
    }


    @Override
    public boolean searchLibrary(TargetCardInLibrary target, Ability source, Game game, UUID targetPlayerId) {
        return computerPlayer.searchLibrary(target, source, game, targetPlayerId);
    }

    @Override
    public boolean seekCard(FilterCard filter, Ability source, Game game) {
        return computerPlayer.seekCard(filter, source, game);
    }

    @Override
    public void lookAtAllLibraries(Ability source, Game game) {
        computerPlayer.lookAtAllLibraries(source, game);
    }

    @Override
    public boolean flipCoin(Ability source, Game game, boolean winnable) {
        return computerPlayer.flipCoin(source, game, true);
    }

    @Override
    public boolean flipCoinResult(Game game) {
        assertAliasSupportInChoices(false);
        if (!choices.isEmpty()) {
            String nextResult = choices.get(0);
            if (nextResult.equals(FLIPCOIN_RESULT_TRUE)) {
                choices.remove(0);
                return true;
            } else if (nextResult.equals(FLIPCOIN_RESULT_FALSE)) {
                choices.remove(0);
                return false;
            }
        }
        this.chooseStrictModeFailed("flip coin result", game, "Use setFlipCoinResult to set it up in unit tests");

        // implementation from PlayerImpl:
        return RandomUtil.nextBoolean();
    }

    @Override
    public List<Integer> rollDice(Outcome outcome, Ability source, Game game, int numSides, int numDice, int ignoreLowestAmount) {
        return computerPlayer.rollDice(outcome, source, game, numSides, numDice, ignoreLowestAmount);
    }

    @Override
    public int rollDieResult(int sides, Game game) {
        assertAliasSupportInChoices(false);
        if (!choices.isEmpty()) {
            String nextResult = choices.get(0);
            if (nextResult.startsWith(DIE_ROLL)) {
                choices.remove(0);
                return Integer.parseInt(nextResult.substring(DIE_ROLL.length()));
            }
        }
        this.chooseStrictModeFailed("die roll result", game, "Use setDieRollResult to set it up in unit tests");

        // implementation from PlayerImpl:
        return RandomUtil.nextInt(sides) + 1;
    }

    @Override
    public List<Permanent> getAvailableAttackers(Game game) {
        return computerPlayer.getAvailableAttackers(game);
    }

    @Override
    public List<Permanent> getAvailableAttackers(UUID defenderId, Game game) {
        return computerPlayer.getAvailableAttackers(defenderId, game);
    }

    @Override
    public List<Permanent> getAvailableBlockers(Game game) {
        return computerPlayer.getAvailableBlockers(game);
    }

    @Override
    public ManaOptions getManaAvailable(Game game) {
        return computerPlayer.getManaAvailable(game);
    }

    @Override
    public void addAvailableTriggeredMana(List<Mana> availableTriggeredMana) {
        computerPlayer.addAvailableTriggeredMana(availableTriggeredMana);
    }

    @Override
    public List<List<Mana>> getAvailableTriggeredMana() {
        return computerPlayer.getAvailableTriggeredMana();
    }

    @Override
    public List<ActivatedAbility> getPlayable(Game game, boolean hidden) {
        return computerPlayer.getPlayable(game, hidden);
    }

    @Override
    public PlayableObjectsList getPlayableObjects(Game game, Zone zone) {
        return computerPlayer.getPlayableObjects(game, zone);
    }

    @Override
    public List<Ability> getPlayableOptions(Ability ability, Game game) {
        return computerPlayer.getPlayableOptions(ability, game);
    }

    @Override
    public boolean isTestsMode() {
        return computerPlayer.isTestsMode();
    }

    @Override
    public void setTestMode(boolean value) {
        computerPlayer.setTestMode(value);
    }

    @Override
    public boolean isTopCardRevealed() {
        return computerPlayer.isTopCardRevealed();
    }

    @Override
    public void setTopCardRevealed(boolean topCardRevealed) {
        computerPlayer.setTopCardRevealed(topCardRevealed);
    }

    @Override
    public UserData getUserData() {
        return computerPlayer.getUserData();
    }

    @Override
    public void setUserData(UserData userData) {
        computerPlayer.setUserData(userData);
    }

    @Override
    public void addAction(String action) {
        computerPlayer.addAction(action);
    }

    @Override
    public void setAllowBadMoves(boolean allowBadMoves) {
        computerPlayer.setAllowBadMoves(allowBadMoves);
    }

    @Override
    public boolean canPayLifeCost(Ability ability) {
        return computerPlayer.canPayLifeCost(ability);
    }

    @Override
    public PayLifeCostLevel getPayLifeCostLevel() {
        return computerPlayer.getPayLifeCostLevel();
    }

    @Override
    public void setPayLifeCostLevel(PayLifeCostLevel payLifeCostLevel) {
        computerPlayer.setPayLifeCostLevel(payLifeCostLevel);
    }

    @Override
    public boolean canPaySacrificeCost(Permanent permanent, Ability source, UUID controllerId, Game game) {
        return computerPlayer.canPaySacrificeCost(permanent, source, controllerId, game);
    }

    @Override
    public FilterPermanent getSacrificeCostFilter() {
        return computerPlayer.getSacrificeCostFilter();
    }

    @Override
    public void setCanPaySacrificeCostFilter(FilterPermanent permanent) {
        computerPlayer.setCanPaySacrificeCostFilter(permanent);
    }

    @Override
    public boolean canLoseByZeroOrLessLife() {
        return computerPlayer.canLoseByZeroOrLessLife();
    }

    @Override
    public void setLoseByZeroOrLessLife(boolean loseByZeroOrLessLife) {
        computerPlayer.setLoseByZeroOrLessLife(loseByZeroOrLessLife);
    }

    @Override
    public boolean canPlayCardsFromGraveyard() {
        return computerPlayer.canPlayCardsFromGraveyard();
    }

    @Override
    public void setDrawsOnOpponentsTurn(boolean drawsOnOpponentsTurn) {
        computerPlayer.setDrawsOnOpponentsTurn(drawsOnOpponentsTurn);
    }

    @Override
    public boolean isDrawsOnOpponentsTurn() {
        return computerPlayer.isDrawsOnOpponentsTurn();
    }

    @Override
    public void setPayManaMode(boolean payManaMode) {
        computerPlayer.setPayManaMode(payManaMode);
    }

    @Override
    public void setPlayCardsFromGraveyard(boolean playCardsFromGraveyard) {
        computerPlayer.setPlayCardsFromGraveyard(playCardsFromGraveyard);
    }

    @Override
    public boolean autoLoseGame() {
        return computerPlayer.autoLoseGame();
    }

    @Override
    public void becomesActivePlayer() {
        computerPlayer.becomesActivePlayer();
    }

    @Override
    public int getTurns() {
        return computerPlayer.getTurns();
    }

    @Override
    public int getStoredBookmark() {
        return computerPlayer.getStoredBookmark();
    }

    @Override
    public void setStoredBookmark(int storedBookmark) {
        computerPlayer.setStoredBookmark(storedBookmark);
    }

    @Override
    public synchronized void resetStoredBookmark(Game game) {
        computerPlayer.resetStoredBookmark(game);
    }

    @Override
    public boolean lookAtFaceDownCard(Card card, Game game, int abilitiesToActivate) {
        return computerPlayer.lookAtFaceDownCard(card, game, abilitiesToActivate);
    }

    @Override
    public void setPriorityTimeLeft(int timeLeft) {
        computerPlayer.setPriorityTimeLeft(timeLeft);
    }

    @Override
    public int getPriorityTimeLeft() {
        return computerPlayer.getPriorityTimeLeft();
    }

    @Override
    public boolean hasQuit() {
        return computerPlayer.hasQuit();
    }

    @Override
    public boolean hasTimerTimeout() {
        return computerPlayer.hasTimerTimeout();
    }

    @Override
    public boolean hasIdleTimeout() {
        return computerPlayer.hasIdleTimeout();
    }

    @Override
    public void setReachedNextTurnAfterLeaving(boolean reachedNextTurnAfterLeaving) {
        computerPlayer.setReachedNextTurnAfterLeaving(reachedNextTurnAfterLeaving);
    }

    @Override
    public boolean hasReachedNextTurnAfterLeaving() {
        return computerPlayer.hasReachedNextTurnAfterLeaving();
    }

    @Override
    public boolean canJoinTable(Table table) {
        return computerPlayer.canJoinTable(table);
    }

    @Override
    public void addCommanderId(UUID commanderId) {
        computerPlayer.addCommanderId(commanderId);
    }

    @Override
    public Set<UUID> getCommandersIds() {
        return computerPlayer.getCommandersIds();
    }

    @Override
    public boolean moveCardToHandWithInfo(Card card, Ability source, Game game, boolean withName) {
        return computerPlayer.moveCardToHandWithInfo(card, source, game, withName);
    }

    @Override
    public boolean moveCardsToHandWithInfo(Cards cards, Ability source, Game game, boolean withName) {
        return computerPlayer.moveCardsToHandWithInfo(cards, source, game, withName);
    }

    @Override
    public boolean moveCardsToExile(Card card, Ability source, Game game, boolean withName, UUID exileId, String exileZoneName) {
        return computerPlayer.moveCardsToExile(card, source, game, withName, exileId, exileZoneName);
    }

    @Override
    public boolean moveCardsToExile(Set<Card> cards, Ability source, Game game, boolean withName, UUID exileId, String exileZoneName) {
        return computerPlayer.moveCardsToExile(cards, source, game, withName, exileId, exileZoneName);
    }

    @Override
    public Set<Card> moveCardsToGraveyardWithInfo(Set<? extends Card> allCards, Ability source, Game game, Zone fromZone) {
        return computerPlayer.moveCardsToGraveyardWithInfo(allCards, source, game, fromZone);
    }

    @Override
    public boolean moveCardToGraveyardWithInfo(Card card, Ability source, Game game, Zone fromZone) {
        return computerPlayer.moveCardToGraveyardWithInfo(card, source, game, fromZone);
    }

    @Override
    public boolean moveCardToLibraryWithInfo(Card card, Ability source, Game game, Zone fromZone, boolean toTop, boolean withName) {
        return computerPlayer.moveCardToLibraryWithInfo(card, source, game, fromZone, toTop, withName);
    }

    @Override
    public boolean moveCardToExileWithInfo(Card card, UUID exileId, String exileName, Ability source, Game game, Zone fromZone, boolean withName) {
        return computerPlayer.moveCardToExileWithInfo(card, exileId, exileName, source, game, fromZone, withName);
    }

    @Override
    public boolean moveCardToCommandWithInfo(Card card, Ability source, Game game, Zone fromZone) {
        return computerPlayer.moveCardToCommandWithInfo(card, source, game, fromZone);
    }

    @Override
    public Cards millCards(int toMill, Ability source, Game game) {
        return computerPlayer.millCards(toMill, source, game);
    }

    @Override
    public boolean hasOpponent(UUID playerToCheckId, Game game) {
        return computerPlayer.hasOpponent(playerToCheckId, game);
    }

    @Override
    public boolean getPassedAllTurns() {
        return computerPlayer.getPassedAllTurns();
    }

    @Override
    public boolean getPassedUntilNextMain() {
        return computerPlayer.getPassedUntilNextMain();
    }

    @Override
    public boolean getPassedUntilEndOfTurn() {
        return computerPlayer.getPassedUntilEndOfTurn();
    }

    @Override
    public boolean getPassedTurn() {
        return computerPlayer.getPassedTurn();
    }

    @Override
    public boolean getPassedUntilStackResolved() {
        return computerPlayer.getPassedUntilStackResolved();
    }

    @Override
    public boolean getPassedUntilEndStepBeforeMyTurn() {
        return computerPlayer.getPassedUntilEndStepBeforeMyTurn();
    }

    @Override
    public void revokePermissionToSeeHandCards() {
        computerPlayer.revokePermissionToSeeHandCards();
    }

    @Override
    public void addPermissionToShowHandCards(UUID watcherUserId) {
        computerPlayer.addPermissionToShowHandCards(watcherUserId);
    }

    @Override
    public boolean isPlayerAllowedToRequestHand(UUID gameId, UUID requesterPlayerId) {
        return computerPlayer.isPlayerAllowedToRequestHand(gameId, requesterPlayerId);
    }

    @Override
    public void addPlayerToRequestedHandList(UUID gameId, UUID requesterPlayerId) {
        computerPlayer.addPlayerToRequestedHandList(gameId, requesterPlayerId);
    }

    @Override
    public boolean hasUserPermissionToSeeHand(UUID userId) {
        return computerPlayer.hasUserPermissionToSeeHand(userId);
    }

    @Override
    public Set<UUID> getUsersAllowedToSeeHandCards() {
        return computerPlayer.getUsersAllowedToSeeHandCards();
    }

    @Override
    public void setMatchPlayer(MatchPlayer matchPlayer) {
        computerPlayer.setMatchPlayer(matchPlayer);
    }

    @Override
    public MatchPlayer getMatchPlayer() {
        return computerPlayer.getMatchPlayer();
    }

    @Override
    public AbilityType getJustActivatedType() {
        return computerPlayer.getJustActivatedType();
    }

    @Override
    public void setJustActivatedType(AbilityType justActivatedType) {
        computerPlayer.setJustActivatedType(justActivatedType);
    }

    @Override
    public void cleanUpOnMatchEnd() {
        computerPlayer.cleanUpOnMatchEnd();
    }

    @Override
    public void skip() {
        computerPlayer.skip();
    }

    @Override
    public boolean choose(Outcome outcome, Target target,
                          Ability source, Game game
    ) {
        // needed to call here the TestPlayer because it's overwitten
        return choose(outcome, target, source, game, null);
    }

    @Override
    public boolean choose(Outcome outcome, Cards cards,
                          TargetCard target, Game game
    ) {
        assertAliasSupportInChoices(false);
        if (!choices.isEmpty()) {

            // skip choices
            if (choices.get(0).equals(CHOICE_SKIP)) {
                choices.remove(0);
                if (cards.isEmpty()) {
                    // cancel button forced in GUI on no possible choices
                    return false;
                } else {
                    Assert.assertTrue("found skip choice, but it require more choices, needs "
                                    + (target.getMinNumberOfTargets() - target.getTargets().size()) + " more",
                            target.getTargets().size() >= target.getMinNumberOfTargets());
                    return true;
                }
            }

            for (String choose2 : choices) {
                // TODO: More targetting to fix
                String[] targetList = choose2.split("\\^");
                boolean targetFound = false;
                for (String targetName : targetList) {
                    for (Card card : cards.getCards(game)) {
                        if (target.getTargets().contains(card.getId())) {
                            continue;
                        }
                        if (hasObjectTargetNameOrAlias(card, targetName)) {
                            if (target.isNotTarget() || target.canTarget(card.getId(), game)) {
                                target.add(card.getId(), game);
                                targetFound = true;
                                break;
                            }
                        }
                    }
                }
                if (targetFound) {
                    choices.remove(choose2);
                    return true;
                }
            }

            assertWrongChoiceUsage(choices.size() > 0 ? choices.get(0) : "empty list");
        }

        this.chooseStrictModeFailed("choice", game, getInfo(target));
        return computerPlayer.choose(outcome, cards, target, game);
    }

    @Override
    public boolean chooseTargetAmount(Outcome outcome, TargetAmount target,
                                      Ability source, Game game
    ) {
        // chooseTargetAmount calls for EACH target cycle (e.g. one target per click, see TargetAmount)
        // if use want to stop choosing then chooseTargetAmount must return false (example: up to xxx)

        Assert.assertNotEquals("chooseTargetAmount needs non zero amount remaining", 0, target.getAmountRemaining());

        assertAliasSupportInTargets(true);
        if (!targets.isEmpty()) {

            // skip targets
            if (targets.get(0).equals(TARGET_SKIP)) {
                Assert.assertTrue("found skip target, but it require more targets, needs "
                                + (target.getMinNumberOfTargets() - target.getTargets().size()) + " more",
                        target.getTargets().size() >= target.getMinNumberOfTargets());
                targets.remove(0);
                return false; // false in chooseTargetAmount = stop to choose
            }

            // only target amount needs
            String[] choiceSettings = targets.get(0).split("\\^");
            if (choiceSettings.length != 2
                    || !choiceSettings[1].startsWith("X=")) {
                Assert.fail("Must be target amount, but found unknown target: " + targets.get(0));
            }
            String targetName = choiceSettings[0];
            int targetAmount = Integer.parseInt(choiceSettings[1].substring("X=".length()));

            checkTargetDefinitionMarksSupport(target, targetName, "=");

            // player target support
            if (targetName.startsWith("targetPlayer=")) {
                targetName = targetName.substring(targetName.indexOf("targetPlayer=") + "targetPlayer=".length());
            }

            String targetInfo = "(" + targetName + " - " + targetAmount + ")";
            Assert.assertTrue("target amount must be non zero " + targetInfo, targetAmount > 0);
            Assert.assertTrue("target amount must be <= remaining = " + target.getAmountRemaining() + " " + targetInfo, targetAmount <= target.getAmountRemaining());

            if (target.getAmountRemaining() > 0) {
                for (UUID possibleTarget : target.possibleTargets(source.getControllerId(), source, game)) {
                    boolean foundTarget = false;

                    // permanent
                    MageObject objectPermanent = game.getObject(possibleTarget);
                    if (objectPermanent != null && hasObjectTargetNameOrAlias(objectPermanent, targetName)) {
                        foundTarget = true;
                    }

                    // player
                    Player objectPlayer = game.getPlayer(possibleTarget);
                    if (!foundTarget && objectPlayer != null && objectPlayer.getName().equals(targetName)) {
                        foundTarget = true;
                    }

                    if (foundTarget) {
                        if (!target.getTargets().contains(possibleTarget) && target.canTarget(possibleTarget, source, game)) {
                            // can select
                            target.addTarget(possibleTarget, targetAmount, source, game);
                            targets.remove(0);
                            return true; // one target per choose call
                        }
                    }
                }
            }
        }

        this.chooseStrictModeFailed("target", game, getInfo(source, game) + "\n" + getInfo(target));
        return computerPlayer.chooseTargetAmount(outcome, target, source, game);
    }

    @Override
    public boolean chooseMulligan(Game game
    ) {
        return computerPlayer.chooseMulligan(game);
    }

    @Override
    public boolean choosePile(Outcome outcome, String message,
                              List<? extends Card> pile1, List<? extends Card> pile2,
                              Game game
    ) {
        return computerPlayer.choosePile(outcome, message, pile1, pile2, game);
    }

    @Override
    public boolean playMana(Ability ability, ManaCost unpaid,
                            String promptText, Game game
    ) {
        groupsForTargetHandling = null;

        if (!computerPlayer.getManaPool().isAutoPayment()) {
            if (!choices.isEmpty()) {
                // manual pay by mana clicks/commands

                // simulate cancel on mana payment (e.g. user press on cancel button)
                if (choices.get(0).equals(MANA_CANCEL)) {
                    choices.remove(0);
                    return false;
                }

                String choice = choices.get(0);
                boolean choiceUsed = false;
                boolean choiceRemoved = false;
                switch (choice) {
                    case "White":
                        Assert.assertTrue("pool must have white mana", computerPlayer.getManaPool().getWhite() > 0);
                        computerPlayer.getManaPool().unlockManaType(ManaType.WHITE);
                        choiceUsed = true;
                        break;
                    case "Blue":
                        Assert.assertTrue("pool must have blue mana", computerPlayer.getManaPool().getBlue() > 0);
                        computerPlayer.getManaPool().unlockManaType(ManaType.BLUE);
                        choiceUsed = true;
                        break;
                    case "Black":
                        Assert.assertTrue("pool must have black mana", computerPlayer.getManaPool().getBlack() > 0);
                        computerPlayer.getManaPool().unlockManaType(ManaType.BLACK);
                        choiceUsed = true;
                        break;
                    case "Red":
                        Assert.assertTrue("pool must have red mana", computerPlayer.getManaPool().getRed() > 0);
                        computerPlayer.getManaPool().unlockManaType(ManaType.RED);
                        choiceUsed = true;
                        break;
                    case "Green":
                        Assert.assertTrue("pool must have green mana", computerPlayer.getManaPool().getGreen() > 0);
                        computerPlayer.getManaPool().unlockManaType(ManaType.GREEN);
                        choiceUsed = true;
                        break;
                    case "Colorless":
                        Assert.assertTrue("pool must have colorless mana", computerPlayer.getManaPool().getColorless() > 0);
                        computerPlayer.getManaPool().unlockManaType(ManaType.COLORLESS);
                        choiceUsed = true;
                        break;
                    default:
                        // go to special block
                        //Assert.fail("Unknown choice command for mana unlock: " + needColor);
                        break;
                }

                // manual pay by special actions like convoke
                if (!choiceUsed) {
                    Map<UUID, SpecialAction> specialActions = game.getState().getSpecialActions().getControlledBy(this.getId(), true);
                    for (SpecialAction specialAction : specialActions.values()) {
                        if (specialAction.getRule(true).startsWith(choice)) {
                            if (specialAction.canActivate(this.getId(), game).canActivate()) {
                                choices.remove(0);
                                choiceRemoved = true;
                                specialAction.setUnpaidMana(unpaid);
                                if (activateAbility(specialAction, game)) {
                                    choiceUsed = true;
                                }
                            }
                        }
                    }
                }

                if (choiceUsed) {
                    if (!choiceRemoved) {
                        choices.remove(0);
                    }
                    return true;
                } else {
                    Assert.fail("Can't use choice in play mana: " + choice);
                }
            }

            Assert.fail(this.getName() + " disabled mana auto-payment, but no choices found for color unlock in pool or special action for unpaid cost: " + unpaid.getText());
        }

        return computerPlayer.playMana(ability, unpaid, promptText, game);
    }

    @Override
    public UUID chooseAttackerOrder(List<Permanent> attacker, Game game
    ) {
        return computerPlayer.chooseAttackerOrder(attacker, game);
    }

    @Override
    public UUID chooseBlockerOrder(List<Permanent> blockers, CombatGroup combatGroup,
                                   List<UUID> blockerOrder, Game game
    ) {
        return computerPlayer.chooseBlockerOrder(blockers, combatGroup, blockerOrder, game);
    }

    @Override
    public void assignDamage(int damage, List<UUID> targets,
                             String singleTargetName, UUID attackerId, Ability source,
                             Game game
    ) {
        computerPlayer.assignDamage(damage, targets, singleTargetName, attackerId, source, game);
    }

    @Override
    public void sideboard(Match match, Deck deck
    ) {
        computerPlayer.sideboard(match, deck);
    }

    @Override
    public void construct(Tournament tournament, Deck deck
    ) {
        computerPlayer.construct(tournament, deck);
    }

    @Override
    public void pickCard(List<Card> cards, Deck deck,
                         Draft draft
    ) {
        computerPlayer.pickCard(cards, deck, draft);
    }

    @Override
    public boolean scry(int value, Ability source,
                        Game game
    ) {
        // Don't scry at the start of the game.
        if (game.getTurnNum() == 1 && game.getStep() == null) {
            return false;
        }
        return computerPlayer.scry(value, source, game);
    }

    @Override
    public boolean surveil(int value, Ability source,
                           Game game
    ) {
        return computerPlayer.surveil(value, source, game);
    }

    @Override
    public boolean moveCards(Card card, Zone toZone,
                             Ability source, Game game
    ) {
        return computerPlayer.moveCards(card, toZone, source, game);
    }

    @Override
    public boolean moveCards(Card card, Zone toZone,
                             Ability source, Game game,
                             boolean tapped, boolean faceDown, boolean byOwner, List<UUID> appliedEffects
    ) {
        return computerPlayer.moveCards(card, toZone, source, game, tapped, faceDown, byOwner, appliedEffects);
    }

    @Override
    public boolean moveCards(Cards cards, Zone toZone,
                             Ability source, Game game
    ) {
        return computerPlayer.moveCards(cards, toZone, source, game);
    }

    @Override
    public boolean moveCards(Set<? extends Card> cards, Zone toZone,
                             Ability source, Game game
    ) {
        return computerPlayer.moveCards(cards, toZone, source, game);
    }

    @Override
    public boolean moveCards(Set<? extends Card> cards, Zone toZone,
                             Ability source, Game game,
                             boolean tapped, boolean faceDown, boolean byOwner, List<UUID> appliedEffects
    ) {
        return computerPlayer.moveCards(cards, toZone, source, game, tapped, faceDown, byOwner, appliedEffects);
    }

    @Override
    public boolean hasDesignation(DesignationType designationName
    ) {
        return computerPlayer.hasDesignation(designationName);
    }

    @Override
    public void addDesignation(Designation designation
    ) {
        computerPlayer.addDesignation(designation);
    }

    @Override
    public List<Designation> getDesignations() {
        return computerPlayer.getDesignations();
    }

    public void setAIPlayer(boolean AIPlayer) {
        this.AIPlayer = AIPlayer;
    }

    public boolean isAIPlayer() {
        return AIPlayer;
    }

    public String getHistory() {
        return computerPlayer.getHistory();
    }

    @Override
    public PlanarDieRollResult rollPlanarDie(Outcome outcome, Ability source, Game game, int numberChaosSides, int numberPlanarSides) {
        return computerPlayer.rollPlanarDie(outcome, source, game, numberChaosSides, numberPlanarSides);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Player obj = (Player) o;
        if (this.getId() == null || obj.getId() == null) {
            return false;
        }

        return this.getId().equals(obj.getId());
    }

    public void setChooseStrictMode(boolean enable) {
        this.strictChooseMode = enable;
    }

    @Override
    public boolean getStrictChooseMode() {
        return this.strictChooseMode;
    }

    @Override
    public UserData getControllingPlayersUserData(Game game) {
        return null;
    }

    @Override
    public void addPhyrexianToColors(FilterMana colors) {
        computerPlayer.addPhyrexianToColors(colors);
    }

    @Override
    public FilterMana getPhyrexianColors() {
        return computerPlayer.getPhyrexianColors();
    }

    @Override
    public SpellAbility chooseAbilityForCast(Card card, Game game, boolean noMana) {
        assertAliasSupportInChoices(false);
        MageObject object = game.getObject(card.getId()); // must be object to find real abilities (example: commander)
        Map<UUID, SpellAbility> useable = PlayerImpl.getCastableSpellAbilities(game, this.getId(), object, game.getState().getZone(object.getId()), noMana);
        if (useable.size() == 1) {
            return useable.values().iterator().next();
        }

        if (!choices.isEmpty()) {
            String choice = choices.get(0);
            for (SpellAbility ability : useable.values()) {
                if (ability.toString().startsWith(choice)) {
                    choices.remove(0);
                    return ability;
                }
            }

            assertWrongChoiceUsage(choice);
        }

        String allInfo = useable.values().stream().map(Object::toString).collect(Collectors.joining("\n"));
        this.chooseStrictModeFailed("choice", game, getInfo(card) + " - can't select ability to cast.\n" + "Card's abilities:\n" + allInfo);
        return computerPlayer.chooseAbilityForCast(card, game, noMana);
    }

    public ComputerPlayer getComputerPlayer() {
        return computerPlayer;
    }

    public void setAIRealGameSimulation(boolean AIRealGameSimulation) {
        this.AIRealGameSimulation = AIRealGameSimulation;
    }

    public Map<Integer, HashMap<UUID, ArrayList<org.mage.test.player.PlayerAction>>> getRollbackActions() {
        return rollbackActions;
    }

    @Override
    public String toString() {
        return computerPlayer.toString();
    }

    public boolean canChooseByComputer() {
        // full playable AI can choose any time
        if (this.AIRealGameSimulation) {
            return true;
        }

        // non-strict mode allows computer assisted choices (for old tests compatibility only)
        return !this.strictChooseMode;
    }

    private void assertWrongChoiceUsage(String choice) {
        // TODO: enable fail checks and fix tests, it's a part of setStrictChooseMode's implementation to all tests
        //Assert.fail("Wrong choice command: " + choice);
        LOGGER.warn("Wrong choice command: " + choice);
    }
}
