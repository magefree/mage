package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Temper extends CardImpl {

    public Temper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{1}{W}");

        // Prevent the next X damage that would be dealt to target creature this turn. For each 1 damage prevented this way, put a +1/+1 counter on that creature.
        this.getSpellAbility().addEffect(new TemperPreventDamageTargetEffect(GetXValue.instance, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Temper(final Temper card) {
        super(card);
    }

    @Override
    public Temper copy() {
        return new Temper(this);
    }
}

class TemperPreventDamageTargetEffect extends PreventionEffectImpl {

    public TemperPreventDamageTargetEffect(DynamicValue dVal, Duration duration) {
        super(duration, 0, false, true, dVal);
        staticText = "Prevent the next X damage that would be dealt to target creature this turn. For each 1 damage prevented this way, put a +1/+1 counter on that creature";
    }

    private TemperPreventDamageTargetEffect(final TemperPreventDamageTargetEffect effect) {
        super(effect);
    }

    @Override
    public TemperPreventDamageTargetEffect copy() {
        return new TemperPreventDamageTargetEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionEffectData = preventDamageAction(event, source, game);
        if (preventionEffectData.getPreventedDamage() > 0) {
            Permanent targetPermanent = game.getPermanent(source.getTargets().getFirstTarget());
            if (targetPermanent != null) {
                targetPermanent.addCounters(CounterType.P1P1.createInstance(preventionEffectData.getPreventedDamage()), source.getControllerId(), source, game);
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game)) {
            return source.getTargets().getFirstTarget().equals(event.getTargetId());
        }
        return false;
    }

}
