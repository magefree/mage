
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class PreventAllDamageByAttachedEffect extends PreventionEffectImpl {

    private final String attachedDescription;

    public PreventAllDamageByAttachedEffect(Duration duration, String attachedDescription, boolean onlyCombat) {
        super(duration, Integer.MAX_VALUE, onlyCombat, false);
        this.attachedDescription = attachedDescription;
        staticText = setText();
    }

    protected PreventAllDamageByAttachedEffect(final PreventAllDamageByAttachedEffect effect) {
        super(effect);
        this.attachedDescription = effect.attachedDescription;
    }

    @Override
    public PreventAllDamageByAttachedEffect copy() {
        return new PreventAllDamageByAttachedEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            Permanent attachment = game.getPermanent(source.getSourceId());
            if (attachment != null && attachment.getAttachedTo() != null) {
                if (event.getSourceId().equals(attachment.getAttachedTo())) {
                    return true;
                }
            }
        }
        return false;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("prevent all ");
        if (onlyCombat) {
            sb.append("combat ");
        }
        sb.append("damage that would be dealt by ");
        sb.append(attachedDescription);
        return sb.toString();
    }
}
