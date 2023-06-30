package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PreventAllDamageToSourceEffect extends PreventionEffectImpl {

    public PreventAllDamageToSourceEffect(Duration duration) {
        super(duration, Integer.MAX_VALUE, false);
        if (duration == Duration.EndOfTurn) {
            staticText = "Prevent all damage that would be dealt to {this} this turn";
        } else {
            staticText = "Prevent all damage that would be dealt to {this}";
        }
    }

    public PreventAllDamageToSourceEffect(final PreventAllDamageToSourceEffect effect) {
        super(effect);
    }

    @Override
    public PreventAllDamageToSourceEffect copy() {
        return new PreventAllDamageToSourceEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return super.applies(event, source, game) && event.getTargetId().equals(source.getSourceId());
    }
}
