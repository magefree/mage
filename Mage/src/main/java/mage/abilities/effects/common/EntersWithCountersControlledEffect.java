package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class EntersWithCountersControlledEffect extends ReplacementEffectImpl {

    private final FilterPermanent filter;
    private final Counter counter;
    private final boolean excludeSource;

    public EntersWithCountersControlledEffect(FilterPermanent filter, Counter counter, boolean excludeSource) {
        this(Duration.WhileOnBattlefield, filter, counter, excludeSource);
    }

    public EntersWithCountersControlledEffect(Duration duration, FilterPermanent filter, Counter counter, boolean excludeSource) {
        super(duration, Outcome.BoostCreature);
        this.filter = filter;
        this.counter = counter;
        this.excludeSource = excludeSource;
        staticText = makeText();
    }

    private EntersWithCountersControlledEffect(final EntersWithCountersControlledEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
        this.counter = effect.counter.copy();
        this.excludeSource = effect.excludeSource;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (excludeSource && event.getTargetId().equals(source.getSourceId())) {
            return false;
        }
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        return permanent != null
                && permanent.isControlledBy(source.getControllerId())
                && filter.match(permanent, source.getControllerId(), source, game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature != null) {
            creature.addCounters(counter, source.getControllerId(), source, game, event.getAppliedEffects());
        }
        return false;
    }

    private String makeText() {
        StringBuilder sb = new StringBuilder();
        if (duration == Duration.EndOfTurn) {
            sb.append("this turn, ");
        }
        sb.append("each ");
        if (excludeSource) {
            sb.append("other ");
        }
        sb.append(filter.getMessage());
        sb.append(" you control enters with ");
        sb.append(CardUtil.numberToText(counter.getCount(), "an"));
        sb.append(" additional ");
        sb.append(counter.getName());
        if (counter.getCount() > 1) {
            sb.append('s');
        }
        sb.append(" on it");
        return sb.toString();
    }

    @Override
    public EntersWithCountersControlledEffect copy() {
        return new EntersWithCountersControlledEffect(this);
    }
}
