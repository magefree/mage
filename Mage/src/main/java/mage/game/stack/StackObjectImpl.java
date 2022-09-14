package mage.game.stack;

import mage.MageItem;
import mage.MageObject;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.choices.Choice;
import mage.choices.ChoiceHintType;
import mage.choices.ChoiceImpl;
import mage.constants.Outcome;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.events.CopyStackObjectEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetAmount;
import mage.util.CardUtil;
import mage.util.functions.StackObjectCopyApplier;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public abstract class StackObjectImpl implements StackObject {

    protected boolean targetChanged; // for Psychic Battle

    @Override
    public void createCopyOnStack(Game game, Ability source, UUID newControllerId, boolean chooseNewTargets) {
        createCopyOnStack(game, source, newControllerId, chooseNewTargets, 1);
    }

    @Override
    public void createCopyOnStack(Game game, Ability source, UUID newControllerId, boolean chooseNewTargets, int amount) {
        createCopyOnStack(game, source, newControllerId, chooseNewTargets, amount, null);
    }

    /**
     * Copy logic:
     * - multiple copies allows
     * - new targets for each copy allows
     * - each next copy can have new target type/filter
     * - player can choose copies order on stack
     * <p>
     * Code logic:
     * - find all possible target types (any target or custom)
     * - user can choose next target type to put on stack
     * - put all copies with that target type and request next choice
     * - implemented by iterator mechanic (target type -> target filter)
     */
    private static final class NewTargetTypeIterator implements Iterator<MageObjectReferencePredicate> {

        private final StackObjectCopyApplier applier;
        private final Player player;
        private final int amount;
        private final Game game;
        private Map<String, MageObjectReferencePredicate> newTargetTypes = null;
        private Iterator<MageObjectReferencePredicate> currentNewTargetType = null;
        private Choice newTargetTypeChoiceDialog = null;

        private NewTargetTypeIterator(Game game, UUID newControllerId, int amount, StackObjectCopyApplier applier) {
            this.applier = applier;
            this.player = game.getPlayer(newControllerId);
            this.amount = amount;
            this.game = game;
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        private void prepareNewTargetTypes() {
            if (newTargetTypes != null) {
                // already prepared
                return;
            }

            int currentAnyTargetNumber = 0;
            int currentFilteredTargetNumber = 0;

            newTargetTypes = new HashMap<>();
            for (int i = 0; i < amount; i++) {
                MageObjectReferencePredicate newTargetType = applier.getNextNewTargetType();
                if (newTargetType == null) {
                    currentAnyTargetNumber++;
                    String message = "Any target";
                    if (currentAnyTargetNumber > 1) {
                        message += " (" + currentAnyTargetNumber + ")";
                    }
                    newTargetTypes.put(message, null);
                    continue;
                }
                currentFilteredTargetNumber++;
                newTargetTypes.put(newTargetType.getName(game), newTargetType);
            }

            // if only one target type then choose it by default
            if ((currentFilteredTargetNumber == 1 && currentAnyTargetNumber == 0) || currentFilteredTargetNumber == 0) {
                currentNewTargetType = newTargetTypes.values().stream().collect(Collectors.toList()).iterator();
            }
        }

        private void prepareFilterChooseDialog() {
            // used choices removes from the dialog
            if (newTargetTypeChoiceDialog != null) {
                // already prepared
                return;
            }
            newTargetTypeChoiceDialog = new ChoiceImpl(false, ChoiceHintType.CARD);
            newTargetTypeChoiceDialog.setMessage("Choose the order of copies to go on the stack");
            newTargetTypeChoiceDialog.setSubMessage("Press cancel to put the rest in any order");
            newTargetTypeChoiceDialog.setChoices(new HashSet<>(newTargetTypes.keySet()));
        }

        @Override
        public MageObjectReferencePredicate next() {
            if (player == null || applier == null) {
                return null;
            }

            prepareNewTargetTypes();
            if (currentNewTargetType != null) {
                // target type already selected
                return currentNewTargetType.hasNext() ? currentNewTargetType.next() : null;
            }

            prepareFilterChooseDialog();
            if (newTargetTypeChoiceDialog.getChoices().size() < 2) {
                // no more unused target types - select the last one
                currentNewTargetType = newTargetTypeChoiceDialog.getChoices().stream().map(newTargetTypes::get).iterator();
                return next();
            }

            // choose next target type for usage
            newTargetTypeChoiceDialog.clearChoice();
            player.choose(Outcome.AIDontUseIt, newTargetTypeChoiceDialog, game);
            String chosen = newTargetTypeChoiceDialog.getChoice();
            if (chosen == null) {
                currentNewTargetType = newTargetTypeChoiceDialog.getChoices().stream().map(newTargetTypes::get).iterator();
                return next();
            }
            newTargetTypeChoiceDialog.getChoices().remove(chosen);
            return newTargetTypes.get(chosen);
        }
    }

    @Override
    public void createCopyOnStack(Game game, Ability source, UUID newControllerId, boolean chooseNewTargets, int amount, StackObjectCopyApplier applier) {
        GameEvent gameEvent = new CopyStackObjectEvent(source, this, newControllerId, amount);
        if (game.replaceEvent(gameEvent)) {
            return;
        }
        Iterator<MageObjectReferencePredicate> newTargetTypeIterator = new NewTargetTypeIterator(game, newControllerId, gameEvent.getAmount(), applier);
        for (int i = 0; i < gameEvent.getAmount(); i++) {
            createSingleCopy(newControllerId, applier, newTargetTypeIterator.next(), game, source, chooseNewTargets);
        }
        Player player = game.getPlayer(newControllerId);
        if (player == null) {
            return;
        }
        game.informPlayers(
                player.getName() + " created " + CardUtil.numberToText(gameEvent.getAmount(), "a")
                        + " cop" + (gameEvent.getAmount() == 1 ? "y" : "ies") + " of " + getIdName()
        );
    }

    /**
     * Choose new targets for a stack Object
     *
     * @param game
     * @param playerId Player UUID who changes the targets.
     * @return
     */
    public boolean chooseNewTargets(Game game, UUID playerId) {
        return chooseNewTargets(game, playerId, false, false, null);
    }

    /**
     * 114.6. Some effects allow a player to change the target(s) of a spell or
     * ability, and other effects allow a player to choose new targets for a
     * spell or ability.
     * <p>
     * 114.6a If an effect allows a player to "change the target(s)" of a spell
     * or ability, each target can be changed only to another legal target. If a
     * target can't be changed to another legal target, the original target is
     * unchanged, even if the original target is itself illegal by then. If all
     * the targets aren't changed to other legal targets, none of them are
     * changed.
     * <p>
     * 114.6b If an effect allows a player to "change a target" of a spell or
     * ability, the process described in rule 114.6a is followed, except that
     * only one of those targets may be changed (rather than all of them or none
     * of them).
     * <p>
     * 114.6c If an effect allows a player to "change any targets" of a spell or
     * ability, the process described in rule 114.6a is followed, except that
     * any number of those targets may be changed (rather than all of them or
     * none of them).
     * <p>
     * 114.6d If an effect allows a player to "choose new targets" for a spell
     * or ability, the player may leave any number of the targets unchanged,
     * even if those targets would be illegal. If the player chooses to change
     * some or all of the targets, the new targets must be legal and must not
     * cause any unchanged targets to become illegal.
     * <p>
     * 114.6e When changing targets or choosing new targets for a spell or
     * ability, only the final set of targets is evaluated to determine whether
     * the change is legal.
     * <p>
     * Example: Arc Trail is a sorcery that reads "Arc Trail deals 2 damage to
     * any target and 1 damage to another target creature or player." The
     * current targets of Arc Trail are Runeclaw Bear and Llanowar Elves, in
     * that order. You cast Redirect, an instant that reads "You may choose new
     * targets for target spell," targeting Arc Trail. You can change the first
     * target to Llanowar Elves and change the second target to Runeclaw Bear.
     * <p>
     * 114.7. Modal spells and abilities may have different targeting
     * requirements for each mode. An effect that allows a player to change the
     * target(s) of a modal spell or ability, or to choose new targets for a
     * modal spell or ability, doesn't allow that player to change its mode.
     * (See rule 700.2.)
     * <p>
     * 706.10c Some effects copy a spell or ability and state that its
     * controller may choose new targets for the copy. The player may leave any
     * number of the targets unchanged, even if those targets would be illegal.
     * If the player chooses to change some or all of the targets, the new
     * targets must be legal. Once the player has decided what the copy's
     * targets will be, the copy is put onto the stack with those targets.
     *
     * @param game
     * @param targetControllerId       - player that can/has to change the target of
     *                                 the spell
     * @param forceChange              - does only work for targets with maximum of one
     *                                 targetId
     * @param onlyOneTarget            - 114.6b one target must be changed to another
     *                                 target
     * @param newTargetFilterPredicate restriction for the new target (null - can select same targets)
     * @return
     */
    @Override
    public boolean chooseNewTargets(Game game, UUID targetControllerId, boolean forceChange, boolean onlyOneTarget, Predicate<MageItem> newTargetFilterPredicate) {
        Player targetController = game.getPlayer(targetControllerId);
        if (targetController != null) {
            StringBuilder oldTargetDescription = new StringBuilder();
            StringBuilder newTargetDescription = new StringBuilder();
            // Fused split spells or spells where "Splice on Arcane" was used can have more than one ability
            Abilities<Ability> objectAbilities = new AbilitiesImpl<>();
            if (this instanceof Spell) {
                objectAbilities.addAll(((Spell) this).getSpellAbilities());
            } else {
                objectAbilities.add(getStackAbility());
            }
            for (Ability ability : objectAbilities) {
                // Some spells can have more than one mode
                for (UUID modeId : ability.getModes().getSelectedModes()) {
                    Mode mode = ability.getModes().get(modeId);
                    ability.getModes().setActiveMode(mode);
                    oldTargetDescription.append(ability.getTargetDescription(mode.getTargets(), game));
                    for (Target target : mode.getTargets()) {
                        Target newTarget = chooseNewTarget(targetController, ability, mode, target, forceChange, newTargetFilterPredicate, game);
                        // clear the old target and copy all targets from new target
                        target.clearChosen();
                        for (UUID targetId : newTarget.getTargets()) {
                            target.addTarget(targetId, newTarget.getTargetAmount(targetId), ability, game, false);
                        }

                    }
                    newTargetDescription.append(ability.getTargetDescription(mode.getTargets(), game));
                }

            }
            if (!newTargetDescription.toString().equals(oldTargetDescription.toString()) && !game.isSimulation()) {
                game.informPlayers(this.getLogName() + " is now " + newTargetDescription);
            }
            return true;
        }
        return false;
    }

    /**
     * Handles the change of one target instance of a mode
     *
     * @param targetController         - player that can choose the new target
     * @param ability
     * @param mode
     * @param target
     * @param forceChange
     * @param newTargetFilterPredicate
     * @param game
     * @return
     */
    private Target chooseNewTarget(Player targetController, Ability ability, Mode mode, Target target, boolean forceChange, Predicate newTargetFilterPredicate, Game game) {
        Target newTarget = target.copy();

        // filter targets
        if (newTargetFilterPredicate != null) {
            newTarget.getFilter().add(newTargetFilterPredicate);
            // If adding a predicate, there will only be one choice and therefore target can be automatic
            newTarget.setRandom(true);
        }

        newTarget.setEventReporting(false);
        if (!targetController.getId().equals(getControllerId())) {
            newTarget.setTargetController(targetController.getId()); // target controller for the change is different from spell controller
            newTarget.setAbilityController(getControllerId());
        }
        newTarget.clearChosen();
        for (UUID targetId : target.getTargets()) {
            String targetNames = getNamesOftargets(targetId, game);
            String targetAmount = "";
            if (target.getTargetAmount(targetId) > 0) {
                targetAmount = " (amount: " + target.getTargetAmount(targetId) + ")";
            }
            // change the target?
            Outcome outcome = mode.getEffects().getOutcome(ability);

            if (targetNames != null
                    && (forceChange || targetController.chooseUse(outcome, "Change this target: " + targetNames + targetAmount + '?', ability, game))) {
                Set<UUID> possibleTargets = target.possibleTargets(getControllerId(), ability, game);
                // choose exactly one other target - already targeted objects are not counted
                if (forceChange && possibleTargets != null && possibleTargets.size() > 1) { // controller of spell must be used (e.g. TargetOpponent)
                    int iteration = 0;
                    do {
                        if (iteration > 0 && !game.isSimulation()) {
                            game.informPlayer(targetController, "You may only select exactly one target that must be different from the origin target!");
                        }
                        iteration++;
                        newTarget.clearChosen();

                        newTarget.chooseTarget(outcome, getControllerId(), ability, game);

                        // workaround to stop infinite AI choose (remove after chooseTarget can be called with extra filter to disable some ids)
                        if (iteration > 10) {
                            break;
                        }
                    }
                    while (targetController.canRespond() && (targetId.equals(newTarget.getFirstTarget()) || newTarget.getTargets().size() != 1));
                    // choose a new target
                } else {
                    // build a target definition with exactly one possible target to select that replaces old target
                    Target tempTarget = target.copy();
                    if (newTargetFilterPredicate != null) {
                        tempTarget.getFilter().add(newTargetFilterPredicate);
                        // If adding a predicate, there will only be one choice and therefore target can be automatic
                        tempTarget.setRandom(true);
                    }
                    tempTarget.setEventReporting(false);
                    if (target instanceof TargetAmount) {
                        ((TargetAmount) tempTarget).setAmountDefinition(StaticValue.get(target.getTargetAmount(targetId)));
                    }
                    tempTarget.setMinNumberOfTargets(1);
                    tempTarget.setMaxNumberOfTargets(1);
                    if (!targetController.getId().equals(getControllerId())) {
                        tempTarget.setTargetController(targetController.getId());
                        tempTarget.setAbilityController(getControllerId());
                    }
                    boolean again;
                    do {
                        again = false;
                        tempTarget.clearChosen();
                        if (!tempTarget.chooseTarget(outcome, getControllerId(), ability, game)) {
                            if (targetController.chooseUse(Outcome.Benefit, "No target object selected. Reset to original target?", ability, game)) {
                                // use previous target no target was selected
                                newTarget.addTarget(targetId, target.getTargetAmount(targetId), ability, game, true);
                            } else {
                                again = true;
                            }
                        } else // if possible add the alternate Target - it may not be included in the old definition nor in the already selected targets of the new definition
                        {
                            if (newTarget.getTargets().contains(tempTarget.getFirstTarget()) || target.getTargets().contains(tempTarget.getFirstTarget())) {
                                if (targetController.isHuman()) {
                                    if (targetController.chooseUse(Outcome.Benefit, "This target was already selected from origin spell. Reset to original target?", ability, game)) {
                                        // use previous target no target was selected
                                        newTarget.addTarget(targetId, target.getTargetAmount(targetId), ability, game, true);
                                    } else {
                                        again = true;
                                    }
                                } else {
                                    newTarget.addTarget(targetId, target.getTargetAmount(targetId), ability, game, true);
                                }
                            } else if (!target.canTarget(getControllerId(), tempTarget.getFirstTarget(), ability, game)) {
                                if (targetController.isHuman()) {
                                    game.informPlayer(targetController, "This target is not valid!");
                                    again = true;
                                } else {
                                    // keep the old
                                    newTarget.addTarget(targetId, target.getTargetAmount(targetId), ability, game, true);
                                }
                            } else {
                                // valid target was selected, add it to the new target definition
                                newTarget.addTarget(tempTarget.getFirstTarget(), target.getTargetAmount(targetId), ability, game, true);
                            }
                        }
                    } while (again && targetController.canRespond());
                }
            } // keep the target
            else {
                newTarget.addTarget(targetId, target.getTargetAmount(targetId), ability, game, true);
            }
        }
        return newTarget;
    }

    @Override
    public boolean canTarget(Game game, UUID targetId) {
        Abilities<Ability> objectAbilities = new AbilitiesImpl<>();
        if (this instanceof Spell) {
            objectAbilities.addAll(((Spell) this).getSpellAbilities());
        } else {
            objectAbilities.add(getStackAbility());
        }
        for (Ability ability : objectAbilities) {
            if (ability.getModes()
                    .getSelectedModes()
                    .stream()
                    .map(ability.getModes()::get)
                    .filter(Objects::nonNull)
                    .map(Mode::getTargets)
                    .flatMap(Collection::stream)
                    .filter(t -> !t.isNotTarget())
                    .anyMatch(t -> t.canTarget(ability.getControllerId(), targetId, ability, game))) {
                return true;
            }
        }
        return false;
    }

    private String getNamesOftargets(UUID targetId, Game game) {
        MageObject object = game.getObject(targetId);
        String name = null;
        if (object == null) {
            Player targetPlayer = game.getPlayer(targetId);
            if (targetPlayer != null) {
                name = targetPlayer.getLogName();
            }
        } else {
            name = object.getIdName();
        }
        return name;
    }

    @Override
    public void removePTCDA() {
    }

    @Override
    public boolean isTargetChanged() {
        return targetChanged;
    }

    @Override
    public void setTargetChanged(boolean targetChanged) {
        this.targetChanged = targetChanged;
    }
}
