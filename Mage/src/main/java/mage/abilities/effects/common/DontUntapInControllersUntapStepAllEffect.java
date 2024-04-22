package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */

public class DontUntapInControllersUntapStepAllEffect extends ContinuousRuleModifyingEffectImpl {

    private final TargetController targetController;
    private final FilterPermanent filter;

    public DontUntapInControllersUntapStepAllEffect(Duration duration, TargetController targetController, FilterPermanent filter) {
        super(duration, Outcome.Detriment, false, false);
        this.targetController = targetController;
        this.filter = filter;
        String text = filter.getMessage() + " don't untap during ";
        switch (targetController) {
            case ANY:
                text += "their controllers'";
                break;
            case YOU:
                text += "your";
                break;
            default:
                throw new IllegalArgumentException("TargetController not supported in DontUntapInControllersNextUntapStepAllEffect");
        }
        staticText = text + (duration == Duration.UntilYourNextTurn ? " next untap step" : " untap steps");
    }

    protected DontUntapInControllersUntapStepAllEffect(final DontUntapInControllersUntapStepAllEffect effect) {
        super(effect);
        this.targetController = effect.targetController;
        this.filter = effect.filter;
    }

    @Override
    public DontUntapInControllersUntapStepAllEffect copy() {
        return new DontUntapInControllersUntapStepAllEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (game.getTurnStepType() != PhaseStep.UNTAP || permanent == null) {
            return false;
        }
        switch (targetController) {
            case YOU:
                if (!permanent.isControlledBy(source.getControllerId())) {
                    return false;
                }
                break;
            case ANY:
                break;
            default:
                throw new IllegalArgumentException("TargetController not supported in DontUntapInControllersNextUntapStepAllEffect");
        }
        return game.isActivePlayer(permanent.getControllerId()) && // controller's untap step
                filter.match(permanent, source.getControllerId(), source, game);
    }

}
