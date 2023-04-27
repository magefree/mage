package mage.abilities.effects.common;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.FilterPlayer;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.predicate.other.PlayerIdPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class PreventAllDamageToAllEffect extends PreventionEffectImpl {

    protected FilterPermanentOrPlayer filter;

    public PreventAllDamageToAllEffect(Duration duration, FilterPermanent filterPermanent) {
        this(duration, createFilter(filterPermanent, null));
    }

    public PreventAllDamageToAllEffect(Duration duration, FilterPermanent filterPermanent, boolean onlyCombat) {
        this(duration, createFilter(filterPermanent, null), onlyCombat);
    }

    public PreventAllDamageToAllEffect(Duration duration, FilterPermanentOrPlayer filter) {
        this(duration, filter, false);
    }

    public PreventAllDamageToAllEffect(Duration duration, FilterPermanentOrPlayer filter, boolean onlyCombat) {
        super(duration, Integer.MAX_VALUE, onlyCombat, false);
        this.filter = filter;
        staticText = "prevent all "
                + (onlyCombat ? "combat " : "")
                + "damage that would be dealt to "
                + filter.getMessage()
                + (duration.toString().isEmpty() ? "" : ' ')
                + (duration == Duration.EndOfTurn ? "this turn" : duration.toString());
    }

    public PreventAllDamageToAllEffect(final PreventAllDamageToAllEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    private static FilterPermanentOrPlayer createFilter(FilterPermanent filterPermanent, FilterPlayer filterPlayer) {
        String mes1 = filterPermanent != null ? filterPermanent.getMessage() : "";
        String mes2 = filterPlayer != null ? filterPlayer.getMessage() : "";
        String message;
        if (!mes1.isEmpty() && !mes2.isEmpty()) {
            message = mes1 + " and " + mes2;
        } else {
            message = mes1 + mes2;
        }
        FilterPermanent filter1 = filterPermanent;
        if (filter1 == null) {
            filter1 = new FilterPermanent();
            filter1.add(new PermanentIdPredicate(UUID.randomUUID())); // disable filter
        }
        FilterPlayer filter2 = filterPlayer;
        if (filter2 == null) {
            filter2 = new FilterPlayer();
            filter2.add(new PlayerIdPredicate(UUID.randomUUID())); // disable filter
        }

        return new FilterPermanentOrPlayer(message, filter1, filter2);
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
            MageItem object;
            if (EventType.DAMAGE_PLAYER.equals(event.getType())) {
                object = game.getPlayer(event.getTargetId());
            } else {
                object = game.getObject(event.getTargetId());
            }
            if (object != null) {
                return filter.match(object, source.getControllerId(), source, game);
            }
        }
        return false;
    }

}
