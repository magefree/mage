package mage.cards.u;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class UndyingMalice extends CardImpl {

    public UndyingMalice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Until end of turn, target creature gains "When this creature dies, return it to the battlefield tapped under its owner's control with a +1/+1 counter on it."
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(new DiesSourceTriggeredAbility(new UndyingMaliceReturnToBattlefieldEffect()))
                .setText("Until end of turn, target creature gains \"When this creature dies, return it to the battlefield tapped under its owner's control with a +1/+1 counter on it.\""));
    }

    private UndyingMalice(final UndyingMalice card) {
        super(card);
    }

    @Override
    public UndyingMalice copy() {
        return new UndyingMalice(this);
    }
}

class UndyingMaliceReturnToBattlefieldEffect extends ReturnSourceFromGraveyardToBattlefieldEffect {

    public UndyingMaliceReturnToBattlefieldEffect() {
        super(true);
        staticText = "return it to the battlefield tapped under its owner's control with a +1/+1 counter on it";
    }

    private UndyingMaliceReturnToBattlefieldEffect(final UndyingMaliceReturnToBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public UndyingMaliceReturnToBattlefieldEffect copy() {
        return new UndyingMaliceReturnToBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UndyingMaliceCounterEffect counterEffect = new UndyingMaliceCounterEffect();
        game.addEffect(counterEffect, source);
        return super.apply(game, source);
    }
}

class UndyingMaliceCounterEffect extends ReplacementEffectImpl {

    public UndyingMaliceCounterEffect() {
        super(Duration.EndOfStep, Outcome.BoostCreature);
    }

    private UndyingMaliceCounterEffect(final UndyingMaliceCounterEffect effect) {
        super(effect);
    }

    @Override
    public UndyingMaliceCounterEffect copy() {
        return new UndyingMaliceCounterEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature == null) {
            return false;
        }
        creature.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
        discard();
        return false;
    }
}
