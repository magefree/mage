

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
        //Some durations have no text
        if ( duration.toString().length()>0){
            staticText = "Prevent all damage that would be dealt to {this} " + duration.toString();
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
        if (super.applies(event, source, game)) {
            if (event.getTargetId().equals(source.getSourceId())) {
                return true;
            }
        }
        return false;
    }

}
