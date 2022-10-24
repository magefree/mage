package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX
 */
public class PreventAllDamageByAllPermanentsEffect extends PreventionEffectImpl {

    protected final FilterPermanent filter;

    public PreventAllDamageByAllPermanentsEffect(Duration duration) {
        this(null, duration, false);
    }

    public PreventAllDamageByAllPermanentsEffect(Duration duration, boolean onlyCombat) {
        this(null, duration, onlyCombat);
    }

    public PreventAllDamageByAllPermanentsEffect(FilterPermanent filter, Duration duration, boolean onlyCombat) {
        super(duration, Integer.MAX_VALUE, onlyCombat);
        this.filter = filter;
        setText();
    }

    public PreventAllDamageByAllPermanentsEffect(final PreventAllDamageByAllPermanentsEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public PreventAllDamageByAllPermanentsEffect copy() {
        return new PreventAllDamageByAllPermanentsEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game) && event instanceof DamageEvent && event.getAmount() > 0) {
            DamageEvent damageEvent = (DamageEvent) event;
            if (damageEvent.isCombatDamage() || !onlyCombat) {
                if (filter == null) {
                    return true;
                }
                Permanent permanent = game.getPermanent(damageEvent.getSourceId());
                if (filter.match(permanent, source.getControllerId(), source, game)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder("prevent all ");
        if (onlyCombat) {
            sb.append("combat ");
        }
        sb.append("damage that would be dealt");
        if (duration == Duration.EndOfTurn) {
            sb.append(" this turn");
        }
        if (filter != null) {
            sb.append(" by ");
            sb.append(filter.getMessage());
        }
        staticText = sb.toString();
    }
}
