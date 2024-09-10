
package mage.abilities.effects.common;

import mage.constants.Duration;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author Quercitron
 */
public class PreventAllDamageToAndByAttachedEffect extends PreventionEffectImpl {

    private final String attachedDescription;

    public PreventAllDamageToAndByAttachedEffect(Duration duration, String attachedDescription, boolean onlyCombat) {
        super(duration, Integer.MAX_VALUE, onlyCombat);
        this.attachedDescription = attachedDescription;
        this.staticText = setText();
    }

    protected PreventAllDamageToAndByAttachedEffect(final PreventAllDamageToAndByAttachedEffect effect) {
        super(effect);
        this.attachedDescription = effect.attachedDescription;
    }

    @Override
    public PreventAllDamageToAndByAttachedEffect copy() {
        return new PreventAllDamageToAndByAttachedEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            Permanent attachment = game.getPermanent(source.getSourceId());
            if (attachment != null && attachment.getAttachedTo() != null) {
                if (event.getTargetId().equals(attachment.getAttachedTo()) || event.getSourceId().equals(attachment.getAttachedTo())) {
                    return true;
                }
            }
        }
        return false;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("Prevent all ");
        if (onlyCombat) {
            sb.append("combat ");
        }
        sb.append("damage that would be dealt to and dealt by ");
        sb.append(attachedDescription);
        return sb.toString();
    }

}
