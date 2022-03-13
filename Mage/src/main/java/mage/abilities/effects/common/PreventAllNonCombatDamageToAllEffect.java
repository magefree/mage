package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author jeffwadsworth
 */
public class PreventAllNonCombatDamageToAllEffect extends PreventionEffectImpl {

    protected final FilterPermanent filter;
    private final boolean andToYou;

    public PreventAllNonCombatDamageToAllEffect(Duration duration, FilterPermanent filter) {
        this(duration, filter, false);
    }

    public PreventAllNonCombatDamageToAllEffect(Duration duration, FilterPermanent filter, boolean andToYou) {
        super(duration, Integer.MAX_VALUE, false);
        this.filter = filter;
        this.andToYou = andToYou;
        staticText = "Prevent all noncombat damage that would be dealt to " + (andToYou ? "you and " : "") + filter.getMessage();

        if (duration != Duration.WhileOnBattlefield) {
            staticText += ' ' + duration.toString();
        }
    }

    private PreventAllNonCombatDamageToAllEffect(final PreventAllNonCombatDamageToAllEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
        this.andToYou = effect.andToYou;
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
                return filter.match(permanent, source.getControllerId(), source, game);
            }
            return andToYou && source.getControllerId().equals(event.getTargetId());
        }
        return false;
    }

}
