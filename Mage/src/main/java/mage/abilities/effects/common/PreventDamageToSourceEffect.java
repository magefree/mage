
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author Quercitron
 */
public class PreventDamageToSourceEffect extends PreventionEffectImpl {

    public PreventDamageToSourceEffect(Duration duration, int amountToPrevent) {
        super(duration, amountToPrevent, false);
    }

    public PreventDamageToSourceEffect(final PreventDamageToSourceEffect effect) {
        super(effect);
    }

    @Override
    public PreventDamageToSourceEffect copy() {
        return new PreventDamageToSourceEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game); //To change body of generated methods, choose Tools | Templates.
        if (duration.isOnlyValidIfNoZoneChange()) {
            // If source permanent is no longer onto battlefield discard the effect
            if (source.getSourcePermanentIfItStillExists(game) == null) {
                discard();
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return super.applies(event, source, game) && event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        if (amountToPrevent == Integer.MAX_VALUE) {
            sb.append("Prevent all damage that would be dealt to ");
        } else {
            sb.append("Prevent the next ").append(amountToPrevent).append(" damage that would be dealt to ");
        }
        sb.append("{this} ");
        if (duration == Duration.EndOfTurn) {
            sb.append("this turn");
        } else {
            sb.append(duration.toString());
        }
        return sb.toString();
    }
}
