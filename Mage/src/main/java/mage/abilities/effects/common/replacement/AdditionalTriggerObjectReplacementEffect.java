package mage.abilities.effects.common.replacement;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterInPlay;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;
import mage.util.CardUtil;

public class AdditionalTriggerObjectReplacementEffect extends ReplacementEffectImpl {

    private final FilterInPlay filter;
    private final Condition condition;

    public AdditionalTriggerObjectReplacementEffect(FilterInPlay filter) {
        this(filter, null);
    }

    public AdditionalTriggerObjectReplacementEffect(FilterInPlay filter, Condition condition) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.filter = filter;
        this.condition = condition;
        this.staticText = (condition == null ? "" : ("as long as " + condition.toString() + ", ")) +
            "if a triggered ability of " + filter.getMessage() + " triggers, that ability triggers an additional time.";
    }

    protected AdditionalTriggerObjectReplacementEffect(final AdditionalTriggerObjectReplacementEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.condition = effect.condition;
    }

    @Override
    public AdditionalTriggerObjectReplacementEffect copy() {
        return new AdditionalTriggerObjectReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (this.condition == null || this.condition.apply(game, source)) {
            final MageObject object = game.getObject(((NumberOfTriggersEvent)event).getSourceId());
            return this.filter.checkObjectClass(object) && this.filter.match(object, source.getControllerId(), source, game);
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 1));
        return false;
    }
}
