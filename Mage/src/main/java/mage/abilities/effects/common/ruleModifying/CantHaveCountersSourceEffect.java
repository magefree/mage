package mage.abilities.effects.common.ruleModifying;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.Objects;

/**
 * @author Styxo
 */
public class CantHaveCountersSourceEffect extends ContinuousRuleModifyingEffectImpl {

    public CantHaveCountersSourceEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "{this} can't have counters put on it";
    }

    protected CantHaveCountersSourceEffect(final CantHaveCountersSourceEffect effect) {
        super(effect);
    }

    @Override
    public CantHaveCountersSourceEffect copy() {
        return new CantHaveCountersSourceEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAN_ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source != null && Objects.equals(source.getSourceId(), event.getTargetId());
    }
}
