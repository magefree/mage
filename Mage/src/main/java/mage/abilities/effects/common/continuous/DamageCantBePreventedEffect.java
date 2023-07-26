package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;

public class DamageCantBePreventedEffect extends ContinuousRuleModifyingEffectImpl {

    public DamageCantBePreventedEffect(Duration duration, String staticText) {
        super(duration, Outcome.Benefit);
        this.staticText = staticText;
    }

    public DamageCantBePreventedEffect(final DamageCantBePreventedEffect effect) {
        super(effect);
    }

    @Override
    public DamageCantBePreventedEffect copy() {
        return new DamageCantBePreventedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PREVENT_DAMAGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }
}
