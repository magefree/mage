package mage.cards.e;

import mage.abilities.condition.common.TargetHasSubtypeCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class EssenceFlux extends CardImpl {

    public EssenceFlux(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Exile target creature you control, then return that card to the battlefield under its owner's control. If it's a Spirit, put a +1/+1 counter on it.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new ExileThenReturnTargetEffect(false, false).withAfterEffect(
                new ConditionalOneShotEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                        new TargetHasSubtypeCondition(SubType.SPIRIT), "If it's a Spirit, put a +1/+1 counter on it")
        ));
    }

    private EssenceFlux(final EssenceFlux card) {
        super(card);
    }

    @Override
    public EssenceFlux copy() {
        return new EssenceFlux(this);
    }
}