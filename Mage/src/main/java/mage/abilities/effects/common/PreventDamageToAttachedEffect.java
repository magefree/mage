package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.AttachmentType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class PreventDamageToAttachedEffect extends PreventionEffectImpl {

    protected AttachmentType attachmentType;

    public PreventDamageToAttachedEffect(Duration duration, AttachmentType attachmentType, boolean combatOnly) {
        this(duration, attachmentType, Integer.MAX_VALUE, combatOnly);
    }

    public PreventDamageToAttachedEffect(Duration duration, AttachmentType attachmentType, int amountToPrevent, boolean combatOnly) {
        super(duration, amountToPrevent, combatOnly, false);
        this.attachmentType = attachmentType;
        staticText = setText();
    }

    public PreventDamageToAttachedEffect(final PreventDamageToAttachedEffect effect) {
        super(effect);
        this.attachmentType = effect.attachmentType;
    }

    @Override
    public PreventDamageToAttachedEffect copy() {
        return new PreventDamageToAttachedEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (!onlyCombat || ((DamageEvent) event).isCombatDamage()) {
                Permanent attachment = game.getPermanent(source.getSourceId());
                if (attachment != null
                        && attachment.getAttachedTo() != null) {
                    if (event.getTargetId().equals(attachment.getAttachedTo())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder();
        if (amountToPrevent == Integer.MAX_VALUE) {
            sb.append("prevent all ");
            if (onlyCombat) {
                sb.append("combat ");
            }
            sb.append("damage that would be dealt to ");
            sb.append(attachmentType.verb()).append(" creature");
        } else {
            sb.append("If a source would deal ");
            if (onlyCombat) {
                sb.append("combat ");
            }
            sb.append("damage to ");
            sb.append(attachmentType.verb());
            sb.append(" creature, prevent ").append(amountToPrevent);
            sb.append(" of that damage");
        }
        return sb.toString();
    }
}
