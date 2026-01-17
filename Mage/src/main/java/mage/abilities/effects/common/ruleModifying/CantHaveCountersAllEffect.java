
package mage.abilities.effects.common.ruleModifying;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public class CantHaveCountersAllEffect extends ContinuousRuleModifyingEffectImpl {

    private final FilterPermanent filter;
    private final CounterType counterType;

    public CantHaveCountersAllEffect(FilterPermanent filter, CounterType counterType) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.filter = filter;
        this.counterType = counterType;
        staticText = filter.getMessage() + " can't have " +
                (counterType != null ? counterType.getName() + ' ' : "") +
                "counters put on them";
    }

    protected CantHaveCountersAllEffect(final CantHaveCountersAllEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.counterType = effect.counterType;
    }

    @Override
    public CantHaveCountersAllEffect copy() {
        return new CantHaveCountersAllEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAN_ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (counterType != null && !counterType.getName().equals(event.getData())) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null) {
            permanent = game.getPermanentEntering(event.getTargetId());
        }
        return permanent != null && filter.match(permanent, source.getControllerId(), source, game);
    }
}
