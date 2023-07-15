package mage.abilities.effects.common;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.Targets;

/**
 *
 * @author LevelX2
 */
public class ChangeATargetOfTargetSpellAbilityToSourceEffect extends OneShotEffect {

    public ChangeATargetOfTargetSpellAbilityToSourceEffect() {
        super(Outcome.Neutral);
        staticText = "change a target of target spell or ability to {this}";
    }

    public ChangeATargetOfTargetSpellAbilityToSourceEffect(final ChangeATargetOfTargetSpellAbilityToSourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject stackObject = game.getStack().getStackObject(source.getFirstTarget());
        MageObject sourceObject = game.getObject(source);
        if (stackObject != null && sourceObject != null) {
            Targets targets = new Targets();
            Ability sourceAbility;
            String oldTargetName = null;
            if (stackObject instanceof Spell) {
                Spell spell = (Spell) stackObject;
                sourceAbility = spell.getSpellAbility();
            } else if (stackObject instanceof StackAbility) {
                StackAbility stackAbility = (StackAbility) stackObject;
                sourceAbility = stackAbility;
            } else {
                return false;
            }
            for (UUID modeId : sourceAbility.getModes().getSelectedModes()) {
                Mode mode = sourceAbility.getModes().get(modeId);
                targets.addAll(mode.getTargets());
            }

            boolean twoTimesTarget = false;
            if (targets.size() == 1 && targets.get(0).getTargets().size() == 1) {
                Target target = targets.get(0);
                if (target.getFirstTarget().equals(source.getSourceId())) {
                    return true; // Target was already the same source, so no change / target event to create
                }
                if (target.canTarget(stackObject.getControllerId(), source.getSourceId(), sourceAbility, game)) {
                    oldTargetName = getTargetName(targets.getFirstTarget(), game);
                    target.clearChosen();
                    // The source is still the spell on the stack
                    target.addTarget(source.getSourceId(), stackObject.getStackAbility(), game);
                }
            } else {
                Player controller = game.getPlayer(source.getControllerId());
                boolean validTargets = false;
                do {
                    for (Target target : targets) {
                        for (UUID targetId : target.getTargets()) {
                            String name = getTargetName(targets.getFirstTarget(), game);
                            if (!targetId.equals(source.getSourceId()) && target.getTargets().contains(source.getSourceId())) {
                                // you can't change this target to source because the source is already another targetId of that target.
                                twoTimesTarget = true;
                                continue;
                            }
                            if (target.canTarget(stackObject.getControllerId(), source.getSourceId(), sourceAbility, game)) {
                                validTargets = true;
                                if (name != null
                                        && controller.chooseUse(Outcome.Neutral, "Change target from " + name + " to " + sourceObject.getLogName() + '?', source, game)) {
                                    oldTargetName = getTargetName(targetId, game);
                                    target.remove(targetId);
                                    // The source is still the spell on the stack
                                    target.addTarget(source.getSourceId(), stackObject.getStackAbility(), game);
                                    break;
                                }
                            }
                        }
                        if (oldTargetName != null) {
                            break;
                        }
                    }
                    if (oldTargetName == null) {
                        game.informPlayer(controller, "You have to select at least one target to change to " + sourceObject.getIdName() + '!');
                    }
                } while (validTargets && oldTargetName == null);
            }
            if (oldTargetName != null) {
                game.informPlayers(sourceObject.getLogName() + ": Changed target of " + stackObject.getLogName() + " from " + oldTargetName + " to " + sourceObject.getLogName());
            } else if (twoTimesTarget) {
                game.informPlayers(sourceObject.getLogName() + ": Target not changed to " + sourceObject.getLogName() + " because its not valid to target it twice for " + stackObject.getLogName());
            } else {
                game.informPlayers(sourceObject.getLogName() + ": Target not changed to " + sourceObject.getLogName() + " because its no valid target for " + stackObject.getLogName());
            }
            return true;
        }
        return false;
    }

    @Override
    public ChangeATargetOfTargetSpellAbilityToSourceEffect copy() {
        return new ChangeATargetOfTargetSpellAbilityToSourceEffect(this);
    }

    private String getTargetName(UUID objectId, Game game) {
        MageObject object = game.getObject(objectId);
        if (object != null) {
            return object.getLogName();
        }
        Player player = game.getPlayer(objectId);
        if (player != null) {
            return player.getLogName();
        }
        return null;
    }
}
