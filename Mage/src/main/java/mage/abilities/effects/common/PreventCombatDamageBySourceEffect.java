package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author jeffwadsworth
 */
public class PreventCombatDamageBySourceEffect extends PreventionEffectImpl {

    public PreventCombatDamageBySourceEffect(Duration duration) {
        super(duration, Integer.MAX_VALUE, true);
        staticText = "Prevent all combat damage that would be dealt by {this}" + duration.toString();
    }

    protected PreventCombatDamageBySourceEffect(final PreventCombatDamageBySourceEffect effect) {
        super(effect);
    }

    @Override
    public PreventCombatDamageBySourceEffect copy() {
        return new PreventCombatDamageBySourceEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return super.applies(event, source, game)
                && event.getSourceId().equals(source.getSourceId());
    }
}
