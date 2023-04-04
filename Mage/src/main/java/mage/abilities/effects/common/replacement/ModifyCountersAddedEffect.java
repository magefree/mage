package mage.abilities.effects.common.replacement;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class ModifyCountersAddedEffect extends ReplacementEffectImpl {

    private final FilterPermanent filter;
    private final CounterType counterType;

    public ModifyCountersAddedEffect(FilterPermanent filter, CounterType counterType) {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature, false);
        this.filter = filter;
        this.counterType = counterType;
        staticText = "if one or more " + (counterType != null ? counterType + " " : "") + "counters would be put on " +
                CardUtil.addArticle(filter.getMessage()) + " you control, that many plus one " +
                (counterType != null ? counterType : "of each of those kinds of") + " counters are put on it instead";
    }

    private ModifyCountersAddedEffect(final ModifyCountersAddedEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.counterType = effect.counterType;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmountForCounters(event.getAmount() + 1, true);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if ((counterType != null && !event.getData().equals(counterType.getName())) || event.getAmount() <= 0) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null) {
            permanent = game.getPermanentEntering(event.getTargetId());
        }
        return permanent != null && filter.match(permanent, source.getControllerId(), source, game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ModifyCountersAddedEffect copy() {
        return new ModifyCountersAddedEffect(this);
    }
}
