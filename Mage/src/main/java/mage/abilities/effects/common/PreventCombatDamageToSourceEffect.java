

package mage.abilities.effects.common;

import mage.constants.Duration;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author jeffwadsworth
 */
public class PreventCombatDamageToSourceEffect extends PreventionEffectImpl {

    public PreventCombatDamageToSourceEffect(Duration duration) {
        super(duration, Integer.MAX_VALUE, true);
        staticText = "Prevent all combat damage that would be dealt to {this}" + duration.toString();
    }

    protected PreventCombatDamageToSourceEffect(final PreventCombatDamageToSourceEffect effect) {
        super(effect);
    }

    @Override
    public PreventCombatDamageToSourceEffect copy() {
        return new PreventCombatDamageToSourceEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (event.getTargetId().equals(source.getSourceId())) {
                return true;
            }
        }
        return false;
    }

}

