
package mage.abilities.common.delayed;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.Modes;
import mage.abilities.TriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.constants.Duration;
import mage.constants.EffectType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.List;

/**
 * "Until your next turn, [trigger]"
 *
 * @author Susucr
 */
public class UntilYourNextTurnDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final TriggeredAbility trigger;

    public UntilYourNextTurnDelayedTriggeredAbility(TriggeredAbility trigger) {
        super(null, Duration.UntilYourNextTurn);
        if (trigger.isLeavesTheBattlefieldTrigger()) {
            this.setLeavesTheBattlefieldTrigger(true);
        }
        this.trigger = trigger;
    }

    protected UntilYourNextTurnDelayedTriggeredAbility(final UntilYourNextTurnDelayedTriggeredAbility ability) {
        super(ability);
        this.trigger = ability.trigger.copy();
    }

    @Override
    public UntilYourNextTurnDelayedTriggeredAbility copy() {
        return new UntilYourNextTurnDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return trigger.checkEventType(event, game);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        trigger.setSourceId(this.getSourceId());
        trigger.setControllerId(this.getControllerId());
        return trigger.checkTrigger(event, game);
    }

    @Override
    public String getRule() {
        return "Until your next turn, " + CardUtil.getTextWithFirstCharLowerCase(trigger.getRule());
    }

    @Override
    public Effects getEffects() {
        return trigger.getEffects();
    }

    @Override
    public void addEffect(Effect effect) {
        trigger.addEffect(effect);
    }

    @Override
    public Modes getModes() {
        return trigger.getModes();
    }

    @Override
    public List<Watcher> getWatchers() {
        return trigger.getWatchers();
    }

    @Override
    public void addWatcher(Watcher watcher) {
        trigger.addWatcher(watcher);
    }

    @Override
    public Effects getEffects(Game game, EffectType effectType) {
        return trigger.getEffects(game, effectType);
    }

    @Override
    public boolean isOptional() {
        return trigger.isOptional();
    }

    @Override
    public void setSourceObjectZoneChangeCounter(int sourceObjectZoneChangeCounter) {
        trigger.setSourceObjectZoneChangeCounter(sourceObjectZoneChangeCounter);
    }

    @Override
    public int getSourceObjectZoneChangeCounter() {
        return trigger.getSourceObjectZoneChangeCounter();
    }
}
