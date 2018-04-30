/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.game.stack;

import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetAmount;

/**
 *
 * @author LevelX2
 */
public abstract class StackObjImpl implements StackObject {

    protected boolean targetChanged; // for Psychic Battle

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
     *
     * 114.6a If an effect allows a player to "change the target(s)" of a spell
     * or ability, each target can be changed only to another legal target. If a
     * target can't be changed to another legal target, the original target is
     * unchanged, even if the original target is itself illegal by then. If all
     * the targets aren't changed to other legal targets, none of them are
     * changed.
     *
     * 114.6b If an effect allows a player to "change a target" of a spell or
     * ability, the process described in rule 114.6a is followed, except that
     * only one of those targets may be changed (rather than all of them or none
     * of them).
     *
     * 114.6c If an effect allows a player to "change any targets" of a spell or
     * ability, the process described in rule 114.6a is followed, except that
     * any number of those targets may be changed (rather than all of them or
     * none of them).
     *
     * 114.6d If an effect allows a player to "choose new targets" for a spell
     * or ability, the player may leave any number of the targets unchanged,
     * even if those targets would be illegal. If the player chooses to change
     * some or all of the targets, the new targets must be legal and must not
     * cause any unchanged targets to become illegal.
     *
     * 114.6e When changing targets or choosing new targets for a spell or
     * ability, only the final set of targets is evaluated to determine whether
     * the change is legal.
     *
     * Example: Arc Trail is a sorcery that reads "Arc Trail deals 2 damage to
     * any target and 1 damage to another target creature or player." The
     * current targets of Arc Trail are Runeclaw Bear and Llanowar Elves, in
     * that order. You cast Redirect, an instant that reads "You may choose new
     * targets for target spell," targeting Arc Trail. You can change the first
     * target to Llanowar Elves and change the second target to Runeclaw Bear.
     *
     * 114.7. Modal spells and abilities may have different targeting
     * requirements for each mode. An effect that allows a player to change the
     * target(s) of a modal spell or ability, or to choose new targets for a
     * modal spell or ability, doesn't allow that player to change its mode.
     * (See rule 700.2.)
     *
     * 706.10c Some effects copy a spell or ability and state that its
     * controller may choose new targets for the copy. The player may leave any
     * number of the targets unchanged, even if those targets would be illegal.
     * If the player chooses to change some or all of the targets, the new
     * targets must be legal. Once the player has decided what the copy's
     * targets will be, the copy is put onto the stack with those targets.
     *
     * @param game
     * @param targetControllerId - player that can/has to change the target of
     * the spell
     * @param forceChange - does only work for targets with maximum of one
     * targetId
     * @param onlyOneTarget - 114.6b one target must be changed to another
     * target
     * @param filterNewTarget restriction for the new target, if null nothing is
     * cheched
     * @return
     */
    @Override
    public boolean chooseNewTargets(Game game, UUID targetControllerId, boolean forceChange, boolean onlyOneTarget, FilterPermanent filterNewTarget) {
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
                        Target newTarget = chooseNewTarget(targetController, ability, mode, target, forceChange, filterNewTarget, game);
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
     * @param targetController - player that can choose the new target
     * @param ability
     * @param mode
     * @param target
     * @param forceChange
     * @param game
     * @return
     */
    private Target chooseNewTarget(Player targetController, Ability ability, Mode mode, Target target, boolean forceChange, FilterPermanent filterNewTarget, Game game) {
        Target newTarget = target.copy();
        if (!targetController.getId().equals(getControllerId())) {
            newTarget.setTargetController(targetController.getId()); // target controller for the change is different from spell controller
            newTarget.setAbilityController(getControllerId());
        }
        newTarget.clearChosen();
        for (UUID targetId : target.getTargets()) {
            String targetNames = getNamesOftargets(targetId, game);
            // change the target?
            Outcome outcome = mode.getEffects().isEmpty() ? Outcome.Detriment : mode.getEffects().get(0).getOutcome();
            if (targetNames != null
                    && (forceChange || targetController.chooseUse(outcome, "Change this target: " + targetNames + '?', ability, game))) {
                Set<UUID> possibleTargets = target.possibleTargets(this.getSourceId(), getControllerId(), game);
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
                        // check target restriction
                        if (newTarget.getFirstTarget() != null && filterNewTarget != null) {
                            Permanent newTargetPermanent = game.getPermanent(newTarget.getFirstTarget());
                            if (newTargetPermanent == null || !filterNewTarget.match(newTargetPermanent, game)) {
                                game.informPlayer(targetController, "Target does not fullfil the target requirements (" + filterNewTarget.getMessage() + ')');
                                newTarget.clearChosen();
                            }
                        }
                    } while (targetController.canRespond() && (targetId.equals(newTarget.getFirstTarget()) || newTarget.getTargets().size() != 1));
                    // choose a new target
                } else {
                    // build a target definition with exactly one possible target to select that replaces old target
                    Target tempTarget = target.copy();
                    if (target instanceof TargetAmount) {
                        ((TargetAmount) tempTarget).setAmountDefinition(new StaticValue(target.getTargetAmount(targetId)));
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
                                newTarget.addTarget(targetId, target.getTargetAmount(targetId), ability, game, false);
                            } else {
                                again = true;
                            }
                        } else // if possible add the alternate Target - it may not be included in the old definition nor in the already selected targets of the new definition
                        {
                            if (newTarget.getTargets().contains(tempTarget.getFirstTarget()) || target.getTargets().contains(tempTarget.getFirstTarget())) {
                                if (targetController.isHuman()) {
                                    if (targetController.chooseUse(Outcome.Benefit, "This target was already selected from origin spell. Reset to original target?", ability, game)) {
                                        // use previous target no target was selected
                                        newTarget.addTarget(targetId, target.getTargetAmount(targetId), ability, game, false);
                                    } else {
                                        again = true;
                                    }
                                } else {
                                    newTarget.addTarget(targetId, target.getTargetAmount(targetId), ability, game, false);
                                }
                            } else if (!target.canTarget(getControllerId(), tempTarget.getFirstTarget(), ability, game)) {
                                if (targetController.isHuman()) {
                                    game.informPlayer(targetController, "This target is not valid!");
                                    again = true;
                                } else {
                                    // keep the old
                                    newTarget.addTarget(targetId, target.getTargetAmount(targetId), ability, game, false);
                                }
                            } else if (newTarget.getFirstTarget() != null && filterNewTarget != null) {
                                Permanent newTargetPermanent = game.getPermanent(newTarget.getFirstTarget());
                                if (newTargetPermanent == null || !filterNewTarget.match(newTargetPermanent, game)) {
                                    game.informPlayer(targetController, "This target does not fullfil the target requirements (" + filterNewTarget.getMessage() + ')');
                                    again = true;
                                }
                            } else {
                                // valid target was selected, add it to the new target definition
                                newTarget.addTarget(tempTarget.getFirstTarget(), target.getTargetAmount(targetId), ability, game, false);
                            }
                        }
                    } while (again && targetController.canRespond());
                }
            } // keep the target
            else {
                newTarget.addTarget(targetId, target.getTargetAmount(targetId), ability, game, false);
            }
        }
        return newTarget;
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
            name = object.getName();
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
