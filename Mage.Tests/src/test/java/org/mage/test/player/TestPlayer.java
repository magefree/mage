package org.mage.test.player;

import mage.MageObject;
import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.*;
import mage.abilities.costs.AlternativeSourceCosts;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.mana.ManaOptions;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.decks.Deck;
import mage.choices.Choice;
import mage.constants.*;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.designations.Designation;
import mage.designations.DesignationType;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
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
import mage.game.match.Match;
import mage.game.match.MatchPlayer;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.game.tournament.Tournament;
import mage.player.ai.ComputerPlayer;
import mage.players.Library;
import mage.players.ManaPool;
import mage.players.Player;
import mage.players.PlayerList;
import mage.players.net.UserData;
import mage.target.*;
import mage.target.common.*;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.mage.test.serverside.base.impl.CardTestPlayerAPIImpl.*;

/**
 * @author BetaSteward_at_googlemail.com
 * @author Simown
 * @author JayDi85
 */
@Ignore
public class TestPlayer implements Player {

    private static final Logger logger = Logger.getLogger(TestPlayer.class);

    private int maxCallsWithoutAction = 100;
    private int foundNoAction = 0;
    private boolean AIPlayer;
    private final List<PlayerAction> actions = new ArrayList<>();
    private final List<String> choices = new ArrayList<>();
    private final List<String> targets = new ArrayList<>();
    private final List<String> modesSet = new ArrayList<>();

    private final ComputerPlayer computerPlayer;

    private String[] groupsForTargetHandling = null;

    // Tracks the initial turns (turn 0s) both players are given at the start of the game.
    // Before actual turns start. Needed for checking attacker/blocker legality in the tests
    private static int initialTurns = 0;

    public TestPlayer(ComputerPlayer computerPlayer) {
        this.computerPlayer = computerPlayer;
        AIPlayer = false;
    }

    public TestPlayer(final TestPlayer testPlayer) {
        this.AIPlayer = testPlayer.AIPlayer;
        this.foundNoAction = testPlayer.foundNoAction;
        this.actions.addAll(testPlayer.actions);
        this.choices.addAll(testPlayer.choices);
        this.targets.addAll(testPlayer.targets);
        this.modesSet.addAll(testPlayer.modesSet);
        this.computerPlayer = testPlayer.computerPlayer.copy();
        if (testPlayer.groupsForTargetHandling != null) {
            this.groupsForTargetHandling = testPlayer.groupsForTargetHandling.clone();
        }
    }

    public void addChoice(String choice) {
        choices.add(choice);
    }

    public void addModeChoice(String mode) {
        modesSet.add(mode);
    }

