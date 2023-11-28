
package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author emerald000
 */
public class CantHaveMoreThanAmountCountersSourceAbility extends SimpleStaticAbility {

    private final CounterType counterType;
    private final int amount;

    public CantHaveMoreThanAmountCountersSourceAbility(CounterType counterType, int amount) {
        super(Zone.BATTLEFIELD, new CantHaveMoreThanAmountCountersSourceEffect(counterType, amount));
        this.counterType = counterType;
        this.amount = amount;
    }

    private CantHaveMoreThanAmountCountersSourceAbility(CantHaveMoreThanAmountCountersSourceAbility ability) {
        super(ability);
        this.counterType = ability.counterType;
        this.amount = ability.amount;
    }

    @Override
    public String getRule() {
        return "Rasputin can't have more than " + CardUtil.numberToText(this.amount) + ' ' + this.counterType.getName() + " counters on it.";
    }

    @Override
    public CantHaveMoreThanAmountCountersSourceAbility copy() {
        return new CantHaveMoreThanAmountCountersSourceAbility(this);
    }

    public CounterType getCounterType() {
        return this.counterType;
    }

    public int getAmount() {
        return this.amount;
    }
}

class CantHaveMoreThanAmountCountersSourceEffect extends ReplacementEffectImpl {

    private final CounterType counterType;
    private final int amount;

    CantHaveMoreThanAmountCountersSourceEffect(CounterType counterType, int amount) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, false);
        this.counterType = counterType;
        this.amount = amount;
    }

    CantHaveMoreThanAmountCountersSourceEffect(final CantHaveMoreThanAmountCountersSourceEffect effect) {
        super(effect);
        this.counterType = effect.counterType;
        this.amount = effect.amount;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_COUNTER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null) {
            permanent = game.getPermanentEntering(event.getTargetId());
        }
        return permanent != null
                && permanent.getId().equals(source.getSourceId())
                && event.getData().equals(this.counterType.getName())
                && permanent.getCounters(game).getCount(this.counterType) == this.amount;
    }

    @Override
    public CantHaveMoreThanAmountCountersSourceEffect copy() {
        return new CantHaveMoreThanAmountCountersSourceEffect(this);
    }
}
