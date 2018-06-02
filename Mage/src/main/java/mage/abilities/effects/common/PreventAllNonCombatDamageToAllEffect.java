

package mage.abilities.effects.common;

import mage.constants.Duration;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.filter.FilterInPlay;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class PreventAllNonCombatDamageToAllEffect extends PreventionEffectImpl {

    protected FilterInPlay filter;

    public PreventAllNonCombatDamageToAllEffect(Duration duration, FilterInPlay filter) {
        super(duration, Integer.MAX_VALUE, false);
        this.filter = filter;
        staticText = "Prevent all non combat damage that would be dealt to " + filter.getMessage() + ' ' + duration.toString();
    }

    public PreventAllNonCombatDamageToAllEffect(final PreventAllNonCombatDamageToAllEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public PreventAllNonCombatDamageToAllEffect copy() {
        return new PreventAllNonCombatDamageToAllEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)
                && !((DamageEvent) event).isCombatDamage()) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null) {
                if (filter.match(permanent, source.getSourceId(), source.getControllerId(), game)) {
                    return true;
                }
            }
            else {
                Player player = game.getPlayer(event.getTargetId());
                if (player != null && filter.match(player, source.getSourceId(), source.getControllerId(), game)) {
                    return true;
                }
            }
        }
        return false;
    }

}
