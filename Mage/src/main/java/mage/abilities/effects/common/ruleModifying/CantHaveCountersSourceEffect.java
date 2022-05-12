
package mage.abilities.effects.common.ruleModifying;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author Styxo
 */
public class CantHaveCountersSourceEffect extends ContinuousRuleModifyingEffectImpl {

    public CantHaveCountersSourceEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "{this} can't have counters put on it";
    }

    public CantHaveCountersSourceEffect(final CantHaveCountersSourceEffect effect) {
        super(effect);
    }

    @Override
    public CantHaveCountersSourceEffect copy() {
        return new CantHaveCountersSourceEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        UUID sourceId = source != null ? source.getSourceId() : null;
        if (sourceId != null) {
            return sourceId.equals(event.getTargetId());
        }
        return false;
    }
}
