

package mage.abilities.effects.common.ruleModifying;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 * @author LevelX2
 */

public class CantRegenerateTargetEffect extends ContinuousRuleModifyingEffectImpl {

    public CantRegenerateTargetEffect(Duration duration, String objectText) {
        super(duration, Outcome.Detriment);
        staticText = objectText + " can't be regenerated this turn";
    }

    protected CantRegenerateTargetEffect(final CantRegenerateTargetEffect effect) {
        super(effect);
    }

    @Override
    public CantRegenerateTargetEffect copy() {
        return new CantRegenerateTargetEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.REGENERATE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        UUID targetId = getTargetPointer().getFirst(game, source);
        if (targetId != null) {
            return targetId.equals(event.getTargetId());
        }
        return false;
    }
}
