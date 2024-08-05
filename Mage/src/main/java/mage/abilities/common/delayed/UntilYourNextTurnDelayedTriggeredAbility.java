
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

    private final TriggeredAbility ability;

    public UntilYourNextTurnDelayedTriggeredAbility(TriggeredAbility ability) {
        super(null, Duration.UntilYourNextTurn, false);
        if (ability.isLeavesTheBattlefieldTrigger()) {
            this.setLeavesTheBattlefieldTrigger(true);
        }
        this.ability = ability;
    }

    protected UntilYourNextTurnDelayedTriggeredAbility(final UntilYourNextTurnDelayedTriggeredAbility triggeredAbility) {
        super(triggeredAbility);
        this.ability = triggeredAbility.ability.copy();
    }

    @Override
    public UntilYourNextTurnDelayedTriggeredAbility copy() {
        return new UntilYourNextTurnDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return ability.checkEventType(event, game);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ability.setSourceId(this.getSourceId());
        ability.setControllerId(this.getControllerId());
        return ability.checkTrigger(event, game);
    }

    @Override
    public String getRule() {
        return "Until your next turn, " + CardUtil.getTextWithFirstCharLowerCase(ability.getRule());
    }

    @Override
    public Effects getEffects() {
        return ability.getEffects();
    }

    @Override
    public void addEffect(Effect effect) {
        ability.addEffect(effect);
    }

    @Override
    public Modes getModes() {
        return ability.getModes();
    }

    @Override
    public List<Watcher> getWatchers() {
        return ability.getWatchers();
    }

    @Override
    public void addWatcher(Watcher watcher) {
        ability.addWatcher(watcher);
    }

    @Override
    public Effects getEffects(Game game, EffectType effectType) {
        return ability.getEffects(game, effectType);
    }

    @Override
    public boolean isOptional() {
        return ability.isOptional();
    }

    @Override
    public void setSourceObjectZoneChangeCounter(int sourceObjectZoneChangeCounter) {
        ability.setSourceObjectZoneChangeCounter(sourceObjectZoneChangeCounter);
    }

    @Override
    public int getSourceObjectZoneChangeCounter() {
        return ability.getSourceObjectZoneChangeCounter();
    }
}
