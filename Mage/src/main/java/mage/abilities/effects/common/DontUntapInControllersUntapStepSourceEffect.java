
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public class DontUntapInControllersUntapStepSourceEffect extends ContinuousRuleModifyingEffectImpl {

    public DontUntapInControllersUntapStepSourceEffect() {
        this(false, true);
    }

    public DontUntapInControllersUntapStepSourceEffect(boolean messageToUser, boolean messageToLog) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, messageToUser, messageToLog);
        staticText = "{this} doesn't untap during your untap step";
    }

    public DontUntapInControllersUntapStepSourceEffect(final DontUntapInControllersUntapStepSourceEffect effect) {
        super(effect);
    }

    @Override
    public DontUntapInControllersUntapStepSourceEffect copy() {
        return new DontUntapInControllersUntapStepSourceEffect(this);
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
        if (game.getTurnStepType() == PhaseStep.UNTAP
                && event.getTargetId().equals(source.getSourceId())) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null && permanent.isControlledBy(game.getActivePlayerId())) {
                return true;
            }
        }
        return false;
    }

}
