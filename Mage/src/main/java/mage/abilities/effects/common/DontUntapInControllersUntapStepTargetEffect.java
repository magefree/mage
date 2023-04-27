package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DontUntapInControllersUntapStepTargetEffect extends ContinuousRuleModifyingEffectImpl {

    private final String targetName;

    public DontUntapInControllersUntapStepTargetEffect(Duration duration) {
        this(duration, "That creature");
    }

    public DontUntapInControllersUntapStepTargetEffect(Duration duration, String targetName) {
        super(duration, Outcome.Detriment);
        this.targetName = targetName;
    }

    public DontUntapInControllersUntapStepTargetEffect(final DontUntapInControllersUntapStepTargetEffect effect) {
        super(effect);
        this.targetName = effect.targetName;
    }

    @Override
    public DontUntapInControllersUntapStepTargetEffect copy() {
        return new DontUntapInControllersUntapStepTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getTurnStepType() != PhaseStep.UNTAP) {
            return false;
        }
        for (UUID targetId : targetPointer.getTargets(game, source)) {
            if (!event.getTargetId().equals(targetId)) {
                continue;
            }
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null && game.isActivePlayer(permanent.getControllerId())) {
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
        return targetName + " doesn't untap during its controller's untap step"
                + (getDuration().toString().isEmpty() ? "" : " ") + getDuration();
    }

}