    public void addTarget(String target) {
        targets.add(target);
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
            index = Integer.valueOf(indexedMatcher.group(2));
        }
        filter.add(new NamePredicate(filteredName));
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
                if (stackObject != null && stackObject.getStackAbility().toString().contains(spellOnTopOFStack)) {
                    return true;
                }
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
            if (ability instanceof SpellAbility && ((SpellAbility) ability).getSpellAbilityType() == SpellAbilityType.SPLIT_FUSED) {
                if (group.contains("FuseLeft-")) {
                    result = handleTargetString(group.substring(group.indexOf("FuseLeft-") + 9), ability, game);
                } else if (group.startsWith("FuseRight-")) {
                    result = handleTargetString(group.substring(group.indexOf("FuseRight-") + 10), ability, game);
                } else {
                    result = false;
                }
            } else {
                result = handleTargetString(group, ability, game);
            }
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
                    throw new UnsupportedOperationException("Ability has no targets, but there is a player target set - " + ability.toString());
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
                throw new UnsupportedOperationException("Mode not available for " + ability.toString());
            }
            if (selectedMode.getTargets().isEmpty()) {
                throw new AssertionError("Ability has no targets. " + ability.toString());
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
                for (UUID id : currentTarget.possibleTargets(ability.getSourceId(), ability.getControllerId(), game)) {
                    if (!currentTarget.getTargets().contains(id)) {
                        MageObject object = game.getObject(id);
                        if (object != null
                                && ((object.isCopy() && !originOnly) || (!object.isCopy() && !copyOnly))
                                && ((!targetName.isEmpty() && object.getName().startsWith(targetName)) || (targetName.isEmpty() && object.getName().isEmpty()))) {
                            if (currentTarget.getNumberOfTargets() == 1) {
                                currentTarget.clearChosen();
                            }
                            if (currentTarget instanceof TargetCreaturePermanentAmount) {
                                // supports only to set the complete amount to one target
                                TargetCreaturePermanentAmount targetAmount = (TargetCreaturePermanentAmount) currentTarget;
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
        int numberOfActions = actions.size();
        List<PlayerAction> tempActions = new ArrayList<>();
        tempActions.addAll(actions);
        for (PlayerAction action : tempActions) {
            if (action.getTurnNum() == game.getTurnNum() && action.getStep() == game.getStep().getType()) {

                if (action.getAction().startsWith("activate:")) {
                    String command = action.getAction();
                    command = command.substring(command.indexOf("activate:") + 9);
                    groupsForTargetHandling = null;
                    String[] groups = command.split("\\$");
                    if (groups.length > 2 && !checkExecuteCondition(groups, game)) {
                        break;
                    }
                    for (Ability ability : computerPlayer.getPlayable(game, true)) { // add wrong action log?
                        if (ability.toString().startsWith(groups[0])) {
                            int bookmark = game.bookmarkState();
                            Ability newAbility = ability.copy();
                            if (groups.length > 1 && !groups[1].equals("target=NO_TARGET")) {
                                groupsForTargetHandling = groups;
                            }
                            if (computerPlayer.activateAbility((ActivatedAbility) newAbility, game)) {
                                actions.remove(action);
                                groupsForTargetHandling = null;
                                return true;
                            } else {
                                game.restoreState(bookmark, ability.getRule());
                            }
                            groupsForTargetHandling = null;
                        }
                    }
                } else if (action.getAction().startsWith("manaActivate:")) {
                    String command = action.getAction();
                    command = command.substring(command.indexOf("manaActivate:") + 13);
                    String[] groups = command.split("\\$");
                    List<MageObject> manaObjects = computerPlayer.getAvailableManaProducers(game);

                    for (MageObject mageObject : manaObjects) {
                        if (mageObject instanceof Permanent) {
                            for (Ability manaAbility : ((Permanent) mageObject).getAbilities(game).getAvailableActivatedManaAbilities(Zone.BATTLEFIELD, game)) {
                                if (manaAbility.toString().startsWith(groups[0])) {
                                    Ability newManaAbility = manaAbility.copy();
                                    computerPlayer.activateAbility((ActivatedAbility) newManaAbility, game);
                                    actions.remove(action);
                                    return true;
                                }
                            }
                        } else if (mageObject instanceof Card) {
                            for (Ability manaAbility : ((Card) mageObject).getAbilities(game).getAvailableActivatedManaAbilities(game.getState().getZone(mageObject.getId()), game)) {
                                if (manaAbility.toString().startsWith(groups[0])) {
                                    Ability newManaAbility = manaAbility.copy();
                                    computerPlayer.activateAbility((ActivatedAbility) newManaAbility, game);
                                    actions.remove(action);
                                    return true;
                                }
                            }
                        } else {
                            for (Ability manaAbility : mageObject.getAbilities().getAvailableActivatedManaAbilities(game.getState().getZone(mageObject.getId()), game)) {
                                if (manaAbility.toString().startsWith(groups[0])) {
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
                        for (ActivatedManaAbilityImpl manaAbility : perm.getAbilities().getAvailableActivatedManaAbilities(Zone.BATTLEFIELD, game)) {
                            if (manaAbility.toString().startsWith(groups[0]) && manaAbility.canActivate(computerPlayer.getId(), game).canActivate()) {
                                Ability newManaAbility = manaAbility.copy();
                                computerPlayer.activateAbility((ActivatedAbility) newManaAbility, game);
                                actions.remove(action);
                                return true;
                            }
                        }
                    }
                } else if (action.getAction().startsWith("addCounters:")) {
                    String command = action.getAction();
                    command = command.substring(command.indexOf("addCounters:") + 12);
                    String[] groups = command.split("\\$");
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents()) {
                        if (permanent.getName().equals(groups[0])) {
                            Counter counter = new Counter(groups[1], Integer.parseInt(groups[2]));
                            permanent.addCounters(counter, null, game);
                            break;
                        }
                    }
                } else if (action.getAction().startsWith("playerAction:")) {
                    String command = action.getAction();
                    command = command.substring(command.indexOf("playerAction:") + 13);
                    groupsForTargetHandling = null;
                    String[] groups = command.split("\\$");
                    if (groups.length > 0) {
                        if (groups[0].equals("Rollback")) {
                            if (groups.length > 1 && groups[1].startsWith("turns=")) {
                                int turns = Integer.parseInt(groups[1].substring(6));
                                game.rollbackTurns(turns);
                                actions.remove(action);
                                return true;
                            }
                        }
                        if (groups[0].equals("Concede")) {
                            game.concede(getId());
                            ((GameImpl) game).checkConcede();
                            actions.remove(action);
                        }
                    }
                } else if (action.getAction().startsWith("check:")) {
                    String command = action.getAction();
                    command = command.substring(command.indexOf("check:") + 6);

                    String[] params = command.split("@");
                    boolean checkProccessed = false;
                    if (params.length > 0) {

                        // check PT: card name, P, T
                        if (params[0].equals(CHECK_COMMAND_PT) && params.length == 4) {
                            assertPT(action, game, computerPlayer, params[1], Integer.parseInt(params[2]), Integer.parseInt(params[3]));
                            actions.remove(action);
                            checkProccessed = true;
                        }

                        // check PT: life
                        if (params[0].equals(CHECK_COMMAND_LIFE) && params.length == 2) {
                            assertLife(action, game, computerPlayer, Integer.parseInt(params[1]));
                            actions.remove(action);
                            checkProccessed = true;
                        }

                        // check ability: card name, ability class, must have
                        if (params[0].equals(CHECK_COMMAND_ABILITY) && params.length == 4) {
                            assertAbility(action, game, computerPlayer, params[1], params[2], Boolean.parseBoolean(params[3]));
                            actions.remove(action);
                            checkProccessed = true;
                        }

                        // check battlefield count: card name, count
                        if (params[0].equals(CHECK_COMMAND_PERMANENT_COUNT) && params.length == 3) {
                            assertPermanentCount(action, game, computerPlayer, params[1], Integer.parseInt(params[2]));
                            actions.remove(action);
                            checkProccessed = true;
                        }

                        // check exile count: card name, count
                        if (params[0].equals(CHECK_COMMAND_EXILE_COUNT) && params.length == 3) {
                            assertExileCount(action, game, computerPlayer, params[1], Integer.parseInt(params[2]));
                            actions.remove(action);
                            checkProccessed = true;
                        }

                        // check hand count: count
                        if (params[0].equals(CHECK_COMMAND_HAND_COUNT) && params.length == 2) {
                            assertHandCount(action, game, computerPlayer, Integer.parseInt(params[1]));
                            actions.remove(action);
                            checkProccessed = true;
                        }

                        // check color: card name, colors, must have
                        if (params[0].equals(CHECK_COMMAND_COLOR) && params.length == 4) {
                            assertColor(action, game, computerPlayer, params[1], params[2], Boolean.parseBoolean(params[3]));
                            actions.remove(action);
                            checkProccessed = true;
                        }

                        // check subtype: card name, subtype, must have
                        if (params[0].equals(CHECK_COMMAND_SUBTYPE) && params.length == 4) {
                            assertSubType(action, game, computerPlayer, params[1], SubType.fromString(params[2]), Boolean.parseBoolean(params[3]));
                            actions.remove(action);
                            checkProccessed = true;
                        }

                        // check mana pool: colors, amount
                        if (params[0].equals(CHECK_COMMAND_MANA_POOL) && params.length == 3) {
                            assertManaPool(action, game, computerPlayer, params[1], Integer.parseInt(params[2]));
                            actions.remove(action);
                            checkProccessed = true;
                        }
                    }

                    if (!checkProccessed) {
                        Assert.fail("Unknow check command or params: " + command);
                    }
                }
            }
        }
        if (AIPlayer) {
            computerPlayer.priority(game);
        } else {
            computerPlayer.pass(game);
        }
        // check to prevent endless loops
        if (numberOfActions == actions.size()) {
            foundNoAction++;
            if (foundNoAction > maxCallsWithoutAction) {
                throw new AssertionError("More priority calls to " + getName() + " and doing no action than allowed (" + maxCallsWithoutAction + ')');
            }
        } else {
            foundNoAction = 0;
        }
        return false;
    }

    private Permanent findPermanentWithAssert(PlayerAction action, Game game, Player player, String cardName) {
        Permanent founded = null;
        for (Permanent perm : game.getBattlefield().getAllPermanents()) {
            if (perm.getName().equals(cardName) && perm.getControllerId().equals(player.getId())) {
                return perm;
            }
        }
        Assert.assertNotNull(action.getActionName() + " - can''t find permanent to check PT: " + cardName, founded);
        return null;
    }

    private void assertPT(PlayerAction action, Game game, Player player, String permanentName, int Power, int Toughness) {
        Permanent perm = findPermanentWithAssert(action, game, player, permanentName);

        Assert.assertEquals(action.getActionName() + " - permanent " + permanentName + " have wrong power: " + perm.getPower().getValue() + " <> " + Power,
                Power, perm.getPower().getValue());
        Assert.assertEquals(action.getActionName() + " - permanent " + permanentName + " have wrong toughness: " + perm.getToughness().getValue() + " <> " + Toughness,
                Toughness, perm.getToughness().getValue());
    }

    private void assertLife(PlayerAction action, Game game, Player player, int Life) {
        Assert.assertEquals(action.getActionName() + " - " + player.getName() + " have wrong life: " + player.getLife() + " <> " + Life,
                Life, player.getLife());
    }

    private void assertAbility(PlayerAction action, Game game, Player player, String permanentName, String abilityClass, boolean mustHave) {
        Permanent perm = findPermanentWithAssert(action, game, player, permanentName);

        boolean founded = false;
        for (Ability ability : perm.getAbilities(game)) {
            if (ability.getClass().getName().equals(abilityClass)) {
                founded = true;
                break;
            }
        }

        if (mustHave) {
            Assert.assertEquals(action.getActionName() + " - permanent " + permanentName + " must have ability " + abilityClass, true, founded);
        } else {
            Assert.assertEquals(action.getActionName() + " - permanent " + permanentName + " must have not ability " + abilityClass, false, founded);
        }
    }

    private void assertPermanentCount(PlayerAction action, Game game, Player player, String permanentName, int count) {
        int foundedCount = 0;
        for (Permanent perm : game.getBattlefield().getAllPermanents()) {
            if (perm.getName().equals(permanentName) && perm.getControllerId().equals(player.getId())) {
                foundedCount++;
            }
        }

        Assert.assertEquals(action.getActionName() + " - permanent " + permanentName + " must exists in " + count + " instances", count, foundedCount);
    }

    private void assertExileCount(PlayerAction action, Game game, Player player, String permanentName, int count) {
        int foundedCount = 0;
        for (Card card : game.getExile().getAllCards(game)) {
            if (card.getName().equals(permanentName) && card.isOwnedBy(player.getId())) {
                foundedCount++;
            }
        }

        Assert.assertEquals(action.getActionName() + " - card " + permanentName + " must exists in exile zone with " + count + " instances", count, foundedCount);
    }

    private void assertHandCount(PlayerAction action, Game game, Player player, int count) {
        Assert.assertEquals(action.getActionName() + " - hand must contain " + count, count, player.getHand().size());
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
            Assert.assertEquals(action.getActionName() + " - must contain colors [" + searchColors.toString() + "] but found only [" + cardColor.toString() + "]", 0, colorsDontHave.size());
        } else {
            Assert.assertEquals(action.getActionName() + " - must not contain colors [" + searchColors.toString() + "] but found [" + cardColor.toString() + "]", 0, colorsHave.size());
        }
    }

    private void assertSubType(PlayerAction action, Game game, Player player, String permanentName, SubType subType, boolean mustHave) {

        Permanent perm = findPermanentWithAssert(action, game, player, permanentName);

        boolean founded = false;
        for (SubType st : perm.getSubtype(game)) {
            if (st.equals(subType)) {
                founded = true;
                break;
            }
        }

        if (mustHave) {
            Assert.assertEquals(action.getActionName() + " - permanent " + permanentName + " must have subtype " + subType, true, founded);
        } else {
            Assert.assertEquals(action.getActionName() + " - permanent " + permanentName + " must have not subtype " + subType, false, founded);
        }
    }

    private void assertManaPoolInner(PlayerAction action, Player player, ManaType manaType, Integer amount) {
        Integer current = player.getManaPool().get(manaType);
        Assert.assertEquals(action.getActionName() + " - mana pool must contain [" + amount.toString() + " " + manaType.toString() + "], but found [" + current.toString() + "]", amount, current);
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
        //List<PlayerAction>
        for (Iterator<org.mage.test.player.PlayerAction> it = actions.iterator(); it.hasNext(); ) {
            PlayerAction action = it.next();
            if (action.getTurnNum() == game.getTurnNum() && action.getAction().startsWith("attack:")) {
                String command = action.getAction();
                command = command.substring(command.indexOf("attack:") + 7);
                String[] groups = command.split("\\$");
                for (int i = 1; i < groups.length; i++) {
                    String group = groups[i];
                    if (group.startsWith("planeswalker=")) {
                        String planeswalkerName = group.substring(group.indexOf("planeswalker=") + 13);
                        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_PLANESWALKER, game)) {
                            if (permanent.getName().equals(planeswalkerName)) {
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
                // secondFilter.add(Predicates.not(new AttackingPredicate()));
                secondFilter.add(Predicates.not(new SummoningSicknessPredicate()));
                // TODO: Cannot enforce legal attackers multiple times per combat. See issue #3038
                Permanent attacker = findPermanent(secondFilter, groups[0], computerPlayer.getId(), game, false);
                if (attacker != null && attacker.canAttack(defenderId, game)) {
                    computerPlayer.declareAttacker(attacker.getId(), defenderId, game, false);
                    it.remove();
                }
            }

        }
    }

    @Override
    public List<UUID> getTurnControllers() {
        return computerPlayer.getTurnControllers();
    }

    @Override
    public void selectBlockers(Game game, UUID defendingPlayerId) {

        UUID opponentId = game.getOpponents(computerPlayer.getId()).iterator().next();
        // Map of Blocker reference -> list of creatures blocked
        Map<MageObjectReference, List<MageObjectReference>> blockedCreaturesByCreature = new HashMap<>();
        for (PlayerAction action : actions) {
            if (action.getTurnNum() == game.getTurnNum() && action.getAction().startsWith("block:")) {
                String command = action.getAction();
                command = command.substring(command.indexOf("block:") + 6);
                String[] groups = command.split("\\$");
                String blockerName = groups[0];
                String attackerName = groups[1];
                Permanent attacker = findPermanent(new FilterAttackingCreature(), attackerName, opponentId, game);
                Permanent blocker = findPermanent(new FilterControlledPermanent(), blockerName, computerPlayer.getId(), game);
                if (canBlockAnother(game, blocker, attacker, blockedCreaturesByCreature)) {
                    computerPlayer.declareBlocker(defendingPlayerId, blocker.getId(), attacker.getId(), game);
                } else {
                    throw new UnsupportedOperationException(blockerName + " cannot block " + attackerName + " it is already blocking the maximum amount of creatures.");
                }
            }
        }
        checkMultipleBlockers(game, blockedCreaturesByCreature);
    }

    // Checks if a creature can block at least one more creature
    private boolean canBlockAnother(Game game, Permanent blocker, Permanent attacker, Map<MageObjectReference, List<MageObjectReference>> blockedCreaturesByCreature) {
        MageObjectReference blockerRef = new MageObjectReference(blocker, game);
        // See if we already reference this blocker
        for (MageObjectReference r : blockedCreaturesByCreature.keySet()) {
            if (r.equals(blockerRef)) {
                // Use the existing reference if we do
                blockerRef = r;
            }
        }
        List<MageObjectReference> blocked = blockedCreaturesByCreature.getOrDefault(blockerRef, new ArrayList<>());
        int numBlocked = blocked.size();
        // Can't block any more creatures
        if (++numBlocked > blocker.getMaxBlocks()) {
            return false;
        }
        // Add the attacker reference to the list of creatures this creature is blocking
        blocked.add(new MageObjectReference(attacker, game));
        blockedCreaturesByCreature.put(blockerRef, blocked);
        return true;
    }

    // Check for Menace type abilities - if creatures can be blocked by >X or <Y only
    private void checkMultipleBlockers(Game game, Map<MageObjectReference, List<MageObjectReference>> blockedCreaturesByCreature) {
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

    @Override
    public Mode chooseMode(Modes modes, Ability source, Game game) {
        if (!modesSet.isEmpty() && modes.getMaxModes() > modes.getSelectedModes().size()) {
            // set mode to null to select less than maximum modes if multiple modes are allowed
            if (modesSet.get(0) == null) {
                modesSet.remove(0);
                return null;
            }
            int selectedMode = Integer.parseInt(modesSet.get(0));
            int i = 1;
            for (Mode mode : modes.getAvailableModes(source, game)) {
                if (i == selectedMode) {
                    modesSet.remove(0);
                    return mode;
                }
                i++;
            }
        }
        if (modes.getMinModes() <= modes.getSelectedModes().size()) {
            return null;
        }
        return computerPlayer.chooseMode(modes, source, game); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean choose(Outcome outcome, Choice choice, Game game) {
        if (!choices.isEmpty()) {
            if (choice.setChoiceByAnswers(choices, true)) {
                return true;
            }
        }
        return computerPlayer.choose(outcome, choice, game);
    }

    @Override
    public int chooseReplacementEffect(Map<String, String> rEffects, Game game) {
        if (!choices.isEmpty()) {
            for (String choice : choices) {
                for (int index = 0; index < rEffects.size(); index++) {
                    if (choice.equals(rEffects.get(Integer.toString(index)))) {
                        choices.remove(choice);
                        return index;
                    }
                }
            }
        }
        return computerPlayer.chooseReplacementEffect(rEffects, game);
    }

    @Override
    public boolean choose(Outcome outcome, Target target, UUID sourceId, Game game, Map<String, Serializable> options) {
        if (!choices.isEmpty()) {
            Ability source = null;
            StackObject stackObject = game.getStack().getStackObject(sourceId);
            if (stackObject != null) {
                source = stackObject.getStackAbility();
            }
            if ((target instanceof TargetPermanent) || (target instanceof TargetPermanentOrPlayer)) { // player target not implemted yet
                FilterPermanent filterPermanent;
                if (target instanceof TargetPermanentOrPlayer) {
                    filterPermanent = ((TargetPermanentOrPlayer) target).getFilterPermanent();
                } else {
                    filterPermanent = ((TargetPermanent) target).getFilter();
                }
                for (String choose2 : choices) {
                    String[] targetList = choose2.split("\\^");
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
                        for (Permanent permanent : game.getBattlefield().getActivePermanents(filterPermanent, getId(), sourceId, game)) {
                            if (target.getTargets().contains(permanent.getId())) {
                                continue;
                            }
                            if (permanent.getName().equals(targetName)) {

                                if (target.isNotTarget() || ((TargetPermanent) target).canTarget(computerPlayer.getId(), permanent.getId(), source, game)) {
                                    if ((permanent.isCopy() && !originOnly) || (!permanent.isCopy() && !copyOnly)) {
                                        target.add(permanent.getId(), game);
                                        targetFound = true;
                                        break;
                                    }
                                }
                            } else if ((permanent.getName() + '-' + permanent.getExpansionSetCode()).equals(targetName)) {
                                if (target.isNotTarget() || ((TargetPermanent) target).canTarget(computerPlayer.getId(), permanent.getId(), source, game)) {
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
                        choices.remove(choose2);
                        return true;
                    }
                }
            }
            if (target instanceof TargetPlayer) {
                for (Player player : game.getPlayers().values()) {
                    for (String choose2 : choices) {
                        if (player.getName().equals(choose2)) {
                            if (((TargetPlayer) target).canTarget(computerPlayer.getId(), player.getId(), null, game) && !target.getTargets().contains(player.getId())) {
                                target.add(player.getId(), game);
                                choices.remove(choose2);
                                return true;
                            }
                        }
                    }
                }
            }
            if (target instanceof TargetCard) {
                TargetCard targetCard = ((TargetCard) target);
                Set<UUID> possibleTargets = targetCard.possibleTargets(sourceId, target.getTargetController() == null ? getId() : target.getTargetController(), game);
                for (String choose2 : choices) {
                    String[] targetList = choose2.split("\\^");
                    boolean targetFound = false;
                    Choice:
                    for (String targetName : targetList) {
                        for (UUID targetId : possibleTargets) {
                            MageObject targetObject = game.getObject(targetId);
                            if (targetObject != null) {
                                if (targetObject.getName().equals(targetName)) {
                                    if (targetCard.canTarget(targetObject.getId(), game)) {
                                        if (targetCard.getTargets() != null && !targetCard.getTargets().contains(targetObject.getId())) {
                                            targetCard.add(targetObject.getId(), game);
                                            targetFound = true;
                                            if (target.getTargets().size() >= target.getMaxNumberOfTargets()) {
                                                break Choice;
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    }
                    if (targetFound) {
                        if (targetCard.isChosen()) {
                            choices.remove(choose2);
                            return true;
                        } else {
                            target.clearChosen();
                        }
                    }
                }
            }
            if (target instanceof TargetSource) {
                Set<UUID> possibleTargets;
                TargetSource t = ((TargetSource) target);
                possibleTargets = t.possibleTargets(sourceId, computerPlayer.getId(), game);
                for (String choose2 : choices) {
                    String[] targetList = choose2.split("\\^");
                    boolean targetFound = false;
                    for (String targetName : targetList) {
                        for (UUID targetId : possibleTargets) {
                            MageObject targetObject = game.getObject(targetId);
                            if (targetObject != null) {
                                if (targetObject.getName().equals(targetName)) {
                                    List<UUID> alreadyTargetted = target.getTargets();
                                    if (t.canTarget(targetObject.getId(), game)) {
                                        if (alreadyTargetted != null && !alreadyTargetted.contains(targetObject.getId())) {
                                            target.add(targetObject.getId(), game);
                                            choices.remove(choose2);
                                            targetFound = true;
                                        }
                                    }
                                }
                            }
                            if (targetFound) {
                                choices.remove(choose2);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return computerPlayer.choose(outcome, target, sourceId, game, options);
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Target target, Ability source, Game game) {
        if (!targets.isEmpty()) {
            UUID abilityControllerId = computerPlayer.getId();
            if (target.getTargetController() != null && target.getAbilityController() != null) {
                abilityControllerId = target.getAbilityController();
            }
            if (target instanceof TargetPlayer
                    || target instanceof TargetAnyTarget
                    || target instanceof TargetCreatureOrPlayer
                    || target instanceof TargetPermanentOrPlayer) {
                for (String targetDefinition : targets) {
                    if (targetDefinition.startsWith("targetPlayer=")) {
                        String playerName = targetDefinition.substring(targetDefinition.indexOf("targetPlayer=") + 13);
                        for (Player player : game.getPlayers().values()) {
                            if (player.getName().equals(playerName)
                                    && target.canTarget(computerPlayer.getId(), player.getId(), source, game)) {
                                target.add(player.getId(), game);
                                targets.remove(targetDefinition);
                                return true;
                            }
                        }
                    }
                }

            }
            if ((target instanceof TargetPermanent)
                    || (target instanceof TargetPermanentOrPlayer)
                    || (target instanceof TargetAnyTarget)
                    || (target instanceof TargetCreatureOrPlayer)) {
                for (String targetDefinition : targets) {
                    String[] targetList = targetDefinition.split("\\^");
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
                        Filter filter = target.getFilter();
                        if (filter instanceof FilterCreatureOrPlayer) {
                            filter = ((FilterCreatureOrPlayer) filter).getCreatureFilter();
                        }
                        if (filter instanceof FilterCreaturePlayerOrPlaneswalker) {
                            filter = ((FilterCreaturePlayerOrPlaneswalker) filter).getCreatureFilter();
                        }
                        if (filter instanceof TargetPermanentOrPlayer) {
                            filter = ((TargetPermanentOrPlayer) filter).getFilterPermanent();
                        }
                        for (Permanent permanent : game.getBattlefield().getAllActivePermanents((FilterPermanent) filter, game)) {
                            if (permanent.getName().equals(targetName) || (permanent.getName() + '-' + permanent.getExpansionSetCode()).equals(targetName)) {
                                if (target.canTarget(abilityControllerId, permanent.getId(), source, game) && !target.getTargets().contains(permanent.getId())) {
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
                        targets.remove(targetDefinition);
                        return true;
                    }
                }
            }

            if (target instanceof TargetCardInHand) {
                for (String targetDefinition : targets) {
                    String[] targetList = targetDefinition.split("\\^");
                    boolean targetFound = false;
                    for (String targetName : targetList) {
                        for (Card card : computerPlayer.getHand().getCards(((TargetCardInHand) target).getFilter(), game)) {
                            if (card.getName().equals(targetName) || (card.getName() + '-' + card.getExpansionSetCode()).equals(targetName)) {
                                if (((TargetCardInHand) target).canTarget(abilityControllerId, card.getId(), source, game) && !target.getTargets().contains(card.getId())) {
                                    target.add(card.getId(), game);
                                    targetFound = true;
                                    break;
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
            if (target instanceof TargetCardInYourGraveyard) {
                for (String targetDefinition : targets) {
                    String[] targetList = targetDefinition.split("\\^");
                    boolean targetFound = false;
                    for (String targetName : targetList) {
                        for (Card card : computerPlayer.getGraveyard().getCards(((TargetCardInYourGraveyard) target).getFilter(), game)) {
                            if (card.getName().equals(targetName) || (card.getName() + '-' + card.getExpansionSetCode()).equals(targetName)) {
                                if (((TargetCardInYourGraveyard) target).canTarget(abilityControllerId, card.getId(), source, game) && !target.getTargets().contains(card.getId())) {
                                    target.add(card.getId(), game);
                                    targetFound = true;
                                    break;
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
            if (target instanceof TargetCardInOpponentsGraveyard) {
                for (String targetDefinition : targets) {
                    String[] targetList = targetDefinition.split("\\^");
                    boolean targetFound = false;

                    for (String targetName : targetList) {
                        IterateOpponentsGraveyards:
                        for (UUID opponentId : game.getState().getPlayersInRange(getId(), game)) {
                            if (computerPlayer.hasOpponent(opponentId, game)) {
                                Player opponent = game.getPlayer(opponentId);
                                for (Card card : opponent.getGraveyard().getCards(((TargetCardInOpponentsGraveyard) target).getFilter(), game)) {
                                    if (card.getName().equals(targetName) || (card.getName() + '-' + card.getExpansionSetCode()).equals(targetName)) {
                                        if (((TargetCardInOpponentsGraveyard) target).canTarget(abilityControllerId, card.getId(), source, game) && !target.getTargets().contains(card.getId())) {
                                            target.add(card.getId(), game);
                                            targetFound = true;
                                            break IterateOpponentsGraveyards;
                                        }
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
            if (target instanceof TargetSpell) {
                for (String targetDefinition : targets) {
                    String[] targetList = targetDefinition.split("\\^");
                    boolean targetFound = false;
                    for (String targetName : targetList) {
                        for (StackObject stackObject : game.getStack()) {
                            if (stackObject.getName().equals(targetName)) {
                                target.add(stackObject.getId(), game);
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
            }

        }
        return computerPlayer.chooseTarget(outcome, target, source, game);
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
        if (!targets.isEmpty()) {
            for (String targetDefinition : targets) {
                String[] targetList = targetDefinition.split("\\^");
                boolean targetFound = false;
                for (String targetName : targetList) {
                    for (Card card : cards.getCards(game)) {
                        if (card.getName().equals(targetName) && !target.getTargets().contains(card.getId())) {
                            target.add(card.getId(), game);
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
        }
        return computerPlayer.chooseTarget(outcome, cards, target, source, game);
    }

    @Override
    public TriggeredAbility chooseTriggeredAbility(List<TriggeredAbility> abilities, Game game) {
        if (!choices.isEmpty()) {
            for (TriggeredAbility ability : abilities) {
                if (ability.toString().startsWith(choices.get(0))) {
                    choices.remove(0);
                    return ability;
                }
            }
        }
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
        if (!choices.isEmpty()) {
            if (choices.get(0).equals("No")) {
                choices.remove(0);
                return false;
            }
            if (choices.get(0).equals("Yes")) {
                choices.remove(0);
                return true;
            }
        }
        return computerPlayer.chooseUse(outcome, message, secondMessage, trueText, falseText, source, game);
    }

    @Override
    public int announceXMana(int min, int max, String message, Game game, Ability ability) {
        if (!choices.isEmpty()) {
            for (String choice : choices) {
                if (choice.startsWith("X=")) {
                    int xValue = Integer.parseInt(choice.substring(2));
                    choices.remove(choice);
                    return xValue;
                }
            }
        }
        return computerPlayer.announceXMana(min, max, message, game, ability);
    }

    @Override
    public int announceXCost(int min, int max, String message, Game game, Ability ability, VariableCost variablCost) {
        if (!choices.isEmpty()) {
            if (choices.get(0).startsWith("X=")) {
                int xValue = Integer.parseInt(choices.get(0).substring(2));
                choices.remove(0);
                return xValue;
            }
        }
        return computerPlayer.announceXCost(min, max, message, game, ability, null);
    }

    @Override
    public int getAmount(int min, int max, String message, Game game) {
        if (!choices.isEmpty()) {
            if (choices.get(0).startsWith("X=")) {
                int xValue = Integer.parseInt(choices.get(0).substring(2));
                choices.remove(0);
                return xValue;
            }
        }
        return computerPlayer.getAmount(min, max, message, game);
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
        this.modesSet.clear();
        this.modesSet.addAll(((TestPlayer) player).modesSet);
        this.actions.clear();
        this.actions.addAll(((TestPlayer) player).actions);
        this.choices.clear();
        this.choices.addAll(((TestPlayer) player).choices);
        this.targets.clear();
        this.targets.addAll(((TestPlayer) player).targets);
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
    public void otherPlayerLeftGame(Game game) {
        computerPlayer.otherPlayerLeftGame(game);
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
    public int drawCards(int num, Game game) {
        return computerPlayer.drawCards(num, game);
    }

    @Override
    public int drawCards(int num, Game game, List<UUID> appliedEffects) {
        return computerPlayer.drawCards(num, game, appliedEffects);
    }

    @Override
    public void discardToMax(Game game) {
        computerPlayer.discardToMax(game);
    }

    @Override
    public boolean putInHand(Card card, Game game) {
        return computerPlayer.putInHand(card, game);
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
    public void discard(int amount, Ability source, Game game) {
        computerPlayer.discard(amount, source, game);
    }

    @Override
    public Card discardOne(boolean random, Ability source, Game game) {
        return computerPlayer.discardOne(random, source, game);
    }

    @Override
    public Cards discard(int amount, boolean random, Ability source, Game game) {
        return computerPlayer.discard(amount, random, source, game);
    }

    @Override
    public boolean discard(Card card, Ability source, Game game) {
        return computerPlayer.discard(card, source, game);
    }

    @Override
    public List<UUID> getAttachments() {
        return computerPlayer.getAttachments();
    }

    @Override
    public boolean addAttachment(UUID permanentId, Game game) {
        return computerPlayer.addAttachment(permanentId, game);
    }

    @Override
    public boolean removeAttachment(Permanent attachment, Game game) {
        return computerPlayer.removeAttachment(attachment, game);
    }

    @Override
    public boolean removeFromBattlefield(Permanent permanent, Game game) {
        return computerPlayer.removeFromBattlefield(permanent, game);
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
    public boolean putCardOnTopXOfLibrary(Card card, Game game, Ability source, int xFromTheTop) {
        return computerPlayer.putCardOnTopXOfLibrary(card, game, source, xFromTheTop);
    }

    @Override
    public boolean putCardsOnTopOfLibrary(Cards cards, Game game, Ability source, boolean anyOrder) {
        return computerPlayer.putCardsOnTopOfLibrary(cards, game, source, anyOrder);
    }

    @Override
    public void setCastSourceIdWithAlternateMana(UUID sourceId, ManaCosts manaCosts, Costs costs) {
        computerPlayer.setCastSourceIdWithAlternateMana(sourceId, manaCosts, costs);
    }

    @Override
    public UUID getCastSourceIdWithAlternateMana() {
        return computerPlayer.getCastSourceIdWithAlternateMana();
    }

    @Override
    public ManaCosts getCastSourceIdManaCosts() {
        return computerPlayer.getCastSourceIdManaCosts();
    }

    @Override
    public Costs<Cost> getCastSourceIdCosts() {
        return computerPlayer.getCastSourceIdCosts();
    }

    @Override
    public boolean isInPayManaMode() {
        return computerPlayer.isInPayManaMode();
    }

    @Override
    public boolean cast(SpellAbility ability, Game game, boolean noMana, MageObjectReference reference) {
        return computerPlayer.cast(ability, game, noMana, reference);
    }

    @Override
    public boolean playCard(Card card, Game game, boolean noMana, boolean ignoreTiming, MageObjectReference reference) {
        return computerPlayer.playCard(card, game, noMana, ignoreTiming, reference);
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
    public LinkedHashMap<UUID, ActivatedAbility> getUseableActivatedAbilities(MageObject object, Zone zone, Game game) {
        return computerPlayer.getUseableActivatedAbilities(object, zone, game);
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
        return computerPlayer.isHuman();
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
    public void setLife(int life, Game game, UUID sourceId) {
        computerPlayer.setLife(life, game, sourceId);
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
    public int loseLife(int amount, Game game, boolean atCombat) {
        return computerPlayer.loseLife(amount, game, atCombat);
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
    public int gainLife(int amount, Game game, UUID sourceId) {
        return computerPlayer.gainLife(amount, game, sourceId);
    }

    @Override
    public int damage(int damage, UUID sourceId, Game game, boolean combatDamage, boolean preventable) {
        return computerPlayer.damage(damage, sourceId, game, combatDamage, preventable);
    }

    @Override
    public int damage(int damage, UUID sourceId, Game game, boolean combatDamage, boolean preventable, List<UUID> appliedEffects) {
        return computerPlayer.damage(damage, sourceId, game, combatDamage, preventable, appliedEffects);
    }

    @Override
    public boolean addCounters(Counter counter, Game game) {
        return computerPlayer.addCounters(counter, game);
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
    public int getLoyaltyUsePerTurn() {
        return computerPlayer.getLoyaltyUsePerTurn();
    }

    @Override
    public void setLoyaltyUsePerTurn(int loyaltyUsePerTurn) {
        computerPlayer.setLoyaltyUsePerTurn(loyaltyUsePerTurn);
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
    public boolean isEmptyDraw() {
        return computerPlayer.isEmptyDraw();
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
    public boolean searchLibrary(TargetCardInLibrary target, Game game) {
        return computerPlayer.searchLibrary(target, game);
    }

    @Override
    public boolean searchLibrary(TargetCardInLibrary target, Game game, boolean triggerEvents) {
        return computerPlayer.searchLibrary(target, game, triggerEvents);
    }

    @Override
    public boolean searchLibrary(TargetCardInLibrary target, Game game, UUID targetPlayerId) {
        return computerPlayer.searchLibrary(target, game, targetPlayerId);
    }

    @Override
    public boolean searchLibrary(TargetCardInLibrary target, Game game, UUID targetPlayerId, boolean triggerEvents) {
        return computerPlayer.searchLibrary(target, game, targetPlayerId, triggerEvents);
    }

    @Override
    public boolean flipCoin(Game game) {
        return computerPlayer.flipCoin(game);
    }

    @Override
    public boolean flipCoin(Game game, ArrayList<UUID> appliedEffects) {
        return computerPlayer.flipCoin(game, appliedEffects);
    }

    @Override
    public int rollDice(Game game, int numSides) {
        return computerPlayer.rollDice(game, numSides);
    }

    @Override
    public int rollDice(Game game, ArrayList<UUID> appliedEffects, int numSides) {
        return computerPlayer.rollDice(game, appliedEffects, numSides);
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

    public List<Permanent> getAvailableManaProducersWithCost(Game game) {
        return computerPlayer.getAvailableManaProducersWithCost(game);
    }

    @Override
    public List<Ability> getPlayable(Game game, boolean hidden) {
        return computerPlayer.getPlayable(game, hidden);
    }

    @Override
    public Set<UUID> getPlayableInHand(Game game) {
        return computerPlayer.getPlayableInHand(game);
    }

    @Override
    public List<Ability> getPlayableOptions(Ability ability, Game game) {
        return computerPlayer.getPlayableOptions(ability, game);
    }

    @Override
    public boolean isTestMode() {
        return computerPlayer.isTestMode();
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
    public boolean canPayLifeCost() {
        return computerPlayer.canPayLifeCost();
    }

    @Override
    public void setCanPayLifeCost(boolean canPayLifeCost) {
        computerPlayer.setCanPayLifeCost(canPayLifeCost);
    }

    @Override
    public boolean canPaySacrificeCost(Permanent permanent, UUID sourceId, UUID controllerId, Game game) {
        return computerPlayer.canPaySacrificeCost(permanent, sourceId, controllerId, game);
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
    public boolean lookAtFaceDownCard(Card card, Game game) {
        return computerPlayer.lookAtFaceDownCard(card, game);
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
    public boolean moveCardToHandWithInfo(Card card, UUID sourceId, Game game) {
        return computerPlayer.moveCardToHandWithInfo(card, sourceId, game);
    }

    @Override
    public boolean moveCardToHandWithInfo(Card card, UUID sourceId, Game game, boolean withName) {
        return computerPlayer.moveCardToHandWithInfo(card, sourceId, game, withName);
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
    public Set<Card> moveCardsToGraveyardWithInfo(Set<Card> allCards, Ability source, Game game, Zone fromZone) {
        return computerPlayer.moveCardsToGraveyardWithInfo(allCards, source, game, fromZone);
    }

    @Override
    public boolean moveCardToGraveyardWithInfo(Card card, UUID sourceId, Game game, Zone fromZone) {
        return computerPlayer.moveCardToGraveyardWithInfo(card, sourceId, game, fromZone);
    }

    @Override
    public boolean moveCardToLibraryWithInfo(Card card, UUID sourceId, Game game, Zone fromZone, boolean toTop, boolean withName) {
        return computerPlayer.moveCardToLibraryWithInfo(card, sourceId, game, fromZone, toTop, withName);
    }

    @Override
    public boolean moveCardToExileWithInfo(Card card, UUID exileId, String exileName, UUID sourceId, Game game, Zone fromZone, boolean withName) {
        return computerPlayer.moveCardToExileWithInfo(card, exileId, exileName, sourceId, game, fromZone, withName);
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
    public boolean isRequestToShowHandCardsAllowed() {
        return computerPlayer.isRequestToShowHandCardsAllowed();
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
    public SpellAbility chooseSpellAbilityForCast(SpellAbility ability, Game game, boolean noMana) {
        switch (ability.getSpellAbilityType()) {
            case SPLIT:
            case SPLIT_FUSED:
            case SPLIT_AFTERMATH:
                if (!choices.isEmpty()) {
                    MageObject object = game.getObject(ability.getSourceId());
                    if (object != null) {
                        LinkedHashMap<UUID, ActivatedAbility> useableAbilities = computerPlayer.getSpellAbilities(object, game.getState().getZone(object.getId()), game);
                        for (String choose : choices) {
                            for (ActivatedAbility actiavtedAbility : useableAbilities.values()) {
                                if (actiavtedAbility.getRule().startsWith(choose)) {
                                    return (SpellAbility) actiavtedAbility;
                                }
                            }
                        }
                    }
                }
        }
        return computerPlayer.chooseSpellAbilityForCast(ability, game, noMana);
    }

    @Override
    public void skip() {
        computerPlayer.skip();
    }

    @Override
    public boolean choose(Outcome outcome, Target target,
                          UUID sourceId, Game game
    ) {
        // needed to call here the TestPlayer because it's overwitten
        return choose(outcome, target, sourceId, game, null);
    }

    @Override
    public boolean choose(Outcome outcome, Cards cards,
                          TargetCard target, Game game
    ) {
        if (!choices.isEmpty()) {
            for (String choose2 : choices) {
                // TODO: More targetting to fix
                String[] targetList = choose2.split("\\^");
                boolean targetFound = false;
                for (String targetName : targetList) {
                    for (Card card : cards.getCards(game)) {
                        if (target.getTargets().contains(card.getId())) {
                            continue;
                        }
                        if (card.getName().equals(targetName)) {
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
        }
        return computerPlayer.choose(outcome, cards, target, game);
    }

    @Override
    public boolean chooseTargetAmount(Outcome outcome, TargetAmount target,
                                      Ability source, Game game
    ) {
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
                             String singleTargetName, UUID sourceId,
                             Game game
    ) {
        computerPlayer.assignDamage(damage, targets, singleTargetName, sourceId, game);
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
    public boolean moveCards(Set<Card> cards, Zone toZone,
                             Ability source, Game game
    ) {
        return computerPlayer.moveCards(cards, toZone, source, game);
    }

    @Override
    public boolean moveCards(Set<Card> cards, Zone toZone,
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
    public PlanarDieRoll rollPlanarDie(Game game) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PlanarDieRoll rollPlanarDie(Game game, ArrayList<UUID> appliedEffects) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PlanarDieRoll rollPlanarDie(Game game, ArrayList<UUID> appliedEffects, int numberChaosSides, int numberPlanarSides) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
}
