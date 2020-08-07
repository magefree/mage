package mage.abilities.effects.common;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DontUntapInControllersUntapStepTargetEffect extends ContinuousRuleModifyingEffectImpl {

    public DontUntapInControllersUntapStepTargetEffect(Duration duration) {
        super(duration, Outcome.Detriment);
    }

    public DontUntapInControllersUntapStepTargetEffect(final DontUntapInControllersUntapStepTargetEffect effect) {
        super(effect);
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
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        Permanent permanentToUntap = game.getPermanent((event.getTargetId()));
        if (permanentToUntap != null && mageObject != null) {
            return permanentToUntap.getIdName() + " doesn't untap (" + mageObject.getIdName() + ')';
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getTurn().getStepType() == PhaseStep.UNTAP) {
            for (UUID targetId : targetPointer.getTargets(game, source)) {
                if (event.getTargetId().equals(targetId)) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null && game.isActivePlayer(permanent.getControllerId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null) {
            return staticText;
        }
        return "target " + mode.getTargets().get(0).getTargetName()
                + " doesn't untap during its controller's untap step" + (getDuration().toString().isEmpty() ? "" : " " + getDuration());
    }

}
