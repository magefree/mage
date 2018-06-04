

package mage.abilities.effects.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.filter.FilterInPlay;
import mage.filter.common.FilterCreatureOrPlayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.PlayerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PreventAllDamageToAllEffect extends PreventionEffectImpl {

    protected FilterInPlay filter;
    
    public PreventAllDamageToAllEffect(Duration duration, FilterCreaturePermanent filter) {
        this(duration, createFilter(filter));
    }

    public PreventAllDamageToAllEffect(Duration duration, FilterInPlay filter) {
        this(duration, filter, false);
    }

    public PreventAllDamageToAllEffect(Duration duration, FilterInPlay filter, boolean onlyCombat) {
        super(duration, Integer.MAX_VALUE, onlyCombat);
        this.filter = filter;
        staticText = "Prevent all "
                + (onlyCombat ? "combat ":"")
                + "damage that would be dealt to " 
                + filter.getMessage()
                + (duration.toString().isEmpty() ?"": ' ' + duration.toString());
    }

    public PreventAllDamageToAllEffect(final PreventAllDamageToAllEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    private static FilterInPlay createFilter(FilterCreaturePermanent filter) {
        FilterCreatureOrPlayer newfilter = new FilterCreatureOrPlayer(filter.getMessage());
        newfilter.setCreatureFilter(filter);
        newfilter.getPlayerFilter().add(new PlayerIdPredicate(UUID.randomUUID()));
        return newfilter;
    }
    
    @Override
    public PreventAllDamageToAllEffect copy() {
        return new PreventAllDamageToAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
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
