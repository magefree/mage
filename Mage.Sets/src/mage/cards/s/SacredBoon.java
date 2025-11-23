package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class SacredBoon extends CardImpl {

    public SacredBoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Prevent the next 3 damage that would be dealt to target creature this turn. At the beginning of the next end step, put a +0/+1 counter on that creature for each 1 damage prevented this way.
        this.getSpellAbility().addEffect(new SacredBoonPreventDamageTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SacredBoon(final SacredBoon card) {
        super(card);
    }

    @Override
    public SacredBoon copy() {
        return new SacredBoon(this);
    }
}

class SacredBoonPreventDamageTargetEffect extends PreventionEffectImpl {


    SacredBoonPreventDamageTargetEffect(Duration duration) {
        super(duration, 3, false);
        staticText = "Prevent the next 3 damage that would be dealt to target creature this turn. At the beginning of the next end step, put a +0/+1 counter on that creature for each 1 damage prevented this way.";
    }

    private SacredBoonPreventDamageTargetEffect(final SacredBoonPreventDamageTargetEffect effect) {
        super(effect);
    }

    @Override
    public SacredBoonPreventDamageTargetEffect copy() {
        return new SacredBoonPreventDamageTargetEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionEffectData = preventDamageAction(event, source, game);
        if (preventionEffectData.getPreventedDamage() > 0) {
            Permanent targetPermanent = game.getPermanent(source.getTargets().getFirstTarget());
            if (targetPermanent != null) {
                DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                        new AddCountersTargetEffect(CounterType.P0P1.createInstance(preventionEffectData.getPreventedDamage()))
                                .setTargetPointer(new FixedTarget(targetPermanent, game)));
                game.addDelayedTriggeredAbility(delayedAbility, source);
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return super.applies(event, source, game) && source.getTargets().getFirstTarget().equals(event.getTargetId());
    }

}
