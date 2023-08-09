package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterObject;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;

/**
 *
 * @author LevelX2
 */
public class CantBeTargetedTargetEffect extends ContinuousRuleModifyingEffectImpl {

    private final FilterObject filterSource;
    private final TargetController targetController;

    public CantBeTargetedTargetEffect(FilterObject filterSource, Duration duration, TargetController targetController) {
        super(duration, Outcome.Benefit, false, false);
        this.targetController = targetController;
        this.filterSource = filterSource;
    }

    protected CantBeTargetedTargetEffect(final CantBeTargetedTargetEffect effect) {
        super(effect);
        this.filterSource = effect.filterSource.copy();
        this.targetController = effect.targetController;
    }

    @Override
    public CantBeTargetedTargetEffect copy() {
        return new CantBeTargetedTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGET;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (getTargetPointer().getTargets(game, source).contains(event.getTargetId())) {
            if (targetController == TargetController.OPPONENT
                    && !game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
                return false;
            }
            StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
            MageObject sourceObject;
            if (stackObject instanceof StackAbility) {
                sourceObject = ((StackAbility) stackObject).getSourceObject(game);
            } else {
                sourceObject = stackObject;
            }
            if (filterSource.match(sourceObject, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder(getTargetPointer().describeTargets(mode.getTargets(), "it"));
        sb.append(" can't be the target of ");
        sb.append(filterSource.getMessage());
        if (!duration.toString().isEmpty()) {
            sb.append(' ');
            if (duration == Duration.EndOfTurn) {
                sb.append("this turn");
            } else {
                sb.append(duration.toString());
            }
        }
        return sb.toString();
    }

}
